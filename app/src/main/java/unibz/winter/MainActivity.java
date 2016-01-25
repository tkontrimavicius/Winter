package unibz.winter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import unibz.winter.model.DatabaseHandler;
import unibz.winter.model.Msg;



public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {
    //The logic
    private  Controller logic = new Controller();
    private String originalText;
    private  String translatedText;
    private String revertedText;
    //Database handler
    private DatabaseHandler db;

    //morse sound playlist
    private Timer timer;
    private MediaPlayer mp;
    private ArrayList<Integer> playlist;
    private int i=0;

    //flashlight variables
    //boolean flashOn= false;
    private Camera cam ;

    //fragments code
    private View sendView ;
    private View historyView ;
    private View translationView ;
    private LayoutInflater layoutinflater;
    ViewGroup parent = null;
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // fragment layout
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
        }
        // end fragment layout


        // logic
        db = new DatabaseHandler(this);

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Msg> Msgs = db.getAllMsgs();

        for (int i = 0; i < Msgs.size(); i++) {
            System.out.println("Reading: " + Msgs.get(i).getMsg() +"-"+Msgs.get(i).getMorseMsg()+"-"+Msgs.get(i).getType());

        }


        //buttons
        layoutinflater =(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sendView = layoutinflater.inflate(R.layout.fragment_send, parent, false);
        setTextMessage();
        Button sendSoundBotton=(Button) sendView.findViewById(R.id.sendSound);
        Button sendFlashBotton=(Button) sendView.findViewById(R.id.sendFlash);


        //send flash morse msg
        sendFlashBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("OUTPUT: onclick");
                setTextMessage();
                //send flash button pressed
                boolean hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
                System.out.println("OUTPUT: FLASH SUPPORTED ");
                if (hasFlash) {
                    db.addMsg(new Msg(originalText, translatedText, "flash"));


                    String[] symbols = translatedText.split("");
                    for (int i = 0; i < symbols.length; i++) {
                        if (symbols[i].equals(".")) {
                            try {
                                flashTurnOn();
                                Thread.sleep(200);
                                flashTurnOff();
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        } else if (symbols[i].equals("-")) {
                            try {
                                flashTurnOn();
                                Thread.sleep(450);
                                flashTurnOff();
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        });

        //send sound morse msg
        sendSoundBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextMessage();
                db.addMsg(new Msg(originalText, translatedText, "sound"));

                playlist = logic.constructSoundMessage(translatedText);
                i = 0;
                mp = MediaPlayer.create(MainActivity.this, playlist.get(i));
                mp.start();
                timer = new Timer();
                if (playlist.size() > 1) {
                    playNext();
                } else {
                    System.out.println("OUTPUT: finished playing");

                }


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch(position){
                case 0:
                    fragment = new TestFragment();
                    break;
                case 1:
                    fragment = new TestFragment();
                    //fragment = new RaggaeMusicFragment();
                case 2:
                    fragment = new TestFragment();
                    //fragment = new RaggaeMusicFragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section2).toUpperCase(l);
            }
            return null;
        }
    }


//logic
    public void setTextMessage()
    {
        //input to translate
        EditText txtname = (EditText)sendView.findViewById(R.id.morseMsg);
        String textToSend      =  txtname.getText().toString();
        originalText =textToSend;
        System.out.println("VALUE: "+textToSend);

        //text to translate
         TextView translated = (TextView) sendView.findViewById(R.id.translated);
//        TextView reverted = (TextView) findViewById(R.id.reverted);
//        TextView text = (TextView) findViewById(R.id.text);
//        text.setText("Example text to send:"+textToSend);

        //translating from text to morse
        translatedText = logic.codeTextToMorse(textToSend);
        translated.setText(translatedText);

        //translating from morse to text again
        revertedText = logic.decodeMorseToText(translatedText);
        //reverted.setText("Translated from morse: " + revertedText);
    }
    public void flashTurnOn()
    {
        cam = Camera.open();
        Camera.Parameters p = cam.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        cam.setParameters(p);
        cam.startPreview();
    }

    public void flashTurnOff()
    {
        System.out.println("OUTPUT: FLASH OFF! ");
        cam.stopPreview();
        cam.release();

    }

    public void playNext() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mp.reset();
                mp = MediaPlayer.create(MainActivity.this,playlist.get(++i));
                mp.start();
                if (playlist.size() > i+1) {
                    playNext();
                }
            }
        },mp.getDuration());
    }

    @Override
    public void onDestroy() {
        if (mp.isPlaying())
            mp.stop();
        timer.cancel();
        super.onDestroy();
    }




}

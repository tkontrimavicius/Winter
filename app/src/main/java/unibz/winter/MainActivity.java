package unibz.winter;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;


public class MainActivity extends AppCompatActivity {
    //The logic
    private  Controller logic = new Controller();

    //morse sound playlist
    Timer timer;
    MediaPlayer mp;
    ArrayList<Integer> playlist;
    int i=0;

    //flashlight variables
    boolean flashOn= false;
    Camera cam ;



    private boolean flag = true;
    private  String translatedText;
    private String revertedText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView translated = (TextView) findViewById(R.id.translated);
        TextView reverted = (TextView) findViewById(R.id.reverted);
        TextView text = (TextView) findViewById(R.id.text);

        //text to translate
        String textToSend = "SOS";
        text.setText("Text to send:"+textToSend);

        //translating from text to morse
         translatedText = logic.codeTextToMorse(textToSend);
        translated.setText("From text to morse: " +translatedText);

        //translating from morse to text again
         revertedText = logic.decodeMorseToText(translatedText);
        reverted.setText("Translated from morse: " + revertedText);
        
        //buttons
        Button sendSoundBotton=(Button)findViewById(R.id.sendSound);
        Button sendFlashBotton=(Button)findViewById(R.id.sendFlash);




        //send flash morse msg
        sendFlashBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send flash button pressed
                boolean hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
                System.out.println("OUTPUT: FLASH SUPPORTED ");
                if(hasFlash)
                {

                    String[] symbols = translatedText.split("");
                    for (int i = 0; i <symbols.length ; i++) {
                        if (symbols[i].equals(".")) {
                            try {
                                flashTurnOn();
                                Thread.sleep(250);
                                flashTurnOff();
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        } else if (symbols[i].equals("-")) {
                            try {
                                flashTurnOn();
                                Thread.sleep(500);
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
}

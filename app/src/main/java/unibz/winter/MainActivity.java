package unibz.winter;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    //The logic
    private  Controller logic = new Controller();



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
        translated.setText("From text to morese: " +translatedText);

        //translating from morse to text again
         revertedText = logic.decodeMorseToText(translatedText);
        reverted.setText("Translated from morse: " + revertedText);
        Button sendButton=(Button)findViewById(R.id.send);



        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logic.sendSoundMessage(translatedText,MainActivity.this);
//                }


            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
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

package unibz.winter;

/**
 * Created by jetzt on 12/10/15.
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.media.SoundPool;
import android.util.Log;
import android.widget.TextView;

import unibz.winter.model.ModelConversation;
import unibz.winter.model.ModelUser;

public class Controller extends Application{

    private  ArrayList<ModelConversation> conversations = new ArrayList<ModelConversation>();
    private ModelUser user = new ModelUser("Test User");


    //Making a playlist of sounds
    private MediaPlayer mp;
    private Timer timer;
    private ArrayList<Integer> playlist;
    private  int i=0;




   public String codeTextToMorse (String text) {
       Morse translator = new Morse(text);
       return translator.codeToMorse();
   }

    public String decodeMorseToText (String text) {
        Morse translator = new Morse(text);
        return translator.decodeFromMorse();

    }
    //to play the play list

    public void playNext(final Context context) {
                mp.reset();
                mp = MediaPlayer.create(context,playlist.get(++i));
                mp.start();
    }



    public void sendSoundMessage (String text, final Context context) {
        try {

         //setting word to send
            String userphrase =  text.toLowerCase();
             Integer wordlength = userphrase.length();
             char[] letarray = userphrase.toCharArray();




                playlist = new ArrayList<>();
                playlist.add(R.raw.line2);
                playlist.add(R.raw.dot2);


                mp = MediaPlayer.create(context,playlist.get(0));
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {

                        try {
                            Log.i("Completion Listener", "Song Complete");
                            mp.release();
                            mp.reset();
                            mp.setDataSource("android.resource://winter/raw/line2.mp3");
                            mp.prepare();
                            mp.start();
                        }catch (Exception e){}
                    }
                });

                ///timer = new Timer();
                //if (playlist.size()>1) playNext(context);






//            //Setting sounds
//            MediaPlayer line=MediaPlayer.create(context,R.raw.line2);
//            MediaPlayer dot=MediaPlayer.create(context,R.raw.dot2);

//            //to add result in the interface
//            TextView sendingTextView = (TextView) ((Activity) context).findViewById(R.id.sending);
//
//            //loop for making the sounds
//            for(int i=0; i<wordlength; i++){
//                if (Character.toString(letarray[i]).matches( ".")) {
//
//                    dot.start();
//                  //  Thread.sleep(1000);
//                    dot.stop();
//
//
//
//                }
//                if (Character.toString(letarray[i]).matches( "-"))
//                {
//                    line.start();
//                  //  Thread.sleep(100);
//                    line.stop();
//
//
//                }
//                //adding the result to the text view in the interface
//                sendingTextView.setText(sendingTextView.getText() +Character.toString(letarray[i]) );
//                Thread.sleep(1000);
//
//               // Thread.sleep(1500);
//            }






        } catch(Exception ex) {
            Thread.currentThread().interrupt();
        }


    }

    private void codeLightToText (String text) {

    }

    private void decodeSoundToText (String text) {

    }

    private void decodeLightToText (String text) {

    }



}
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


    public  ArrayList<Integer> constructSoundMessage (String text) {
        ArrayList<Integer> playlist = new ArrayList<Integer>();

        String[] symbols = text.split("");
        System.out.println("OUTPUT: "+text.length());


        for (int i = 0; i <symbols.length ; i++) {
//
            if(symbols[i].equals("."))
            {
                System.out.println("OUTPUT: .");
                playlist.add(R.raw.dot2);
//
            }
            else if(symbols[i].equals("-"))
            {
                System.out.println("OUTPUT: -");
                playlist.add(R.raw.line2);
            }

        }

        System.out.println("OUTPUT: finished");
        return playlist;

    }




}
package unibz.winter;

/**
 * Created by jetzt on 12/10/15.
 */

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;

import unibz.winter.model.DatabaseHandler;

public class Controller extends Application{

    private  ArrayList<DatabaseHandler> conversations = new ArrayList<DatabaseHandler>();



    //Making a playlist of sounds
    private ArrayList<Integer> playlist;
    private  int i=0;


    public void saveMsg (Context context)
    {
        DatabaseHandler mDbHelper = new DatabaseHandler(context);//getContext()


    }

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
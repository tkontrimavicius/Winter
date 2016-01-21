package unibz.winter;

/**
 * Created by jetzt on 12/10/15.
 */

import java.util.ArrayList;
import android.app.Application;

import unibz.winter.model.ModelConversation;
import unibz.winter.model.ModelUser;

public class Controller extends Application{

    private  ArrayList<ModelConversation> conversations = new ArrayList<ModelConversation>();
    private ModelUser user = new ModelUser("Test User");





   public String codeTextToMorse (String text) {
       Morse translator = new Morse(text);
       return translator.codeToMorse();
   }

    public String decodeMorseToText (String text) {
        Morse translator = new Morse(text);
        return translator.decodeFromMorse();

    }

    private void decodeSoundToText (String text) {

    }

    private void decodeLightToText (String text) {

    }

    private void codeSoundToText (String text) {

    }

    private void codeLightToText (String text) {

    }


}
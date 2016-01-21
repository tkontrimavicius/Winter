package unibz.winter;

/**
 * Created by jetzt on 12/14/15.
 */
import java.util.ArrayList;


public class Morse extends Controller{
    private String userphrase;
    private String[] wordamt;

    private int wordlength;

    private static final char[] Letters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private static final String[] MorseLet = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",
            ".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};

    public Morse(String msg) {
        userphrase =  msg.toLowerCase();
        wordlength = userphrase.length();
    }

    public String codeToMorse() {
        String[] encodephrase;
        encodephrase = new String[wordlength];
        char[] letarray = userphrase.toCharArray();
        for(int i=0; i<wordlength; i++){
            for(int j=0; j<Letters.length; j++){
                if (letarray[i] == Letters[j]){
                    encodephrase[i] = MorseLet[j];
                }
                if(letarray[i] == ' '){
                    encodephrase[i]= " ";
                }

            }
        }
        return this.constructWordFromArray(encodephrase);
    }

    private String constructWordFromArray (String[] word)
    {
        String result= "";

        for (String letter:word) {
            result = result +" "+ letter;
        }
        return result;
    }

    public String decodeFromMorse() {

        String[] words = userphrase.split("\\s\\s\\s");
        wordamt = words;
        String result= "";
        //first we separate words and then letters
        for (int i = 0; i < wordamt.length; i++) {
            result = result+" "+SeperateLetters(words[i]);
        }
        return result;
    }

    private String SeperateLetters(String l) {

        String[] letters = l.split("\\s");
        String result="";

        for(int i=0; i<letters.length; i++){
            for(int j=0; j<MorseLet.length; j++){
                if(letters[i].equals(MorseLet[j])){
                   // System.out.print(Letters[j]);
                    result =result+ Character.toString(Letters[j]);
                }
            }
        }
       // System.out.print(" ");
        return result;

    }






}
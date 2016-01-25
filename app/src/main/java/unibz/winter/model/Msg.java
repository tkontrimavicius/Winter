package unibz.winter.model;

/**
 * Created by jetzt on 1/25/16.
 */
public class Msg {
    int _id;
    String msg;
    String morseMsg;
    String type;
    public Msg(){   }

    public Msg(int id, String name, String morseMsg, String type){
        this._id = id;
        this.msg = name;
        this.morseMsg = morseMsg;
        this.type = type;

    }
    public Msg(String name, String morseMsg, String type){
        this.msg = name;
        this.morseMsg = morseMsg;
        this.type = type;

    }


    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getMsg(){
        return this.msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public void setMorseMsg(String morseMsg){
        this.morseMsg = morseMsg;
    }
    public String getMorseMsg(){
        return this.msg;
    }
}
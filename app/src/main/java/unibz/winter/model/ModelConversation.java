package unibz.winter.model;

/**
 * Created by jetzt on 12/10/15.
 */
public class ModelConversation {
    public String recipientID;
    public String senderID;
    public String msg;
    public String type; //it depends on if it is sent with light or sound

    public ModelConversation(String recipientID, String senderID, String msg, String type) {
        this.recipientID = recipientID;
        this.senderID = senderID;
        this.msg = msg;
        this.type = type;
    }

    public String getRecipientID() {
        return recipientID;
    }

    public void setRecipientID(String recipientID) {
        this.recipientID = recipientID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package xyz.cro.croandroidsocketexample.models;

/**
 * Created by juyounglee on 2017. 9. 1..
 */

public class ChatMessage {
    private String userAction;
    private String messageType;
    private String messageOwner;
    private String messageContent;

    public ChatMessage(String userAction, String messageType, String messageOwner, String messageContent) {
        this.userAction = userAction;
        this.messageType = messageType;
        this.messageOwner = messageOwner;
        this.messageContent = messageContent;
    }

    public String getUserAction() {
        return userAction;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getMessageOwner() {
        return messageOwner;
    }

    public String getMessageContent() {
        return messageContent;
    }
}

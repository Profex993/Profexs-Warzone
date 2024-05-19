package client.userInterface;

import client.clientMain.ConstantsClient;

/**
 * class for chat message
 */
public class ChatMessage {
    private int timer = ConstantsClient.CHAT_TIMER;
    private final String message;

    public ChatMessage(String message) {
        this.message = message;
    }

    /**
     * decrease time when message will be displayed
     */
    public void decreaseTimer() {
        timer--;
    }

    public int getTimer() {
        return timer;
    }

    public String getMessage() {
        return message;
    }
}

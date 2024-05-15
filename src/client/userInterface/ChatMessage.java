package client.userInterface;

import client.clientMain.ConstantsClient;

public class ChatMessage {
    private int timer = ConstantsClient.CHAT_TIMER;
    private final String message;

    public ChatMessage(String message) {
        this.message = message;
    }

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

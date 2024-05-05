package client.clientMain;


import client.entity.MainPlayer;
import client.enums.GameState;
import client.userInterface.menu.Menu;
import shared.objects.Object;

import java.util.ArrayList;

public class UpdateManager implements Runnable {
    public static int tick = 0;
    private Thread thread;
    private final ServerCommunication serverCommunication;
    private final Menu menu;
    private final MainPlayer mainPlayer;
    private final ArrayList<Object> objectList;
    private final MouseHandler mouseHandler;

    public UpdateManager(ServerCommunication serverCommunication, Menu menu, MainPlayer mainPlayer, ArrayList<Object> objectList,
                         MouseHandler mouseHandler) {
        this.menu = menu;
        this.serverCommunication = serverCommunication;
        this.mainPlayer = mainPlayer;
        this.objectList = objectList;
        this.mouseHandler = mouseHandler;
    }

    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        double interval = 8333333; //16666666 60fps
        double delta = 0;
        long last = System.nanoTime();
        long time;
        long timer = 0;
        while (thread != null) {

            time = System.nanoTime();
            delta += (time - last) / interval;
            timer += (time - last);
            last = time;
            if (delta >= 1) {
                update();
                delta--;
                tick++;
            }
            if (timer >= 1000000000) {
                timer = 0;
            }
        }
    }

    private void update() {
        serverCommunication.update();
        mainPlayer.update();
        for (int i = 0; i < objectList.size(); i++) {
            objectList.get(i).updateClientSide(mouseHandler.getX(), mouseHandler.getY(), mouseHandler.rightClick, mainPlayer, objectList);
        }
        if (GameCore.gameState == GameState.PAUSED.intValue) {
            menu.update();
        }
    }
}

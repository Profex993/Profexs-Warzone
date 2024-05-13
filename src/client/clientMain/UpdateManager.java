package client.clientMain;


import client.clientMain.serverCommunication.ServerCommunication;
import client.entity.MainPlayer;
import client.enums.GameStateClient;
import client.userInterface.menu.Menu;
import shared.object.objectClasses.Object;

import java.util.ArrayList;

public class UpdateManager implements Runnable {
    private int tick = 0;
    private Thread thread;
    private final ServerCommunication serverCommunication;
    private final Menu menu;
    private final MainPlayer mainPlayer;
    private ArrayList<Object> objectList = new ArrayList<>();
    private final MouseHandler mouseHandler;
    private final GameCore core;

    public UpdateManager(ServerCommunication serverCommunication, Menu menu, MainPlayer mainPlayer, MouseHandler mouseHandler,
                         GameCore core) {
        this.menu = menu;
        this.serverCommunication = serverCommunication;
        this.mainPlayer = mainPlayer;
        this.mouseHandler = mouseHandler;
        this.core = core;
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
                if (core.getCurrentMatchTime() > 0) {
                    core.decreaseCurrentMatchTime();
                }
            }
        }
    }

    private void update() {
        serverCommunication.update();
        mainPlayer.update();
        for (Object object : objectList) {
            object.updateClientSide(mouseHandler.getX(), mouseHandler.getY(), mainPlayer);
        }
        if (core.getGameState() == GameStateClient.PAUSED) {
            menu.update();
        }
    }

    public void setObjectList(ArrayList<Object> objectList) {
        this.objectList = objectList;
    }

    public int getTick() {
        return tick;
    }
}

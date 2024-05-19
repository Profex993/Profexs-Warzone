package client.clientMain;


import client.clientMain.serverCommunication.ServerCommunication;
import client.entity.MainPlayer;
import client.enums.GameStateClient;
import client.userInterface.menu.Menu;
import shared.object.objectClasses.MapObject;

import java.util.ArrayList;

/**
 * class for updating client components
 */
public class UpdateManager implements Runnable {
    private int tick = 0;
    private Thread thread;
    private final ServerCommunication serverCommunication;
    private final Menu menu;
    private final MainPlayer mainPlayer;
    private ArrayList<MapObject> mapObjectList = new ArrayList<>();
    private final MouseHandler mouseHandler;
    private final GameCore core;

    /**
     * @param serverCommunication Server communication for updates from server
     * @param menu Menu for updating menu components
     * @param mainPlayer MainPlayer for updating MainPlayer
     * @param mouseHandler MouseHandler for mouse input
     * @param core GameCore for accessing client components
     */
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

    /**
     * update loop locked on 120 tick per second
     */
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

                // update once a second
                if (core.getCurrentMatchTime() > 0) {
                    core.decreaseCurrentMatchTime();
                }
            }
        }
    }

    /**
     * updates components each tick
     */
    private void update() {
        serverCommunication.update();
        mainPlayer.update();
        for (MapObject mapObject : mapObjectList) {
            mapObject.updateClientSide(mouseHandler.getX(), mouseHandler.getY(), mainPlayer);
        }
        if (core.getGameState() == GameStateClient.PAUSED) {
            menu.update();
        }
    }

    public void setObjectList(ArrayList<MapObject> mapObjectList) {
        this.mapObjectList = mapObjectList;
    }

    /**
     * @return returns current tick
     */
    public int getTick() {
        return tick;
    }
}

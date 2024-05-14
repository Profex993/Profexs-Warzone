package server;

import server.entity.PlayerServerSide;
import server.entity.ProjectileServerSide;
import server.enums.ServerMatchState;
import shared.ObjectGenerator;
import shared.object.objectClasses.Object;

import java.util.ArrayList;
import java.util.Random;

public class ServerUpdateManager implements Runnable {
    private Thread thread;
    private final ArrayList<PlayerServerSide> playerList;
    private final ArrayList<ProjectileServerSide> projectileList = new ArrayList<>();
    private final ArrayList<Object> objectList;
    private final ArrayList<ClientHandler> clientHandlers;
    private final ServerCore core;
    private final ArrayList<ItemSpawnLocation> itemSpawnLocations;
    private int tick = 0;
    private int gameTime = 0;
    private final Random random;

    public ServerUpdateManager(ArrayList<PlayerServerSide> playerList, ArrayList<ClientHandler> clientHandlers, ServerCore core,
                               ArrayList<ItemSpawnLocation> itemSpawnLocations, ArrayList<Object> objectList, Random random) {
        this.playerList = playerList;
        this.objectList = objectList;
        this.clientHandlers = clientHandlers;
        this.core = core;
        this.itemSpawnLocations = itemSpawnLocations;
        this.random = random;
    }

    public void startThread() {
        this.thread = new Thread(this);
        this.thread.start();
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

                updatePerSecond();

                //for testing
//                if (!playerList.isEmpty()) {
//                    System.out.println("x" + playerList.get(0).getWorldX() + " y" + playerList.get(0).getWorldY());
//                }
            }
        }
    }

    private void update() {
        for (int i = 0; i < projectileList.size(); i++) {
            projectileList.get(i).update(playerList, projectileList);
        }
        for (PlayerServerSide player : playerList) {
            player.update();
        }
    }

    private void updatePerSecond() {
        updateTime();

        for (int i = 0; i < objectList.size(); i++) {
            objectList.get(i).updatePerSecond(core);
        }

        for (ItemSpawnLocation location : itemSpawnLocations) {
            try {
                location.decreaseDelay();
                if (location.getSpawnDelay() == 0) {
                    Object object = ObjectGenerator.getObjectByName(
                            ConstantsServer.SPAWN_WEAPON_NAMES[random.nextInt(ConstantsServer.SPAWN_WEAPON_NAMES.length)],
                            location.getX(), location.getY());

                    location.setObjectAndResetDelay(object);
                    core.addObject(object);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateTime() {
        gameTime++;

        if (core.getMatchState() == ServerMatchState.MATCH && gameTime > core.getMATCH_TIME()) {
            core.setMatchState(ServerMatchState.MATCH_OVER);
            gameTime = 0;
            for (ClientHandler clientHandler : clientHandlers) {
                clientHandler.triggerEndOfMatch();
            }
        } else if (core.getMatchState() == ServerMatchState.MATCH_OVER && gameTime > ConstantsServer.MATCH_OVER_TIME) {
            core.setMatchState(ServerMatchState.MATCH);
            gameTime = 0;
            for (ClientHandler clientHandler : clientHandlers) {
                clientHandler.triggerStartOfMatch();
            }
            for (PlayerServerSide player : playerList) {
                player.resetPlayer();
            }
        }
    }

    public int getTick() {
        return tick;
    }

    public ArrayList<ProjectileServerSide> getProjectileList() {
        return projectileList;
    }
}

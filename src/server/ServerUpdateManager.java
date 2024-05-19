package server;

import server.entity.PlayerServerSide;
import server.entity.ProjectileServerSide;
import server.enums.ServerMatchState;
import shared.ObjectGenerator;
import shared.object.objectClasses.MapObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * class for managing updates of game components
 */
public class ServerUpdateManager implements Runnable {
    private Thread thread;
    private final ArrayList<PlayerServerSide> playerList;
    private final ArrayList<ProjectileServerSide> projectileList = new ArrayList<>();
    private final ArrayList<MapObject> mapObjectList;
    private final ArrayList<ClientHandler> clientHandlers;
    private final ServerCore core;
    private final ArrayList<ItemSpawnLocation> itemSpawnLocations;
    private int tick = 0;
    private int gameTime = 0;
    private final Random random;

    /**
     *
     * @param playerList Arraylist of PlayerServerSide for updating players
     * @param clientHandlers Arraylist of ClientHandler for updating clients about match state
     * @param core ServerCore for adding and removing MapObjects and updating match state
     * @param itemSpawnLocations Arraylist of ItemSpawnLocations for spawning items
     * @param mapObjectList Arraylist of MapObject to update map objects
     * @param random Random
     */
    public ServerUpdateManager(ArrayList<PlayerServerSide> playerList, ArrayList<ClientHandler> clientHandlers, ServerCore core,
                               ArrayList<ItemSpawnLocation> itemSpawnLocations, ArrayList<MapObject> mapObjectList, Random random) {
        this.playerList = playerList;
        this.mapObjectList = mapObjectList;
        this.clientHandlers = clientHandlers;
        this.core = core;
        this.itemSpawnLocations = itemSpawnLocations;
        this.random = random;
    }

    public void startThread() {
        this.thread = new Thread(this);
        this.thread.start();
    }

    /**
     * update loop with lock for 120 ticks per second
     */
    @Override
    public void run() {
        double interval = 8333333;
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
            }
        }
    }

    /**
     * update each tick
     * updates projectile list and players
     */
    private void update() {
        for (int i = 0; i < projectileList.size(); i++) {
            projectileList.get(i).update(playerList, projectileList);
        }
        for (PlayerServerSide player : playerList) {
            player.update();
        }
    }

    /**
     * updates match time map objects and item spawn locations once pre second
     */
    private void updatePerSecond() {
        updateTime();

        for (int i = 0; i < mapObjectList.size(); i++) {
            mapObjectList.get(i).updatePerSecond(core);
        }

        for (ItemSpawnLocation location : itemSpawnLocations) {
            try {
                location.decreaseDelay();
                if (location.getSpawnDelay() == 0) {
                    MapObject mapObject = ObjectGenerator.getObjectByName(
                            ConstantsServer.SPAWN_WEAPON_NAMES[random.nextInt(ConstantsServer.SPAWN_WEAPON_NAMES.length)],
                            location.getX(), location.getY());

                    location.setObjectAndResetDelay(mapObject);
                    core.addObject(mapObject);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * updates match time and server time
     */
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

    /**
     *
     * @return int value of current tick of the server
     */
    public int getTick() {
        return tick;
    }

    public ArrayList<ProjectileServerSide> getProjectileList() {
        return projectileList;
    }
}

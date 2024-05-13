package server;

import server.entity.PlayerServerSide;
import server.entity.ProjectileServerSide;
import server.enums.ServerMatchState;
import shared.ConstantsShared;

import java.util.ArrayList;

public class ServerUpdateManager implements Runnable {
    private Thread thread;
    private int tick = 0;
    private int gameTime = 0;
    private ServerMatchState matchState = ServerMatchState.MATCH;
    private final ArrayList<PlayerServerSide> playerList;
    private final ArrayList<ProjectileServerSide> projectileList = new ArrayList<>();
    private final ArrayList<ClientHandler> clientHandlers;
    private final ServerCore core;

    public ServerUpdateManager(ArrayList<PlayerServerSide> playerList, ArrayList<ClientHandler> clientHandlers, ServerCore core) {
        this.playerList = playerList;
        this.clientHandlers = clientHandlers;
        this.core = core;
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
                updateTime();

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

    private void updateTime() {
        gameTime++;

        if (matchState == ServerMatchState.MATCH && gameTime > core.getMATCH_TIME()) {
            matchState = ServerMatchState.MATCH_OVER;
            gameTime = 0;
            for (ClientHandler clientHandler : clientHandlers) {
                clientHandler.triggerEndOfMatch();
            }
        } else if (matchState == ServerMatchState.MATCH_OVER && gameTime > ConstantsShared.MATCH_OVER_TIME) {
            matchState = ServerMatchState.MATCH;
            gameTime = 0;
            for (ClientHandler clientHandler : clientHandlers) {
                clientHandler.triggerStartOfMatch();
            }
            for (PlayerServerSide player : playerList) {
                player.resetPlayer();
            }
        }
    }

    public ServerMatchState getMatchState() {
        return matchState;
    }

    public int getTick() {
        return tick;
    }

    public ArrayList<ProjectileServerSide> getProjectileList() {
        return projectileList;
    }
}

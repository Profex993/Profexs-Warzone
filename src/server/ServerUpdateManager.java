package server;

import server.entity.PlayerServerSide;
import server.entity.ProjectileServerSide;

import java.util.ArrayList;

public class ServerUpdateManager implements Runnable {
    private Thread thread;
    private int tick = 0;

    private final ArrayList<PlayerServerSide> playerList;
    private final ArrayList<ProjectileServerSide> projectileList = new ArrayList<>();

    public ServerUpdateManager(ArrayList<PlayerServerSide> playerList) {
        this.playerList = playerList;
    }

    public void startThread() {
        this.thread = new Thread(this);
        this.thread.start();
    }
    @Override
    public void run() {
        double interval = 16666666; //16666666 60fps
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
        for (int i = 0; i < projectileList.size(); i++) {
            projectileList.get(i).update(playerList, projectileList);
        }
    }

    public int getTick() {
        return tick;
    }

    public ArrayList<ProjectileServerSide> getProjectileList() {
        return projectileList;
    }
}

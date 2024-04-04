package client.clientMain;

public class UpdateManager implements Runnable {
    private Thread thread;
    private final ServerCommunication serverCommunication;

    public UpdateManager(ServerCommunication serverCommunication) {
        this.serverCommunication = serverCommunication;
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
            }
            if (timer >= 1000000000) {
                timer = 0;
            }
        }
    }

    private void update() {
        serverCommunication.update();
    }
}

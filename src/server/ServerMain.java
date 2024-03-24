package server;

import server.console.Console;

public class ServerMain {
    public static void main(String[] args) {
        ServerCore core = new ServerCore();
        Console console = new Console(core);
        core.runServer(console.selectPort());
        console.startConsole();
    }
}
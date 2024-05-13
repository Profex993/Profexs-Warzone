package server.console;

import server.ServerCore;
import server.console.commands.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

public class Console {
    private boolean exit = true;
    private final LinkedHashMap<String, Command> commandList;
    private final ServerCore core;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public Console(ServerCore core) {
        this.core = core;
        this.commandList = new LinkedHashMap<>();
        commandList.put("help", new Command_Help());
        commandList.put("exit", new Command_Exit());
        commandList.put("players", new Command_PlayerList());
        commandList.put("change match time", new Command_ChangeMatchTime());
    }

    public void startConsole() {
        new Thread(() -> {
            while (exit) {
                try {
                    System.out.println(commandList.get(reader.readLine()).execute(this));
                } catch (NullPointerException e) {
                    System.out.println("unknown command");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.exit(0);
        }).start();
    }

    public int selectPort() {
        System.out.println("enter port number:");
//        try {
//            return Integer.parseInt(reader.readLine());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return 8080;
    }

    public void triggerExit() {
        this.exit = false;
    }

    public ServerCore getCore() {
        return core;
    }

    public LinkedHashMap<String, Command> getCommandList() {
        return commandList;
    }
}

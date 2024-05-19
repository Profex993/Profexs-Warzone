package server.console;

import server.ServerCore;
import server.console.commands.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

/**
 * server console class
 * this class has its own console making it compatible with the main thread of ServerCore class
 */
public class Console {
    private boolean exit = true;
    private final LinkedHashMap<String, Command> commandList;
    private final ServerCore core;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * constructor initializes commandList and puts commands in it
     * @param core ServerCore for method access
     */
    public Console(ServerCore core) {
        this.core = core;
        this.commandList = new LinkedHashMap<>();
        commandList.put("help", new Command_Help());
        commandList.put("exit", new Command_Exit());
        commandList.put("players", new Command_PlayerList());
        commandList.put("change match time", new Command_ChangeMatchTime());
    }

    /**
     * start console thread and wait for input
     */
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

    /**
     * select port from player input
     * @return port int
     */
    public int selectPort() {
        System.out.println("enter port number:");
        try {
            return Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

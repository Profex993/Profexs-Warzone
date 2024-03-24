package server.console;

import server.ServerCore;
import server.console.commands.Command;
import server.console.commands.Command_Exit;
import server.console.commands.Command_Help;
import server.console.commands.Command_PlayerList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Console {
    private boolean exit = true;
    private final HashMap<String, Command> commandList;
    private final ServerCore core;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public Console(ServerCore core) {
        this.core = core;
        this.commandList = new HashMap<>();
        commandList.put("exit", new Command_Exit());
        commandList.put("players", new Command_PlayerList());
        commandList.put("help", new Command_Help());
    }

    public void startConsole() {
        new Thread(() -> {
            while (exit) {
                try {
                    System.out.println(commandList.get(reader.readLine()).execute(this));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            System.exit(0);
        }).start();
    }

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

    public HashMap<String, Command> getCommandList() {
        return commandList;
    }
}

package server.console.commands;

import server.console.Console;

public class Command_PlayerList implements Command {
    @Override
    public String execute(Console console) {
        return console.getCore().getPlayers().toString();
    }
}

package server.console.commands;

import server.PlayerServerSide;
import server.console.Console;

public class Command_PlayerList implements Command {
    @Override
    public String execute(Console console) {
        if (!console.getCore().getPlayerList().isEmpty()) {
            StringBuilder out = new StringBuilder("{");
            for (PlayerServerSide playerServerSide : console.getCore().getPlayerList()) {
                out.append(playerServerSide.toString()).append("\n");
            }
            out.append("}");
            return out.toString();
        } else {
            return "no players connected";
        }
    }

    @Override
    public String description() {
        return "return list of connected players";
    }
}

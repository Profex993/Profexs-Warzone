package server.console.commands;

import server.PlayerData;
import server.console.Console;

public class Command_PlayerList implements Command {
    @Override
    public String execute(Console console) {
        if (!console.getCore().getPlayerList().isEmpty()) {
            StringBuilder out = new StringBuilder("{");
            for (PlayerData playerData : console.getCore().getPlayerList()) {
                out.append(playerData.toString()).append("\n");
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

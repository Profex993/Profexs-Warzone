package server.console.commands;

import server.console.Console;

public class Command_Help implements Command {
    @Override
    public String execute(Console console) {
        StringBuilder out = new StringBuilder();
        for (String string : console.getCommandList().keySet()) {
            out.append("- ").append(string).append(" : "). append(console.getCommandList().get(string).description()).append("\n");
        }
        return out.toString();
    }

    @Override
    public String description() {
        return "show list of all commands";
    }
}

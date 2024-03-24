package server.console.commands;

import server.console.Console;

public class Command_Help implements Command {
    @Override
    public String execute(Console console) {
        return console.getCommandList().keySet().toString();
    }
}

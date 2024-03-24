package server.console.commands;

import server.console.Console;

public class Command_Exit implements Command {
    @Override
    public String execute(Console console) {
        console.triggerExit();
        return "shutting down...";
    }
}

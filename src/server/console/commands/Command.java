package server.console.commands;

import server.console.Console;

public interface Command {
    String execute(Console console);
    String description();
}

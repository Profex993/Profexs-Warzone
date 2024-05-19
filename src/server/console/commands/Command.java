package server.console.commands;

import server.console.Console;

/**
 * server console command interface
 */
public interface Command {
    /**
     * @param console ServerConsole
     * @return returns String as output to print into console
     */
    String execute(Console console);

    /**
     * @return String description of the command
     */
    String description();
}

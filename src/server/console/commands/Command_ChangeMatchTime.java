package server.console.commands;

import server.console.Console;

import java.util.Scanner;

public class Command_ChangeMatchTime implements Command {
    @Override
    public String execute(Console console) {
        Scanner scn = new Scanner(System.in);
        try {
            console.getCore().setMATCH_TIME(scn.nextInt());
            return "time changed";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String description() {
        return "change match time";
    }
}

package client.clientMain;

import javax.swing.*;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        GameCore core = new GameCore(scn.nextLine());

        JFrame window = new JFrame();
        window.setTitle("game");
//      window.setUndecorated(true);
        window.add(core.getGamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.pack();
    }
}
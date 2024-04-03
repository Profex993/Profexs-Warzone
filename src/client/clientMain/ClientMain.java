package client.clientMain;

import shared.enums.PlayerModel;

import javax.swing.*;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader in = null;
        BufferedWriter out = null;
        Scanner scn = new Scanner(System.in);
        try {
            System.out.println("enter ip and port");
            socket = new Socket(scn.nextLine(), scn.nextInt());
//            socket = new Socket("192.168.123.188", 8080);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (ConnectException | UnknownHostException e) {
            System.out.println("no server found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        String playerModel = PlayerModel.DEFAULT.name; //select player model
        GameCore core = new GameCore(setName(socket, in, out, scn), playerModel, socket, in, out);

        JFrame window = new JFrame();
        window.setTitle("game");
        window.setUndecorated(true);
        window.add(core.getGamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.pack();
    }

    private static String setName(Socket socket, BufferedReader in, BufferedWriter out, Scanner scn) {
        String message = "enter username";
        String username;
        try {
            do {
                System.out.println(message);
                username = scn.next();
                out.write(username);
                out.newLine();
                out.flush();
                message = in.readLine();
            } while (!message.equals("name available"));   // to do [a-zA-Z\d_]{1,15}
            return username;
        } catch (Exception e) {
            closeSocket(socket, in, out);
            throw new RuntimeException(e);
        }
    }

    public static void closeSocket(Socket socket, BufferedReader in, BufferedWriter out) {
        try {
            socket.close();
            out.close();
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
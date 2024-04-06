package client.clientMain;

import client.menu.MainMenu;

import javax.swing.*;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMain {
    static Socket socket = null;
    static BufferedReader in = null;
    static BufferedWriter out = null;

    public static void main(String[] args) {
        MainMenu menu = new MainMenu();
        menu.open();
    }

    public static void startGame(String ip, int port) throws Exception {
        try {
            socket = new Socket(ip, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (ConnectException | UnknownHostException e) {
            throw new Exception("no server found");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void lunch(String username, String playerModel) {
        GameCore core = new GameCore(username, playerModel, socket, in, out);

        JFrame window = new JFrame();
        window.setTitle("game");
        window.setUndecorated(true);
        window.add(core.getGamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.pack();
    }

    public static void setName(String username, String playerModel) throws Exception {
        checkName(socket, in, out, username);
        lunch(username, playerModel);
    }

    public static void checkName(Socket socket, BufferedReader in, BufferedWriter out, String username) throws Exception {
        String message;
        try {
            out.write(username);
            out.newLine();
            out.flush();
            message = in.readLine();
        } catch (Exception e) {
            closeSocket(socket, in, out);
            throw new RuntimeException(e);
        }
        if (!message.equals("name available")) {
            throw new Exception("name taken");
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
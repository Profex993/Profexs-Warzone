package client.clientMain;

import client.userInterface.menu.MainMenu;
import shared.ConstantsShared;

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
        new MainMenu().open();
    }

    /**
     * establish connection with the server
     *
     * @param ip   String of ip of server
     * @param port int of port of server
     * @throws Exception if no server found
     */
    public static void establishConnection(String ip, int port) throws Exception {
        try {
            socket = new Socket(ip, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (ConnectException | UnknownHostException e) {
            throw new Exception("no server found");
        } catch (IOException e) {
            showErrorWindowAndExit("start game exception", e);
        }
    }

    /**
     * lunch game window and create key components of client
     *
     * @param username    String of players name
     * @param playerModel String of players player model
     */
    public static void lunch(String username, String playerModel) {
        GameCore core = new GameCore(username, playerModel, in, out);

        JFrame window = new JFrame();
        window.setTitle("Profex's warzone");
        window.setUndecorated(true);
        window.add(core.getGamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.pack();
    }

    /**
     * set players name
     *
     * @param username String name
     * @throws Exception if name is taken or invalid
     */
    public static void setName(String username) throws Exception {
        String message;
        try {
            out.write(username);
            out.newLine();
            out.flush();
            message = in.readLine();
        } catch (Exception e) {
            showErrorWindowAndExit("name exception", e);
            throw new RuntimeException(e);
        }
        if (message.equals(ConstantsShared.NAME_TAKEN)) {
            throw new Exception("name taken");
        } else if (message.equals(ConstantsShared.INVALID_NAME)) {
            throw new Exception("invalid name");
        }
    }

    /**
     * shows exception message box, prints error details into console and exits
     *
     * @param message String error message
     * @param e       Exception that has occurred
     */
    public static void showErrorWindowAndExit(String message, Exception e) {
        try {
            socket.close();
            out.close();
            in.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        String errorMessage = message + ":\n" + e.getMessage() + "\n" + e;
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
        System.exit(1);
    }

    /**
     * close socket
     */
    public static void closeSocket() {
        try {
            socket.close();
            out.close();
            in.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
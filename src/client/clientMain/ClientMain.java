package client.clientMain;

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
//            socket = new Socket(scn.nextLine(), scn.nextInt());
            socket = new Socket("localhost", 8080);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (ConnectException | UnknownHostException e) {
            System.out.println("no server found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }


        GameCore core = new GameCore(setName(socket, in, out, scn), socket, in, out);

        JFrame window = new JFrame();
        window.setTitle("game");
//      window.setUndecorated(true);
        window.add(core.getGamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.pack();
    }

    private static String setName(Socket socket, BufferedReader in, BufferedWriter out, Scanner scn) {
        String message;
        try {
            System.out.println("enter username");
            String username = scn.next();
            out.write(username);
            out.newLine();
            out.flush();
            message = in.readLine();
            if (message.equals("nameAvailable")) {
                return username;
            } else {
                System.out.println("name taken");
                closeSocket(socket, in, out);
                System.exit(0);
            }
        } catch (Exception e) {
            closeSocket(socket, in, out);
            throw new RuntimeException(e);
        }
        return "";
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
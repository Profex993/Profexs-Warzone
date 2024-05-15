package client.clientMain.serverCommunication;

import client.clientMain.ClientMain;
import client.clientMain.GameCore;
import client.clientMain.KeyHandler;
import client.clientMain.MouseHandler;
import client.entity.MainPlayer;
import shared.ConstantsShared;
import shared.packets.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

public class ServerCommunication {
    private final BufferedWriter out;
    private final BufferedReader in;
    private final MainPlayer mainPlayer;
    private final MouseHandler mouseHandler;
    private final KeyHandler keyHandler;
    private final GameCore core;
    private final HashMap<String, Packet_Process> processMap = new HashMap<>();

    public ServerCommunication(MainPlayer mainPlayer, String playerModel, BufferedReader in, BufferedWriter out, KeyHandler keyHandler,
                               MouseHandler mouseHandler, GameCore core) {

        this.mainPlayer = mainPlayer;
        this.out = out;
        this.in = in;
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
        this.core = core;
        startCommunication(playerModel);

        processMap.put(Packet_AddPlayer.head, new Packet_Process_AddPlayer());
        processMap.put(Packet_RemovePlayer.head, new Packet_Process_RemovePlayer());
        processMap.put(Packet_PlayerUpdateInput.head, new Packet_Process_PlayerUpdateInput());
        processMap.put(Packet_RemoveObject.head, new Packet_Process_RemoveObject());
        processMap.put(Packet_ChangeMap.head, new Packet_Process_ChangeMap());
        processMap.put(Packet_AddObject.head, new packet_Process_AddObject());
        processMap.put(Packet_EndMatch.head, new Packet_Process_EndMatch());
        processMap.put(Packet_StartMatch.head, new Packet_Process_StartMatch());
    }

    private void startCommunication(String playerModel) {
        try {
            Packet_AddPlayerToServer data = new Packet_AddPlayerToServer(mainPlayer.getName(), playerModel);
            out.write(data.toString());
            out.newLine();
            out.flush();
        } catch (IOException e) {
            ClientMain.showErrorWindowAndExit("server connection lost", e);
        }
    }

    public void update() {
        try {
            out.write(Packet_PlayerInputToServer.getFromPlayerInput(keyHandler, mouseHandler, mainPlayer.getScreenX(),
                    mainPlayer.getScreenY()).toString());
            out.newLine();
            out.flush();

            String inputLine = in.readLine();
            updateMainPlayer(inputLine);

            int numberOfPackets = Integer.parseInt(in.readLine());
            for (int i = 0; i < numberOfPackets; i++) {
                inputLine = in.readLine();
                Packet packet = Packet.parseFromString(inputLine);
                processMap.get(packet.head).process(core, packet.body());
            }
        } catch (Exception e) {
            ClientMain.showErrorWindowAndExit("server connection lost", e);
        }
    }

    private void updateMainPlayer(String inputLine) throws IOException {
        Packet_ServerOutputToClient input = Packet_ServerOutputToClient.parseFromString(inputLine);
        mainPlayer.updateFromServerInput(input);
    }

    private record Packet(String head, String body) {
        public static Packet parseFromString(String inputLine) throws IOException {
            try {
                String[] parts = inputLine.split(ConstantsShared.PROTOCOL_LINE_SPLIT);
                return new Packet(parts[0], parts[1]);
            } catch (Exception e) {
                throw new IOException("corrupted input");
            }
        }
    }
}

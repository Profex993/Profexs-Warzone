package client.clientMain.serverCommunication;

import client.clientMain.GameCore;
import client.enums.GameStateClient;
import shared.packets.Packet_StartMatch;

public class Packet_Process_StartMatch implements Packet_Process {

    @Override
    public void process(GameCore core, String body) {
        Packet_StartMatch packet = Packet_StartMatch.getFromString(body);
        core.setMatchTime(packet.time());
        core.setGameState(GameStateClient.GAME);
    }
}

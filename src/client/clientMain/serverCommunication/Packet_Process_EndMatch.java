package client.clientMain.serverCommunication;

import client.clientMain.GameCore;
import client.enums.GameStateClient;
import shared.packets.Packet_EndMatch;

public class Packet_Process_EndMatch implements Packet_Process {
    @Override
    public void process(GameCore core, String body) {
        Packet_EndMatch packet = Packet_EndMatch.getFromString(body);
        core.setMatchTime(packet.time());
        core.setGameState(GameStateClient.MATCH_END);
    }
}

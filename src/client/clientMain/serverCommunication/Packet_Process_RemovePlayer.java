package client.clientMain.serverCommunication;

import client.clientMain.GameCore;
import shared.packets.Packet_RemovePlayer;

public class Packet_Process_RemovePlayer implements Packet_Process {
    @Override
    public void process(GameCore core, String body) {
        Packet_RemovePlayer player = Packet_RemovePlayer.parseString(body);
        core.getPlayerList().removeIf(n -> (player.name().equals(n.getName())));
    }
}

package client.clientMain.serverCommunication;

import client.clientMain.GameCore;
import shared.packets.Packet_ChangeMap;

import java.io.IOException;

public class Packet_Process_ChangeMap implements Packet_Process {
    @Override
    public void process(GameCore core, String body) throws IOException {
        Packet_ChangeMap packet = Packet_ChangeMap.parseString(body);
        core.setMap(packet.mapNumber());
    }
}

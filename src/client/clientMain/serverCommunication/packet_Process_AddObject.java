package client.clientMain.serverCommunication;

import client.clientMain.GameCore;
import shared.ObjectGenerator;
import shared.packets.Packet_AddObject;

public class packet_Process_AddObject implements Packet_Process {
    @Override
    public void process(GameCore core, String body) throws Exception {
        Packet_AddObject packet = Packet_AddObject.parseString(body);
        core.addToObjectList(ObjectGenerator.getObjectByName(packet.name(), packet.x(), packet.y()));
    }
}

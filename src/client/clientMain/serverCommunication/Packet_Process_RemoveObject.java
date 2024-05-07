package client.clientMain.serverCommunication;

import client.clientMain.GameCore;
import shared.packets.Packet_RemoveObject;

import java.io.IOException;

public class Packet_Process_RemoveObject implements Packet_Process {
    @Override
    public void process(GameCore core, String body) throws IOException {
        Packet_RemoveObject removeObject = Packet_RemoveObject.parseString(body);
        core.getObjectList().removeIf(object -> removeObject.objectHash() == object.hashCode());
    }
}

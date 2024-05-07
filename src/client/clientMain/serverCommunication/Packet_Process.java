package client.clientMain.serverCommunication;

import client.clientMain.GameCore;

import java.io.IOException;

public interface Packet_Process {
    void process(GameCore core, String body) throws IOException;
}

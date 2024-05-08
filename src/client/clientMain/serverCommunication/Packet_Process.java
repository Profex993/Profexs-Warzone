package client.clientMain.serverCommunication;

import client.clientMain.GameCore;

public interface Packet_Process {
    void process(GameCore core, String body) throws Exception;
}

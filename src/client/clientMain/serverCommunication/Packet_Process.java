package client.clientMain.serverCommunication;

import client.clientMain.GameCore;

/**
 * process packet from server
 */
public interface Packet_Process {
    /**
     * @param core for accessing client components
     * @param body String input data from server
     * @throws Exception when exception occurs
     */
    void process(GameCore core, String body) throws Exception;
}

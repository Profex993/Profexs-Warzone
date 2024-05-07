package client.clientMain.serverCommunication;

import client.clientMain.GameCore;
import client.entity.Player;
import shared.packets.Packet_PlayerUpdateInput;

import java.io.IOException;

public class Packet_Process_PlayerUpdateInput implements Packet_Process {
    @Override
    public void process(GameCore core, String body) throws IOException {
        Packet_PlayerUpdateInput input = Packet_PlayerUpdateInput.parseFromString(body);
        for (Player player : core.getPlayerList()) {
            if (player.getName().equals(input.id())) {
                player.updateFromInputData(input);
                break;
            }
        }
    }
}

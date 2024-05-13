package client.clientMain.serverCommunication;

import client.clientMain.GameCore;
import client.entity.Player;
import shared.packets.Packet_AddPlayer;

import java.io.IOException;
import java.util.ArrayList;

public class Packet_Process_AddPlayer implements Packet_Process {
    @Override
    public void process(GameCore core, String body) throws IOException {
        Packet_AddPlayer data = Packet_AddPlayer.parseString(body);
        if (!data.name().equals(core.getMainPlayer().getName()) && isNewPlayer(data, core.getPlayerList())) {
            Player newPlayer = new Player(core.getMainPlayer(), data.name(), data.playerModel(), core);
            core.getPlayerList().add(newPlayer);
        }
    }

    private boolean isNewPlayer(Packet_AddPlayer data, ArrayList<Player> playerList) {
        return playerList.stream().noneMatch(player -> player.getName().equals(data.name()));
    }
}

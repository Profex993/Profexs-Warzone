package shared.packets;

import server.entity.PlayerServerSide;
import shared.ConstantsShared;

public record Packet_RemovePlayer(String name) {
    public final static String head = "removePlayer";
    public static Packet_RemovePlayer parseString(String name) {
        return new Packet_RemovePlayer(name);
    }

    public static Packet_RemovePlayer getFromPlayer(PlayerServerSide player) {
        return new Packet_RemovePlayer(player.getId());
    }

    public String toString() {
        return head + ConstantsShared.protocolLineSplit + name;
    }
}

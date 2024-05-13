package shared.packets;

import shared.ConstantsShared;

public record Packet_StartMatch(int time) {
    public final static String head = "startMatch";

    public static Packet_StartMatch getFromString(String line) {
        return new Packet_StartMatch(Integer.parseInt(line));
    }

    public String toString() {
        return head + ConstantsShared.protocolLineSplit + time;
    }
}

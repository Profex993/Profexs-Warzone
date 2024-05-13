package shared.packets;

import shared.ConstantsShared;

public record Packet_EndMatch(int time) {
    public final static String head = "endMatch";

    public static Packet_EndMatch getFromString(String line) {
        return new Packet_EndMatch(Integer.parseInt(line));
    }
    public String toString() {
        return head + ConstantsShared.protocolLineSplit + time;
    }
}

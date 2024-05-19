package shared.packets;

import shared.ConstantsShared;

/**
 * packet to inform client that match has ended
 * @param time int representing time until next match
 */
public record Packet_EndMatch(int time) {
    public final static String head = "endMatch";

    public static Packet_EndMatch getFromString(String line) {
        return new Packet_EndMatch(Integer.parseInt(line));
    }
    public String toString() {
        return head + ConstantsShared.PROTOCOL_LINE_SPLIT + time;
    }
}

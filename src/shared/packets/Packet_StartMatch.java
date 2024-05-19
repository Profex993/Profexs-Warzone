package shared.packets;

import shared.ConstantsShared;

/**
 * packet to start match at clients side
 * @param time int representing time until end of match
 */
public record Packet_StartMatch(int time) {
    public final static String head = "startMatch";

    public static Packet_StartMatch getFromString(String line) {
        return new Packet_StartMatch(Integer.parseInt(line));
    }

    public String toString() {
        return head + ConstantsShared.PROTOCOL_LINE_SPLIT + time;
    }
}

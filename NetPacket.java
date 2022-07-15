import java.io.Serializable;

/**
 * Class defines structure of client-server network packets
 * These packets are transmitted over sockets and so must be serializable
 * i.e. can be converted to and from a byte stream for transmission
 */

public class NetPacket<PayloadType> implements Serializable {
    public String sender;
    public String recipient;
    public PayloadType payload;

    public NetPacket(String sender, String recipient, PayloadType payload) {
        this.sender = sender;
        this.recipient = recipient;
        this.payload = payload;
    }
}

import java.io.Serializable;

public class NetPacket implements Serializable {
    String message;

    public NetPacket(String message) {
        this.message = message;
    }
}

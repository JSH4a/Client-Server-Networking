import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatClient extends NetClient {

    /**
     * Constructor sets address and port of server and attempts to connect to the specified destination
     *
     * @param add  The address of the server to connect to
     * @param port The port number of the server to connect to
     */
    public ChatClient(String id, String add, int port) {
        super(id, add, port);
    }

    /**
     * Defines what a client should do with received data
     *
     * @param response The data received from the server
     */
    @Override
    protected Boolean onServerResponse(NetPacket<?> response) {
        if (response.recipient.equals(id)) {
            System.out.println("\n"+response.sender+":"+response.payload+"\n");
        }

        if (response.sender.equals("server")) {

        }
        return true;
    }

    @Override
    protected void runClient() {
        /**
        try {

            // temporary for taking user input
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String userInput;

            NetPacket request;

            do {

                System.out.print("To: ");
                String recipient = br.readLine();

                System.out.print("Message: ");
                userInput = br.readLine();


                request = new NetPacket(id, recipient, userInput);
                sendToServer(request);
            } while (!request.payload.equals("Bye"));
        }
        catch (IOException e) {
            System.out.println("Unable to send user input");
            e.printStackTrace();
        }
        **/

    }

    public void getServerClients() {
        NetPacket getClients = new NetPacket(this.id, "Server", "send connected clients");
        sendToServer(getClients);
    }

    public static void main(String[] args) {
        ChatClient c1 = new ChatClient("3","localhost", 14003);
    }
}

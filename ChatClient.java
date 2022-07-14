import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatClient extends NetClient<String> {

    /**
     * Constructor sets address and port of server and attempts to connect to the specified destination
     *
     * @param add  The address of the server to connect to
     * @param port The port number of the server to connect to
     */
    public ChatClient(String add, int port) {
        super(add, port);
    }

    /**
     * Defines what a client should do with received data
     *
     * @param response The data received from the server
     */
    @Override
    protected void onServerResponse(NetPacket response) {
        System.out.println("Echo: " + response.message);
    }

    @Override
    protected boolean runClient() {
        try {
            // temporary for taking user input
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String userInput;

            NetPacket request;

            do {
                userInput = br.readLine();
                request = new NetPacket(userInput);
                serverOutput.writeObject(request);
            } while (!request.message.equals("Bye"));
        }
        catch (IOException e) {
            System.out.println("Unable to send user input");
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        ChatClient c1 = new ChatClient("localhost", 14003);

    }
}

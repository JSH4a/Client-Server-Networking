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
     * @param data The data received from the server
     */
    @Override
    protected void receivedFromServer(String data) {

    }

    public static void main(String[] args) {
        ChatClient c1 = new ChatClient("localhost", 14003);
        ChatClient c2 = new ChatClient("localhost", 14003);
    }
}

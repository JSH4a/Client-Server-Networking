public class ChatServer extends NetServer{

    /**
     * Constructor sets server port number and begins listening for clients
     *
     * @param port The port the server will operate over
     */
    public ChatServer(int port) {
        super(port);
    }

    /**
     * Defines how the server should respond when a client connects
     *
     */
    @Override
    protected void onClientConnection() {
        System.out.println("Client "+clientSockets.get(clientSockets.size())+" has connected");
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer(14003);
    }
}



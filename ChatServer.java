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
     */
    @Override
    protected void onClientConnection() {
        System.out.println("Hello "+clientSockets.get(clientSockets.size()-1));
    }

    /**
     * Defines how the server should respond when a client sends a message to the server
     *
     * @param request
     */
    @Override
    protected void onClientRequest(NetPacket request) {
        System.out.println(clientSockets.get(clientSockets.size()-1)+request.message);
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer(14003);
    }
}



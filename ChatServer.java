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
        System.out.print(connectedClients.toString());
    }

    /**
     * Defines how the server should respond when a client sends a message to the server
     *
     * @param request the request data packet that was received from the client
     */
    @Override
    protected void onClientRequest(NetPacket request) {
        sendToClient(request);
        System.out.println(request.recipient + " to " + request.sender);
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer(14003);
    }
}



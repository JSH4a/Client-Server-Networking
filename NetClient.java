import java.io.*;
import java.net.Socket;

/**
 * Abstract class for a creating client to communicate over network for a specified data type
 *
 */
public abstract class NetClient {
    // Server socket information
    protected String serverAddress;
    protected int serverPort;
    protected Socket serverSocket;
    protected ClientListener clientListener;
    protected String id;


    /**
     * Constructor sets address and port of server and attempts to connect to the specified destination
     *
     * @param add The address of the server to connect to
     * @param port The port number of the server to connect to
     */
    public NetClient(String id, String add, int port) {
        serverAddress = add;
        serverPort = port;
        this.id = id;

        // Establish connection to server and listen for response
        connectToServer();
    }

    /**
     * Attempts to initialise the socket to be connected to with the server destination
     *
     */
    public void connectToServer() {

        try {
            // Start connection and create listener thread that will listen for server communication
            serverSocket = new Socket(serverAddress, serverPort);

            clientListener = new ClientListener();
            clientListener.start();

            runClient();
        }
        catch (IOException e) {
            System.out.println("Unable to establish connection to server with address "+serverAddress+" on port "+serverPort);
            e.printStackTrace();
        }
    }

    /**
     * Send a Network Packet to the server
     * @param packet the network packet to be sent to the server
     */
    public void sendToServer(NetPacket<?> packet) {
        try {
            ObjectOutputStream serverOutput = new ObjectOutputStream(serverSocket.getOutputStream());
            serverOutput.writeObject(packet);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Handshake protocol is called when client connects in order to demonstrate connectivity and to
     * get the user ID for the server
     */
    private void handshake() {
        NetPacket<String> handshakePacket = new NetPacket<>(id, "server", id);
        sendToServer(handshakePacket);
    }

    /**
     * Defines what a client should do with received data
     *
     * @param response The data received from the server
     */
    protected abstract Boolean onServerResponse(NetPacket<?> response);

    /**
     * Defines the main function for the client.
     * Will terminate the client when returns false.
     */
    protected abstract void runClient();


    /**
     * Inner class allows client to listen for server response in separate thread so the client can also transmit
     * to the server in the main thread
     */
    private class ClientListener extends Thread {
        public void run() {
            try {
                NetPacket<?> response;

                do {
                    ObjectInputStream serverInput = new ObjectInputStream(serverSocket.getInputStream());
                    response = (NetPacket<?>) serverInput.readObject();

                    if (response.payload.equals("Send ID")) {
                        handshake();
                    }

                } while (onServerResponse(response));

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

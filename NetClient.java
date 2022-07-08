import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Abstract class for a creating client to communicate over network for a specified data type
 *
 * @param <DataType> The type of data to be sent by the client
 */
public abstract class NetClient<DataType> {
    // Server socket information
    protected String serverAddress;
    protected int serverPort;
    protected Socket serverSocket;
    protected ClientListener clientListener;

    // The location data will be sent to within the server from the client
    protected PrintWriter toServerStream;

    /**
     * Constructor sets address and port of server and attempts to connect to the specified destination
     *
     * @param add The address of the server to connect to
     * @param port The port number of the server to connect to
     */
    public NetClient(String add, int port) {
        serverAddress = add;
        serverPort = port;

        // Establish connection to server and listen for response
        connectToServer();

    }

    /**
     * Attempts to initialise the socket to be connected to with the server destination
     *
     */
    public void connectToServer() {

        try {
            serverSocket = new Socket(serverAddress, serverPort);
            toServerStream = new PrintWriter(serverSocket.getOutputStream());

            clientListener = new ClientListener();
            clientListener.start();
        }
        catch (IOException e) {
            System.out.println("Unable to establish connection to server with address "+serverAddress+" on port "+serverPort);
            e.printStackTrace();
        }
    }

    /**
     * Should send data of specified type to the output stream of the server socket
     *
     * @param data The data to be sent to the server
     */
    private void sendToServer(DataType data) {
        toServerStream.println(data);
    }

    /**
     * Defines what a client should do with received data
     *
     * @param data The data received from the server
     */
    protected abstract void receivedFromServer(DataType data);

    /**
     * Inner class allows client to listen for server response in separate thread so the client can also transmit
     * to the server in the main thread
     */
    private class ClientListener extends Thread {

    }
}

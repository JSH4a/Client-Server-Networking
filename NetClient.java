import java.io.*;
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
    protected ObjectOutputStream serverOutput;
    protected ObjectInputStream serverInput;


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

            serverOutput = new ObjectOutputStream(serverSocket.getOutputStream());
            serverInput = new ObjectInputStream(serverSocket.getInputStream());

            clientListener = new ClientListener();
            clientListener.start();

            while (runClient());
        }
        catch (IOException e) {
            System.out.println("Unable to establish connection to server with address "+serverAddress+" on port "+serverPort);
            e.printStackTrace();
        }
    }

    /**
     * Defines what a client should do with received data
     *
     * @param response The data received from the server
     */
    protected abstract void onServerResponse(NetPacket response);

    /**
     * Defines the main function for the client.
     * Will terminate the client when returns false.
     * @return
     */
    protected abstract boolean runClient();


    /**
     * Inner class allows client to listen for server response in separate thread so the client can also transmit
     * to the server in the main thread
     */
    private class ClientListener extends Thread {
        public void run() {
            try {
                NetPacket response;

                do {
                    response = (NetPacket) serverInput.readObject();
                    onServerResponse(response);
                } while (!response.message.equals("Bye"));

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Abstract class for creating a server that can listen for multiple clients connecting to it
 * and handling data from clients
 * @param <DataType> The type of data being transmitted and received
 */
public abstract class NetServer<DataType> {
    protected Integer serverPort;
    protected ArrayList<Socket> clientSockets = new ArrayList<>();
    protected ServerSocket serverSocket;

    /**
     * Constructor sets server port number and begins listening for clients
     * @param port The port the server will operate over
     */
    public NetServer(int port) {
        serverPort = port;

        listenForConnections();
    }

    /**
     * Accepts incoming clients trying to connect to server
     */
    private void listenForConnections() {
        try {
            // attempt to establish server on port
            serverSocket = new ServerSocket(serverPort);

            // continuously listen for incoming clients until server closed
            while (!serverSocket.isClosed()) {
                System.out.println("Server listening...");
                // Accept a connection from a client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Server accepted connection on: " + serverSocket.getLocalPort() + " ; " + clientSocket.getPort());
                // Add socket to list of connected sockets
                clientSockets.add(clientSocket);

            }

        } catch (IOException e) {
            System.out.println("Unable to establish a server on port "+serverPort);
            try {
                serverSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * Defines how the server should respond when a client connects
     */
    protected abstract void onClientConnection();

    /**
     * Inner class allows server to listen for client transmission in separate thread so the server can also transmit
     * to the server in the main thread
     */
    private class ClientHandler {

    }
}

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class for creating a server that can listen for multiple clients connecting to it
 * and handling data from clients
 */
public abstract class NetServer {
    protected Integer serverPort;
    protected HashMap<String, Socket> connectedClients = new HashMap<>();
    protected ServerSocket serverSocket;

    protected Boolean debugging = true;

    /**
     * Defines how the server should respond when a client connects
     */
    protected abstract void onClientConnection();

    /**
     * Defines how the server should respond when a client sends a message to the server
     */
    protected abstract void onClientRequest(NetPacket<?> request);


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
                if (debugging) {
                    System.out.println("Server listening...");
                }
                // Accept a connection from a client
                Socket clientSocket = serverSocket.accept();

                if (debugging) {
                    System.out.println("Server accepted connection on: " + serverSocket.getLocalPort() + " ; " + clientSocket.getPort());
                }
                // Start a new thread to handle listening to the new client
                // this will start by performing a handshake to confirm the connection
                new ClientHandler(clientSocket).start();

                // Server may need to do some processing when a client connects e.g. login
                onClientConnection();

            }

        } catch (IOException e) {
            System.out.println("Unable to establish a server on port "+serverPort);
            closeServer();
        }
    }

    /**
     * Method to close the server socket and all connected client sockets
     * should always be called when the server is no longer needed
     */
    public void closeServer() {
        try {
            // close all connected clients
            for (Map.Entry<String, Socket> client : connectedClients.entrySet()) {
                client.getValue().close();
                connectedClients.remove(client.getKey());
            }
            // close server socket
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method sends a network packet to the specified client
     * @param packet the packet to be sent
     */
    public void sendToClient(NetPacket<?> packet) {
        // init input and output streams
        // clientOut is used to write out of the server to the client
        try {
            Socket client = getClientSocket(packet.recipient);

            ObjectOutputStream clientOut = new ObjectOutputStream(client.getOutputStream());
            clientOut.writeObject(packet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method gets the socket associated with the given ID
     * @param id the id of the client we wish to get the socket of
     * @return  the corresponding socket of the given client
     */
    public Socket getClientSocket(String id) {
        return connectedClients.get(id);
    }

    /**
     * Inner class allows server to listen for client transmission in separate thread so the server can also transmit
     * to the server in the main thread
     */
    private class ClientHandler extends Thread {
        private final Socket clientSocket;
        private String clientID = null;

        public ClientHandler(Socket client) {
            clientSocket = client;
        }

        private void handshake() {
            try {
                NetPacket<String> handshakePacket = new NetPacket<>("Server", String.valueOf(clientSocket.getPort()), "Send ID");

                ObjectOutputStream clientOut = new ObjectOutputStream(clientSocket.getOutputStream());
                clientOut.writeObject(handshakePacket);

                ObjectInputStream clientInput = new ObjectInputStream(clientSocket.getInputStream());
                NetPacket<?> response = (NetPacket<?>) clientInput.readObject();

                clientID = (String) response.payload;

                connectedClients.put(clientID, clientSocket);

                System.out.println("Client connected: " + connectedClients);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                // init input and output streams
                // clientOut is used to write out of the server to the client
                // clientIn is used to read in to the server from the client
                //clientOut = new ObjectOutputStream(clientSocket.getOutputStream());

                handshake();

                NetPacket<?> request;

                do {
                    ObjectInputStream clientInput = new ObjectInputStream(clientSocket.getInputStream());
                    request = (NetPacket<?>) clientInput.readObject();

                    onClientRequest(request);

                } while (!request.payload.equals("Bye"));

                connectedClients.remove(clientID);
                clientSocket.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.howtodoinjava.demo.chatbot;

import javax.swing.*;

public class ChatBotClient extends NetClient {
    private ChatBotGUI gui;

    /**
     * Constructor sets address and port of server and attempts to connect to the specified destination
     *
     * @param id
     * @param add  The address of the server to connect to
     * @param port The port number of the server to connect to
     */
    public ChatBotClient(String id, String add, int port) {
        super(id, add, port);
    }

    /**
     * Defines what a client should do with received data
     *
     * @param response The data received from the server
     */
    @Override
    protected Boolean onServerResponse(NetPacket<?> response) {
        if (!(gui == null) && response.sender.equals("Bot")) {
            gui.setBotOutput((String) response.payload);
        }
        return true;
    }

    /**
     * Defines the main function for the client.
     * Will terminate the client when returns false.
     */
    @Override
    protected void runClient() {

    }

    public void setGUI( ChatBotGUI guiInstance) {
        gui = guiInstance;
    }

    public static void main(String args[]) {
        ChatBotClient client1 = new ChatBotClient("1", "localhost", 14003);
    }
}

package com.howtodoinjava.demo.chatbot;

import java.io.File;
import java.util.HashMap;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

public class ChatBotServer extends NetServer{
    private static HashMap<String, ChatHandler> chatHandlers = new HashMap<String, ChatHandler>();

    /**
     * Constructor sets server port number and begins listening for clients
     *
     * @param port The port the server will operate over
     */
    public ChatBotServer(int port) {
        super(port);
    }

    /**
     * Defines how the server should respond when a client connects
     */
    @Override
    protected void onClientConnection(String clientID) {
        // create new chat handler and save
        ChatHandler chat = new ChatHandler();
        chatHandlers.put(clientID, chat);
    }

    /**
     * Defines how the server should respond when a client sends a message to the server
     *
     * @param request
     */
    @Override
    protected Boolean onClientRequest(NetPacket<?> request) {
        if (request.recipient.equals("Bot")) {
            NetPacket ret = new NetPacket("Bot", request.sender, chatHandlers.get(request.sender).newMsg((String)request.payload));
            sendToClient(ret);
        }
        return true;
    }

    public class ChatHandler {
        private Chat chatSession;
        private Bot bot;

        public ChatHandler() {
                String resourcesPath = getResourcesPath();
                System.out.println(resourcesPath);
                MagicBooleans.trace_mode = true;
                bot = new Bot("super", resourcesPath);
                chatSession = new Chat(bot);
                bot.brain.nodeStats();
        }

        private String getResourcesPath() {
            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            path = path.substring(0, path.length() - 2);
            System.out.println(path);
            String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
            return resourcesPath;
        }

        public String newMsg(String textLine) {
            String response = "";
            if ((textLine == null) || (textLine.length() < 1))
                textLine = MagicStrings.null_input;
            if (textLine.equals("q")) {
                System.exit(0);
            } else if (textLine.equals("wq")) {
                bot.writeQuit();
                System.exit(0);
            } else {
                String request = textLine;
                if (MagicBooleans.trace_mode)
                    System.out.println(
                            "STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0)
                                    + ":TOPIC=" + chatSession.predicates.get("topic"));

                response = chatSession.multisentenceRespond(request);
                while (response.contains("&lt;"))
                    response = response.replace("&lt;", "<");
                while (response.contains("&gt;"))
                    response = response.replace("&gt;", ">");

                return response;
            }
            return response;
        }
    }

    public static void main(String args[]) {
        new ChatBotServer(14003);
    }
}

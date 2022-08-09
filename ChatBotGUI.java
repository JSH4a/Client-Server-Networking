package com.howtodoinjava.demo.chatbot;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatBotGUI {
    private ChatBotClient client;
    private JPanel main;
    private JPanel loginPanel;

    public ChatBotGUI() {
        ChatBotGUI tempThis = this;

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get entered username
                String username = usernameInput.getText();

                // start client with username
                client = new ChatBotClient(username, "localhost", 14003);
                client.setGUI(tempThis);
                client.connectToServer();

                // hide login window
                loginPanel.setVisible(false);

                // show chat window
                chatPanel.setVisible(true);
            }
        });
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // send packet with user input
                NetPacket<String> packet = new NetPacket<>(client.id, "Bot", userIn.getText());
                client.sendToServer(packet);

                // wipe message
                userIn.setText("");
            }
        });
    }

    public void setBotOutput(String output) {
        botOut.setText(output);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ChatBotGUI");
        frame.setContentPane(new ChatBotGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JFormattedTextField usernameInput;
    private JButton loginButton;
    private JTextField userIn;
    private JTextField botOut;
    private JButton sendButton;
    private JPanel chatPanel;
}

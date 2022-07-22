import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ClientGUI {
    private JTextField usernameField;
    private ChatClient client;
    private String partner;

    public ClientGUI() {
        loginButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // check if details correct
                if (usernameField.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Incorrect username or password..Try Again with correct detail");
                }
                else {
                    loggedIn();
                }
                client = new ChatClient(usernameField.getText(), "localhost", 14003);
            }
        });
        sendButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // display the message
                String text = client.id+": "+userInput.getText();
                JLabel newMsg = new JLabel(text);

                // display the message
                previousMsgs.add(newMsg, new com.intellij.uiDesigner.core.GridConstraints());
                newMsg.setVisible(true);
                previousMsgs.revalidate();

                // reset input to empty box
                userInput.setText("");

                //client.sendToServer(new NetPacket<>(client.id, recipient, text));
            }
        });

        selectView.addComponentListener(new ComponentAdapter() {
            /**
             * Invoked when the component has been made visible.
             *
             * @param e
             */
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);

                client.serverSocket.getOutputStream()

                for (String username : client.getServerClients()) {

                }
            }
        });
    }

    public void postMessage(String sender, String message) {
    }

    private void loggedIn() {
        loginPanel.setVisible(false);
        mainView.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ClientGUI");

        frame.setContentPane(new ClientGUI().demoChat);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
    }

    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel demoChat;
    private JPanel header;
    private JPanel loginPanel;
    private JTextArea userInput;
    private JPanel mainView;
    private JPanel previousMsgs;
    private JButton sendButton;
    private JPanel selectView;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

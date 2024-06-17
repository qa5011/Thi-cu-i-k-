import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientApplication extends JPanel {

    private JTextField messageText;
    private JButton send;
    private JTextArea message;
    private JPanel socketPanel;

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dout;

    public ClientApplication() {
        initComponents();
        connectToServer();

    }

    private void initComponents() {
        messageText = new JTextField(20);
        send = new JButton("Send");
        message= new JTextArea(10, 20);
        message.setEditable(false);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter Message: "));
        inputPanel.add(messageText);
        inputPanel.add(send);

        socketPanel = new JPanel();
        socketPanel.add(inputPanel);
        socketPanel.add(new JScrollPane(message));

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        add(socketPanel);
    }

    private void connectToServer() {
        try {
            // Connect to the server running on localhost at port 1201
            socket = new Socket("localhost", 1201);
            System.out.println("Connected to server");

            // Initialize input and output streams
            dis = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());

            // Start listening for messages from the server
            listenForMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listenForMessages() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String msgin = "";
                    while (!msgin.equals("exit")) {
                        msgin = dis.readUTF();
                        message.append("\nServer: " + msgin);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void sendMessage() {
        try {
            String msg = messageText.getText();
            dout.writeUTF(msg);
            messageText.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Client");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 500);
                frame.setLocationRelativeTo(null);
                frame.setContentPane(new ClientApplication());
                frame.setVisible(true);
            }
        });
    }
}

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApplication extends JPanel {

    private JTextField messageText;
    private JButton send;
    private JTextArea messageArea;
    private JPanel socketPanel;

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static DataInputStream dis;
    private static DataOutputStream dout;

    public ServerApplication() {
        initComponents();
        startServer();

    }

    private void initComponents() {
        messageText = new JTextField(20);
        send = new JButton("Send");
        messageArea = new JTextArea(10, 20);
        messageArea.setEditable(false);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter Message: "));
        inputPanel.add(messageText);
        inputPanel.add(send);

        socketPanel = new JPanel();
        socketPanel.add(inputPanel);
        socketPanel.add(new JScrollPane(messageArea));

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        add(socketPanel);
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(1201);
            System.out.println("Server started, waiting for clients...");

            // Wait for client connection
            clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket);

            // Initialize input and output streams
            dis = new DataInputStream(clientSocket.getInputStream());
            dout = new DataOutputStream(clientSocket.getOutputStream());

            // Start listening for messages from client
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
                        messageArea.append("\nClient: " + msgin);
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
                JFrame frame = new JFrame("Server");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 500);
                frame.setLocationRelativeTo(null);
                frame.setContentPane(new ServerApplication());
                frame.setVisible(true);
            }
        });
    }
}

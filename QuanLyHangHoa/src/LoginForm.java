import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JFrame {
    private JTextField emailhihi;
    private JPasswordField passwordhihi;
    private JButton loginButton;
    private JButton cancelButton;
    private JPanel Main;
    private JPanel loginForm;

    public LoginForm() {
        // Setting up the UI components
        setContentPane(Main);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        //

        setTitle("Login");
        setMinimumSize(new Dimension(450,474));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);




        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailhihi.getText();
                String password = String.valueOf(passwordhihi.getPassword());

                User user = getAuthenticatedUser(email, password);

                if (user != null) {
                    JOptionPane.showMessageDialog(LoginForm.this, "Login successful!");
                    dispose();
                    MainFrame main = new MainFrame();
                    main.setTitle("Quản Lý Hàng Hóa Xuất Nhập Kho");

                    main.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Email or Password Invalid",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public User user;

    private User getAuthenticatedUser(String email, String password) {
        User user = null;

        final String DB_URL = "jdbc:mysql://127.0.0.1:3306/new_schema";
        final String DB_USER = "root";
        final String DB_PASSWORD = "newjeans";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "SELECT * FROM mystore WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
                user.phone = resultSet.getString("phone");
                user.address = resultSet.getString("address");
                user.password = resultSet.getString("password");
            }
            resultSet.close();
            preparedStatement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return user;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginForm();

            }
        });
    }

    // Define the User class

}

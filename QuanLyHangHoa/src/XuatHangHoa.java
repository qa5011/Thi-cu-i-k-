import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class XuatHangHoa extends JFrame {
    private JTextField ngayXuat;
    private JTextField soLuong;
    private JTextField id;
    private JPanel toxicLover;
    private JButton xuấtHàngHóaButton;
    private JButton cancelButton;

    Connection conn;
    PreparedStatement pst;

    public void connect3() {
        try {
            final String DB_URL = "jdbc:mysql://127.0.0.1:3306/new_schema";
            final String USER = "root";
            final String PASSWORD = "newjeans";
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Database connection success");
        } catch (Exception e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }

    public XuatHangHoa() {
        // Call superclass constructor
        super("Xuat Hang Hoa");
        setContentPane(toxicLover);
        setMinimumSize(new Dimension(500, 500));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        xuấtHàngHóaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect3();

                String NGAYXUAT = ngayXuat.getText();
                String SOLUONG = soLuong.getText();
                String ID = id.getText();

                try {
                    pst = conn.prepareStatement("UPDATE qlhh SET SoLuong = ?, NgayXuat = ? WHERE id = ?");
                    pst.setString(1, SOLUONG);
                    pst.setString(2, NGAYXUAT);
                    pst.setString(3, ID);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updated!");
                    // Clear input fields after update
                    soLuong.setText("");
                    ngayXuat.setText("");
                    id.setText("");
                    dispose();
                    MainFrame main = new MainFrame();
                    main.setTitle("Quản Lý Hàng Hóa Xuất Nhập Kho");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((JFrame) SwingUtilities.getWindowAncestor(cancelButton)).dispose();
                dispose();
                MainFrame main = new MainFrame();
                main.setTitle("Quản Lý Hàng Hóa Xuất Nhập Kho");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new XuatHangHoa();
            }
        });
    }
}

























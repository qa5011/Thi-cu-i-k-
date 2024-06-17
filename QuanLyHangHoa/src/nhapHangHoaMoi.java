import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class nhapHangHoaMoi extends JFrame {
    private JTextField id;
    private JTextField ten;
    private JTextField gia;
    private JTextField soLuong;
    private JTextField ngayNhap;
    private JButton OKButton;
    private JButton cancelButton;
    private JPanel Lesesrafim;

    Connection conn;
    PreparedStatement pst;

    public void connect2() {
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

    public nhapHangHoaMoi() {
        setContentPane(Lesesrafim);
        setTitle("Nhap Hang Hoa Moi");
        setMinimumSize(new Dimension(500, 500));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect2();

                String Ten = ten.getText();
                String Gia = gia.getText();
                String SOLUONG = soLuong.getText();
                String NGAYNHAP = ngayNhap.getText();

                try {
                    pst = conn.prepareStatement("INSERT INTO qlhh(Ten, Gia, SoLuong, NgayNhap) VALUES (?, ?, ?, ?)");
                    pst.setString(1, Ten);
                    pst.setString(2, Gia);
                    pst.setString(3, SOLUONG);
                    pst.setString(4, NGAYNHAP);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Added!");

                    ten.setText("");
                    gia.setText("");
                    soLuong.setText("");
                    ngayNhap.setText("");
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
                new nhapHangHoaMoi();
            }
        });
    }
}


































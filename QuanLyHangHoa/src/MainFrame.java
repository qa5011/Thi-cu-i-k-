import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainFrame extends JFrame {
    private JTable table1;
    private JPanel mainFrame;
    private JButton xóaHàngHóaButton;
    private JButton nhapHangHoaMoiButton;
    private JButton xuấtHàngHóaButton;
    private JButton tìmKiếmButton; // Corrected component name
    private JTextField id;
    private JTextField ten;
    private JTextField price;
    private JTextField soluong;
    private JButton chatButton;

    Connection conn;
    PreparedStatement pst;

    public void table_load() {
        try {
            pst = conn.prepareStatement("SELECT * FROM qlhh");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MainFrame() {
        connect();
        table_load();

        setContentPane(mainFrame);
        setTitle("Xuat Hang Hoa Xuat Nhap Kho");
        setMinimumSize(new Dimension(1000, 500));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        xóaHàngHóaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empid = id.getText();

                try {
                    String sql = "DELETE FROM qlhh WHERE id = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, empid);

                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Record Deleted!");
                        table_load(); // Reload the table data
                        id.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Record not found.");
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        tìmKiếmButton.addActionListener(new ActionListener() { // Corrected component name
            @Override
            public void actionPerformed(ActionEvent e) {
                connect();
                try {
                    String empid = id.getText();

                    pst = conn.prepareStatement("SELECT Ten, Gia, SoLuong FROM qlhh WHERE id = ?");
                    pst.setString(1, empid);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        String empname = rs.getString(1);
                        String emsalary = rs.getString(2);
                        String emmobile = rs.getString(3);

                        ten.setText(empname);
                        price.setText(emsalary);
                        soluong.setText(emmobile);
                    } else {
                        ten.setText("");
                        price.setText("");
                        soluong.setText("");
                        JOptionPane.showMessageDialog(null, "Invalid Employee No");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        nhapHangHoaMoiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                nhapHangHoaMoi nhap = new nhapHangHoaMoi();
                nhap.setTitle("Nhap Hang hoa");
            }
        });

        xuấtHàngHóaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                XuatHangHoa xuat = new XuatHangHoa();
                xuat.setTitle("Xuat Hang Hoa");
            }
        });
        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();


            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }

    public void connect() {
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
}

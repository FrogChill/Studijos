import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Panaikinit_Admin {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Panaikinit_Admin().createAndShowGUI());
    }

    private void createAndShowGUI() {
        // Main frame
        JFrame frame = new JFrame("Remove Admin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout(10, 10));

        // Components
        JLabel lblTitle = new JLabel("Select a Admin to Remove:", JLabel.CENTER);
        JList<String> AdminListUI = new JList<>();  // This is the JList that will display teacher names
        JButton btnRemove = new JButton("Remove Selected");
        JLabel lblStatus = new JLabel("", JLabel.CENTER);

        // Panel for buttons
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        bottomPanel.add(btnRemove);
        bottomPanel.add(lblStatus);

        // Add components to frame
        frame.add(lblTitle, BorderLayout.NORTH);
        frame.add(new JScrollPane(AdminListUI), BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Fetch all teachers from database and populate the list model
        ArrayList<Admin> AdminList = fetchAllAdmins();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Admin boss : AdminList) {
            listModel.addElement(boss.getAdminID() + " - " + boss.getAdminName() + " " + boss.getAdminSurname());
        }
        AdminListUI.setModel(listModel); // Set the list model to JList

        // Action listener for remove button
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = AdminListUI.getSelectedIndex();
                if (selectedIndex == -1) {
                    lblStatus.setText("Please select a Admin to Remove.");
                    lblStatus.setForeground(Color.RED);
                } else {
                    Admin selectedAdmin = AdminList.get(selectedIndex);
                    boolean success = removeAdminFromDataBase(selectedAdmin.getAdminID());
                    if (success) {
                        lblStatus.setText("Teacher removed successfully!");
                        lblStatus.setForeground(Color.GREEN);
                        listModel.remove(selectedIndex); // Remove from JList model
                    } else {
                        lblStatus.setText("Failed to remove teacher.");
                        lblStatus.setForeground(Color.RED);
                    }
                }
            }
        });
        frame.setVisible(true);
    }

    private ArrayList<Admin> fetchAllAdmins() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Admin> AdminList = new ArrayList<>();
        try {
            conn = Prisijungimas_DB.prisijungimas_DB();
            if (conn != null) {
                String sql = "SELECT * FROM admin"; // Query to select all teachers
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    int IDA = rs.getInt("idAdmin"); // Use correct column names
                    String Aname = rs.getString("Admin_name"); // Use correct column names
                    String Asurname = rs.getString("Admin_surrname"); // Use correct column names
                    AdminList.add(new Admin(IDA, Aname, Asurname));
                }
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }

        return AdminList;
    }
    private boolean removeAdminFromDataBase(int AdminID) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Prisijungimas_DB.prisijungimas_DB(); // Use your existing database connection method
            if (conn != null) {
                String sql = "DELETE FROM admin WHERE idAdmin = ?"; // Ensure column name matches the DB
                pst = conn.prepareStatement(sql);
                pst.setInt(1, AdminID);

                int rowsDeleted = pst.executeUpdate();
                return rowsDeleted > 0;
            } else {
                System.out.println("Failed to connect to the database.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return false;
        } finally {
            try {
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}



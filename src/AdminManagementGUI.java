import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

// Main GUI Class
public class AdminManagementGUI {
    private JFrame frame;
    private JList<String> adminListUI;
    private DefaultListModel<String> listModel;
    private JLabel lblStatus;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminManagementGUI().createAndShowGUI());
    }

    // Method to create and show the GUI
    private void createAndShowGUI() {
        frame = new JFrame("Admin Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout(10, 10));

        // Title Label
        JLabel lblTitle = new JLabel("Admin Management", JLabel.CENTER);
        frame.add(lblTitle, BorderLayout.NORTH);

        // List to display Admins
        adminListUI = new JList<>();
        listModel = new DefaultListModel<>();
        adminListUI.setModel(listModel);
        frame.add(new JScrollPane(adminListUI), BorderLayout.CENTER);

        // Status Label
        lblStatus = new JLabel("", JLabel.CENTER);
        frame.add(lblStatus, BorderLayout.SOUTH);

        // Panel for Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton btnAddAdmin = new JButton("Add Admin");
        JButton btnRemoveAdmin = new JButton("Remove Admin");

        buttonPanel.add(btnAddAdmin);
        buttonPanel.add(btnRemoveAdmin);
        frame.add(buttonPanel, BorderLayout.EAST);

        // Fetch admins and display in the list
        fetchAdmins();

        // Add Action Listeners
        btnAddAdmin.addActionListener(this::addAdminAction);
        btnRemoveAdmin.addActionListener(this::removeAdminAction);

        frame.setVisible(true);
    }

    // Fetch admins from database and populate JList
    private void fetchAdmins() {
        ArrayList<Admin> adminList = fetchAllAdmins();
        listModel.clear();
        for (Admin admin : adminList) {
            listModel.addElement(admin.getAdminID() + " - " + admin.getAdminName() + " " + admin.getAdminSurname());
        }
    }

    // Action to add new Admin
    private void addAdminAction(ActionEvent e) {
        String adminName = JOptionPane.showInputDialog(frame, "Enter Admin Name:");
        String adminSurname = JOptionPane.showInputDialog(frame, "Enter Admin Surrname:");

        if (adminName != null && adminSurname != null && !adminName.trim().isEmpty() && !adminSurname.trim().isEmpty()) {
            boolean success = addAdminToDatabase(adminName, adminSurname);
            if (success) {
                lblStatus.setText("Admin added successfully.");
                lblStatus.setForeground(Color.GREEN);
                fetchAdmins();  // Refresh the list
            } else {
                lblStatus.setText("Failed to add Admin.");
                lblStatus.setForeground(Color.RED);
            }
        } else {
            lblStatus.setText("Admin name and surname cannot be empty.");
            lblStatus.setForeground(Color.RED);
        }
    }

    // Action to remove selected Admin
    private void removeAdminAction(ActionEvent e) {
        int selectedIndex = adminListUI.getSelectedIndex();
        if (selectedIndex == -1) {
            lblStatus.setText("Please select an Admin to Remove.");
            lblStatus.setForeground(Color.RED);
        } else {
            String selectedText = adminListUI.getSelectedValue();
            int adminID = Integer.parseInt(selectedText.split(" - ")[0]);

            boolean success = removeAdminFromDatabase(adminID);
            if (success) {
                lblStatus.setText("Admin removed successfully.");
                lblStatus.setForeground(Color.GREEN);
                listModel.remove(selectedIndex);  // Remove from JList
            } else {
                lblStatus.setText("Failed to remove Admin.");
                lblStatus.setForeground(Color.RED);
            }
        }
    }

    // Fetch all admins from the database
    private ArrayList<Admin> fetchAllAdmins() {
        ArrayList<Admin> adminList = new ArrayList<>();
        try (Connection conn = Prisijungimas_DB.prisijungimas_DB()) {
            if (conn != null) {
                String sql = "SELECT * FROM admin";
                try (PreparedStatement pst = conn.prepareStatement(sql);
                     ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("idAdmin");
                        String name = rs.getString("Admin_name");
                        String surname = rs.getString("Admin_surrname");
                        adminList.add(new Admin(id, name, surname));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminList;
    }

    // Add new admin to the database
    private boolean addAdminToDatabase(String name, String surname) {
        try (Connection conn = Prisijungimas_DB.prisijungimas_DB()) {
            if (conn != null) {
                String sql = "INSERT INTO admin (Admin_name, Admin_surrname) VALUES (?, ?)";
                try (PreparedStatement pst = conn.prepareStatement(sql)) {
                    pst.setString(1, name);
                    pst.setString(2, surname);
                    int rowsAffected = pst.executeUpdate();
                    return rowsAffected > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Remove admin from the database
    private boolean removeAdminFromDatabase(int adminID) {
        try (Connection conn = Prisijungimas_DB.prisijungimas_DB()) {
            if (conn != null) {
                String sql = "DELETE FROM admin WHERE idAdmin = ?";
                try (PreparedStatement pst = conn.prepareStatement(sql)) {
                    pst.setInt(1, adminID);
                    int rowsAffected = pst.executeUpdate();
                    return rowsAffected > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

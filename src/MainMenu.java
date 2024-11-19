import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainMenu extends JFrame {

    private String role;
    private List<String> permissions;

    // Constructor with role and permissions
    public MainMenu(String role, List<String> permissions) {
        this.role = role;
        this.permissions = permissions;
        setUpMenu();
    }

    private void setUpMenu() {
        // Set up the window
        setTitle("Main Menu - " + role);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);  // Adjust size as needed
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout(10, 10));

        // Title Label
        JLabel titleLabel = new JLabel("Welcome to the Main Menu", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));

        // Buttons for different functionalities
        JButton btnMarks = new JButton("View Your Marks");
        JButton btnDienynas = new JButton("View Dienynas");
        JButton btnAdminManagement = new JButton("Open Admin Management");
        JButton btnTeacherManagement = new JButton("Open Teacher Management");
        JButton btnStudentManagement = new JButton("Open Student Management");
        JButton btnDalykaiManagement = new JButton("Open Dalykai Management");

        // Dynamically adjust visibility based on permissions
        if (permissions.contains("View Marks")) {
            buttonPanel.add(btnMarks);
        }
        if (permissions.contains("Assign Marks")) {
            buttonPanel.add(btnDienynas);
        }
        if (permissions.contains("Manage Admins")) {
            buttonPanel.add(btnAdminManagement);
        }
        if (permissions.contains("Manage Teachers")) {
            buttonPanel.add(btnTeacherManagement);
        }
        if (permissions.contains("Manage Students")) {
            buttonPanel.add(btnStudentManagement);
        }
        if (permissions.contains("Manage Subjects")) {
            buttonPanel.add(btnDalykaiManagement);
        }

        add(buttonPanel, BorderLayout.CENTER);

        // Action Listeners for each button
        btnDienynas.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Dienynas GUI opening...");
            new DienynoGUI();
        });

        btnAdminManagement.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Admin Management GUI opening...");
            new AdminManagementGUI();
        });

        btnTeacherManagement.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Teacher Management GUI opening...");
            new TeacherGUI();
        });

        btnStudentManagement.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Student Management GUI opening...");
            new StudentGUI();
        });

        btnDalykaiManagement.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Dalykai Management GUI opening...");
            new DalykoGUI();
        });

        // Make the window visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // Simulate a login with a role and permissions
        String role = "Teacher";  // Example role: "Student", "Teacher", "Admin"
        List<String> permissions = List.of("View Marks", "Manage Students", "Assign Marks"); // Example permissions

        // Open the Main Menu GUI based on role and permissions
        SwingUtilities.invokeLater(() -> new MainMenu(role, permissions));
    }
}

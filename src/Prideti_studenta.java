import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class Prideti_studenta {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Prideti_studenta().createAndShowGUI());
    }

    private void createAndShowGUI() {
        // Create the main frame
        JFrame frame = new JFrame("Add Student");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 2, 10, 10));

        // Add labels and text fields
        JLabel lblFirstName = new JLabel("First Name:");
        JTextField txtFirstName = new JTextField();
        JLabel lblLastName = new JLabel("Last Name:");
        JTextField txtLastName = new JTextField();

        // Add button to submit data
        JButton btnAddStudent = new JButton("Add Student");
        JLabel lblStatus = new JLabel("", JLabel.CENTER);

        // Add components to the frame
        frame.add(lblFirstName);
        frame.add(txtFirstName);
        frame.add(lblLastName);
        frame.add(txtLastName);
        frame.add(btnAddStudent);
        frame.add(lblStatus);

        // Action listener for the button
        btnAddStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = txtFirstName.getText().trim();
                String lastName = txtLastName.getText().trim();

                if (firstName.isEmpty() || lastName.isEmpty()) {
                    lblStatus.setText("All fields are required!");
                    lblStatus.setForeground(Color.RED);
                } else {
                    try {
                        boolean success = addStudentToDatabase(firstName, lastName);
                        if (success) {
                            lblStatus.setText("Student added successfully!");
                            lblStatus.setForeground(Color.GREEN);
                            txtFirstName.setText("");
                            txtLastName.setText("");
                        } else {
                            lblStatus.setText("Failed to add Student.");
                            lblStatus.setForeground(Color.RED);
                        }
                    } catch (NumberFormatException ex) {
                        lblStatus.setText("Student ID must be a number!");
                        lblStatus.setForeground(Color.RED);
                    }
                }
            }
        });

        // Show the GUI
        frame.setVisible(true);
    }

    public boolean addStudentToDatabase(String firstName, String lastName) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Prisijungimas_DB.prisijungimas_DB(); // Establish connection
            if (conn != null) {
                String sql = "INSERT INTO studentai (Stud_Vardas, Stud_Pavardė) VALUES (?, ?)";
                pst = conn.prepareStatement(sql);

                pst.setString(1, firstName);
                pst.setString(2, lastName);

                int rowsInserted = pst.executeUpdate();
                return rowsInserted > 0;
            } else {
                System.out.println("Failed to connect to the database.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return false;
        } finally {
            // Close resources
            try {
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}

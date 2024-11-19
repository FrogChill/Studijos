import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.List;

public class MarksGUI extends JFrame {

    private int studentId;

    public MarksGUI(int studentId) {
        this.studentId = studentId;

        // Set up the window
        setTitle("Student Marks");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout(10, 10));

        // Title Label
        JLabel titleLabel = new JLabel("Your Marks", JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Marks Panel
        JPanel marksPanel = new JPanel();
        marksPanel.setLayout(new BoxLayout(marksPanel, BoxLayout.Y_AXIS));

        // Fetch marks from the database and display them
        fetchAndDisplayMarks(marksPanel);

        // Scroll panel to handle overflowing marks list
        JScrollPane scrollPane = new JScrollPane(marksPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Back button to go to the main menu
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Go back to the main menu, pass default role and permissions for now
                String role = "Student";  // Example role
                List<String> permissions = List.of("View Marks");  // Example permissions
                new MainMenu(role, permissions);  // Pass the role and permissions
                dispose();  // Close the current window
            }
        });
        add(backButton, BorderLayout.SOUTH);

        // Make the window visible
        setVisible(true);
    }

    private void fetchAndDisplayMarks(JPanel marksPanel) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = Prisijungimas_DB.prisijungimas_DB(); // Establish connection
            if (conn != null) {
                System.out.println("Connected to the database.");

                String sql = "SELECT dalykas, markas FROM marks WHERE idStudentai = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, studentId);  // Set the student ID for the query

                // Execute the query and retrieve the result
                rs = pst.executeQuery();

                // Check if any results are found
                boolean hasMarks = false;

                while (rs.next()) {
                    String subject = rs.getString("dalykas");
                    int mark = rs.getInt("markas");

                    // Display each subject and the corresponding mark
                    JLabel markLabel = new JLabel(subject + ": " + mark);
                    marksPanel.add(markLabel);
                    hasMarks = true;
                }

                // If no marks were found, show a message
                if (!hasMarks) {
                    JLabel noMarksLabel = new JLabel("No marks available.");
                    marksPanel.add(noMarksLabel);
                }

            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        } finally {
            // Close resources in reverse order of their opening
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        // Assuming student ID is passed during the login phase
        // For demo purposes, assume a student with ID 1
        SwingUtilities.invokeLater(() -> new MarksGUI(1));
    }
}

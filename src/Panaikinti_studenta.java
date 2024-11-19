import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Panaikinti_studenta {
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new Panaikinti_studenta().createAndShowGUI());
}

private void createAndShowGUI() {
    // Main frame
    JFrame frame = new JFrame("Remove Student");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 300);
    frame.setLayout(new BorderLayout(10, 10));

    // Components
    JLabel lblTitle = new JLabel("Select a Student to Remove:", JLabel.CENTER);
    JList<String> studentList = new JList<>();
    JButton btnRemove = new JButton("Remove Selected");
    JLabel lblStatus = new JLabel("", JLabel.CENTER);

    // Panel for buttons
    JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 5, 5));
    bottomPanel.add(btnRemove);
    bottomPanel.add(lblStatus);

    // Add components to frame
    frame.add(lblTitle, BorderLayout.NORTH);
    frame.add(new JScrollPane(studentList), BorderLayout.CENTER);
    frame.add(bottomPanel, BorderLayout.SOUTH);

    // Populate the list of students
    ArrayList<Studentai> students = fetchAllStudents();
    DefaultListModel<String> listModel = new DefaultListModel<>();
    for (Studentai student : students) {
        listModel.addElement(student.getStudentID() + " - " + student.getStud_name() + " " + student.getStud_surname());
    }
    studentList.setModel(listModel);

    // Action listener for remove button
    btnRemove.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = studentList.getSelectedIndex();
            if (selectedIndex == -1) {
                lblStatus.setText("Please select a student.");
                lblStatus.setForeground(Color.RED);
            } else {
                Studentai selectedStudent = students.get(selectedIndex);
                boolean success = removeStudentFromDatabase(selectedStudent.getStudentID());
                if (success) {
                    lblStatus.setText("Student removed successfully!");
                    lblStatus.setForeground(Color.GREEN);
                    listModel.remove(selectedIndex);
                } else {
                    lblStatus.setText("Failed to remove student.");
                    lblStatus.setForeground(Color.RED);
                }
            }
        }
    });

    // Show the frame
    frame.setVisible(true);
}

private ArrayList<Studentai> fetchAllStudents() {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    ArrayList<Studentai> students = new ArrayList<>();

    try {
        conn = Prisijungimas_DB.prisijungimas_DB(); // Use your existing database connection method
        if (conn != null) {
            String sql = "SELECT * FROM studentai"; // Query to select all students
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                int studentID = rs.getInt("idStudentai"); // Use correct column names
                String firstName = rs.getString("Stud_Vardas"); // Use correct column names
                String lastName = rs.getString("Stud_Pavardė"); // Use correct column names
                students.add(new Studentai(studentID, firstName, lastName));
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

    return students;
}

public boolean removeStudentFromDatabase(int studentID) {
    Connection conn = null;
    PreparedStatement pst = null;

    try {
        conn = Prisijungimas_DB.prisijungimas_DB(); // Use your existing database connection method
        if (conn != null) {
            String sql = "DELETE FROM studentai WHERE idStudentai = ?"; // Ensure column name matches the DB
            pst = conn.prepareStatement(sql);
            pst.setInt(1, studentID);

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
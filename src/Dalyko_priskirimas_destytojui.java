import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class Dalyko_priskirimas_destytojui extends JFrame {
    private JComboBox<Dalykas> subjectComboBox;
    private JComboBox<Destytojai> teacherComboBox;
    private JButton assignButton;

    public Dalyko_priskirimas_destytojui() {
        setTitle("Assign Teacher to Subject");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(3, 2));

        // Components
        subjectComboBox = new JComboBox<>();
        teacherComboBox = new JComboBox<>();
        assignButton = new JButton("Assign");

        // Labels and components
        add(new JLabel("Select Subject:"));
        add(subjectComboBox);
        add(new JLabel("Select Teacher:"));
        add(teacherComboBox);
        add(new JLabel("")); // Empty placeholder
        add(assignButton);

        // Load subjects and teachers
        loadSubjects();
        loadTeachers();

        // Action Listener for Assign Button
        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assignTeacherToSubject();
            }
        });

        // Make the window visible
        setVisible(true);
    }
   private void loadSubjects() {
        Dalykai dalykai = new Dalykai();
        Dalykas[] subjects = dalykai.fetchDalykai();

        for (Dalykas subject : subjects) {
            subjectComboBox.addItem(subject);
        }
    }
    private void loadTeachers() {
        Destytojo_if_priskirimas retriever = new Destytojo_if_priskirimas();
        Destytojai[] teachers = retriever.getAllTeachers();

        for (Destytojai teacher : teachers) {
            teacherComboBox.addItem(teacher);
        }
    }
    private void assignTeacherToSubject() {
        Dalykas selectedSubject = (Dalykas) subjectComboBox.getSelectedItem();
        Destytojai selectedTeacher = (Destytojai) teacherComboBox.getSelectedItem();

        if (selectedSubject == null || selectedTeacher == null) {
            JOptionPane.showMessageDialog(this, "Please select both subject and teacher.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int subjectID = selectedSubject.getIdDalykas();
        int teacherID = selectedTeacher.getdestytojoID();

        // Perform the database insert operation
        Connection conn = null;
        PreparedStatement pst = null;
        Statement stmt = null;

        try {
            conn = Prisijungimas_DB.prisijungimas_DB(); // Establish connection
            if (conn != null) {
                stmt = conn.createStatement();

                // Check if the column for the teacher's ID exists
                DatabaseMetaData metaData = conn.getMetaData();
                ResultSet rs = metaData.getColumns(null, null, "dalyko_paskirstymas", String.valueOf(teacherID));
                if (!rs.next()) {
                    // If the column does not exist, create it
                    String alterTableSQL = "ALTER TABLE dalyko_paskirstymas ADD COLUMN `" + teacherID + "` INT";
                    stmt.executeUpdate(alterTableSQL);
                    JOptionPane.showMessageDialog(this, "Column for teacher ID " + teacherID + " created.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }

                // Now update or insert the teacher ID for the selected subject
                String updateSQL = "UPDATE dalyko_paskirstymas SET `" + teacherID + "` = ? WHERE DalykoID = ?";
                pst = conn.prepareStatement(updateSQL);
                pst.setInt(1, teacherID);
                pst.setInt(2, subjectID);

                // Execute the update
                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Teacher assigned to subject successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to assign teacher to subject.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Failed to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "SQL Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) pst.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error closing resources: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new Dalyko_priskirimas_destytojui();

    }
}

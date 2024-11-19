import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Redaguoti_Ivertinimai extends JFrame {

    private JComboBox<Dalykas> subjectComboBox;
    private JComboBox<Studentai> studentComboBox;
    private JComboBox<String> ivertinimasComboBox; // Ivertinimas1, Ivertinimas2, etc.
    private JTextField markField;
    private JButton updateButton, deleteButton;

    public Redaguoti_Ivertinimai() {

        // Set up the GUI window
        setTitle("Update/Delete Mark");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLayout(new GridLayout(5, 2));

        // Components
        subjectComboBox = new JComboBox<>();
        studentComboBox = new JComboBox<>();
        ivertinimasComboBox = new JComboBox<>(new String[] {"Ivertinimas", "Ivertinimas2", "Ivertinimas3", "Ivertinimas4", "Ivertinimas5"});
        markField = new JTextField();
        updateButton = new JButton("Update Mark");
        deleteButton = new JButton("Delete Mark");

        // Labels
        add(new JLabel("Select Subject:"));
        add(subjectComboBox);
        add(new JLabel("Select Student:"));
        add(studentComboBox);
        add(new JLabel("Select Ivertinimas:"));
        add(ivertinimasComboBox);
        add(new JLabel("Enter Mark (1-10):"));
        add(markField);
        add(updateButton);
        add(deleteButton);

        // Populate data
        loadSubjects();
        loadStudents();

        // Action Listeners
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMark();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMark();
            }
        });

        // Make GUI visible
        setVisible(true);
    }
    private void loadSubjects(){
        Dalykai dalykai = new Dalykai();
        Dalykas[] subjects = dalykai.fetchDalykai();

        for (Dalykas subject : subjects) {
            subjectComboBox.addItem(subject);
        }
    }
    private void loadStudents(){
        Stud_if_priskirimas studentRetriever = new Stud_if_priskirimas();
        Studentai[] students = studentRetriever.getAllStudents();

        for (Studentai student : students) {
            studentComboBox.addItem(student);
        }
    }
    private void updateMark() {
        Dalykas selectedSubject = (Dalykas) subjectComboBox.getSelectedItem();
        Studentai selectedStudent = (Studentai) studentComboBox.getSelectedItem();
        String ivertinimasColumn = (String) ivertinimasComboBox.getSelectedItem();
        String mark = markField.getText();

        if (selectedSubject == null || selectedStudent == null || ivertinimasColumn == null || mark.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int markC;
        try {
            markC = Integer.parseInt(mark);
            if (markC < 1 || markC > 10) {
                JOptionPane.showMessageDialog(this, "Mark must be between 1 and 10.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mark must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Prisijungimas_DB.prisijungimas_DB();
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Failed to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String selectSQL = "SELECT " + ivertinimasColumn + " FROM dienynas WHERE DalykoID = ? AND StudentoID = ?";
            ps = con.prepareStatement(selectSQL);
            ps.setInt(1, selectedSubject.getIdDalykas());
            ps.setInt(2, selectedStudent.getStudentID());
            rs = ps.executeQuery();

            if (rs.next()) {
                // Update the selected mark
                String updateSQL = "UPDATE dienynas SET " + ivertinimasColumn + " = ? WHERE DalykoID = ? AND StudentoID = ?";
                try (PreparedStatement updatePs = con.prepareStatement(updateSQL)) {
                    updatePs.setInt(1, markC);
                    updatePs.setInt(2, selectedSubject.getIdDalykas());
                    updatePs.setInt(3, selectedStudent.getStudentID());
                    updatePs.executeUpdate();
                }
                JOptionPane.showMessageDialog(this, "Mark updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No record found for the selected Ivertinimas.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "SQL Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error closing resources: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void deleteMark() {
        Dalykas selectedSubject = (Dalykas) subjectComboBox.getSelectedItem();
        Studentai selectedStudent = (Studentai) studentComboBox.getSelectedItem();
        String ivertinimasColumn = (String) ivertinimasComboBox.getSelectedItem();

        if (selectedSubject == null || selectedStudent == null || ivertinimasColumn == null) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = Prisijungimas_DB.prisijungimas_DB();
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Failed to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String deleteSQL = "UPDATE dienynas SET " + ivertinimasColumn + " = NULL WHERE DalykoID = ? AND StudentoID = ?";
            ps = con.prepareStatement(deleteSQL);
            ps.setInt(1, selectedSubject.getIdDalykas());
            ps.setInt(2, selectedStudent.getStudentID());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Mark deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "SQL Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error closing resources: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public static void main(String[] args) {
        new Redaguoti_Ivertinimai();




}
}

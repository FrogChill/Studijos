import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ivertinimas extends JFrame {

    private JComboBox<Dalykas> subjectComboBox;
    private JComboBox<Studentai> studentComboBox;
    private JTextField markField;
    private JButton submitButton;

    public Ivertinimas() {

        // Set up the GUI window
        setTitle("Mark Entry System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLayout(new GridLayout(4, 2));

        // Components
        subjectComboBox = new JComboBox<>();
        studentComboBox = new JComboBox<>();
        markField = new JTextField();
        submitButton = new JButton("Submit");

        // Labels
        add(new JLabel("Select Subject:"));
        add(subjectComboBox);
        add(new JLabel("Select Student:"));
        add(studentComboBox);
        add(new JLabel("Enter Mark (1-10):"));
        add(markField);
        add(new JLabel("")); // Empty placeholder
        add(submitButton);

        // Populate data
        loadSubjects();
        loadStudents();
        // Action Listener for Submit Button
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        submitMark();
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
    private void submitMark() {
        Dalykas selectedSubject = (Dalykas) subjectComboBox.getSelectedItem();
        Studentai selectedStudent = (Studentai) studentComboBox.getSelectedItem();
        String mark = markField.getText();

        if (selectedSubject == null || selectedStudent == null || mark.isEmpty()) {
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
            String selectSQL = "SELECT Ivertinimas, Ivertinimas2, Ivertinimas3, Ivertinimas4, Ivertinimas5 FROM dienynas WHERE DalykoID = ? AND StudentoID = ?";
            ps = con.prepareStatement(selectSQL);
            ps.setInt(1, selectedSubject.getIdDalykas());
            ps.setInt(2, selectedStudent.getStudentID());
            rs = ps.executeQuery();

            if (rs.next()) {
                for (int i = 1; i <= 5; i++) {
                    String columnName = "Ivertinimas" + (i == 1 ? "" : i);
                    int columnValue = rs.getInt(columnName);
                    if (rs.wasNull()) {
                        String updateSQL = "UPDATE dienynas SET " + columnName + " = ? WHERE DalykoID = ? AND StudentoID = ?";
                        try (PreparedStatement updatePs = con.prepareStatement(updateSQL)) {
                            updatePs.setInt(1, markC);
                            updatePs.setInt(2, selectedSubject.getIdDalykas());
                            updatePs.setInt(3, selectedStudent.getStudentID());
                            updatePs.executeUpdate();
                        }
                        JOptionPane.showMessageDialog(this, "Mark added to " + columnName + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this, "The student already has 5 marks for this subject.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String insertSQL = "INSERT INTO dienynas (DalykoID, StudentoID, Ivertinimas) VALUES (?, ?, ?)";
                try (PreparedStatement insertPs = con.prepareStatement(insertSQL)) {
                    insertPs.setInt(1, selectedSubject.getIdDalykas());
                    insertPs.setInt(2, selectedStudent.getStudentID());
                    insertPs.setInt(3, markC);
                    insertPs.executeUpdate();
                }
                JOptionPane.showMessageDialog(this, "New record created and mark added to Ivertinimas.", "Success", JOptionPane.INFORMATION_MESSAGE);
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
    public static void main(String[] args) {
        new Ivertinimas();
    }

}

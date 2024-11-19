import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DienynoGUI extends JFrame {

    private JComboBox<Dalykas> subjectComboBox;
    private JComboBox<Studentai> studentComboBox;
    private JComboBox<String> ivertinimasComboBox; // Ivertinimas1, Ivertinimas2, etc.
    private JTextField markField;
    private JButton viewButton, addButton, deleteButton;
    private JTextArea marksDisplayArea;

    public DienynoGUI() {
        // Set up the GUI window
        setTitle("Dienynas - Marks Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new GridLayout(8, 2));

        // Components
        subjectComboBox = new JComboBox<>();
        studentComboBox = new JComboBox<>();
        ivertinimasComboBox = new JComboBox<>(new String[] {"Ivertinimas", "Ivertinimas2", "Ivertinimas3", "Ivertinimas4", "Ivertinimas5"});
        markField = new JTextField();
        viewButton = new JButton("View Marks");
        addButton = new JButton("Add Mark");
        deleteButton = new JButton("Delete Mark");
        marksDisplayArea = new JTextArea(5, 20);
        marksDisplayArea.setEditable(false);

        // Labels
        add(new JLabel("Select Subject:"));
        add(subjectComboBox);
        add(new JLabel("Select Student:"));
        add(studentComboBox);
        add(new JLabel("Select Ivertinimas:"));
        add(ivertinimasComboBox);
        add(new JLabel("Enter Mark (1-10):"));
        add(markField);
        add(viewButton);
        add(addButton);
        add(deleteButton);
        add(new JLabel("Marks for Selected Student:"));
        add(new JScrollPane(marksDisplayArea)); // JScrollPane for scrollable area

        // Populate data
        loadSubjects();
        loadStudents();

        // Action Listeners
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewMarks();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMark();
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

    private void loadSubjects() {
        Dalykai dalykai = new Dalykai();
        Dalykas[] subjects = dalykai.fetchDalykai();
        for (Dalykas subject : subjects) {
            subjectComboBox.addItem(subject);
        }
    }

    private void loadStudents() {
        Stud_if_priskirimas studentRetriever = new Stud_if_priskirimas();
        Studentai[] students = studentRetriever.getAllStudents();
        for (Studentai student : students) {
            studentComboBox.addItem(student);
        }
    }

    private void viewMarks() {
        Studentai selectedStudent = (Studentai) studentComboBox.getSelectedItem();

        if (selectedStudent == null) {
            JOptionPane.showMessageDialog(this, "Please select a student.", "Error", JOptionPane.ERROR_MESSAGE);
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

            // Fetch all marks for the selected student
            String selectSQL = "SELECT * FROM dienynas WHERE StudentoID = ?";
            ps = con.prepareStatement(selectSQL);
            ps.setInt(1, selectedStudent.getStudentID());
            rs = ps.executeQuery();

            // Clear the text area before displaying the new results
            marksDisplayArea.setText("");

            // Display all marks
            while (rs.next()) {
                int subjectId = rs.getInt("DalykoID");
                String ivertinimas1 = rs.getString("Ivertinimas");
                String ivertinimas2 = rs.getString("Ivertinimas2");
                String ivertinimas3 = rs.getString("Ivertinimas3");
                String ivertinimas4 = rs.getString("Ivertinimas4");
                String ivertinimas5 = rs.getString("Ivertinimas5");

                // Append to the display area
                marksDisplayArea.append("Subject ID: " + subjectId + "\n");
                marksDisplayArea.append("Ivertinimas: " + ivertinimas1 + "\n");
                marksDisplayArea.append("Ivertinimas2: " + ivertinimas2 + "\n");
                marksDisplayArea.append("Ivertinimas3: " + ivertinimas3 + "\n");
                marksDisplayArea.append("Ivertinimas4: " + ivertinimas4 + "\n");
                marksDisplayArea.append("Ivertinimas5: " + ivertinimas5 + "\n\n");
            }

            if (marksDisplayArea.getText().isEmpty()) {
                marksDisplayArea.append("No marks found for this student.\n");
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

    private void addMark() {
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

        try {
            con = Prisijungimas_DB.prisijungimas_DB();
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Failed to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String updateSQL = "UPDATE dienynas SET " + ivertinimasColumn + " = ? WHERE DalykoID = ? AND StudentoID = ?";
            ps = con.prepareStatement(updateSQL);
            ps.setInt(1, markC);
            ps.setInt(2, selectedSubject.getIdDalykas());
            ps.setInt(3, selectedStudent.getStudentID());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Mark added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

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
        new DienynoGUI();
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentGUI().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Student Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Panel for displaying students
        JPanel panel = new JPanel(new BorderLayout());
        JList<String> studentList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(studentList);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel for buttons and actions
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton btnAdd = new JButton("Add Student");
        JButton btnRemove = new JButton("Remove Student");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnRemove);

        // Add buttonPanel to frame
        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Status label
        JLabel lblStatus = new JLabel("", JLabel.CENTER);
        frame.add(lblStatus, BorderLayout.NORTH);

        // Fetch all students and populate the list
        ArrayList<Studentai> students = fetchAllStudents();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Studentai student : students) {
            listModel.addElement(student.getStudentID() + " - " + student.getStud_name() + " " + student.getStud_surname());
        }
        studentList.setModel(listModel);

        // Add Student action
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent(lblStatus);
            }
        });

        // Remove Student action
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
        // Fetch the students from the database (returns an array of Studentai)
        Studentai[] studentsArray = new Stud_if_priskirimas().getAllStudents();

        // Convert the array to an ArrayList
        ArrayList<Studentai> studentsList = new ArrayList<>();

        // Add all students from the array to the ArrayList
        for (Studentai student : studentsArray) {
            studentsList.add(student);
        }

        // Return the ArrayList
        return studentsList;
    }
    private void addStudent(JLabel lblStatus) {
        JTextField txtFirstName = new JTextField();
        JTextField txtLastName = new JTextField();
        Object[] message = {
                "First Name:", txtFirstName,
                "Last Name:", txtLastName
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Enter Student Details", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String firstName = txtFirstName.getText().trim();
            String lastName = txtLastName.getText().trim();

            if (firstName.isEmpty() || lastName.isEmpty()) {
                lblStatus.setText("All fields are required!");
                lblStatus.setForeground(Color.RED);
            } else {
                boolean success = new Prideti_studenta().addStudentToDatabase(firstName, lastName);
                if (success) {
                    lblStatus.setText("Student added successfully!");
                    lblStatus.setForeground(Color.GREEN);
                } else {
                    lblStatus.setText("Failed to add Student.");
                    lblStatus.setForeground(Color.RED);
                }
            }
        }
    }

    private boolean removeStudentFromDatabase(int studentID) {
        return new Panaikinti_studenta().removeStudentFromDatabase(studentID);
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Login extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public Login() {
        // Set up the window
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);  // Adjust size as needed
        setLayout(new BorderLayout(10, 10));

        // Title label
        JLabel titleLabel = new JLabel("Login", JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Panel for username and password fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel lblUsername = new JLabel("Username (First Name): ");
        txtUsername = new JTextField();
        JLabel lblPassword = new JLabel("Password (Surname): ");
        txtPassword = new JPasswordField();
        btnLogin = new JButton("Login");

        inputPanel.add(lblUsername);
        inputPanel.add(txtUsername);
        inputPanel.add(lblPassword);
        inputPanel.add(txtPassword);
        inputPanel.add(btnLogin);

        add(inputPanel, BorderLayout.CENTER);

        // Action listener for the login button
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());

                // Attempt to authenticate
                User user = authenticate(username, password);
                if (user != null) {
                    // If authenticated, open the MainMenu with the user's role and permissions
                    new MainMenu(user.getRole(), user.getPermissions());
                    dispose();  // Close the login window
                } else {
                    // If authentication failed, show an error message
                    JOptionPane.showMessageDialog(Login.this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Make the window visible
        setVisible(true);
    }

    // Method to authenticate the user based on username and password
    private User authenticate(String username, String password) {
        // Check if the user is an Admin
        Admin_Priskirimas adminRetriever = new Admin_Priskirimas();
        Admin[] admins = adminRetriever.getAllAdmins();
        for (Admin admin : admins) {
            if (admin.getAdminName().equals(username) && admin.getAdminSurname().equals(password)) {
                return new User(admin.getAdminName(), admin.getAdminSurname(), "Admin", List.of("Manage Admins", "Manage Teachers", "Manage Students"));
            }
        }

        // Check if the user is a Student
        Stud_if_priskirimas studentRetriever = new Stud_if_priskirimas();
        Studentai[] students = studentRetriever.getAllStudents();
        for (Studentai student : students) {
            if (student.getStud_name().equals(username) && student.getStud_surname().equals(password)) {
                return new User(student.getStud_name(), student.getStud_surname(), "Student", List.of("View Marks"));
            }
        }

        // Check if the user is a Teacher
        Destytojo_if_priskirimas teacherRetriever = new Destytojo_if_priskirimas();
        Destytojai[] teachers = teacherRetriever.getAllTeachers();
        for (Destytojai teacher : teachers) {
            if (teacher.getdestytojo_vardas().equals(username) && teacher.getdestytojo_pavarde().equals(password)) {
                return new User(teacher.getdestytojo_vardas(), teacher.getdestytojo_pavarde(), "Teacher", List.of("Manage Marks", "Manage Students"));
            }
        }

        // If no match found, return null (authentication failed)
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());  // Start the Login GUI
    }
}

// User class to store user information
class users {
    private String firstName;
    private String lastName;
    private String role;
    private List<String> permissions;

    public users(String firstName, String lastName, String role, List<String> permissions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.permissions = permissions;
    }

    public String getRole() {
        return role;
    }

    public List<String> getPermissions() {
        return permissions;
    }
}

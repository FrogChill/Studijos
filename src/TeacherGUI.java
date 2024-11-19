import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherGUI extends JFrame {
    private JButton btnAddTeacher;
    private JButton btnRemoveTeacher;
    private JButton btnAssignTeacher;
    private JPanel panel;

    public TeacherGUI() {
        setTitle("Teacher Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);  // Center the window on screen

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        // Buttons for each operation
        btnAddTeacher = new JButton("Add Teacher");
        btnRemoveTeacher = new JButton("Remove Teacher");
        btnAssignTeacher = new JButton("Assign Teacher to Subject");

        // Add buttons to the panel
        panel.add(btnAddTeacher);
        panel.add(btnRemoveTeacher);
        panel.add(btnAssignTeacher);

        // Action listeners for buttons
        btnAddTeacher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Prideti_destytoja();  // Open "Add Teacher" window
            }
        });

        btnRemoveTeacher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Panaikinti_Destytoja();  // Open "Remove Teacher" window
            }
        });

        btnAssignTeacher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Dalyko_priskirimas_destytojui();  // Open "Assign Teacher to Subject" window
            }
        });

        // Add panel to the frame and display the window
        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TeacherGUI();
    }
}

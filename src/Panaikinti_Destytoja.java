import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Panaikinti_Destytoja extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Panaikinti_Destytoja().createAndShowGUI());
    }

    private void createAndShowGUI() {
        // Main frame
        JFrame frame = new JFrame("Remove Teacher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout(10, 10));

        // Components
        JLabel lblTitle = new JLabel("Select a Teacher to Remove:", JLabel.CENTER);
        JList<String> teachersListUI = new JList<>();  // This is the JList that will display teacher names
        JButton btnRemove = new JButton("Remove Selected");
        JLabel lblStatus = new JLabel("", JLabel.CENTER);

        // Panel for buttons
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        bottomPanel.add(btnRemove);
        bottomPanel.add(lblStatus);

        // Add components to frame
        frame.add(lblTitle, BorderLayout.NORTH);
        frame.add(new JScrollPane(teachersListUI), BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Fetch all teachers from database and populate the list model
        ArrayList<Destytojai> teachersList = fetchAllTeachers();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Destytojai teacher : teachersList) {
            listModel.addElement(teacher.getdestytojoID() + " - " + teacher.getdestytojo_vardas() + " - " + teacher.getdestytojo_pavarde());
        }
        teachersListUI.setModel(listModel); // Set the list model to JList

        // Action listener for remove button
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = teachersListUI.getSelectedIndex();
                if (selectedIndex == -1) {
                    lblStatus.setText("Please select a teacher.");
                    lblStatus.setForeground(Color.RED);
                } else {
                    Destytojai selectedTeacher = teachersList.get(selectedIndex);
                    boolean success = removeTeacherFromDatabase(selectedTeacher.getdestytojoID());
                    if (success) {
                        lblStatus.setText("Teacher removed successfully!");
                        lblStatus.setForeground(Color.GREEN);
                        listModel.remove(selectedIndex); // Remove from JList model
                    } else {
                        lblStatus.setText("Failed to remove teacher.");
                        lblStatus.setForeground(Color.RED);
                    }
                }
            }
        });

        // Show the frame
        frame.setVisible(true);
    }

    private ArrayList<Destytojai> fetchAllTeachers() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Destytojai> teachersList = new ArrayList<>();

        try {
            conn = Prisijungimas_DB.prisijungimas_DB(); // Use your existing database connection method
            if (conn != null) {
                String sql = "SELECT * FROM destytojai"; // Query to select all teachers
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();

                while (rs.next()) {
                    int destytojoID = rs.getInt("destytojoID"); // Use correct column names
                    String Dname = rs.getString("destytojo_vardas"); // Use correct column names
                    String Dsurname = rs.getString("destytojo_pavarde"); // Use correct column names
                    teachersList.add(new Destytojai(destytojoID, Dname, Dsurname));
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

        return teachersList;
    }

    private boolean removeTeacherFromDatabase(int destytojoid) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Prisijungimas_DB.prisijungimas_DB(); // Use your existing database connection method
            if (conn != null) {
                String sql = "DELETE FROM destytojai WHERE destytojoID = ?"; // Ensure column name matches the DB
                pst = conn.prepareStatement(sql);
                pst.setInt(1, destytojoid);

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

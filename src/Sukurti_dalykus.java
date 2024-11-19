import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class Sukurti_dalykus {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Sukurti_dalykus().createAndShowGUI());
    }
    private void createAndShowGUI() {
        JFrame frame = new JFrame("Dalykas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 2, 10, 10));

        // Add labels and text fields
        JLabel lblFirstName = new JLabel("Dalyvo pavadinimas");
        JTextField txtFirstName = new JTextField();
;

        // Add button to submit data
        JButton btnAddDalykas = new JButton("Add dalykas");
        JLabel lblStatus = new JLabel("", JLabel.CENTER);

        frame.add(lblFirstName);
        frame.add(txtFirstName);
        frame.add(btnAddDalykas);
        frame.add(lblStatus);

        btnAddDalykas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String firstName = txtFirstName.getText().trim();

                if (firstName.isEmpty()) {
                    lblStatus.setText("All fields are required!");
                    lblStatus.setForeground(Color.RED);
                } else {
                    try {
                        boolean success = addDalykasToDatabase(firstName);
                        if (success) {
                            lblStatus.setText("Dalykas added successfully!");
                            lblStatus.setForeground(Color.GREEN);
                            txtFirstName.setText("");
                        } else {
                            lblStatus.setText("Failed to add Dalykas.");
                            lblStatus.setForeground(Color.RED);
                        }
                    } catch (NumberFormatException ex) {
                        lblStatus.setText("Pacan gatov");
                        lblStatus.setForeground(Color.RED);
                    }
                }
            }
        });
        frame.setVisible(true);
    }
    private boolean addDalykasToDatabase(String firstName) {
            Connection conn = null;
            PreparedStatement pst = null;

            try {
                conn = Prisijungimas_DB.prisijungimas_DB(); // Establish connection
                if (conn != null) {
                    String sql = "INSERT INTO dalykas (Dalyko_pavadinimas) VALUES (?)";
                    pst = conn.prepareStatement(sql);

                    pst.setString(1, firstName);


                    int rowsInserted = pst.executeUpdate();
                    return rowsInserted > 0;
                } else {
                    System.out.println("Failed to connect to the database.");
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("SQL Error: " + e.getMessage());
                return false;
            } finally {
                // Close resources
                try {
                    if (pst != null) pst.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    System.out.println("Error closing resources: " + e.getMessage());
                }
            }
        }
    }





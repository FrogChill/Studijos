import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Panaikinti_dalyka {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Panaikinti_dalyka().createAndShowGUI());
    }
    public void createAndShowGUI() {
        JFrame frame = new JFrame("Remove Dalykas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout(10, 10));

        // Components
        JLabel lblTitle = new JLabel("Select a dalykas to Remove:", JLabel.CENTER);
        JList<String> dalykasListUI = new JList<>();  // This is the JList that will display teacher names
        JButton btnRemove = new JButton("Remove Selected");
        JLabel lblStatus = new JLabel("", JLabel.CENTER);

        // Panel for buttons
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        bottomPanel.add(btnRemove);
        bottomPanel.add(lblStatus);

        // Add components to frame
        frame.add(lblTitle, BorderLayout.NORTH);
        frame.add(new JScrollPane(dalykasListUI), BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        ArrayList<Dalykas> dalykasList = fetchAlldalykai();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Dalykas dalyka : dalykasList) {
            listModel.addElement(dalyka.getIdDalykas() + " " + dalyka.getDalykoPavadinimas());
        }
        dalykasListUI.setModel(listModel);
        btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = dalykasListUI.getSelectedIndex();
                if (selectedIndex == -1) {
                    lblStatus.setText("Select a dalykas to Remove");
                    lblStatus.setForeground(Color.RED);
                } else {
                    Dalykas selecteddalykas = dalykasList.get(selectedIndex);
                    boolean success = removedDalykasfromDataBase(selecteddalykas.getIdDalykas());
                    if (success) {
                        lblStatus.setText("Removed Dalykas");
                        lblStatus.setForeground(Color.GREEN);
                        listModel.remove(selectedIndex); // Remove from JList model
                    } else {
                        lblStatus.setText("Faild to Remove dalykas");
                        lblStatus.setForeground(Color.RED);
                    }
                }
            }
        });
        frame.setVisible(true);
    }
    private ArrayList<Dalykas> fetchAlldalykai() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Dalykas> dalykasList = new ArrayList<>();

        try {
            conn = Prisijungimas_DB.prisijungimas_DB();
            if (conn != null) {
                String sql = "SELECT * FROM dalykas";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int dalykoID = rs.getInt("idDalykas");
                    String dalykoPV = rs.getString("Dalyko_pavadinimas");
                    dalykasList.add(new Dalykas(dalykoID, dalykoPV));
                }
            } else {
                System.out.println("Failed to connect to the database.");
            }
        }catch (SQLException e){
            System.out.println("SQL Error: " + e.getMessage());
            } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("SQL Error: " + e.getMessage());
            }
        }
        return dalykasList;
    }
    private boolean removedDalykasfromDataBase(int idDalykas) {
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = Prisijungimas_DB.prisijungimas_DB();
            if (conn != null) {
                String sql = "DELETE FROM dalykas WHERE idDalykas = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, idDalykas);

                int rowsDeleted = ps.executeUpdate();
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
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
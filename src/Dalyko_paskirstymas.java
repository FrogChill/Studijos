import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Dalyko_paskirstymas {

    // Method to insert subjects into the dalyko_paskirstymas table
    public void insertToDalykoPaskirstymas(Dalykas[] dalykai) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Prisijungimas_DB.prisijungimas_DB(); // Establish connection
            if (conn != null) {
                System.out.println("Connected to the database.");

                String insertSQL = "INSERT INTO dalyko_paskirstymas (DalykoID, Dalyko_pavadinimas) VALUES (?, ?)";
                pst = conn.prepareStatement(insertSQL);

                for (Dalykas dalykas : dalykai) {
                    pst.setInt(1, dalykas.getIdDalykas());  // Set the DalykoID
                    pst.setString(2, dalykas.getDalykoPavadinimas());  // Set the Dalyko_pavadinimas

                    // Execute the insert for each subject
                    pst.executeUpdate();
                    System.out.println("Inserted subject: " + dalykas.getDalykoPavadinimas());
                }
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } finally {
            // Close resources in reverse order of their opening
            try {
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Dalykai retrieve = new Dalykai();
        Dalykas[] subjects = retrieve.fetchDalykai();  // Fetch subjects from the dalykas table

        if (subjects != null && subjects.length > 0) {
            Dalyko_paskirstymas dalykoPaskirstymas = new Dalyko_paskirstymas();
            dalykoPaskirstymas.insertToDalykoPaskirstymas(subjects);  // Insert them into the dalyko_paskirstymas table
        } else {
            System.out.println("No subjects found to insert.");
        }
    }
}
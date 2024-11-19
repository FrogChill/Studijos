import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Grupes {
    public static void main(String[] args) {

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = Prisijungimas_DB.prisijungimas_DB(); // Establish connection
            if (conn != null) {
                System.out.println("Connected to the database.");

                String sql = "SELECT * FROM grupės";
                pst = conn.prepareStatement(sql);

                // Execute the query
                rs = pst.executeQuery();

                // Check if there is any data in the ResultSet
                if (!rs.isBeforeFirst()) {
                    System.out.println("Table 'grupės' is empty or no data was retrieved.");
                } else {
                    System.out.println("idGrupės\t\tGrupės_pavadinimas");

                    // Loop through the rows
                    while (rs.next()) {
                        int id = rs.getInt("idGrupės");
                        String name = rs.getString("Grupės_pavadinimas");
                        System.out.println(id + "\t\t" + name);
                    }
                }
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        } finally {
            // Close resources in reverse order of their opening
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}

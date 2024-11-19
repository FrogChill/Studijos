import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Dalykai {

    public static void main(String[] args) {

        Dalykai retrieve = new Dalykai();
        Dalykas[] sub = retrieve.fetchDalykai();

        if (sub != null && sub.length > 0) {
            for (Dalykas dalyka : sub) {
                System.out.println(dalyka);
            }
        } else {
            System.out.println("No Dalykai found.");
        }
    }

    // Method to fetch subjects from the database and return them in an array
    public Dalykas[] fetchDalykai() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Dalykas> dalykaiList = new ArrayList<>();

        try {
            conn = Prisijungimas_DB.prisijungimas_DB(); // Establish connection
            if (conn != null) {
                System.out.println("Connected to the database.");

                String sql = "SELECT * FROM dalykas";
                pst = conn.prepareStatement(sql);

                // Execute the query
                rs = pst.executeQuery();

                // Check if there is any data in the ResultSet
                if (!rs.isBeforeFirst()) {
                    System.out.println("Table 'dalykas' is empty or no data was retrieved.");
                } else {
                    System.out.println("idDalykas\t\tDalyko_pavadinimas");

                    // Loop through the rows and add each subject to the ArrayList
                    while (rs.next()) {
                        int id = rs.getInt("idDalykas");
                        String name = rs.getString("Dalyko_pavadinimas");
                        System.out.println(id + "\t\t" + name);

                        dalykaiList.add(new Dalykas(id, name));
                    }
                }
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
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

        // Convert ArrayList to array and return it
        return dalykaiList.toArray(new Dalykas[0]);
    }
}
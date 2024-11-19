import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dienynas {
    public static void main(String[] args) {

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = Prisijungimas_DB.prisijungimas_DB(); // Establish connection
            if (conn != null) {
                System.out.println("Connected to the database.");

                String sql = "SELECT * FROM dienynas";
                pst = conn.prepareStatement(sql);

                // Execute the query
                rs = pst.executeQuery();

                // Check if there is any data in the ResultSet
                if (!rs.isBeforeFirst()) {
                    System.out.println("Table 'dienas' is empty or no data was retrieved.");
                } else {
                    System.out.println("DalykoID\t\tDalyko_pavadinimas\t\tStudentoID\t\tStudentas\t\tgrupe\t\tIvertinimas\t\tIvertinimas2\t\tIvertinimas3\t\tIvertinimas4\t\tIvertinimas5");

                    // Loop through the rows
                    while (rs.next()) {
                        int id = rs.getInt("DalykoID");
                        String name = rs.getString("Dalyko_pavadinimas");
                        int Studid = rs.getInt("StudentoID");
                        String Stud = rs.getString("studento_vardas");
                        String StudN = rs.getString("studento_pavarde");
                        String group = rs.getString("grupe");
                        int ivertinimas = rs.getInt("Ivertinimas");
                        int ivertinimas2 = rs.getInt("Ivertinimas2");
                        int ivertinimas3 = rs.getInt("Ivertinimas3");
                        int ivertinimas4 = rs.getInt("Ivertinimas4");
                        int ivertinimas5 = rs.getInt("Ivertinimas5");


                        System.out.println(id + "\t\t" + name + "\t\t" + Studid + "\t\t" + Stud + "\t\t" + StudN + "\t\t" + group + "\t\t" + ivertinimas + "\t\t" + ivertinimas2 + "\t\t" + ivertinimas3 + "\t\t" + ivertinimas4 + "\t\t" + ivertinimas5);
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

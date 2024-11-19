import java.sql.*;
import java.util.ArrayList;

public class Admin_Priskirimas {

    public static void main(String[] args) {
        Admin_Priskirimas retriever = new Admin_Priskirimas();
        Admin[] Admins = retriever.getAllAdmins();
        if (Admins != null && Admins.length > 0) {
            for (Admin boss : Admins) {
                System.out.println(boss);
            }
        } else {
            System.out.println("No students found.");
        }
    }
    public Admin[] getAllAdmins(){
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Admin> adlist = new ArrayList<>();

        try {
            conn = Prisijungimas_DB.prisijungimas_DB(); // Establish connection
            if (conn != null) {
                System.out.println("Connected to the database.");

                String sql = "SELECT * FROM admin";
                pst = conn.prepareStatement(sql);

                // Execute the query
                rs = pst.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("idAdmin");
                    String name = rs.getString("Admin_name");
                    String surname = rs.getString("Admin_surrname");
                    adlist.add(new Admin(id, name, surname));
                }
                // Check if there is any data in the ResultSet
                System.out.println("Admin fetched successfully.");
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
        return adlist.toArray(new Admin[0]);
    }
}






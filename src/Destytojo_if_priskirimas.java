import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Destytojo_if_priskirimas {

    public static void main(String[] args) {

        Destytojo_if_priskirimas retriever = new Destytojo_if_priskirimas();
        Destytojai[] Teacher = retriever.getAllTeachers();

        if (Teacher != null && Teacher.length > 0) {
            for (Destytojai ummm : Teacher) {
                System.out.println(ummm);
            }
        } else {
            System.out.println("No teachers found.");
        }
    }


        public Destytojai[] getAllTeachers() {

            Connection conn = null;
            PreparedStatement pst = null;
            ResultSet rs = null;
            ArrayList<Destytojai> teacherslist = new ArrayList<>();

            try {
                conn = Prisijungimas_DB.prisijungimas_DB(); // Establish connection
                if (conn != null) {
                    System.out.println("Connected to the database.");

                    String sql = "SELECT * FROM destytojai";
                    pst = conn.prepareStatement(sql);

                    // Execute the query
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        int destytojoID = rs.getInt("destytojoID");
                        String Dname = rs.getString("destytojo_vardas");
                        String Dsurname = rs.getString("destytojo_pavarde");

                        // Add each student to the list
                        teacherslist.add(new Destytojai(destytojoID, Dname, Dsurname));
                    }
                    System.out.println("Destytojai fetched successfully.");
                }
                else {
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
            // Convert the list to an array and return it
            return teacherslist.toArray(new Destytojai[0]);
        }
    }



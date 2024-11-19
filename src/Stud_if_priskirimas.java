import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Stud_if_priskirimas {




    public static void main(String[] args) {
        // Create an instance of StudentRetriever and fetch the students
        Stud_if_priskirimas retriever = new Stud_if_priskirimas();
        Studentai[] students = retriever.getAllStudents();

        // Print all the students
        if (students != null && students.length > 0) {
            for (Studentai student : students) {
                System.out.println(student);
            }
        } else {
            System.out.println("No students found.");
        }
    }

    public Studentai[] getAllStudents() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Studentai> studentList = new ArrayList<>();

        try {
            conn = Prisijungimas_DB.prisijungimas_DB(); // Establish connection
            if (conn != null) {
                System.out.println("Connected to the database.");

                String sql = "SELECT * FROM studentai"; // Query to select all students
                pst = conn.prepareStatement(sql);

                // Execute the query and retrieve the result
                rs = pst.executeQuery();

                // Loop through the result set and create Studentai objects
                while (rs.next()) {
                    int studentId = rs.getInt("idStudentai");
                    String name = rs.getString("Stud_Vardas");
                    String surname = rs.getString("Stud_Pavardė");

                    // Add each student to the list
                    studentList.add(new Studentai(studentId, name, surname));
                }

                System.out.println("Students fetched successfully.");
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

        // Convert the list to an array and return it
        return studentList.toArray(new Studentai[0]);
    }
}




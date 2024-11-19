import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.sql.ResultSet;
public class atnaujinti_dienyna {
    public static void main(String[] args) {
        Dalykai dalykai = new Dalykai();
        Dalykas[] sub = dalykai.fetchDalykai();  // Fetch the subjects

        Stud_if_priskirimas retrieve = new Stud_if_priskirimas();
        Studentai[] studentai = retrieve.getAllStudents();  // Fetch the students

        // Assign students to groups
        Studentai_grupes grupes = new Studentai_grupes();
        Map<String, List<Studentai>> groupedStudents = grupes.assignGroups(studentai);

        atnaujinti_dienyna transfer = new atnaujinti_dienyna();
        transfer.moveStudentsToDienynas(groupedStudents, sub);  // Move students to dienynas table
    }

    public void moveStudentsToDienynas(Map<String, List<Studentai>> groupedStudents, Dalykas[] sub) {
        if (groupedStudents == null || groupedStudents.isEmpty()) {
            System.out.println("No groups found");
            return;
        }
        if (sub == null || sub.length == 0) {
            System.out.println("No subjects found");
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        PreparedStatement checkStmt = null;
        ResultSet rs = null;

        try {
            conn = Prisijungimas_DB.prisijungimas_DB();  // Get the database connection
            if (conn != null) {
                System.out.println("Connection established");

                // SQL Insert statement without DienynoID (auto-increment handled by DB)
                String sql = "INSERT INTO dienynas (DalykoID, Dalyko_pavadinimas, StudentoID, studento_vardas, studento_pavarde, grupe) "
                        + "VALUES (?, ?, ?, ?, ?, ?)";

                // SQL Select statement to check for existing records
                String checkSql = "SELECT COUNT(*) FROM dienynas WHERE DalykoID = ? AND StudentoID = ? AND grupe = ?";

                stmt = conn.prepareStatement(sql);
                checkStmt = conn.prepareStatement(checkSql);

                // Loop through all subjects
                for (Dalykas dalyka : sub) {
                    // Loop through each group
                    for (Map.Entry<String, List<Studentai>> entry : groupedStudents.entrySet()) {
                        String groupName = entry.getKey();  // Get group name (e.g., "Grupe A" or "Grupe B")
                        List<Studentai> group = entry.getValue();  // Get the list of students in the group

                        // Loop through each student in the group
                        for (Studentai student : group) {
                            // Check if the record already exists in the dienynas table
                            checkStmt.setInt(1, dalyka.getIdDalykas());
                            checkStmt.setInt(2, student.getStudentID());
                            checkStmt.setString(3, groupName);

                            rs = checkStmt.executeQuery();
                            if (rs.next() && rs.getInt(1) > 0) {
                                // Record exists, skip insertion
                                System.out.println("Skipping duplicate student ID " + student.getStudentID() + " in group " + groupName);
                                continue;
                            }

                            // If the record does not exist, insert it (DienynoID will auto-increment)
                            stmt.setInt(1, dalyka.getIdDalykas());  // Set subject ID
                            stmt.setString(2, dalyka.getDalykoPavadinimas());  // Set subject name
                            stmt.setInt(3, student.getStudentID());  // Set student ID
                            stmt.setString(4, student.getStud_name());  // Set student first name
                            stmt.setString(5, student.getStud_surname());  // Set student last name
                            stmt.setString(6, groupName);  // Set the group name (e.g., "Grupe A" or "Grupe B")

                            stmt.executeUpdate();  // Execute the insert statement
                            System.out.println("Inserting student info for student ID " + student.getStudentID() + " in group " + groupName + " to dienynas.");
                        }
                    }
                }

                System.out.println("All students have been transferred to Dienynas.");
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } finally {
            // Close resources in reverse order of their opening
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (checkStmt != null) checkStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}

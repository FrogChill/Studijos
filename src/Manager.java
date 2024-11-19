import java.sql.*;
import java.util.ArrayList;

public class Manager {

    private Connection getConnection() throws SQLException {
        try {
            // Setup your database connection (ensure you have the correct JDBC URL, username, password)
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdatabase", "root", "password");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Database connection error");
        }
    }

    // Fetch all teachers from the database
    public ArrayList<Destytojai> getAllTeachers() {
        ArrayList<Destytojai> teachers = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM destytojai";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    teachers.add(new Destytojai(rs.getInt("destytojoID"), rs.getString("destytojo_vardas"), rs.getString("destytojo_pavarde")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    // Fetch all subjects from the database
    public ArrayList<Dalykas> getAllSubjects() {
        ArrayList<Dalykas> subjects = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM dalykas";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    subjects.add(new Dalykas(rs.getInt("idDalykas"), rs.getString("Dalyko_pavadinimas")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    // Add an existing subject to a teacher
    public boolean addTeacherToSubject(int teacherId, int subjectId, String subjectName) {
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO dalyko_paskirstymas (dalykoID, Dalyko_pavadinimas, destytojoID) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, subjectId);
                stmt.setString(2, subjectName);
                stmt.setInt(3, teacherId);

                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

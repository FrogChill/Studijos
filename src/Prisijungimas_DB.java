import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Prisijungimas_DB {

    private static Connection con = null;

    public static Connection prisijungimas_DB() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentu_sistema", "root", "MoleNumberUno!");
            System.out.println("Connected to the database.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }

        return con; // Return the connection object
    }
}
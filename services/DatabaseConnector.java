package services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/veterinaria_huellitas_final";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;
    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa");
            return connection;
        } catch (SQLException error) {
            System.out.println("Error al conectar a la base de datos: " +
                    error.getMessage());
            return null;
        }
    }
}
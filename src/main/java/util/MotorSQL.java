package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MotorSQL {
    // Aquí debes poner la URL de conexión correcta (localhost si lo tienes local, o el endpoint de tu RDS si lo tienes en la nube)
    private static final String URL = "jdbc:postgresql://postgres.cidzcgfuc16l.us-east-1.rds.amazonaws.com:5432/postgres";  // Cambia 'localhost' si estás usando un servidor diferente
    private static final String USER = "postgres";  // Tu usuario de PostgreSQL
    private static final String PASSWORD = "postgres1234";  // Tu contraseña de PostgreSQL

    //private static final String URL = "jdbc:postgresql://localhost:5432/postgres";  // Cambia 'localhost' si estás usando un servidor diferente
    //private static final String USER = "postgres";  // Tu usuario de PostgreSQL
    //private static final String PASSWORD = "1234";  // Tu contraseña de PostgreSQL

    public static Connection getConnection() throws SQLException {
        try {
            // Registra el driver de PostgreSQL (solo una vez)
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);  // Devuelve la conexión
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();  // En caso de error, imprimimos la excepción
            throw new SQLException("Error al conectar con la base de datos.");  // Lanza una excepción si hay error
        }
    }
}

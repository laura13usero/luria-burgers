package test;

import util.MotorSQL;  // Importamos la clase MotorSQL
import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        // Intentamos obtener la conexión
        try (Connection con = MotorSQL.getConnection()) {
            if (con != null) {
                System.out.println("✅ Conexión exitosa a la BBDD");
            } else {
                System.out.println("❌ No se pudo conectar a la BBDD");
            }
        } catch (Exception e) {
            // Si ocurre algún error en la conexión, lo imprimimos
            System.out.println("❌ Error al conectar a la BBDD: " + e.getMessage());
        }
    }
}

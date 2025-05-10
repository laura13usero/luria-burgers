package util;

import jakarta.servlet.ServletContext;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoUtils {

    // Método para encriptar la contraseña
    public static String encriptarContrasena(String contrasena) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // Usa un algoritmo de encriptación adecuado
            byte[] hashedBytes = md.digest(contrasena.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();  // Retorna la contraseña encriptada en formato hexadecimal
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }

    // Método para comparar la contraseña proporcionada con la almacenada (encriptada)
    public static boolean compararContrasena(String contrasenaProporcionada, String contrasenaAlmacenada) {
        System.out.println("Contraseña proporcionada (sin encriptar): " + contrasenaProporcionada);
        System.out.println("Contraseña almacenada (encriptada): " + contrasenaAlmacenada);

        String contrasenaEncriptada = encriptarContrasena(contrasenaProporcionada);

        System.out.println("== COMPARACIÓN DE CONTRASEÑAS ==");
        System.out.println("Contraseña proporcionada (en texto claro): " + contrasenaProporcionada);
        System.out.println("Contraseña proporcionada (encriptada): " + contrasenaEncriptada);
        System.out.println("Contraseña almacenada: " + contrasenaAlmacenada);
        System.out.println("¿Coinciden? " + contrasenaEncriptada.equals(contrasenaAlmacenada));


        return contrasenaEncriptada.equals(contrasenaAlmacenada);
    }
}

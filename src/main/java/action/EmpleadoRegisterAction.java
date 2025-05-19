package action;

import com.google.gson.JsonObject;
import dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;
import util.CryptoUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class EmpleadoRegisterAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        // Log parameters (CRUCIAL for debugging)
        System.out.println("EmpleadoRegisterAction - Parameters:");
        System.out.println("  nombre: " + request.getParameter("nombre"));
        System.out.println("  email: " + request.getParameter("email"));
        System.out.println("  contrasena: " + request.getParameter("contrasena"));
        System.out.println("  telefono: " + request.getParameter("telefono"));
        System.out.println("  direccion: " + request.getParameter("direccion"));

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");

        // Validation (Basic - Expand as needed)
        if (nombre == null || nombre.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                contrasena == null || contrasena.trim().isEmpty()) {
            response.setStatus(400);  // Bad Request
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Nombre, email, y contraseña son obligatorios.");
            out.print(jsonResponse.toString());
            out.flush();
            return;  // Important: Stop further processing
        }


        try {
            String contrasenaEncriptada = CryptoUtils.encriptarContrasena(contrasena);

            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setContrasena(contrasenaEncriptada);
            usuario.setTelefono(telefono);
            usuario.setFechaRegistro(LocalDateTime.now());
            usuario.setRol("empleado");

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.agregarEmpleado(usuario);

            jsonResponse.addProperty("status", "ok");
            jsonResponse.addProperty("message", "Registro de empleado completado");
            response.setStatus(200); // OK

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(500); // Internal Server Error
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Error de base de datos: " + e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500); // Internal Server Error
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Error inesperado: " + e.getMessage());

        } finally {
            out.print(jsonResponse.toString());
            out.flush();
        }
    }
}
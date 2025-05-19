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
        response.setCharacterEncoding("UTF-8"); // Important for UTF-8 compatibility
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");

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
            e.printStackTrace(); // Log the error!
            response.setStatus(500); // Internal Server Error
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Error de base de datos: " + e.getMessage());

        } catch (Exception e) {
            e.printStackTrace(); // Log the error!
            response.setStatus(500); // Internal Server Error
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Error inesperado: " + e.getMessage());

        } finally {
            out.print(jsonResponse.toString());
            out.flush();
        }
    }
}
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
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        // Obtener los datos del formulario
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");

        try {
            // Encriptar la contraseña
            String contrasenaEncriptada = CryptoUtils.encriptarContrasena(contrasena);

            // Crear el usuario
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setContrasena(contrasenaEncriptada);
            usuario.setTelefono(telefono);
            usuario.setFechaRegistro(LocalDateTime.now());
            usuario.setRol("empleado");  // Esto es solo informativo, no se usa en la inserción

            // Guardar en la base de datos
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.agregarEmpleado(usuario);

            jsonResponse.addProperty("status", "ok");
            jsonResponse.addProperty("message", "Registro de empleado completado");

        } catch (SQLException e) {
            e.printStackTrace();
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Error al registrar el empleado: " + e.getMessage());
            response.setStatus(500); // Internal Server Error
        }

        out.print(jsonResponse.toString());
        out.flush();
    }
}
package action;

import com.google.gson.Gson;
import dao.UsuarioDAO;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import model.Usuario;
import util.CryptoUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class UserRegisterAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Map<String, Object> result = new HashMap<>();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Leer JSON del body
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            Map<String, String> data = gson.fromJson(reader, Map.class);

            String nombre = data.get("nombre");
            String email = data.get("email");
            String contrasena = data.get("contrasena");
            String telefono = data.get("telefono");
            String direccion = data.get("direccion");

            String contrasenaEncriptada = CryptoUtils.encriptarContrasena(contrasena);

            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setContrasena(contrasenaEncriptada);
            usuario.setTelefono(telefono);
            usuario.setDireccion(direccion);
            usuario.setFechaRegistro(LocalDateTime.now());
            usuario.setRol("cliente");

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.agregarUsuario(usuario);

            result.put("status", "ok");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", e.getMessage());
        }

        String json = new Gson().toJson(result);
        response.getWriter().write(json);
    }
}

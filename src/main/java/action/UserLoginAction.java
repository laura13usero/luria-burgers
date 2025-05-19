package action;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Usuario;
import dao.UsuarioDAO;
import util.CryptoUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserLoginAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        Map<String, String> datos = gson.fromJson(reader, Map.class);

        String email = datos.get("email");
        String contrasena = datos.get("contrasena");
        String origen = datos.get("origen"); // Obtener el origen de la petición

        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.buscarPorEmail(email);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, String> resultado = new HashMap<>();

        if (usuario != null) {
            if (CryptoUtils.compararContrasena(contrasena, usuario.getContrasena())) {
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogueado", usuario);
                session.setAttribute("usuarioLogueadoNombre", usuario.getNombre()); // Guardar el nombre
                session.setAttribute("rol", usuario.getRol());
                session.setMaxInactiveInterval(30 * 60);

                resultado.put("status", "ok");
                resultado.put("nombre", usuario.getNombre()); // Send the name for potential display
            } else {
                resultado.put("status", "error");
                resultado.put("message", "Contraseña incorrecta");
            }
        } else {
            resultado.put("status", "error");
            resultado.put("message", "Email no registrado");
        }

        String json = gson.toJson(resultado);
        response.getWriter().write(json);
    }
}
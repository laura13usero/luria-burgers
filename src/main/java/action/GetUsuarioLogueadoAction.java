package action;

import com.google.gson.JsonObject;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import model.Usuario; // Asegúrate que la ruta a tu modelo Usuario sea correcta

public class GetUsuarioLogueadoAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogueado") : null;

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8"); // Es buena práctica añadir el encoding
        JsonObject json = new JsonObject();

        if (usuario != null) {
            json.addProperty("status", "ok");
            json.addProperty("nombre", usuario.getNombre()); // Asumiendo que Usuario tiene getNombre()
            json.addProperty("email", usuario.getEmail());    // AÑADIDO: Asumiendo que Usuario tiene getEmail()
            json.addProperty("rol", usuario.getRol());      // AÑADIDO: Asumiendo que Usuario tiene getRol()
            // Puedes añadir más datos si quieres, por ejemplo:
            // json.addProperty("apellido", usuario.getApellido());
        } else {
            json.addProperty("status", "error");
            json.addProperty("message", "Usuario no logueado");
        }

        PrintWriter out = response.getWriter();
        out.print(json.toString());
        out.flush();
    }
}
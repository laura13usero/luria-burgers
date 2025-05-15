package action;

import com.google.gson.JsonObject;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import model.Usuario;

public class GetUsuarioLogueadoAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogueado") : null;

        response.setContentType("application/json");
        JsonObject json = new JsonObject();

        if (usuario != null) {
            json.addProperty("status", "ok");
            json.addProperty("nombre", usuario.getNombre());
            // Puedes añadir más datos si quieres
        } else {
            json.addProperty("status", "error");
            json.addProperty("message", "Usuario no logueado");
        }

        PrintWriter out = response.getWriter();
        out.print(json.toString());
        out.flush();
    }
}
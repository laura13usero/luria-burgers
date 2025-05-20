
package action;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CheckSessionAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); // false: no crear nueva sesión si no existe
        Map<String, Object> resultado = new HashMap<>();
        Gson gson = new Gson();

        if (session != null && session.getAttribute("usuarioLogueado") != null) {
            resultado.put("loggedIn", true);
            // Opcional: Si necesitas el rol en el frontend, asegúrate de que el usuario tenga un método getRol()
            // resultado.put("rol", ((model.Usuario) session.getAttribute("usuarioLogueado")).getRol());
        } else {
            resultado.put("loggedIn", false);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(resultado));
    }
}
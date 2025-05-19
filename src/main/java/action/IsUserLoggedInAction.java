package action;

import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

public class IsUserLoggedInAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObject json = new JsonObject();

        HttpSession session = request.getSession(false); // Obtener la sesión sin crear una nueva
        if (session != null && session.getAttribute("usuarioLogueado") != null) {
            json.addProperty("loggedIn", true);
        } else {
            json.addProperty("loggedIn", false);
            json.addProperty("redirect", request.getContextPath() + "/necesitalogin.html");
        }

        out.print(json);
    }
}
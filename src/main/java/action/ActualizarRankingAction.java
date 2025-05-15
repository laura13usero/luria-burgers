package action;

import dao.HamburguesaDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ActualizarRankingAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        String usuario = request.getParameter("usuario");

        HamburguesaDAO hamburguesaDAO = new HamburguesaDAO();
        hamburguesaDAO.actualizarRankingHamburguesa(idProducto, usuario);

        // Enviar una respuesta JSON al cliente
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("status", "ok");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(jsonResponse));
    }
}
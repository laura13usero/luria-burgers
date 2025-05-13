package action;

import com.google.gson.Gson;
import dao.PostreDAO;
import model.Producto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GetPostresJSONAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        PostreDAO postreDAO = new PostreDAO();
        List<Producto> postres = postreDAO.obtenerPostres();

        // Convertimos la lista de postres en JSON
        String json = new Gson().toJson(postres);

        // Configuramos la respuesta como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}

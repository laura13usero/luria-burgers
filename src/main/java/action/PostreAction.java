package action;

import com.google.gson.Gson;
import dao.PostreDAO;
import model.Producto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PostreAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        PostreDAO postreDAO = new PostreDAO();
        List<Producto> postres = postreDAO.obtenerPostres();

        // Devolver los postres como JSON
        String json = new Gson().toJson(postres);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}

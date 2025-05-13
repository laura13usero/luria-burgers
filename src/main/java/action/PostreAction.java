package action;

import dao.PostreDAO;
import model.Producto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;

public class PostreAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        PostreDAO postreDAO = new PostreDAO();
        List<Producto> postres = postreDAO.obtenerPostres();

        request.setAttribute("postres", postres);

        try {
            request.getRequestDispatcher("/jsp/postres.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

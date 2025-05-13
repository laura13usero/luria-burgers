package action;

import dao.HamburguesaDAO;
import model.Producto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GetHamburguesasJSONAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String filtro = request.getParameter("filtro");

        HamburguesaDAO hamburguesaDAO = new HamburguesaDAO();
        List<Producto> hamburguesas;

        if (filtro != null && !filtro.trim().isEmpty()) {
            hamburguesas = hamburguesaDAO.obtenerHamburguesasPorFiltro(filtro);
        } else {
            hamburguesas = hamburguesaDAO.obtenerHamburguesas();
        }

        Gson gson = new Gson();
        String hamburguesasJson = gson.toJson(hamburguesas);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(hamburguesasJson);
    }
}

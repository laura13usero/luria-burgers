package action;

import dao.HamburguesaDAO;
import model.Producto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;

public class HamburguesasAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        HamburguesaDAO hamburguesaDAO = new HamburguesaDAO();
        List<Producto> hamburguesas = hamburguesaDAO.obtenerHamburguesas();

        // Almacenamos las hamburguesas en el request para pasarlas al JSP
        request.setAttribute("hamburguesas", hamburguesas);

        try {
            request.getRequestDispatcher("/jsp/hamburguesas.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

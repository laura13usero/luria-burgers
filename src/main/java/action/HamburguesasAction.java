// src/action/HamburguesasAction.java
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
        String filtro = request.getParameter("filtro");

        HamburguesaDAO hamburguesaDAO = new HamburguesaDAO();
        List<Producto> hamburguesas;

        if (filtro != null && !filtro.trim().isEmpty()) {
            hamburguesas = hamburguesaDAO.obtenerHamburguesasPorFiltro(filtro);
        } else {
            hamburguesas = hamburguesaDAO.obtenerHamburguesas();
        }

        request.setAttribute("hamburguesas", hamburguesas);

        try {
            request.getRequestDispatcher("/jsp/hamburguesas.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package action;

import dao.HamburguesaDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Producto;

import java.util.List;

public class FiltrarHamburguesasAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String filtro = request.getParameter("filtro"); // puede ser "", "premium", "spicy" o "pollo"
        HamburguesaDAO hamburguesaDAO = new HamburguesaDAO();
        List<Producto> hamburguesas;

        if (filtro == null || filtro.isEmpty()) {
            hamburguesas = hamburguesaDAO.obtenerHamburguesas();
        } else {
            hamburguesas = hamburguesaDAO.obtenerHamburguesasPorFiltro(filtro);
        }

        request.setAttribute("hamburguesas", hamburguesas);

        try {
            request.getRequestDispatcher("/jsp/hamburguesas.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

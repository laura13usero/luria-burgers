package action;

import dao.BebidaDAO;
import model.Producto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;

public class BebidasAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        BebidaDAO bebidaDAO = new BebidaDAO();
        List<Producto> bebidas = bebidaDAO.obtenerBebidas();

        request.setAttribute("bebidas", bebidas);

        try {
            request.getRequestDispatcher("/jsp/bebidas.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

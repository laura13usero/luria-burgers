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

        // Añadimos las bebidas a la solicitud
        request.setAttribute("bebidas", bebidas);

        try {
            // Redirigimos a la JSP de bebidas
            request.getRequestDispatcher("/bebidas.html").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//lalallaa
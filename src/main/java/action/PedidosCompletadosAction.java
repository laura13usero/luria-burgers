package action;

import dao.CompraDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Compra;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PedidosCompletadosAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        System.out.println("CARGANDO PEDIDOS COMPLETADOS...");

        CompraDAO compraDAO = new CompraDAO();
        List<Compra> pedidosCompletados = compraDAO.getComprasCompletadas();

        request.setAttribute("pedidosCompletados", pedidosCompletados);

        // Redirigimos al JSP que mostrará los pedidos
        request.getRequestDispatcher("/jsp/pedidosCompletados.jsp").forward(request, response);
    }
}

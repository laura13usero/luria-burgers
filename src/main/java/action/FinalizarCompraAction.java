package action;

import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import dao.CompraDAO;
import model.Usuario;

public class FinalizarCompraAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect("loginIntranet.jsp");
            return;
        }

        try {
            CompraDAO compraDAO = new CompraDAO();
            boolean exito = compraDAO.finalizarCompra(usuario.getIdUsuario());

            if (exito) {
                // Limpiar el carrito de la sesión
                session.removeAttribute("carrito");

                // Redirigir a página de confirmación
                response.sendRedirect("pagoConfirmado.jsp");
            } else {
                response.sendRedirect("errorPago.jsp");
            }

        } catch (SQLException e) {
            throw new ServletException("Error al finalizar la compra", e);
        }
    }
}

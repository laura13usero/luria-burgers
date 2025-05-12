// src/action/VotarHamburguesaAction.java
package action;

import dao.HamburguesaDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;

import java.io.IOException;
import java.sql.SQLException;

public class VotarHamburguesaAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        String idProductoStr = request.getParameter("idProducto");
        if (idProductoStr == null || idProductoStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/jsp/hamburguesas.jsp");
            return;
        }

        int idProducto = Integer.parseInt(idProductoStr);
        HamburguesaDAO hamburguesaDAO = new HamburguesaDAO();
        boolean votoExitoso = hamburguesaDAO.votarHamburguesa(idProducto, usuario.getIdUsuario());

        if (votoExitoso) {
            response.sendRedirect(request.getContextPath() + "/control?action=verHamburguesas");
        } else {
            request.setAttribute("error", "Ya has votado por esta hamburguesa.");
            try {
                request.getRequestDispatcher("/jsp/hamburguesas.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

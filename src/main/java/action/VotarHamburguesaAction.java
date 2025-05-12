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
        // Obtener el usuario logueado
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");

        // Verificar si el usuario está logueado
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        // Obtener el ID del producto (hamburguesa)
        String idProductoStr = request.getParameter("idProducto");
        if (idProductoStr == null || idProductoStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/jsp/hamburguesas.jsp");
            return;
        }

        int idProducto = Integer.parseInt(idProductoStr);

        // Llamar al DAO para agregar el voto
        HamburguesaDAO hamburguesaDAO = new HamburguesaDAO();
        boolean votoExitoso = hamburguesaDAO.votarHamburguesa(idProducto, usuario.getIdUsuario());

        if (votoExitoso) {
            // Si el voto fue exitoso, redirigimos a la página de hamburguesas
            response.sendRedirect(request.getContextPath() + "/jsp/hamburguesas.jsp");
        } else {
            // Si ya ha votado, redirigimos con un mensaje de error
            request.setAttribute("error", "Ya has votado por esta hamburguesa.");
            try {
                request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

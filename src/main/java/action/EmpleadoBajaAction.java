package action;

import dao.UsuarioDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

public class EmpleadoBajaAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int idEmpleado = Integer.parseInt(request.getParameter("id"));
        try {
            UsuarioDAO dao = new UsuarioDAO();
            dao.darDeBajaEmpleado(idEmpleado);
            request.setAttribute("status", "baja-ok");  // Indica que la baja fue exitosa
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("status", "baja-error");  // Si hay un error en la BD
            throw e;  // Relanzamos la excepción para que se maneje adecuadamente en un filtro o controlador
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("status", "baja-error");  // Si ocurre un error general
        }

        // Redirigimos a la página donde se muestra el estado
        request.getRequestDispatcher("jsp/registrarEmpleado.jsp").forward(request, response);
    }
}

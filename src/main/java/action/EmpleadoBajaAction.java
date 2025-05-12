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
            request.setAttribute("status", "baja-ok");

            // 🔥 Este era el que faltaba para actualizar la tabla
            request.setAttribute("empleados", dao.obtenerEmpleadosActivos());

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("status", "baja-error");
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("status", "baja-error");
        }

        request.getRequestDispatcher("jsp/registrarEmpleado.jsp").forward(request, response);
    }
}

package action;

import dao.UsuarioDAO;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import model.Usuario;

import java.util.List;

public class EmpleadoEditarAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");

        UsuarioDAO dao = new UsuarioDAO();
        boolean ok = dao.actualizarTelefonoYDireccion(id, telefono, direccion);

        request.setAttribute("status", ok ? "editar-ok" : "editar-error");

        // Volvemos a cargar los empleados actualizados
        List<Usuario> empleados = dao.obtenerUsuariosPorRol(3);
        request.setAttribute("empleados", empleados);
        request.getRequestDispatcher("/jsp/registrarEmpleado.jsp").forward(request, response);
    }
}

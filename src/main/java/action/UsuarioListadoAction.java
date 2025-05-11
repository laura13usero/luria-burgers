package action;

import dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UsuarioListadoAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        System.out.println("EMPLEADOS ENCONTRADOS:");


        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> empleados = usuarioDAO.obtenerUsuariosPorRol(3); // Solo empleados (rol 3)

        request.setAttribute("empleados", empleados);

        // Redirigimos al JSP que mostrará los empleados
        request.getRequestDispatcher("/jsp/registrarEmpleado.jsp").forward(request, response);
    }
}

package action;

import dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;

import java.io.IOException;
import java.util.List;

public class UsuarioListadoAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.obtenerTodos(); // usamos el método que acabas de adaptar

        request.setAttribute("usuarios", usuarios);

        // Redirigimos al JSP que mostrará los usuarios
        request.getRequestDispatcher("/jsp/usuarios.jsp").forward(request, response);
    }
}


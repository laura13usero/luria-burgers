package action;

import dao.UsuarioDAO;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import model.Usuario;
import util.CryptoUtils;

import java.io.IOException;

public class UserLoginAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");
        String origen = request.getParameter("origen");

        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.buscarPorEmail(email);

        if (usuario != null) {
            if (CryptoUtils.compararContrasena(contrasena, usuario.getContrasena())) {

                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogueado", usuario);
                session.setAttribute("rol", usuario.getRol());
                session.setMaxInactiveInterval(30 * 60);

                if ("intranet".equals(origen)) {
                    String rol = usuario.getRol();
                    if ("admin".equals(rol)) { //rol 2 es admin
                        response.sendRedirect(request.getContextPath() + "/jsp/adminintranet.jsp?login=ok");
                    } else if ("empleado".equals(rol)) { //rol 3 es empleado
                        response.sendRedirect(request.getContextPath() + "/jsp/empleadointranet.jsp?login=ok");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/jsp/accesoDenegado.jsp");
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/jsp/loginExitoso.jsp");
                }

            } else {
                request.setAttribute("error", "Contraseña incorrecta");
                request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Email no registrado");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        }
    }
}

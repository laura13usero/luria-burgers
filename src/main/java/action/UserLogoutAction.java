package action;

import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

public class UserLogoutAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Cerramos sesión
        }

        // Redirigimos al login
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
    }
}

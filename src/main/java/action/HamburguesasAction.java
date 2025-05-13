package action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HamburguesasAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Solo redirige a la vista de hamburguesas (HTML)
        response.sendRedirect(request.getContextPath() + "/hamburguesas.html");
    }
}

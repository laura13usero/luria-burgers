package action;

import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

public class UnknownAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Acción no reconocida.");
    }
}


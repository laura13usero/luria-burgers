package action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class HamburguesasAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        // Redirigir al HTML que usará fetch para pedir los datos
        response.sendRedirect("html/hamburguesas.html");
    }
}


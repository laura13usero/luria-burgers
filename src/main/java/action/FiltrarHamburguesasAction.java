package action;

import com.google.gson.Gson;
import dao.HamburguesaDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Producto;

import java.io.IOException;
import java.util.List;

public class FiltrarHamburguesasAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String filtro = request.getParameter("filtro");
        HamburguesaDAO hamburguesaDAO = new HamburguesaDAO();
        List<Producto> hamburguesas;

        if (filtro == null || filtro.isEmpty()) {
            hamburguesas = hamburguesaDAO.obtenerHamburguesas();
        } else {
            hamburguesas = hamburguesaDAO.obtenerHamburguesasPorFiltro(filtro);
        }

        // Convertir la lista de hamburguesas a JSON usando Gson
        Gson gson = new Gson();
        String jsonHamburguesas = gson.toJson(hamburguesas);

        // Configurar la respuesta para indicar que es JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Escribir el JSON en la respuesta
        try {
            response.getWriter().write(jsonHamburguesas);
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar el error adecuadamente (log, enviar error al cliente, etc.)
        }
    }
}
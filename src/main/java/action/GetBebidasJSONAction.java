package action;

import com.google.gson.Gson;
import dao.BebidaDAO;
import model.Producto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GetBebidasJSONAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        BebidaDAO bebidaDAO = new BebidaDAO();
        List<Producto> bebidas = bebidaDAO.obtenerBebidas();

        // Convertimos la lista de bebidas en JSON
        String json = new Gson().toJson(bebidas);

        // Configuramos la respuesta como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);


    }
}

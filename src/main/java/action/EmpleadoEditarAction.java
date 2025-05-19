package action;

import com.google.gson.JsonObject;
import dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class EmpleadoEditarAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        int id = Integer.parseInt(request.getParameter("id"));
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");

        UsuarioDAO dao = new UsuarioDAO();
        boolean ok = dao.actualizarTelefonoYDireccion(id, telefono, direccion);

        if (ok) {
            jsonResponse.addProperty("status", "ok");
            jsonResponse.addProperty("message", "Datos actualizados correctamente");
        } else {
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Error al actualizar los datos");
            response.setStatus(500); // Internal Server Error
        }

        out.print(jsonResponse.toString());
        out.flush();
    }
}
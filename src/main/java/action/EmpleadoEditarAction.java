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
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        int id = Integer.parseInt(request.getParameter("id"));
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");

        try {
            UsuarioDAO dao = new UsuarioDAO();
            boolean ok = dao.actualizarTelefonoYDireccion(id, telefono, direccion);

            if (ok) {
                jsonResponse.addProperty("status", "ok");
                jsonResponse.addProperty("message", "Datos actualizados correctamente");
                response.setStatus(200); // OK
            } else {
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", "Error al actualizar los datos");
                response.setStatus(500); // Internal Server Error
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(500); // Internal Server Error
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Error de base de datos: " + e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500); // Internal Server Error
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Error inesperado: " + e.getMessage());

        } finally {
            out.print(jsonResponse.toString());
            out.flush();
        }
    }
}
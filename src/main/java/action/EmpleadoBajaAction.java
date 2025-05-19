package action;

import com.google.gson.JsonObject;
import dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class EmpleadoBajaAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        int idEmpleado = Integer.parseInt(request.getParameter("id"));
        try {
            UsuarioDAO dao = new UsuarioDAO();
            boolean bajaExitosa = dao.darDeBajaEmpleado(idEmpleado);

            if (bajaExitosa) {
                jsonResponse.addProperty("status", "ok");
                jsonResponse.addProperty("message", "Empleado dado de baja correctamente");
            } else {
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", "Error al dar de baja al empleado");
                response.setStatus(500); // Internal Server Error
            }

        } catch (SQLException e) {
            e.printStackTrace();
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Error al dar de baja al empleado: " + e.getMessage());
            response.setStatus(500); // Internal Server Error
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Error al dar de baja al empleado: " + e.getMessage());
            response.setStatus(500); // Internal Server Error
        }

        out.print(jsonResponse.toString());
        out.flush();
    }
}
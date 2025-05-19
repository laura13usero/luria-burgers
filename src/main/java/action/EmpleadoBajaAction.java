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
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        int idEmpleado = Integer.parseInt(request.getParameter("id"));
        try {
            UsuarioDAO dao = new UsuarioDAO();
            boolean bajaExitosa = dao.darDeBajaEmpleado(idEmpleado);

            if (bajaExitosa) {
                jsonResponse.addProperty("status", "ok");
                jsonResponse.addProperty("message", "Empleado dado de baja correctamente");
                response.setStatus(200); // OK
            } else {
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", "Error al dar de baja al empleado");
                response.setStatus(500); // Internal Server Error
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(500); // Internal Server Error
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Error al dar de baja al empleado: " + e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500); // Internal Server Error
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Error al dar de baja al empleado: " + e.getMessage());

        } finally {
            out.print(jsonResponse.toString());
            out.flush();
        }
    }
}
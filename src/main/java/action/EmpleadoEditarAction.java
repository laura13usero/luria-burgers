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

        // Log parameters
        System.out.println("EmpleadoEditarAction - Parameters:");
        System.out.println("  id: " + request.getParameter("id"));
        System.out.println("  telefono: " + request.getParameter("telefono"));
        System.out.println("  direccion: " + request.getParameter("direccion"));

        int id;
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");

        // Validation
        if (telefono == null || direccion == null || request.getParameter("id") == null) {
            response.setStatus(400);
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Faltan datos para la actualización.");
            out.print(jsonResponse.toString());
            out.flush();
            return;
        }

        try {
            id = Integer.parseInt(request.getParameter("id"));
            UsuarioDAO dao = new UsuarioDAO();
            boolean ok = dao.actualizarTelefonoYDireccion(id, telefono, direccion);

            if (ok) {
                jsonResponse.addProperty("status", "ok");
                jsonResponse.addProperty("message", "Datos actualizados correctamente");
                response.setStatus(200);
            } else {
                response.setStatus(500);
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", "Error al actualizar los datos");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.setStatus(400);
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "ID de usuario inválido.");

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(500);
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Error de base de datos: " + e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Error inesperado: " + e.getMessage());

        } finally {
            out.print(jsonResponse.toString());
            out.flush();
        }
    }
}
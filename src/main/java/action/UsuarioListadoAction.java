package action;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioListadoAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {
            List<Usuario> empleados = usuarioDAO.obtenerUsuariosPorRol(3); // Solo empleados (rol 3)
            Gson gson = new Gson();
            String empleadosJson = gson.toJson(empleados.stream().map(u -> {
                JsonObject empJson = new JsonObject();
                empJson.addProperty("idUsuario", u.getIdUsuario());
                empJson.addProperty("nombre", u.getNombre());
                empJson.addProperty("email", u.getEmail());
                empJson.addProperty("telefono", u.getTelefono());
                empJson.addProperty("direccion", u.getDireccion());
                empJson.addProperty("fechaRegistro", u.getFechaRegistro().toString());
                return empJson;
            }).collect(Collectors.toList()));

            jsonResponse.addProperty("status", "ok");
            jsonResponse.add("empleados", gson.fromJson(empleadosJson, com.google.gson.JsonArray.class));

        } catch (SQLException e) {
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Error al obtener la lista de empleados: " + e.getMessage());
            response.setStatus(500); // Internal Server Error
            e.printStackTrace();
        }

        out.print(jsonResponse.toString());
        out.flush();
    }
}
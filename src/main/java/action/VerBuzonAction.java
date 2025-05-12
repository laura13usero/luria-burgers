package action;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.MotorSQL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VerBuzonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String[]> mensajes = new ArrayList<>();

        String sql = "SELECT tipo, mensaje, fecha FROM buzon ORDER BY fecha DESC";  // Trae los mensajes más recientes primero

        try (Connection conn = MotorSQL.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                String mensaje = rs.getString("mensaje");
                String fecha = rs.getString("fecha");
                mensajes.add(new String[]{tipo, mensaje, fecha});
            }

            request.setAttribute("mensajes", mensajes);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/verBuzon.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al obtener los mensajes. Intenta más tarde.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }
}

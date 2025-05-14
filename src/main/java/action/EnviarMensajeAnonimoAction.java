package action;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.MotorSQL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class EnviarMensajeAnonimoAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipo = request.getParameter("tipo");
        String mensaje = request.getParameter("mensaje");

        String sql = "INSERT INTO buzon (tipo, mensaje) VALUES (?, ?)";

        try (Connection conn = MotorSQL.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, tipo);
            pst.setString(2, mensaje);
            pst.executeUpdate();

            // Redirige a JSP de agradecimiento
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/agradecimiento.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al enviar el mensaje. Intenta más tarde.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }
}

//lallalalalalla
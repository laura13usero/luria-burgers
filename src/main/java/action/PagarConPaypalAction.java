package action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Producto;

import java.io.IOException;
import java.util.List;

public class PagarConPaypalAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("carrito") == null) {
            response.sendRedirect("jsp/carrito.jsp");
            return;
        }

        List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");

        if (carrito.isEmpty()) {
            response.sendRedirect("jsp/carrito.jsp");
            return;
        }

        // Aquí puedes hacer validaciones o cálculos adicionales si quieres

        // De momento, redirige a una página JSP con el botón de PayPal
        response.sendRedirect("jsp/pagoPaypal.jsp");
    }
}

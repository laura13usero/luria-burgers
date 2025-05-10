package action;

import dao.CompraDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;
import model.Compra;
import model.Hamburguesa;
import model.Producto;
import model.Usuario;

import java.io.IOException;
import java.util.List;

public class ViewCartAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("/webapp-1.0-SNAPSHOT/jsp/necesitalogin.jsp");
        } else {
            // Obtener el carrito desde la sesión
            List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");


            CompraDAO compraDAO = new CompraDAO();
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            Compra compraActiva = compraDAO.getCompraActivaPorUsuario(usuario);

            if (compraActiva != null) {
                // 2. Obtener los productos de esa compra
                carrito = compraDAO.obtenerProductosDeCompra(compraActiva.getIdCompra());

                // 3. Guardar el carrito en la sesión
                session.setAttribute("carrito", carrito);
            } else {
                // Si no hay compra activa, podemos crear una nueva compra o manejarlo de otra manera
                System.out.println("No se encontró compra activa para este usuario.");
            }


            double total = 0.0;

            if (carrito != null) {
                for (Producto p : carrito) {
                    if (p != null) {
                        total += p.getPrecio(); // Evita null y suma bien
                    }
                }
            }

            // Solo pasamos el total como atributo temporal
            request.setAttribute("total", total);

            // El carrito ya lo tiene la sesión, no es necesario volverlo a setear
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/carrito.jsp");
            dispatcher.forward(request, response);
        }
    }
}

package action;

import dao.CompraDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;
import model.Compra;
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

            if (request.getParameter("operation") != null) {
                String operation = request.getParameter("operation");
                int index = Integer.parseInt(request.getParameter("index"));
                Producto producto = carrito.get(index);

                if ("increment".equals(operation)) {
                    carrito.add(producto); // Aumentar la cantidad
                } else if ("decrement".equals(operation)) {
                    if (carrito.contains(producto)) {
                        carrito.remove(producto); // Disminuir la cantidad
                    }
                }

                // Actualizar la sesión del carrito y recalcular el total
                session.setAttribute("carrito", carrito);
                double total = calcularTotalCarrito(carrito); // Método para recalcular el total
                request.setAttribute("total", total);

                // Actualizar las tablas en la base de datos
                CompraDAO compraDAO = new CompraDAO();
                Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
                compraDAO.actualizarLineaCompra(usuario, producto.getId(), carrito.size(), producto.getPrecio());

                RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/carrito.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    // Método para recalcular el total del carrito
    private double calcularTotalCarrito(List<Producto> carrito) {
        double total = 0;
        for (Producto producto : carrito) {
            total += producto.getPrecio();
        }
        return total;
    }
}

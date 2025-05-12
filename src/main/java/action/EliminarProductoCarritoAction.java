package action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dao.CompraDAO;
import model.Compra;
import model.Hamburguesa;
import model.Producto;
import model.Usuario;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class EliminarProductoCarritoAction implements Action {
    @Override
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");

        // Verificamos si el usuario está logueado
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            // Si no está logueado, redirigimos a la página de login
            response.sendRedirect(request.getContextPath() + "/jsp/necesitalogin.jsp");
            return;
        }

        if (carrito != null) {
            int index = Integer.parseInt(request.getParameter("index"));

            if (index >= 0 && index < carrito.size()) {
                Producto producto = carrito.get(index);

                // Eliminamos el producto de la base de datos
                CompraDAO compraDAO = new CompraDAO();
                boolean eliminadoDeBBDD = compraDAO.eliminarLineaCompra(usuario, producto.getId());

                // Recuperamos la compra activa para obtener el total antes de la eliminación
                Compra compra = compraDAO.getCompraActivaPorUsuario(usuario);

                // Verificamos si el producto tiene una línea de compra asociada
                BigDecimal subtotalProductoEliminado = compraDAO.obtenerSubtotalLineaCompra(compra.getIdCompra(), producto.getId());

                // Restamos el subtotal del producto eliminado del total de la compra
                BigDecimal nuevoTotal = compra.getTotal().subtract(subtotalProductoEliminado);
                boolean totalActualizado = compraDAO.actualizarTotalCompra(compra.getIdCompra(), nuevoTotal);

                if (eliminadoDeBBDD) {
                    // Eliminamos el producto del carrito en la sesión
                    carrito.remove(index);
                    session.setAttribute("carrito", carrito);  // Actualizamos el carrito en la sesión
                } else {
                    // Si no se pudo eliminar de la base de datos, mostramos un error
                    request.setAttribute("error", "No se pudo eliminar el producto de la base de datos.");
                }
            }
        }

        // Redirigimos de vuelta al carrito
        response.sendRedirect(request.getContextPath() + "/control?action=verCarrito");
    }

}


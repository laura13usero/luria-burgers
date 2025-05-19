package dao;

import model.Compra;
import model.Producto;
import model.Usuario;
import util.MotorSQL;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO {

    public boolean finalizarCompra(int idUsuario) throws SQLException {
        String sql = "UPDATE compra SET estado = 'completado', fecha = current_timestamp WHERE id_usuario = ? AND estado = 'pendiente'";
        try (Connection con = MotorSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }


    // Método para obtener la compra pendiente de un usuario
    public Compra getCompraActivaPorUsuario(Usuario usuario) {
        String query = "SELECT * FROM compra WHERE id_usuario = ? AND estado = 'pendiente'";
        try (Connection connection = MotorSQL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, usuario.getIdUsuario());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("🟢 Compra encontrada para el usuario: " + usuario.getIdUsuario());
                return new Compra(rs.getInt("id_compra"),
                        rs.getInt("id_usuario"),
                        rs.getTimestamp("fecha_hora"),
                        rs.getString("metodo_pago"),
                        rs.getBigDecimal("total"));
            } else {
                System.out.println("🔴 No se encontró compra activa.");
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Aquí debería usar un sistema de logging adecuado
        }
        return null;
    }

    // Método para obtener todas las compras completadas
    public List<Compra> getComprasCompletadas() {
        List<Compra> compras = new ArrayList<>();
        String query = "SELECT * FROM compra WHERE estado = 'completado' ORDER BY fecha_hora DESC";

        try (Connection connection = MotorSQL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                compras.add(new Compra(
                        rs.getInt("id_compra"),
                        rs.getInt("id_usuario"),
                        rs.getTimestamp("fecha_hora"),
                        rs.getString("metodo_pago"),
                        rs.getBigDecimal("total")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Sustituir por logger si tienes uno
        }

        return compras;
    }




    // Método para crear una nueva compra pendiente
    public Compra crearCompra(Usuario usuario) {
        String sql = "INSERT INTO compra (id_usuario) VALUES (?) RETURNING id_compra";
        System.out.println("🚀 Intentando crear una compra para el usuario: " + usuario.getIdUsuario());

        try (Connection conn = MotorSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuario.getIdUsuario());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idCompra = rs.getInt("id_compra");
                System.out.println("🟢 Compra creada con ID: " + idCompra);  // Verificación de la creación
                return new Compra(idCompra, usuario.getIdUsuario(), null, null, BigDecimal.ZERO);
            } else {
                System.out.println("🔴 No se generó ningún ID de compra.");
            }

        } catch (SQLException e) {
            System.out.println("🔴 Error al crear la compra:");
            e.printStackTrace();
        }

        return null;
    }

    // Método para actualizar el total de la compra
    public boolean actualizarTotalCompra(int idCompra, BigDecimal nuevoTotal) {
        String sql = "UPDATE compra SET total = ? WHERE id_compra = ?";

        try (Connection conn = MotorSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBigDecimal(1, nuevoTotal);
            stmt.setInt(2, idCompra);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("🟢 Total de la compra actualizado correctamente.");
                return true;
            } else {
                System.out.println("🔴 No se actualizó el total de la compra.");
            }
        } catch (SQLException e) {
            System.out.println("🔴 Error al actualizar el total de la compra:");
            e.printStackTrace();
        }
        return false;
    }

    // Método para añadir o actualizar una línea de compra (producto) en el carrito
    public boolean agregarLineaCompra(Usuario usuario, int idProducto, String tipo_producto, int cantidad, BigDecimal subtotal) {
        Compra compra = getCompraActivaPorUsuario(usuario);
        if (compra == null) {
            compra = crearCompra(usuario);
        }

        if (compra == null) return false;

        String checkQuery = "SELECT cantidad, subtotal FROM lineacompra WHERE id_compra = ? AND id_producto = ?";
        try (Connection connection = MotorSQL.getConnection();
             PreparedStatement stmtCheck = connection.prepareStatement(checkQuery)) {

            stmtCheck.setInt(1, compra.getIdCompra());
            stmtCheck.setInt(2, idProducto);
            ResultSet rs = stmtCheck.executeQuery();

            if (rs.next()) {
                int nuevaCantidad = rs.getInt("cantidad") + cantidad;
                BigDecimal nuevoSubtotal = rs.getBigDecimal("subtotal").add(subtotal);

                String updateQuery = "UPDATE lineacompra SET cantidad = ?, subtotal = ? WHERE id_compra = ? AND id_producto = ?";
                try (PreparedStatement stmtUpdate = connection.prepareStatement(updateQuery)) {
                    stmtUpdate.setInt(1, nuevaCantidad);
                    stmtUpdate.setBigDecimal(2, nuevoSubtotal);
                    stmtUpdate.setInt(3, compra.getIdCompra());
                    stmtUpdate.setInt(4, idProducto);

                    int rowsAffected = stmtUpdate.executeUpdate();
                    if (rowsAffected > 0) {
                        actualizarTotalCompra(compra.getIdCompra(), calcularTotalCompra(compra.getIdCompra()));
                        return true;
                    }
                }
            } else {
                String insertQuery = "INSERT INTO lineacompra (id_compra, tipo_producto, id_producto, cantidad, subtotal) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement stmtInsert = connection.prepareStatement(insertQuery)) {
                    stmtInsert.setInt(1, compra.getIdCompra());
                    stmtInsert.setString(2, tipo_producto);
                    stmtInsert.setInt(3, idProducto);
                    stmtInsert.setInt(4, cantidad);
                    stmtInsert.setBigDecimal(5, subtotal);

                    int rowsAffected = stmtInsert.executeUpdate();
                    if (rowsAffected > 0) {
                        actualizarTotalCompra(compra.getIdCompra(), calcularTotalCompra(compra.getIdCompra()));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



    // Método para añadir una línea de compra (producto) al carrito
    /*public boolean agregarLineaCompra(Usuario usuario, int idProducto, String tipo_producto, int cantidad, BigDecimal subtotal) {
        // Primero, obtenemos la compra pendiente del usuario
        Compra compra = getCompraActivaPorUsuario(usuario);
        if (compra == null) {
            // Si no existe una compra pendiente, creamos una nueva
            compra = crearCompra(usuario);
        }

        if (compra == null) {
            return false;  // Si no pudimos crear la compra, devolvemos false
        }

        // Insertamos la línea de compra
        String query = "INSERT INTO lineacompra (id_compra, tipo_producto, id_producto, cantidad, subtotal) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = MotorSQL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, compra.getIdCompra());  // Usamos el id_compra de la compra recién creada o existente
            stmt.setString(2, tipo_producto);
            stmt.setInt(3, idProducto);
            stmt.setInt(4, cantidad);
            stmt.setBigDecimal(5, subtotal);  // Usamos BigDecimal para el subtotal

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }*/

    public boolean eliminarLineaCompra(Usuario usuario, int idProducto) {
        Compra compra = getCompraActivaPorUsuario(usuario);
        if (compra == null) return false;

        String selectQuery = "SELECT cantidad, subtotal FROM lineacompra WHERE id_compra = ? AND id_producto = ?";
        try (Connection conn = MotorSQL.getConnection();
             PreparedStatement stmtSelect = conn.prepareStatement(selectQuery)) {

            stmtSelect.setInt(1, compra.getIdCompra());
            stmtSelect.setInt(2, idProducto);
            ResultSet rs = stmtSelect.executeQuery();

            if (rs.next()) {
                int cantidadActual = rs.getInt("cantidad");
                BigDecimal subtotalActual = rs.getBigDecimal("subtotal");
                BigDecimal precioUnitario = subtotalActual.divide(BigDecimal.valueOf(cantidadActual), 2, RoundingMode.HALF_UP);


                if (cantidadActual > 1) {
                    int nuevaCantidad = cantidadActual - 1;
                    BigDecimal nuevoSubtotal = subtotalActual.subtract(precioUnitario);

                    String updateQuery = "UPDATE lineacompra SET cantidad = ?, subtotal = ? WHERE id_compra = ? AND id_producto = ?";
                    try (PreparedStatement stmtUpdate = conn.prepareStatement(updateQuery)) {
                        stmtUpdate.setInt(1, nuevaCantidad);
                        stmtUpdate.setBigDecimal(2, nuevoSubtotal);
                        stmtUpdate.setInt(3, compra.getIdCompra());
                        stmtUpdate.setInt(4, idProducto);
                        stmtUpdate.executeUpdate();
                    }
                } else {
                    String deleteQuery = "DELETE FROM lineacompra WHERE id_compra = ? AND id_producto = ?";
                    try (PreparedStatement stmtDelete = conn.prepareStatement(deleteQuery)) {
                        stmtDelete.setInt(1, compra.getIdCompra());
                        stmtDelete.setInt(2, idProducto);
                        stmtDelete.executeUpdate();
                    }
                }

                // Actualizar el total de la compra tras eliminar
                actualizarTotalCompra(compra.getIdCompra(), calcularTotalCompra(compra.getIdCompra()));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



    public List<Producto> obtenerProductosDeCompra(int idCompra) {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT p.id_producto, p.nombre, p.descripcion, p.precio, lc.cantidad " +
                "FROM Productos p " +
                "JOIN lineacompra lc ON p.id_producto = lc.id_producto " +
                "WHERE lc.id_compra = ?";
        try (Connection connection = MotorSQL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, idCompra);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                // Si la cantidad está en la tabla línea de compra
                int cantidad = rs.getInt("cantidad");
                // Lógica para agregar los productos al carrito, según la cantidad
                for (int i = 0; i < cantidad; i++) {
                    productos.add(producto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Aquí debería usar un sistema de logging adecuado
        }
        return productos;
    }

    public BigDecimal calcularTotalCompra(int idCompra) {
        String query = "SELECT SUM(subtotal) FROM lineacompra WHERE id_compra = ?";
        try (Connection conn = MotorSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idCompra);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal(1) != null ? rs.getBigDecimal(1) : BigDecimal.ZERO;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }


}


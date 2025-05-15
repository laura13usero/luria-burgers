package dao;

import model.Producto;
import util.MotorSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductosDAO {

    // Método para obtener productos de una categoría específica
    public List<Producto> obtenerProductosPorCategoria(String categoria) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, precio, categoria, enlace_html, imagen_png FROM Productos WHERE categoria = ?";

        try (Connection conn = MotorSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Producto p = new Producto();
                    p.setId(rs.getInt("id_producto"));
                    p.setNombre(rs.getString("nombre"));
                    p.setPrecio(rs.getDouble("precio"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setEnlace_html(rs.getString("enlace_html"));
                    p.setImagen_png(rs.getString("imagen_png"));
                    productos.add(p);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return productos;
    }

    // Método para obtener todos los productos
    public List<Producto> obtenerTodosLosProductos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, precio, categoria, enlace_html, imagen_png FROM Productos";

        try (Connection conn = MotorSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setCategoria(rs.getString("categoria"));
                p.setEnlace_html(rs.getString("enlace_html"));
                p.setImagen_png(rs.getString("imagen_png"));
                productos.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return productos;
    }
}

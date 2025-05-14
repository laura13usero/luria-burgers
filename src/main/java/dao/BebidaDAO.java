package dao;

import model.Producto;
import util.MotorSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BebidaDAO {

    public List<Producto> obtenerBebidas() {
        List<Producto> bebidas = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, descripcion, precio, imagen_png FROM Productos WHERE categoria ILIKE 'Bebidas'";

        try (Connection conn = MotorSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setImagen_png(rs.getString("imagen_png")); // Añadido
                bebidas.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bebidas;
    }
}
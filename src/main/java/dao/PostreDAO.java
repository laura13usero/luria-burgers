package dao;

import model.Producto;
import util.MotorSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PostreDAO {

    public List<Producto> obtenerPostres() {
        List<Producto> postres = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, descripcion, precio FROM Productos WHERE categoria ILIKE 'Postres'";

        try (Connection conn = MotorSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                postres.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return postres;
    }
}


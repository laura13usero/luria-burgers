package dao;

import model.Producto;
import util.MotorSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HamburguesaDAO {



    public List<Producto> obtenerHamburguesas() {
        List<Producto> hamburguesas = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, descripcion, precio, filtros FROM Productos WHERE categoria ILIKE 'Burger'";

        try (Connection conn = MotorSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setFiltros(rs.getString("filtros"));  // Aquí asignamos el filtro
                hamburguesas.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hamburguesas;
    }


    public List<Producto> obtenerHamburguesasPorFiltro(String filtro) {
        List<Producto> hamburguesasFiltradas = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, descripcion, precio, filtros " +
                "FROM Productos " +
                "WHERE categoria ILIKE 'Burger' AND filtros ILIKE ?";

        try (Connection conn = MotorSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + filtro + "%"); // Ej: %spicy%

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Producto p = new Producto();
                    p.setId(rs.getInt("id_producto"));
                    p.setNombre(rs.getString("nombre"));
                    p.setDescripcion(rs.getString("descripcion"));
                    p.setPrecio(rs.getDouble("precio"));
                    p.setFiltros(rs.getString("filtros"));
                    hamburguesasFiltradas.add(p);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hamburguesasFiltradas;
    }


}

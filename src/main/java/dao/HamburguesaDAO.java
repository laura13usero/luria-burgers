package dao;

import model.Producto;
import util.MotorSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HamburguesaDAO {

    public List<Producto> obtenerHamburguesas() {
        List<Producto> hamburguesas = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, precio, filtros, imagen_png, enlace_html, ranking FROM Productos WHERE categoria ILIKE 'Burger'";

        try (Connection conn = MotorSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setFiltros(rs.getString("filtros"));
                p.setImagen_png(rs.getString("imagen_png"));
                p.setEnlace_html(rs.getString("enlace_html"));
                p.setRanking(convertStringToList(rs.getString("ranking")));
                hamburguesas.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hamburguesas;
    }

    public List<Producto> obtenerHamburguesasPorFiltro(String filtro) {
        List<Producto> hamburguesasFiltradas = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, precio, filtros, imagen_png, enlace_html, ranking " +
                "FROM Productos " +
                "WHERE categoria ILIKE 'Burger' AND filtros ILIKE ?";

        try (Connection conn = MotorSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + filtro + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Producto p = new Producto();
                    p.setId(rs.getInt("id_producto"));
                    p.setNombre(rs.getString("nombre"));
                    p.setPrecio(rs.getDouble("precio"));
                    p.setFiltros(rs.getString("filtros"));
                    p.setImagen_png(rs.getString("imagen_png"));
                    p.setEnlace_html(rs.getString("enlace_html"));
                    p.setRanking(convertStringToList(rs.getString("ranking")));
                    hamburguesasFiltradas.add(p);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hamburguesasFiltradas;
    }

    private List<String> convertStringToList(String rankingString) {
        if (rankingString != null && !rankingString.isEmpty()) {
            return Arrays.asList(rankingString.split(","));
        } else {
            return new ArrayList<>();
        }
    }

    public void actualizarRankingHamburguesa(int idProducto, String nuevoUsuario) {
        String sql = "UPDATE Productos SET ranking = array_append(ranking, ?) WHERE id_producto = ?";

        try (Connection conn = MotorSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoUsuario);
            stmt.setInt(2, idProducto);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
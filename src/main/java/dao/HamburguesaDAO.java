package dao;

import model.Producto;
import util.MotorSQL;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HamburguesaDAO {

    public boolean votarHamburguesa(int idProducto, int idUsuario) {
        String sql = "UPDATE Productos SET ranking = array_append(ranking, ?) WHERE id_producto = ? AND NOT ranking @> array[?]";
        try (Connection conn = MotorSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Convertir el ID del usuario a String para agregarlo al array
            stmt.setString(1, String.valueOf(idUsuario));
            stmt.setInt(2, idProducto);
            stmt.setString(3, String.valueOf(idUsuario));

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int obtenerCantidadVotos(int idProducto) {
        String sql = "SELECT array_length(ranking, 1) FROM Productos WHERE id_producto = ?";
        try (Connection conn = MotorSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProducto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }



    public List<Producto> obtenerHamburguesas() {
        List<Producto> hamburguesas = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, descripcion, precio, filtros, ranking FROM Productos WHERE categoria ILIKE 'Burger'";

        try (Connection conn = MotorSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setFiltros(rs.getString("filtros"));

                // Mapear el array ranking
                Array sqlArray = rs.getArray("ranking");
                if (sqlArray != null) {
                    String[] rankingArray = (String[]) sqlArray.getArray();
                    p.setRanking(Arrays.asList(rankingArray));
                }

                hamburguesas.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hamburguesas;
    }



    public List<Producto> obtenerHamburguesasPorFiltro(String filtro) {
        List<Producto> hamburguesasFiltradas = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, descripcion, precio, filtros, ranking " +
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

                    Array sqlArray = rs.getArray("ranking");
                    if (sqlArray != null) {
                        String[] rankingArray = (String[]) sqlArray.getArray();
                        p.setRanking(Arrays.asList(rankingArray));
                    }

                    hamburguesasFiltradas.add(p);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hamburguesasFiltradas;
    }



}

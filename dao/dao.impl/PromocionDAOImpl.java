package dao.impl;

import dao.PromocionDAO;
import models.PromocionModel;
import services.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PromocionDAOImpl implements PromocionDAO {
    @Override
    public boolean insertar(PromocionModel promocion) {
        String sql =
                "INSERT INTO promocion " +
                        "(nombre, descuento, inicio_descuento, fin_descuento, cantidad_minima) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, promocion.getNombre());
                ps.setDouble(2, promocion.getDescuento());
                ps.setString(3, promocion.getInicioDescuento());
                ps.setString(4, promocion.getFinDescuento());
                ps.setInt(5, promocion.getCantidadMinima());

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException error) {
            System.out.println("Error al registrar promoción: " + error.getMessage());
            return false;
        }
    }

    @Override
    public List<PromocionModel> listarTodos() {
        List<PromocionModel> promociones = new ArrayList<>();
        String sql =
                "SELECT id_promocion, nombre, descuento, inicio_descuento, " +
                        "fin_descuento, cantidad_minima " +
                        "FROM promocion ORDER BY id_promocion";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return promociones;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    promociones.add(mapearPromocion(rs));
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al listar promociones: " + error.getMessage());
        }

        return promociones;
    }

    @Override
    public PromocionModel buscarPorId(int idPromocion) {
        String sql =
                "SELECT id_promocion, nombre, descuento, inicio_descuento, " +
                        "fin_descuento, cantidad_minima " +
                        "FROM promocion WHERE id_promocion = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return null;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idPromocion);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapearPromocion(rs);
                    }
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al buscar promoción: " + error.getMessage());
        }

        return null;
    }

    @Override
    public PromocionModel buscarVigentePorCantidad(int cantidad) {
        String sql =
                "SELECT id_promocion, nombre, descuento, inicio_descuento, " +
                        "fin_descuento, cantidad_minima " +
                        "FROM promocion " +
                        "WHERE CURDATE() BETWEEN inicio_descuento AND fin_descuento " +
                        "AND cantidad_minima <= ? " +
                        "ORDER BY descuento DESC LIMIT 1";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return null;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, cantidad);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapearPromocion(rs);
                    }
                }
            }

        } catch (SQLException error) {
            System.out.println(
                    "Error al buscar promoción vigente: " + error.getMessage()
            );
        }

        return null;
    }

    @Override
    public boolean actualizar(PromocionModel promocion) {
        String sql =
                "UPDATE promocion SET nombre = ?, descuento = ?, " +
                        "inicio_descuento = ?, fin_descuento = ?, cantidad_minima = ? " +
                        "WHERE id_promocion = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, promocion.getNombre());
                ps.setDouble(2, promocion.getDescuento());
                ps.setString(3, promocion.getInicioDescuento());
                ps.setString(4, promocion.getFinDescuento());
                ps.setInt(5, promocion.getCantidadMinima());
                ps.setInt(6, promocion.getIdPromocion());

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException error) {
            System.out.println("Error al actualizar promoción: " + error.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int idPromocion) {
        String sql = "DELETE FROM promocion WHERE id_promocion = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idPromocion);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException error) {
            System.out.println("Error al eliminar promoción: " + error.getMessage());
            return false;
        }
    }

    private PromocionModel mapearPromocion(ResultSet rs) throws SQLException {
        return new PromocionModel(
                rs.getInt("id_promocion"),
                rs.getString("nombre"),
                rs.getDouble("descuento"),
                rs.getString("inicio_descuento"),
                rs.getString("fin_descuento"),
                rs.getInt("cantidad_minima")
        );
    }
}

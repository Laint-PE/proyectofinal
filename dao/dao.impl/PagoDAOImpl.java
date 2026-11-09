package dao.impl;

import dao.PagoDAO;
import models.PagoModel;
import services.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PagoDAOImpl implements PagoDAO {

    @Override
    public PagoModel buscarPorPedido(int idPedido) {
        String sql =
                "SELECT id_pago, id_pedido, metodo_pago, monto, fecha_pago, " +
                        "estado_pago, monto_recibido, vuelto " +
                        "FROM pago WHERE id_pedido = ? ORDER BY id_pago DESC LIMIT 1";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return null;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idPedido);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapearPago(rs);
                    }
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al buscar el pago: " + error.getMessage());
        }

        return null;
    }

    @Override
    public List<PagoModel> listarTodos() {
        List<PagoModel> pagos = new ArrayList<>();
        String sql =
                "SELECT id_pago, id_pedido, metodo_pago, monto, fecha_pago, " +
                        "estado_pago, monto_recibido, vuelto " +
                        "FROM pago ORDER BY fecha_pago DESC";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return pagos;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    pagos.add(mapearPago(rs));
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al listar pagos: " + error.getMessage());
        }

        return pagos;
    }

    private PagoModel mapearPago(ResultSet rs) throws SQLException {
        return new PagoModel(
                rs.getInt("id_pago"),
                rs.getInt("id_pedido"),
                rs.getString("metodo_pago"),
                rs.getDouble("monto"),
                rs.getString("fecha_pago"),
                rs.getString("estado_pago"),
                rs.getDouble("monto_recibido"),
                rs.getDouble("vuelto")
        );
    }
}
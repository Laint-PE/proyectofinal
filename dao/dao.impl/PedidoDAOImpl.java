package dao.impl;

import dao.PedidoDAO;
import models.DetallePedidoModel;
import models.PagoModel;
import models.PedidoModel;
import services.DatabaseConnector;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {

    @Override
    public boolean registrarVenta(
            PedidoModel pedido,
            List<DetallePedidoModel> detalles,
            PagoModel pago) {

        if (pedido == null || detalles == null || detalles.isEmpty() || pago == null) {
            return false;
        }

        String sqlPedido =
                "INSERT INTO pedido " +
                        "(id_cliente, id_empleado, tipo_entrega, direccion_entrega, id_repartidor) " +
                        "VALUES (?, ?, ?, ?, ?)";

        String sqlStock = "{CALL sp_descontar_stock(?, ?, ?, ?)}";

        String sqlDetalle =
                "INSERT INTO detalle_pedido " +
                        "(id_pedido, codigo_producto, cantidad, precio_unitario, " +
                        "descuento, subtotal, id_promocion) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        String sqlPago =
                "INSERT INTO pago " +
                        "(id_pedido, metodo_pago, monto, estado_pago, monto_recibido, vuelto) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            conexion.setAutoCommit(false);

            try {
                int idPedido;

                // Registra el pedido y obtiene el ID autogenerado.
                try (PreparedStatement psPedido = conexion.prepareStatement(
                        sqlPedido, PreparedStatement.RETURN_GENERATED_KEYS)) {

                    psPedido.setInt(1, pedido.getIdCliente());
                    psPedido.setInt(2, pedido.getIdEmpleado());
                    psPedido.setString(3, pedido.getTipoEntrega());
                    psPedido.setString(4, pedido.getDireccionEntrega());
                    psPedido.setObject(5, pedido.getIdRepartidor());
                    psPedido.executeUpdate();

                    try (ResultSet claves = psPedido.getGeneratedKeys()) {
                        if (!claves.next()) {
                            throw new SQLException("No se generó el ID del pedido.");
                        }

                        idPedido = claves.getInt(1);
                    }
                }

                // Descuenta el stock y guarda cada detalle.
                for (DetallePedidoModel detalle : detalles) {
                    try (CallableStatement cs = conexion.prepareCall(sqlStock)) {
                        cs.setString(1, detalle.getCodigoProducto());
                        cs.setInt(2, detalle.getCantidad());
                        cs.registerOutParameter(3, Types.TINYINT);
                        cs.registerOutParameter(4, Types.VARCHAR);
                        cs.execute();

                        if (cs.getInt(3) != 1) {
                            throw new SQLException(cs.getString(4));
                        }
                    }

                    try (PreparedStatement psDetalle =
                                 conexion.prepareStatement(sqlDetalle)) {
                        psDetalle.setInt(1, idPedido);
                        psDetalle.setString(2, detalle.getCodigoProducto());
                        psDetalle.setInt(3, detalle.getCantidad());
                        psDetalle.setDouble(4, detalle.getPrecioUnitario());
                        psDetalle.setDouble(5, detalle.getDescuento());
                        psDetalle.setDouble(6, detalle.getSubtotal());
                        psDetalle.setObject(7, detalle.getIdPromocion());
                        psDetalle.executeUpdate();
                    }
                }

                // Registra el pago del pedido.
                try (PreparedStatement psPago = conexion.prepareStatement(sqlPago)) {
                    psPago.setInt(1, idPedido);
                    psPago.setString(2, pago.getMetodoPago());
                    psPago.setDouble(3, pago.getMonto());
                    psPago.setString(4, pago.getEstadoPago());
                    psPago.setDouble(5, pago.getMontoRecibido());
                    psPago.setDouble(6, pago.getVuelto());
                    psPago.executeUpdate();
                }

                conexion.commit();
                return true;

            } catch (SQLException error) {
                conexion.rollback();
                System.out.println("Error al registrar venta: " + error.getMessage());
                return false;

            } finally {
                conexion.setAutoCommit(true);
            }

        } catch (SQLException error) {
            System.out.println("Error de conexión al registrar venta: "
                    + error.getMessage());
            return false;
        }
    }

    @Override
    public List<PedidoModel> listarTodos() {
        List<PedidoModel> pedidos = new ArrayList<>();

        String sql =
                "SELECT id_pedido, id_cliente, id_empleado, fecha, estado, " +
                        "tipo_entrega, direccion_entrega, id_repartidor " +
                        "FROM pedido ORDER BY fecha DESC";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return pedidos;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    pedidos.add(mapearPedido(rs));
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al listar pedidos: " + error.getMessage());
        }

        return pedidos;
    }

    @Override
    public PedidoModel buscarPorId(int idPedido) {
        String sql =
                "SELECT id_pedido, id_cliente, id_empleado, fecha, estado, " +
                        "tipo_entrega, direccion_entrega, id_repartidor " +
                        "FROM pedido WHERE id_pedido = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return null;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idPedido);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapearPedido(rs);
                    }
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al buscar pedido: " + error.getMessage());
        }

        return null;
    }

    @Override
    public boolean actualizarEstado(int idPedido, String estado) {
        String sql = "UPDATE pedido SET estado = ? WHERE id_pedido = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, estado);
                ps.setInt(2, idPedido);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException error) {
            System.out.println("Error al actualizar pedido: " + error.getMessage());
            return false;
        }
    }

    private PedidoModel mapearPedido(ResultSet rs) throws SQLException {
        Integer idRepartidor =
                (Integer) rs.getObject("id_repartidor");

        return new PedidoModel(
                rs.getInt("id_pedido"),
                rs.getInt("id_cliente"),
                rs.getInt("id_empleado"),
                rs.getString("fecha"),
                rs.getString("estado"),
                rs.getString("tipo_entrega"),
                rs.getString("direccion_entrega"),
                idRepartidor
        );
    }
}
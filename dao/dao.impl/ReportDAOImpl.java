package dao.impl;

import dao.ReportDAO;
import models.ProductoStockModel;
import services.DatabaseConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReportDAOImpl implements ReportDAO {

    @Override
    public int contarConsultasDeHoy() {
        String sql =
                "SELECT COUNT(*) AS total FROM consulta " +
                        "WHERE DATE(fecha) = CURDATE()";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return -1;
            }

            try (Statement statement = conexion.createStatement();
                 ResultSet rs = statement.executeQuery(sql)) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }

        } catch (SQLException error) {
            System.out.println("Error en reporte de consultas: " + error.getMessage());
        }

        return -1;
    }

    @Override
    public int contarPedidosDeHoy() {
        String sql =
                "SELECT COUNT(*) AS total FROM pedido " +
                        "WHERE DATE(fecha) = CURDATE()";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return -1;
            }

            try (Statement statement = conexion.createStatement();
                 ResultSet rs = statement.executeQuery(sql)) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }

        } catch (SQLException error) {
            System.out.println("Error en reporte de pedidos: " + error.getMessage());
        }

        return -1;
    }

    @Override
    public double calcularVentasDeHoy() {
        String sql =
                "SELECT COALESCE(SUM(monto), 0) AS total " +
                        "FROM pago " +
                        "WHERE DATE(fecha_pago) = CURDATE() " +
                        "AND estado_pago = 'PAGADO'";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return -1;
            }

            try (Statement statement = conexion.createStatement();
                 ResultSet rs = statement.executeQuery(sql)) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }

        } catch (SQLException error) {
            System.out.println("Error en reporte de ventas: " + error.getMessage());
        }

        return -1;
    }

    @Override
    public List<ProductoStockModel> listarProductosConStockBajo() {
        List<ProductoStockModel> productos = new ArrayList<>();

        String sql =
                "SELECT codigo, nombre, stock FROM producto " +
                        "WHERE stock <= 5 ORDER BY stock, nombre";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return productos;
            }

            try (Statement statement = conexion.createStatement();
                 ResultSet rs = statement.executeQuery(sql)) {

                while (rs.next()) {
                    productos.add(new ProductoStockModel(
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getInt("stock")
                    ));
                }
            }

        } catch (SQLException error) {
            System.out.println("Error en reporte de stock: " + error.getMessage());
        }

        return productos;
    }
}
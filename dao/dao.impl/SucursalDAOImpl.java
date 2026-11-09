package dao.impl;

import dao.SucursalDAO;
import models.SucursalModel;
import services.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SucursalDAOImpl implements SucursalDAO {

    @Override
    public boolean insertar(SucursalModel sucursal) {
        String sql =
                "INSERT INTO sucursal " +
                        "(nombre, direccion, telefono, horario_atencion, activo) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, sucursal.getNombre());
                ps.setString(2, sucursal.getDireccion());
                ps.setString(3, sucursal.getTelefono());
                ps.setString(4, sucursal.getHorarioAtencion());
                ps.setBoolean(5, sucursal.isActivo());

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException error) {
            System.out.println("Error al registrar sucursal: " + error.getMessage());
            return false;
        }
    }

    @Override
    public List<SucursalModel> listarTodas() {
        List<SucursalModel> sucursales = new ArrayList<>();
        String sql =
                "SELECT id_sucursal, nombre, direccion, telefono, " +
                        "horario_atencion, activo FROM sucursal " +
                        "ORDER BY id_sucursal";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return sucursales;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    sucursales.add(mapearSucursal(rs));
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al listar sucursales: " + error.getMessage());
        }

        return sucursales;
    }

    @Override
    public SucursalModel buscarPorId(int idSucursal) {
        String sql =
                "SELECT id_sucursal, nombre, direccion, telefono, " +
                        "horario_atencion, activo FROM sucursal " +
                        "WHERE id_sucursal = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return null;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idSucursal);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapearSucursal(rs);
                    }
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al buscar sucursal: " + error.getMessage());
        }

        return null;
    }

    @Override
    public boolean actualizar(SucursalModel sucursal) {
        String sql =
                "UPDATE sucursal SET nombre = ?, direccion = ?, telefono = ?, " +
                        "horario_atencion = ?, activo = ? WHERE id_sucursal = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, sucursal.getNombre());
                ps.setString(2, sucursal.getDireccion());
                ps.setString(3, sucursal.getTelefono());
                ps.setString(4, sucursal.getHorarioAtencion());
                ps.setBoolean(5, sucursal.isActivo());
                ps.setInt(6, sucursal.getIdSucursal());

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException error) {
            System.out.println("Error al actualizar sucursal: " + error.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int idSucursal) {
        String sql = "DELETE FROM sucursal WHERE id_sucursal = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idSucursal);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException error) {
            System.out.println(
                    "No se pudo eliminar. Puede haber empleados asignados. "
                            + error.getMessage()
            );
            return false;
        }
    }

    private SucursalModel mapearSucursal(ResultSet rs) throws SQLException {
        return new SucursalModel(
                rs.getInt("id_sucursal"),
                rs.getString("nombre"),
                rs.getString("direccion"),
                rs.getString("telefono"),
                rs.getString("horario_atencion"),
                rs.getBoolean("activo")
        );
    }
}
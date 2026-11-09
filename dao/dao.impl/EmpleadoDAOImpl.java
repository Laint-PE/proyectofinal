package dao.impl;

import dao.EmpleadoDAO;
import models.EmpleadoModel;
import services.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO {

    @Override
    public boolean insertar(EmpleadoModel empleado) {
        String sqlPersona =
                "INSERT INTO persona (dni, nombre, apellido, edad, telefono) " +
                        "VALUES (?, ?, ?, ?, ?)";
        String sqlEmpleado =
                "INSERT INTO empleado (dni, cargo, id_sucursal, estado) " +
                        "VALUES (?, ?, ?, ?)";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            conexion.setAutoCommit(false);

            try (PreparedStatement psPersona =
                         conexion.prepareStatement(sqlPersona);
                 PreparedStatement psEmpleado =
                         conexion.prepareStatement(sqlEmpleado)) {

                psPersona.setString(1, empleado.getDni());
                psPersona.setString(2, empleado.getNombre());
                psPersona.setString(3, empleado.getApellido());
                psPersona.setInt(4, empleado.getEdad());
                psPersona.setString(5, empleado.getTelefono());
                psPersona.executeUpdate();

                psEmpleado.setString(1, empleado.getDni());
                psEmpleado.setString(2, empleado.getCargo());
                psEmpleado.setInt(3, empleado.getIdSucursal());
                psEmpleado.setString(4, empleado.getEstado());
                psEmpleado.executeUpdate();

                conexion.commit();
                return true;

            } catch (SQLException error) {
                conexion.rollback();
                System.out.println("Error al registrar empleado: "
                        + error.getMessage());
                return false;
            } finally {
                conexion.setAutoCommit(true);
            }

        } catch (SQLException error) {
            System.out.println("Error de conexión: " + error.getMessage());
            return false;
        }
    }

    @Override
    public List<EmpleadoModel> listarTodos() {
        List<EmpleadoModel> empleados = new ArrayList<>();
        String sql = consultaBase() + "ORDER BY e.id_empleado";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return empleados;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    empleados.add(mapearEmpleado(rs));
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al listar empleados: " + error.getMessage());
        }

        return empleados;
    }

    @Override
    public List<EmpleadoModel> listarPorCargo(String cargo) {
        List<EmpleadoModel> empleados = new ArrayList<>();
        String sql = consultaBase() + "WHERE e.cargo = ? ORDER BY e.id_empleado";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return empleados;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, cargo);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        empleados.add(mapearEmpleado(rs));
                    }
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al filtrar empleados: " + error.getMessage());
        }

        return empleados;
    }

    @Override
    public EmpleadoModel buscarPorId(int idEmpleado) {
        String sql = consultaBase() + "WHERE e.id_empleado = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return null;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idEmpleado);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapearEmpleado(rs);
                    }
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al buscar empleado: " + error.getMessage());
        }

        return null;
    }

    @Override
    public boolean actualizar(EmpleadoModel empleado) {
        String sqlPersona =
                "UPDATE persona SET nombre = ?, apellido = ?, edad = ?, " +
                        "telefono = ? WHERE dni = ?";
        String sqlEmpleado =
                "UPDATE empleado SET cargo = ?, id_sucursal = ?, estado = ? " +
                        "WHERE id_empleado = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            conexion.setAutoCommit(false);

            try (PreparedStatement psPersona =
                         conexion.prepareStatement(sqlPersona);
                 PreparedStatement psEmpleado =
                         conexion.prepareStatement(sqlEmpleado)) {

                psPersona.setString(1, empleado.getNombre());
                psPersona.setString(2, empleado.getApellido());
                psPersona.setInt(3, empleado.getEdad());
                psPersona.setString(4, empleado.getTelefono());
                psPersona.setString(5, empleado.getDni());

                psEmpleado.setString(1, empleado.getCargo());
                psEmpleado.setInt(2, empleado.getIdSucursal());
                psEmpleado.setString(3, empleado.getEstado());
                psEmpleado.setInt(4, empleado.getIdEmpleado());

                int personaActualizada = psPersona.executeUpdate();
                int empleadoActualizado = psEmpleado.executeUpdate();

                if (personaActualizada > 0 && empleadoActualizado > 0) {
                    conexion.commit();
                    return true;
                }

                conexion.rollback();
                return false;

            } catch (SQLException error) {
                conexion.rollback();
                System.out.println("Error al actualizar empleado: "
                        + error.getMessage());
                return false;
            } finally {
                conexion.setAutoCommit(true);
            }

        } catch (SQLException error) {
            System.out.println("Error de conexión: " + error.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int idEmpleado) {
        String sql = "DELETE FROM empleado WHERE id_empleado = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idEmpleado);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException error) {
            System.out.println(
                    "No se pudo eliminar. El empleado puede tener pedidos, consultas "
                            + "o un usuario asociado. " + error.getMessage()
            );
            return false;
        }
    }

    private String consultaBase() {
        return "SELECT e.id_empleado, e.cargo, e.id_sucursal, e.estado, " +
                "p.dni, p.nombre, p.apellido, p.edad, p.telefono " +
                "FROM empleado e " +
                "INNER JOIN persona p ON e.dni = p.dni ";
    }

    private EmpleadoModel mapearEmpleado(ResultSet rs) throws SQLException {
        return new EmpleadoModel(
                rs.getString("dni"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getInt("edad"),
                rs.getString("telefono"),
                rs.getString("cargo"),
                rs.getInt("id_sucursal"),
                rs.getString("estado"),
                rs.getInt("id_empleado")
        );
    }
}
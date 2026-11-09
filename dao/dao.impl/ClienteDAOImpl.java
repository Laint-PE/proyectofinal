package dao.impl;

import dao.ClienteDAO;
import models.ClienteModel;
import services.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public boolean insertar(ClienteModel cliente) {
        String sqlPersona =
                "INSERT INTO persona (dni, nombre, apellido, edad, telefono) " +
                        "VALUES (?, ?, ?, ?, ?)";
        String sqlCliente =
                "INSERT INTO cliente (dni, correo, direccion) VALUES (?, ?, ?)";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            conexion.setAutoCommit(false);

            try (PreparedStatement psPersona =
                         conexion.prepareStatement(sqlPersona);
                 PreparedStatement psCliente =
                         conexion.prepareStatement(sqlCliente)) {

                psPersona.setString(1, cliente.getDni());
                psPersona.setString(2, cliente.getNombre());
                psPersona.setString(3, cliente.getApellido());
                psPersona.setInt(4, cliente.getEdad());
                psPersona.setString(5, cliente.getTelefono());
                psPersona.executeUpdate();

                psCliente.setString(1, cliente.getDni());
                psCliente.setString(2, cliente.getCorreo());
                psCliente.setString(3, cliente.getDireccion());
                psCliente.executeUpdate();

                conexion.commit();
                return true;

            } catch (SQLException error) {
                conexion.rollback();
                System.out.println("Error al registrar cliente: " + error.getMessage());
                return false;
            } finally {
                conexion.setAutoCommit(true);
            }

        } catch (SQLException error) {
            System.out.println("Error de conexión al registrar cliente: "
                    + error.getMessage());
            return false;
        }
    }

    @Override
    public List<ClienteModel> listarTodos() {
        List<ClienteModel> clientes = new ArrayList<>();

        String sql =
                "SELECT c.id_cliente, p.dni, p.nombre, p.apellido, " +
                        "p.edad, p.telefono, c.correo, c.direccion " +
                        "FROM cliente c " +
                        "INNER JOIN persona p ON c.dni = p.dni " +
                        "ORDER BY c.id_cliente";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return clientes;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    clientes.add(mapearCliente(rs));
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al listar clientes: " + error.getMessage());
        }

        return clientes;
    }

    @Override
    public ClienteModel buscarPorId(int idCliente) {
        String sql =
                "SELECT c.id_cliente, p.dni, p.nombre, p.apellido, " +
                        "p.edad, p.telefono, c.correo, c.direccion " +
                        "FROM cliente c " +
                        "INNER JOIN persona p ON c.dni = p.dni " +
                        "WHERE c.id_cliente = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return null;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idCliente);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapearCliente(rs);
                    }
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al buscar cliente: " + error.getMessage());
        }

        return null;
    }

    @Override
    public boolean actualizar(ClienteModel cliente) {
        String sqlPersona =
                "UPDATE persona SET nombre = ?, apellido = ?, edad = ?, " +
                        "telefono = ? WHERE dni = ?";
        String sqlCliente =
                "UPDATE cliente SET correo = ?, direccion = ? " +
                        "WHERE id_cliente = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            conexion.setAutoCommit(false);

            try (PreparedStatement psPersona =
                         conexion.prepareStatement(sqlPersona);
                 PreparedStatement psCliente =
                         conexion.prepareStatement(sqlCliente)) {

                psPersona.setString(1, cliente.getNombre());
                psPersona.setString(2, cliente.getApellido());
                psPersona.setInt(3, cliente.getEdad());
                psPersona.setString(4, cliente.getTelefono());
                psPersona.setString(5, cliente.getDni());

                psCliente.setString(1, cliente.getCorreo());
                psCliente.setString(2, cliente.getDireccion());
                psCliente.setInt(3, cliente.getIdCliente());

                int personaActualizada = psPersona.executeUpdate();
                int clienteActualizado = psCliente.executeUpdate();

                if (personaActualizada > 0 && clienteActualizado > 0) {
                    conexion.commit();
                    return true;
                }

                conexion.rollback();
                return false;

            } catch (SQLException error) {
                conexion.rollback();
                System.out.println("Error al actualizar cliente: " + error.getMessage());
                return false;
            } finally {
                conexion.setAutoCommit(true);
            }

        } catch (SQLException error) {
            System.out.println("Error de conexión al actualizar cliente: "
                    + error.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int idCliente) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idCliente);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException error) {
            System.out.println(
                    "No se pudo eliminar el cliente. Comprueba si tiene mascotas asociadas. "
                            + error.getMessage()
            );
            return false;
        }
    }

    private ClienteModel mapearCliente(ResultSet rs) throws SQLException {
        return new ClienteModel(
                rs.getString("dni"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getInt("edad"),
                rs.getString("telefono"),
                rs.getString("correo"),
                rs.getString("direccion"),
                rs.getInt("id_cliente")
        );
    }
}
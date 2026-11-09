package dao.impl;

import java.sql.CallableStatement;
import java.sql.Types;
import dao.ConsultaDAO;
import models.ConsultaModel;
import services.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAOImpl implements ConsultaDAO {

    @Override
    public boolean insertar(ConsultaModel consulta) {
        String sql = "{CALL sp_registrar_consulta(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            try (CallableStatement cs = conexion.prepareCall(sql)) {
                cs.setString(1, consulta.getCodigoAnimal());
                cs.setInt(2, consulta.getIdVeterinario());
                cs.setString(3, consulta.getMotivo());
                cs.setString(4, consulta.getSintomas());
                cs.setString(5, consulta.getDiagnostico());
                cs.setString(6, consulta.getTratamiento());
                cs.setDouble(7, consulta.getCosto());

                cs.registerOutParameter(8, Types.TINYINT);
                cs.registerOutParameter(9, Types.VARCHAR);
                cs.registerOutParameter(10, Types.INTEGER);

                cs.execute();

                int exito = cs.getInt(8);
                String mensaje = cs.getString(9);

                System.out.println(mensaje);
                return exito == 1;
            }

        } catch (SQLException error) {
            System.out.println("Error al registrar consulta: " + error.getMessage());
            return false;
        }
    }
    @Override
    public List<ConsultaModel> listarTodos() {
        List<ConsultaModel> consultas = new ArrayList<>();
        String sql =
                "SELECT id_consulta, codigo_animal, id_veterinario, fecha, " +
                        "motivo, sintomas, diagnostico, tratamiento, costo, estado " +
                        "FROM consulta ORDER BY fecha DESC";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return consultas;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    consultas.add(mapearConsulta(rs));
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al listar consultas: " + error.getMessage());
        }

        return consultas;
    }

    @Override
    public ConsultaModel buscarPorId(int idConsulta) {
        String sql =
                "SELECT id_consulta, codigo_animal, id_veterinario, fecha, " +
                        "motivo, sintomas, diagnostico, tratamiento, costo, estado " +
                        "FROM consulta WHERE id_consulta = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return null;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idConsulta);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapearConsulta(rs);
                    }
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al buscar consulta: " + error.getMessage());
        }

        return null;
    }

    @Override
    public int contarConsultasDeHoy() {
        String sql =
                "SELECT COUNT(*) AS cantidad " +
                        "FROM consulta WHERE DATE(fecha) = CURDATE()";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return -1;
            }

            try (Statement statement = conexion.createStatement();
                 ResultSet rs = statement.executeQuery(sql)) {

                if (rs.next()) {
                    return rs.getInt("cantidad");
                }
            }

        } catch (SQLException error) {
            System.out.println(
                    "Error al contar las consultas de hoy: " + error.getMessage()
            );
        }

        return -1;
    }

    @Override
    public boolean actualizar(ConsultaModel consulta) {
        String sql =
                "UPDATE consulta SET motivo = ?, sintomas = ?, diagnostico = ?, " +
                        "tratamiento = ?, costo = ?, estado = ? " +
                        "WHERE id_consulta = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, consulta.getMotivo());
                ps.setString(2, consulta.getSintomas());
                ps.setString(3, consulta.getDiagnostico());
                ps.setString(4, consulta.getTratamiento());
                ps.setDouble(5, consulta.getCosto());
                ps.setString(6, consulta.getEstado());
                ps.setInt(7, consulta.getIdConsulta());

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException error) {
            System.out.println("Error al actualizar consulta: " + error.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int idConsulta) {
        String sql = "DELETE FROM consulta WHERE id_consulta = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idConsulta);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException error) {
            System.out.println("Error al eliminar consulta: " + error.getMessage());
            return false;
        }
    }

    private ConsultaModel mapearConsulta(ResultSet rs) throws SQLException {
        return new ConsultaModel(
                rs.getInt("id_consulta"),
                rs.getString("codigo_animal"),
                rs.getInt("id_veterinario"),
                rs.getString("fecha"),
                rs.getString("motivo"),
                rs.getString("sintomas"),
                rs.getString("diagnostico"),
                rs.getString("tratamiento"),
                rs.getDouble("costo"),
                rs.getString("estado")
        );
    }
}
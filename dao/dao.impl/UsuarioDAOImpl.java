package dao.impl;

import dao.UsuarioDAO;
import models.UsuarioModel;
import services.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public UsuarioModel autenticar(String usuario, String password) {
        String sql =
                "SELECT id_usuario, usuario, password, rol, activo, id_empleado " +
                        "FROM usuario WHERE usuario = ? AND password = ? AND activo = TRUE";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return null;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, usuario);
                ps.setString(2, password);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapearUsuario(rs);
                    }
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al iniciar sesión: " + error.getMessage());
        }

        return null;
    }

    @Override
    public List<UsuarioModel> listarTodos() {
        List<UsuarioModel> usuarios = new ArrayList<>();
        String sql =
                "SELECT id_usuario, usuario, rol, activo, id_empleado " +
                        "FROM usuario ORDER BY id_usuario";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return usuarios;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    usuarios.add(new UsuarioModel(
                            rs.getInt("id_usuario"),
                            rs.getString("usuario"),
                            "",
                            rs.getString("rol"),
                            rs.getBoolean("activo"),
                            rs.getInt("id_empleado")
                    ));
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al listar usuarios: " + error.getMessage());
        }

        return usuarios;
    }

    @Override
    public boolean cambiarEstado(int idUsuario, boolean activo) {
        String sql = "UPDATE usuario SET activo = ? WHERE id_usuario = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setBoolean(1, activo);
                ps.setInt(2, idUsuario);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException error) {
            System.out.println("Error al cambiar estado del usuario: "
                    + error.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int idUsuario) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idUsuario);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException error) {
            System.out.println("Error al eliminar usuario: " + error.getMessage());
            return false;
        }
    }

    private UsuarioModel mapearUsuario(ResultSet rs) throws SQLException {
        return new UsuarioModel(
                rs.getInt("id_usuario"),
                rs.getString("usuario"),
                rs.getString("password"),
                rs.getString("rol"),
                rs.getBoolean("activo"),
                rs.getInt("id_empleado")
        );
    }
}
package dao.impl;

import dao.CategoriaDAO;
import models.CategoriaModel;
import services.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOImpl implements CategoriaDAO {

    @Override
    public boolean insertar(CategoriaModel categoria) {
        String sql = "INSERT INTO categoria (nombre, descripcion) VALUES (?, ?)";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, categoria.getNombre());
                ps.setString(2, categoria.getDescripcion());
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException error) {
            System.out.println("Error al registrar categoría: " + error.getMessage());
            return false;
        }
    }

    @Override
    public List<CategoriaModel> listarTodos() {
        List<CategoriaModel> categorias = new ArrayList<>();
        String sql = "SELECT id_categoria, nombre, descripcion " +
                "FROM categoria ORDER BY id_categoria";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return categorias;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    categorias.add(mapearCategoria(rs));
                }
            }
        } catch (SQLException error) {
            System.out.println("Error al listar categorías: " + error.getMessage());
        }

        return categorias;
    }

    @Override
    public CategoriaModel buscarPorId(int idCategoria) {
        String sql = "SELECT id_categoria, nombre, descripcion " +
                "FROM categoria WHERE id_categoria = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return null;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idCategoria);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapearCategoria(rs);
                    }
                }
            }
        } catch (SQLException error) {
            System.out.println("Error al buscar categoría: " + error.getMessage());
        }

        return null;
    }

    @Override
    public boolean actualizar(CategoriaModel categoria) {
        String sql = "UPDATE categoria SET nombre = ?, descripcion = ? " +
                "WHERE id_categoria = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, categoria.getNombre());
                ps.setString(2, categoria.getDescripcion());
                ps.setInt(3, categoria.getIdCategoria());
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException error) {
            System.out.println("Error al actualizar categoría: " + error.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int idCategoria) {
        String sql = "DELETE FROM categoria WHERE id_categoria = ?";

        try (Connection conexion = DatabaseConnector.getConnection()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idCategoria);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException error) {
            System.out.println(
                    "No se pudo eliminar la categoría. Puede tener productos asociados. "
                            + error.getMessage()
            );
            return false;
        }
    }

    private CategoriaModel mapearCategoria(ResultSet rs) throws SQLException {
        return new CategoriaModel(
                rs.getInt("id_categoria"),
                rs.getString("nombre"),
                rs.getString("descripcion")
        );
    }
}
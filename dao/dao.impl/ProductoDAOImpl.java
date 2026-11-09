package dao.impl;

import dao.ProductoDAO;
import models.AccesorioModel;
import models.AlimentoModel;
import models.MedicamentoModel;
import models.ProductoModel;
import services.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    private Connection conectar() throws SQLException {
        Connection conexion = DatabaseConnector.getConnection();

        if (conexion == null) {
            throw new SQLException("No se pudo conectar a la base de datos.");
        }

        return conexion;
    }

    @Override
    public boolean insertar(ProductoModel producto) {
        String sql =
                "INSERT INTO producto " +
                        "(codigo, nombre, precio, stock, id_categoria) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = conectar()) {
            conexion.setAutoCommit(false);

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, producto.getCodigo());
                ps.setString(2, producto.getNombre());
                ps.setDouble(3, producto.getPrecio());
                ps.setInt(4, producto.getStock());
                ps.setInt(5, producto.getIdCategoria());
                ps.executeUpdate();
            }

            insertarSubtipo(conexion, producto);
            conexion.commit();
            return true;

        } catch (SQLException error) {
            System.out.println("Error al registrar producto: " + error.getMessage());
            return false;
        }
    }

    private void insertarSubtipo(Connection conexion, ProductoModel producto)
            throws SQLException {

        if (producto instanceof AccesorioModel) {
            AccesorioModel accesorio = (AccesorioModel) producto;
            String sql =
                    "INSERT INTO accesorio (codigo, tipo, material, ropa) " +
                            "VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, accesorio.getCodigo());
                ps.setString(2, accesorio.getTipo());
                ps.setString(3, accesorio.getMaterial());
                ps.setString(4, accesorio.getRopa());
                ps.executeUpdate();
            }

        } else if (producto instanceof AlimentoModel) {
            AlimentoModel alimento = (AlimentoModel) producto;
            String sql =
                    "INSERT INTO alimento " +
                            "(codigo, tipo_animal, fecha_vencimiento, sabor) " +
                            "VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, alimento.getCodigo());
                ps.setString(2, alimento.getTipoAnimal());
                ps.setString(3, alimento.getFechaVencimiento());
                ps.setString(4, alimento.getSabor());
                ps.executeUpdate();
            }

        } else if (producto instanceof MedicamentoModel) {
            MedicamentoModel medicamento = (MedicamentoModel) producto;
            String sql =
                    "INSERT INTO medicamento " +
                            "(codigo, fecha_vencimiento, dosis) VALUES (?, ?, ?)";

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, medicamento.getCodigo());
                ps.setString(2, medicamento.getFechaVencimiento());
                ps.setString(3, medicamento.getDosis());
                ps.executeUpdate();
            }

        } else {
            throw new SQLException(
                    "El producto debe ser accesorio, alimento o medicamento."
            );
        }
    }

    private String consultaBase() {
        return "SELECT p.codigo, p.nombre, p.precio, p.stock, p.id_categoria, " +
                "a.codigo AS codigo_accesorio, a.tipo, a.material, a.ropa, " +
                "al.codigo AS codigo_alimento, al.tipo_animal, " +
                "al.fecha_vencimiento AS vencimiento_alimento, al.sabor, " +
                "m.codigo AS codigo_medicamento, " +
                "m.fecha_vencimiento AS vencimiento_medicamento, m.dosis " +
                "FROM producto p " +
                "LEFT JOIN accesorio a ON p.codigo = a.codigo " +
                "LEFT JOIN alimento al ON p.codigo = al.codigo " +
                "LEFT JOIN medicamento m ON p.codigo = m.codigo ";
    }

    @Override
    public List<ProductoModel> listarTodos() {
        List<ProductoModel> productos = new ArrayList<>();
        String sql = consultaBase() + "ORDER BY p.codigo";

        try (Connection conexion = conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProductoModel producto = mapearProducto(rs);

                if (producto != null) {
                    productos.add(producto);
                } else {
                    System.out.println(
                            "El producto " + rs.getString("codigo")
                                    + " no tiene subtipo registrado."
                    );
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al listar productos: " + error.getMessage());
        }

        return productos;
    }

    @Override
    public ProductoModel buscarPorCodigo(String codigo) {
        String sql = consultaBase() + "WHERE p.codigo = ?";

        try (Connection conexion = conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearProducto(rs);
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al buscar producto: " + error.getMessage());
        }

        return null;
    }

    private ProductoModel mapearProducto(ResultSet rs) throws SQLException {
        String codigo = rs.getString("codigo");
        String nombre = rs.getString("nombre");
        double precio = rs.getDouble("precio");
        int stock = rs.getInt("stock");
        int idCategoria = rs.getInt("id_categoria");

        if (rs.getString("codigo_accesorio") != null) {
            return new AccesorioModel(
                    codigo, nombre, precio, stock, idCategoria,
                    rs.getString("tipo"),
                    rs.getString("material"),
                    rs.getString("ropa")
            );
        }

        if (rs.getString("codigo_alimento") != null) {
            return new AlimentoModel(
                    codigo, nombre, precio, stock, idCategoria,
                    rs.getString("tipo_animal"),
                    rs.getString("vencimiento_alimento"),
                    rs.getString("sabor")
            );
        }

        if (rs.getString("codigo_medicamento") != null) {
            return new MedicamentoModel(
                    codigo, nombre, precio, stock, idCategoria,
                    rs.getString("vencimiento_medicamento"),
                    rs.getString("dosis")
            );
        }

        return null;
    }

    @Override
    public boolean actualizar(ProductoModel producto) {
        String sql =
                "UPDATE producto SET nombre = ?, precio = ?, stock = ?, " +
                        "id_categoria = ? WHERE codigo = ?";

        try (Connection conexion = conectar()) {
            conexion.setAutoCommit(false);

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, producto.getNombre());
                ps.setDouble(2, producto.getPrecio());
                ps.setInt(3, producto.getStock());
                ps.setInt(4, producto.getIdCategoria());
                ps.setString(5, producto.getCodigo());

                if (ps.executeUpdate() == 0) {
                    conexion.rollback();
                    return false;
                }
            }

            actualizarSubtipo(conexion, producto);
            conexion.commit();
            return true;

        } catch (SQLException error) {
            System.out.println("Error al actualizar producto: " + error.getMessage());
            return false;
        }
    }

    private void actualizarSubtipo(Connection conexion, ProductoModel producto)
            throws SQLException {

        String sql;
        PreparedStatement ps;

        if (producto instanceof AccesorioModel) {
            AccesorioModel accesorio = (AccesorioModel) producto;
            sql = "UPDATE accesorio SET tipo = ?, material = ?, ropa = ? " +
                    "WHERE codigo = ?";

            ps = conexion.prepareStatement(sql);
            ps.setString(1, accesorio.getTipo());
            ps.setString(2, accesorio.getMaterial());
            ps.setString(3, accesorio.getRopa());
            ps.setString(4, accesorio.getCodigo());

        } else if (producto instanceof AlimentoModel) {
            AlimentoModel alimento = (AlimentoModel) producto;
            sql = "UPDATE alimento SET tipo_animal = ?, fecha_vencimiento = ?, " +
                    "sabor = ? WHERE codigo = ?";

            ps = conexion.prepareStatement(sql);
            ps.setString(1, alimento.getTipoAnimal());
            ps.setString(2, alimento.getFechaVencimiento());
            ps.setString(3, alimento.getSabor());
            ps.setString(4, alimento.getCodigo());

        } else if (producto instanceof MedicamentoModel) {
            MedicamentoModel medicamento = (MedicamentoModel) producto;
            sql = "UPDATE medicamento SET fecha_vencimiento = ?, dosis = ? " +
                    "WHERE codigo = ?";

            ps = conexion.prepareStatement(sql);
            ps.setString(1, medicamento.getFechaVencimiento());
            ps.setString(2, medicamento.getDosis());
            ps.setString(3, medicamento.getCodigo());

        } else {
            throw new SQLException("El tipo de producto no está reconocido.");
        }

        try (PreparedStatement psSubtipo = ps) {
            psSubtipo.executeUpdate();
        }
    }

    @Override
    public boolean eliminar(String codigo) {
        String sql = "DELETE FROM producto WHERE codigo = ?";

        try (Connection conexion = conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;

        } catch (SQLException error) {
            System.out.println("Error al eliminar producto: " + error.getMessage());
            return false;
        }
    }

    @Override
    public List<ProductoModel> listarPorCategoria(int idCategoria) {
        List<ProductoModel> productos = new ArrayList<>();
        String sql = consultaBase() + "WHERE p.id_categoria = ? ORDER BY p.codigo";

        try (Connection conexion = conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idCategoria);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductoModel producto = mapearProducto(rs);

                    if (producto != null) {
                        productos.add(producto);
                    } else {
                        System.out.println(
                                "El producto " + rs.getString("codigo")
                                        + " no tiene subtipo registrado."
                        );
                    }
                }
            }

        } catch (SQLException error) {
            System.out.println(
                    "Error al listar productos por categoría: "
                            + error.getMessage()
            );
        }

        return productos;
    }
}
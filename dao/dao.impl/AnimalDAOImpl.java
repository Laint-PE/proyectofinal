package dao.dao.impl;

import dao.AnimalDAO;
import models.AnimalModel;
import models.GatoModel;
import models.PerroModel;
import services.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAOImpl implements AnimalDAO {

    private Connection conectar() throws SQLException {
        Connection conexion = DatabaseConnector.getConnection();

        if (conexion == null) {
            throw new SQLException("No se pudo conectar a la base de datos.");
        }

        return conexion;
    }

    @Override
    public boolean insertar(AnimalModel animal) {
        String sqlAnimal =
                "INSERT INTO animal " +
                        "(codigo, nombre, raza, sexo, edad, peso, id_cliente) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = conectar()) {
            conexion.setAutoCommit(false);

            try (PreparedStatement ps = conexion.prepareStatement(sqlAnimal)) {
                ps.setString(1, animal.getCodigo());
                ps.setString(2, animal.getNombre());
                ps.setString(3, animal.getRaza());
                ps.setString(4, animal.getSexo());
                ps.setInt(5, animal.getEdad());
                ps.setDouble(6, animal.getPeso());
                ps.setInt(7, animal.getIdCliente());
                ps.executeUpdate();
            }

            insertarSubtipo(conexion, animal);
            conexion.commit();
            return true;

        } catch (SQLException error) {
            System.out.println("Error al registrar mascota: " + error.getMessage());
            return false;
        }
    }

    private void insertarSubtipo(Connection conexion, AnimalModel animal)
            throws SQLException {

        if (animal instanceof PerroModel) {
            PerroModel perro = (PerroModel) animal;
            String sql =
                    "INSERT INTO perro " +
                            "(codigo, nivel_entrenamiento, requiere_bozal) " +
                            "VALUES (?, ?, ?)";

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, perro.getCodigo());
                ps.setString(2, perro.getNivelEntenamiento());
                ps.setBoolean(3, perro.isRequiereBozal());
                ps.executeUpdate();
            }

        } else if (animal instanceof GatoModel) {
            GatoModel gato = (GatoModel) animal;
            String sql =
                    "INSERT INTO gato " +
                            "(codigo, temperamento, requiere_transportadora) " +
                            "VALUES (?, ?, ?)";

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, gato.getCodigo());
                ps.setString(2, gato.getTemperamento());
                ps.setBoolean(3, gato.isRequiereTransportadora());
                ps.executeUpdate();
            }

        } else {
            throw new SQLException("La mascota debe ser perro o gato.");
        }
    }

    private String consultaBase() {
        return "SELECT a.codigo, a.nombre, a.raza, a.sexo, a.edad, a.peso, " +
                "a.id_cliente, p.codigo AS codigo_perro, " +
                "p.nivel_entrenamiento, p.requiere_bozal, " +
                "g.codigo AS codigo_gato, g.temperamento, " +
                "g.requiere_transportadora " +
                "FROM animal a " +
                "LEFT JOIN perro p ON a.codigo = p.codigo " +
                "LEFT JOIN gato g ON a.codigo = g.codigo ";
    }

    @Override
    public List<AnimalModel> listarTodos() {
        List<AnimalModel> animales = new ArrayList<>();
        String sql = consultaBase() + "ORDER BY a.codigo";

        try (Connection conexion = conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                AnimalModel animal = mapearAnimal(rs);

                if (animal != null) {
                    animales.add(animal);
                } else {
                    System.out.println(
                            "La mascota " + rs.getString("codigo")
                                    + " no tiene tipo perro o gato registrado."
                    );
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al listar mascotas: " + error.getMessage());
        }

        return animales;
    }

    @Override
    public AnimalModel buscarPorCodigo(String codigo) {
        String sql = consultaBase() + "WHERE a.codigo = ?";

        try (Connection conexion = conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearAnimal(rs);
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al buscar mascota: " + error.getMessage());
        }

        return null;
    }

    @Override
    public List<AnimalModel> listarPorCliente(int idCliente) {
        List<AnimalModel> animales = new ArrayList<>();
        String sql = consultaBase() + "WHERE a.id_cliente = ? ORDER BY a.codigo";

        try (Connection conexion = conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AnimalModel animal = mapearAnimal(rs);

                    if (animal != null) {
                        animales.add(animal);
                    }
                }
            }

        } catch (SQLException error) {
            System.out.println("Error al listar mascotas del cliente: "
                    + error.getMessage());
        }

        return animales;
    }

    private AnimalModel mapearAnimal(ResultSet rs) throws SQLException {
        String codigo = rs.getString("codigo");
        String nombre = rs.getString("nombre");
        String raza = rs.getString("raza");
        String sexo = rs.getString("sexo");
        int edad = rs.getInt("edad");
        double peso = rs.getDouble("peso");
        int idCliente = rs.getInt("id_cliente");

        if (rs.getString("codigo_perro") != null) {
            return new PerroModel(
                    codigo, nombre, raza, sexo, edad, peso, idCliente,
                    rs.getString("nivel_entrenamiento"),
                    rs.getBoolean("requiere_bozal")
            );
        }

        if (rs.getString("codigo_gato") != null) {
            return new GatoModel(
                    codigo, nombre, raza, sexo, edad, peso, idCliente,
                    rs.getString("temperamento"),
                    rs.getBoolean("requiere_transportadora")
            );
        }

        return null;
    }

    @Override
    public boolean actualizar(AnimalModel animal) {
        String sqlAnimal =
                "UPDATE animal SET nombre = ?, raza = ?, sexo = ?, " +
                        "edad = ?, peso = ?, id_cliente = ? WHERE codigo = ?";

        try (Connection conexion = conectar()) {
            conexion.setAutoCommit(false);

            try (PreparedStatement ps = conexion.prepareStatement(sqlAnimal)) {
                ps.setString(1, animal.getNombre());
                ps.setString(2, animal.getRaza());
                ps.setString(3, animal.getSexo());
                ps.setInt(4, animal.getEdad());
                ps.setDouble(5, animal.getPeso());
                ps.setInt(6, animal.getIdCliente());
                ps.setString(7, animal.getCodigo());

                if (ps.executeUpdate() == 0) {
                    conexion.rollback();
                    return false;
                }
            }

            actualizarSubtipo(conexion, animal);
            conexion.commit();
            return true;

        } catch (SQLException error) {
            System.out.println("Error al actualizar mascota: " + error.getMessage());
            return false;
        }
    }

    private void actualizarSubtipo(Connection conexion, AnimalModel animal)
            throws SQLException {

        if (animal instanceof PerroModel) {
            PerroModel perro = (PerroModel) animal;
            String sql =
                    "UPDATE perro SET nivel_entrenamiento = ?, " +
                            "requiere_bozal = ? WHERE codigo = ?";

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, perro.getNivelEntenamiento());
                ps.setBoolean(2, perro.isRequiereBozal());
                ps.setString(3, perro.getCodigo());

                if (ps.executeUpdate() == 0) {
                    throw new SQLException("No se encontró el registro del perro.");
                }
            }

        } else if (animal instanceof GatoModel) {
            GatoModel gato = (GatoModel) animal;
            String sql =
                    "UPDATE gato SET temperamento = ?, " +
                            "requiere_transportadora = ? WHERE codigo = ?";

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, gato.getTemperamento());
                ps.setBoolean(2, gato.isRequiereTransportadora());
                ps.setString(3, gato.getCodigo());

                if (ps.executeUpdate() == 0) {
                    throw new SQLException("No se encontró el registro del gato.");
                }
            }
        }
    }

    @Override
    public boolean eliminar(String codigo) {
        String sql = "DELETE FROM animal WHERE codigo = ?";

        try (Connection conexion = conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;

        } catch (SQLException error) {
            System.out.println(
                    "No se pudo eliminar la mascota. Comprueba si tiene consultas registradas. "
                            + error.getMessage()
            );
            return false;
        }
    }
}
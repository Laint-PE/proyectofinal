package dao;

import models.AnimalModel;
import java.util.List;

public interface AnimalDAO {
    boolean insertar(AnimalModel animal);
    List<AnimalModel> listarTodos();
    AnimalModel buscarPorCodigo(String codigo);
    List<AnimalModel> listarPorCliente(int idCliente);
    boolean actualizar(AnimalModel animal);
    boolean eliminar(String codigo);
}
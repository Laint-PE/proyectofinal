package dao;

import models.CategoriaModel;
import java.util.List;

public interface CategoriaDAO {
    boolean insertar(CategoriaModel categoria);
    List<CategoriaModel> listarTodos();
    CategoriaModel buscarPorId(int idCategoria);
    boolean actualizar(CategoriaModel categoria);
    boolean eliminar(int idCategoria);
}
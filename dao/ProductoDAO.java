package dao;

import models.ProductoModel;
import java.util.List;

public interface ProductoDAO {
    boolean insertar(ProductoModel producto);
    List<ProductoModel> listarTodos();
    ProductoModel buscarPorCodigo(String codigo);
    boolean actualizar(ProductoModel producto);
    boolean eliminar(String codigo);
    List<ProductoModel> listarPorCategoria(int idCategoria);
}
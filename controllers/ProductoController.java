package controllers;

import dao.ProductoDAO;
import models.ProductoModel;

import java.util.List;

public class ProductoController {
    private final ProductoDAO productoDAO;

    public ProductoController(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    public boolean registrar(ProductoModel producto) {
        if (producto == null
                || producto.getCodigo() == null
                || producto.getCodigo().length() != 8
                || producto.getNombre() == null
                || producto.getNombre().isBlank()
                || producto.getPrecio() < 0
                || producto.getStock() < 0
                || producto.getIdCategoria() <= 0) {
            return false;
        }

        return productoDAO.insertar(producto);
    }

    public List<ProductoModel> listarTodos() {
        return productoDAO.listarTodos();
    }

    public ProductoModel buscar(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        return productoDAO.buscarPorCodigo(codigo.trim());
    }

    public boolean actualizar(ProductoModel producto) {
        if (producto == null
                || producto.getCodigo() == null
                || producto.getNombre() == null
                || producto.getNombre().isBlank()
                || producto.getPrecio() < 0
                || producto.getStock() < 0
                || producto.getIdCategoria() <= 0) {
            return false;
        }

        return productoDAO.actualizar(producto);
    }

    public boolean eliminar(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return false;
        }

        return productoDAO.eliminar(codigo.trim());
    }

    public List<ProductoModel> listarPorCategoria(int idCategoria) {
        if (idCategoria <= 0) {
            return List.of();
        }

        return productoDAO.listarPorCategoria(idCategoria);
    }
}
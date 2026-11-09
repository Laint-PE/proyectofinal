package controllers;

import dao.CategoriaDAO;
import models.CategoriaModel;

import java.util.List;

public class CategoriaController {
    private final CategoriaDAO categoriaDAO;

    public CategoriaController(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    public boolean registrar(String nombre, String descripcion) {
        if (nombre == null || nombre.isBlank()) {
            return false;
        }

        CategoriaModel categoria =
                new CategoriaModel(0, nombre.trim(), descripcion);

        return categoriaDAO.insertar(categoria);
    }

    public List<CategoriaModel> listarTodas() {
        return categoriaDAO.listarTodos();
    }

    public CategoriaModel buscar(int idCategoria) {
        if (idCategoria <= 0) {
            return null;
        }

        return categoriaDAO.buscarPorId(idCategoria);
    }

    public boolean actualizar(
            int idCategoria, String nombre, String descripcion) {

        if (idCategoria <= 0 || nombre == null || nombre.isBlank()) {
            return false;
        }

        CategoriaModel categoria =
                new CategoriaModel(idCategoria, nombre.trim(), descripcion);

        return categoriaDAO.actualizar(categoria);
    }

    public boolean eliminar(int idCategoria) {
        if (idCategoria <= 0) {
            return false;
        }

        return categoriaDAO.eliminar(idCategoria);
    }
}
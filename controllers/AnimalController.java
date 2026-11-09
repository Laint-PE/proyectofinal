package controllers;

import dao.AnimalDAO;
import models.AnimalModel;

import java.util.List;

public class AnimalController {
    private final AnimalDAO animalDAO;

    public AnimalController(AnimalDAO animalDAO) {
        this.animalDAO = animalDAO;
    }

    public boolean registrar(AnimalModel animal) {
        if (animal == null
                || animal.getCodigo() == null
                || animal.getCodigo().length() != 8
                || animal.getNombre() == null
                || animal.getNombre().isBlank()
                || animal.getRaza() == null
                || animal.getRaza().isBlank()
                || animal.getEdad() < 0
                || animal.getPeso() <= 0
                || animal.getIdCliente() <= 0) {
            return false;
        }

        return animalDAO.insertar(animal);
    }

    public List<AnimalModel> listarTodos() {
        return animalDAO.listarTodos();
    }

    public AnimalModel buscar(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        return animalDAO.buscarPorCodigo(codigo);
    }

    public List<AnimalModel> listarPorCliente(int idCliente) {
        if (idCliente <= 0) {
            return List.of();
        }

        return animalDAO.listarPorCliente(idCliente);
    }

    public boolean actualizar(AnimalModel animal) {
        if (animal == null
                || animal.getNombre() == null
                || animal.getNombre().isBlank()
                || animal.getEdad() < 0
                || animal.getPeso() <= 0
                || animal.getIdCliente() <= 0) {
            return false;
        }

        return animalDAO.actualizar(animal);
    }

    public boolean eliminar(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return false;
        }

        return animalDAO.eliminar(codigo);
    }
}
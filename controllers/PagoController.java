package controllers;

import dao.PagoDAO;
import models.PagoModel;

import java.util.List;

public class PagoController {
    private final PagoDAO pagoDAO;

    public PagoController(PagoDAO pagoDAO) {
        this.pagoDAO = pagoDAO;
    }

    public PagoModel buscarPorPedido(int idPedido) {
        if (idPedido <= 0) {
            return null;
        }

        return pagoDAO.buscarPorPedido(idPedido);
    }

    public List<PagoModel> listarTodos() {
        return pagoDAO.listarTodos();
    }
}
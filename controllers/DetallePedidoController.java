package controllers;

import dao.DetallePedidoDAO;
import models.DetallePedidoModel;

import java.util.List;

public class DetallePedidoController {
    private final DetallePedidoDAO detallePedidoDAO;

    public DetallePedidoController(DetallePedidoDAO detallePedidoDAO) {
        this.detallePedidoDAO = detallePedidoDAO;
    }

    public List<DetallePedidoModel> listarPorPedido(int idPedido) {
        if (idPedido <= 0) {
            return List.of();
        }

        return detallePedidoDAO.listarPorPedido(idPedido);
    }
}
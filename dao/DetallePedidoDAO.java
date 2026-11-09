package dao;

import models.DetallePedidoModel;
import java.util.List;

public interface DetallePedidoDAO {
    List<DetallePedidoModel> listarPorPedido(int idPedido);
}
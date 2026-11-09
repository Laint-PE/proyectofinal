package dao;

import models.DetallePedidoModel;
import models.PagoModel;
import models.PedidoModel;

import java.util.List;

public interface PedidoDAO {
    boolean registrarVenta(
            PedidoModel pedido,
            List<DetallePedidoModel> detalles,
            PagoModel pago
    );

    List<PedidoModel> listarTodos();
    PedidoModel buscarPorId(int idPedido);
    boolean actualizarEstado(int idPedido, String estado);
}
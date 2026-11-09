package dao;

import models.PagoModel;
import java.util.List;

public interface PagoDAO {
    PagoModel buscarPorPedido(int idPedido);
    List<PagoModel> listarTodos();
}
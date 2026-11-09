package dao;

import models.ClienteModel;
import java.util.List;

public interface ClienteDAO {
    boolean insertar(ClienteModel cliente);
    List<ClienteModel> listarTodos();
    ClienteModel buscarPorId(int idCliente);
    boolean actualizar(ClienteModel cliente);
    boolean eliminar(int idCliente);
}
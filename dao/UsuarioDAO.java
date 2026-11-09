package dao;

import models.UsuarioModel;
import java.util.List;

public interface UsuarioDAO {
    UsuarioModel autenticar(String usuario, String password);
    List<UsuarioModel> listarTodos();
    boolean cambiarEstado(int idUsuario, boolean activo);
    boolean eliminar(int idUsuario);
}
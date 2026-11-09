package dao;

import models.ConsultaModel;
import java.util.List;

public interface ConsultaDAO {
    boolean insertar(ConsultaModel consulta);
    List<ConsultaModel> listarTodos();
    ConsultaModel buscarPorId(int idConsulta);
    int contarConsultasDeHoy();
    boolean actualizar(ConsultaModel consulta);
    boolean eliminar(int idConsulta);
}
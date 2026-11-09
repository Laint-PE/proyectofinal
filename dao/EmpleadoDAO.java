package dao;

import models.EmpleadoModel;
import java.util.List;

public interface EmpleadoDAO {
    boolean insertar(EmpleadoModel empleado);
    List<EmpleadoModel> listarTodos();
    List<EmpleadoModel> listarPorCargo(String cargo);
    EmpleadoModel buscarPorId(int idEmpleado);
    boolean actualizar(EmpleadoModel empleado);
    boolean eliminar(int idEmpleado);
}
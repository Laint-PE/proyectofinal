package controllers;

import dao.EmpleadoDAO;
import models.EmpleadoModel;

import java.util.List;

public class PersonalController {
    private final EmpleadoDAO empleadoDAO;

    public PersonalController(EmpleadoDAO empleadoDAO) {
        this.empleadoDAO = empleadoDAO;
    }

    public boolean registrar(
            String dni, String nombre, String apellido, int edad,
            String telefono, String cargo, int idSucursal) {

        if (dni == null || dni.length() != 8
                || nombre == null || nombre.isBlank()
                || apellido == null || apellido.isBlank()
                || edad < 18
                || telefono == null || telefono.isBlank()
                || cargo == null || cargo.isBlank()
                || idSucursal <= 0) {
            return false;
        }

        EmpleadoModel empleado = new EmpleadoModel(
                dni, nombre.trim(), apellido.trim(), edad,
                telefono, cargo.trim().toUpperCase(),
                idSucursal, "ACTIVO", 0
        );

        return empleadoDAO.insertar(empleado);
    }

    public List<EmpleadoModel> listarTodos() {
        return empleadoDAO.listarTodos();
    }

    public List<EmpleadoModel> listarPorCargo(String cargo) {
        return empleadoDAO.listarPorCargo(cargo);
    }

    public EmpleadoModel buscar(int idEmpleado) {
        if (idEmpleado <= 0) {
            return null;
        }

        return empleadoDAO.buscarPorId(idEmpleado);
    }

    public boolean actualizar(
            int idEmpleado, String dni, String nombre, String apellido,
            int edad, String telefono, String cargo, int idSucursal,
            String estado) {

        if (idEmpleado <= 0 || dni == null || dni.length() != 8
                || nombre == null || nombre.isBlank()
                || apellido == null || apellido.isBlank()
                || edad < 18 || telefono == null || telefono.isBlank()
                || cargo == null || cargo.isBlank()
                || idSucursal <= 0 || estado == null || estado.isBlank()) {
            return false;
        }

        EmpleadoModel empleado = new EmpleadoModel(
                dni, nombre.trim(), apellido.trim(), edad,
                telefono, cargo.trim().toUpperCase(),
                idSucursal, estado.trim().toUpperCase(), idEmpleado
        );

        return empleadoDAO.actualizar(empleado);
    }

    public boolean eliminar(int idEmpleado) {
        if (idEmpleado <= 0) {
            return false;
        }

        return empleadoDAO.eliminar(idEmpleado);
    }
}
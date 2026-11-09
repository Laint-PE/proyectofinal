package controllers;

import dao.SucursalDAO;
import models.SucursalModel;

import java.util.List;

public class SucursalController {
    private final SucursalDAO sucursalDAO;

    public SucursalController(SucursalDAO sucursalDAO) {
        this.sucursalDAO = sucursalDAO;
    }

    public boolean registrar(
            String nombre, String direccion, String telefono,
            String horarioAtencion) {

        if (nombre == null || nombre.isBlank()
                || direccion == null || direccion.isBlank()
                || telefono == null || telefono.isBlank()
                || horarioAtencion == null || horarioAtencion.isBlank()) {
            return false;
        }

        SucursalModel sucursal = new SucursalModel(
                0, nombre.trim(), direccion.trim(),
                telefono.trim(), horarioAtencion.trim(), true
        );

        return sucursalDAO.insertar(sucursal);
    }

    public List<SucursalModel> listarTodas() {
        return sucursalDAO.listarTodas();
    }

    public SucursalModel buscar(int idSucursal) {
        if (idSucursal <= 0) {
            return null;
        }

        return sucursalDAO.buscarPorId(idSucursal);
    }

    public boolean actualizar(
            int idSucursal, String nombre, String direccion,
            String telefono, String horarioAtencion, boolean activo) {

        if (idSucursal <= 0
                || nombre == null || nombre.isBlank()
                || direccion == null || direccion.isBlank()
                || telefono == null || telefono.isBlank()
                || horarioAtencion == null || horarioAtencion.isBlank()) {
            return false;
        }

        SucursalModel sucursal = new SucursalModel(
                idSucursal, nombre.trim(), direccion.trim(),
                telefono.trim(), horarioAtencion.trim(), activo
        );

        return sucursalDAO.actualizar(sucursal);
    }

    public boolean eliminar(int idSucursal) {
        if (idSucursal <= 0) {
            return false;
        }

        return sucursalDAO.eliminar(idSucursal);
    }
}
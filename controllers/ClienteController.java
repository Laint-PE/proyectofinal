package controllers;

import dao.ClienteDAO;
import models.ClienteModel;

import java.util.List;

public class ClienteController {
    private final ClienteDAO clienteDAO;

    public ClienteController(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public boolean registrar(
            String dni, String nombre, String apellido, int edad,
            String telefono, String correo, String direccion) {

        if (dni == null || dni.length() != 8
                || nombre == null || nombre.isBlank()
                || apellido == null || apellido.isBlank()
                || edad < 0
                || telefono == null || telefono.isBlank()) {
            return false;
        }

        ClienteModel cliente = new ClienteModel(
                dni, nombre.trim(), apellido.trim(), edad,
                telefono, correo, direccion, 0
        );

        return clienteDAO.insertar(cliente);
    }

    public List<ClienteModel> listarTodos() {
        return clienteDAO.listarTodos();
    }

    public ClienteModel buscar(int idCliente) {
        if (idCliente <= 0) {
            return null;
        }

        return clienteDAO.buscarPorId(idCliente);
    }

    public boolean actualizar(
            int idCliente, String dni, String nombre, String apellido, int edad,
            String telefono, String correo, String direccion) {

        if (idCliente <= 0 || dni == null || dni.length() != 8
                || nombre == null || nombre.isBlank()
                || apellido == null || apellido.isBlank()
                || edad < 0
                || telefono == null || telefono.isBlank()) {
            return false;
        }

        ClienteModel cliente = new ClienteModel(
                dni, nombre.trim(), apellido.trim(), edad,
                telefono, correo, direccion, idCliente
        );

        return clienteDAO.actualizar(cliente);
    }

    public boolean eliminar(int idCliente) {
        if (idCliente <= 0) {
            return false;
        }

        return clienteDAO.eliminar(idCliente);
    }
}
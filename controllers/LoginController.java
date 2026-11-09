package controllers;

import dao.UsuarioDAO;
import models.UsuarioModel;

public class LoginController {
    private final UsuarioDAO usuarioDAO;

    public LoginController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public UsuarioModel autenticar(String usuario, String password) {
        if (usuario == null || usuario.isBlank()
                || password == null || password.isBlank()) {
            return null;
        }

        return usuarioDAO.autenticar(usuario.trim(), password);
    }
}
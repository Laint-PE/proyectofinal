package views;

import controllers.LoginController;
import models.UsuarioModel;

import java.util.Scanner;

public class LoginView {
    private final LoginController loginController;
    private final Scanner scanner;

    public LoginView(LoginController loginController, Scanner scanner) {
        this.loginController = loginController;
        this.scanner = scanner;
    }

    public UsuarioModel iniciarSesion() {
        System.out.println("--- INICIO DE SESIÓN ---");

        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();

        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        UsuarioModel usuarioAutenticado =
                loginController.autenticar(usuario, password);

        if (usuarioAutenticado == null) {
            System.out.println("Usuario o contraseña incorrectos, o cuenta inactiva.");
            return null;
        }

        System.out.println("Bienvenido. Rol: " + usuarioAutenticado.getRol());
        return usuarioAutenticado;
    }
}
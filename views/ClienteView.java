package views;

import controllers.ClienteController;
import models.ClienteModel;

import java.util.List;
import java.util.Scanner;

public class ClienteView {
    private final ClienteController clienteController;
    private final Scanner scanner;

    public ClienteView(ClienteController clienteController, Scanner scanner) {
        this.clienteController = clienteController;
        this.scanner = scanner;
    }

    public void iniciar() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- CLIENTES ---");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Buscar cliente por ID");
            System.out.println("4. Actualizar cliente");
            System.out.println("5. Eliminar cliente");
            System.out.println("0. Volver");
            System.out.print("Ingrese una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        registrar();
                        break;
                    case 2:
                        listar();
                        break;
                    case 3:
                        buscar();
                        break;
                    case 4:
                        actualizar();
                        break;
                    case 5:
                        eliminar();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException error) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    private void registrar() {
        System.out.print("DNI (8 caracteres): ");
        String dni = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        int edad = leerEntero("Edad: ");

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        if (clienteController.registrar(
                dni, nombre, apellido, edad, telefono, correo, direccion)) {
            System.out.println("Cliente registrado.");
        } else {
            System.out.println("No se pudo registrar el cliente.");
        }
    }

    private void listar() {
        List<ClienteModel> clientes = clienteController.listarTodos();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        for (ClienteModel cliente : clientes) {
            mostrar(cliente);
        }
    }

    private void buscar() {
        int id = leerEntero("ID del cliente: ");
        ClienteModel cliente = clienteController.buscar(id);

        if (cliente == null) {
            System.out.println("No se encontró el cliente.");
        } else {
            mostrar(cliente);
        }
    }

    private void actualizar() {
        int id = leerEntero("ID del cliente que actualizará: ");

        System.out.print("DNI: ");
        String dni = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        int edad = leerEntero("Edad: ");

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        if (clienteController.actualizar(
                id, dni, nombre, apellido, edad, telefono, correo, direccion)) {
            System.out.println("Cliente actualizado.");
        } else {
            System.out.println("No se pudo actualizar el cliente.");
        }
    }

    private void eliminar() {
        int id = leerEntero("ID del cliente que eliminará: ");

        if (clienteController.eliminar(id)) {
            System.out.println("Cliente eliminado.");
        } else {
            System.out.println("No se pudo eliminar el cliente.");
        }
    }

    private int leerEntero(String mensaje) {
        System.out.print(mensaje);

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException error) {
            return -1;
        }
    }

    private void mostrar(ClienteModel cliente) {
        System.out.println(
                cliente.getIdCliente() + " | "
                        + cliente.getDni() + " | "
                        + cliente.getNombre() + " "
                        + cliente.getApellido() + " | Teléfono: "
                        + cliente.getTelefono() + " | Correo: "
                        + cliente.getCorreo() + " | Dirección: "
                        + cliente.getDireccion()
        );
    }
}
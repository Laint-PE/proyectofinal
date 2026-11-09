package views;

import controllers.PersonalController;
import models.EmpleadoModel;

import java.util.List;
import java.util.Scanner;

public class PersonalView {
    private final PersonalController personalController;
    private final Scanner scanner;

    public PersonalView(PersonalController personalController, Scanner scanner) {
        this.personalController = personalController;
        this.scanner = scanner;
    }

    public void iniciar() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- PERSONAL ---");
            System.out.println("1. Registrar empleado, veterinario o repartidor");
            System.out.println("2. Listar personal");
            System.out.println("3. Listar por cargo");
            System.out.println("4. Buscar empleado");
            System.out.println("5. Eliminar empleado");
            System.out.println("0. Volver");
            System.out.print("Ingrese una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        registrar();
                        break;
                    case 2:
                        listar(personalController.listarTodos());
                        break;
                    case 3:
                        listarPorCargo();
                        break;
                    case 4:
                        buscar();
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

        System.out.print("Cargo (EMPLEADO/VETERINARIO/REPARTIDOR): ");
        String cargo = scanner.nextLine();

        int idSucursal = leerEntero("ID de sucursal: ");

        if (personalController.registrar(
                dni, nombre, apellido, edad, telefono, cargo, idSucursal)) {
            System.out.println("Personal registrado.");
        } else {
            System.out.println("No se pudo registrar. Revise los datos y la sucursal.");
        }
    }

    private void listarPorCargo() {
        System.out.print("Cargo que desea listar: ");
        String cargo = scanner.nextLine().trim().toUpperCase();

        listar(personalController.listarPorCargo(cargo));
    }

    private void listar(List<EmpleadoModel> empleados) {
        if (empleados.isEmpty()) {
            System.out.println("No se encontraron empleados.");
            return;
        }

        for (EmpleadoModel empleado : empleados) {
            mostrar(empleado);
        }
    }

    private void buscar() {
        int id = leerEntero("ID del empleado: ");
        EmpleadoModel empleado = personalController.buscar(id);

        if (empleado == null) {
            System.out.println("No se encontró el empleado.");
        } else {
            mostrar(empleado);
        }
    }

    private void eliminar() {
        int id = leerEntero("ID del empleado que eliminará: ");

        if (personalController.eliminar(id)) {
            System.out.println("Empleado eliminado.");
        } else {
            System.out.println(
                    "No se pudo eliminar; puede tener pedidos, consultas o usuario asociados."
            );
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

    private void mostrar(EmpleadoModel empleado) {
        System.out.println(
                empleado.getIdEmpleado() + " | "
                        + empleado.getDni() + " | "
                        + empleado.getNombre() + " "
                        + empleado.getApellido() + " | "
                        + empleado.getCargo() + " | Sucursal: "
                        + empleado.getIdSucursal() + " | "
                        + empleado.getEstado()
        );
    }
}
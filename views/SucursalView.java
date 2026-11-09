package views;

import controllers.SucursalController;
import models.SucursalModel;

import java.util.List;
import java.util.Scanner;

public class SucursalView {
    private final SucursalController sucursalController;
    private final Scanner scanner;

    public SucursalView(
            SucursalController sucursalController, Scanner scanner) {
        this.sucursalController = sucursalController;
        this.scanner = scanner;
    }

    public void iniciar() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- SUCURSALES ---");
            System.out.println("1. Registrar sucursal");
            System.out.println("2. Listar sucursales");
            System.out.println("3. Buscar sucursal");
            System.out.println("4. Actualizar sucursal");
            System.out.println("5. Eliminar sucursal");
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
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("Horario de atención: ");
        String horario = scanner.nextLine();

        if (sucursalController.registrar(nombre, direccion, telefono, horario)) {
            System.out.println("Sucursal registrada.");
        } else {
            System.out.println("No se pudo registrar la sucursal.");
        }
    }

    private void listar() {
        List<SucursalModel> sucursales = sucursalController.listarTodas();

        if (sucursales.isEmpty()) {
            System.out.println("No hay sucursales registradas.");
            return;
        }

        for (SucursalModel sucursal : sucursales) {
            mostrar(sucursal);
        }
    }

    private void buscar() {
        int id = leerEntero("ID de la sucursal: ");
        SucursalModel sucursal = sucursalController.buscar(id);

        if (sucursal == null) {
            System.out.println("No se encontró la sucursal.");
        } else {
            mostrar(sucursal);
        }
    }

    private void actualizar() {
        int id = leerEntero("ID de la sucursal: ");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("Horario de atención: ");
        String horario = scanner.nextLine();

        System.out.print("¿Está activa? (s/n): ");
        boolean activo = scanner.nextLine().equalsIgnoreCase("s");

        if (sucursalController.actualizar(
                id, nombre, direccion, telefono, horario, activo)) {
            System.out.println("Sucursal actualizada.");
        } else {
            System.out.println("No se pudo actualizar la sucursal.");
        }
    }

    private void eliminar() {
        int id = leerEntero("ID de la sucursal que eliminará: ");

        if (sucursalController.eliminar(id)) {
            System.out.println("Sucursal eliminada.");
        } else {
            System.out.println("No se pudo eliminar la sucursal.");
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

    private void mostrar(SucursalModel sucursal) {
        System.out.println(
                sucursal.getIdSucursal() + " | "
                        + sucursal.getNombre() + " | "
                        + sucursal.getDireccion() + " | Teléfono: "
                        + sucursal.getTelefono() + " | Horario: "
                        + sucursal.getHorarioAtencion() + " | Activa: "
                        + sucursal.isActivo()
        );
    }
}
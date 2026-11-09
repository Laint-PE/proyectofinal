package views;

import controllers.PromocionController;
import models.PromocionModel;

import java.util.List;
import java.util.Scanner;

public class PromocionView {
    private final PromocionController promocionController;
    private final Scanner scanner;

    public PromocionView(
            PromocionController promocionController, Scanner scanner) {
        this.promocionController = promocionController;
        this.scanner = scanner;
    }

    public void iniciar() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- PROMOCIONES ---");
            System.out.println("1. Registrar promoción");
            System.out.println("2. Listar promociones");
            System.out.println("3. Buscar promoción");
            System.out.println("4. Actualizar promoción");
            System.out.println("5. Eliminar promoción");
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

        double descuento = leerDecimal("Descuento en porcentaje: ");

        System.out.print("Fecha de inicio (AAAA-MM-DD): ");
        String inicio = scanner.nextLine();

        System.out.print("Fecha de fin (AAAA-MM-DD): ");
        String fin = scanner.nextLine();

        int cantidad = leerEntero("Cantidad mínima de productos: ");

        if (promocionController.registrar(
                nombre, descuento, inicio, fin, cantidad)) {
            System.out.println("Promoción registrada.");
        } else {
            System.out.println("No se pudo registrar la promoción.");
        }
    }

    private void listar() {
        List<PromocionModel> promociones =
                promocionController.listarTodas();

        if (promociones.isEmpty()) {
            System.out.println("No hay promociones registradas.");
            return;
        }

        for (PromocionModel promocion : promociones) {
            mostrar(promocion);
        }
    }

    private void buscar() {
        int id = leerEntero("ID de la promoción: ");
        PromocionModel promocion = promocionController.buscar(id);

        if (promocion == null) {
            System.out.println("No se encontró la promoción.");
        } else {
            mostrar(promocion);
        }
    }

    private void actualizar() {
        int id = leerEntero("ID de la promoción que actualizará: ");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        double descuento = leerDecimal("Descuento en porcentaje: ");

        System.out.print("Fecha de inicio (AAAA-MM-DD): ");
        String inicio = scanner.nextLine();

        System.out.print("Fecha de fin (AAAA-MM-DD): ");
        String fin = scanner.nextLine();

        int cantidad = leerEntero("Cantidad mínima: ");

        if (promocionController.actualizar(
                id, nombre, descuento, inicio, fin, cantidad)) {
            System.out.println("Promoción actualizada.");
        } else {
            System.out.println("No se pudo actualizar la promoción.");
        }
    }

    private void eliminar() {
        int id = leerEntero("ID de la promoción que eliminará: ");

        if (promocionController.eliminar(id)) {
            System.out.println("Promoción eliminada.");
        } else {
            System.out.println("No se pudo eliminar la promoción.");
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

    private double leerDecimal(String mensaje) {
        System.out.print(mensaje);

        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException error) {
            return -1;
        }
    }

    private void mostrar(PromocionModel promocion) {
        System.out.println(
                promocion.getIdPromocion() + " | "
                        + promocion.getNombre() + " | "
                        + promocion.getDescuento() + "% | "
                        + promocion.getInicioDescuento() + " al "
                        + promocion.getFinDescuento() + " | Mínimo: "
                        + promocion.getCantidadMinima()
        );
    }
}
package views;

import controllers.PedidoController;
import models.PedidoModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PedidoView {
    private final PedidoController pedidoController;
    private final Scanner scanner;

    public PedidoView(PedidoController pedidoController, Scanner scanner) {
        this.pedidoController = pedidoController;
        this.scanner = scanner;
    }

    public void iniciar() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- VENTAS ---");
            System.out.println("1. Nuevo pedido");
            System.out.println("2. Pedidos registrados");
            System.out.println("3. Buscar pedido");
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
        int idCliente = leerEntero("ID del cliente: ");
        int idEmpleado = leerEntero("ID del empleado que atiende: ");

        System.out.print("Tipo de entrega (TIENDA/DELIVERY): ");
        String tipoEntrega = scanner.nextLine();

        String direccion = null;
        Integer idRepartidor = null;

        if (tipoEntrega.equalsIgnoreCase("DELIVERY")) {
            System.out.print("Dirección de entrega: ");
            direccion = scanner.nextLine();

            int repartidor = leerEntero(
                    "ID del repartidor (0 si aún no se asigna): "
            );

            if (repartidor > 0) {
                idRepartidor = repartidor;
            }
        }

        PedidoModel pedido = new PedidoModel(
                0, idCliente, idEmpleado, null, "PENDIENTE",
                tipoEntrega, direccion, idRepartidor
        );

        List<String> codigos = new ArrayList<>();
        List<Integer> cantidades = new ArrayList<>();
        String continuar = "s";

        while (continuar.equalsIgnoreCase("s")) {
            System.out.print("Código del producto: ");
            codigos.add(scanner.nextLine());

            cantidades.add(leerEntero("Cantidad: "));

            System.out.print("¿Agregar otro producto? (s/n): ");
            continuar = scanner.nextLine();
        }

        System.out.print("Método de pago: ");
        String metodoPago = scanner.nextLine();

        double recibido = leerDecimal("Monto recibido: ");

        if (pedidoController.registrarVenta(
                pedido, codigos, cantidades, metodoPago, recibido)) {
            System.out.println("Pedido y pago registrados; stock actualizado.");
        } else {
            System.out.println(
                    "No se registró la venta. Revise los datos, el pago y el stock."
            );
        }
    }

    private void listar() {
        List<PedidoModel> pedidos = pedidoController.listarTodos();

        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
            return;
        }

        for (PedidoModel pedido : pedidos) {
            mostrar(pedido);
        }
    }

    private void buscar() {
        int idPedido = leerEntero("ID del pedido: ");
        PedidoModel pedido = pedidoController.buscar(idPedido);

        if (pedido == null) {
            System.out.println("No se encontró el pedido.");
        } else {
            mostrar(pedido);
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

    private void mostrar(PedidoModel pedido) {
        System.out.println(
                "Pedido " + pedido.getIdPedido()
                        + " | Cliente: " + pedido.getIdCliente()
                        + " | Fecha: " + pedido.getFecha()
                        + " | Estado: " + pedido.getEstado()
                        + " | Entrega: " + pedido.getTipoEntrega()
                        + " | Repartidor: " + pedido.getIdRepartidor()
        );
    }
}
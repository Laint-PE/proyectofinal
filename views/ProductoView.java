package views;

import controllers.ProductoController;
import models.AccesorioModel;
import models.AlimentoModel;
import models.MedicamentoModel;
import models.ProductoModel;

import java.util.List;
import java.util.Scanner;

public class ProductoView {
    private final ProductoController productoController;
    private final Scanner scanner;

    public ProductoView(
            ProductoController productoController, Scanner scanner) {
        this.productoController = productoController;
        this.scanner = scanner;
    }

    public void iniciar() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- PRODUCTOS ---");
            System.out.println("1. Registrar producto");
            System.out.println("2. Listar productos");
            System.out.println("3. Buscar producto");
            System.out.println("4. Actualizar producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Listar por categoría");
            System.out.println("0. Volver");
            System.out.print("Ingrese una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        registrar();
                        break;
                    case 2:
                        listar(productoController.listarTodos());
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
                    case 6:
                        listarPorCategoria();
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
        System.out.println("Tipo de producto:");
        System.out.println("1. Accesorio");
        System.out.println("2. Alimento");
        System.out.println("3. Medicamento");

        int tipo = leerEntero("Seleccione: ");

        System.out.print("Código (8 caracteres): ");
        String codigo = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        double precio = leerDecimal("Precio: ");
        int stock = leerEntero("Stock: ");
        int idCategoria = leerEntero("ID de categoría: ");

        ProductoModel producto;

        if (tipo == 1) {
            System.out.print("Tipo de accesorio: ");
            String tipoAccesorio = scanner.nextLine();

            System.out.print("Material: ");
            String material = scanner.nextLine();

            System.out.print("Ropa (si aplica): ");
            String ropa = scanner.nextLine();

            producto = new AccesorioModel(
                    codigo, nombre, precio, stock, idCategoria,
                    tipoAccesorio, material, ropa
            );

        } else if (tipo == 2) {
            System.out.print("Tipo de animal (PERRO/GATO/AMBOS): ");
            String tipoAnimal = scanner.nextLine();

            System.out.print("Vencimiento (AAAA-MM-DD, vacío si no aplica): ");
            String vencimiento = scanner.nextLine();
            if (vencimiento.isBlank()) {
                vencimiento = null;
            }

            System.out.print("Sabor: ");
            String sabor = scanner.nextLine();

            producto = new AlimentoModel(
                    codigo, nombre, precio, stock, idCategoria,
                    tipoAnimal, vencimiento, sabor
            );

        } else if (tipo == 3) {
            System.out.print("Vencimiento (AAAA-MM-DD, vacío si no aplica): ");
            String vencimiento = scanner.nextLine();
            if (vencimiento.isBlank()) {
                vencimiento = null;
            }

            System.out.print("Dosis indicada: ");
            String dosis = scanner.nextLine();

            producto = new MedicamentoModel(
                    codigo, nombre, precio, stock, idCategoria,
                    vencimiento, dosis
            );

        } else {
            System.out.println("Tipo de producto no válido.");
            return;
        }

        if (productoController.registrar(producto)) {
            System.out.println("Producto registrado.");
        } else {
            System.out.println("No se pudo registrar el producto.");
        }
    }

    private void buscar() {
        System.out.print("Código del producto: ");
        String codigo = scanner.nextLine();

        ProductoModel producto = productoController.buscar(codigo);

        if (producto == null) {
            System.out.println("No se encontró el producto.");
        } else {
            mostrar(producto);
        }
    }

    private void actualizar() {
        System.out.print("Código del producto que actualizará: ");
        String codigo = scanner.nextLine();

        ProductoModel producto = productoController.buscar(codigo);

        if (producto == null) {
            System.out.println("No se encontró el producto.");
            return;
        }

        System.out.print("Nuevo nombre: ");
        producto.setNombre(scanner.nextLine());

        producto.setPrecio(leerDecimal("Nuevo precio: "));
        producto.setStock(leerEntero("Nuevo stock: "));
        producto.setIdCategoria(leerEntero("Nuevo ID de categoría: "));

        if (producto instanceof AccesorioModel) {
            AccesorioModel accesorio = (AccesorioModel) producto;

            System.out.print("Tipo de accesorio: ");
            accesorio.setTipo(scanner.nextLine());

            System.out.print("Material: ");
            accesorio.setMaterial(scanner.nextLine());

            System.out.print("Ropa (si aplica): ");
            accesorio.setRopa(scanner.nextLine());

        } else if (producto instanceof AlimentoModel) {
            AlimentoModel alimento = (AlimentoModel) producto;

            System.out.print("Tipo de animal: ");
            alimento.setTipoAnimal(scanner.nextLine());

            System.out.print("Vencimiento (AAAA-MM-DD, vacío si no aplica): ");
            String vencimiento = scanner.nextLine();
            alimento.setFechaVencimiento(
                    vencimiento.isBlank() ? null : vencimiento
            );

            System.out.print("Sabor: ");
            alimento.setSabor(scanner.nextLine());

        } else if (producto instanceof MedicamentoModel) {
            MedicamentoModel medicamento = (MedicamentoModel) producto;

            System.out.print("Vencimiento (AAAA-MM-DD, vacío si no aplica): ");
            String vencimiento = scanner.nextLine();
            medicamento.setFechaVencimiento(
                    vencimiento.isBlank() ? null : vencimiento
            );

            System.out.print("Dosis indicada: ");
            medicamento.setDosis(scanner.nextLine());
        }

        if (productoController.actualizar(producto)) {
            System.out.println("Producto actualizado.");
        } else {
            System.out.println("No se pudo actualizar el producto.");
        }
    }

    private void eliminar() {
        System.out.print("Código del producto que eliminará: ");
        String codigo = scanner.nextLine();

        if (productoController.eliminar(codigo)) {
            System.out.println("Producto eliminado.");
        } else {
            System.out.println("No se pudo eliminar el producto.");
        }
    }

    private void listarPorCategoria() {
        int idCategoria = leerEntero("ID de la categoría: ");
        listar(productoController.listarPorCategoria(idCategoria));
    }

    private void listar(List<ProductoModel> productos) {
        if (productos.isEmpty()) {
            System.out.println("No hay productos para mostrar.");
            return;
        }

        for (ProductoModel producto : productos) {
            mostrar(producto);
        }
    }

    private void mostrar(ProductoModel producto) {
        System.out.println(
                producto.getCodigo() + " | "
                        + producto.getNombre() + " | S/ "
                        + producto.getPrecio() + " | Stock: "
                        + producto.getStock() + " | Categoría: "
                        + producto.getIdCategoria()
        );

        if (producto instanceof AccesorioModel) {
            AccesorioModel accesorio = (AccesorioModel) producto;
            System.out.println(
                    "Accesorio | " + accesorio.getTipo()
                            + " | Material: " + accesorio.getMaterial()
                            + " | Ropa: " + accesorio.getRopa()
            );
        } else if (producto instanceof AlimentoModel) {
            AlimentoModel alimento = (AlimentoModel) producto;
            System.out.println(
                    "Alimento | Para: " + alimento.getTipoAnimal()
                            + " | Vence: " + alimento.getFechaVencimiento()
                            + " | Sabor: " + alimento.getSabor()
            );
        } else if (producto instanceof MedicamentoModel) {
            MedicamentoModel medicamento = (MedicamentoModel) producto;
            System.out.println(
                    "Medicamento | Vence: "
                            + medicamento.getFechaVencimiento()
                            + " | Dosis: " + medicamento.getDosis()
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

    private double leerDecimal(String mensaje) {
        System.out.print(mensaje);

        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException error) {
            return -1;
        }
    }
}
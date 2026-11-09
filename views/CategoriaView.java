package views;

import controllers.CategoriaController;
import models.CategoriaModel;

import java.util.List;
import java.util.Scanner;

public class CategoriaView {
    private final CategoriaController categoriaController;
    private final Scanner scanner;

    public CategoriaView(
            CategoriaController categoriaController, Scanner scanner) {
        this.categoriaController = categoriaController;
        this.scanner = scanner;
    }

    public void iniciar() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- CATEGORÍAS ---");
            System.out.println("1. Registrar categoría");
            System.out.println("2. Listar categorías");
            System.out.println("3. Buscar categoría");
            System.out.println("4. Actualizar categoría");
            System.out.println("5. Eliminar categoría");
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

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        if (categoriaController.registrar(nombre, descripcion)) {
            System.out.println("Categoría registrada.");
        } else {
            System.out.println("No se pudo registrar la categoría.");
        }
    }

    private void listar() {
        List<CategoriaModel> categorias = categoriaController.listarTodas();

        if (categorias.isEmpty()) {
            System.out.println("No hay categorías registradas.");
            return;
        }

        for (CategoriaModel categoria : categorias) {
            mostrar(categoria);
        }
    }

    private void buscar() {
        int id = leerId("ID de la categoría: ");
        CategoriaModel categoria = categoriaController.buscar(id);

        if (categoria == null) {
            System.out.println("No se encontró la categoría.");
        } else {
            mostrar(categoria);
        }
    }

    private void actualizar() {
        int id = leerId("ID de la categoría que actualizará: ");

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Nueva descripción: ");
        String descripcion = scanner.nextLine();

        if (categoriaController.actualizar(id, nombre, descripcion)) {
            System.out.println("Categoría actualizada.");
        } else {
            System.out.println("No se pudo actualizar la categoría.");
        }
    }

    private void eliminar() {
        int id = leerId("ID de la categoría que eliminará: ");

        if (categoriaController.eliminar(id)) {
            System.out.println("Categoría eliminada.");
        } else {
            System.out.println("No se pudo eliminar. Revise si tiene productos asociados.");
        }
    }

    private int leerId(String mensaje) {
        System.out.print(mensaje);

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException error) {
            return -1;
        }
    }

    private void mostrar(CategoriaModel categoria) {
        System.out.println(
                categoria.getIdCategoria() + " | " +
                        categoria.getNombre() + " | " +
                        categoria.getDescripcion()
        );
    }
}
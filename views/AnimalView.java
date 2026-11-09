package views;

import controllers.AnimalController;
import models.AnimalModel;
import models.GatoModel;
import models.PerroModel;

import java.util.List;
import java.util.Scanner;

public class AnimalView {
    private final AnimalController animalController;
    private final Scanner scanner;

    public AnimalView(AnimalController animalController, Scanner scanner) {
        this.animalController = animalController;
        this.scanner = scanner;
    }

    public void iniciar() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- MASCOTAS ---");
            System.out.println("1. Registrar perro");
            System.out.println("2. Registrar gato");
            System.out.println("3. Listar mascotas");
            System.out.println("4. Buscar mascota");
            System.out.println("5. Eliminar mascota");
            System.out.println("0. Volver");
            System.out.print("Ingrese una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        registrarPerro();
                        break;
                    case 2:
                        registrarGato();
                        break;
                    case 3:
                        listar();
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

    private void registrarPerro() {
        System.out.print("Código (8 caracteres): ");
        String codigo = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Raza: ");
        String raza = scanner.nextLine();

        System.out.print("Sexo: ");
        String sexo = scanner.nextLine();

        int edad = leerEntero("Edad: ");
        double peso = leerDecimal("Peso: ");
        int idCliente = leerEntero("ID del dueño (cliente): ");

        System.out.print("Nivel de entrenamiento: ");
        String nivel = scanner.nextLine();

        boolean bozal = leerSiNo("¿Requiere bozal? (s/n): ");

        PerroModel perro = new PerroModel(
                codigo, nombre, raza, sexo, edad, peso,
                idCliente, nivel, bozal
        );

        if (animalController.registrar(perro)) {
            System.out.println("Perro registrado.");
        } else {
            System.out.println("No se pudo registrar el perro.");
        }
    }

    private void registrarGato() {
        System.out.print("Código (8 caracteres): ");
        String codigo = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Raza: ");
        String raza = scanner.nextLine();

        System.out.print("Sexo: ");
        String sexo = scanner.nextLine();

        int edad = leerEntero("Edad: ");
        double peso = leerDecimal("Peso: ");
        int idCliente = leerEntero("ID del dueño (cliente): ");

        System.out.print("Temperamento: ");
        String temperamento = scanner.nextLine();

        boolean transportadora =
                leerSiNo("¿Requiere transportadora? (s/n): ");

        GatoModel gato = new GatoModel(
                codigo, nombre, raza, sexo, edad, peso,
                idCliente, temperamento, transportadora
        );

        if (animalController.registrar(gato)) {
            System.out.println("Gato registrado.");
        } else {
            System.out.println("No se pudo registrar el gato.");
        }
    }

    private void listar() {
        List<AnimalModel> animales = animalController.listarTodos();

        if (animales.isEmpty()) {
            System.out.println("No hay mascotas registradas.");
            return;
        }

        for (AnimalModel animal : animales) {
            mostrar(animal);
        }
    }

    private void buscar() {
        System.out.print("Código de la mascota: ");
        String codigo = scanner.nextLine();

        AnimalModel animal = animalController.buscar(codigo);

        if (animal == null) {
            System.out.println("No se encontró la mascota.");
        } else {
            mostrar(animal);
        }
    }

    private void eliminar() {
        System.out.print("Código de la mascota que eliminará: ");
        String codigo = scanner.nextLine();

        if (animalController.eliminar(codigo)) {
            System.out.println("Mascota eliminada.");
        } else {
            System.out.println(
                    "No se pudo eliminar. Puede tener consultas registradas."
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

    private boolean leerSiNo(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().equalsIgnoreCase("s");
    }

    private void mostrar(AnimalModel animal) {
        System.out.println(
                animal.getCodigo() + " | " + animal.getNombre()
                        + " | " + animal.getRaza()
                        + " | " + animal.getSexo()
                        + " | " + animal.getEdad() + " años"
                        + " | " + animal.getPeso() + " kg"
                        + " | ID dueño: " + animal.getIdCliente()
        );

        if (animal instanceof PerroModel) {
            PerroModel perro = (PerroModel) animal;
            System.out.println(
                    "Perro | Entrenamiento: " + perro.getNivelEntenamiento()
                            + " | Requiere bozal: " + perro.isRequiereBozal()
            );
        } else if (animal instanceof GatoModel) {
            GatoModel gato = (GatoModel) animal;
            System.out.println(
                    "Gato | Temperamento: " + gato.getTemperamento()
                            + " | Requiere transportadora: "
                            + gato.isRequiereTransportadora()
            );
        }
    }
}
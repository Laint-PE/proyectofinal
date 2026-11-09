package views;

import controllers.ConsultaController;
import models.ConsultaModel;

import java.util.List;
import java.util.Scanner;

public class ConsultaView {
    private final ConsultaController consultaController;
    private final Scanner scanner;

    public ConsultaView(
            ConsultaController consultaController, Scanner scanner) {
        this.consultaController = consultaController;
        this.scanner = scanner;
    }

    public void iniciar() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- CONSULTAS VETERINARIAS ---");
            System.out.println("1. Nueva consulta");
            System.out.println("2. Consultas registradas");
            System.out.println("3. Buscar consulta");
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
        int cantidadHoy = consultaController.contarConsultasDeHoy();

        if (cantidadHoy < 0) {
            System.out.println("No se pudo consultar el límite diario.");
            return;
        }

        if (cantidadHoy >= 15) {
            System.out.println("Ya se alcanzó el límite de 15 consultas de hoy.");
            return;
        }

        System.out.println("Consultas registradas hoy: " + cantidadHoy + " de 15");

        System.out.print("Código de la mascota: ");
        String codigoAnimal = scanner.nextLine();

        int idVeterinario = leerEntero("ID del veterinario: ");

        System.out.print("Motivo: ");
        String motivo = scanner.nextLine();

        System.out.print("Síntomas: ");
        String sintomas = scanner.nextLine();

        System.out.print("Diagnóstico: ");
        String diagnostico = scanner.nextLine();

        System.out.print("Tratamiento: ");
        String tratamiento = scanner.nextLine();

        double costo = leerDecimal("Costo: ");

        if (consultaController.registrar(
                codigoAnimal, idVeterinario, motivo,
                sintomas, diagnostico, tratamiento, costo)) {
            System.out.println("Consulta registrada con la fecha de hoy.");
        } else {
            System.out.println(
                    "No se pudo registrar. Revise los datos y el límite diario."
            );
        }
    }

    private void listar() {
        List<ConsultaModel> consultas = consultaController.listarTodas();

        if (consultas.isEmpty()) {
            System.out.println("No hay consultas registradas.");
            return;
        }

        for (ConsultaModel consulta : consultas) {
            mostrar(consulta);
        }
    }

    private void buscar() {
        int idConsulta = leerEntero("ID de la consulta: ");
        ConsultaModel consulta = consultaController.buscar(idConsulta);

        if (consulta == null) {
            System.out.println("No se encontró la consulta.");
        } else {
            mostrar(consulta);
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

    private void mostrar(ConsultaModel consulta) {
        System.out.println(
                "ID: " + consulta.getIdConsulta()
                        + " | Mascota: " + consulta.getCodigoAnimal()
                        + " | Veterinario: " + consulta.getIdVeterinario()
                        + " | Fecha: " + consulta.getFecha()
                        + " | Motivo: " + consulta.getMotivo()
                        + " | Estado: " + consulta.getEstado()
                        + " | Costo: S/ " + consulta.getCosto()
        );
    }
}
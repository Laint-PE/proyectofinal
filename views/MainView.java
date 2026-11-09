package views;

import controllers.AnimalController;
import controllers.CategoriaController;
import controllers.ClienteController;
import controllers.ConsultaController;
import controllers.LoginController;
import controllers.PersonalController;
import controllers.PedidoController;
import controllers.ProductoController;
import controllers.PromocionController;
import controllers.ReportController;
import controllers.SucursalController;

import dao.AnimalDAO;
import dao.CategoriaDAO;
import dao.ClienteDAO;
import dao.ConsultaDAO;
import dao.EmpleadoDAO;
import dao.PedidoDAO;
import dao.ProductoDAO;
import dao.PromocionDAO;
import dao.ReportDAO;
import dao.SucursalDAO;
import dao.UsuarioDAO;

import dao.dao.impl.AnimalDAOImpl;
import dao.impl.CategoriaDAOImpl;
import dao.impl.ClienteDAOImpl;
import dao.impl.ConsultaDAOImpl;
import dao.impl.EmpleadoDAOImpl;
import dao.impl.PedidoDAOImpl;
import dao.impl.ProductoDAOImpl;
import dao.impl.PromocionDAOImpl;
import dao.impl.ReportDAOImpl;
import dao.impl.SucursalDAOImpl;
import dao.impl.UsuarioDAOImpl;

import models.UsuarioModel;

import java.util.Scanner;

public class MainView {
    private final Scanner scanner = new Scanner(System.in);

    private final LoginView loginView;
    private final ProductoView productoView;
    private final CategoriaView categoriaView;
    private final PromocionView promocionView;
    private final ClienteView clienteView;
    private final AnimalView animalView;
    private final ConsultaView consultaView;
    private final PedidoView pedidoView;
    private final PersonalView personalView;
    private final SucursalView sucursalView;
    private final ReportView reportView;

    public MainView() {
        ProductoDAO productoDAO = new ProductoDAOImpl();
        CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
        PromocionDAO promocionDAO = new PromocionDAOImpl();
        ClienteDAO clienteDAO = new ClienteDAOImpl();
        AnimalDAO animalDAO = new AnimalDAOImpl();
        ConsultaDAO consultaDAO = new ConsultaDAOImpl();
        PedidoDAO pedidoDAO = new PedidoDAOImpl();
        EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl();
        SucursalDAO sucursalDAO = new SucursalDAOImpl();
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        ReportDAO reportDAO = new ReportDAOImpl();

        ProductoController productoController =
                new ProductoController(productoDAO);
        productoView = new ProductoView(productoController, scanner);

        categoriaView = new CategoriaView(
                new CategoriaController(categoriaDAO), scanner
        );

        promocionView = new PromocionView(
                new PromocionController(promocionDAO), scanner
        );

        clienteView = new ClienteView(
                new ClienteController(clienteDAO), scanner
        );

        animalView = new AnimalView(
                new AnimalController(animalDAO), scanner
        );

        consultaView = new ConsultaView(
                new ConsultaController(consultaDAO), scanner
        );

        PedidoController pedidoController =
                new PedidoController(pedidoDAO, productoDAO, promocionDAO);
        pedidoView = new PedidoView(pedidoController, scanner);

        personalView = new PersonalView(
                new PersonalController(empleadoDAO), scanner
        );

        sucursalView = new SucursalView(
                new SucursalController(sucursalDAO), scanner
        );

        loginView = new LoginView(
                new LoginController(usuarioDAO), scanner
        );

        reportView = new ReportView(
                new ReportController(reportDAO)
        );
    }

    public void iniciar() {
        UsuarioModel usuario = loginView.iniciarSesion();

        if (usuario == null) {
            System.out.println("No se inició sesión. Fin del programa.");
            scanner.close();
            return;
        }

        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- VETERINARIA HUELLITAS ---");
            System.out.println("1. Ventas");
            System.out.println("2. Clientes y mascotas");
            System.out.println("3. Catálogo");
            System.out.println("4. Consultas veterinarias");
            System.out.println("5. Personal y sucursales");
            System.out.println("6. Reportes del día");
            System.out.println("0. Salir");
            System.out.print("Ingrese una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        pedidoView.iniciar();
                        break;
                    case 2:
                        menuClientesYMascotas();
                        break;
                    case 3:
                        menuCatalogo();
                        break;
                    case 4:
                        consultaView.iniciar();
                        break;
                    case 5:
                        menuPersonal();
                        break;
                    case 6:
                        reportView.mostrarReportesDelDia();
                        break;
                    case 0:
                        System.out.println("Fin del programa.");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException error) {
                System.out.println("Ingrese un número válido.");
            }
        }

        scanner.close();
    }

    private void menuClientesYMascotas() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- CLIENTES Y MASCOTAS ---");
            System.out.println("1. Clientes");
            System.out.println("2. Mascotas");
            System.out.println("0. Volver");
            System.out.print("Ingrese una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        clienteView.iniciar();
                        break;
                    case 2:
                        animalView.iniciar();
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

    private void menuCatalogo() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- CATÁLOGO ---");
            System.out.println("1. Productos");
            System.out.println("2. Categorías");
            System.out.println("3. Promociones");
            System.out.println("0. Volver");
            System.out.print("Ingrese una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        productoView.iniciar();
                        break;
                    case 2:
                        categoriaView.iniciar();
                        break;
                    case 3:
                        promocionView.iniciar();
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

    private void menuPersonal() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- PERSONAL ---");
            System.out.println("1. Empleados, veterinarios y repartidores");
            System.out.println("2. Sucursales");
            System.out.println("0. Volver");
            System.out.print("Ingrese una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        personalView.iniciar();
                        break;
                    case 2:
                        sucursalView.iniciar();
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
}
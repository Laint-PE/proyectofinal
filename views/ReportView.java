package views;

import controllers.ReportController;
import models.ProductoStockModel;

import java.util.List;

public class ReportView {
    private final ReportController reportController;

    public ReportView(ReportController reportController) {
        this.reportController = reportController;
    }

    public void mostrarReportesDelDia() {
        int consultas = reportController.consultasDeHoy();
        int pedidos = reportController.pedidosDeHoy();
        double ventas = reportController.ventasDeHoy();

        System.out.println("\n--- REPORTES DE HOY ---");
        System.out.println("Consultas veterinarias: " + consultas);
        System.out.println("Pedidos registrados: " + pedidos);
        System.out.println("Ventas pagadas: S/ " + ventas);

        System.out.println("\n--- PRODUCTOS CON STOCK BAJO (5 O MENOS) ---");
        List<ProductoStockModel> productos =
                reportController.productosConStockBajo();

        if (productos.isEmpty()) {
            System.out.println("No hay productos con stock bajo.");
            return;
        }

        for (ProductoStockModel producto : productos) {
            System.out.println(
                    producto.getCodigo() + " | "
                            + producto.getNombre() + " | Stock: "
                            + producto.getStock()
            );
        }
    }
}
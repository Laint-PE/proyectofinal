package controllers;

import dao.ReportDAO;
import models.ProductoStockModel;

import java.util.List;

public class ReportController {
    private final ReportDAO reportDAO;

    public ReportController(ReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    public int consultasDeHoy() {
        return reportDAO.contarConsultasDeHoy();
    }

    public int pedidosDeHoy() {
        return reportDAO.contarPedidosDeHoy();
    }

    public double ventasDeHoy() {
        return reportDAO.calcularVentasDeHoy();
    }

    public List<ProductoStockModel> productosConStockBajo() {
        return reportDAO.listarProductosConStockBajo();
    }
}
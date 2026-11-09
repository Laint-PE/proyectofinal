package dao;

import models.ProductoStockModel;
import java.util.List;

public interface ReportDAO {
    int contarConsultasDeHoy();
    int contarPedidosDeHoy();
    double calcularVentasDeHoy();
    List<ProductoStockModel> listarProductosConStockBajo();
}
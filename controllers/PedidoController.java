package controllers;

import dao.PedidoDAO;
import dao.ProductoDAO;
import dao.PromocionDAO;
import models.DetallePedidoModel;
import models.PagoModel;
import models.PedidoModel;
import models.ProductoModel;
import models.PromocionModel;

import java.util.ArrayList;
import java.util.List;

public class PedidoController {
    private final PedidoDAO pedidoDAO;
    private final ProductoDAO productoDAO;
    private final PromocionDAO promocionDAO;

    public PedidoController(
            PedidoDAO pedidoDAO,
            ProductoDAO productoDAO,
            PromocionDAO promocionDAO) {
        this.pedidoDAO = pedidoDAO;
        this.productoDAO = productoDAO;
        this.promocionDAO = promocionDAO;
    }

    public boolean registrarVenta(
            PedidoModel pedido,
            List<String> codigos,
            List<Integer> cantidades,
            String metodoPago,
            double montoRecibido) {

        if (pedido == null || codigos == null || cantidades == null
                || codigos.isEmpty() || codigos.size() != cantidades.size()
                || pedido.getIdCliente() <= 0
                || pedido.getIdEmpleado() <= 0
                || pedido.getTipoEntrega() == null
                || pedido.getTipoEntrega().isBlank()
                || metodoPago == null || metodoPago.isBlank()) {
            return false;
        }

        List<ProductoModel> productos = new ArrayList<>();
        int cantidadTotal = 0;

        for (int i = 0; i < codigos.size(); i++) {
            String codigo = codigos.get(i);
            int cantidad = cantidades.get(i);

            if (codigo == null || codigo.isBlank() || cantidad <= 0) {
                return false;
            }

            ProductoModel producto = productoDAO.buscarPorCodigo(codigo);

            if (producto == null || producto.getStock() < cantidad) {
                return false;
            }

            productos.add(producto);
            cantidadTotal += cantidad;
        }

        PromocionModel promocion =
                promocionDAO.buscarVigentePorCantidad(cantidadTotal);

        List<DetallePedidoModel> detalles = new ArrayList<>();
        double total = 0;

        for (int i = 0; i < productos.size(); i++) {
            ProductoModel producto = productos.get(i);
            int cantidad = cantidades.get(i);

            double subtotalBruto = producto.getPrecio() * cantidad;
            double montoDescuento = 0;
            Integer idPromocion = null;

            if (promocion != null) {
                montoDescuento =
                        subtotalBruto * promocion.getDescuento() / 100.0;
                idPromocion = promocion.getIdPromocion();
            }

            double subtotalFinal = subtotalBruto - montoDescuento;
            total += subtotalFinal;

            detalles.add(new DetallePedidoModel(
                    0,
                    0,
                    producto.getCodigo(),
                    cantidad,
                    producto.getPrecio(),
                    montoDescuento,
                    subtotalFinal,
                    idPromocion
            ));
        }

        if (montoRecibido < total) {
            return false;
        }

        PagoModel pago = new PagoModel(
                0,
                0,
                metodoPago,
                total,
                null,
                "PAGADO",
                montoRecibido,
                montoRecibido - total
        );

        return pedidoDAO.registrarVenta(pedido, detalles, pago);
    }

    public List<PedidoModel> listarTodos() {
        return pedidoDAO.listarTodos();
    }

    public PedidoModel buscar(int idPedido) {
        if (idPedido <= 0) {
            return null;
        }

        return pedidoDAO.buscarPorId(idPedido);
    }

    public boolean actualizarEstado(int idPedido, String estado) {
        if (idPedido <= 0 || estado == null || estado.isBlank()) {
            return false;
        }

        return pedidoDAO.actualizarEstado(idPedido, estado);
    }
}
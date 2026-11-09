package models;

public class DetallePedidoModel {
    private int idDetalle;
    private int idPedido;
    private String codigoProducto;
    private int cantidad;
    private double precioUnitario;
    private double descuento;
    private double subtotal;
    private Integer idPromocion;

    public DetallePedidoModel(int idDetalle, int idPedido, String codigoProducto, int cantidad, double precioUnitario, double descuento, double subtotal, Integer idPromocion) {
        this.idDetalle = idDetalle;
        this.idPedido = idPedido;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = descuento;
        this.subtotal = subtotal;
        this.idPromocion = idPromocion;
    }

    public int getIdDetalle() {
        return idDetalle;
    }
    public int getIdPedido() {
        return idPedido;
    }
    public String getCodigoProducto() {
        return codigoProducto;
    }
    public int getCantidad() {
        return cantidad;
    }
    public double getPrecioUnitario() {
        return precioUnitario;
    }
    public double getDescuento() {
        return descuento;
    }
    public double getSubtotal() {
        return subtotal;
    }
    public Integer getIdPromocion() {
        return idPromocion;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void mostrarInformacion(){
        System.out.println("ID DETALLE: " + idDetalle);
        System.out.println("ID PEDIDO: " + idPedido);
        System.out.println("CODIGO PRODUCTO: " + codigoProducto);
        System.out.println("CANTIDAD: " + cantidad);
        System.out.println("PRECIO UNITARIO: " + precioUnitario);
        System.out.println("DESCUENTO: " + descuento);
        System.out.println("SUBTOTAL: " + subtotal);
        System.out.println("ID PROMOCION: " + idPromocion);
    }
}

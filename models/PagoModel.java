package models;

public class PagoModel {
    private int idPago;
    private int idPedido;
    private String metodoPago;
    private double monto;
    private String fechaPago;
    private String estadoPago;
    private double montoRecibido;
    private double vuelto;

    public PagoModel(int idPago, int idPedido, String metodoPago, double monto, String fechaPago, String estadoPago, double montoRecibido, double vuelto) {
        this.idPago = idPago;
        this.idPedido = idPedido;
        this.metodoPago = metodoPago;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.estadoPago = estadoPago;
        this.montoRecibido = montoRecibido;
        this.vuelto = vuelto;
    }

    public int getIdPago() {
        return idPago;
    }
    public int getIdPedido() {
        return idPedido;
    }
    public String getMetodoPago() {
        return metodoPago;
    }
    public double getMonto() {
        return monto;
    }
    public String getFechaPago() {
        return fechaPago;
    }
    public String getEstadoPago() {
        return estadoPago;
    }
    public double getMontoRecibido() {
        return montoRecibido;
    }
    public double getVuelto() {
        return vuelto;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }
    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }
    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }
    public void setMontoRecibido(double montoRecibido) {
        this.montoRecibido = montoRecibido;
    }
    public void setVuelto(double vuelto) {
        this.vuelto = vuelto;
    }

    public void mostrarInformacion(){
        System.out.println("ID PAGO: " + idPago);
        System.out.println("ID PEDIDO:" + idPedido);
        System.out.println("METODO DE PAGO: " + metodoPago);
        System.out.println("MONTO: " + monto);
        System.out.println("FECHA DE PAGO: " + fechaPago);
        System.out.println("ESTADO DE PAGO:" + estadoPago);
        System.out.println("MONTO RECIBIDO: " +montoRecibido );
        System.out.println("VUELTO: " + vuelto);

    }
}

package models;

public class PedidoModel {
    private int idPedido;
    private int idCliente;
    private int idEmpleado;
    private String fecha;
    private String estado;
    private String tipoEntrega;
    private String direccionEntrega;
    private Integer idRepartidor;

    public PedidoModel(int idPedido, int idCliente, int idEmpleado, String fecha, String estado, String tipoEntrega, String direccionEntrega, Integer idRepartidor) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.fecha = fecha;
        this.estado = estado;
        this.tipoEntrega = tipoEntrega;
        this.direccionEntrega = direccionEntrega;
        this.idRepartidor = idRepartidor;
    }

    public int getIdPedido() {
        return idPedido;
    }
    public int getIdCliente() {
        return idCliente;
    }
    public int getIdEmpleado() {
        return idEmpleado;
    }
    public String getFecha() {
        return fecha;
    }
    public String getEstado() {
        return estado;
    }
    public String getTipoEntrega() {
        return tipoEntrega;
    }
    public String getDireccionEntrega() {
        return direccionEntrega;
    }
    public Integer getIdRepartidor() {
        return idRepartidor;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public void setTipoEntrega(String tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }
    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public void mostrarInformacion(){
        System.out.println("ID PEDIDO: " + idPedido);
        System.out.println("CODIGO CLIENTE: " + idCliente);
        System.out.println("ID EMPLEADO: " + idEmpleado);
        System.out.println("FECHA: " + fecha);
        System.out.println("ESTADO: " + estado);
        System.out.println("TIPO DE ENTREGA:" + tipoEntrega);
        System.out.println("DIRECCION: " + direccionEntrega);
        System.out.println("ID REPARTIDOR: " + idRepartidor);
    }
}

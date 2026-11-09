package models;

public class EmpleadoModel extends PersonaModel {
    private String cargo;
    private int idSucursal;
    private String estado;
    private int idEmpleado;

    public EmpleadoModel(String dni, String nombre, String apellido, int edad, String telefono, String cargo, int idSucursal, String estado, int idEmpleado) {
        super(dni, nombre, apellido, edad, telefono);
        this.cargo = cargo;
        this.idSucursal = idSucursal;
        this.estado = estado;
        this.idEmpleado = idEmpleado;
    }

    public String getCargo() {
        return cargo;
    }
    public int getIdSucursal() {
        return idSucursal;
    }
    public String getEstado() {
        return estado;
    }
    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("CARGO: " + cargo);
        System.out.println("SUCURSAL: " + idSucursal);
        System.out.println("ESTADO: " + estado);
        System.out.println("ID EMPLEADO: " + idEmpleado);
    }
}

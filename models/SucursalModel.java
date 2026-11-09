package models;

public class SucursalModel {
    private int idSucursal;
    private String nombre;
    private String direccion;
    private String telefono;
    private String horarioAtencion;
    private boolean activo;

    public SucursalModel(int idSucursal, String nombre, String direccion, String telefono, String horarioAtencion, boolean activo) {
        this.idSucursal = idSucursal;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.horarioAtencion = horarioAtencion;
        this.activo = activo;
    }

    public int getIdSucursal() {
        return idSucursal;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDireccion() {
        return direccion;
    }
    public String getTelefono() {
        return telefono;
    }
    public String getHorarioAtencion() {
        return horarioAtencion;
    }
    public boolean isActivo() {
        return activo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public void setHorarioAtencion(String horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void mostrarInformacion(){
        System.out.println("ID SUCURSAL: " + idSucursal);
        System.out.println("NOMBRE: " + nombre);
        System.out.println("DIRECCION: " + direccion);
        System.out.println("TELEFONO: " + telefono);
        System.out.println("HORARIO DE ATENCION: " + horarioAtencion);
        System.out.println("ACTIVO: " + activo);
    }
}

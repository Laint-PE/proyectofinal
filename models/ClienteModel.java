package models;

public class ClienteModel extends PersonaModel {
    private String correo;
    private String direccion;
    private int idCliente;

    public ClienteModel(String dni, String nombre, String apellido, int edad, String telefono, String correo, String direccion, int idCliente) {
        super(dni, nombre, apellido, edad, telefono);
        this.correo = correo;
        this.direccion = direccion;
        this.idCliente = idCliente;
    }

    public String getCorreo() {
        return correo;
    }
    public String getDireccion() {
        return direccion;
    }
    public int getIdCliente() {
        return idCliente;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("CORREO: " + correo);
        System.out.println("DIRECCION: " + direccion);
        System.out.println("ID CLIENTE: " + idCliente);
    }
}
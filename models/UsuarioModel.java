package models;

public class UsuarioModel {
    private int idUsuario;
    private String usuario;
    private String password;
    private String rol;
    private boolean activo;
    private int idEmpleado;


    public UsuarioModel(int idUsuario, String usuario, String password, String rol, boolean activo, int idEmpleado) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
        this.activo = activo;
        this.idEmpleado = idEmpleado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
    public String getUsuario() {
        return usuario;
    }
    public String getPassword() {
        return password;
    }
    public String getRol() {
        return rol;
    }
    public boolean isActivo() {
        return activo;
    }
    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void mostrarInformacion(){
        System.out.println("ID USUARIO: " + idUsuario);
        System.out.println("USUARIO: " + usuario);
        System.out.println("CONTRASEÑA: " + password);
        System.out.println("ROL: " + rol);
        System.out.println("ESTA ACTIVO: " + activo);
        System.out.println("ID EMPLEADO: " + idEmpleado);
    }
}

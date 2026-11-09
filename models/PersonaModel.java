package models;

public abstract class PersonaModel {
    protected String dni;
    protected String nombre;
    protected String apellido;
    protected int edad;
    protected String telefono;

    public PersonaModel(String dni, String nombre, String apellido, int edad, String telefono) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.telefono = telefono;

    }

    public String getDni() {
        return dni;
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public String getTelefono() {
        return telefono;
    }
    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public void mostrarInformacion(){
        System.out.print("DNI: " + dni);
        System.out.print("NOMBRE: " + nombre);
        System.out.print("APELLIDO: " + apellido);
        System.out.print("TELEFONO: " + telefono);
        System.out.print("EDAD: " + edad);
    }
}

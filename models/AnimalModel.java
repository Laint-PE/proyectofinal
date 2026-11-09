package models;

public abstract class AnimalModel {
    protected String codigo;
    protected String nombre;
    protected String raza;
    protected String sexo;
    protected int edad;
    protected double peso;
    protected int idCliente;

    public AnimalModel(String codigo, String nombre, String raza, String sexo, int edad, double peso, int idCliente) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.raza = raza;
        this.sexo = sexo;
        this.edad = edad;
        this.peso = peso;
        this.idCliente = idCliente;
    }

    public String getCodigo() {
        return codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public String getRaza() {
        return raza;
    }
    public String getSexo() {
        return sexo;
    }
    public int getEdad() {
        return edad;
    }
    public double getPeso() {
        return peso;
    }
    public int getIdCliente() {
        return idCliente;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }


    public void mostrarInformacion() {
        System.out.println("CODIGO: " + codigo);
        System.out.println("NOMBRE: " + nombre);
        System.out.println("EDAD: " + edad + " años");
        System.out.println("PESO: " + peso + " kg");
        System.out.println("SEXO: " + sexo);
        System.out.println("ID CLIENTE: " + idCliente);
    }
}

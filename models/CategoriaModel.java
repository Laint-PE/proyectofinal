package models;

public class CategoriaModel {
    private int idCategoria;
    private String nombre;
    private String descripcion;

    public CategoriaModel(int idCategoria, String nombre, String descripcion) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIdCategoria() {
        return idCategoria;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void mostrarInformacion(){
        System.out.println("ID CATEGORIA: " + idCategoria);
        System.out.println("NOMBRE: " + nombre);
        System.out.println("DESCRIPCION: " + descripcion);
    }
}

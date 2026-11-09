package models;

public class ProductoStockModel {
    private String codigo;
    private String nombre;
    private int stock;

    public ProductoStockModel(String codigo, String nombre, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.stock = stock;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getStock() {
        return stock;
    }
}
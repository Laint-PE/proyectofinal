package models;

public class AccesorioModel extends ProductoModel {
    private String tipo;
    private String material;
    private String ropa;

    public AccesorioModel(
            String codigo, String nombre, double precio, int stock,
            int idCategoria, String tipo, String material, String ropa) {
        super(codigo, nombre, precio, stock, idCategoria);
        this.tipo = tipo;
        this.material = material;
        this.ropa = ropa;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMaterial() {
        return material;
    }

    public String getRopa() {
        return ropa;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setRopa(String ropa) {
        this.ropa = ropa;
    }
}
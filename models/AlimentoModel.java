package models;

public class AlimentoModel extends ProductoModel {
    private String tipoAnimal;
    private String fechaVencimiento;
    private String sabor;

    public AlimentoModel(
            String codigo, String nombre, double precio, int stock,
            int idCategoria, String tipoAnimal, String fechaVencimiento,
            String sabor) {
        super(codigo, nombre, precio, stock, idCategoria);
        this.tipoAnimal = tipoAnimal;
        this.fechaVencimiento = fechaVencimiento;
        this.sabor = sabor;
    }

    public String getTipoAnimal() {
        return tipoAnimal;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public String getSabor() {
        return sabor;
    }

    public void setTipoAnimal(String tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }
}
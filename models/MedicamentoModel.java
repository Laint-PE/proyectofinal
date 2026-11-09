package models;

public class MedicamentoModel extends ProductoModel {
    private String fechaVencimiento;
    private String dosis;

    public MedicamentoModel(
            String codigo, String nombre, double precio, int stock,
            int idCategoria, String fechaVencimiento, String dosis) {
        super(codigo, nombre, precio, stock, idCategoria);
        this.fechaVencimiento = fechaVencimiento;
        this.dosis = dosis;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public String getDosis() {
        return dosis;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }
}
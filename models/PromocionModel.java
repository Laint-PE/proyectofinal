package models;

public class PromocionModel {
    private int idPromocion;
    private String nombre;
    private double descuento;
    private String inicioDescuento;
    private String finDescuento;
    private int cantidadMinima;


    public PromocionModel(int idPromocion, String nombre, double descuento, String inicioDescuento, String finDescuento, int cantidadMinima) {
        this.idPromocion = idPromocion;
        this.nombre = nombre;
        this.descuento = descuento;
        this.inicioDescuento = inicioDescuento;
        this.finDescuento = finDescuento;
        this.cantidadMinima = cantidadMinima;
    }

    public int getIdPromocion() {
        return idPromocion;
    }

    public String getNombre() {
        return nombre;
    }
    public double getDescuento() {
        return descuento;
    }
    public String getInicioDescuento() {
        return inicioDescuento;
    }
    public String getFinDescuento() {
        return finDescuento;
    }
    public int getCantidadMinima() {
        return cantidadMinima;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
    public void setInicioDescuento(String inicioDescuento) {
        this.inicioDescuento = inicioDescuento;
    }
    public void setFinDescuento(String finDescuento) {
        this.finDescuento = finDescuento;
    }
    public void setCantidadMinima(int cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public void mostrarInformacion(){
        System.out.println("ID PROMOCION: " + idPromocion);
        System.out.println("NOMBRE: " + nombre);
        System.out.println("DESCUENTO: " + descuento);
        System.out.println("INICIO: " + inicioDescuento);
        System.out.println("FIN: " + finDescuento);
        System.out.println("CANTIDAD MINIMA: " + cantidadMinima);
    }
}

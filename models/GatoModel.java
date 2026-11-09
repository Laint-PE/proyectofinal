package models;

public class GatoModel extends AnimalModel {
    private String temperamento;
    private boolean requiereTransportadora;

    public GatoModel(String codigo, String nombre, String raza, String sexo, int edad, double peso, int idCliente, String temperamento, boolean requiereTransportadora) {
        super(codigo, nombre, raza, sexo, edad, peso, idCliente);
        this.temperamento = temperamento;
        this.requiereTransportadora = requiereTransportadora;
    }

    public String getTemperamento() {
        return temperamento;
    }
    public boolean isRequiereTransportadora() {
        return requiereTransportadora;
    }

    public void setTemperamento(String temperamento) {
        this.temperamento = temperamento;
    }
    public void setRequiereTransportadora(boolean requiereTransportadora) {
        this.requiereTransportadora = requiereTransportadora;
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Temperamento: " + temperamento);
        System.out.println("Requiere Transportadora: " + requiereTransportadora);
    }
}

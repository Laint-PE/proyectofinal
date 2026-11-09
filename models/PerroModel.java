package models;

public class PerroModel extends AnimalModel {
    private String nivelEntenamiento;
    private boolean requiereBozal;

    public PerroModel(String codigo, String nombre, String raza, String sexo, int edad, double peso, int idCliente, String nivelEntenamiento, boolean requiereBozal) {
        super(codigo, nombre, raza, sexo, edad, peso, idCliente);
        this.nivelEntenamiento = nivelEntenamiento;
        this.requiereBozal = requiereBozal;
    }

    public String getNivelEntenamiento() {
        return nivelEntenamiento;
    }
    public boolean isRequiereBozal() {
        return requiereBozal;
    }

    public void setNivelEntenamiento(String nivelEntenamiento) {
        this.nivelEntenamiento = nivelEntenamiento;
    }
    public void setRequiereBozal(boolean requiereBozal) {
        this.requiereBozal = requiereBozal;
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Nivel de Entrenamiento: " + nivelEntenamiento);
        System.out.println("Requiere bozal: " + requiereBozal);
    }
}

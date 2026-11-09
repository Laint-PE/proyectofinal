package models;

public class ConsultaModel {
    private int idConsulta;
    private String codigoAnimal;
    private int idVeterinario;
    private String fecha;
    private String motivo;
    private String sintomas;
    private String diagnostico;
    private String tratamiento;
    private double costo;
    private String estado;

    public ConsultaModel(int idConsulta, String codigoAnimal, int idVeterinario, String fecha, String motivo, String sintomas, String diagnostico, String tratamiento, double costo, String estado) {
        this.idConsulta = idConsulta;
        this.codigoAnimal = codigoAnimal;
        this.idVeterinario = idVeterinario;
        this.fecha = fecha;
        this.motivo = motivo;
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.costo = costo;
        this.estado = estado;
    }

    public int getIdConsulta() {
        return idConsulta;
    }
    public String getCodigoAnimal() {
        return codigoAnimal;
    }
    public int getIdVeterinario() {
        return idVeterinario;
    }
    public String getFecha() {
        return fecha;
    }
    public String getMotivo() {
        return motivo;
    }
    public String getSintomas() {
        return sintomas;
    }
    public String getDiagnostico() {
        return diagnostico;
    }
    public String getTratamiento() {
        return tratamiento;
    }
    public double getCosto() {
        return costo;
    }
    public String getEstado() {
        return estado;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void  mostrarInformacion(){
        System.out.println("ID CONSULTA: " + idConsulta );
        System.out.println("CODIGO ANIMAL: " + codigoAnimal );
        System.out.println("ID VETERINARIO: " + idVeterinario);
        System.out.println("FECHA: " + fecha);
        System.out.println("MOTIVO: " + motivo);
        System.out.println("SINTOMAS: " + sintomas);
        System.out.println("DIAGNOSTICO" + diagnostico);
        System.out.println("TRATAMIENTO: " + tratamiento);
        System.out.println("COSTO: " + costo);
        System.out.println("ESTADO: " + estado);
    }
}

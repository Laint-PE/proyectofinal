package controllers;

import dao.ConsultaDAO;
import models.ConsultaModel;

import java.util.List;

public class ConsultaController {
    private static final int LIMITE_DIARIO = 15;

    private final ConsultaDAO consultaDAO;

    public ConsultaController(ConsultaDAO consultaDAO) {
        this.consultaDAO = consultaDAO;
    }

    public int contarConsultasDeHoy() {
        return consultaDAO.contarConsultasDeHoy();
    }

    public boolean registrar(
            String codigoAnimal, int idVeterinario, String motivo,
            String sintomas, String diagnostico, String tratamiento,
            double costo) {

        if (codigoAnimal == null || codigoAnimal.isBlank()
                || idVeterinario <= 0
                || motivo == null || motivo.isBlank()
                || costo < 0) {
            return false;
        }

        int cantidadHoy = consultaDAO.contarConsultasDeHoy();

        if (cantidadHoy < 0 || cantidadHoy >= LIMITE_DIARIO) {
            return false;
        }

        ConsultaModel consulta = new ConsultaModel(
                0, codigoAnimal, idVeterinario, null,
                motivo, sintomas, diagnostico, tratamiento,
                costo, "PENDIENTE"
        );

        return consultaDAO.insertar(consulta);
    }

    public List<ConsultaModel> listarTodas() {
        return consultaDAO.listarTodos();
    }

    public ConsultaModel buscar(int idConsulta) {
        if (idConsulta <= 0) {
            return null;
        }

        return consultaDAO.buscarPorId(idConsulta);
    }

    public boolean actualizar(ConsultaModel consulta) {
        if (consulta == null || consulta.getIdConsulta() <= 0
                || consulta.getMotivo() == null
                || consulta.getMotivo().isBlank()
                || consulta.getCosto() < 0) {
            return false;
        }

        return consultaDAO.actualizar(consulta);
    }

    public boolean eliminar(int idConsulta) {
        if (idConsulta <= 0) {
            return false;
        }

        return consultaDAO.eliminar(idConsulta);
    }
}
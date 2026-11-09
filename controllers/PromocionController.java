package controllers;

import dao.PromocionDAO;
import models.PromocionModel;

import java.util.List;

public class PromocionController {
    private final PromocionDAO promocionDAO;

    public PromocionController(PromocionDAO promocionDAO) {
        this.promocionDAO = promocionDAO;
    }

    public boolean registrar(
            String nombre, double descuento, String inicio,
            String fin, int cantidadMinima) {

        if (nombre == null || nombre.isBlank()
                || descuento <= 0 || descuento > 100
                || inicio == null || inicio.isBlank()
                || fin == null || fin.isBlank()
                || cantidadMinima < 1) {
            return false;
        }

        PromocionModel promocion =
                new PromocionModel(0, nombre.trim(), descuento,
                        inicio, fin, cantidadMinima);

        return promocionDAO.insertar(promocion);
    }

    public List<PromocionModel> listarTodas() {
        return promocionDAO.listarTodos();
    }

    public PromocionModel buscar(int idPromocion) {
        if (idPromocion <= 0) {
            return null;
        }

        return promocionDAO.buscarPorId(idPromocion);
    }

    public PromocionModel buscarVigente(int cantidad) {
        if (cantidad < 1) {
            return null;
        }

        return promocionDAO.buscarVigentePorCantidad(cantidad);
    }

    public boolean actualizar(
            int idPromocion, String nombre, double descuento,
            String inicio, String fin, int cantidadMinima) {

        if (idPromocion <= 0 || nombre == null || nombre.isBlank()
                || descuento <= 0 || descuento > 100
                || inicio == null || inicio.isBlank()
                || fin == null || fin.isBlank()
                || cantidadMinima < 1) {
            return false;
        }

        PromocionModel promocion =
                new PromocionModel(idPromocion, nombre.trim(), descuento,
                        inicio, fin, cantidadMinima);

        return promocionDAO.actualizar(promocion);
    }

    public boolean eliminar(int idPromocion) {
        if (idPromocion <= 0) {
            return false;
        }

        return promocionDAO.eliminar(idPromocion);
    }
}
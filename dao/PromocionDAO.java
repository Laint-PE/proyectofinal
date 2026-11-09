package dao;

import models.PromocionModel;
import java.util.List;

public interface PromocionDAO {
    boolean insertar(PromocionModel promocion);
    List<PromocionModel> listarTodos();
    PromocionModel buscarPorId(int idPromocion);
    PromocionModel buscarVigentePorCantidad(int cantidad);
    boolean actualizar(PromocionModel promocion);
    boolean eliminar(int idPromocion);
}
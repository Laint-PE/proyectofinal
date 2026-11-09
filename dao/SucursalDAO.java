package dao;

import models.SucursalModel;
import java.util.List;

public interface SucursalDAO {
    boolean insertar(SucursalModel sucursal);
    List<SucursalModel> listarTodas();
    SucursalModel buscarPorId(int idSucursal);
    boolean actualizar(SucursalModel sucursal);
    boolean eliminar(int idSucursal);
}
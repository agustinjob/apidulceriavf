package com.punto.venta.apidulceria.repository;
import java.util.Date;
import java.util.List;

import com.punto.venta.apidulceria.model.MovimientosExtras;
import com.punto.venta.apidulceria.model.corte.IDatosCorte;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovimientosExtras extends CrudRepository<MovimientosExtras,Integer>{
    
    @Query(value="SELECT * FROM `movimientos_extras` where tipo= ?1 and date(fecha)= ?2 and id_usuario = ?3 and id_sucursal= ?4",nativeQuery = true)
    List<MovimientosExtras> findByTipoAndFechaAndIdSucursalAndIdUsuario(String tipo, String fecha, Integer idUsuario,Integer idSucursal);
// ocupar para dinero caja, entradas,salidas,devolucion
    @Query(value="SELECT sum(monto) as dato FROM movimientos_extras WHERE tipo = ?1 and id_usuario= ?2 and date(fecha) = ?3 and id_sucursal= ?4",nativeQuery = true)
    IDatosCorte encontrarDatos(String tipoMovimiento,String idUsuario,String fecha,String idSucursal);

    @Query(value="SELECT * FROM movimientos_extras where tipo= 'efectivo_inicial' and date(fecha)= ?1 and id_usuario = ?2 and id_sucursal= ?3",nativeQuery = true)
    MovimientosExtras encontrarEfectivoInicias(String fecha, Integer idUsuario, Integer idSucursal);

    
}

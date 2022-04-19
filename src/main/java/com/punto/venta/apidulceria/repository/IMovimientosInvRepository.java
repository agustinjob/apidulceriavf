package com.punto.venta.apidulceria.repository;

import java.util.List;

import com.punto.venta.apidulceria.model.MovimientosInv;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovimientosInvRepository extends CrudRepository<MovimientosInv,Integer> {
    @Query(value="select * from movimientos_inv where date(fecha)=?1 and tipo_movimiento=?2 and id_sucursal=?3",nativeQuery = true)
    List<MovimientosInv> obtenerMoviPorFechaTipo(String fecha,String tipo,String idSucursal);
    @Query(value="select * from movimientos_inv where date(fecha)=?1  and id_sucursal=?2",nativeQuery = true)
    List<MovimientosInv> obtenerMoviPorFecha(String fecha,String idSucursal);
}

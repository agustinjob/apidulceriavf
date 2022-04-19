package com.punto.venta.apidulceria.repository;

import java.util.List;

import com.punto.venta.apidulceria.model.Sucursal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends CrudRepository <Sucursal,Long> {
    
    List<Sucursal> findAllByEstatusSucursalAndIdUsuario(String estatusSucursal, String idUsuario);

    @Query(value="Select * from sucursal where propietario=?1 and estatus_sucursal=?2 and id_sucursal != ?3", nativeQuery=true)
    List<Sucursal> encontrarOtrasSucursales(String propietario, String estatus, Integer idSucursal);
}

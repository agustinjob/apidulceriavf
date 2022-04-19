package com.punto.venta.apidulceria.repository;

import java.util.List;

import com.punto.venta.apidulceria.model.Area;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAreaRepository extends CrudRepository<Area,Integer> {
    List<Area> findByIdSucursalAndEstatusArea(Integer idSucursal, String estatusArea);
    Area findByNombreAndIdSucursalAndEstatusArea(String nombre,Integer idSucursal, String estatusArea);
}

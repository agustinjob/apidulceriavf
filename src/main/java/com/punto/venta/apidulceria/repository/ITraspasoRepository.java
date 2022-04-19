package com.punto.venta.apidulceria.repository;

import com.punto.venta.apidulceria.model.Traspaso;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITraspasoRepository extends CrudRepository<Traspaso, Integer> {
    
}

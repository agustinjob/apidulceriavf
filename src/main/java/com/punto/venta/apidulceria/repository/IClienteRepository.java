package com.punto.venta.apidulceria.repository;

import java.util.List;

import com.punto.venta.apidulceria.model.Cliente;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends CrudRepository<Cliente,Integer>{

    Cliente findByEmailAndIdSucursalAndEstatusCliente(String email,Integer idSucursal, String estatusCliente);
    List<Cliente> findByIdSucursalAndEstatusCliente(Integer idSucursal, String estatusCliente);

}

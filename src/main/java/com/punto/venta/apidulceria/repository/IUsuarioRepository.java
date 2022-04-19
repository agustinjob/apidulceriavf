package com.punto.venta.apidulceria.repository;

import java.util.List;

import com.punto.venta.apidulceria.model.Usuario;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends CrudRepository<Usuario,Integer> {

    List<Usuario> findByIdUsuarioAndEliminadoAndIdSucursal(Integer idUsuario, boolean eliminado,Integer idSucursal);
    List<Usuario> findByIdSucursalAndEliminado(Integer idSucursal,boolean eliminado);
    Usuario findByIdSucursalAndEliminadoAndUsernameAndPassword(Integer idSucursal,boolean eliminado,String username,String password);
    
}

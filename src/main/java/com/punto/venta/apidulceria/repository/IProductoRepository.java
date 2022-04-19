package com.punto.venta.apidulceria.repository;

import java.util.List;

import com.punto.venta.apidulceria.model.Producto;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends CrudRepository<Producto,Integer> {
    
    List<Producto> findByIdSucursalAndEliminado(Integer idSucursal, boolean eliminado);
    Producto findByIdSucursalAndCodigo(Integer idSucursal, String codigo);
    
    /*@Query(value="select id_producto,id_sucursal,codigo,descripcion,cantidad,precio_costo,precio_venta,TRUNCATE ((precio_venta-precio_costo),2) as utilidad, (precio_costo*cantidad) as "+ 
    "preciocostototal, (precio_venta*cantidad) as precioventatotal,TRUNCATE(((precio_venta*cantidad) - (precio_costo*cantidad)),2) as utilidadtotal  from producto "+ 
    "where eliminado = false and id_sucursal=?1", nativeQuery=true)
    List<Producto> encontrarUtilidad(Integer idSucursal);*/

    @Query(value="select * from producto where cantidad <= inventario_minimo and eliminado = false and id_sucursal = ?1", nativeQuery = true)
    List<Producto> inventarioBajo (Integer idSucursal);

    @Query(value="Select * from producto where eliminado = false and descripcion like ?1%",nativeQuery = true)
    List<Producto> busquedaPorCaracter(String cadena); 
    // transferencia
    @Query(value="select * from producto where cantidad>0 and id_sucursal=?1 and eliminado=false and propietario=?2",nativeQuery=true)
    List<Producto> productosPorSucursal(Integer idSucursal, String propietario);
}

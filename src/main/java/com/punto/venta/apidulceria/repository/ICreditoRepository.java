package com.punto.venta.apidulceria.repository;

import java.util.List;

import com.punto.venta.apidulceria.model.Credito;
import com.punto.venta.apidulceria.model.corte.IDatosCorte;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ICreditoRepository extends CrudRepository<Credito,Integer>{

    @Query(value="SELECT * FROM credito WHERE id_ticket = ?1 and id_cliente = ?2 and fecha_venta = ?3 and id_sucursal= ?4",nativeQuery = true)
    List<Credito> getAbonosCliente(Integer idTicket,Integer idCliente, String fecha, Integer idSucursal);
    
    @Modifying
    @Query(value="Update credito SET finalizado=true where id_ticket=?1 and id_cliente = ?2 and fecha_venta = ?3 and id_sucursal= ?4",nativeQuery = true)
    void actualizaAFinalizadaCredito(Integer idTicket,Integer idCliente, String fecha, Integer idSucursal);

    @Query(value="SELECT sum(abonado) FROM credito WHERE finalizado = ?1 and id_cliente = ?2 and id_sucursal= ?3",nativeQuery = true)
    String getAbonosClientePorFinalizado(Boolean finalizado,Integer idCliente,  Integer idSucursal);

    @Modifying
    @Query(value="UPDATE credito SET finalizado=true where id_cliente = ?1 and id_sucursal=?2", nativeQuery = true)
    void liquidarAdeudoCredito(Integer idCliente, Integer idSucursal);

    @Query(value="SELECT sum(abonado) as dato FROM credito WHERE id_sucursal= ?1 and date(fecha)=?2",nativeQuery =true)
    IDatosCorte obtenerAbonosParaCorte(Integer idSucursal, String fecha);
  
    
}

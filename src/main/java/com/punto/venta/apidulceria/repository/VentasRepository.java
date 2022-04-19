package com.punto.venta.apidulceria.repository;

import java.util.Date;
import java.util.List;

import com.punto.venta.apidulceria.model.Ventas;
import com.punto.venta.apidulceria.model.corte.IDatosCorte;
import com.punto.venta.apidulceria.model.corte.IVentasCorte;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentasRepository extends CrudRepository<Ventas,Integer> {
    
    @Query(value = "select * from ventas where date(fecha) = ?1 and id_sucursal=?2", nativeQuery = true)
    List<Ventas> encontrarVentasPorFecha(Date fecha, Integer idSucursal);

    @Query(value="Select * from ventas where fecha=?2 and id_ticket=?1 and codigo=?3 and id_sucursal=?4",nativeQuery = true)
    Ventas findVentaByIdTicketAndFechaAndCodigoAndIdSucursal(Integer idTicket,String fecha,String codigo, Integer idSucursal);

    @Query(value="select * from ventas where date(fecha) = Date_format(DATE_SUB(NOW(),INTERVAL 1 DAY),'%Y-%m-%d') and id_sucursal=?1", nativeQuery = true)
    List<Ventas> encontrarVentasAyer(Integer idSucursal);

    @Query(value = "select * from ventas where date(fecha) >= Date_format(DATE_SUB(NOW(),INTERVAL 7 DAY),'%Y-%m-%d') && date(fecha) <= ?1 and id_sucursal=?2", nativeQuery = true)
    List<Ventas> ventasSemanaPasada(Date fecha, Integer idSucursal);

    @Query(value="select * from ventas where date(fecha) >= Date_format(DATE_SUB(NOW(),INTERVAL 1 MONTH),'%Y-%m-%d') && date(fecha) <= ?1 and id_sucursal=?2", nativeQuery = true)
    List<Ventas> ventasHaceUnMes(Date fecha, Integer idSucursal);

    @Query(value="select * from ventas where date(fecha) >= ?1 and date(fecha) <= ?2 and id_sucursal= ?3", nativeQuery = true)
    List<Ventas> ventasPorLapsoDeTiempo(String fechaI, String fechaF, Integer idSucursal);

    @Query(value="SELECT * FROM ventas where date(fecha) = ?1 and id_sucursal =?2 and devolucion = false  and devocompleta=false GROUP by fecha",nativeQuery=true)
    List<Ventas> ventasAgrupadasPorFecha(String fecha, Integer idSucursal);

    @Query(value="SELECT * FROM ventas WHERE id_ticket = ?1 and fecha = ?2  and id_sucursal=?3 and devocompleta = false" ,nativeQuery = true)
    List<Ventas> ventasPorTicket(String idTicket,String fecha,Integer idSucursal);

    @Modifying
    @Query(value="UPDATE ventas SET devolucion = true, devocompleta = true where id_ticket = ?1 and id_sucursal=?2 and fecha=?3",nativeQuery = true)
    void actualizarDevolucion(String idTicket,Integer idSucursal,String fecha);
    //ventas efectivo y a credito
    @Query(value="select sum(importe) as ventas, sum(precio_costo*cantidad) as costo from ventas where id_usuario = ?1  and devocompleta = false and date(fecha) = ?2 and tipo_compra=?3 and id_sucursal=?4", nativeQuery = true)
    IVentasCorte ventasParaCorte(Integer idUsuario, String fecha, String tipoCompra,Integer idSucursal);

    @Query(value="SELECT sum(importe) as dato FROM ventas where  devolucion = true and devocompleta = true and date(fecha) = ?1 and id_usuario= ?2 and id_sucursal=?3",nativeQuery = true)
    IDatosCorte obtenerDevolucionVentasParaCorte(String fecha,Integer idUsuario,Integer idSucursal);
    // sección cliente
    @Query(value="SELECT sum(importe) as importe FROM ventas WHERE id_cliente = ?1 and tipo_compra = 'Credito' and id_sucursal=?2 and devocompleta = false and finalizada = false",nativeQuery=true)
    String obtenerSaldoCliente(Integer idCliente,Integer idSucursal);

    @Query(value="SELECT * FROM ventas WHERE id_cliente = ?1 and id_sucursal=?2 and tipo_compra = 'Credito' and devocompleta = false and finalizada = false  GROUP BY id_ticket",nativeQuery = true)
    List<Ventas> obtenerTicketsCliente(Integer idCliente, Integer idSucursal);

    @Query(value="SELECT * FROM ventas WHERE id_sucursal = ?1 and id_ticket = ?2 and id_cliente = ?3 and fecha = ?4",nativeQuery=true)
    List<Ventas> getVentasPorTicketCliente(Integer idSucursal,Integer idTicket, Integer idCliente, String fecha);

    @Modifying
    @Query(value="UPDATE ventas SET finalizada=true where id_cliente = ?1 and id_sucursal=?2", nativeQuery = true)
    void liquidarAdeudoVentas(Integer idCliente, Integer idSucursal);
}

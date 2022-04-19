package com.punto.venta.apidulceria.controller;

import java.util.ArrayList;

import com.punto.venta.apidulceria.model.CorteModelo;
import com.punto.venta.apidulceria.model.Devolucion;
import com.punto.venta.apidulceria.model.FechasModel;
import com.punto.venta.apidulceria.model.Ventas;
import com.punto.venta.apidulceria.model.VentasModel;
import com.punto.venta.apidulceria.model.respuesta.VentasResponse;
import com.punto.venta.apidulceria.service.VentasService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableTransactionManagement
@RequestMapping("/v1")
public class VentasController {
    
    @Autowired
    VentasService service;

    @PostMapping("/ventas")
    public ResponseEntity<VentasResponse> guardarVentas(@RequestBody VentasModel ventas){
        VentasResponse are=new VentasResponse();
        try{
        are=service.almacenarVentas(ventas);
        if(are.isRealizado()==false){
            return new ResponseEntity<VentasResponse>(are,HttpStatus.CONFLICT); 
        }else{
            return new ResponseEntity<VentasResponse>(are,HttpStatus.OK); 
        }
    }catch(Exception e){
        are.setRealizado(false);
        are.setVentas(new ArrayList<Ventas>());
        are.setMensaje("Ocurrio un error, no se almacenarón las ventas, solo se almacenarán localmente");
        return new ResponseEntity<>(are,HttpStatus.INTERNAL_SERVER_ERROR);
    }
     
    }

    @PutMapping("/ventas")
    public ResponseEntity<VentasResponse> modificarVentas(@RequestBody Ventas ventas){
        return new ResponseEntity<VentasResponse>(service.modificarEliminar(ventas),HttpStatus.OK);
    }

    @GetMapping("/ventas-lapso/{tipoBusqueda}/{idSucursal}")
    public ResponseEntity<VentasResponse> ventasPorLapso(@PathVariable int tipoBusqueda,@PathVariable Integer idSucursal){
        return new ResponseEntity<VentasResponse>(service.ventasPorLapso(tipoBusqueda,idSucursal),HttpStatus.OK);
    }

    @PostMapping("/ventas-fiff")
    public ResponseEntity<VentasResponse> ventasPorFiYFf(@RequestBody FechasModel fechas){
        return new ResponseEntity<VentasResponse>(service.ventasPorFechaInicioFin(fechas.getFechaI(),fechas.getFechaF(),fechas.getIdSucursal()),HttpStatus.OK);
    }

    @GetMapping("/ventas/{fecha}/{idSucursal}")
    public ResponseEntity<VentasResponse> ventasAgrupadas(@PathVariable String fecha, @PathVariable Integer idSucursal){
        return new ResponseEntity<VentasResponse>(service.ventasAgrupadasPorFecha(fecha, idSucursal),HttpStatus.OK);
    }

    @GetMapping("/ventas-ticket/{idTicket}/{fecha}/{idSucursal}")
    public ResponseEntity<VentasResponse> ventasPorTicket(@PathVariable String idTicket, @PathVariable String fecha,@PathVariable Integer idSucursal){
        return new ResponseEntity<VentasResponse>(service.ventasPorTicket(idTicket, fecha,idSucursal),HttpStatus.OK);
    }

    @PutMapping("/ventas-devoluciones/{idTicket}/{fecha}/{idSucursal}")
    public ResponseEntity<VentasResponse> devoluciones(@PathVariable String idTicket, @PathVariable String fecha,@PathVariable Integer idSucursal){
        return new ResponseEntity<VentasResponse>(service.devolverVentasTicketTodas(idTicket, fecha,idSucursal),HttpStatus.OK);
    }

    @PutMapping("/ventas-devoluciones")
    public ResponseEntity<VentasResponse> devolucionPorparte(@RequestBody Devolucion devo){
        return new ResponseEntity<VentasResponse>(service.devolverVentaTicket(devo),HttpStatus.OK);
    }

    @GetMapping("/saldo-cliente/{idCliente}/{idSucursal}")
    public ResponseEntity<String> getObtenerSaldoCliente(@PathVariable Integer idCliente, @PathVariable Integer idSucursal){
        return new ResponseEntity<String>(service.getObtenerSaldoCliente(idCliente, idSucursal), HttpStatus.OK);
    }

    @GetMapping("/tickets-cliente/{idCliente}/{idSucursal}")
    public ResponseEntity<VentasResponse> getObtenerTicketsCliente(@PathVariable Integer idCliente, @PathVariable Integer idSucursal){
        return new ResponseEntity<VentasResponse>(service.getObtenerTicketsCliente(idCliente, idSucursal),HttpStatus.OK);
    }

    @GetMapping("/tickets-ventas-clientes/{idSucursal}/{idTicket}/{idCliente}/{fecha}")
    public ResponseEntity<VentasResponse> getVentasPorTicketClientes(@PathVariable Integer idSucursal, @PathVariable Integer idTicket, @PathVariable Integer idCliente, @PathVariable String fecha){
        return new ResponseEntity<VentasResponse>(service.getVentasPorTicketClientes(idSucursal, idTicket, idCliente, fecha), HttpStatus.OK);
    }

    @GetMapping("/corte-dia/{idUsuario}/{fecha}/{idSucursal}")
    public ResponseEntity<CorteModelo> corteDelDia(@PathVariable Integer idUsuario,@PathVariable String fecha,@PathVariable Integer idSucursal){
     return new ResponseEntity<CorteModelo>(service.realizarCorte(idUsuario, idSucursal, fecha),HttpStatus.OK);   

    }



   
}

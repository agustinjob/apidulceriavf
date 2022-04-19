package com.punto.venta.apidulceria.controller;

import com.punto.venta.apidulceria.model.MovimientosExtras;
import com.punto.venta.apidulceria.model.respuesta.MovimientosResponse;
import com.punto.venta.apidulceria.service.MovimientosExtrasService;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class MovimientosExtrasController {
    
    @Autowired
    MovimientosExtrasService service;

    @PostMapping("/movimientos")
    public ResponseEntity<MovimientosResponse> guardar(@RequestBody MovimientosExtras movimientos){
        return new ResponseEntity<MovimientosResponse>(service.guardar(movimientos),HttpStatus.OK);
    }

    @PostMapping("/movimientos-fecha")
    public ResponseEntity<MovimientosResponse> movimientos(@RequestBody MovimientosExtras movimientos){
        return new ResponseEntity<MovimientosResponse>(service.obtenerPorIdSucursalYFecha(movimientos),HttpStatus.OK);
    }

    @GetMapping("/movimientos-efectivo-inicial/{idUsuario}/{idSucursal}")
    public ResponseEntity<MovimientosResponse> obtenerInicioEfectivo( @PathVariable Integer idUsuario, @PathVariable Integer idSucursal){
        return new ResponseEntity<MovimientosResponse>(service.encontrarEfectivoInicial( idUsuario, idSucursal),HttpStatus.OK);
    }
}

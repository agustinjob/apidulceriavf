package com.punto.venta.apidulceria.controller;

import com.punto.venta.apidulceria.model.MovimientosInv;
import com.punto.venta.apidulceria.model.respuesta.MovimientosInvResponse;
import com.punto.venta.apidulceria.service.MovimientosInvService;

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
public class MovimientosInvController {
    
    @Autowired
    MovimientosInvService service;

    @PostMapping("/movimientosinv")
    public ResponseEntity<MovimientosInvResponse> guardar(@RequestBody MovimientosInv movi){
        return new ResponseEntity<MovimientosInvResponse>(service.guardar(movi),HttpStatus.OK);
    }

    @GetMapping("/movimientosinv/{fecha}/{tipo}/{idSucursal}")
    public ResponseEntity<MovimientosInvResponse> obtenerDatos(@PathVariable String fecha, @PathVariable String tipo, @PathVariable String idSucursal){
     return new ResponseEntity<MovimientosInvResponse>(service.obtenerDatos(fecha, tipo, idSucursal),HttpStatus.OK);
    }

}

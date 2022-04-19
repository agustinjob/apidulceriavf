package com.punto.venta.apidulceria.controller;

import java.util.List;

import com.punto.venta.apidulceria.excepciones.IncosistenciaException;
import com.punto.venta.apidulceria.model.Sucursal;
import com.punto.venta.apidulceria.model.TransferenciaProductos;
import com.punto.venta.apidulceria.model.respuesta.SucursalResponse;
import com.punto.venta.apidulceria.service.SucursalService;

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
public class SucursalController {
    
    @Autowired
    SucursalService service;

    @GetMapping("/sucursales/{idUsuario}")
    public List<Sucursal> obtenerSucursales(@PathVariable String idUsuario){
        return service.encontrarSucursales(idUsuario);
    }

    @PostMapping("/sucursales")
    public ResponseEntity<SucursalResponse> guardarSucursal(@RequestBody Sucursal sucursal){
        return new ResponseEntity<SucursalResponse>(service.guardarSucursal(sucursal), HttpStatus.OK);
    }

    @PostMapping("/sucursales-transferencia")
    public ResponseEntity<SucursalResponse> realizaTransferencia(@RequestBody TransferenciaProductos trans){
        try{
        SucursalResponse su=service.realizarTransferencia(trans);
        return new ResponseEntity<SucursalResponse>(su, HttpStatus.OK);
    }catch(IncosistenciaException e){
        SucursalResponse su=new SucursalResponse();
        su.setMensaje(e.getMessage());
        su.setRealizado(false);
        su.setSucursal(null);

        return new ResponseEntity<SucursalResponse>(su, HttpStatus.OK);

    }

       
    }

    @GetMapping("/sucursales/{propietario}/{idSucursal}")
    public ResponseEntity<SucursalResponse> encontrarOtrasSucursales(@PathVariable String propietario, @PathVariable Integer idSucursal){
        return new ResponseEntity<SucursalResponse>(service.otrasSucursales(propietario, idSucursal),HttpStatus.OK);
    }
}

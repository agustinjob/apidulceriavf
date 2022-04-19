package com.punto.venta.apidulceria.controller;

import com.punto.venta.apidulceria.model.Credito;
import com.punto.venta.apidulceria.model.respuesta.CreditoResponse;
import com.punto.venta.apidulceria.service.CreditoService;

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
public class CreditoController {
    @Autowired
    CreditoService service;

    @PostMapping("/creditos/{fecha}")
    public ResponseEntity<CreditoResponse> guardar(@PathVariable String fecha,@RequestBody Credito credito){
        return new ResponseEntity<CreditoResponse>(service.guardarCredito(credito,fecha),HttpStatus.OK);
    }

    @GetMapping("/creditos-abonos/{idTicket}/{idCliente}/{idSucursal}/{fecha}")
    public ResponseEntity<CreditoResponse> obtenerAbonos(@PathVariable Integer idTicket,@PathVariable Integer idCliente,@PathVariable Integer idSucursal,@PathVariable String fecha){
        return new ResponseEntity<CreditoResponse>(service.getAbonosClientes(idTicket, idCliente, idSucursal, fecha),HttpStatus.OK);
    }

    @GetMapping("/creditos-abonos/{idCliente}/{idSucursal}")
    public ResponseEntity<String> abonosClientesCuentasNoFinalizados(@PathVariable Integer idCliente,@PathVariable Integer idSucursal){
        return new ResponseEntity<String>(service.getAbonosClientesCuentasNoFinalizados(idCliente, idSucursal),HttpStatus.OK);
    }

    @PostMapping("/creditos-liquidar")
    public ResponseEntity<CreditoResponse> liquidarAdeudo(@RequestBody Credito cre){
        return new ResponseEntity<CreditoResponse>(service.liquidarAdeudo(cre),HttpStatus.OK);
    }

}

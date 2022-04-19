package com.punto.venta.apidulceria.controller;

import com.punto.venta.apidulceria.model.Cliente;
import com.punto.venta.apidulceria.model.respuesta.ClienteResponse;
import com.punto.venta.apidulceria.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1")
public class ClientesController {

    @Autowired
    ClienteService service;

    @GetMapping("/clientes/{idSucursal}")
    public ResponseEntity<ClienteResponse> obtenerClientes(@PathVariable Integer idSucursal){
     return new ResponseEntity<ClienteResponse>(service.getAllClientesByIdSucursal(idSucursal), HttpStatus.OK);
    }

    @PostMapping("/clientes")
    public ResponseEntity<ClienteResponse> guardarClientes(@RequestBody Cliente cliente){
        ClienteResponse are=service.guardar(cliente);
        if(are.isRealizado()==false){
            return new ResponseEntity<ClienteResponse>(are,HttpStatus.CONFLICT); 
        }else{
            return new ResponseEntity<ClienteResponse>(are,HttpStatus.OK); 
        }
    }

    @PutMapping("/clientes")
    public ResponseEntity<ClienteResponse> modificarCliente(@RequestBody Cliente cliente){
        return new ResponseEntity<ClienteResponse>(service.modificarEliminar(cliente),HttpStatus.OK);
    }

    @DeleteMapping("/clientes")
    public ResponseEntity<ClienteResponse> eliminarCliente(@RequestBody Cliente cliente){
        cliente.setEliminado(true);
        return new ResponseEntity<ClienteResponse>(service.modificarEliminar(cliente),HttpStatus.OK);
    }


}

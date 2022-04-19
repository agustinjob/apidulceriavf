package com.punto.venta.apidulceria.controller;

import com.punto.venta.apidulceria.model.Traspaso;
import com.punto.venta.apidulceria.model.respuesta.TraspasoResponse;
import com.punto.venta.apidulceria.service.TraspasoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class TraspasoController {
    
    @Autowired
    TraspasoService service;

    @PostMapping("/traspasos")
    public ResponseEntity<TraspasoResponse> guardarTraspaso(@RequestBody Traspaso traspaso){
        TraspasoResponse are=service.guardar(traspaso);
        if(are.isRealizado()==false){
            return new ResponseEntity<TraspasoResponse>(are,HttpStatus.CONFLICT); 
        }else{
            return new ResponseEntity<TraspasoResponse>(are,HttpStatus.OK); 
        }
     
    }

    @PutMapping("/traspasos")
    public ResponseEntity<TraspasoResponse> modificarTraspaso(@RequestBody Traspaso traspaso){
        return new ResponseEntity<TraspasoResponse>(service.modificarEliminar(traspaso),HttpStatus.OK);
    }

  /*  @DeleteMapping("/traspasos")
    public ResponseEntity<TraspasoResponse> eliminarTraspaso(@RequestBody Traspaso traspaso){
        
        return new ResponseEntity<TraspasoResponse>(service.modificarEliminar(traspaso),HttpStatus.OK);
    }*/

}

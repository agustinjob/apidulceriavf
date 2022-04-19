package com.punto.venta.apidulceria.controller;

import com.punto.venta.apidulceria.model.Area;
import com.punto.venta.apidulceria.model.respuesta.AreaResponse;
import com.punto.venta.apidulceria.service.AreaService;

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
public class AreaController {

    @Autowired
    AreaService service;

    @PostMapping("/areas")
    public ResponseEntity<AreaResponse> guardarArea(@RequestBody Area area){
        AreaResponse are=service.guardar(area);
        if(are.isRealizado()==false){
            return new ResponseEntity<AreaResponse>(are,HttpStatus.CONFLICT); 
        }else{
            return new ResponseEntity<AreaResponse>(are,HttpStatus.OK); 
        }
     
    }
    @GetMapping("/areas/{idSucursal}")
    public ResponseEntity<AreaResponse> obtenerAreas(@PathVariable Integer idSucursal){
        AreaResponse are= service.obtenerAreas(idSucursal);
        return new ResponseEntity<AreaResponse>(are,HttpStatus.OK);
    }

    @PostMapping("/areas-nombre")
    public ResponseEntity<AreaResponse> obtenerAreaPorNombre(@RequestBody Area area){
        AreaResponse are= service.obtenerAreaPorNombre(area.getIdSucursal(), area.getNombre(), "vigente");
        return new ResponseEntity<AreaResponse>(are,HttpStatus.OK);
    }

    @PutMapping("/areas")
    public ResponseEntity<AreaResponse> modificarArea(@RequestBody Area area){
        return new ResponseEntity<AreaResponse>(service.modificarEliminar(area),HttpStatus.OK);
    }

    @DeleteMapping("/areas")
    public ResponseEntity<AreaResponse> eliminarArea(@RequestBody Area area){
        area.setEstatusArea("eliminado");
        return new ResponseEntity<AreaResponse>(service.modificarEliminar(area),HttpStatus.OK);
    }
    
}


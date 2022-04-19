package com.punto.venta.apidulceria.controller;

import com.punto.venta.apidulceria.model.Usuario;
import com.punto.venta.apidulceria.model.respuesta.UsuarioResponse;
import com.punto.venta.apidulceria.service.UsuarioService;

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
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioResponse> guardarUsuario(@RequestBody Usuario usuario){
        UsuarioResponse usu=service.guardarUsuario(usuario);
        if(usu.isRealizado()==false){
            return new ResponseEntity<UsuarioResponse>(usu,HttpStatus.CONFLICT); 
        }else{
            return new ResponseEntity<UsuarioResponse>(usu,HttpStatus.OK); 
        }
    }
    @PostMapping("/usuarios-login")
    public ResponseEntity<UsuarioResponse> login(@RequestBody Usuario usuario){
        UsuarioResponse usu=service.login(usuario);
            return new ResponseEntity<UsuarioResponse>(usu,HttpStatus.OK); 
        
    }

    @GetMapping("/usuarios/{idUsuario}/{idSucursal}")
    public ResponseEntity<UsuarioResponse> getUsuarioPorIdYSucursal(@PathVariable Integer idSucursal, @PathVariable Integer idUsuario){
        return new ResponseEntity<UsuarioResponse>(service.obtenerUsuarioPorIdSucursal(idSucursal, idUsuario),HttpStatus.OK);
    }

    @GetMapping("/usuarios/{idSucursal}")
    public ResponseEntity<UsuarioResponse> getUsuarioPorIdSucursal(@PathVariable Integer idSucursal){
        return new ResponseEntity<UsuarioResponse>(service.obtenerUsuariosPorSucursal(idSucursal),HttpStatus.OK);
    }

    @PutMapping("/usuarios")
    public ResponseEntity<UsuarioResponse> modificarUsuario(@RequestBody Usuario usuario){
        return new ResponseEntity<UsuarioResponse>(service.modificarEliminarUsuario(usuario),HttpStatus.OK);
    }

    @DeleteMapping("/usuarios")
    public ResponseEntity<UsuarioResponse> eliminarUsuario(@RequestBody Usuario usuario){
        usuario.setEliminado(true);
        return new ResponseEntity<UsuarioResponse>(service.modificarEliminarUsuario(usuario),HttpStatus.OK);
    }
    
}

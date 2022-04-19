package com.punto.venta.apidulceria.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.punto.venta.apidulceria.model.Usuario;
import com.punto.venta.apidulceria.model.respuesta.UsuarioResponse;
import com.punto.venta.apidulceria.repository.IUsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    
@Autowired
IUsuarioRepository repository;

UsuarioResponse response=new UsuarioResponse();

public UsuarioResponse guardarUsuario(Usuario usuario) {
List<Usuario> lista = new ArrayList<Usuario>();
Date d= new Date();
Optional<Usuario> u=repository.findById(usuario.getIdUsuario());


if(u.isPresent()) {
lista.add(usuario);
    response.setUsuarios(lista);
    response.setMensaje("Ya se encuentra un usuario con el mismo id en la base de datos");
    response.setRealizado(false);
return  response;  
}

usuario.setFecha(d);
Usuario user=repository.save(usuario);
lista.add(user);
response.setUsuarios(lista);
response.setMensaje("Usuario agregado correctamente");
response.setRealizado(true);
return response;

}

public UsuarioResponse modificarEliminarUsuario(Usuario usuario){
    List<Usuario> lista = new ArrayList<Usuario>();
    Usuario usu=repository.save(usuario);
    lista.add(usu);
    response.setUsuarios(lista);
    response.setMensaje(usu.isEliminado()==true?"Datos del usuario eliminados correctamente":"Datos del usuario modificados correctamente");
    response.setRealizado(true);
    return response;
}

public UsuarioResponse obtenerUsuarioPorIdSucursal(Integer idSucursal, Integer idUsuario){
    
    List<Usuario> list=repository.findByIdUsuarioAndEliminadoAndIdSucursal(idUsuario, false, idSucursal);
    System.out.println("Numero " +list.size() );
    response.setUsuarios(list);
    response.setRealizado(true);
    response.setMensaje(list.isEmpty()==true?"No se encontro ningún usuario con esa información":"Usuario encontrado");
    return response;
}

public UsuarioResponse obtenerUsuariosPorSucursal(Integer idSucursal){
    List<Usuario> list= repository.findByIdSucursalAndEliminado(idSucursal, false);
    response.setUsuarios(list);
    response.setRealizado(true);
    response.setMensaje(list.isEmpty()==true?"No se encontraron usuarios":"Usuarios encontrados");
    return response;
}

public UsuarioResponse login(Usuario usu){
    Usuario u=repository.findByIdSucursalAndEliminadoAndUsernameAndPassword(usu.getIdSucursal(), usu.isEliminado(), usu.getUsername(), usu.getPassword());
    List<Usuario> lista = new ArrayList<Usuario>();
    lista.add(u);
    response.setUsuarios(lista);
    response.setMensaje(lista.isEmpty()==true?"Nombre de usuario o password incorrectos, vuelve a intentarlo":"Datos correctos");
    response.setRealizado(!lista.isEmpty());
    return response;
}
}

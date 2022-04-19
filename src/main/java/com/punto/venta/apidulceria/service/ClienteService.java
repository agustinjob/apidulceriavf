package com.punto.venta.apidulceria.service;

import java.util.ArrayList;
import java.util.List;

import com.punto.venta.apidulceria.model.Cliente;
import com.punto.venta.apidulceria.model.Credito;
import com.punto.venta.apidulceria.model.respuesta.ClienteResponse;
import com.punto.venta.apidulceria.repository.IClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    
    @Autowired
    IClienteRepository repository;

    ClienteResponse clienteResponse=new ClienteResponse();
    List<Cliente> lista = new ArrayList<Cliente>();

    public ClienteResponse guardar(Cliente cliente) {

      Cliente ar=  repository.findByEmailAndIdSucursalAndEstatusCliente(cliente.getEmail(),cliente.getIdSucursal(),"vigente");

      if(ar!=null){
          lista.add(ar);
          clienteResponse.setClientes(lista);
          clienteResponse.setMensaje("Ya existe un cliente registrado previamente, con ese email, el nombre del otro cliente es: "+ ar.getNombre());
          clienteResponse.setRealizado(false);
          return clienteResponse;
      }

      lista.add(repository.save(cliente));
      clienteResponse.setClientes(lista);
      clienteResponse.setMensaje("Datos registrados satisfactoriamente");
      clienteResponse.setRealizado(true);
      return clienteResponse;        
    }

    public ClienteResponse modificarEliminar(Cliente cliente){
       lista.add(repository.save(cliente));
       clienteResponse.setClientes(lista);
       clienteResponse.setMensaje(cliente.getEstatusCliente().equalsIgnoreCase("eliminado")?"Datos eliminados correctamente":"Datos modificados correctamente");
       clienteResponse.setRealizado(true);
       return clienteResponse; 
    }

    public ClienteResponse getAllClientesByIdSucursal(Integer idSucursal){
      clienteResponse.setClientes(repository.findByIdSucursalAndEstatusCliente(idSucursal, "vigente"));
      String mensaje=clienteResponse.getClientes().isEmpty()==true ? "No se encontraron datos":"Clientes obtenidos";
      clienteResponse.setMensaje(mensaje);
      clienteResponse.setRealizado(true);
      return clienteResponse;
    }

   
}

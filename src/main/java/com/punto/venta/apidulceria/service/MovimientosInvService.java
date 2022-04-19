package com.punto.venta.apidulceria.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.punto.venta.apidulceria.model.MovimientosInv;
import com.punto.venta.apidulceria.model.respuesta.MovimientosInvResponse;
import com.punto.venta.apidulceria.repository.IMovimientosInvRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimientosInvService {
    
    @Autowired
    IMovimientosInvRepository repository;
    List<MovimientosInv> lista;
    MovimientosInvResponse movires= new MovimientosInvResponse();

    public MovimientosInvResponse guardar(MovimientosInv movi){
        lista= new ArrayList<MovimientosInv>();
        movi.setFecha(new Date());
        lista.add(repository.save(movi));
        movires.setLista(lista);
        movires.setMensaje("Realizado");
        movires.setRealizado(!lista.isEmpty());
       return movires;
    }

    public MovimientosInvResponse obtenerDatos(String fecha,String tipo,String idSucursal){
        lista= new ArrayList<MovimientosInv>();
        if(tipo.equalsIgnoreCase("todos")){
            lista=repository.obtenerMoviPorFecha(fecha, idSucursal);
        }else{
            String t=tipo.equalsIgnoreCase("Entradas")?"Entrada":tipo.equalsIgnoreCase("Salidas")?"Salida":tipo.equalsIgnoreCase("Devoluciones")?"Devolución":"Todos";
            lista=repository.obtenerMoviPorFechaTipo(fecha, t, idSucursal);
        }
        movires.setLista(lista);
        movires.setMensaje("Realizado");
        movires.setRealizado(!lista.isEmpty());
       return movires;
        

    }


}

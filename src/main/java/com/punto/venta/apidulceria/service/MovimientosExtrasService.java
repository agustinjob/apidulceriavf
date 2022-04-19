package com.punto.venta.apidulceria.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.punto.venta.Utilidades.Utilities;
import com.punto.venta.apidulceria.model.MovimientosExtras;
import com.punto.venta.apidulceria.model.respuesta.MovimientosResponse;
import com.punto.venta.apidulceria.repository.IMovimientosExtras;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimientosExtrasService {
    
    @Autowired
    IMovimientosExtras repository;
    MovimientosResponse movi=new MovimientosResponse();
    List<MovimientosExtras> lista;

    public MovimientosResponse guardar(MovimientosExtras mov){
        lista=new ArrayList<MovimientosExtras>();
        mov.setFecha(new Date());
        lista.add(repository.save(mov));
        movi.setMovimientos(lista);
        movi.setRealizado(true);
        movi.setMensaje("Datos agregados correctamente");
        return movi; 
    }

    public MovimientosResponse obtenerPorIdSucursalYFecha(MovimientosExtras mov)
    {
   
     movi.setMovimientos(repository.findByTipoAndFechaAndIdSucursalAndIdUsuario(mov.getTipo(),Utilities.getFechaString(), mov.getIdUsuario(), mov.getIdSucursal()));
     movi.setMensaje("realizado");
     movi.setRealizado(true);
     return movi;   
        
    }

    public MovimientosResponse encontrarEfectivoInicial( Integer idUsuario, Integer idSucursal)
    {
        lista=new ArrayList<MovimientosExtras>();
        String d=Utilities.getFechaString();
        MovimientosExtras mo=repository.encontrarEfectivoInicias(d, idUsuario, idSucursal);
       if(mo != null)
        lista.add(mo);
        
     movi.setMovimientos(lista);
     movi.setMensaje(lista.isEmpty()==true?"No hay":"Si hay");
     movi.setRealizado(true);
     return movi;   
        
    }
}

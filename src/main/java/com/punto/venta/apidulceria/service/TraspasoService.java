package com.punto.venta.apidulceria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.punto.venta.apidulceria.model.Traspaso;
import com.punto.venta.apidulceria.model.respuesta.TraspasoResponse;
import com.punto.venta.apidulceria.repository.ITraspasoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraspasoService {
    
    @Autowired
    ITraspasoRepository repository;

    TraspasoResponse traspasoResponse=new TraspasoResponse();
    List<Traspaso> lista = new ArrayList<Traspaso>();

    public TraspasoResponse guardar(Traspaso traspaso) {

      Optional <Traspaso> ar=  repository.findById(traspaso.getIdTraspaso());
      

      if(ar.isPresent()){
          lista.add(ar.get());
          traspasoResponse.setTraspasos(lista);
          traspasoResponse.setMensaje("Ya existe un traspaso registrada previamente");
          traspasoResponse.setRealizado(false);
          return traspasoResponse;
      }

      lista.add(repository.save(traspaso));
      traspasoResponse.setTraspasos(lista);
      traspasoResponse.setMensaje("realizado");
      traspasoResponse.setRealizado(true);
      return traspasoResponse;        
    }

    public TraspasoResponse modificarEliminar(Traspaso traspaso){
        lista.add(repository.save(traspaso));
       traspasoResponse.setTraspasos(lista);
       traspasoResponse.setMensaje("realizado");
       traspasoResponse.setRealizado(true);
       return traspasoResponse; 
    }
}

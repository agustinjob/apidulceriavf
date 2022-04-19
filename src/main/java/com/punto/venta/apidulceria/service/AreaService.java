package com.punto.venta.apidulceria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.punto.venta.apidulceria.model.Area;
import com.punto.venta.apidulceria.model.respuesta.AreaResponse;
import com.punto.venta.apidulceria.repository.IAreaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaService {
    
    @Autowired
    IAreaRepository repository;

    AreaResponse areaResponse=new AreaResponse();
    List<Area> lista ;

    public AreaResponse guardar(Area area) {
        lista= new ArrayList<Area>();

      Optional <Area> ar=  repository.findById(area.getIdArea());
      

      if(ar.isPresent()){
          lista.add(ar.get());
          areaResponse.setAreas(lista);
          areaResponse.setMensaje("Ya existe un área registrada previamente");
          areaResponse.setRealizado(false);
          return areaResponse;
      }

      lista.add(repository.save(area));
      areaResponse.setAreas(lista);
      areaResponse.setMensaje("Datos registrados satisfactoriamente");
      areaResponse.setRealizado(true);
      return areaResponse;        
    }

    public AreaResponse modificarEliminar(Area area){
        lista.add(repository.save(area));
       areaResponse.setAreas(lista);
       areaResponse.setMensaje("realizado");
       areaResponse.setRealizado(true);
       return areaResponse; 
    }

    public AreaResponse obtenerAreas(Integer idSucursal){
        areaResponse.setAreas(repository.findByIdSucursalAndEstatusArea(idSucursal, "vigente"));
        areaResponse.setMensaje(areaResponse.getAreas().isEmpty()==true?"No hay áreas registradas":"Áreas encontradas");
        areaResponse.setRealizado(true);
        return areaResponse;
    }

    public AreaResponse obtenerAreaPorNombre(Integer idSucursal,String nombre, String estatusArea){
        lista= new ArrayList<Area>();
        Area a=repository.findByNombreAndIdSucursalAndEstatusArea(nombre,idSucursal, "vigente");

        if(a != null)
        lista.add(a);

        areaResponse.setAreas(lista);
        areaResponse.setMensaje(lista.isEmpty()==true?"No":"Si");
        areaResponse.setRealizado(!lista.isEmpty());
        return areaResponse;
    }
}



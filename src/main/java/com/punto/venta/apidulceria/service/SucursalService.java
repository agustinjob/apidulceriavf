package com.punto.venta.apidulceria.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.punto.venta.apidulceria.excepciones.IncosistenciaException;
import com.punto.venta.apidulceria.model.MovimientosInv;
import com.punto.venta.apidulceria.model.Producto;
import com.punto.venta.apidulceria.model.Sucursal;
import com.punto.venta.apidulceria.model.TransferenciaProductos;
import com.punto.venta.apidulceria.model.respuesta.SucursalResponse;
import com.punto.venta.apidulceria.repository.IMovimientosInvRepository;
import com.punto.venta.apidulceria.repository.IProductoRepository;
import com.punto.venta.apidulceria.repository.SucursalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SucursalService {
    
    @Autowired
    SucursalRepository repository;

    @Autowired
    IProductoRepository iproducto;

    @Autowired
    IMovimientosInvRepository imovInv;

    List<Sucursal> lista= new ArrayList<Sucursal>();


    public List<Sucursal> encontrarSucursales(String idUsuario){
        return repository.findAllByEstatusSucursalAndIdUsuario("vigente",idUsuario);
    }

    public SucursalResponse guardarSucursal(Sucursal sucursal){
        SucursalResponse sure= new SucursalResponse(); 
        lista.add(repository.save(sucursal));
        sure.setSucursal(lista);
        sure.setMensaje("Sucursal agregada exitosamente");
        sure.setRealizado(true);
        return sure; 
    }

    public SucursalResponse otrasSucursales(String propietario,Integer idSucursal){
        SucursalResponse sure= new SucursalResponse(); 
        List<Sucursal> lista=repository.encontrarOtrasSucursales(propietario, "vigente", idSucursal);
        sure.setMensaje(lista.isEmpty()==true?"No hay otras sucursales registradas":"Sucursales encontradas");
        sure.setRealizado(true);
        sure.setSucursal(lista);
        return sure;
    }
    @Transactional
    public SucursalResponse realizarTransferencia(TransferenciaProductos trans) throws IncosistenciaException{
        SucursalResponse sure= new SucursalResponse(); 
        sure.setRealizado(true);
        sure.setMensaje("Realizado correctamente");
        List<Producto> listaModi= new ArrayList<>();

        List<Producto> lista= trans.getLista();
        for(Producto p:lista){
           Producto penvia= iproducto.findByIdSucursalAndCodigo(trans.getIdSucursalLocal(), p.getCodigo());
           Producto precibe= iproducto.findByIdSucursalAndCodigo(trans.getIdSucursalRecibe(), p.getCodigo());
            MovimientosInv movLocal=new MovimientosInv();
            MovimientosInv movRecibe=new MovimientosInv();
           if(precibe == null){
            throw new IncosistenciaException("Error: El producto con código '"+penvia.getCodigo()+"' no se encuentra en la otra sucursal");
           }
           float invLocal= penvia.getCantidad()- p.getCantidad();
           float invRecibe= precibe.getCantidad()+p.getCantidad();
         
           movLocal.setCantidad(p.getCantidad());
           movRecibe.setCantidad(p.getCantidad());
           movLocal.setDepartamento(penvia.getArea());
           movRecibe.setDepartamento(precibe.getArea());
           movLocal.setDescripcion("Transferencia sucursales");
           movRecibe.setDescripcion("Transferencia sucursales");
           movLocal.setTipoMovimiento("Salida");
           movRecibe.setTipoMovimiento("Entrada");
           movLocal.setFecha(new Date());
           movRecibe.setFecha(new Date());
           movLocal.setHabia(penvia.getCantidad());
           movRecibe.setHabia(precibe.getCantidad());
           movLocal.setHay(invLocal);
           movRecibe.setHay(invRecibe);
           movLocal.setIdSucursal(trans.getIdSucursalLocal());
           movRecibe.setIdSucursal(trans.getIdSucursalRecibe());
           movLocal.setIdUsuario(trans.getIdUsuario());
           movRecibe.setIdUsuario(trans.getIdUsuario());
           movLocal.setNombre(penvia.getDescripcion());
           movRecibe.setNombre(precibe.getDescripcion());
           movLocal.setPropietario(trans.getPropietarioLocal());
           movRecibe.setPropietario(trans.getPropietarioRecibe());
           penvia.setCantidad(invLocal);
           precibe.setCantidad(invRecibe);
        
            listaModi.add( iproducto.save(penvia));
            listaModi.add( iproducto.save(precibe));   
           imovInv.save(movLocal);
           imovInv.save(movRecibe);
        }
        

        return sure;
    }
}

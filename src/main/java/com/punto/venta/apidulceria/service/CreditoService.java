package com.punto.venta.apidulceria.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.punto.venta.Utilidades.Utilities;
import com.punto.venta.apidulceria.model.Credito;
import com.punto.venta.apidulceria.model.Ventas;
import com.punto.venta.apidulceria.model.respuesta.CreditoResponse;
import com.punto.venta.apidulceria.model.respuesta.VentasResponse;
import com.punto.venta.apidulceria.repository.ICreditoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreditoService {
    
    @Autowired
    ICreditoRepository repository;

    @Autowired
    VentasService ventasService;

    List<Credito> lista;
    CreditoResponse response= new CreditoResponse();


    @Transactional
    public CreditoResponse guardarCredito(Credito credito,String fecha){
        lista= new ArrayList<Credito>();
        credito.setFechaVenta(Utilities.convertirFechaStringCompletoADate(fecha));
        credito.setFecha(new Date());
        if(credito.isFinalizado()){
        VentasResponse v=  ventasService.getVentasPorTicketClientes(credito.getIdSucursal(), credito.getIdTicket(), credito.getIdCliente(), fecha);
        for(Ventas vo: v.getVentas()){
          vo.setFinalizada(true);
          ventasService.guardar(vo);
        }
        repository.actualizaAFinalizadaCredito(credito.getIdTicket(), credito.getIdCliente(), fecha, credito.getIdSucursal());
        }
        lista.add(repository.save(credito));
        response.setCreditos(lista);
        response.setMensaje("Datos registrados satisfactoriamente");
        response.setRealizado(true);
        return response;
    }

    public CreditoResponse getAbonosClientes(Integer idTicket, Integer idCliente, Integer idSucursal, String fecha){
      List<Credito> lista=  repository.getAbonosCliente(idTicket, idCliente, fecha, idSucursal);
      response.setCreditos(lista);
      response.setRealizado(true);
      response.setMensaje(lista.isEmpty()==true?"No hay abonos del cliente":"Abonos recuperados");
      return response;

    }

    public String getAbonosClientesCuentasNoFinalizados(Integer idCliente, Integer idSucursal){
      String abonado=repository.getAbonosClientePorFinalizado(false,idCliente,idSucursal);
      return abonado==null?"0":abonado;
    }

    @Transactional
    public CreditoResponse liquidarAdeudo(Credito cre){
      List<Credito> lista= new ArrayList<>();
      cre.setFecha(new Date());
      cre.setFechaVenta(new Date());
      Credito c=repository.save(cre);
      lista.add(c);
      ventasService.liquidarAdeudoVentas(cre.getIdCliente(), cre.getIdSucursal());
      repository.liquidarAdeudoCredito(cre.getIdCliente(), cre.getIdSucursal());
      response.setCreditos(lista);
      response.setMensaje("Se ha registrado la operación satisfactoriamente");
      response.setRealizado(true);
      return response;

    }

    
}

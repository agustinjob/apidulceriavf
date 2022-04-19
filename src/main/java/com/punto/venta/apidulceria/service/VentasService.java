package com.punto.venta.apidulceria.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.punto.venta.Utilidades.Utilities;
import com.punto.venta.apidulceria.model.CorteModelo;
import com.punto.venta.apidulceria.model.Devolucion;
import com.punto.venta.apidulceria.model.MovimientosExtras;
import com.punto.venta.apidulceria.model.MovimientosInv;
import com.punto.venta.apidulceria.model.Producto;
import com.punto.venta.apidulceria.model.Ventas;
import com.punto.venta.apidulceria.model.VentasModel;
import com.punto.venta.apidulceria.model.corte.IDatosCorte;
import com.punto.venta.apidulceria.model.corte.IVentasCorte;
import com.punto.venta.apidulceria.model.respuesta.VentasResponse;
import com.punto.venta.apidulceria.repository.ICreditoRepository;
import com.punto.venta.apidulceria.repository.IMovimientosExtras;
import com.punto.venta.apidulceria.repository.IProductoRepository;
import com.punto.venta.apidulceria.repository.VentasRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VentasService {

    @Autowired
    VentasRepository repository;

    @Autowired
    IProductoRepository repositoryProducto;

    @Autowired
    IMovimientosExtras repositoryMovimientos;

    @Autowired
    MovimientosInvService serviceMo;

    @Autowired
    ICreditoRepository repositoryCredito;

    VentasResponse ventasResponse = new VentasResponse();
    List<Ventas> lista = new ArrayList<Ventas>();

    public VentasResponse guardar(Ventas ventas) {

        Optional<Ventas> ar = repository.findById(ventas.getIdVenta());

        if (ar.isPresent()) {
            lista.add(ar.get());
            ventasResponse.setVentas(lista);
            ventasResponse.setMensaje("Ya existe un ventas registrada previamente");
            ventasResponse.setRealizado(false);
            return ventasResponse;
        }

        lista.add(repository.save(ventas));
        ventasResponse.setVentas(lista);
        ventasResponse.setMensaje("realizado");
        ventasResponse.setRealizado(true);
        return ventasResponse;
    }

    public VentasResponse modificarEliminar(Ventas ventas) {
        lista.add(repository.save(ventas));
        ventasResponse.setVentas(lista);
        ventasResponse.setMensaje("realizado");
        ventasResponse.setRealizado(true);
        return ventasResponse;
    }

    @Transactional
    public VentasResponse almacenarVentas(VentasModel ventas) {
        List<Ventas> listVen = ventas.getVentas();
        Date fecha = new Date();
        for (Ventas ven : listVen) {
            MovimientosInv mov = new MovimientosInv();
            float cantidad = ven.getCantidad();
            Producto pro = repositoryProducto.findByIdSucursalAndCodigo(ven.getIdSucursal(), ven.getCodigo());
            float total = pro.getCantidad() - cantidad;
            mov.setCantidad(cantidad);
            mov.setDepartamento(pro.getArea());
            mov.setNombre(pro.getDescripcion());
            mov.setDescripcion("Venta, ticket: " + ven.getIdTicket());
            mov.setFecha(new Date());
            mov.setHabia(pro.getCantidad());
            mov.setHay(total);
            mov.setIdMovimientoInv(0);
            mov.setIdSucursal(pro.getIdSucursal());
            mov.setIdUsuario(ven.getIdUsuario());
            mov.setPropietario(pro.getPropietario());
            mov.setTipoMovimiento("Salida");
            serviceMo.guardar(mov);
            pro.setCantidad(total);
            ven.setFecha(fecha);
            Ventas obj = repository.save(ven);
            repositoryProducto.save(pro);
            lista.add(obj);

        }
        ventasResponse.setMensaje("Venta realizada exitosamente");
        ventasResponse.setRealizado(true);
        ventasResponse.setVentas(lista);
        return ventasResponse;
    }

    public VentasResponse ventasPorLapso(int tipoBusqueda, Integer idSucursal) {
        List<Ventas> lista = new ArrayList<Ventas>();

        if (tipoBusqueda == 1) {
            lista = repository.encontrarVentasPorFecha(Utilities.getFecha(), idSucursal);
        }
        if (tipoBusqueda == 2) {
            lista = repository.encontrarVentasAyer(idSucursal);
        }
        if (tipoBusqueda == 3) {
            lista = repository.ventasSemanaPasada(Utilities.getFecha(), idSucursal);
        }
        if (tipoBusqueda == 4) {
            lista = repository.ventasHaceUnMes(Utilities.getFecha(), idSucursal);
        }
        ventasResponse.setVentas(lista);
        ventasResponse.setMensaje(lista.isEmpty() == true ? "No hay datos en ese lapso" : "Información encontrada");
        ventasResponse.setRealizado(true);

        return ventasResponse;

    }

    public VentasResponse ventasPorFechaInicioFin(String fechaI, String fechaF, Integer idSucursal) {
        List<Ventas> lista = new ArrayList<Ventas>();
        lista = repository.ventasPorLapsoDeTiempo(fechaI, fechaF, idSucursal);
        ventasResponse.setVentas(lista);
        ventasResponse.setMensaje(lista.isEmpty() == true ? "No hay datos en ese lapso" : "Información encontrada");
        ventasResponse.setRealizado(true);

        return ventasResponse;

    }

    public VentasResponse ventasAgrupadasPorFecha(String fecha, Integer idSucursal) {
        List<Ventas> lista = new ArrayList<Ventas>();
        lista = repository.ventasAgrupadasPorFecha(fecha, idSucursal);
        ventasResponse.setVentas(lista);
        ventasResponse.setMensaje(lista.isEmpty() == true ? "No hay ventas en la fecha especificada"
                : "Datos obtenidos satisfactoriamente");
        ventasResponse.setRealizado(true);
        return ventasResponse;
    }

    public VentasResponse ventasPorTicket(String idTicket, String fecha, Integer idSucursal) {
        List<Ventas> lista = new ArrayList<Ventas>();
        lista = repository.ventasPorTicket(idTicket, fecha, idSucursal);
        ventasResponse.setVentas(lista);
        ventasResponse.setMensaje(lista.isEmpty() == true ? "No hay ventas en la fecha especificada"
                : "Datos obtenidos satisfactoriamente");
        ventasResponse.setRealizado(true);
        return ventasResponse;
    }

    @Transactional
    public VentasResponse devolverVentasTicketTodas(String idTicket, String fecha, Integer idSucursal) {
        List<Ventas> lista = new ArrayList<Ventas>();
        lista = repository.ventasPorTicket(idTicket, fecha, idSucursal);
        MovimientosInv mov = new MovimientosInv();

        try {
            for (Ventas v : lista) {
                Producto p = repositoryProducto.findByIdSucursalAndCodigo(v.getIdSucursal(), v.getCodigo());
                float totalSuma = p.getCantidad() + v.getCantidad();
                mov.setCantidad(v.getCantidad());
                mov.setDepartamento(p.getArea());
                mov.setNombre(p.getDescripcion());
                mov.setDescripcion("Ticket: " + v.getIdTicket());
                mov.setFecha(new Date());
                mov.setHabia(p.getCantidad());
                mov.setHay(totalSuma);
                mov.setIdMovimientoInv(0);
                mov.setIdSucursal(p.getIdSucursal());
                mov.setIdUsuario(v.getIdUsuario());
                mov.setPropietario(p.getPropietario());
                mov.setTipoMovimiento("Devolución");
                serviceMo.guardar(mov);
                p.setCantidad(totalSuma);
                repositoryProducto.save(p);
            }
            ventasResponse.setVentas(lista);
            ventasResponse.setMensaje("Devolución registrada");
            ventasResponse.setRealizado(true);
            repository.actualizarDevolucion(idTicket, idSucursal, fecha);
            return ventasResponse;

        } catch (Exception e) {

        }
        ventasResponse.setVentas(lista);
        ventasResponse.setMensaje("Devolución no registrada, ocurrio un error por favor vuelve a intentarlo");
        ventasResponse.setRealizado(false);

        return ventasResponse;

    }

    @Transactional
    public VentasResponse devolverVentaTicket(Devolucion dev) {
        List<Ventas> lista = new ArrayList<Ventas>();
        Ventas v = repository.findVentaByIdTicketAndFechaAndCodigoAndIdSucursal(dev.getIdTicket(), dev.getFecha(),
                dev.getCodigo(), dev.getIdSucursal());
        lista.add(v);
        Producto p = repositoryProducto.findByIdSucursalAndCodigo(dev.getIdSucursal(), dev.getCodigo());
        MovimientosExtras movi = new MovimientosExtras();
        MovimientosInv mov = new MovimientosInv();
        float cantidad;
        if (dev.getTipo() == 1) {
            cantidad = p.getCantidad();
            p.setCantidad(cantidad + dev.getCantidadDevuelta());
            v.setDevolucion(true);
            v.setDevocompleta(true);

        } else {
            float c = p.getCantidad();
            p.setCantidad(c + dev.getCantidadDevuelta());
            v.setDevolucion(true);
            cantidad = v.getCantidad() - dev.getCantidadDevuelta();
            v.setCantidad(cantidad);
            // PENSAR SI SE AGREGA EN LA VENTA LA ACTUALIZACIÓN DEL MONTO
            // YA QUE EN MOVIMIENTOS SE GUARDA EL MONTO DE VUELTO Y AHÍ SE HACE LA RESTA
            movi.setTipo("Devolucion");
            movi.setDescripcion("Devolución de: " + p.getDescripcion() + " en el ticket " + v.getCantidad());
            movi.setMonto(dev.getMontoDevuelto());
            movi.setFecha(new Date());
            movi.setIdUsuario(dev.getIdUsuario());
            movi.setIdSucursal(dev.getIdSucursal());
            movi.setPropietario(dev.getPropietario());
        }
        mov.setCantidad(dev.getCantidadDevuelta());
        mov.setDepartamento(p.getArea());
        mov.setNombre(p.getDescripcion());
        mov.setDescripcion("Ticket: " + v.getIdTicket());
        mov.setFecha(new Date());
        mov.setHabia(p.getCantidad() - dev.getCantidadDevuelta());
        mov.setHay(p.getCantidad());
        mov.setIdMovimientoInv(0);
        mov.setIdSucursal(p.getIdSucursal());
        mov.setIdUsuario(v.getIdUsuario());
        mov.setPropietario(p.getPropietario());
        mov.setTipoMovimiento("Devolución");
        serviceMo.guardar(mov);

        repositoryProducto.save(p);
        v = repository.save(v);
        lista.add(v);
        repositoryMovimientos.save(movi);

        ventasResponse.setMensaje("Devolucíón registrada");
        ventasResponse.setVentas(null);
        ventasResponse.setRealizado(true);

        return ventasResponse;
    }

    public String getObtenerSaldoCliente(Integer idCliente, Integer idSucursal) {
        String saldo = repository.obtenerSaldoCliente(idCliente, idSucursal);
        saldo = saldo == null ? "0" : saldo;
        return saldo;
    }

    public VentasResponse getObtenerTicketsCliente(Integer idCliente, Integer idSucursal) {
        List<Ventas> lista = repository.obtenerTicketsCliente(idCliente, idSucursal);
        ventasResponse.setMensaje(lista.isEmpty() == true ? "No se encontro ninguna compra" : "Ventas recuperadas");
        ventasResponse.setRealizado(true);
        ventasResponse.setVentas(lista);
        return ventasResponse;

    }

    public VentasResponse getVentasPorTicketClientes(Integer idSucursal, Integer idTicket, Integer idCliente,
            String fecha) {
        List<Ventas> lista = repository.getVentasPorTicketCliente(idSucursal, idTicket, idCliente, fecha);
        ventasResponse.setMensaje(lista.isEmpty() == true ? "No se encontro ninguna compra" : "Ventas recuperadas");
        ventasResponse.setRealizado(true);
        ventasResponse.setVentas(lista);
        return ventasResponse;
    }

    public boolean liquidarAdeudoVentas(Integer idCliente, Integer idSucursal) {
        repository.liquidarAdeudoVentas(idCliente, idSucursal);
        return true;
    }

    public CorteModelo realizarCorte(Integer idUsuario, Integer idSucursal, String fecha) {
        CorteModelo corte = new CorteModelo();
        IDatosCorte dineroCaja = repositoryMovimientos.encontrarDatos("efectivo_inicial", idUsuario + "", fecha,idSucursal + "");
        IDatosCorte entradas = repositoryMovimientos.encontrarDatos("entrada_efectivo", idUsuario + "", fecha,idSucursal + "");
        IDatosCorte salidas = repositoryMovimientos.encontrarDatos("salida_efectivo", idUsuario + "", fecha,idSucursal + "");
        IDatosCorte devolucionPorPartes = repositoryMovimientos.encontrarDatos("devolucion", idUsuario + "", fecha,idSucursal + "");
        IVentasCorte ventas = repository.ventasParaCorte(idUsuario, fecha, "Efectivo", idSucursal);
        IVentasCorte ventasCredito = repository.ventasParaCorte(idUsuario, fecha, "Credito", idSucursal);
        IDatosCorte  devoluciones = repository.obtenerDevolucionVentasParaCorte(fecha, idUsuario, idSucursal);
        IDatosCorte  abonos = repositoryCredito.obtenerAbonosParaCorte(idSucursal, fecha);

        String entrada=entradas==null?"0":entradas.getDato();
        String abono=abonos==null?"0":abonos.getDato();
        String salida=salidas==null?"0":salidas.getDato();
        String devolucion=devoluciones==null?"0":devoluciones.getDato();
        String dineroC=dineroCaja==null?"0":dineroCaja.getDato();
        String devolucionPorParte=devolucionPorPartes==null?"0":devolucionPorPartes.getDato();
        String ven=ventas.getVentas()==null?"0":ventas.getVentas();
        String cos=ventas.getCosto()==null?"0":ventas.getCosto();
        String venCredito=ventasCredito.getVentas()==null?"0":ventasCredito.getVentas();
        float totalDevoluciones= Float.parseFloat(devolucion)+ Float.parseFloat(devolucionPorParte);
        corte.setDineroCaja(dineroC);
        corte.setVentas(ven);
        corte.setCosto(cos);
        corte.setEntradas(entrada);
        corte.setSalidas(salida);
        corte.setDevoluciones(totalDevoluciones+"");
        corte.setAbonos(abono);
        corte.setVentasACredito(venCredito);
        corte.setEntradasLista( repositoryMovimientos.findByTipoAndFechaAndIdSucursalAndIdUsuario("entrada_efectivo", fecha, idUsuario, idSucursal));
        corte.setSalidasLista(repositoryMovimientos.findByTipoAndFechaAndIdSucursalAndIdUsuario("salida_efectivo", fecha, idUsuario, idSucursal));
       
        
        return corte;
    }

}

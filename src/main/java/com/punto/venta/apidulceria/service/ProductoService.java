package com.punto.venta.apidulceria.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.punto.venta.apidulceria.model.MovimientosInv;
import com.punto.venta.apidulceria.model.Producto;
import com.punto.venta.apidulceria.model.respuesta.ProductoResponse;
import com.punto.venta.apidulceria.repository.IProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

    @Autowired
    IProductoRepository repository;

    @Autowired
    MovimientosInvService serviceMo;

    MovimientosInv mov= new MovimientosInv();

    ProductoResponse productoResponse = new ProductoResponse();
    List<Producto> lista;


    public ProductoResponse guardar(Producto producto) {
        lista = new ArrayList<Producto>();
        Producto ar = repository.findByIdSucursalAndCodigo(producto.getIdSucursal(), producto.getCodigo());
        if (ar != null) {
            lista.add(ar);
            productoResponse.setProductos(lista);
            productoResponse
                    .setMensaje("Ya existe un producto registrado previamente con ese código: " + ar.getDescripcion());
            productoResponse.setRealizado(false);
            return productoResponse;
        }
        Producto pro= repository.save(producto);
        lista.add(pro);
        productoResponse.setProductos(lista);
        productoResponse.setMensaje("Datos registrados satisfactoriamente");
        productoResponse.setRealizado(true);
        
        if(pro!=null){
        mov.setCantidad(pro.getCantidad());
        mov.setDepartamento(pro.getArea());
        mov.setNombre(pro.getDescripcion());
        mov.setDescripcion("Nuevo producto");
        mov.setFecha(new Date());
        mov.setHabia(0);
        mov.setHay(pro.getCantidad());
        mov.setIdMovimientoInv(0);
        mov.setIdSucursal(pro.getIdSucursal());
        mov.setIdUsuario(pro.getIdUSuario());
        mov.setPropietario(pro.getPropietario());
        mov.setTipoMovimiento("Entrada");
        

        serviceMo.guardar(mov);
    }

        return productoResponse;
    }

    public ProductoResponse modificar(Producto producto) {
        lista = new ArrayList<Producto>();
        Optional<Producto> anterior= repository.findById(producto.getIdProducto());
       
        
        if(producto!=null){
            mov.setCantidad(Math.abs(producto.getCantidad()-anterior.get().getCantidad()));
            mov.setDepartamento(producto.getArea());
            mov.setNombre(producto.getDescripcion());
            mov.setDescripcion("Modificación inventario");
            mov.setFecha(new Date());
            mov.setHabia(anterior.get().getCantidad());
            mov.setHay(producto.getCantidad());
            mov.setIdMovimientoInv(0);
            mov.setIdSucursal(producto.getIdSucursal());
            mov.setIdUsuario(producto.getIdUSuario());
            mov.setPropietario(producto.getPropietario());
            String tipo="Entrada";
            if(producto.getCantidad()<anterior.get().getCantidad()){
               
                tipo="Salida";
            }
            mov.setTipoMovimiento(tipo);
    
            serviceMo.guardar(mov);
            Producto pro=repository.save(producto);
            lista.add(pro);
        }
        
        productoResponse.setProductos(lista);


        productoResponse.setMensaje("Datos modificados satisfactoriamente");
        productoResponse.setRealizado(true);
        return productoResponse;
    }

    public ProductoResponse eliminar(Integer idProducto) {
        lista = new ArrayList<Producto>();
        Optional<Producto> p = repository.findById(idProducto);
        if (p.isPresent()) {
            p.get().setEliminado(true);
            lista.add(repository.save(p.get()));
            productoResponse.setMensaje("Datos eliminados satisfactoriamente");
            productoResponse.setRealizado(true);
        } else {
            productoResponse.setMensaje("No se encontro el producto, vuelve a intentar o revisa tu información");
            productoResponse.setRealizado(false);
        }
        productoResponse.setProductos(lista);

        return productoResponse;
    }

    public ProductoResponse getAllProductosByIdSucursal(Integer idSucursal) {
        lista = new ArrayList<Producto>();
        productoResponse.setProductos(repository.findByIdSucursalAndEliminado(idSucursal, false));
        productoResponse.setMensaje(
                productoResponse.getProductos().isEmpty() == true ? "No hay productos" : "Productos encontrados");
        productoResponse.setRealizado(true);
        return productoResponse;
    }

    public ProductoResponse getAllProductosByIdSucursalAndCodigo(Integer idSucursal, String codigo) {
        lista = new ArrayList<Producto>();
        lista.add(repository.findByIdSucursalAndCodigo(idSucursal, codigo));
        productoResponse.setProductos(lista);
        productoResponse.setMensaje(productoResponse.getProductos().isEmpty() == true ? "No hay productos"
                : "Ya hay un producto, con ese codigo");
        productoResponse.setRealizado(true);
        return productoResponse;
    }

    public ProductoResponse getProductoById(Integer idProducto){
        lista = new ArrayList<Producto>();
       Optional<Producto> pro= repository.findById(idProducto);

       if(pro.isPresent()) lista.add(pro.get());

       productoResponse.setProductos(lista);
       productoResponse.setMensaje(lista.isEmpty()==true?"No se encontro el producto":"Producto encontrado");
       productoResponse.setRealizado(true);
       return productoResponse;
       
    }

    @Transactional
    public ProductoResponse transferirInventario(List<Producto> productos){
      
        try{
        Producto p1= productos.get(0);
        Optional<Producto> p1ant=repository.findById(p1.getIdProducto());
        Producto p2= productos.get(1);
        Optional<Producto> p2ant=repository.findById(p2.getIdProducto());
       MovimientosInv mov2= new MovimientosInv();
      
        if(p1!=null && p2!=null){

            mov.setCantidad(Math.abs(p1.getCantidad()-p1ant.get().getCantidad()));
            mov2.setCantidad(Math.abs(p2.getCantidad()-p2ant.get().getCantidad()));
            mov.setDepartamento(p1.getArea());
            mov2.setDepartamento(p2.getArea());
            mov.setNombre(p1.getDescripcion());
            mov2.setNombre(p2.getDescripcion());
            mov.setDescripcion("Transferencia");
            mov2.setDescripcion("Transferencia");
            mov.setFecha(new Date());
            mov2.setFecha(new Date());
            mov.setHabia(p1ant.get().getCantidad());
            mov2.setHabia(p2ant.get().getCantidad());
            mov.setHay(p1.getCantidad());
            mov2.setHay(p2.getCantidad());
            mov.setIdMovimientoInv(0);
            mov2.setIdMovimientoInv(0);
            mov.setIdSucursal(p1.getIdSucursal());
            mov2.setIdSucursal(p2.getIdSucursal());
            mov.setIdUsuario(p1.getIdUSuario());
            mov2.setIdUsuario(p2.getIdUSuario());
            mov.setPropietario(p1.getPropietario());
            mov2.setPropietario(p2.getPropietario());
            mov.setTipoMovimiento("Salida");
            mov2.setTipoMovimiento("Entrada");
    
            serviceMo.guardar(mov);
            serviceMo.guardar(mov2);
        
        }
        List<Producto> res= new ArrayList<Producto>();
        res.add( repository.save(p1));
        res.add( repository.save(p2));
        productoResponse.setProductos(res);
        productoResponse.setMensaje("Transferencia realizada exitosamente");
        productoResponse.setRealizado(true);


        return productoResponse;
    }catch(Exception e){
        productoResponse.setProductos(null);
        productoResponse.setMensaje("Ocurrio un error");
        productoResponse.setRealizado(false);
    }
        return productoResponse;
    }

   /* public ProductoResponse verUtilidad(Integer idSucursal){
        ProductoResponse pu= new ProductoResponse();
        List<Producto> lpu =repository.encontrarUtilidad(idSucursal);
        pu.setProductos(lpu);
        pu.setRealizado(true);
        pu.setMensaje("Realizado");
        return pu;
    }*/

    public ProductoResponse getAllProductosInventarioBajo(Integer idSucursal) {
        productoResponse.setProductos(repository.inventarioBajo(idSucursal));
        productoResponse.setMensaje(productoResponse.getProductos().isEmpty() == true ? "No hay inventario bajo"
                : "Si hay inventario bajo");
        productoResponse.setRealizado(true);
        return productoResponse;
    }

    public ProductoResponse getProductosCoincidenciaCaracter(String cadena){

        List<Producto> lista=repository.busquedaPorCaracter(cadena);
        productoResponse.setProductos(lista);
        productoResponse.setRealizado(true);
        productoResponse.setMensaje("Datos encontrados");
        return productoResponse;
    }

    public ProductoResponse productosPorSucursalParaTransferencia(Integer idSucursal, String propietario){

      List<Producto> lista= repository.productosPorSucursal(idSucursal, propietario);
      productoResponse.setProductos(lista);
      productoResponse.setRealizado(true);
      productoResponse.setMensaje("Realizado");
      return productoResponse;
    }

}

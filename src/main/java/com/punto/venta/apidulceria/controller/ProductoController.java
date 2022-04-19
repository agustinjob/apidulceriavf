package com.punto.venta.apidulceria.controller;

import com.punto.venta.apidulceria.model.Producto;
import com.punto.venta.apidulceria.model.respuesta.ProductoResponse;
import com.punto.venta.apidulceria.service.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class ProductoController {

    @Autowired
    ProductoService service;

    @PostMapping("/productos")
    public ResponseEntity<ProductoResponse> guardarProducto(@RequestBody Producto producto) {
        ProductoResponse are = service.guardar(producto);
        if (are.isRealizado() == false) {
            return new ResponseEntity<ProductoResponse>(are, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<ProductoResponse>(are, HttpStatus.OK);
        }

    }

    @GetMapping("/productos/{idSucursal}")
    public ResponseEntity<ProductoResponse> getAllProductos(@PathVariable Integer idSucursal) {
        return new ResponseEntity<ProductoResponse>(service.getAllProductosByIdSucursal(idSucursal), HttpStatus.OK);
    }

    @GetMapping("/productos-id/{idProducto}")
    public ResponseEntity<ProductoResponse> getByIdProducto(@PathVariable Integer idProducto) {
        return new ResponseEntity<ProductoResponse>(service.getProductoById(idProducto), HttpStatus.OK);
    }

    @GetMapping("/productos/{idSucursal}/{codigo}")
    public ResponseEntity<ProductoResponse> getAllProductosByCodigo(@PathVariable Integer idSucursal, @PathVariable String codigo) {
        return new ResponseEntity<ProductoResponse>(service.getAllProductosByIdSucursalAndCodigo(idSucursal, codigo), HttpStatus.OK);
    }

    @GetMapping("/productos-transferencia/{idSucursal}/{propietario}")
    public ResponseEntity<ProductoResponse> getAllProductosParaTransferencia(@PathVariable Integer idSucursal, @PathVariable String propietario) {
        return new ResponseEntity<ProductoResponse>(service.productosPorSucursalParaTransferencia(idSucursal, propietario), HttpStatus.OK);
    }

    @GetMapping("/productos-caracter/{cadena}")
    public ResponseEntity<ProductoResponse> getProductosPorCaracter(@PathVariable String cadena){
        return new ResponseEntity<ProductoResponse>(service.getProductosCoincidenciaCaracter(cadena),HttpStatus.OK);
    }

    /*@GetMapping("/productos-utilidad/{idSucursal}")
    public ResponseEntity<ProductoUtilidadResponse> verUtilidad(@PathVariable Integer idSucursal){
        return new ResponseEntity<ProductoUtilidadResponse>(service.verUtilidad(idSucursal), HttpStatus.OK);     
    }*/

    @GetMapping("/productos-inv-bajo/{idSucursal}")
    public ResponseEntity<ProductoResponse> verInventarioBajo(@PathVariable Integer idSucursal){
        return new ResponseEntity<ProductoResponse>(service.getAllProductosInventarioBajo(idSucursal), HttpStatus.OK);     
    }

    @PutMapping("/productos")
    public ResponseEntity<ProductoResponse> modificarProducto(@RequestBody Producto producto) {
        return new ResponseEntity<ProductoResponse>(service.modificar(producto), HttpStatus.OK);
    }

    @DeleteMapping("/productos/{idProducto}")
    public ResponseEntity<ProductoResponse> eliminarProducto(@PathVariable Integer idProducto) {
        
        return new ResponseEntity<ProductoResponse>(service.eliminar(idProducto), HttpStatus.OK);
    }

    @PostMapping("/productos-transferir")
    public ResponseEntity<ProductoResponse> productosTransferir(@RequestBody ProductoResponse productos){
        return new ResponseEntity<ProductoResponse>(service.transferirInventario(productos.getProductos()),HttpStatus.OK);
    }

}

package com.punto.venta.apidulceria.model.respuesta;

import java.util.List;

import com.punto.venta.apidulceria.model.Producto;

import lombok.Data;

@Data
public class ProductoResponse {
    
    private List<Producto> productos;
    private String mensaje;
    private boolean realizado;
}

package com.punto.venta.apidulceria.model.respuesta;

import java.util.List;

import com.punto.venta.apidulceria.model.ProductoUtilidad;

import lombok.Data;

@Data
public class ProductoUtilidadResponse {
    private List<ProductoUtilidad> productos;
    private String mensaje;
    private boolean realizado;
}

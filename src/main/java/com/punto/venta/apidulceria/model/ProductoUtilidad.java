package com.punto.venta.apidulceria.model;

import lombok.Data;

@Data
public class ProductoUtilidad {
    
    private String           codigo;
    private String      descripcion;
    private float         cantidad;
    private float     precio_costo;
    private float     precio_venta;
    private Double         utilidad;
    private Double preciocostototal;
    private Double precioventatotal;
    private Double    utilidadtotal;
}






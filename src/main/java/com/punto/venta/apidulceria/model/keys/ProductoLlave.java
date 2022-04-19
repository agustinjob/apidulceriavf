package com.punto.venta.apidulceria.model.keys;



import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
public class ProductoLlave{
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idProducto;
    private int idSucursal;
}

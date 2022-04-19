package com.punto.venta.apidulceria.model.keys;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class VentaLlave implements Serializable {

    private int idVenta;
    private int idSucursal;
    
}

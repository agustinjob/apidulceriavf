package com.punto.venta.apidulceria.model.keys;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class AreaLlave implements Serializable {
    
    private int idArea;
    private int idSucursal;
}

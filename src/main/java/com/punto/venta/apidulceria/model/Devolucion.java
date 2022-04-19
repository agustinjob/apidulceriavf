package com.punto.venta.apidulceria.model;

import lombok.Data;

@Data
public class Devolucion {
    private Integer idTicket;
    private String fecha;
    private String codigo;
    private float cantidadDevuelta;
    private Integer idSucursal;
    private String propietario;
    private float montoDevuelto;
    private Integer tipo; 
    private Integer idUsuario;
}

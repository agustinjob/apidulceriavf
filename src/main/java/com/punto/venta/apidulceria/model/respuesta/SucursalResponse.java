package com.punto.venta.apidulceria.model.respuesta;

import java.util.List;

import com.punto.venta.apidulceria.model.Sucursal;

import lombok.Data;

@Data
public class SucursalResponse{
    private List<Sucursal> sucursal;
    private String mensaje;
    private boolean realizado;
}
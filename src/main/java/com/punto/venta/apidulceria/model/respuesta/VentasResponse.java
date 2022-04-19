package com.punto.venta.apidulceria.model.respuesta;

import java.util.List;

import com.punto.venta.apidulceria.model.Ventas;

import lombok.Data;

@Data
public class VentasResponse {
    private List<Ventas> ventas;
    private String mensaje;
    private boolean realizado;

}

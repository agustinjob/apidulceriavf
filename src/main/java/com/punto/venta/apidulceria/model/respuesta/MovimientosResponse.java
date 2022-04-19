package com.punto.venta.apidulceria.model.respuesta;

import java.util.List;

import com.punto.venta.apidulceria.model.MovimientosExtras;

import lombok.Data;

@Data
public class MovimientosResponse {
    private List<MovimientosExtras> movimientos;
    private String mensaje;
    private boolean realizado;   
}

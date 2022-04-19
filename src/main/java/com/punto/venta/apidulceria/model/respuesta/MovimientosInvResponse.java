package com.punto.venta.apidulceria.model.respuesta;

import java.util.List;

import com.punto.venta.apidulceria.model.MovimientosInv;

import lombok.Data;

@Data
public class MovimientosInvResponse {
    List<MovimientosInv> lista;
    String mensaje;
    Boolean realizado;
}

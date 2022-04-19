package com.punto.venta.apidulceria.model.respuesta;

import java.util.List;

import com.punto.venta.apidulceria.model.Traspaso;

import lombok.Data;

@Data
public class TraspasoResponse {
    private List<Traspaso> traspasos;
    private String mensaje;
    private boolean realizado;
}

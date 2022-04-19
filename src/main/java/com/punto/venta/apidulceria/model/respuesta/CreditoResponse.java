package com.punto.venta.apidulceria.model.respuesta;

import java.util.List;

import com.punto.venta.apidulceria.model.Credito;

import lombok.Data;

@Data
public class CreditoResponse {
    private List<Credito> creditos;
    private String mensaje;
    private boolean realizado;
}

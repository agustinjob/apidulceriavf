package com.punto.venta.apidulceria.model.respuesta;

import java.util.List;

import com.punto.venta.apidulceria.model.Area;

import lombok.Data;

@Data
public class AreaResponse {
    private List<Area> areas;
    private String mensaje;
    private boolean realizado;
}

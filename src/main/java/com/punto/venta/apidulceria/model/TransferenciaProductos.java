package com.punto.venta.apidulceria.model;

import java.util.List;

import lombok.Data;

@Data
public class TransferenciaProductos {
    private int idSucursalLocal;
    private String sucursalLocal;
    private int idSucursalRecibe;
    private String sucursalRecibe;
    private List<Producto> lista;
    private String propietarioLocal;
    private String propietarioRecibe;
    private Integer idUsuario;
}

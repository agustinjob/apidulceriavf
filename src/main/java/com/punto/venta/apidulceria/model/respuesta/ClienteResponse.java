package com.punto.venta.apidulceria.model.respuesta;

import java.util.List;

import com.punto.venta.apidulceria.model.Cliente;

import lombok.Data;

@Data
public class ClienteResponse {
    private List<Cliente> clientes;
    private String mensaje;
    private boolean realizado;
}

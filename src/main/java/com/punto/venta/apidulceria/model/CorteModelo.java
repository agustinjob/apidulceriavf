package com.punto.venta.apidulceria.model;

import java.util.List;

import lombok.Data;
@Data
public class CorteModelo {
 
   private String dineroCaja;
   private String ventas;
   private String ventasACredito;
   private String costo;
   private String entradas;
   private String salidas;
   private String abonos;
   private String devoluciones;
   private List<MovimientosExtras> entradasLista;
   private List<MovimientosExtras>salidasLista;
   
}

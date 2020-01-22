package com.example.facturasapp;

public class Factura {

    String concepto="",fecha_factura="",tipo="",dueño="";
    double importe=0;


    public Factura(String concepto, String fecha_factura, double importe, String tipo, String dueño) {
        this.concepto = concepto;
        this.fecha_factura = fecha_factura;
        this.importe = importe;
        this.tipo = tipo;
        this.dueño = dueño;
    }

    
    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getFecha_factura() {
        return fecha_factura;
    }

    public void setFecha_factura(String fecha_factura) {
        this.fecha_factura = fecha_factura;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDueño(){
        return dueño;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double factura) {
        this.importe = factura;
    }

    public String visualizar_facturas(){
        String resultado="";
        resultado="\n-------------------\n" +
                  "Concepto: "+this.concepto+"\n"+
                  "Fecha: "+this.fecha_factura+"\n"+
                  "Importe: "+this.importe+"\n"+
                  "Tipo: "+this.tipo+"\n"+
                  "------------------";

        return resultado;
    }
}

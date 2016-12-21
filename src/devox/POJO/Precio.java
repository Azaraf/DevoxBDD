/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devox.POJO;

import java.sql.Date;

/**
 *
 * @author Ricardo
 */
public class Precio implements POJO{
    
    private Sucursal sucursal;
    private Producto producto;
    private float precio;
    private Date fechaPrecio;

    public Precio() {
    }

    public Precio(Sucursal sucursal, Producto producto, float precio, Date fechaPrecio) {
        this.sucursal = sucursal;
        this.producto = producto;
        this.precio = precio;
        this.fechaPrecio = fechaPrecio;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Date getFechaPrecio() {
        return fechaPrecio;
    }

    public void setFechaPrecio(Date fechaPrecio) {
        this.fechaPrecio = fechaPrecio;
    }
    
    
    
}

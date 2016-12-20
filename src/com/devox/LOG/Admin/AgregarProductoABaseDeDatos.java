/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.LOG.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.inspira.devox.database.DatabaseConnection;
import org.inspira.devox.services.productos.AccionesCodigoBarras;
import org.inspira.devox.services.productos.AccionesHistorialPrecio;
import org.inspira.devox.services.productos.AccionesLote;
import org.inspira.devox.services.productos.AccionesProducto;
import org.inspira.devox.services.relaciones.AccionesSucursalHasProducto;

/**
 *
 * @author azaraf
 */
public class AgregarProductoABaseDeDatos{
    private String codigoDeBarras;
    private String codigoInterno;
    private String descripcion;
    private String lote;
    private Float precio;
    private String idSucursal;
    private AccionesCodigoBarras acb;
    private AccionesProducto ap;
    private AccionesLote al;
    private AccionesHistorialPrecio ahp;
    private AccionesSucursalHasProducto ashl;
    private int i = 0; 

    public static int YA_EXISTE_CB = 0;
    public static int YA_EXISTE_PRODUCTO = 1;
    public static int YA_EXISTE_LOTE = 2;
    public static int YA_EXISTE_PRECIO = 3;

    public AgregarProductoABaseDeDatos() {
    }
    
    
    
    public int nuevoCompleto() { //Nuevo CB, producto, lote y precio
        DatabaseConnection db = new DatabaseConnection();
        try{
            Connection con = db.getConnection();
            PreparedStatement stm;
            
            //Inserta Producto
            stm = con.prepareStatement("INSERT INTO producto (idProducto, descripcion) VALUES (?, ?)");
            stm.setString(1, codigoInterno);
            stm.setString(2, descripcion);
            stm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        db.closeConnection();
        return 1;
    }
    
    public void nuevoSinPrecio() { //Nuevo CB, producto y lote
        acb = new AccionesCodigoBarras();
        acb.setCodigoBarras(codigoDeBarras);
        acb.alta();
        ap = new AccionesProducto();
        ap.setIdProducto(codigoInterno);
        ap.setDescripcion(descripcion);
        ap.alta();
        al = new AccionesLote();
        al.setIdProducto(codigoInterno);
        al.setIdLote(lote);
        al.alta();
        ashl = new AccionesSucursalHasProducto();
        ashl.setIdSucursal(idSucursal);
        ashl.setIdProducto(codigoInterno);
        ashl.alta();
    }
    
    public void nuevoCByProducto() { //Nuevo CB y producto
        acb = new AccionesCodigoBarras();
        acb.setCodigoBarras(codigoDeBarras);
        acb.alta();
        ap = new AccionesProducto();
        ap.setIdProducto(codigoInterno);
        ap.setDescripcion(descripcion);
        ap.alta();
    }
    
    public void nuevoCB() { // Nuevo CB
        acb = new AccionesCodigoBarras();
        acb.setCodigoBarras(codigoDeBarras);
        acb.alta();
    }
    
    public void nuevoProducto(){
        ap = new AccionesProducto();
        ap.setIdProducto(codigoInterno);
        ap.setDescripcion(descripcion);
        ap.alta();
    }
    
    public void nuevoLote(){
        al = new AccionesLote();
        al.setIdProducto(codigoInterno);
        al.setIdLote(lote);
        al.alta();
        ashl = new AccionesSucursalHasProducto();
        ashl.setIdSucursal(idSucursal);
        ashl.setIdProducto(codigoInterno);
        ashl.alta();
    }

    public void nuevoProductoConLoteyPrecio(){ //Nuevo producto, lote y precio
        ap = new AccionesProducto();
        ap.setIdProducto(codigoInterno);
        ap.setDescripcion(descripcion);
        ap.alta();
        al = new AccionesLote();
        al.setIdProducto(codigoInterno);
        al.setIdLote(lote);
        al.alta();
        ashl = new AccionesSucursalHasProducto();
        ashl.setIdSucursal(idSucursal);
        ashl.setIdProducto(codigoInterno);
        ashl.alta();
        ahp = new AccionesHistorialPrecio();
        ahp.setIdProducto(codigoInterno);
        ahp.setIdSucursal(idSucursal);
        ahp.setPrecio(precio);
        ahp.alta();
    }
    
    public void nuevoLoteyPrecio(){ //Nuevo lote y precio
        al = new AccionesLote();
        al.setIdProducto(codigoInterno);
        al.setIdLote(lote);
        al.alta();
        ashl = new AccionesSucursalHasProducto();
        ashl.setIdSucursal(idSucursal);
        ashl.setIdProducto(codigoInterno);
        ashl.alta();
        ahp = new AccionesHistorialPrecio();
        ahp.setIdProducto(codigoInterno);
        ahp.setIdSucursal(idSucursal);
        ahp.setPrecio(precio);
        ahp.alta();
    }
    
    public void nuevoPrecio(){ // nuevo precio
        ahp = new AccionesHistorialPrecio();
        ahp.setIdProducto(codigoInterno);
        ahp.setIdSucursal(idSucursal);
        ahp.setPrecio(precio);
        ahp.alta();
    }
    
    public void nuevoProductoyLote(){
        ap = new AccionesProducto();
        ap.setIdProducto(codigoInterno);
        ap.setDescripcion(descripcion);
        ap.alta();
        al = new AccionesLote();
        al.setIdProducto(codigoInterno);
        al.setIdLote(lote);
        al.alta();
        ashl = new AccionesSucursalHasProducto();
        ashl.setIdSucursal(idSucursal);
        ashl.setIdProducto(codigoInterno);
        ashl.alta();
    }

    public void setCodigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }
    
    
}

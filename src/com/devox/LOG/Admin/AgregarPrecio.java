/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.LOG.Admin;

import com.devox.LOG.interfaces.Agregar;
import org.inspira.devox.services.productos.AccionesHistorialPrecio;

/**
 *
 * @author azaraf
 */
public class AgregarPrecio{
    private String idSucursal;
    private Float precio;
    private String idProducto;

    public AgregarPrecio(String idLote, String idSucursal, Float precio) {
        this.idProducto = idLote;
        this.idSucursal = idSucursal;
        this.precio = precio;
        PorLote p = new PorLote();
    }

    public AgregarPrecio(String idProducto,  Float precio, String idSucursal) {
        this.idSucursal = idSucursal;
        this.precio = precio;
        this.idProducto = idProducto;
        PorProducto p = new PorProducto();
    }
    

     
    
    private final class PorProducto implements Agregar{

        private PorProducto() {
            agregar();
        }

        
        @Override
        public void agregar() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            
        }
        
    }
    
    private final class PorLote implements Agregar{

        public PorLote() {
            agregar();
        }
        
        @Override
        public void agregar() {
            AccionesHistorialPrecio acc = new AccionesHistorialPrecio();
            acc.setIdProducto(idProducto);
            acc.setIdSucursal(idSucursal);
            acc.setPrecio(precio);
            acc.alta();
        }
        
    }
}

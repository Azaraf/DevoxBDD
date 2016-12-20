/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.LOG.interfaces;

/**
 *
 * @author azaraf
 */
public interface ProductReturned {
    public void agregarProducto(String codigoDeBarras, String cantidad);
    public Object[] obtenerFila(int row);
}

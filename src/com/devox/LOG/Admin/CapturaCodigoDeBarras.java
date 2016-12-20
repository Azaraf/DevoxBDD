/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.LOG.Admin;

import javax.swing.JOptionPane;
import org.inspira.devox.services.productos.AccionesHistorialPrecio;
import org.inspira.devox.services.productos.AccionesProducto;

/**
 *
 * @author azaraf
 */
public class CapturaCodigoDeBarras {

    public static final int NO_EXISTE_CB = -1;
    public static final int NO_TIENE_PROD = -2;
    public static final int NO_TIENE_LOTE = -3;
    public static final int NO_TIENE_PRECIO = -4;
    public static final int NO_QUIERE_AGREGAR = -5;
    public static final int SI_QUIERE_AGREGAR = 0;
    
    private CapturaCodigoDeBarras() {
    }
    

    public static int insertarCodigoDeBarras(String codigo){
        int i = JOptionPane.showConfirmDialog(
                null, 
                "Este código de barras al parecer no está\n"
                        + "en la base de datos.\n"
                        + "Agregue uno nuevo.",
                "Código de Barras desconocido",
                JOptionPane.OK_CANCEL_OPTION);
        if (i == JOptionPane.OK_OPTION){
            return CapturaCodigoDeBarras.SI_QUIERE_AGREGAR;
        }
        return CapturaCodigoDeBarras.NO_QUIERE_AGREGAR;
    }
    
    public static void insertDescripcion(String CB, String ID){
        String respuesta = (String)JOptionPane.showInputDialog(
                null, 
                "Escriba descripción del nuevo producto", 
                "Agregar descripción", 
                JOptionPane.PLAIN_MESSAGE,
                null, 
                null,
                null
        );
        if (respuesta != null){
            AccionesProducto ap = new AccionesProducto();
            ap.setDescripcion(respuesta);
            ap.setIdProducto(ID);
            ap.alta();
        }
    }
    
    public static String insertarIdProducto(String CB){
        String respuesta = (String)JOptionPane.showInputDialog(
                null, 
                "Escriba el código del nuevo producto", 
                "Agregar Producto", 
                JOptionPane.PLAIN_MESSAGE,
                null, 
                null,
                null
        );
        return respuesta;
    }
    

    public static String selectProducto(String[] args){
        String p = (String)JOptionPane.showInputDialog(
                null,
                "Este código de barras tiene\n"
                        + "más de un codigo producto.\n"
                        + "Seleccione uno.", 
                "Seleccionar producto",
                JOptionPane.PLAIN_MESSAGE,
                null,
                args,
                args[0]
        );
        return p;
    }
    
    public static int insertLote(){
        int i = JOptionPane.showConfirmDialog(
                null, 
                "Este código de barras al parecer no tiene\n"
                        + "en la base de datos ningún lote relacionado.\n"
                        + "Agregue uno nuevo.",
                "Lote desconocido",
                JOptionPane.OK_CANCEL_OPTION);
        if (i == JOptionPane.OK_OPTION){
            return CapturaCodigoDeBarras.SI_QUIERE_AGREGAR;
        }
        return CapturaCodigoDeBarras.NO_QUIERE_AGREGAR;
    }
    
    public static int insertPrecio(){
        int i = JOptionPane.showConfirmDialog(
                null, 
                "Este código de barras al parecer no tiene\n"
                        + "en la base de datos ningún lote relacionado.\n"
                        + "Agregue uno nuevo.",
                "Lote desconocido",
                JOptionPane.OK_CANCEL_OPTION);
        if (i == JOptionPane.OK_OPTION){
            return CapturaCodigoDeBarras.SI_QUIERE_AGREGAR;
        }
        return CapturaCodigoDeBarras.NO_QUIERE_AGREGAR;
    }
    
    public static Float getNewPrecio(String lote, String sucursal){
        String p = (String)JOptionPane.showInputDialog(
                null,
                "Este producto no tiene ningún precio.\nAgregue un nuevo precio:",
                "Agregar precio",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "0.00"
        );
        Float r = Float.parseFloat(p);
        if (r != 0){
            AccionesHistorialPrecio ahp = new AccionesHistorialPrecio();
            ahp.setPrecio(r);
            ahp.setIdProducto(lote);
            ahp.setIdSucursal(sucursal);
            ahp.alta();
            return r;
        }
        return (float) 0;
    }
    
    public static void mostrarAlerta(String alerta){
       JOptionPane.showMessageDialog(null, alerta);
    }
    
    public static int insertarCodigoInterno(){
        int i = JOptionPane.showConfirmDialog(
                null, 
                "Este producto no corresponde al\n"
                        + "cliente de la devolución en curso\n"
                        + "Agregue uno nuevo",
                "Producto desconocido",
                JOptionPane.OK_CANCEL_OPTION);
        if (i == JOptionPane.OK_OPTION){
            return CapturaCodigoDeBarras.SI_QUIERE_AGREGAR;
        }
        return CapturaCodigoDeBarras.NO_QUIERE_AGREGAR;
    }
}

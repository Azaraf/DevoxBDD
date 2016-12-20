/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.LOG.Entidades;

import com.devox.GUI.panels.CuadriculaLista;
import java.util.ArrayList;
import java.util.List;
import org.inspira.devox.services.devolucion.AccionesDevolucion;
import org.inspira.devox.shared.Devolucion;
import org.inspira.devox.shared.Shareable;

/**
 *
 * @author azaraf
 */
public class Devoluciones {
    private static AccionesDevolucion accionesDevolucion = new AccionesDevolucion();
    private static List<CuadriculaLista> lista = new ArrayList<>();
    
    static{
        for (Shareable s1 : Devoluciones.accionesDevolucion.getEntityInstances()) {
            Devolucion dev = (Devolucion) s1;
            CuadriculaLista cd = new CuadriculaLista(dev.getFolio());
            Devoluciones.lista.add(cd);
        }
    }
    
    public static CuadriculaLista getDevolucion(int folio){
        return null;
    }
    
    public static List<CuadriculaLista> getLastDevoluciones(int number){
        return null;
    }
    
    public static List<CuadriculaLista> getDevolucionesEnEstado(String estado){
        return null;
    }
    
    public static List<CuadriculaLista> getDevolucionesDay(){
        return null;
    }
    
    public static List<CuadriculaLista> getDevolucionesWeek(){
        return null;
    }
    
    public static List<CuadriculaLista> getDevolucionesMonth(){
        return null;
    }
    
    public static void addDevolucion(CuadriculaLista cuadricula){
        
    }
}

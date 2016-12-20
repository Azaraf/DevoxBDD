/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.LOG.cuadricula;

import com.devox.GUI.panels.CuadriculaTarima;

/**
 *
 * @author azaraf
 */
public class CuadTarimaObj implements Comparable<CuadTarimaObj>{

    private int index;
    private String nombre;
    private boolean created;
    private CuadriculaTarima objeto;

    public CuadTarimaObj(CuadriculaTarima objeto) {
        this.nombre = objeto.getNameTarima();
        this.index = objeto.index;
        this.created = true;
        this.objeto = objeto;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public CuadriculaTarima getObjeto() {
        if(created){
            return objeto;
        }
        else{
            objeto = new CuadriculaTarima();
            created = true;
            return objeto;
        }
    }

    public void setObjeto(CuadriculaTarima objeto) {
        this.objeto = objeto;
        if(objeto != null) this.created = true;
    }
    @Override
    public int compareTo(CuadTarimaObj o) {
        int compareFolio = ((CuadTarimaObj) o).index;
        return compareFolio - this.index;
    }
    
}

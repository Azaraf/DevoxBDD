/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.LOG.cuadricula;

import com.devox.GUI.panels.CuadriculaLista;

/**
 *
 * @author azaraf
 */
public class Cuadricula implements Comparable<Cuadricula>{
    private int index;
    private boolean created;
    private CuadriculaLista objeto;

    public Cuadricula() {
        this.created = false;
        this.objeto = null;
    }

    public Cuadricula(int index) {
        this.index = index;
        this.created = false;
        this.objeto = null;
    }

    public Cuadricula(CuadriculaLista objeto) {
        this.index = objeto.getFolioSimple();
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
    
    public CuadriculaLista getObjeto() {
        if(created){
            return objeto;
        }
        else{
            objeto = new CuadriculaLista(index);
            created = true;
            return objeto;
        }
    }

    public void setObjeto(CuadriculaLista objeto) {
        this.objeto = objeto;
        if(objeto != null) this.created = true;
    }
    
    @Override
    public int compareTo(Cuadricula o) {
        int compareFolio = ((Cuadricula) o).index;
        return compareFolio - this.index;
    }
    
}

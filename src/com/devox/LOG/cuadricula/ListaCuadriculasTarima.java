/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.LOG.cuadricula;

import com.devox.GUI.panels.CuadriculaTarima;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.inspira.devox.services.organico.AccionesTarima;
import org.inspira.devox.shared.Shareable;
import org.inspira.devox.shared.Tarima;

/**
 *
 * @author azaraf
 */
public class ListaCuadriculasTarima {
    public static List<CuadTarimaObj> listaCuadriculas;
    private static final AccionesTarima accionesTarima;
    private static int length;
    
    static{
        listaCuadriculas = new ArrayList<>();
        accionesTarima = new AccionesTarima();
        accionesTarima.loadDatabaseInstances();
        length = accionesTarima.getEntityInstances().length;
    }
    
    public static void getLastNTarimas(List<CuadriculaTarima> listaTarimas, int n){
        for (int i = (length - 1); i > (length - n - 1); i--) { // desde la última devolucion hasta la devolucion (ultima - n)
            Shareable s1 = accionesTarima.getEntityInstances()[i];
            Tarima tam = (Tarima) s1;
            int j = buscarEnLista(tam.getNombre()); //busca el index en la lista de cuadriculas global
            if (!isTarima(listaTarimas, tam.getNombre())) { //busca si este folio está en la lista que llegó
                if (j == -1) {
                    //el folio no está creado y se agrega a la lista de cuadrículas global
                    listaCuadriculas.add(new CuadTarimaObj(new CuadriculaTarima(
                            tam.getNombre(),
                            tam.getFechaCreacion(),
                            tam.getFechaCierre(),
                            tam.getIdTarima()
                        )));
                    j = listaCuadriculas.size() - 1;
                }
                listaTarimas.add(listaCuadriculas.get(j).getObjeto());
            }
        }
        Collections.sort(listaTarimas);
    }
    
    public static int buscarEnLista(String nombre) {
        int index = -1;
        for (int i = listaCuadriculas.size() -1; i >=0; i--) {
            if (listaCuadriculas.get(i).getNombre().equals(nombre) && listaCuadriculas.get(i).isCreated()) {
                index = i;
                break;
            }
        }
        return index;
    }
    
    private static boolean isTarima(List<CuadriculaTarima> lista, String nombre) {
        List<String> tarimas = new ArrayList<>();
        lista.stream().forEach((c) -> {
            tarimas.add(c.getNameTarima());
        });
        return tarimas.contains(nombre);
    }
    
    public static void actualizarTotal() {
        accionesTarima.loadDatabaseInstances();
        length = accionesTarima.getEntityInstances().length;
    }
}

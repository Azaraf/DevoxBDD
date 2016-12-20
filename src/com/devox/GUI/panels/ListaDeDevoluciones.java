/*
 * Copyright (c) 2016, azaraf
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.devox.GUI.panels;

import java.util.ArrayList;
import java.util.List;
import org.inspira.devox.services.MakeDevolucion;
import org.inspira.devox.services.queries.ReportePorEstadoDeFolio;

/**
 *
 * @author azaraf
 */
public class ListaDeDevoluciones {

    private static final String S = "Segregacion";
    private static final String D = "Dictamen";
    private static final String C = "Captura DLX";
    private static final String E = "Entregado";
    private static int total;
    private static boolean created = false;
    private static List<CuadriculaLista> listaL;
    private static List<Integer> segregados;
    private static List<Integer> dictaminados;
    private static List<Integer> capturados;
    private static List<Integer> entregados;
    private static ReportePorEstadoDeFolio rpdf;

    static {
        rpdf = new ReportePorEstadoDeFolio();
        listaL = new ArrayList<>();
        total = new MakeDevolucion().getLastFolio();
        
    }

    public static List<CuadriculaLista> getListaL() {
        return listaL;
    }

    public static void addNewCuadriculaL(CuadriculaLista cd) {
        listaL.add(cd);
    }

    public static int getSize() {
        return listaL.size();
    }

    public static int getCantidadSegregados() {
        return rpdf.obtenerCantidadDeFoliosEnEstado(S);
    }

    public static int getCantidadDictaminados() {
        return rpdf.obtenerCantidadDeFoliosEnEstado(D);
    }

    public static int getCantidadCapturados() {
        return rpdf.obtenerCantidadDeFoliosEnEstado(C);
    }

    public static int getCantidadEntregados() {
        return rpdf.obtenerCantidadDeFoliosEnEstado(E);
    }

    public static List<Integer> getSegregados() {
        List<Integer> list = new ArrayList<>();
        
        Object[][] obj = rpdf.obtenerReportePorEstado(S);
        for (Object[] o : obj){
            list.add((Integer)o[0]);
        }
        return list;
    }

    public static List<Integer> getDictaminados() {
        List<Integer> list = new ArrayList<>();
        
        Object[][] obj = rpdf.obtenerReportePorEstado(D);
        for (Object[] o : obj){
            list.add((Integer)o[0]);
        }
        return list;
    }

    public static List<Integer> getCapturados() {
        List<Integer> list = new ArrayList<>();
        
        Object[][] obj = rpdf.obtenerReportePorEstado(C);
        for (Object[] o : obj){
            list.add((Integer)o[0]);
        }
        return list;
    }

    public static List<Integer> getEntregados() {
        List<Integer> list = new ArrayList<>();
        
        Object[][] obj = rpdf.obtenerReportePorEstado(E);
        for (Object[] o : obj){
            list.add((Integer)o[0]);
        }
        return list;
    }

    public static void crearFolios(int cantidad) {
        if (created) {
            int tam = listaL.size();
            if(cantidad > tam && tam-cantidad >=0){
                for(int i = total-tam; i > total-cantidad; i--){
                    CuadriculaLista cd = new CuadriculaLista(i);
                    listaL.add(cd);
                }
            }
            else if(cantidad > total){
                for(int i = total-tam; i > 0; i--){
                    CuadriculaLista cd = new CuadriculaLista(i);
                    listaL.add(cd);
                }
            }
        } else {
            for (int i = total; i > total - cantidad; i--) {
                System.out.println("haciendo la cuadrídula: " + i);
                CuadriculaLista cd = new CuadriculaLista(i);
                listaL.add(cd);
            }
        }
    }
    
    public static void agregarNuevaDevolucion(int folio){
        CuadriculaLista cd =  new CuadriculaLista(folio);
        listaL.add(0, cd);
        total++;
    }
    
    public static List<CuadriculaLista> obtenerLista(int desdeFolio, int hastaFolio){
        int fromIndex = total-hastaFolio;
        int toIndex = total-desdeFolio;
        if(toIndex > (listaL.size()-1)){
            
        }
        return listaL.subList(fromIndex, toIndex);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.LOG.cuadricula;

import com.devox.GUI.panels.CuadriculaLista;
import com.devox.GUI.panels.ListaDeDevoluciones;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.inspira.devox.database.DatabaseConnection;
import org.inspira.devox.logger.Log;
import org.inspira.devox.services.devolucion.AccionesDevolucion;
import org.inspira.devox.shared.Devolucion;
import org.inspira.devox.shared.Shareable;

/**
 *
 * @author azaraf
 */
public class ListaCuadriculas {

    public static List<Cuadricula> listaCuadriculas;
    private static final AccionesDevolucion accionesDevolucion;
    private static int length;

    static {
        listaCuadriculas = new ArrayList<>();
        accionesDevolucion = new AccionesDevolucion();
        accionesDevolucion.loadDatabaseInstances();
        length = accionesDevolucion.getEntityInstances().length;
    }

    public static void getLastNDevoluciones(List<CuadriculaLista> listaDevoluciones, int n) {
        //Llenar con las últimas N devoluciones si lo requiere.
        for (int i = (length - 1); i > (length - n - 1); i--) { // desde la última devolucion hasta la devolucion (ultima - n)
            Shareable s1 = accionesDevolucion.getEntityInstances()[i];
            Devolucion dev = (Devolucion) s1;
            int j = buscarEnLista(dev.getFolio()); //busca el index en la lista de cuadriculas global
            if (!isFolio(listaDevoluciones, dev.getFolio())) { //busca si este folio está en la lista que llegó
                if (j == -1) {
                    //el folio no está creado y se agrega a la lista de cuadrículas global
                    System.out.println("creada la cuadricula " + dev.getFolio());
                    listaCuadriculas.add(new Cuadricula(new CuadriculaLista(dev.getFolio())));
                    j = listaCuadriculas.size() - 1;
                    Log.print("debug: lista size: " + listaCuadriculas.size() + " linea " + "53 " + "clase com.devox.LOG.cuadricula.ListaCuadriculas");
                }
                listaDevoluciones.add(listaCuadriculas.get(j).getObjeto());
            }
        }
        Collections.sort(listaDevoluciones);
    }

    public static void actualizarTotal() {
        accionesDevolucion.loadDatabaseInstances();
        length = accionesDevolucion.getEntityInstances().length;
    }

    public static int buscarEnLista(int folio) {
        int index = -1;
        for (int i = 0; i < listaCuadriculas.size(); i++) {
            if (listaCuadriculas.get(i).getIndex() == folio && listaCuadriculas.get(i).isCreated()) {
                index = i;
                break;
            }
        }
        return index;
    }

    private static boolean isFolio(List<CuadriculaLista> lista, int folio) {
        List<Integer> folios = new ArrayList<>();
        lista.stream().forEach((c) -> {
            folios.add(c.getFolioSimple());
        });
        return folios.contains(folio);
    }

    public static CuadriculaLista getCuadricula(int folio) {
        int j = buscarEnLista(folio); //busca el index en la lista de cuadriculas global
        if (j == -1) {
            //el folio no está creado y se agrega a la lista de cuadrículas global
            System.out.println("creada la cuadricula " + folio);
            listaCuadriculas.add(new Cuadricula(new CuadriculaLista(folio)));
            Log.print("debug: lista size: " + listaCuadriculas.size() + " linea " + "91 " + "clase com.devox.LOG.cuadricula.ListaCuadriculas");
            Collections.sort(listaCuadriculas);
            j = buscarEnLista(folio);
        }
        return listaCuadriculas.get(j).getObjeto();
    }

    public static void getLastDevolucionesPorEstado(List<CuadriculaLista> listaDevoluciones, int limite, String estado) {
        //Llenar con las últimas N devoluciones si lo requiere.
        List<Integer> folios = new ArrayList<>();
        switch (estado) {
            case "segregación":
                folios = ListaDeDevoluciones.getSegregados();
                break;
            case "dictamen":
                folios = ListaDeDevoluciones.getDictaminados();
                break;
            case "captura DLX":
                folios = ListaDeDevoluciones.getCapturados();
                break;
            case "entregado":
                folios = ListaDeDevoluciones.getEntregados();
                break;
            default:
                Log.print("Error porque no existe la lista de " + estado);
        }
        int tam = folios.size();
        if (tam != 0) {
            int stop;
            if(tam <= limite) stop = 0;
            else stop = tam - limite - 1;
            for (int i = (tam - 1); i > stop; i--) { // desde la última devolucion hasta la devolucion (ultima - n)
                int j = buscarEnLista(folios.get(i)); //busca el index en la lista de cuadriculas global
                if (!isFolio(listaDevoluciones, folios.get(i))) { //busca si este folio está en la lista que llegó
                    if (j == -1) {
                        //el folio no está creado y se agrega a la lista de cuadrículas global
                        System.out.println("creada la cuadricula " + folios.get(i));
                        listaCuadriculas.add(new Cuadricula(new CuadriculaLista(folios.get(i))));
                        Log.print("debug: lista size: " + listaCuadriculas.size() + " linea " + "129 " + "clase com.devox.LOG.cuadricula.ListaCuadriculas");
                        j = listaCuadriculas.size() - 1;
                    }
                    listaDevoluciones.add(listaCuadriculas.get(j).getObjeto());
                }
            }
            Collections.sort(listaDevoluciones);
        } else {
            listaDevoluciones = new ArrayList<>();
        }
    }

    public static boolean search(int folio) {
        DatabaseConnection db = new DatabaseConnection();
        CallableStatement stmnt;
        int f = -1;
        try {
            stmnt = db.getConnection().prepareCall("{call buscar_folio(?,?)}");
            stmnt.setInt(1, folio);
            stmnt.registerOutParameter(2, Types.INTEGER);
            stmnt.executeUpdate();
            f = stmnt.getInt(2);
        } catch (SQLException localSQLException) {
            Log.print(localSQLException);
        } catch (NullPointerException e) {
            Log.print(e);
        }
        return f != -1;
    }

    public static void getLastDevolucionesPorFechas(List<CuadriculaLista> lista, Date desde, Date hasta, int limit) {
        DatabaseConnection db = new DatabaseConnection();
        CallableStatement stmnt;
        List<Integer> folios = new ArrayList<>();
        try {
            stmnt = db.getConnection().prepareCall("{call ultimos_n_por_fecha(?,?,?)}");
            stmnt.setInt(1, limit);
            stmnt.setDate(2, new java.sql.Date(desde.getTime()));
            stmnt.setDate(3, new java.sql.Date(hasta.getTime()));
            stmnt.executeUpdate();
            ResultSet rs = stmnt.getResultSet();
            while (rs.next()) {
                folios.add(rs.getInt("folio"));
            }
        } catch (SQLException localSQLException) {
            Log.print(localSQLException);
        } catch (NullPointerException e) {
            Log.print(e);
        }

        for (int f : folios) { // desde la última devolucion hasta la devolucion (ultima - n)
            int j = buscarEnLista(f); //busca el index en la lista de cuadriculas global
            if (!isFolio(lista, f)) { //busca si este folio está en la lista que llegó
                if (j == -1) {
                    //el folio no está creado y se agrega a la lista de cuadrículas global
                    System.out.println("creada la cuadricula " + f);
                    listaCuadriculas.add(new Cuadricula(new CuadriculaLista(f)));
                    j = listaCuadriculas.size() - 1;
                    Log.print("debug: lista size: " + listaCuadriculas.size() + " linea " + "187 " + "clase com.devox.LOG.cuadricula.ListaCuadriculas");
                }
                lista.add(listaCuadriculas.get(j).getObjeto());
            }
        }
        Collections.sort(lista);
        Collections.reverse(lista);
    }

}

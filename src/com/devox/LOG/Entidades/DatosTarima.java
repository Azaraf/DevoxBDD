/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.LOG.Entidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.inspira.devox.logger.Log;
import org.inspira.devox.services.organico.AccionesTarima;
import org.inspira.devox.shared.Shareable;
import org.inspira.devox.shared.Tarima;

/**
 *
 * @author azaraf
 */
public class DatosTarima {

    private String nombreTarima;
    private String division;
    private String almacen;
    private Date fecha_Apertura;
    private Date fecha_Cierra = null;
    private int destruccionFiscal;
    private List<String[]> listaProductos;

    public DatosTarima() {
        //prueba
        this.nombreTarima = "";
        this.division = "";
        this.almacen = "";
        this.fecha_Apertura = new Date(2016, 0, 13);
        this.fecha_Cierra = new Date(2016, 0, 16);
        this.destruccionFiscal = 1;
        this.listaProductos = new ArrayList<>();
        this.listaProductos.add(new String[]{"", "", "", "0"});

    }

    public DatosTarima(String nombre, Object[][] layout) {
        this.nombreTarima = nombre;
        this.listaProductos = new ArrayList<>();
        if (layout.length != 0) {
            this.division = layout[0][0].toString();
            this.destruccionFiscal = (Integer) layout[0][4];
            this.almacen = layout[0][1].toString();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                this.fecha_Apertura = sdf.parse(layout[0][2].toString());
                if (layout[0][3] != null) {
                    SimpleDateFormat sdfnew = new SimpleDateFormat("yyyy-MM-dd");
                    this.fecha_Cierra = sdfnew.parse(layout[0][3].toString());
                } else {
                    this.fecha_Cierra = null;
                }
            } catch (ParseException ex) {
                Log.print(ex);
            }
            for (Object[] obj1 : layout) {
                String[] p = new String[]{
                    String.format("%06d", (Integer) obj1[5]),
                    obj1[6].toString(),
                    obj1[7].toString(),
                    obj1[8].toString()
                };
                listaProductos.add(p);
            }
        } else {
            AccionesTarima axT = new AccionesTarima();
            axT.loadDatabaseInstances();
            for (Shareable s : axT.getEntityInstances()) {
                if (((Tarima) s).getNombre().equals(nombre)) {
                    this.division = "---";
                    this.almacen = ((Tarima)s).getAlmacen_cv();
                    this.fecha_Apertura = ((Tarima)s).getFechaCreacion();
                    this.fecha_Cierra = ((Tarima)s).getFechaCierre();
                    this.destruccionFiscal = ((Tarima)s).getIdDestruccionFiscal();
                    this.listaProductos = new ArrayList<>();
                    this.listaProductos.add(new String[]{"Vacío", "Vacío", "Vacío", "0"});
                }
            }

        }
    }

    public String getNombreTarima() {
        return nombreTarima;
    }

    public void setNombreTarima(String nombreTarima) {
        this.nombreTarima = nombreTarima;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public Date getFecha_Apertura() {
        return fecha_Apertura;
    }

    public void setFecha_Apertura(Date fecha_Apertura) {
        this.fecha_Apertura = fecha_Apertura;
    }

    public Date getFecha_Cierra() {
        return fecha_Cierra;
    }

    public void setFecha_Cierra(Date fecha_Cierra) {
        this.fecha_Cierra = fecha_Cierra;
    }

    public int getDestruccionFiscal() {
        return destruccionFiscal;
    }

    public void setDestruccionFiscal(int destruccionFiscal) {
        this.destruccionFiscal = destruccionFiscal;
    }

    public List<String[]> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<String[]> listaProductos) {
        this.listaProductos = listaProductos;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.GUI.tables;

import java.util.Date;

/**
 *
 * @author azaraf
 */
interface DevoxTable {

    public static final String[] columnsApto = new String[]{
        "Codigo de barras",
        "Código interno",
        "Descripción del producto",
        "Lote",
        "Precio",
        "Cantidad",
        "Monto"
    };

    public static final String[] columnsDestruccion = new String[]{
        "Codigo de barras",
        "Código interno",
        "Descripción del producto",
        "Lote",
        "Precio",
        "Cantidad",
        "Monto",
        "Tarima"
    };

    public static final String[] columnsViewPrecios = new String[]{
        "Código de barras",
        "Código de producto",
        "Descripción",
        "Código de cliente",
        "Cliente",
        "División",
        "Precio",
        "Fecha"
    };

    public static final String[] columnsViewPreciosPorFolio = new String[]{
        "Folio DHL",
        "Código de cliente",
        "Cliente",
        "Código de producto",
        "Descripción",
        "Precio",
        "División",
        "Fecha recepción precio",
        "Fecha de captura"
    };

    public static final String[] columnsReporteTarimas = new String[]{
        "Folio",
        "Producto",
        "Codigo",
        "Cantidad"
    };

    public static final String[] columnsReportePreciosPorFolio = new String[]{
        "Producto",
        "Descripción",
        "Fecha",
        "Precio"
    };

    public static final String[] columnasEnBusqueda = new String[]{
        "Folio DHL",
        "Cliente",
        "Folio Abbott",
        "Folio Cliente",
        "Factura",
        "División",
        "Fecha de recepción",
        "Monto total"
    };

    public static final String[] columnasUsers = new String[]{
        "Usuario",
        "Contraseña",
        "Permisos"
    };

    public static final Class[] tiposApto = new Class[]{
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.Float.class,
        java.lang.Integer.class,
        java.lang.Float.class
    };

    public static final Class[] tiposDestruccion = new Class[]{
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.Float.class,
        java.lang.Integer.class,
        java.lang.Float.class,
        java.lang.String.class
    };

    public static final Class[] tiposViewPrecios = new Class[]{
        String.class,
        String.class,
        String.class,
        String.class,
        String.class,
        String.class,
        String.class,
        String.class
    };

    public static final Class[] tiposViewPreciosPorFolio = new Class[]{
        String.class,
        String.class,
        String.class,
        String.class,
        String.class,
        Float.class,
        String.class,
        Date.class,
        Date.class
    };

    public static final Class[] tiposReporteTarima = new Class[]{
        Integer.class,
        String.class,
        String.class,
        Long.class
    };

    public static final Class[] tiposReportePreciosPorFolio = new Class[]{
        String.class,
        String.class,
        String.class,
        String.class
    };

    public static final Class[] tiposEnBusqueda = new Class[]{
        String.class,
        String.class,
        String.class,
        String.class,
        String.class,
        String.class,
        String.class,
        String.class
    };

    public static final Class[] tiposUsers = new Class[]{
        String.class,
        String.class,
        boolean.class
    };
//    public void onInsertEvent();
//    public void onUpdateEvent();
//    public void onDeleteEvent();
}

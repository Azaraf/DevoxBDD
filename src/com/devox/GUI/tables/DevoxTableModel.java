/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.GUI.tables;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author azaraf
 */
public final class DevoxTableModel extends DefaultTableModel implements Serializable, DevoxTable {

    private Object[][] datos;
    private String[] nombreColumnas;
    private JTable tabla;
    private Class[] tipoColumnas;
    private boolean[] editables;
    private String name;
    private TableRowSorter sorter;

    public DevoxTableModel() {
        super();
        tabla = new JTable();
        tabla.setDragEnabled(false);
        tabla.setAutoscrolls(true);
        tabla.setAutoCreateRowSorter(true);
    }

    public DevoxTableModel(String[] columnas) {
        super(null, columnas);
        this.nombreColumnas = columnas;
        tabla = new JTable();
        tabla.setDragEnabled(false);
        tabla.setAutoscrolls(true);
        tabla.setAutoCreateRowSorter(true);
    }

    public DevoxTableModel(String[] columnas, boolean[] editables) {
        super(null, columnas);
        this.nombreColumnas = columnas;
        this.editables = editables;
        if (Arrays.equals(columnas, columnsApto)) {
            this.tipoColumnas = tiposApto;
        }
        if (Arrays.equals(columnas, columnsDestruccion)) {
            this.tipoColumnas = tiposDestruccion;
        }
        if (Arrays.equals(columnas, columnsReporteTarimas)) {
            this.tipoColumnas = tiposReporteTarima;
        }
        if (Arrays.equals(columnas, columnsViewPrecios)) {
            this.tipoColumnas = tiposViewPrecios;
        }
        if (Arrays.equals(columnas, columnsViewPreciosPorFolio)) {
            this.tipoColumnas = tiposViewPreciosPorFolio;
        }
        if (Arrays.equals(columnas, columnsReportePreciosPorFolio)) {
            this.tipoColumnas = tiposReportePreciosPorFolio;
        }
        if (Arrays.equals(columnas, columnasEnBusqueda)) {
            this.tipoColumnas = tiposEnBusqueda;
        }
        if (Arrays.equals(columnas, columnasUsers)) {
            this.tipoColumnas = tiposUsers;
        }
        createTable();
    }

    public void setClasses(Class... tipoColumnas) {
        this.tipoColumnas = tipoColumnas;
    }

    public void setEditables(boolean... editables) {
        this.editables = editables;
    }

    public void createTable() {
        tabla = new JTable(this) {
            @Override
            public Class getColumnClass(int column) {
                return tipoColumnas[column]; //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return editables[column]; //To change body of generated methods, choose Tools | Templates.
            }
        };

        tabla.setAutoscrolls(true);
        tabla.setAutoCreateRowSorter(true);
        tabla.setSelectionMode(0);
        sorter = new TableRowSorter<>(this);
        tabla.setRowSorter(sorter);

    }

    public JTable getTabla() {
        return tabla;
    }

    public void agregarTabla(JScrollPane scrollPane) {
        scrollPane.setViewportView(tabla);
    }

    public void llenarDeDatos(Object[][] datos) {
        this.datos = datos;
        setDataVector(datos, nombreColumnas);
    }

    public void buildTable(JScrollPane scrollPane, ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);

        }

        this.columnIdentifiers = columnNames;
        this.dataVector = data;
        createTable();
        agregarTabla(scrollPane);
        setDataVector(data, columnNames);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

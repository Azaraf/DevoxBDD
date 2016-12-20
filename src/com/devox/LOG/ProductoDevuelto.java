/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.LOG;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import org.inspira.devox.services.productos.ScanBarCode;

/**
 *
 * @author azaraf
 */
public final class ProductoDevuelto {

    private ScanBarCode infoBarCode;
    private String loteSeleccionado;
    private int cantidad;
    private float precioSeleccionado;
    private int posicion;

    private JComboBox lotesCombo;
    private JComboBox preciosCombo;

    public ProductoDevuelto() {
        selecteditems();
    }

    public ProductoDevuelto(ScanBarCode infoBarCode) {
        this.infoBarCode = infoBarCode;
    }

    public String getLoteSeleccionado() {
        return loteSeleccionado;
    }

    public void setLoteSeleccionado(String loteSeleccionado) {
        this.loteSeleccionado = loteSeleccionado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public ScanBarCode getInfoBarCode() {
        return infoBarCode;
    }

    public void setInfoBarCode(ScanBarCode infoBarCode) {
        this.infoBarCode = infoBarCode;
    }

    public float getPrecioTotal() {
        return precioSeleccionado;
    }

    public JComboBox getLotesCombo() {
        return lotesCombo;
    }

    public void setLotesCombo(JComboBox lotesCombo) {
        this.lotesCombo = lotesCombo;
    }

    public JComboBox getPreciosCombo() {
        return preciosCombo;
    }

    public float getPrecioSeleccionado() {
        return precioSeleccionado;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public void selecteditems() {

        lotesCombo.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                loteSeleccionado = lotesCombo.getSelectedItem().toString();
            }
        });

        preciosCombo.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                precioSeleccionado = (Float) preciosCombo.getSelectedItem();
            }
        });

    }

    public void setUpComboBox(JTable table, TableColumn columna, Object[] data) {
        //Set up the editor for the cells.
        JComboBox comboBox = new JComboBox(data);
        columna.setCellEditor(new DefaultCellEditor(comboBox));

        //Set up tool tips for the cells.
        DefaultTableCellRenderer renderer
                = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click para elegir");
        columna.setCellRenderer(renderer);
    }

}

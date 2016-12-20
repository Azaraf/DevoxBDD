/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.LOG.Entidades;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author azaraf
 */
public class Table extends AbstractTableModel{
    private String[] columnNames;
    private Object[][] data;
    private JTable tabla;
    private JScrollPane scrollPane;

    public Table(String[] columnNames, Object[][] data) {
        this.columnNames = columnNames;
        this.data = data;
        tabla = new JTable(this);
        tabla.setAutoCreateRowSorter(true);
        scrollPane = new JScrollPane(tabla);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setLayout(new ScrollPaneLayout());
        
    }

    public Table(String[] columnNames) {
        this.columnNames = columnNames;
        
        tabla = new JTable(this);
        tabla.setAutoCreateRowSorter(true);
        scrollPane = new JScrollPane(tabla);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setLayout(new ScrollPaneLayout());
    }
    
    

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }
    

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }
    
    public JScrollPane getScrollPane(){
        return scrollPane;
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
    
    @Override
    public Class getColumnClass(int c){
        return columnNames[c].getClass();
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    public JTable getTabla() {
        return tabla;
    }    
    
    public void repaint(){
        tabla.revalidate();
        tabla.repaint();
    }
    
    
}

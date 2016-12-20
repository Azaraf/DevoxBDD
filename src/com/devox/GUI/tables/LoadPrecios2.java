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
package com.devox.GUI.tables;

import org.inspira.devox.logger.Log;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.inspira.devox.services.productos.AccionesHistorialPrecio;

/**
 *
 * @author azaraf
 */
public class LoadPrecios2 extends JPanel implements ActionListener, PropertyChangeListener {

    private AccionesHistorialPrecio historialPrecio;
    private JButton cargarButton;
    private JProgressBar progressBar;
    private DefaultTableModel model;
    private JTable table;
    private JPopupMenu popup;
    private JMenuItem itemRemove;
    private JMenuItem itemAdd;
    private Task task;
    private int position;

    public LoadPrecios2(DefaultTableModel model) {
        super(new BorderLayout());
        this.model = model;

        cargarButton = new JButton("Actualizar");
        cargarButton.setActionCommand("start");
        cargarButton.addActionListener(this);

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        popup = new JPopupMenu();
        itemRemove = new JMenuItem("Eliminar");
        popup.add(itemRemove);
        itemAdd = new JMenuItem("Agregar nuevo");
        popup.add(itemAdd);

        itemRemove.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Component c = (Component) e.getSource();
                JPopupMenu popup = (JPopupMenu) c.getParent();
                JTable table = (JTable) popup.getInvoker();
                if (JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar?", "Eliminar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    model.removeRow(table.getSelectedRow());

                }
            }
        });

        itemAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                model.addRow(new String[0]);
            }
        });

        table = new JTable(model);
        table.setSelectionMode(0);
        table.setComponentPopupMenu(popup);
        table.addMouseListener(new TableMouseListener(table));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);

        JPanel panel = new JPanel();
        panel.add(cargarButton);
        panel.add(progressBar);
        panel.setBackground(new Color(250, 250, 250));

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.PAGE_END);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        createFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cargarButton.setEnabled(false);
        table.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        task = new Task();
        task.addPropertyChangeListener(this);
        task.execute();

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
            Log.print(String.format("Completed %d%% of task.\n", task.getProgress()));

        }
    }

    public void setRowColor(int row, Color color) {

    }

    public void createFrame() {
        //Crear ventana
        JFrame frame = new JFrame("Actualizar precios");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Crear contenido

        this.setOpaque(true);
        this.setBackground(new Color(250, 250, 250));
        frame.setContentPane(this);

        //Display
        frame.pack();
        frame.setVisible(true);
    }

    class Task extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            int total = model.getRowCount();
            int progress = 0;
            //Inicializar la propiedad de progreso en 0
            setProgress(0);
            historialPrecio = new AccionesHistorialPrecio();
            try {
                for (progress = 0; progress < total; progress++) {
                    System.out.println("Progresss: " + progress);
                    System.out.println("Total: " + total);
                    String ean = model.getValueAt(progress, 0).toString();
                    String idProducto = model.getValueAt(progress, 1).toString();
                    String idSucursal = model.getValueAt(progress, 2).toString();
                    float precio = new Float(model.getValueAt(progress, 3).toString());
                    System.out.println("first row: " + ean + ", " + idProducto + ", " + idSucursal + ", $" + precio);
                    historialPrecio.setIdProducto(idProducto);
                    historialPrecio.setIdSucursal(idSucursal);
                    historialPrecio.setPrecio(precio);
                    historialPrecio.setEan(ean);
                    Log.print("Actualizando: EAN - " + ean + ", Producto - " + idProducto + ", Sucursal - " + idSucursal + " y precio: $" + precio);
                    if (!historialPrecio.alta()) {
                        Log.print("El producto EAN - " + ean + ", Producto - " + idProducto + " ya existía. Sólo se actualiza");
                        historialPrecio.cambio();
                    }
                    position = progress;
                    int percent = (100 * progress) / total;
                    System.out.println("Percent: %" + percent);
                    setProgress(percent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            historialPrecio.closeConnection();
            return null;
        }

        @Override
        protected void done() {
            Toolkit.getDefaultToolkit().beep();
            cargarButton.setEnabled(true);
            table.setEnabled(true);
            setCursor(null);
            model.setDataVector(null, new String[]{"Código de Barras", "Código Inerno", "Clave Cliente", "Precio"});
            JOptionPane.showMessageDialog(null, "¡Lista de precios actualizada!", "Precios", JOptionPane.INFORMATION_MESSAGE);
        }

    }
}

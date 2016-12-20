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
package com.devox.GUI.PDF;

import org.inspira.devox.logger.Log;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Date;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.inspira.devox.services.queries.ReportePrincipal;

/**
 *
 * @author azaraf
 */
public class VerReportePrincipal extends JPanel {

    private JTable table;
    private JTextField filterText;
    private JTextField statusText;
    
    private TableRowSorter<DefaultTableModel> sorter;
    private Date desde;
    private Date hasta;

    public VerReportePrincipal(Date desde, Date hasta) {
        super();
        this.desde = desde;
        this.hasta = hasta;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        filterText = new JTextField();
        DefaultTableModel model = new DefaultTableModel();
        
        sorter = new TableRowSorter<>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        table.setPreferredScrollableViewportSize(new Dimension(900, 230));
        table.setFillsViewportHeight(true);
        fill(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        JPanel form = new JPanel(new SpringLayout());
        JLabel l1 = new JLabel("Buscar: ", SwingConstants.TRAILING);
        form.add(l1);
        JPanel panelito = new JPanel();
        panelito.setBackground(Color.white);
        GroupLayout glayout = new GroupLayout(panelito);
        panelito.setLayout(glayout);

        table.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = table.getSelectedRow();
                        if (viewRow < 0) {
                            //Selection got filtered away.
                            statusText.setText("row selected: " + viewRow);
                        } else {
                            int modelRow = 
                                table.convertRowIndexToModel(viewRow);
                            statusText.setText("ROW SELECTED: " + modelRow);
                        }
                    }
                }
        );
//        filterText = new JXSearchField("Buscar");
//        filterText.setInstantSearchDelay(100);
//        filterText.setSearchMode(JXSearchField.SearchMode.INSTANT);
//        filterText.setLayoutStyle(JXSearchField.LayoutStyle.VISTA);
//        filterText.setPromptFontStyle(Font.ITALIC);
//
//        filterText.addActionListener((ActionEvent e) -> {
//            newFilter(e);
//        });
//
//        panelito.add(filterText, BorderLayout.WEST);
        filterText.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newFilter();
                    }
                    public void insertUpdate(DocumentEvent e) {
                        newFilter();
                    }
                    public void removeUpdate(DocumentEvent e) {
                        newFilter();
                    }
                });
        l1.setLabelFor(filterText);
        form.add(filterText);
        JLabel l2 = new JLabel("", SwingConstants.TRAILING);
        form.add(l2);
        statusText = new JTextField();
        l2.setLabelFor(statusText);
        form.add(statusText);
        SpringUtilities.makeCompactGrid(form, 2,2, 6, 6, 6, 6);
        add(form);
    }

    private void newFilter() {
        RowFilter<DefaultTableModel, Object> rf = null;

        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(filterText.getText(), 0, 1, 2, 3, 4, 9, 10, 11, 13);
        } catch (java.util.regex.PatternSyntaxException ex) {
            Log.print(ex.getMessage());
            return;
        }
        sorter.setRowFilter(rf);
    }

    private void fill(DefaultTableModel model) {
        List<List<String>> contenido;
        Object[][] obj;
        ReportePrincipal rp = new ReportePrincipal();
        contenido = rp.general(desde, hasta);
        obj = new Object[contenido.size()][];
        for (int i = 0; i < contenido.size(); i++) {
            obj[i] = contenido.get(i).toArray();
        }
        model.setDataVector(obj, new String[]{
            "Folio DHL",
            "Clave cliente",
            "Folio cliente",
            "Cliente",
            "Código de producto",
            "Descripción producto",
            "Cantidad",
            "Precio unitario",
            "Precio total",
            "Lote",
            "División",
            "Destino",
            "Código de Razón",
            "Motivo",
            "Tarima",
            "Recibido",
            "Capturado"
        });
        
        
    }
}

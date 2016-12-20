/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.GUI.panels.forms;

import com.devox.GUI.panels.CapturaDevolucion;
import com.devox.LOG.Admin.AgregarProductoABaseDeDatos;
import org.inspira.devox.logger.Log;
import java.util.List;
import javax.swing.JOptionPane;
import org.inspira.devox.services.clientes.AccionesSucursal;
import org.inspira.devox.services.productos.AccionesCodigoBarras;
import org.inspira.devox.shared.Shareable;
import org.inspira.devox.shared.Sucursal;

/**
 *
 * @author azaraf
 */
public class AgregarProductoFormulario extends javax.swing.JFrame {

    private AccionesCodigoBarras barcodes;
    private List<String> listaCB;
    private String idSucursal;
    private CapturaDevolucion panelPadre;

    /**
     * Creates new form AgregarProducto
     */
    public AgregarProductoFormulario() {
        initComponents();
        setVisible(true);
        
        AccionesSucursal as = new AccionesSucursal();
        as.loadDatabaseInstances();
        int index = 0;
        int i = 0;
        for (Shareable s : as.getEntityInstances()) {
            comboSucursal.addItem((((Sucursal) s).getCodigoSucursal() + " – " + ((Sucursal) s).getFamilia_idFamilia()));
            if (((Sucursal) s).getCodigoSucursal().equals(idSucursal)) {
                index = i;
            }
            i++;
        }

        comboSucursal.setSelectedIndex(index);
    }

//    public AgregarProductoFormulario(String codigoDeBarras, CapturaDevolucion panelPadre) {
//        this.panelPadre = panelPadre;
//        initComponents();
//        setVisible(true);
//        campoCodigoDeBarras.setText(codigoDeBarras);
//        campoCodigoDeBarras.setEnabled(true);
//        campoCodigoInterno.selectAll();
//        panelPadre.bandera = false;
//    }
//
//    public AgregarProductoFormulario(String codigoDeBarras, String codigoInterno, String descripcion, CapturaDevolucion panelPadre) {
//        this.panelPadre = panelPadre;
//        initComponents();
//        setVisible(true);
//        campoCodigoDeBarras.setText(codigoDeBarras);
//        campoCodigoInterno.setText(codigoInterno);
//        campoDescripcion.setText(descripcion);
//        campoCodigoDeBarras.setEnabled(false);
//        campoDescripcion.setEnabled(false);
//        campoCodigoInterno.setEnabled(false);
//        panelPadre.bandera = false;
//    }
    public AgregarProductoFormulario(String codigoDeBarras, String codigoInterno, String descripcion, String idSucursal) {
        initComponents();
        setVisible(true);
        campoCodigoDeBarras.setText(codigoDeBarras);
        campoCodigoInterno.setText(codigoInterno);
        campoDescripcion.setText(descripcion);
        AccionesSucursal as = new AccionesSucursal();
        as.loadDatabaseInstances();
        int index = 0;
        int i = 0;
        for (Shareable s : as.getEntityInstances()) {
            comboSucursal.addItem((((Sucursal) s).getCodigoSucursal() + " – " + ((Sucursal) s).getFamilia_idFamilia()));
            if (((Sucursal) s).getCodigoSucursal().equals(idSucursal)) {
                index = i;
            }
            i++;
        }

        comboSucursal.setSelectedIndex(index);
    }

//    public AgregarProductoFormulario(String codigoDeBarras, String codigoInterno, String descripcion, String lote, CapturaDevolucion panelPadre) {
//        this.panelPadre = panelPadre;
//        initComponents();
//        setVisible(true);
//        campoCodigoDeBarras.setText(codigoDeBarras);
//        campoCodigoInterno.setText(codigoInterno);
//        campoDescripcion.setText(descripcion);
//        campoLote.setText(lote);
//        campoLote.selectAll();
//        campoCodigoDeBarras.setEnabled(false);
//        campoCodigoInterno.setEnabled(false);
//        campoLote.setEnabled(false);
//        panelPadre.bandera = false;
//    }
//
//    public AgregarProductoFormulario(String codigoDeBarras, String codigoInterno, String descripcion, String lote, float precio, CapturaDevolucion panelPadre) {
//
//        this.panelPadre = panelPadre;
//        initComponents();
//        setVisible(true);
//        campoCodigoDeBarras.setText(codigoDeBarras);
//        campoCodigoInterno.setText(codigoInterno);
//        campoDescripcion.setText(descripcion);
//        campoLote.setText(lote);
//        campoPrecio.setText(String.valueOf(precio));
//        campoCodigoDeBarras.setEnabled(false);
//        campoCodigoInterno.setEnabled(false);
//        campoLote.setEnabled(false);
//        campoPrecio.selectAll();
//
//        panelPadre.bandera = false;
//    }
    public String getCodigoInterno() {
        return codigoInterno;
    }

    public String getLote() {
        return lote;
    }

    public Float getPrecio() {
        return precio;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        barraSuperior = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        panelCancelar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelAceptar = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        panelCampos = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        labelData3 = new javax.swing.JLabel();
        campoCodigoDeBarras = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        labelData4 = new javax.swing.JLabel();
        campoCodigoInterno = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        labelData2 = new javax.swing.JLabel();
        campoDescripcion = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        labelData5 = new javax.swing.JLabel();
        campoLote = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        labelData6 = new javax.swing.JLabel();
        campoPrecio = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        labelData7 = new javax.swing.JLabel();
        comboSucursal = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        barraSuperior.setBackground(new java.awt.Color(9, 91, 173));

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 2, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(250, 250, 250));
        jLabel3.setText("   Agregar Producto");

        javax.swing.GroupLayout barraSuperiorLayout = new javax.swing.GroupLayout(barraSuperior);
        barraSuperior.setLayout(barraSuperiorLayout);
        barraSuperiorLayout.setHorizontalGroup(
            barraSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        barraSuperiorLayout.setVerticalGroup(
            barraSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        panelCancelar.setBackground(new java.awt.Color(255, 102, 102));

        jLabel1.setForeground(new java.awt.Color(250, 250, 250));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Cancelar");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelCancelarLayout = new javax.swing.GroupLayout(panelCancelar);
        panelCancelar.setLayout(panelCancelarLayout);
        panelCancelarLayout.setHorizontalGroup(
            panelCancelarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCancelarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelCancelarLayout.setVerticalGroup(
            panelCancelarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCancelarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelAceptar.setBackground(new java.awt.Color(102, 204, 0));

        jLabel2.setForeground(new java.awt.Color(250, 250, 250));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Aceptar");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelAceptarLayout = new javax.swing.GroupLayout(panelAceptar);
        panelAceptar.setLayout(panelAceptarLayout);
        panelAceptarLayout.setHorizontalGroup(
            panelAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAceptarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelAceptarLayout.setVerticalGroup(
            panelAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAceptarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelCampos.setBackground(new java.awt.Color(250, 250, 250));
        panelCampos.setLayout(new javax.swing.BoxLayout(panelCampos, javax.swing.BoxLayout.Y_AXIS));

        jPanel3.setBackground(new java.awt.Color(250, 250, 250));

        labelData3.setBackground(new java.awt.Color(250, 250, 250));
        labelData3.setForeground(new java.awt.Color(9, 91, 173));
        labelData3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelData3.setText("Código de barras");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelData3, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoCodigoDeBarras, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelData3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoCodigoDeBarras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelCampos.add(jPanel3);

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));

        labelData4.setBackground(new java.awt.Color(250, 250, 250));
        labelData4.setForeground(new java.awt.Color(9, 91, 173));
        labelData4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelData4.setText("Código interno de producto");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelData4, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoCodigoInterno, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelData4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoCodigoInterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelCampos.add(jPanel1);

        jPanel2.setBackground(new java.awt.Color(250, 250, 250));

        labelData2.setBackground(new java.awt.Color(250, 250, 250));
        labelData2.setForeground(new java.awt.Color(9, 91, 173));
        labelData2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelData2.setText("Descripción");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelData2, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelData2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelCampos.add(jPanel2);

        jPanel4.setBackground(new java.awt.Color(250, 250, 250));

        labelData5.setBackground(new java.awt.Color(250, 250, 250));
        labelData5.setForeground(new java.awt.Color(9, 91, 173));
        labelData5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelData5.setText("Lote");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelData5, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoLote, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelData5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelCampos.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(250, 250, 250));

        labelData6.setBackground(new java.awt.Color(250, 250, 250));
        labelData6.setForeground(new java.awt.Color(9, 91, 173));
        labelData6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelData6.setText("Precio: $");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelData6, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelData6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelCampos.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(250, 250, 250));

        labelData7.setBackground(new java.awt.Color(250, 250, 250));
        labelData7.setForeground(new java.awt.Color(9, 91, 173));
        labelData7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelData7.setText("Sucursal");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelData7, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelData7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelCampos.add(jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(barraSuperior, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelCampos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(panelAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(barraSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCampos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(panelAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        cancelar();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        int i = aceptar();
        Log.print("" + i);
    }//GEN-LAST:event_jLabel2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel barraSuperior;
    private javax.swing.JTextField campoCodigoDeBarras;
    private javax.swing.JTextField campoCodigoInterno;
    private javax.swing.JTextField campoDescripcion;
    private javax.swing.JTextField campoLote;
    private javax.swing.JTextField campoPrecio;
    private javax.swing.JComboBox comboSucursal;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel labelData2;
    private javax.swing.JLabel labelData3;
    private javax.swing.JLabel labelData4;
    private javax.swing.JLabel labelData5;
    private javax.swing.JLabel labelData6;
    private javax.swing.JLabel labelData7;
    private javax.swing.JPanel panelAceptar;
    private javax.swing.JPanel panelCampos;
    private javax.swing.JPanel panelCancelar;
    // End of variables declaration//GEN-END:variables

    private boolean insertado = true;
    private String codigoDeBarras;
    private String codigoInterno;
    private String descripcion;
    private String lote;
    private Float precio;

    private int aceptar() {
        codigoDeBarras = campoCodigoDeBarras.getText();
        codigoInterno = campoCodigoInterno.getText();
        descripcion = campoDescripcion.getText();
        lote = campoLote.getText();
        precio = Float.parseFloat(campoPrecio.getText());

        String s = comboSucursal.getSelectedItem().toString();
        String ss[] = s.split(" – ");
        idSucursal = ss[0];

        if (codigoDeBarras == null
                || codigoInterno == null
                || descripcion == null
                || lote == null
                || precio == null) {
            JOptionPane.showMessageDialog(null, "Hay campos vacíos");
        } else {
            AgregarProductoABaseDeDatos add = new AgregarProductoABaseDeDatos();
            add.setIdSucursal(idSucursal);
            add.setCodigoDeBarras(codigoDeBarras);
            add.setCodigoInterno(codigoInterno);
            add.setDescripcion(descripcion);
            add.setLote(lote);
            add.setPrecio(precio);
            
            add.nuevoCompleto();
        }

//        if (codigoDeBarras == null) {
//            cancelar();
//            return -2;
//        }
//        add.setCodigoDeBarras(codigoDeBarras);
//        if (campoCodigoDeBarras.isEnabled()) {//el usuario lo ingresó
//            if (codigoInterno == null) {
//                if (JOptionPane.showConfirmDialog(this, "¿Sólo insertar el código de barras?", "Atención", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
//                    add.nuevoCB();
//                    this.dispose();
//                    return 0;
//                }
//            }
//            add.setCodigoInterno(codigoInterno);
//            if (descripcion == null) {
//                //falta la descripción
//                JOptionPane.showMessageDialog(this, "No puede insertar producto\nsin descripción.", "Atención", JOptionPane.OK_OPTION);
//                return -1;
//            }
//            add.setDescripcion(descripcion);
//            if (campoCodigoInterno.isEnabled() && campoDescripcion.isEnabled()) {//el usuario lo edita
//                if (lote == null) {
//                    add.nuevoCByProducto();
//                    this.dispose();
//                    return 0;
//                    //falta el lote
//                }
//                add.setLote(lote);
//                if (campoLote.isEnabled()) { //usuario puede editar lote
//                    if (campoPrecio.getText() == null) {
//                        add.nuevoSinPrecio();
//                        this.dispose();
//                        return 0;
//                        //falta el precio
//                    }
//                    add.nuevoCompleto();
//                    this.dispose();
//                    return 0;
//                    //add.nuevo
//                }
//                //el lote viene por default
//                if (campoPrecio.getText() == null) {
//                    JOptionPane.showMessageDialog(this, "No puede insertar producto\nsin nuevo precio.", "Atención", JOptionPane.OK_OPTION);
//                    return -1;//falta el precio
//                }
//                add.nuevoPrecio();
//                this.dispose();
//                return 0;
//            }
//            //el producto viene por default
//            if (campoLote.getText() == null) {
//                JOptionPane.showMessageDialog(this, "No puede insertar producto\nsin nuevo lote.", "Atención", JOptionPane.OK_OPTION);
//                return -1;
//            }
//            if (campoPrecio.getText() == null) {
//                add.nuevoLote();
//                this.dispose();
//                return 0;
//            }
//            add.nuevoLoteyPrecio();
//        }
//        if (campoCodigoInterno.isEnabled() && campoDescripcion.isEnabled()) {
//            if (campoCodigoInterno.getText() == null || campoDescripcion.getText() == null) {
//                JOptionPane.showMessageDialog(this, "No puede insertar producto\nsin nuevo código o descripción.", "Atención", JOptionPane.OK_OPTION);
//                return -1;
//            }
//            if (lote == null) {
//                add.nuevoProducto();
//                this.dispose();
//                return 0;
//            }
//            if (campoPrecio.getText() == null) {
//                add.nuevoProductoyLote();
//                this.dispose();
//                return 0;
//            }
//            add.nuevoProductoConLoteyPrecio();
//            this.dispose();
//            return 0;
//        }
//        if (campoLote.isEnabled() && lote != null && campoPrecio.getText() != null) {
//            add.nuevoLoteyPrecio();
            this.dispose();
            return 0;
//        }
//        return 3;
    }

    public void setSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    private void cancelar() {
        insertado = false;
        this.dispose();
    }

    private void setEditable(boolean b) {
        campoCodigoDeBarras.setEnabled(b);
        campoCodigoInterno.setEnabled(b);
        campoLote.setEnabled(b);
        campoPrecio.setEnabled(b);
    }
}

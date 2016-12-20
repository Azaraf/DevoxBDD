package com.devox.GUI.panels.forms;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;
import org.inspira.devox.services.clientes.AccionesCliente;
import org.inspira.devox.services.clientes.AccionesFamilia;
import org.inspira.devox.services.clientes.AccionesSucursal;
import org.inspira.devox.shared.Cliente;
import org.inspira.devox.shared.Familia;
import org.inspira.devox.shared.Shareable;

/**
 *
 * @author azaraf
 */
public class NuevaSucursal extends javax.swing.JFrame {

    /**
     * Creates new form NuevaTarima1
     */
    public NuevaSucursal() {
        
        divisiones = new AccionesFamilia();
        rfcs = new AccionesCliente();
        listaDivisiones = new ArrayList<>();
        listaRfcs = new ArrayList<>();
        
        divisiones.loadDatabaseInstances();
        for (Shareable m : divisiones.getEntityInstances()){
              listaDivisiones.add(((Familia)m).getCodigoFamilia());
         }
        rfcs.loadDatabaseInstances();
        for(Shareable m: rfcs.getEntityInstances()){
            listaRfcs.add(((Cliente)m).getRfc());
        }

        nuevo = true;
       
        initComponents();
    }
    
    
        public NuevaSucursal(String idSucursal, String familia, String nombre, String rfc_Cliente) {
            this.idSucursal = idSucursal;
            this.division = familia;
            this.nombre = nombre;
            this.rfc_Cliente = rfc_Cliente;
            
            divisiones = new AccionesFamilia();
            rfcs = new AccionesCliente();
            listaDivisiones = new ArrayList<>();
            listaRfcs = new ArrayList<>();

            divisiones.loadDatabaseInstances();
            for (Shareable m : divisiones.getEntityInstances()){
                listaDivisiones.add(((Familia)m).getCodigoFamilia());
            }
            rfcs.loadDatabaseInstances();
            for(Shareable m: rfcs.getEntityInstances()){
                 listaRfcs.add(((Cliente)m).getRfc());
            }

            
            nuevo = false;
            
            initComponents();
             jTextField2.setText(idSucursal);
            jTextField1.setText(nombre);
            jComboBox3.setSelectedItem(familia);
           jComboBox4.setSelectedItem(rfc_Cliente);

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
        jPanel1 = new javax.swing.JPanel();
        labelData1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        labelData5 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox(listaDivisiones.toArray(new Object[0]));
        jPanel4 = new javax.swing.JPanel();
        labelData4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        labelData6 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox(listaRfcs.toArray(new Object[0]));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        barraSuperior.setBackground(new java.awt.Color(9, 91, 173));

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 2, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(250, 250, 250));
        jLabel3.setText("   Nueva Sucursal");

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

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));

        labelData1.setBackground(new java.awt.Color(250, 250, 250));
        labelData1.setForeground(new java.awt.Color(9, 91, 173));
        labelData1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelData1.setText("Clave cliente");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(labelData1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelData1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        panelCampos.add(jPanel1);

        jPanel5.setBackground(new java.awt.Color(250, 250, 250));

        labelData5.setBackground(new java.awt.Color(250, 250, 250));
        labelData5.setForeground(new java.awt.Color(9, 91, 173));
        labelData5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelData5.setText("División");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(labelData5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(134, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelData5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        panelCampos.add(jPanel5);

        jPanel4.setBackground(new java.awt.Color(250, 250, 250));

        labelData4.setBackground(new java.awt.Color(250, 250, 250));
        labelData4.setForeground(new java.awt.Color(9, 91, 173));
        labelData4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelData4.setText("Nombre");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(labelData4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addGap(50, 50, 50))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelData4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        panelCampos.add(jPanel4);

        jPanel6.setBackground(new java.awt.Color(250, 250, 250));

        labelData6.setBackground(new java.awt.Color(250, 250, 250));
        labelData6.setForeground(new java.awt.Color(9, 91, 173));
        labelData6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelData6.setText("RFC Cliente");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(labelData6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(134, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelData6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        panelCampos.add(jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(barraSuperior, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
                .addComponent(panelCampos, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        cancelar();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        aceptar();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        
    }//GEN-LAST:event_jTextField1KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel barraSuperior;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel labelData1;
    private javax.swing.JLabel labelData4;
    private javax.swing.JLabel labelData5;
    private javax.swing.JLabel labelData6;
    private javax.swing.JPanel panelAceptar;
    private javax.swing.JPanel panelCampos;
    private javax.swing.JPanel panelCancelar;
    // End of variables declaration//GEN-END:variables
   
    private List<String> listaDivisiones;
    private List<String> listaRfcs;
    private AccionesFamilia divisiones;
    private AccionesCliente rfcs;
    private String idSucursal;
    private String division;
    private String nombre;
    private String rfc_Cliente;
    private boolean insertado = false;
    private boolean nuevo;
    private int position;

    public void setPosition(int position) {
        this.position = position;
    }
    
    
    private void aceptar() {
        rfc_Cliente = jComboBox4.getSelectedItem().toString();
        nombre = jTextField1.getText();
        division = (jComboBox3.getSelectedItem().toString());
        idSucursal = jTextField2.getText();
        
            if (nuevo){
                //generar código para insertar
                AccionesSucursal newC = new AccionesSucursal();
                newC.setFamilia_idFamilia(division);
                newC.setIdSucursal(idSucursal);
                newC.setCliente_rfc(rfc_Cliente);
                newC.setNombre(nombre);

                newC.alta();

                 insertado = true;
                this.setVisible(false);
        }else{
            //Código para hacer update
                AccionesSucursal newC = new AccionesSucursal();
                newC.setFamilia_idFamilia(division);
                newC.setIdSucursal(idSucursal);
                newC.setCliente_rfc(rfc_Cliente);
                newC.setNombre(nombre);
                
                newC.setPosition(position);
                newC.loadDatabaseInstances();
                newC.cambio();
                
                insertado = true;
                this.setVisible(false);
        }
    }

    private void cancelar() {
        insertado = false;
        this.setVisible(false);
    }

    

    public boolean isInsertado() {
        return insertado;
    }
    
    
}

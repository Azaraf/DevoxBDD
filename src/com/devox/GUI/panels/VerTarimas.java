/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.GUI.panels;

import com.devox.GUI.PDF.CrearReporteTarimas;
import com.devox.LOG.Entidades.Configuraciones;
import com.devox.LOG.cuadricula.ListaCuadriculasTarima;
import org.inspira.devox.logger.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.inspira.devox.services.queries.LayoutTarima;

/**
 *
 * @author azaraf
 */
public class VerTarimas extends javax.swing.JPanel {

    //private FolioJoinSucursal sucursal;
    private int quantity;
    String num;

    /**
     * Creates new form VerDevoluciones
     */
    public VerTarimas() {
        num = new Date().toString();
        initComponents();
        quantity = (Integer)spinner.getValue();
        llenarDatos();
        llenarPanel(panelTarimas, lista, quantity);
        jScrollPane1.getHorizontalScrollBar().setUnitIncrement(20);
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(20);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jLabel1 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jFileChooser1 = new javax.swing.JFileChooser();
        panel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelTarimas = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        spinner = new javax.swing.JSpinner();

        jFrame1.setTitle("Seleccione rango de fechas");
        jFrame1.setBackground(new java.awt.Color(250, 250, 250));
        jFrame1.setSize(new java.awt.Dimension(314, 130));

        jLabel1.setText("De:");

        jLabel2.setText("A:");

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(jFrame1Layout.createSequentialGroup()
                        .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18))
                            .addGroup(jFrame1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(10, 10, 10)))
                        .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jFrame1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jFileChooser1.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        jFileChooser1.setSelectedFile(new File("/" + "Reporte folios "+num+".pdf"));

        setBackground(new java.awt.Color(250, 250, 250));
        setMinimumSize(new java.awt.Dimension(720, 400));
        setPreferredSize(new java.awt.Dimension(720, 400));

        panel1.setBackground(new java.awt.Color(250, 250, 250));
        panel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tarimas", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Lucida Grande", 0, 13), new java.awt.Color(50, 50, 50))); // NOI18N
        panel1.setPreferredSize(new java.awt.Dimension(720, 300));

        panelTarimas.setBackground(new java.awt.Color(250, 250, 250));
        panelTarimas.setLayout(new java.awt.GridLayout(0, 1, 5, 10));
        jScrollPane1.setViewportView(panelTarimas);

        jLabel11.setText("Ver últimas");

        spinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(10), null, null, Integer.valueOf(1)));
        spinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spinner, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(554, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(spinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Date desde = jDateChooser1.getDate();
        Date hasta = jDateChooser2.getDate();
        jFrame1.setVisible(false);
        seleccionarRangos(lista, true, desde, hasta);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void spinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerStateChanged
        // TODO add your handling code here:
        quantity = (Integer) spinner.getValue();
        ListaCuadriculasTarima.getLastNTarimas(lista, quantity);
        llenarPanel(panelTarimas, lista, quantity);
    }//GEN-LAST:event_spinnerStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panelTarimas;
    private javax.swing.JSpinner spinner;
    // End of variables declaration//GEN-END:variables
    List<CuadriculaTarima> lista = new ArrayList<>();

    private void llenarDatos() {
//        AccionesTarima at = new AccionesTarima();
//        at.loadDatabaseInstances();
//        for (Shareable s : at.getEntityInstances()) {
//            CuadriculaTarima ct = new CuadriculaTarima(
//                    ((Tarima) s).getNombre(),
//                    ((Tarima) s).getFechaCreacion(),
//                    ((Tarima) s).getFechaCierre(),
//                    ((Tarima) s).getIdTarima());
//            lista.add(ct);
//        }
        ListaCuadriculasTarima.getLastNTarimas(lista, quantity);
    }

    private void llenarPanel(JPanel panel, List<CuadriculaTarima> lista, int quantity) {
        panel.removeAll();
        int limit = lista.size();
        if (limit >= quantity) {
            for (int i = 0; i < quantity; i++) {
                panel.add(lista.get(i));
            }
        } else {
            for (int i = 0; i < limit; i++) {
                panel.add(lista.get(i));
            }
        }
        panel.revalidate();
        panel.repaint();
    }

    private void seleccionarTodos(List<CuadriculaTarima> lista, boolean what) {
        for (CuadriculaTarima c : lista) {
            c.setjCheckBox1(what);
            c.revalidate();
            c.repaint();
        }

    }

    private void seleccionarRangos(List<CuadriculaTarima> lista, boolean what, Date desde, Date hasta) {
        for (CuadriculaTarima c : lista) {
            if (c.getFecha().after(desde) && c.getFecha().before(hasta)) {
                c.setjCheckBox1(what);
            }
            c.revalidate();
            c.repaint();
        }
    }

    private void imprimirMuchasTarimas() {
        System.err.println("Not supported yet");
    }

    private void imprimirEstaTarima(String name) {
        CrearReporteTarimas pdf = new CrearReporteTarimas(name, new LayoutTarima().obtenerLayout(name));
        pdf.crear(new java.io.File(new Configuraciones().getPath() + "/Tarima_" + name + ".pdf"));
        Log.print("tarimas imprimio pdf /Tarima_" + name + ".pdf\")");
        JOptionPane.showMessageDialog(null, "Documento guardado!");
    }

}
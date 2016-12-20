/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.inspira.devox.logger.Log;

/**
 *
 * @author azaraf
 */
public class Cargando {

    private JPanel glass;
    private JFrame frame;
    private JLabel icon;
    private JLabel mensaje = new JLabel();
    private int x;

    public Cargando(JFrame frame) {
        this.frame = frame;
        x = 0;
    }

    public Cargando(JFrame frame, int x) {
        this.frame = frame;
        this.x = x;
    }

    private void crearGlassPanel() {
        try {
            glass = new JPanel() {

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
                    g.setColor(new Color(255, 255, 255, 150));
                    g.fillRect(x, 0, frame.getWidth(), frame.getHeight());
                }
            };
            glass.setOpaque(false);
            glass.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            icon = new JLabel();
            icon.setIcon(new ImageIcon(getClass().getResource("/com/devox/GUI/images/load.gif")));
            icon.setText("Espere...");
            icon.setFont(new Font("Lucida Grande", 1, 24));
            icon.setForeground(new Color(55, 55, 55));
            icon.setVerticalTextPosition(JLabel.BOTTOM);
            icon.setHorizontalTextPosition(JLabel.CENTER);
            icon.setHorizontalAlignment(JLabel.CENTER);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 40;
            c.ipadx = 60;
            c.weightx = 0.0;
            c.gridwidth = 1;
            c.gridx = 0;
            c.gridy = 1;
            mensaje.setForeground(new Color(55, 55, 55));
            mensaje.setVerticalTextPosition(JLabel.BOTTOM);
            mensaje.setHorizontalTextPosition(JLabel.CENTER);
            mensaje.setHorizontalAlignment(JLabel.CENTER);
            mensaje.setFont(new Font("Lucida Grande", 0, 16));
            glass.add(icon, c);
            c.gridx = 0;
            c.gridy = 2;
            glass.add(mensaje, c);

            glass.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    e.consume();
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("They're touching me :v");
                }
            });
            frame.setGlassPane(glass);
        } catch(Exception e){
            Log.print(e);
        }
        glass.setVisible(true);
    }

    public void setCantidad(int x, int y){
        mensaje.setText("Creando devolucion "+ x + " de " + y +".");
    }
    
    public void setProgreso(int x, int y){
        int z = (100*x)/y;
        mensaje.setText("Cargando devoluciones: progreso " + z + "%");
    }
    
    public void setProgresoEstados(int x, int y, String estado){
        int z = (100*x)/y;
        mensaje.setText("Cargando devoluciones en " + estado + ": progreso " + z + "%");
    }
    
    public void personalizarMensaje(String msg){
        mensaje.setText(msg);
    }
    
    public void setLoadingPanel(){
        crearGlassPanel();
        glass.setVisible(true);
    }
    
    public void removeLoadingPanel(){
        glass.remove(icon);
        glass.remove(mensaje);
        glass.setVisible(false);
    }
    
    public JPanel getGlass() {
        return glass;
    }
}

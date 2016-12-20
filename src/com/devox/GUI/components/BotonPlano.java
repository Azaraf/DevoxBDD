/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.GUI.components;

import java.awt.Color;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author azaraf
 */
public class BotonPlano{
    private boolean selected;
    private final JPanel panel;
    private final Color colorEntered;
    private final Color colorExited;
    private final Color colorClicked;
    private final Border borderClicked;
    
    public BotonPlano(JPanel panel) {
        this.panel = panel;
        selected = false;
        colorEntered = new Color(230, 230, 230);
        colorExited = new Color(203, 203, 203);
        borderClicked = null;
        this.colorClicked = colorEntered;
    }
    
    public BotonPlano(JPanel panel, Color colorEntered, Color colorExited) {
        this.panel = panel;
        selected = false;
        this.colorEntered = colorEntered;
        this.colorExited = colorExited;
        this.colorClicked = colorEntered;
        borderClicked = new org.jdesktop.swingx.border.DropShadowBorder();
    }

    public BotonPlano(JPanel panel, Border borderClicked) {
        this.panel = panel;
        selected = false;
        colorEntered = new Color(230, 230, 230);
        colorExited = new Color(203, 203, 203);
        this.colorClicked = colorEntered;
        this.borderClicked = borderClicked;
    }

    public BotonPlano(JPanel panel, Color colorEntered, Color colorExited, Border borderClicked) {
        this.panel = panel;
        selected = false;
        this.colorEntered = colorEntered;
        this.colorExited = colorExited;
        this.colorClicked = colorEntered;
        this.borderClicked = borderClicked;
    }
    
    public BotonPlano(JPanel panel, Color colorEntered, Color colorExited, Color colorClicked, Border borderClicked) {
        this.panel = panel;
        selected = false;
        this.colorEntered = colorEntered;
        this.colorExited = colorExited;
        this.colorClicked = colorClicked;
        this.borderClicked = borderClicked;
    }
    
    public boolean isSelected(){
        return selected;
    }
    
    public void setSelected(boolean selected){
        this.selected = selected;
    }
    
    public void mouseEntered(){
        panel.setBackground(colorEntered);
    }
    
    public void mouseExited(){
        panel.setBackground(colorExited);
    } 
    public void mouseClicked(){
        panel.setBorder(borderClicked);
        panel.setBackground(colorClicked);
        panel.repaint();
        selected = true;
    }
    public void mouseUnclicked(){
        panel.setBorder(null);
        panel.setBackground(colorExited);
        panel.repaint();
        selected = false;
    }
    
    public void clic(List<BotonPlano> listaBotones, int i){
        if (listaBotones.get(i).isSelected()){
        }else{
            for (BotonPlano b : listaBotones){
                b.mouseUnclicked();
            }
            listaBotones.get(i).mouseClicked();
        }
    }
}

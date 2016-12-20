/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.GUI.tables;

import java.awt.event.ActionEvent;
import javax.swing.event.PopupMenuEvent;

/**
 *
 * @author azaraf
 */
public interface ClickDerechoTabla {
    public void inClicDerecho(ActionEvent e);
    public void inClicDerechoOnCellVisible(PopupMenuEvent e);
    public void inClicDerechoOnCellInvisible(PopupMenuEvent e);
    public void inClicDerechoOnCellCanceled(PopupMenuEvent e);
}

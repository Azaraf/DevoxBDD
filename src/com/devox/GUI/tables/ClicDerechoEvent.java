/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.GUI.tables;

import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 *
 * @author azaraf
 */
public abstract class ClicDerechoEvent implements ClickDerechoTabla {

    private JPopupMenu popupMenu;

    public ClicDerechoEvent(JMenuItem item) {

        item.addActionListener((ActionEvent e) -> {
            inClicDerecho(e);
        });
        popupMenu = new JPopupMenu();
        popupMenu.add(item);
        popupMenu.addPopupMenuListener(new PopupMenuListener() {

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                inClicDerechoOnCellVisible(e);
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                inClicDerechoOnCellInvisible(e);
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                inClicDerechoOnCellCanceled(e);
            }
        });
    }

    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }
}

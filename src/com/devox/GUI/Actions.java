 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.GUI;

import com.devox.GUI.panels.CapturaDevolucion;
import com.devox.GUI.panels.ConsultasBusqueda;
import com.devox.GUI.panels.NuevaDevolucion;
import com.devox.GUI.panels.VerReportes;
import com.devox.GUI.panels.VerDevoluciones;
import com.devox.GUI.panels.VerEstados;
import com.devox.GUI.panels.VerPrecios;
import com.devox.GUI.panels.VerTarimas;
import com.devox.LOG.Entidades.ContenidoDevolucion;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author azaraf
 */
public class Actions {

    private JPanel panelContenido;
    private JLabel logoAbbottGrande;
    private JFrame parentFrame;
    private JLabel titulo;
    private JLabel backButton;
    private NuevaDevolucion dev;
    private CapturaDevolucion captura;
    private int posicionCaptura = 0; //0 - no hay devolución en curso, 
//1 - está en el formulario NuevaDevolucion, 
//2 - Está capturando CapturaDevolucion

    /**
     * Esta clase sirve para controlar la interfaz gráfica. Se usa ejecutar cada una de las
     * acciones del menú principal (los botones laterales).
     * @param panelContenido Es el panel de la ventana que contendrá todas las ventanas
     * internas de las acciones.
     * @param logoAbbottGrande Es la imagen logotipo de abbott
     */
    public Actions(JPanel panelContenido, JLabel logoAbbottGrande, JFrame frame) {
        this.panelContenido = panelContenido;
        this.logoAbbottGrande = logoAbbottGrande;
        this.parentFrame = frame;
    }

    public void nuevaDevolucion() {
        if (posicionCaptura == 1) {
            panelContenido.removeAll();
            panelContenido.add(dev);
            panelContenido.revalidate();
            panelContenido.repaint();
            titulo.setText("Nueva devolución");
        } else {
            posicionCaptura = 1;
//            ContenidoDevolucion content = new ContenidoDevolucion();
//            content.setFolioDHL(0);
            dev = new NuevaDevolucion(this);
            panelContenido.removeAll();
            panelContenido.add(dev);
            panelContenido.revalidate();
            panelContenido.repaint();
            titulo.setText("Nueva devolución");
        }
    }

    public void capturarProductos(ContenidoDevolucion contenido) {
        if (posicionCaptura == 2) {
            posicionCaptura = 1;
            panelContenido.removeAll();
            panelContenido.add(captura);
            panelContenido.revalidate();
            panelContenido.repaint();
            titulo.setText("Captura");
        } else {
            posicionCaptura = 2;
            captura = new CapturaDevolucion(contenido, this);
            panelContenido.removeAll();
            panelContenido.add(captura);
            panelContenido.revalidate();
            panelContenido.repaint();
            titulo.setText("Captura");
            backButton.setVisible(true);
        }

    }

    public void guardarDevolucion(ContenidoDevolucion contenidoDevolucion) {
        posicionCaptura = 0;
        panelContenido.removeAll();
        logoAbbottGrande.setVisible(true);
        panelContenido.add(logoAbbottGrande);
        panelContenido.revalidate();
        panelContenido.repaint();
        titulo.setText("Devox");
        backButton.setVisible(false);
        System.gc();
    }

    public void cancelarDevolucion() {
        posicionCaptura = 0;
        panelContenido.removeAll();
        logoAbbottGrande.setVisible(true);
        panelContenido.add(logoAbbottGrande);
        panelContenido.revalidate();
        panelContenido.repaint();
        titulo.setText("Devox");
        backButton.setVisible(false);
    }

    public void verCatalogo() {
        ConsultasBusqueda catalogos = new ConsultasBusqueda();
        panelContenido.removeAll();
        panelContenido.add(catalogos);
        panelContenido.revalidate();
        panelContenido.repaint();
        titulo.setText("Catálogo");
    }

    public JLabel getTitulo() {
        return titulo;
    }

    public void setTitulo(JLabel titulo) {
        this.titulo = titulo;
    }

    void verEstados() {
        //System.gc();
        VerEstados ver = new VerEstados(parentFrame);
        panelContenido.removeAll();
        panelContenido.add(ver);
        panelContenido.revalidate();
        panelContenido.repaint();
        titulo.setText("Estados");
    }

    void verDevoluciones() {
        //System.gc();
        VerDevoluciones ver = new VerDevoluciones(parentFrame);
        panelContenido.removeAll();
        panelContenido.add(ver);
        panelContenido.revalidate();
        panelContenido.repaint();
        titulo.setText("Devoluciones");
    }

    void verReportes() {
        //System.gc();
        VerReportes ver = new VerReportes(parentFrame);
        panelContenido.removeAll();
        panelContenido.add(ver);
        panelContenido.revalidate();
        panelContenido.repaint();
        titulo.setText("Reportes");
    }

    void verPrecios() {
        System.gc();
        VerPrecios precios = new VerPrecios();
        panelContenido.removeAll();
        panelContenido.add(precios);
        panelContenido.revalidate();
        panelContenido.repaint();
        titulo.setText("Precios");
    }

    void verTarimas() {
        System.gc();
        VerTarimas ver = new VerTarimas();
        panelContenido.removeAll();
        panelContenido.add(ver);
        panelContenido.revalidate();
        panelContenido.repaint();
        titulo.setText("Tarimas");
    }

    public void setBackButton(JLabel backButton) {
        this.backButton = backButton;
        backButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                switch (posicionCaptura) {
                    case 1:
                        panelContenido.removeAll();
                        panelContenido.add(dev);
                        panelContenido.revalidate();
                        panelContenido.repaint();
                        titulo.setText("Nueva devolución");
                        backButton.setVisible(false);
                        break;
                    case 2:
                        panelContenido.removeAll();
                        panelContenido.add(captura);
                        panelContenido.revalidate();
                        panelContenido.repaint();
                        titulo.setText("Captura");
                        break;

                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

}

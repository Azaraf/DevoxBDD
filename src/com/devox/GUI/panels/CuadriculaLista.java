/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.GUI.panels;

import com.devox.GUI.PDF.CrearReporteApto;
import com.devox.GUI.PDF.CrearReporteDestruccion;
import com.devox.GUI.tables.DevoxTableModel;
import com.devox.LOG.Admin.EscarnearCodigoDeBarras;
import com.devox.LOG.Admin.OptionPanels;
import com.devox.LOG.Entidades.Configuraciones;
import com.devox.LOG.Entidades.Producto;
import com.devox.LOG.Funciones;
import java.awt.Color;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import org.inspira.devox.database.DatabaseConnection;
import org.inspira.devox.logger.Log;
import org.inspira.devox.services.devolucion.AccionesDescripcionDevolucionEstado;
import org.inspira.devox.services.organico.AccionesAlmacen;
import org.inspira.devox.services.organico.AccionesTarima;
import org.inspira.devox.services.productos.AccionesHistorialPrecio;
import org.inspira.devox.services.queries.FolioJoinSucursal;
import org.inspira.devox.shared.Almacen;
import org.inspira.devox.shared.HistorialPrecio;
import org.inspira.devox.shared.Shareable;

import org.inspira.devox.shared.Tarima;
import org.jdesktop.swingx.prompt.PromptSupport;

/**
 *
 * @author azaraf
 */
public class CuadriculaLista extends javax.swing.JPanel implements Comparable<CuadriculaLista>{

    private FolioJoinSucursal fjs;
    private boolean flagPanelDestino = false;
    List<ImageIcon> iconos = new ArrayList<>();
    List<Color> colores = new ArrayList<>();
    List<String> estaditos = new ArrayList<>();
    private boolean checked = true;
    private Date fecha;
    private final Color seg = new Color(50, 50, 50); // Color negro
    private final Color dic = new Color(9, 91, 173); // Color azul
    private final Color dev = new Color(220, 102, 102); // Color rojo
    private final Color ent = new Color(102, 204, 0); // Color verde
    private final Color colorSelected = new Color(220, 235, 255); // Color azul claro
    private final Color colorUnselected = new Color(250, 250, 250); // Color blanco
    private final ImageIcon iconSeg = new ImageIcon(getClass().getResource("/com/devox/GUI/images/devoluciongrey.png"));
    private final ImageIcon iconDic = new ImageIcon(getClass().getResource("/com/devox/GUI/images/devolucionblue.png"));
    private final ImageIcon iconDev = new ImageIcon(getClass().getResource("/com/devox/GUI/images/devolucionred.png"));
    private final ImageIcon iconEnt = new ImageIcon(getClass().getResource("/com/devox/GUI/images/devoluciongreen.png"));
    private DevoxTableModel model;
    private boolean[] editable = new boolean[]{false, false, false, false, false, false, false, false};
    private boolean vacia;
    private int folio;
    private int status;
    private OptionPanels devolucionProductos;
    private float montoTotal;
    private int cantidadTotal;
    private boolean created = false;
    private boolean isEditable = false;

    /**
     * Creates new form Cuadricula
     *
     * @param folio
     */
    public CuadriculaLista(int folio) {
        this.folio = folio;
        iconos.add(null);
        iconos.add(iconSeg);
        iconos.add(iconDic);
        iconos.add(iconDev);
        iconos.add(iconEnt);
        colores.add(null);
        colores.add(seg);
        colores.add(dic);
        colores.add(dev);
        colores.add(ent);
        estaditos.add(null);
        estaditos.add("Segregación");
        estaditos.add("Dictamen");
        estaditos.add("Captura DLX");
        estaditos.add("Entregado");
        fjs = new FolioJoinSucursal(folio);
        fjs.loadBasicData();
        initComponents();
        setUpInformation();
    }

    public void crearCuadricula() {
        if (!created) {
            fjs.loadData();
            flagPanelDestino = fjs.isDestino();
            this.status = fjs.getStatus().getCodigo();
            vacia = fjs.getCliente() == null;
            setUpInformation();
            created = true;
            if (this.status != 1) {
                if (!Funciones.isSuperUserSession()) {
                    panelAddItem.setVisible(false);
                    panelEditar.setVisible(false);
                    panelRemoveItem.setVisible(false);
                    panelTarima.setVisible(false);
                    jButton1.setEnabled(false);
                    jButton1.setToolTipText("No tiene permiso de editar devoluciones en este estado");
                }
            }
        }
    }

    public boolean getjCheckBox1() {
        return jCheckBox1.isSelected();
    }

    public void setjCheckBox1(boolean pos) {
        jCheckBox1.setSelected(pos);
        if (pos) {
            this.setBackground(colorSelected);
        } else {
            System.out.println("unselect!");
            this.setBackground(colorUnselected);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        frameEscaner = new javax.swing.JFrame();
        panelEscaner = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        campoCodigoDeBarras = new javax.swing.JTextField();
        botonTerminarDeEscanear = new javax.swing.JButton();
        Informacion = new javax.swing.JFrame();
        jPanel2 = new javax.swing.JPanel();
        jLabelFolio = new javax.swing.JLabel();
        infoFolioDHL = new javax.swing.JLabel();
        infoCliente = new javax.swing.JLabel();
        infoSucursal = new javax.swing.JLabel();
        infoDivision = new javax.swing.JLabel();
        infoFolioAbbott = new javax.swing.JTextField();
        infoFolioCliente = new javax.swing.JTextField();
        infoFactura = new javax.swing.JTextField();
        infoMotivo = new javax.swing.JLabel();
        infoCodigoRazon = new javax.swing.JLabel();
        infoObservaciones = new javax.swing.JLabel();
        infoCajas = new javax.swing.JLabel();
        infoTransporte = new javax.swing.JLabel();
        infoTarima = new javax.swing.JLabel();
        infoAlmacen = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        infoDestino = new javax.swing.JLabel();
        infoEstado = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        panelOpciones4 = new javax.swing.JPanel();
        panelCambioStatus = new javax.swing.JPanel();
        infoBotonEstatus = new javax.swing.JLabel();
        panelEditar = new javax.swing.JPanel();
        infoBotonEditar = new javax.swing.JLabel();
        panelImprimir = new javax.swing.JPanel();
        botonImprimir = new javax.swing.JLabel();
        panelAddItem = new javax.swing.JPanel();
        botonAgregar = new javax.swing.JLabel();
        panelRemoveItem = new javax.swing.JPanel();
        botonEliminar = new javax.swing.JLabel();
        panelTarima = new javax.swing.JPanel();
        botonTarima = new javax.swing.JLabel();
        panelFecha1 = new javax.swing.JFormattedTextField();
        panelFecha = new javax.swing.JFormattedTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        labelMonto1 = new javax.swing.JLabel();
        labelCantidadTota1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        scrollPanel2 = new javax.swing.JScrollPane();
        labelFolio = new javax.swing.JLabel();
        labelEstado = new javax.swing.JLabel();
        labelCliente = new javax.swing.JLabel();
        labelSucursal = new javax.swing.JLabel();
        labelFecha = new javax.swing.JLabel();
        botonVerMas = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();

        jFileChooser1.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        jFileChooser1.setSelectedFile(new java.io.File("/" + folio + ".pdf"));

        frameEscaner.setAlwaysOnTop(true);
        frameEscaner.setMinimumSize(new java.awt.Dimension(220, 220));
        frameEscaner.setSize(new java.awt.Dimension(220, 260));

        panelEscaner.setBackground(new java.awt.Color(250, 250, 250));
        panelEscaner.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(250, 250, 250), 5, true));

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/barcode.png"))); // NOI18N

        jLabel30.setText("<html>Escanea el código de barras del producto</html>");

        campoCodigoDeBarras.setFont(new java.awt.Font("Courier", 0, 18)); // NOI18N
        campoCodigoDeBarras.setForeground(new java.awt.Color(53, 53, 53));
        campoCodigoDeBarras.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoCodigoDeBarras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoCodigoDeBarrasKeyTyped(evt);
            }
        });

        botonTerminarDeEscanear.setText("Dejar de capturar");
        botonTerminarDeEscanear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTerminarDeEscanearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEscanerLayout = new javax.swing.GroupLayout(panelEscaner);
        panelEscaner.setLayout(panelEscanerLayout);
        panelEscanerLayout.setHorizontalGroup(
            panelEscanerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEscanerLayout.createSequentialGroup()
                .addGroup(panelEscanerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEscanerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelEscanerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoCodigoDeBarras)
                            .addGroup(panelEscanerLayout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(botonTerminarDeEscanear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelEscanerLayout.setVerticalGroup(
            panelEscanerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEscanerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEscanerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoCodigoDeBarras, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonTerminarDeEscanear, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addGap(47, 47, 47))
        );

        javax.swing.GroupLayout frameEscanerLayout = new javax.swing.GroupLayout(frameEscaner.getContentPane());
        frameEscaner.getContentPane().setLayout(frameEscanerLayout);
        frameEscanerLayout.setHorizontalGroup(
            frameEscanerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelEscaner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        frameEscanerLayout.setVerticalGroup(
            frameEscanerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelEscaner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Informacion.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        Informacion.setTitle("Devolucion");
        Informacion.setMinimumSize(new java.awt.Dimension(1097, 620));
        Informacion.setSize(new java.awt.Dimension(1097, 620));

        jPanel2.setBackground(new java.awt.Color(250, 250, 250));

        jLabelFolio.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        jLabelFolio.setText("Folio DHL");

        infoFolioDHL.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        infoFolioDHL.setForeground(new java.awt.Color(9, 91, 173));
        infoFolioDHL.setText("0000");

        infoCliente.setFont(new java.awt.Font("Lucida Grande", 0, 20)); // NOI18N
        infoCliente.setText("Cliente");

        infoSucursal.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        infoSucursal.setForeground(new java.awt.Color(51, 51, 51));
        infoSucursal.setText("Sucursal, clave");

        infoDivision.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        infoDivision.setForeground(new java.awt.Color(51, 51, 51));
        infoDivision.setText("División");

        infoFolioAbbott.setEditable(false);

        infoFolioCliente.setEditable(false);

        infoFactura.setEditable(false);

        infoMotivo.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        infoMotivo.setForeground(new java.awt.Color(51, 51, 51));
        infoMotivo.setText("Motivo");

        infoCodigoRazon.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        infoCodigoRazon.setForeground(new java.awt.Color(51, 51, 51));
        infoCodigoRazon.setText("Código de razón");

        infoObservaciones.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        infoObservaciones.setForeground(new java.awt.Color(51, 51, 51));
        infoObservaciones.setText("Observaciones");

        infoCajas.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        infoCajas.setForeground(new java.awt.Color(51, 51, 51));
        infoCajas.setText("Caja");

        infoTransporte.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        infoTransporte.setForeground(new java.awt.Color(51, 51, 51));
        infoTransporte.setText("Transporte");

        infoTarima.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        infoTarima.setForeground(new java.awt.Color(51, 51, 51));
        infoTarima.setText("Tarima");

        infoAlmacen.setEditable(false);
        infoAlmacen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                infoAlmacenMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(infoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(infoSucursal, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(infoFolioAbbott)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabelFolio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(infoFolioDHL, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(infoDivision, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(infoFolioCliente)
                    .addComponent(infoFactura)
                    .addComponent(infoMotivo, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(infoCodigoRazon, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(infoObservaciones, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(infoCajas, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(infoTransporte, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(infoTarima, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(infoAlmacen, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelFolio)
                    .addComponent(infoFolioDHL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(infoCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(infoSucursal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(infoDivision)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(infoFolioAbbott, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoFolioCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoMotivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(infoCodigoRazon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(infoObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoCajas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(infoTransporte)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(infoTarima)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(infoAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(250, 250, 250));

        infoDestino.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoDestino.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/noapto.png"))); // NOI18N
        infoDestino.setText("<html><p align=\"center\">Producto no apto</html>");
        infoDestino.setToolTipText("Haz clic para cambiar de apto a No apto");
        infoDestino.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        infoDestino.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        infoDestino.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        infoDestino.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                infoDestinoMouseClicked(evt);
            }
        });

        infoEstado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/devoluciongrey.png"))); // NOI18N
        infoEstado.setText("Estado");
        infoEstado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        infoEstado.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jButton1.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jButton1.setText("Actualizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(infoEstado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(infoDestino, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(infoEstado)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(250, 250, 250));

        panelOpciones4.setBackground(new java.awt.Color(250, 250, 250));
        panelOpciones4.setLayout(new java.awt.GridLayout(1, 4, 5, 3));

        panelCambioStatus.setBackground(new java.awt.Color(9, 91, 173));

        infoBotonEstatus.setForeground(new java.awt.Color(250, 250, 250));
        infoBotonEstatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoBotonEstatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/minirefresh.png"))); // NOI18N
        infoBotonEstatus.setText("Cambiar estado");
        infoBotonEstatus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        infoBotonEstatus.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        infoBotonEstatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                infoBotonEstatusMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                infoBotonEstatusMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                infoBotonEstatusMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panelCambioStatusLayout = new javax.swing.GroupLayout(panelCambioStatus);
        panelCambioStatus.setLayout(panelCambioStatusLayout);
        panelCambioStatusLayout.setHorizontalGroup(
            panelCambioStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCambioStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoBotonEstatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelCambioStatusLayout.setVerticalGroup(
            panelCambioStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCambioStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoBotonEstatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelOpciones4.add(panelCambioStatus);

        panelEditar.setBackground(new java.awt.Color(9, 91, 173));

        infoBotonEditar.setForeground(new java.awt.Color(250, 250, 250));
        infoBotonEditar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoBotonEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/miniedit.png"))); // NOI18N
        infoBotonEditar.setText("<html>Editar</html>");
        infoBotonEditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        infoBotonEditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        infoBotonEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                infoBotonEditarMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                infoBotonEditarMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                infoBotonEditarMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panelEditarLayout = new javax.swing.GroupLayout(panelEditar);
        panelEditar.setLayout(panelEditarLayout);
        panelEditarLayout.setHorizontalGroup(
            panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoBotonEditar)
                .addContainerGap())
        );
        panelEditarLayout.setVerticalGroup(
            panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoBotonEditar)
                .addContainerGap())
        );

        panelOpciones4.add(panelEditar);

        panelImprimir.setBackground(new java.awt.Color(9, 91, 173));

        botonImprimir.setForeground(new java.awt.Color(250, 250, 250));
        botonImprimir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        botonImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/miniprint.png"))); // NOI18N
        botonImprimir.setText("Imprimir");
        botonImprimir.setToolTipText("");
        botonImprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonImprimir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonImprimir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonImprimirMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonImprimirMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonImprimirMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panelImprimirLayout = new javax.swing.GroupLayout(panelImprimir);
        panelImprimir.setLayout(panelImprimirLayout);
        panelImprimirLayout.setHorizontalGroup(
            panelImprimirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImprimirLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelImprimirLayout.setVerticalGroup(
            panelImprimirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImprimirLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelOpciones4.add(panelImprimir);

        panelAddItem.setBackground(new java.awt.Color(9, 91, 173));

        botonAgregar.setForeground(new java.awt.Color(250, 250, 250));
        botonAgregar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        botonAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/miniadd.png"))); // NOI18N
        botonAgregar.setText("Agregar");
        botonAgregar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonAgregar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonAgregarMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonAgregarMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonAgregarMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panelAddItemLayout = new javax.swing.GroupLayout(panelAddItem);
        panelAddItem.setLayout(panelAddItemLayout);
        panelAddItemLayout.setHorizontalGroup(
            panelAddItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddItemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelAddItemLayout.setVerticalGroup(
            panelAddItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddItemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelOpciones4.add(panelAddItem);

        panelRemoveItem.setBackground(new java.awt.Color(220, 102, 102));

        botonEliminar.setForeground(new java.awt.Color(250, 250, 250));
        botonEliminar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        botonEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/minidelete.png"))); // NOI18N
        botonEliminar.setText("Eliminar");
        botonEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonEliminarMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonEliminarMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonEliminarMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panelRemoveItemLayout = new javax.swing.GroupLayout(panelRemoveItem);
        panelRemoveItem.setLayout(panelRemoveItemLayout);
        panelRemoveItemLayout.setHorizontalGroup(
            panelRemoveItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRemoveItemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelRemoveItemLayout.setVerticalGroup(
            panelRemoveItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRemoveItemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelOpciones4.add(panelRemoveItem);

        panelTarima.setBackground(new java.awt.Color(9, 91, 173));

        botonTarima.setForeground(new java.awt.Color(250, 250, 250));
        botonTarima.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        botonTarima.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/minitarima.png"))); // NOI18N
        botonTarima.setText("<html>Cambiar tarima</html>");
        botonTarima.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonTarima.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonTarima.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonTarimaMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonTarimaMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonTarimaMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panelTarimaLayout = new javax.swing.GroupLayout(panelTarima);
        panelTarima.setLayout(panelTarimaLayout);
        panelTarimaLayout.setHorizontalGroup(
            panelTarimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTarimaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonTarima, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTarimaLayout.setVerticalGroup(
            panelTarimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTarimaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonTarima)
                .addContainerGap())
        );

        if (flagPanelDestino){

            panelOpciones4.add(panelTarima);
        }

        panelFecha1.setEditable(false);
        panelFecha1.setBackground(new java.awt.Color(250, 250, 250));
        panelFecha1.setBorder(null);
        panelFecha1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL))));
        panelFecha1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panelFecha1.setFocusable(false);
        panelFecha1.setFont(new java.awt.Font("Lucida Grande", 0, 20)); // NOI18N

        panelFecha.setEditable(false);
        panelFecha.setBackground(new java.awt.Color(250, 250, 250));
        panelFecha.setBorder(null);
        panelFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL))));
        panelFecha.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panelFecha.setFocusable(false);
        panelFecha.setFont(new java.awt.Font("Lucida Grande", 0, 20)); // NOI18N
        panelFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                panelFechaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(panelFecha1)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(panelFecha)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(panelOpciones4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(panelOpciones4, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(250, 250, 250));

        jPanel5.setBackground(new java.awt.Color(250, 250, 250));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(250, 250, 250), 5, true));

        labelMonto1.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        labelMonto1.setForeground(new java.awt.Color(9, 91, 173));
        labelMonto1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelMonto1.setText("    ");

        labelCantidadTota1.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        labelCantidadTota1.setForeground(new java.awt.Color(9, 91, 173));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelCantidadTota1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelMonto1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelMonto1)
                        .addComponent(labelCantidadTota1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        scrollPanel2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel2.setAutoscrolls(true);
        scrollPanel2.getHorizontalScrollBar().setUnitIncrement(20);
        scrollPanel2.getVerticalScrollBar().setUnitIncrement(20);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(scrollPanel2)))
                .addGap(10, 10, 10))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(scrollPanel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout InformacionLayout = new javax.swing.GroupLayout(Informacion.getContentPane());
        Informacion.getContentPane().setLayout(InformacionLayout);
        InformacionLayout.setHorizontalGroup(
            InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InformacionLayout.createSequentialGroup()
                .addGroup(InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        InformacionLayout.setVerticalGroup(
            InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InformacionLayout.createSequentialGroup()
                .addGroup(InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(InformacionLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, InformacionLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        setMaximumSize(new java.awt.Dimension(800, 80));
        setPreferredSize(new java.awt.Dimension(800, 80));
        setSize(new java.awt.Dimension(800, 80));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        labelFolio.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        labelFolio.setForeground(new java.awt.Color(9, 92, 173));
        labelFolio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelFolio.setText("000000");

        labelEstado.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        labelEstado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/devoluciongrey.png"))); // NOI18N
        labelEstado.setText("Estado");
        labelEstado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelEstado.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        labelCliente.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        labelCliente.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelCliente.setText("Cliente");

        labelSucursal.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        labelSucursal.setForeground(new java.awt.Color(102, 102, 102));
        labelSucursal.setText("Sucursal");

        labelFecha.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        labelFecha.setText("00/00/2015");

        botonVerMas.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        botonVerMas.setText("Ver más");
        botonVerMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVerMasActionPerformed(evt);
            }
        });

        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFolio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelSucursal, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelEstado)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(botonVerMas, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addComponent(labelFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelSucursal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(labelFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonVerMas))
                    .addComponent(labelFolio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        setjCheckBox1(checked);
        checked = !checked;
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        setjCheckBox1(checked);
        checked = !checked;
    }//GEN-LAST:event_formMouseClicked

    private void botonVerMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVerMasActionPerformed
        Informacion.setVisible(true);
        setInformation(fjs.getFolio());
    }//GEN-LAST:event_botonVerMasActionPerformed

    private void infoDestinoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoDestinoMouseClicked

    }//GEN-LAST:event_infoDestinoMouseClicked

    private void panelFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_panelFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_panelFechaActionPerformed

    private void campoCodigoDeBarrasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoCodigoDeBarrasKeyTyped
        char c = evt.getKeyChar();
        if (c == '\n') {
            if (checarCodigoDeBarras(campoCodigoDeBarras.getText(), 1)) {
                rows++;
            }
            campoCodigoDeBarras.setText(null);
        }
        if (!Character.isDigit(c)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_campoCodigoDeBarrasKeyTyped

    private void botonTerminarDeEscanearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTerminarDeEscanearActionPerformed
        frameEscaner.setVisible(false);
    }//GEN-LAST:event_botonTerminarDeEscanearActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DatabaseConnection db = new DatabaseConnection();
        try {
            Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement("UPDATE Descripcion_devolucion SET fiolioAbbot = ?, folioCliente = ?,"
                    + "factura = ? where folio = ?;");
            stmt.setString(1, infoFolioAbbott.getText());
            stmt.setString(2, infoFolioCliente.getText());
            stmt.setString(3, infoFactura.getText());
            stmt.setInt(4, folio);
            stmt.executeUpdate();
            Log.print("Editó la devolución " + String.format("%06d", folio));
            if (JOptionPane.showConfirmDialog(null, "¿Desea modificar los productos?") == JOptionPane.YES_OPTION) {
                if (!devolucionProductos.isEmpty()) {
                    if (!flagPanelDestino) { //productos en destrucción 
                        PreparedStatement st1 = con.prepareStatement("select idDevolucion_has_Instancia_Producto from devolucion_has_instancia_producto where Devolucion_folio = ?");
                        st1.setInt(1, folio);
                        ResultSet rs = st1.executeQuery();
                        while (rs.next()) {
                            PreparedStatement st2 = con.prepareStatement("DELETE FROM devolucion_has_instancia_producto_destruccion where idDevolucion_has_instancia_Producto = ?;");
                            st2.setInt(1, rs.getInt("idDevolucion_has_Instancia_Producto"));
                            st2.executeUpdate();
                        }
                    }
                    PreparedStatement stmt2 = con.prepareStatement("DELETE FROM devolucion_has_instancia_producto where Devolucion_folio = ?;");
                    stmt2.setInt(1, folio);
                    stmt2.executeUpdate();
                    devolucionProductos.cerrarCaptura();
                
                JOptionPane.showMessageDialog(null, "Guardado!");
                Log.print("Modificó los productos de la devolución " + String.format("%06d", folio));
                }else{
                    JOptionPane.showMessageDialog(null, "No se hará ningún cambio, ya que no puede guardar una devolución como vacía");
                    Log.print("No pudo guardar los cambios de la devolución " + String.format("%06d", folio) + "porque la iba a guardar como vacía");
                }
            }
            
            Informacion.dispose();
        } catch (SQLException ex) {
            Log.print(ex);
            JOptionPane.showMessageDialog(null, "Error al guardar: SQLException-" + ex.getErrorCode());
            
        }
        db.closeConnection();
        //eliminar todos los productos de esta devolucion
        //Lista de productos
        //agregar todo lo que hay en esta tabla
        created = false;
        crearCuadricula();
        Informacion.revalidate();
        Informacion.repaint();
        Informacion.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void infoBotonEstatusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoBotonEstatusMouseClicked

        cambiarEstado();
        Log.print("Usuario cambió estatus de folio " + folio + " al estado " + status);
        VerDevoluciones.updateStatus(status, true);
    }//GEN-LAST:event_infoBotonEstatusMouseClicked

    private void infoBotonEstatusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoBotonEstatusMouseExited
        panelCambioStatus.setBackground(dic);
        infoBotonEstatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/minirefresh.png")));
        infoBotonEstatus.setForeground(colorUnselected);
    }//GEN-LAST:event_infoBotonEstatusMouseExited

    private void infoBotonEstatusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoBotonEstatusMouseEntered
        panelCambioStatus.setBackground(colorUnselected);
        infoBotonEstatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/minirefresh_inv.png")));
        infoBotonEstatus.setForeground(dic);
    }//GEN-LAST:event_infoBotonEstatusMouseEntered

    private void infoBotonEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoBotonEditarMouseClicked
        if (fjs.getStatus().getCodigo() < 5) {
            if (!isEditable) {
                editable = new boolean[]{false, false, false, true, true, true, false};
                infoFolioAbbott.setEditable(true);
                infoFolioCliente.setEditable(true);
                infoFactura.setEditable(true);
                model.setEditables(editable);
                isEditable = true;
                JOptionPane.showMessageDialog(null, "Ahora puede editar esta devolución");
                panelEditar.setBackground(colorUnselected);
                infoBotonEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/miniedit_inv.png")));
                infoBotonEditar.setForeground(dic);
            } else {
                JOptionPane.showMessageDialog(null, "Usted ya puede editar esta devolución");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No puede editar una devolución en otro estado que no sea Segregación o Dictamen");
        }
        //fill();
    }//GEN-LAST:event_infoBotonEditarMouseClicked

    private void infoBotonEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoBotonEditarMouseExited

        if (isEditable) {
            panelEditar.setBackground(colorUnselected);
            infoBotonEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/miniedit_inv.png")));
            infoBotonEditar.setForeground(dic);
        } else {
            panelEditar.setBackground(dic);
            infoBotonEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/miniedit.png")));
            infoBotonEditar.setForeground(colorUnselected);
        }
    }//GEN-LAST:event_infoBotonEditarMouseExited

    private void infoBotonEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoBotonEditarMouseEntered
        panelEditar.setBackground(colorUnselected);
        infoBotonEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/miniedit_inv.png")));
        infoBotonEditar.setForeground(dic);
    }//GEN-LAST:event_infoBotonEditarMouseEntered

    private void botonImprimirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonImprimirMouseClicked
        imprimir();
    }//GEN-LAST:event_botonImprimirMouseClicked

    private void botonImprimirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonImprimirMouseExited
        panelImprimir.setBackground(dic);
        botonImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/miniprint.png")));
        botonImprimir.setForeground(colorUnselected);
    }//GEN-LAST:event_botonImprimirMouseExited

    private void botonImprimirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonImprimirMouseEntered
        panelImprimir.setBackground(colorUnselected);
        botonImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/miniprint_inv.png")));
        botonImprimir.setForeground(dic);
    }//GEN-LAST:event_botonImprimirMouseEntered

    private void botonAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonAgregarMouseClicked
        if (!flagPanelDestino) {
            cambiarTarima();
        }
        frameEscaner.setVisible(true);
    }//GEN-LAST:event_botonAgregarMouseClicked

    private void botonAgregarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonAgregarMouseExited
        panelAddItem.setBackground(dic);
        botonAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/miniadd.png")));
        botonAgregar.setForeground(colorUnselected);
    }//GEN-LAST:event_botonAgregarMouseExited

    private void botonAgregarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonAgregarMouseEntered
        panelAddItem.setBackground(colorUnselected);
        botonAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/miniadd_inv.png")));
        botonAgregar.setForeground(dic);
    }//GEN-LAST:event_botonAgregarMouseEntered

    private void botonEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonEliminarMouseClicked
        int value = JOptionPane.showConfirmDialog(Informacion,
                "¿Está seguro de querer eliminar este\n"
                + "dato definitivamente? ",
                "Eliminar datos",
                JOptionPane.YES_NO_OPTION);
        if (value == JOptionPane.YES_OPTION) {
            int[] rowsSelected = model.getTabla().getSelectedRows();
            for (int i : rowsSelected) {

                model.removeRow(i);
            }
        }
    }//GEN-LAST:event_botonEliminarMouseClicked

    private void botonEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonEliminarMouseExited
        panelRemoveItem.setBackground(dev);
        botonEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/minidelete.png")));
        botonEliminar.setForeground(colorUnselected);
    }//GEN-LAST:event_botonEliminarMouseExited

    private void botonEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonEliminarMouseEntered
        panelRemoveItem.setBackground(colorUnselected);
        botonEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/minidelete_inv.png")));
        botonEliminar.setForeground(dev);
    }//GEN-LAST:event_botonEliminarMouseEntered

    private void botonTarimaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonTarimaMouseClicked
        cambiarTarima();
    }//GEN-LAST:event_botonTarimaMouseClicked

    private void botonTarimaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonTarimaMouseExited
        // TODO add your handling code here:
        panelTarima.setBackground(dic);
        botonTarima.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/minitarima.png")));
        botonTarima.setForeground(colorUnselected);
    }//GEN-LAST:event_botonTarimaMouseExited

    private void botonTarimaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonTarimaMouseEntered
        // TODO add your handling code here:
        panelTarima.setBackground(colorUnselected);
        botonTarima.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/minitarima_inv.png")));
        botonTarima.setForeground(dic);
    }//GEN-LAST:event_botonTarimaMouseEntered

    private void infoAlmacenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoAlmacenMouseClicked
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Para editar el almacén, entre a la configuración de la tarima correspondiente.");
    }//GEN-LAST:event_infoAlmacenMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame Informacion;
    private javax.swing.JLabel botonAgregar;
    private javax.swing.JLabel botonEliminar;
    private javax.swing.JLabel botonImprimir;
    private javax.swing.JLabel botonTarima;
    private javax.swing.JButton botonTerminarDeEscanear;
    private javax.swing.JButton botonVerMas;
    private javax.swing.JTextField campoCodigoDeBarras;
    private javax.swing.JFrame frameEscaner;
    private javax.swing.JTextField infoAlmacen;
    private javax.swing.JLabel infoBotonEditar;
    private javax.swing.JLabel infoBotonEstatus;
    private javax.swing.JLabel infoCajas;
    private javax.swing.JLabel infoCliente;
    private javax.swing.JLabel infoCodigoRazon;
    private javax.swing.JLabel infoDestino;
    private javax.swing.JLabel infoDivision;
    private javax.swing.JLabel infoEstado;
    private javax.swing.JTextField infoFactura;
    private javax.swing.JTextField infoFolioAbbott;
    private javax.swing.JTextField infoFolioCliente;
    private javax.swing.JLabel infoFolioDHL;
    private javax.swing.JLabel infoMotivo;
    private javax.swing.JLabel infoObservaciones;
    private javax.swing.JLabel infoSucursal;
    private javax.swing.JLabel infoTarima;
    private javax.swing.JLabel infoTransporte;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabelFolio;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel labelCantidadTota1;
    private javax.swing.JLabel labelCliente;
    private javax.swing.JLabel labelEstado;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JLabel labelFolio;
    private javax.swing.JLabel labelMonto1;
    private javax.swing.JLabel labelSucursal;
    private javax.swing.JPanel panelAddItem;
    private javax.swing.JPanel panelCambioStatus;
    private javax.swing.JPanel panelEditar;
    private javax.swing.JPanel panelEscaner;
    private javax.swing.JFormattedTextField panelFecha;
    private javax.swing.JFormattedTextField panelFecha1;
    private javax.swing.JPanel panelImprimir;
    private javax.swing.JPanel panelOpciones4;
    private javax.swing.JPanel panelRemoveItem;
    private javax.swing.JPanel panelTarima;
    private javax.swing.JScrollPane scrollPanel2;
    // End of variables declaration//GEN-END:variables
    private Object[][] datos;
    private int rows;
    private Tarima tarima;

    public Date getFecha() {
        return fecha;
    }

    private void setInformation(int f) {
        crearCuadricula();
        infoFolioDHL.setText(String.format("%06d", folio));
        infoCliente.setText(fjs.getCliente().getNombre());
        infoSucursal.setText(fjs.getSucursal().getNombre() + ", " + fjs.getSucursal().getCodigoSucursal());
        infoDivision.setText("División: " + fjs.getFamilia().getCodigoFamilia());
        infoFolioAbbott.setText(fjs.getDescripcionDevolucion().getFolioAbbot());
        infoFolioCliente.setText(fjs.getDescripcionDevolucion().getDocumentoCliente());
        infoFactura.setText(fjs.getDescripcionDevolucion().getFactura());
        infoMotivo.setText("Motivo: " + fjs.getMotivo().getDescripcion());
        infoCodigoRazon.setText("Código de razón: " + fjs.getSubDivision().getSubDivision());
        infoObservaciones.setText("Observaciones: " + fjs.getDescripcionDevolucion().getObservaciones());
        infoCajas.setText("" + fjs.getDescripcionDevolucion().getCajas() + " cajas");
        infoTransporte.setText("Transporte: " + fjs.getTransporte().getDescripcion());
        try {
            infoTarima.setText("Tarimas: ");
            for (Tarima tar : fjs.getTarimas()) {
                String tarimas = infoTarima.getText();
                tarimas = tarimas.concat(tar.getNombre() + ", ");
                infoTarima.setText(tarimas);
            }
        } catch (Exception e) {
            Log.print(e);
            e.getMessage();
            infoTarima.setText("Sin tarimas");
        }
        try {
            infoAlmacen.setText(fjs.getTarimas()[0].getAlmacen_cv());
        } catch (Exception e) {
            Log.print(e);
            infoAlmacen.setText("CV1");
        }

        if (flagPanelDestino) {
            panelTarima.setVisible(false);
        }
        infoEstado.setIcon(iconos.get(fjs.getStatus().getCodigo()));
        infoEstado.setText(estaditos.get(fjs.getStatus().getCodigo()));
        infoEstado.setForeground(colores.get(fjs.getStatus().getCodigo()));
        panelFecha.setText("Capturado el " + Funciones.getFechaCompleta(fjs.getDescripcionDevolucion().getFechaCaptura()));
        panelFecha1.setText("Recibido el " + Funciones.getFechaCompleta(fjs.getDescripcionDevolucion().getFechaDevolucion()));

        PromptSupport.setPrompt("Folio Abbott", infoFolioAbbott);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, infoFolioAbbott);
        PromptSupport.setPrompt("Folio Cliente", infoFolioCliente);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, infoFolioCliente);
        PromptSupport.setPrompt("Factura", infoFactura);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, infoFactura);
        PromptSupport.setPrompt("Almacén", infoAlmacen);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, infoAlmacen);

        fill();
        if (flagPanelDestino) {
            infoDestino.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/apto.png"))); // NOI18N
            infoDestino.setText("<html><p align=\"center\">Producto apto</html>");
        }
    }

    private void fill() {
        if (flagPanelDestino) {
            model = new DevoxTableModel(DevoxTableModel.columnsApto, editable);
        } else {
            //Destrucción
            model = new DevoxTableModel(DevoxTableModel.columnsDestruccion, editable);
        }
        devolucionProductos = new OptionPanels(model, fjs);
        for (int i = 0; i < fjs.getProductos().length; i++) {
            Producto producto = new Producto(fjs, i);
            producto.setWithSbc(false);
            devolucionProductos.agregarProducto(producto);
        }
        rows = model.getRowCount();
        actualizarMontoTotal();
        model.agregarTabla(scrollPanel2);
        //model.llenarDeDatos(datos);

        model.addTableModelListener((TableModelEvent e) -> {
            
            if (e.getType() == TableModelEvent.INSERT) {
                //añadió una nueva fila
                actualizarMontoTotal();
            }
            if (e.getType() == TableModelEvent.UPDATE) {
                //cambió datos de una fila
                int r = e.getFirstRow();
                int c = e.getColumn();
                if (c == 5) {
                    //float pre = (devolucionProductos.getProducto(r).getPrecioSeleccionado());
                    devolucionProductos.getProducto(r).setCantidad(((Integer) model.getValueAt(r, 5)));
                    model.setValueAt(devolucionProductos.getProducto(r).getMonto(), r, 6);
                }

                if (c == 6) {
                    actualizarMontoTotal();
                }

                if (c == 4) {
                    
                    float newPrice = (float) model.getValueAt(r, 4);
                    Log.print("precio modificado a: " + newPrice);
                    String ean = model.getValueAt(r, 0).toString();
                    String idProducto = model.getValueAt(r, 1).toString();
                    int change = JOptionPane.NO_OPTION;
                    change = JOptionPane.showConfirmDialog(
                            null,
                            "¿Desea cambiar este precio en la Base de Datos?",
                            "Cambio de precio",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (change == JOptionPane.YES_OPTION) {
                        changePrice(newPrice, ean, idProducto);
                    }

                    devolucionProductos.getProducto(r).setPrecioSeleccionado(newPrice);
                    devolucionProductos.getProducto(r).setCantidad(((Integer) model.getValueAt(r, 5)));
                    model.setValueAt(((Integer) model.getValueAt(r, 5) * (float) model.getValueAt(r, 4)), r, 6);
                    actualizarMontoTotal();
                }
            }
            if (e.getType() == TableModelEvent.DELETE) {
                //borró filas de la tabla
                devolucionProductos.eliminarProducto(e.getFirstRow());
                actualizarMontoTotal();
            }

        });

    }

    private void setUpInformation() {
        try {
            if (!vacia) {
                fecha = fjs.getDescripcionDevolucion().getFechaCaptura();
                labelFolio.setText(String.valueOf(folio));
                labelFecha.setText(Funciones.getFechaCompleta(fecha));
                labelCliente.setText(Funciones.setText(fjs.getCliente().getNombre(), Funciones.ALIGN_LEFT));
                labelCliente.setToolTipText("Cliente");
                labelSucursal.setText(Funciones.setText(fjs.getSucursal().getNombre(), Funciones.ALIGN_LEFT));
                labelSucursal.setToolTipText("Sucursal");
                labelEstado.setIcon(iconos.get(fjs.getStatus().getCodigo()));
                labelEstado.setText(estaditos.get(fjs.getStatus().getCodigo()));
                labelEstado.setForeground(colores.get(fjs.getStatus().getCodigo()));
            } else {
                fecha = fjs.getDescripcionDevolucion().getFechaCaptura();
                labelFolio.setText(String.valueOf(fjs.getFolio()));
                labelFecha.setText(Funciones.getFechaCompleta(fecha));
                labelCliente.setText("Devolución vacía.");
                labelCliente.setToolTipText("Cliente");
                labelSucursal.setText(" - - - ");
                labelSucursal.setToolTipText("Sucursal");
                labelEstado.setText("");
                labelEstado.setIcon(null);
            }
        } catch (NullPointerException ex) {
            Log.print("Problemas al crear el folio " + folio + ", pues está vacío.");
        }
    }

    void cambiarEstado() {
        int folioactual = fjs.getStatus().getCodigo();
        int nuevofolio = folioactual + 1;
        if (nuevofolio < 5) {
            AccionesDescripcionDevolucionEstado estado = new AccionesDescripcionDevolucionEstado();
            estado.setFolio(fjs.getFolio());
            estado.setIdEstado(nuevofolio);
            estado.alta();
        } else {
            JOptionPane.showMessageDialog(null, "No puede cambiar un folio con estado Entregado");
        }

        //Mostrar los nuevos cambios
        fjs = new FolioJoinSucursal(fjs.getFolio());
        fjs.loadData();
        labelEstado.setIcon(iconos.get(fjs.getStatus().getCodigo()));
        labelEstado.setText(estaditos.get(fjs.getStatus().getCodigo()));
        labelEstado.setForeground(colores.get(fjs.getStatus().getCodigo()));
        infoEstado.setIcon(iconos.get(fjs.getStatus().getCodigo()));
        infoEstado.setText(estaditos.get(fjs.getStatus().getCodigo()));
        infoEstado.setForeground(colores.get(fjs.getStatus().getCodigo()));
        status = nuevofolio;
    }

    public String getFolio() {
        return String.format("%06d", fjs.getFolio());
    }
    
    public int getFolioSimple() {
        return fjs.getFolio();
    }

    public String getDestino() {
        return (fjs.getTarimas().length == 0 ? "APTO" : "DESTRUCCION");
    }

    public String getEstado() {
        return fjs.getStatus().getNombre();
    }

    private void imprimir() {
        String f = String.format("%06d", folio);

        if (fjs.getTarimas().length == 0) {

            String fac = fjs.getDescripcionDevolucion().getFactura();
            String fc = fjs.getDescripcionDevolucion().getDocumentoCliente();
            String cs = fjs.getSucursal().getCodigoSucursal() + ", " + fjs.getCliente().getNombre() + " - " + fjs.getSucursal().getNombre();
            List<List<Object>> listadelistas = new ArrayList<>();
            for (int i = 0; i < model.getRowCount(); i++) {
                List<Object> l = new ArrayList<>();
                l.add(model.getValueAt(i, 1));
                l.add(model.getValueAt(i, 2));
                l.add(model.getValueAt(i, 3));
                l.add(model.getValueAt(i, 5));
                l.add(model.getValueAt(i, 4));
                listadelistas.add(l);
            }
            Object[][] dat = new Object[listadelistas.size()][];
            for (int i = 0; i < listadelistas.size(); i++) {
                dat[i] = listadelistas.get(i).toArray();
            }

            CrearReporteApto pdf = new CrearReporteApto(f, fac, fc, cs, "" + fjs.getMotivo().getCodigo() + ", " + fjs.getMotivo().getDescripcion(), dat);
            pdf.crear(new File(new Configuraciones().getPath() + "/Folio_" + f + ".pdf"));
            JOptionPane.showMessageDialog(null, "Documento guardado!");
        } else {
            CrearReporteDestruccion pdf = new CrearReporteDestruccion(model, fjs, fjs.getSucursal().getFamilia_idFamilia());
            pdf.crear(new File(new Configuraciones().getPath() + "/Folio_" + f + ".pdf"));
            JOptionPane.showMessageDialog(null, "Documento guardado!");
        }
        Log.print("Guardado en PDF! pdf.crear(new File(\"/Reportes/Destruccion/Folio_" + f + ".pdf\"));");

    }

    public int getStatus() {
        return status;
    }

    private void cambiarTarima() {
        String nombre;
        int destfiscal;
        String almacen;
        Integer[] selectionValues = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        List<String> almacenes = new ArrayList<>();
        AccionesAlmacen aa = new AccionesAlmacen();
        aa.loadDatabaseInstances();
        for (Shareable s : aa.getEntityInstances()) {
            almacenes.add(((Almacen) s).getCv());
        }
        nombre = (String) JOptionPane.showInputDialog(null, "Nombre de la nueva tarima:", "Nueva tarima", JOptionPane.OK_OPTION, null, null, null);
        if (!nombre.isEmpty()) {
            Object df = JOptionPane.showInputDialog(
                    null,
                    "Folio de destrucción fiscal:",
                    "Nueva tarima",
                    JOptionPane.OK_OPTION,
                    null,
                    selectionValues,
                    selectionValues[0]
            );
            if (df != null) {
                destfiscal = (Integer) df;
                almacen = (String) JOptionPane.showInputDialog(null, "Almacén", "Nueva tarima", JOptionPane.OK_OPTION, null, almacenes.toArray(), almacenes.toArray()[0]);
                if (!almacen.equals("null")) {
                    AccionesTarima t = new AccionesTarima();
                    t.setNombre(nombre);
                    t.setAlmacen_cv(almacen);
                    t.setFolioDestruccionFiscal(destfiscal);
                    t.alta();
                    t.loadDatabaseInstances();
                    this.tarima = new Tarima();
                    this.tarima.setAlmacen_cv(almacen);
                    this.tarima.setNombre(nombre);
                    this.tarima.setIdTarima(t.grabIdTarimaByNombre(nombre));
                }
            }

        }

    }

    private boolean checarCodigoDeBarras(String CB, int cantidad) {
        com.devox.LOG.Admin.EscarnearCodigoDeBarras ecdb = new EscarnearCodigoDeBarras(CB, cantidad, flagPanelDestino, this.tarima, fjs.getSucursal().getCodigoSucursal());
        if (ecdb.isSuccess()) {
            com.devox.LOG.Entidades.Producto p = ecdb.getProducto();
            if (flagPanelDestino) {
                p.setIdTarima(this.tarima.getIdTarima());
                p.setTarimaNombre(this.tarima.getNombre());
                Log.print("tarima aquí: " + tarima.getNombre());
            }
            devolucionProductos.agregarProducto(p);
            return true;
        }
        return false;
    }

    private void actualizarMontoTotal() {
        montoTotal = 0;
        cantidadTotal = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            montoTotal += (float) model.getValueAt(i, 6);
            cantidadTotal += (Integer) model.getValueAt(i, 5);
        }
        labelMonto1.setText("$" + String.format("%.2f", montoTotal));
        labelCantidadTota1.setText(String.valueOf(cantidadTotal) + " productos");
    }

    public void changePrice(float precio, String ean, String idProducto) {
        Log.print("precio: $" + String.format("%.2f", precio));
        Log.print("EAN: " + ean);
        Log.print("Producto: " + idProducto);
        Log.print("Sucursal: " + fjs.getSucursal().getCodigoSucursal());

        AccionesHistorialPrecio ahp = new AccionesHistorialPrecio();
        ahp.loadDatabaseInstances();
        int x = -1;
        Shareable[] s = ahp.getEntityInstances();
        for (int i = 0; i < s.length; i++) {
            HistorialPrecio price = (HistorialPrecio) s[i];
            if (ean.equals(price.getEan())
                    && idProducto.equals(price.getIdProducto())
                    && fjs.getSucursal().getCodigoSucursal().equals(price.getIdSucursal())) {
                x = i;
                break;
            }
        }
        ahp.setPrecio(precio);
        ahp.setEan(ean);
        ahp.setIdProducto(idProducto);
        ahp.setIdSucursal(fjs.getSucursal().getCodigoSucursal());
        ahp.setPosition(x);
        ahp.cambio();
    }

    public String getStatusName() {
        return fjs.getStatus().getNombre();
    }

    @Override
    public int compareTo(CuadriculaLista cuadricula) {
        int compareFolio = ((CuadriculaLista) cuadricula).folio;
        return compareFolio - this.folio;
    }
    
}

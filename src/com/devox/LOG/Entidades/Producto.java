/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.LOG.Entidades;

import org.inspira.devox.logger.Log;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import org.inspira.devox.database.DatabaseConnection;
import org.inspira.devox.services.organico.AccionesTarima;
import org.inspira.devox.services.productos.AccionesLote;
import org.inspira.devox.services.productos.ScanBarCode;
import org.inspira.devox.services.queries.FolioJoinSucursal;
import org.inspira.devox.shared.Tarima;

/**
 *
 * @author azaraf
 */
public final class Producto {

    private String codigoBarras;
    private String idSucursal;
    private String codigoProducto;
    private String descripcion;
    private String loteSeleccionado;
    private Float precioSeleccionado;
    private int cantidad;
    private Float montoTotal;
    private int idTarima;
    private List<String> listaLotes;
    private List<Float> listaPrecios;
    private String tarimaNombre;
    private boolean destino;
    private boolean withSbc;

    private ScanBarCode sbc;

    private int posicion;

    public Producto(FolioJoinSucursal fjs, int i) {
        try {
            this.codigoBarras = fjs.getCodigoBarras()[i].getEan();
            this.codigoProducto = fjs.getProductos()[i].getCodigoLocal();
            this.descripcion = fjs.getProductos()[i].getDescripcion();
            this.idSucursal = fjs.getSucursal().getCodigoSucursal();
            this.loteSeleccionado = fjs.getLotes()[i].getCodigoLote();
            this.precioSeleccionado = fjs.getPrecios()[i];
            this.cantidad = fjs.getDevolucionHasInstanciaProducto()[i].getCantidad();
            updateMonto();
            this.destino = fjs.getTarimas().length == 0;
            if (!this.destino) {
                AccionesTarima t = new AccionesTarima();
                this.idTarima = t.grabIdTarimaByNombre(fjs.getDevolucionHasInstanciaProductoDestruccion()[i].getNombreTarima());
                this.tarimaNombre = fjs.getDevolucionHasInstanciaProductoDestruccion()[i].getNombreTarima();
            }
            this.withSbc = true;
        } catch (IndexOutOfBoundsException ex) {
            Log.print(ex);
            JOptionPane.showMessageDialog(null, "No se pueden mostrar los productos de esta devolución porque está vacía.", "Devolución sin productos", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Producto(ScanBarCode sbc, int cantidad, int pox) {
        this.destino = true;
        this.sbc = sbc;
        this.cantidad = cantidad;
        this.posicion = pox;
        setProducto();
        this.withSbc = true;
    }

    public Producto(ScanBarCode sbc, int cantidad, int pox, Tarima tarima) {
        this.destino = false;
        this.sbc = sbc;
        this.cantidad = cantidad;
        this.posicion = pox;
        setProducto();
        this.idTarima = tarima.getIdTarima();
        this.tarimaNombre = tarima.getNombre();
        this.withSbc = true;

    }

    private void setProducto() {
        codigoBarras = sbc.getBarcode();
        codigoProducto = sbc.getIdProducto()[posicion];
        descripcion = sbc.getDescProducto()[posicion];
        listaLotes = sbc.getIdsLote().get(posicion);
        idSucursal = sbc.getIdSucursal();
        listaPrecios = sbc.getPrecioProductos();
        precioSeleccionado = listaPrecios.get(posicion);
        loteSeleccionado = listaLotes.size() > 0 ? listaLotes.get(0) : sbc.getDefidLote();
        updateMonto();
    }

    public boolean equals(String codigoDeBarras, String codigoProducto) {
        return this.codigoBarras.equals(codigoDeBarras) && this.codigoProducto.equals(codigoProducto);
    }

    public void increment(int quantity) {
        cantidad += quantity;
        updateMonto();
    }

    public Object[] obtenerFila(boolean destino) {
        seleccionaLote();
        Object[] fila;
        if (destino) {//apto
            fila = new Object[]{
                codigoBarras,
                codigoProducto,
                descripcion,
                loteSeleccionado,
                precioSeleccionado,
                cantidad,
                montoTotal
            };
        } else {//destrucción
            fila = new Object[]{
                codigoBarras,
                codigoProducto,
                descripcion,
                loteSeleccionado,
                precioSeleccionado,
                cantidad,
                montoTotal,
                tarimaNombre
            };
        }
        return fila;
    }

    private void seleccionaLote() {
        if (withSbc) {
            sbc.scanProducto();
            listaLotes = sbc.getIdsLote().get(posicion);
            listaLotes.add("Agregar un nuevo lote");
            loteSeleccionado = (String) JOptionPane.showInputDialog(
                    null,
                    "Este código interno tiene más de un lote.\n"
                    + "Seleccione uno.",
                    "Seleccionar lote",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    listaLotes.toArray(new String[0]),
                    listaLotes.get(0)
            );
        }
        try {
            if (loteSeleccionado.equals("Agregar un nuevo lote")) {
                loteSeleccionado = JOptionPane.showInputDialog(null, "Agregue un nuevo lote", "LOTE");
                if (!loteSeleccionado.equals("null")) {
                    AccionesLote al = new AccionesLote();
                    al.setIdLote(loteSeleccionado);
                    al.setIdProducto(codigoProducto);
                    al.alta();
                }
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "No seleccionó ningún lote");
            Log.print("No seleccionó ningun lote. Exception " + e.getLocalizedMessage());
        }

    }

    public Float getMonto() {
        return montoTotal;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getLoteSeleccionado() {
        return loteSeleccionado;
    }

    public Float getPrecioSeleccionado() {
        Log.print("precio final de esta dev: $" + precioSeleccionado);
        return precioSeleccionado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setLoteSeleccionado(String loteSeleccionado) {
        this.loteSeleccionado = loteSeleccionado;
    }

    public void setPrecioSeleccionado(Float precioSeleccionado) {
        this.precioSeleccionado = precioSeleccionado;
        updateMonto();
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        updateMonto();

    }

    private void updateMonto() {
        montoTotal = (precioSeleccionado * cantidad);

    }

    public void setIdTarima(int idTarima) {
        this.idTarima = idTarima;
    }

    public void setTarimaNombre(String tarimaNombre) {
        this.tarimaNombre = tarimaNombre;
    }

    public String getTarimaNombre() {
        return tarimaNombre;
    }

    public int getIdTarima() {
        return idTarima;
    }

    public boolean isDestruccion() {
        return destino;
    }

    public String getAlmacen() {
        String almacen = "CV";
        DatabaseConnection db = new DatabaseConnection();
        try {
            ResultSet rs = db.getConnection().prepareStatement("select almacen_cv from tarima where NombreTarima = '" + tarimaNombre + "'").executeQuery();
            while (rs.next()) {
                almacen = rs.getString("almacen_cv");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Log.print(ex);
        }
        db.closeConnection();
        return almacen;
    }

    public boolean isWithSbc() {
        return withSbc;
    }

    public void setWithSbc(boolean withSbc) {
        this.withSbc = withSbc;
    }

}

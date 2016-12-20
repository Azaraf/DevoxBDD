/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.LOG.Admin;

import com.devox.GUI.tables.DevoxTableModel;
import com.devox.LOG.Entidades.ContenidoDevolucion;
import com.devox.LOG.Entidades.Producto;
import org.inspira.devox.logger.Log;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.inspira.devox.services.queries.FolioJoinSucursal;
import org.inspira.devox.services.relaciones.AccionesDevolucionHasInstanciaProducto;

/**
 *
 * @author azaraf
 */
public class OptionPanels {

    private DevoxTableModel tabla;
    private List<Producto> listaProductos = new ArrayList<>();
    private boolean destino;
    private int folio;
    private String sucursal;

    public OptionPanels() {
        this.destino = false;
    }

    public OptionPanels(ContenidoDevolucion devolucion, DevoxTableModel tabla) {
        this.tabla = tabla;
        this.destino = devolucion.getDestino();
        this.folio = devolucion.getFolio();
        this.sucursal = devolucion.getSucursal().getCodigoSucursal();
    }

    public OptionPanels(DevoxTableModel tabla, FolioJoinSucursal fjs) {
        this.tabla = tabla;
        this.destino = fjs.isDestino();
        this.folio = fjs.getFolio();
        this.sucursal = fjs.getSucursal().getCodigoSucursal();
    }

    public void agregarProducto(Producto producto) {
        if (sumarProducto(
                producto.getCodigoBarras(),
                producto.getCodigoProducto(),
                producto.getCantidad()
        )) {//el producto nuevo ya está en la lista
        } else {
            listaProductos.add(producto);
            tabla.addRow(producto.obtenerFila(destino));
        }
    }

    public void eliminarProducto(int index) {
        listaProductos.remove(index);
    }

    public boolean sumarProducto(String codigoDeBarras, String codigoProducto, int cantidad) {
        if (listaProductos.size() > 0) {
            for (int i = listaProductos.size() - 1; i >= 0; i--) {
                if (listaProductos.get(i).equals(codigoDeBarras, codigoProducto)) {
                    listaProductos.get(i).increment(cantidad);
                    tabla.setValueAt(listaProductos.get(i).getCantidad(), i, 5);
                    Log.print("suma: Producto: " + listaProductos.get(i).getCodigoBarras() + " " + listaProductos.get(i).getCodigoProducto()
                            + ", lote " + listaProductos.get(i).getLoteSeleccionado()
                            + ", Cantidad " + listaProductos.get(i).getCantidad()
                            + ", Monto total: " + listaProductos.get(i).getMonto());
                    tabla.setValueAt(listaProductos.get(i).getMonto(), i, 6);

                    return true;
                }
            }
        }
        return false;
    }

    public Producto getProducto(int index) {
        return listaProductos.get(index);
    }

    public void editar(int index, String loteNuevo, Float precioNuevo, int cantidadNueva) {
        Producto pnuevo = listaProductos.get(index);
        pnuevo.setLoteSeleccionado(loteNuevo);
        pnuevo.setPrecioSeleccionado(precioNuevo);
        pnuevo.setCantidad(cantidadNueva);
        tabla.setValueAt(pnuevo.getMonto(), index, 6);
    }

    public void cerrarCaptura() {
        AccionesDevolucionHasInstanciaProducto instanciaProducto = new AccionesDevolucionHasInstanciaProducto();
        instanciaProducto.setFolio(folio);
        instanciaProducto.setIdSucursal(sucursal);
        for (Producto p : listaProductos) {
            instanciaProducto.setIdLote(p.getLoteSeleccionado());
            instanciaProducto.setCantidad(p.getCantidad());
            if (p.isDestruccion()) {
                System.out.println("Se va como apto");
            } else {
                instanciaProducto.setIdTarima(p.getIdTarima());
            }
            System.out.println("Precio final: " + p.getPrecioSeleccionado());
            instanciaProducto.setPrecioCaptura(p.getPrecioSeleccionado());
            instanciaProducto.setFolio(folio);
            instanciaProducto.setIdLote(p.getLoteSeleccionado());
            instanciaProducto.alta();
        }
        JOptionPane.showMessageDialog(null, "Devolución guardada.");
    }

    public boolean isEmpty() {
        return listaProductos.isEmpty();
    }
}

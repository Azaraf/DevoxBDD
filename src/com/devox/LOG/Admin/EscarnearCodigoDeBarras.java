/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.LOG.Admin;

import com.devox.GUI.panels.CapturaDevolucion;
import com.devox.LOG.Entidades.ContenidoDevolucion;
import com.devox.LOG.Entidades.Producto;
import org.inspira.devox.logger.Log;
import java.util.List;
import javax.swing.JOptionPane;
import org.inspira.devox.services.productos.AccionesHistorialPrecio;
import org.inspira.devox.services.productos.AccionesLote;
import org.inspira.devox.services.productos.AccionesProducto;
import org.inspira.devox.services.productos.ScanBarCode;
import org.inspira.devox.services.relaciones.AccionesSucursalHasProducto;
import org.inspira.devox.shared.Lote;
import org.inspira.devox.shared.Shareable;
import org.inspira.devox.shared.Tarima;

/**
 *
 * @author azaraf
 */
public class EscarnearCodigoDeBarras {

    private String codigoDeBarras;
    private String lote;
    private int cantidad;
    private String idProducto;
    private int pox = 0;
    private ContenidoDevolucion devolucion;
    private ScanBarCode sbc;
    private Producto producto;
    private boolean success = false;
    private final CapturaDevolucion panel;

    public EscarnearCodigoDeBarras(String codigoDeBarras, int cantidad, ContenidoDevolucion devolucion, CapturaDevolucion panel) {
        this.codigoDeBarras = codigoDeBarras;
        this.cantidad = cantidad;
        this.devolucion = devolucion;
        this.panel = panel;
        administrarCodigoDeBarras();
    }

    public EscarnearCodigoDeBarras(String codigoDeBarras, int cantidad, ContenidoDevolucion devolucion) {
        this.codigoDeBarras = codigoDeBarras;
        this.cantidad = cantidad;
        this.devolucion = devolucion;
        this.panel = null;
        administrarCodigoDeBarras();
    }

    public EscarnearCodigoDeBarras(String codigoDeBarras, int cantidad, boolean destino, Tarima tarima, String codigoSucursal) {
        this.codigoDeBarras = codigoDeBarras;
        this.cantidad = cantidad;
        this.panel = null;
        administrarCodigoDeBarras(destino, tarima, codigoSucursal);
    }

    private void administrarCodigoDeBarras() {
        //comprobar codigo de barras
        sbc = new ScanBarCode(codigoDeBarras, devolucion.getSucursal().getCodigoSucursal());
        if (comprobarExistencia()) {
            Log.print("El código de barras existe");
            //Obtener el código del producto, si no existe dárselo
            if (obtenerCodigoProducto()) {
                Log.print("Se obtuvo el idProducto");
                //Si se da el caso que este producto no tiene ningún lote, es necesario contar con al menos uno para continuar
                if (tieneLotes()) {
                    Log.print("Se obtuvieron lotes");
                    //Si se da el caso que el lote seleccionado no tiene precio, agregarle mínimo uno.
                    if (tienePrecios()) {
                        Log.print("Se obtuvieron precios");
                        //agregarADevolucion
                        if (devolucion.getDestino()) {
                            producto = new Producto(sbc, cantidad, pox);
                            
                        } else {
                             producto = new Producto(sbc, cantidad, pox, devolucion.getTarima());
                        }
                        success = true;
                    } else {
                        Log.print("No se obtuvieron precios");
                    }
                } else {
                    Log.print("No se obtuvieron lotes");
                }
            } else {
                Log.print("No se obtuvieron idProductos");
            }
        } else {
            Log.print("El código de barras no existe");
        }

        Log.print("Se verificó completamente el código de barras");
    }

    private void administrarCodigoDeBarras(boolean destino, Tarima tarima, String claveCliente) {
        //comprobar codigo de barras
        sbc = new ScanBarCode(codigoDeBarras, claveCliente);
        if (comprobarExistencia()) {
            Log.print("El código de barras existe");
            //Obtener el código del producto, si no existe dárselo
            if (obtenerCodigoProducto(claveCliente)) {
                Log.print("Se obtuvo el idProducto");
                //Si se da el caso que este producto no tiene ningún lote, es necesario contar con al menos uno para continuar
                if (tieneLotes(claveCliente)) {
                    Log.print("Se obtuvieron lotes");
                    //Si se da el caso que el lote seleccionado no tiene precio, agregarle mínimo uno.
                    if (tienePrecios(claveCliente)) {
                        Log.print("Se obtuvieron precios");
                        //agregarADevolucion
                        if (destino) {
                            producto = new Producto(sbc, cantidad, pox);
                        } else {
                            producto = new Producto(sbc, cantidad, pox, tarima);
                        }
                        success = true;
                    } else {
                        Log.print("No se obtuvieron precios");
                    }
                } else {
                    Log.print("No se obtuvieron lotes");
                }
            } else {
                Log.print("No se obtuvieron idProductos");
            }
        } else {
            Log.print("El código de barras no existe");
        }

        Log.print("Se verificó completamente el código de barras");
    }

    private boolean comprobarExistencia() {
        /**
         * La forma de comprobar el código de barras. En HistorialPrecio, debe
         * existir el CB mencionado.
         */

        boolean exist = false;

        AccionesHistorialPrecio ahp = new AccionesHistorialPrecio();
        exist = ahp.existEAN(codigoDeBarras);

        //en este caso, si no existe...
        if (!exist) {
            JOptionPane.showMessageDialog(null, "El código de barras " + codigoDeBarras + " no existe en la base de datos.");
        }
        return exist;
    }

    private boolean obtenerCodigoProducto() {
        /**
         * Paso 2: checar que en este código de barras exista al menos un
         * producto
         */
        boolean getCode = false;
        //Crear la instancia a la clase ScanBarCode que tiene la info de un código de barras escaneado
        sbc.scanProducto();
        //comprobar que el código de barras tenga códigos de producto
        int tam = sbc.getIdProducto().length;
        switch (tam) {
            case 0: //el CB no tiene ningún CP
                //Descartar
                sbc.setInfoNuevo(codigoDeBarras);
                Log.print("Este producto no corresponde al cliente de la devolución en curso.");
                if (CapturaCodigoDeBarras.insertarCodigoInterno() == CapturaCodigoDeBarras.SI_QUIERE_AGREGAR) {
                    sbc.setInfoNuevo(codigoDeBarras);
                    AccionesHistorialPrecio hp = new AccionesHistorialPrecio();
                    hp.setEan(codigoDeBarras);
                    String ci = JOptionPane.showInputDialog(null, "Agregue el Código interno.", sbc.getDefidProducto());
                    idProducto = ci;
                    hp.setIdProducto(ci);
                    hp.setIdSucursal(sbc.getIdSucursal());
                    String precio = JOptionPane.showInputDialog(null, "Agregue el precio.\nNo use ningún símbolo ni comas.\nSólo números y un punto para los decimales.", sbc.getDefPrecio());
                    hp.setPrecio(Float.parseFloat(precio));
                    AccionesProducto ap = new AccionesProducto();
                    boolean flag = false;
                    ap.loadDatabaseInstances();
                    for (Shareable s : ap.getEntityInstances()) {
                        if (((org.inspira.devox.shared.Producto) s).getCodigoLocal().equals(ci)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        String desc = JOptionPane.showInputDialog(null, "El producto no tiene descripción.\nAgregue nueva descripción", "Nueva alta de producto");
                        ap.setDescripcion(desc);
                        ap.setIdProducto(idProducto);
                        ap.alta();
                        String lot = JOptionPane.showInputDialog(null, "Agregue al menos un lote", "Nueva alta de producto");
                        AccionesLote al = new AccionesLote();
                        al.setIdLote(lot);
                        al.setIdProducto(idProducto);
                        al.alta();
                    } else {
                        AccionesLote al = new AccionesLote();
                        boolean bandera = false;
                        al.loadDatabaseInstances();
                        for (Shareable s : al.getEntityInstances()) {
                            if (((Lote) s).getProducto_idProducto().equals(idProducto)) {
                                bandera = true;
                                break;
                            }
                        }
                        if (!bandera) {
                            String lot = JOptionPane.showInputDialog(null, "Agregue al menos un lote", "Nueva alta de producto");
                            AccionesLote l = new AccionesLote();
                            l.setIdLote(lot);
                            Log.print("" + idProducto);
                            l.setIdProducto(idProducto);
                            l.alta();
                        }
                    }
                    AccionesSucursalHasProducto adhip = new AccionesSucursalHasProducto();
                    adhip.setIdProducto(ci);
                    adhip.setIdSucursal(hp.getIdSucursal());
                    adhip.alta();
                    hp.alta();
                }
                break;

            case 1: //el CB tiene sólo un producto -> ideal
                pox = 0;
                idProducto = sbc.getIdProducto()[pox];
                getCode = true;
                break;

            default: //el CB tiene más de 1 producto -> escoger el producto
                idProducto = CapturaCodigoDeBarras.selectProducto(sbc.getIdProducto());
                pox = getPox(idProducto);
                getCode = true;
                break;
        }
        return getCode;
    }

    private boolean obtenerCodigoProducto(String claveCliente) {
        /**
         * Paso 2: checar que en este código de barras exista al menos un
         * producto
         */
        boolean getCode = false;
        //Crear la instancia a la clase ScanBarCode que tiene la info de un código de barras escaneado
        sbc.setIdSucursal(claveCliente);
        sbc.scanProducto();
        //comprobar que el código de barras tenga códigos de producto
        int tam = sbc.getIdProducto().length;
        switch (tam) {
            case 0: //el CB no tiene ningún CP
                //Descartar
                Log.print("Este producto no corresponde al cliente de la devolución en curso.");
                if (CapturaCodigoDeBarras.insertarCodigoInterno() == CapturaCodigoDeBarras.SI_QUIERE_AGREGAR) {
                    AccionesHistorialPrecio hp = new AccionesHistorialPrecio();
                    hp.setEan(codigoDeBarras);
                    hp.loadProducts(codigoDeBarras);
                    Object ci = JOptionPane.showInputDialog(null, "Agregue el Código interno.", "Nueva alta de producto", JOptionPane.WARNING_MESSAGE, null, hp.loadProducts(codigoDeBarras).toArray(), hp.loadProducts(codigoDeBarras).toArray()[0]);
                    idProducto = ci.toString();
                    hp.setIdProducto(ci.toString());
                    hp.setIdSucursal(sbc.getIdSucursal());
                    String precio = JOptionPane.showInputDialog(null, "Agregue el precio.\nNo use ningún símbolo ni comas.\nSólo números y un punto para los decimales.", "Nueva alta de producto");
                    hp.setPrecio(Float.parseFloat(precio));
                    AccionesProducto ap = new AccionesProducto();
                    boolean flag = false;
                    ap.loadDatabaseInstances();
                    for (Shareable s : ap.getEntityInstances()) {
                        if (((org.inspira.devox.shared.Producto) s).getCodigoLocal().equals(ci)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        String desc = JOptionPane.showInputDialog(null, "El producto no tiene descripción.\nAgregue nueva descripción", "Nueva alta de producto");
                        ap.setDescripcion(desc);
                        ap.setIdProducto(idProducto);
                        ap.alta();
                        String lot = JOptionPane.showInputDialog(null, "Agregue al menos un lote", "Nueva alta de producto");
                        AccionesLote al = new AccionesLote();
                        al.setIdLote(lot);
                        al.setIdProducto(idProducto);
                        al.alta();
                    } else {
                        AccionesLote al = new AccionesLote();
                        boolean bandera = false;
                        al.loadDatabaseInstances();
                        for (Shareable s : al.getEntityInstances()) {
                            if (((Lote) s).getProducto_idProducto().equals(idProducto)) {
                                bandera = true;
                                break;
                            }
                        }
                        if (!bandera) {
                            String lot = JOptionPane.showInputDialog(null, "Agregue al menos un lote", "Nueva alta de producto");
                            AccionesLote l = new AccionesLote();
                            l.setIdLote(lot);
                            Log.print("" + idProducto);
                            l.setIdProducto(idProducto);
                            l.alta();
                        }
                    }
                    AccionesSucursalHasProducto adhip = new AccionesSucursalHasProducto();
                    adhip.setIdProducto(ci.toString());
                    adhip.setIdSucursal(hp.getIdSucursal());
                    adhip.alta();
                    hp.alta();
                }
                break;

            case 1: //el CB tiene sólo un producto -> ideal
                pox = 0;
                idProducto = sbc.getIdProducto()[pox];
                getCode = true;
                break;

            default: //el CB tiene más de 1 producto -> escoger el producto
                idProducto = CapturaCodigoDeBarras.selectProducto(sbc.getIdProducto());
                pox = getPox(idProducto);
                getCode = true;
                break;
        }
        return getCode;
    }

    private boolean tieneLotes() {
        /**
         * Paso 3: verificar que tenga lotes
         */
        boolean loteBool = false;
        //Crear la instancia al producto ScanBarCode
        sbc.scanProducto();
        List<String> lotecitos = sbc.getLotes(pox);
        //Verificar que tenga lotes
        if (lotecitos.isEmpty()) {
            //esta vacío!
            if (CapturaCodigoDeBarras.insertLote() == CapturaCodigoDeBarras.SI_QUIERE_AGREGAR) {
                String lot = JOptionPane.showInputDialog(null, "Agregue al menos un lote", "Nuevo Lote");
                if (lot != null) {
                    AccionesLote l = new AccionesLote();
                    l.setIdLote(lot);
                    l.setIdProducto(idProducto);
                    l.alta();
                    lote = lot;
                    sbc.getLotes(pox).add(lote);
                    loteBool = true;
                }
            }

        } else {
            //El producto sí tiene lotes, por lo que puede continuar.
            lote = lotecitos.get(0);
            loteBool = true;
        }
        return loteBool;
    }

    private boolean tieneLotes(String claveCliente) {
        /**
         * Paso 3: verificar que tenga lotes
         */
        boolean loteBool = false;
        //Crear la instancia al producto ScanBarCode
        sbc.setIdSucursal(claveCliente);
        sbc.scanProducto();
        //Verificar que tenga lotes
        if (pox != -1){
        List<String> lotecitos = sbc.getLotes(pox);
        if (lotecitos.isEmpty()) {
            //esta vacío!
            if (CapturaCodigoDeBarras.insertLote() == CapturaCodigoDeBarras.SI_QUIERE_AGREGAR) {
                String lot = JOptionPane.showInputDialog(null, "Agregue al menos un lote", "Nueva alta de producto");
                AccionesLote l = new AccionesLote();
                l.setIdLote(lot);
                Log.print("" + idProducto);
                l.setIdProducto(idProducto);
                l.alta();
                return loteBool;
            }

        }
        //El producto sí tiene lotes, por lo que puede continuar.
        lote = lotecitos.get(0);
        loteBool = true;
        }else{
            JOptionPane.showMessageDialog(panel, "No seleccionó ningún lote");
        }
        return loteBool;
    }

    private boolean tienePrecios(String claveCliente) {
        /**
         * Paso 4: agregar precio: 1: obtener el precio de cierto producto y
         * cierto lote
         *
         */
        boolean precioBool = false;
        sbc.setIdSucursal(claveCliente);
        List<Float> precios = sbc.getPrecioProductos();

        //Verifica que tenga precios
        if (precios.isEmpty()) {
            //está vacío!
            if (CapturaCodigoDeBarras.insertPrecio() == CapturaCodigoDeBarras.SI_QUIERE_AGREGAR) {
//                AgregarProductoFormulario apf = new AgregarProductoFormulario(sbc.getBarcode(), sbc.getIdProducto()[pox], sbc.getDescProducto()[pox], sbc.getIdsLote().get(pox).get(0), panel);
//                apf.setSucursal(claveCliente);
                return precioBool;
            }

        } else {
            precioBool = true;
        }
        //El producto sí tiene precios, por lo que se puede continuar
        return precioBool;
    }

    private boolean tienePrecios() {
        /**
         * Paso 4: agregar precio: 1: obtener el precio de cierto producto y
         * cierto lote
         *
         */
        boolean precioBool = false;
        List<Float> precios = sbc.getPrecioProductos();

        //Verifica que tenga precios
        if (precios.isEmpty()) {
            //está vacío!
            if (CapturaCodigoDeBarras.insertPrecio() == CapturaCodigoDeBarras.SI_QUIERE_AGREGAR) {
//                AgregarProductoFormulario apf = new AgregarProductoFormulario(sbc.getBarcode(), sbc.getIdProducto()[pox], sbc.getDescProducto()[pox], sbc.getIdsLote().get(pox).get(0), panel);
//                apf.setSucursal(devolucion.getSucursal().getCodigoSucursal());
                return precioBool;
            }

        } else {
            precioBool = true;
        }
        //El producto sí tiene precios, por lo que se puede continuar
        return precioBool;
    }

    private int getPox(String p) {
        sbc.scanProducto();
        for (int i = 0; i < sbc.getIdProducto().length; i++) {
            if (sbc.getIdProducto()[i].equals(p)) {
                return i;
            }
        }
        return -1;
    }

    private int getPox(String p, String claveCliente) {
        sbc = new ScanBarCode(codigoDeBarras, claveCliente);
        for (int i = 0; i < sbc.getIdProducto().length; i++) {
            if (sbc.getIdProducto()[i].equals(p)) {
                return i;
            }
        }
        return -1;
    }

    public Producto getProducto() {
        return producto;
    }

    public boolean isSuccess() {
        return success;
    }

    private boolean isEmpty(Object[] array) {
        return array.length == 0;
    }

}

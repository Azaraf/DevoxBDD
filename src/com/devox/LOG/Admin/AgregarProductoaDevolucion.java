/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.LOG.Admin;

import com.devox.LOG.interfaces.Agregar;
import org.inspira.devox.services.relaciones.AccionesDevolucionHasInstanciaProducto;

/**
 *
 * @author azaraf
 */
public class AgregarProductoaDevolucion implements Agregar {
    private Integer folio;
    private String idLote;
    private String idSucursal;
    private Integer idTarima;

    public AgregarProductoaDevolucion(Integer folio, String idLote, String idSucursal) {
        this.folio = folio;
        this.idLote = idLote;
        this.idSucursal = idSucursal;
        this.idTarima = null;
        agregar();
    }

    public AgregarProductoaDevolucion(Integer folio, String idLote, String idSucursal, Integer idTarima) {
        this.folio = folio;
        this.idLote = idLote;
        this.idSucursal = idSucursal;
        this.idTarima = idTarima;
        agregar();
    }

    @Override
    public void agregar() {
        AccionesDevolucionHasInstanciaProducto adhip = new AccionesDevolucionHasInstanciaProducto();
        adhip.setFolio(folio);
        adhip.setIdLote(idLote);
        adhip.setIdSucursal(idSucursal);
        adhip.setIdTarima(idTarima);
        adhip.alta();
    }
    
    
}

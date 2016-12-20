/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.LOG.Entidades;

import com.devox.LOG.interfaces.Formulario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.inspira.devox.services.clientes.AccionesCliente;
import org.inspira.devox.services.clientes.AccionesFamilia;
import org.inspira.devox.services.clientes.AccionesSucursal;
import org.inspira.devox.services.devolucion.AccionesDescripcionDevolucionEstado;
import org.inspira.devox.services.devolucion.AccionesDevolucion;
import org.inspira.devox.services.devolucion.AccionesMotivo;
import org.inspira.devox.services.devolucion.AccionesStatus;
import org.inspira.devox.services.devolucion.AccionesSubDivision;
import org.inspira.devox.services.devolucion.AccionesTransporte;
import org.inspira.devox.services.organico.AccionesAlmacen;
import org.inspira.devox.services.organico.AccionesDestruccionFiscal;
import org.inspira.devox.shared.Almacen;
import org.inspira.devox.shared.Cliente;
import org.inspira.devox.shared.DescripcionDevolucion;
import org.inspira.devox.shared.DescripcionDevolucionEstado;
import org.inspira.devox.shared.DestruccionFiscal;
import org.inspira.devox.shared.Devolucion;
import org.inspira.devox.shared.DevolucionHasInstanciaProducto;
import org.inspira.devox.shared.Familia;
import org.inspira.devox.shared.Motivo;
import org.inspira.devox.shared.Shareable;
import org.inspira.devox.shared.Status;
import org.inspira.devox.shared.SubDivision;
import org.inspira.devox.shared.Sucursal;
import org.inspira.devox.shared.Tarima;
import org.inspira.devox.shared.Transporte;

/**
 *
 * @author azaraf
 */
public class ContenidoDevolucion implements Formulario {

    private Devolucion devolucion;
    private DescripcionDevolucion descripcionDevolucion;
    private DescripcionDevolucionEstado descripcionDevolucionEstado;
    private Tarima tarima;
    private Sucursal sucursal;
    private DevolucionHasInstanciaProducto devolucionHasInstanciaProducto;
    private AccionesDevolucion accionesDevolucion;

    private Status status;
    private Motivo motivo;
    private Transporte transporte;
    private Cliente cliente;
    private Familia division;
    private SubDivision codRazon;
    private DestruccionFiscal dxFiscal;
    private Almacen almacen;

    private boolean destino;
    private Date fechaCaptura;
    private Date fechaRecibido;
    private String folioAbbott;
    private String folioCliente;
    private String factura;
    private String observaciones;
    private Integer cajas;

    public List<String> listaIdLotes;

    public ContenidoDevolucion() {
        
        devolucion = new Devolucion();
        descripcionDevolucion = new DescripcionDevolucion();
        descripcionDevolucionEstado = new DescripcionDevolucionEstado();
        tarima = new Tarima();
        sucursal = new Sucursal();
        devolucionHasInstanciaProducto = new DevolucionHasInstanciaProducto();
        status = new Status();
        motivo = new Motivo();
        transporte = new Transporte();
        cliente = new Cliente();
        division = new Familia();
        codRazon = new SubDivision();
        dxFiscal = new DestruccionFiscal();
        almacen = new Almacen();
        listaIdLotes = new ArrayList<>();
    }

    public List<String> getListaIdLotes() {
        return listaIdLotes;
    }

    public void setListaIdLotes(List<String> listaIdLotes) {
        this.listaIdLotes = listaIdLotes;
    }

    public void setFolioDHL(int folio) {
        devolucion.setFolio(folio);
    }

    public String getFolioDHL() {
        return String.format("%06d", devolucion.getFolio());
    }

    public Status getEstado() {
        return status;
    }

    public void setEstado(int idStatus) {
        AccionesStatus as = new AccionesStatus();
        as.loadDatabaseInstances();

        this.status = as.getStatuses()[idStatus - 1];
        descripcionDevolucionEstado.setStatus_idStatus(status.getCodigo());
    }

    public void updateEstado() {
        
        int folioactual = status.getCodigo();
        int nuevofolio = folioactual + 1;
        AccionesDescripcionDevolucionEstado estado = new AccionesDescripcionDevolucionEstado();
        estado.setFolio(getFolio());
        estado.setIdEstado(nuevofolio);
        estado.alta();
        AccionesStatus as = new AccionesStatus();
        Shareable s[] = as.getEntityInstances();
        for (int i = 0; i < s.length; i++){
            Status stat = (Status)s[i];
            if(stat.getCodigo() == nuevofolio){
                this.status = stat;
            }
                
        }
        descripcionDevolucionEstado.setStatus_idStatus(nuevofolio);
    }

    public boolean getDestino() {
        //true = apto
        //false = destrucción
        return destino;
    }

    public void setDestino(boolean destino) {
        //true = apto
        //false = destrucción
        this.destino = destino;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(String idSucursal) {
        AccionesSucursal as = new AccionesSucursal();
        as.loadDatabaseInstances();
        for (Shareable s : as.getEntityInstances()) {
            if (((Sucursal) s).getCodigoSucursal().equals(idSucursal)) {
                sucursal = ((Sucursal) s);
            }
        }
        AccionesCliente ac = new AccionesCliente();
        ac.loadDatabaseInstances();
        for (Shareable s : ac.getEntityInstances()) {
            if (((Cliente) s).getRfc().equals(sucursal.getCliente_rfc())) {
                cliente = ((Cliente) s);
            }
        }
        AccionesFamilia af = new AccionesFamilia();
        af.loadDatabaseInstances();
        for (Shareable s : af.getEntityInstances()) {
            if (((Familia) s).getCodigoFamilia().equals(sucursal.getFamilia_idFamilia())) {
                division = ((Familia) s);
            }
        }
    }

    public Date getFechaRecibido() {
        return descripcionDevolucion.getFechaDevolucion();
    }

    public void setFechaRecibido(Date fechaRecibido) {
        this.fechaRecibido = fechaRecibido;
        descripcionDevolucion.setFechaDevolucion(fechaRecibido);
    }

    public Motivo getMotivo() {
        return motivo;
    }

    public void setMotivo(int idMotivo) {
        AccionesMotivo am = new AccionesMotivo();
        am.loadDatabaseInstances();

        for (Shareable s : am.getEntityInstances()) {
            if (((Motivo) s).getCodigo() == idMotivo) {
                motivo = ((Motivo) s);
            }
        }
        devolucion.setMotivo_idMotivo(motivo.getCodigo());
    }

    public String getCodigoRazon() {
        return codRazon.getSubDivision();
    }

    public void setCodigoRazon(int codigoRazon) {
        AccionesSubDivision asd = new AccionesSubDivision();
        asd.loadDatabaseInstances();
        for (Shareable s : asd.getEntityInstances()) {
            if (((SubDivision) s).getCodigo() == codigoRazon) {
                this.codRazon = ((SubDivision) s);
            }
        }
        descripcionDevolucion.setIdSubDivision(codRazon.getCodigo());
    }

    public String getFolioAbbott() {
        return folioAbbott;
    }

    public void setFolioAbbott(String folioAbbott) {
        this.folioAbbott = folioAbbott;
        descripcionDevolucion.setFolioAbbot(folioAbbott);
    }

    public String getFolioCliente() {
        return folioCliente;
    }

    public void setFolioCliente(String folioCliente) {
        this.folioCliente = folioCliente;
        descripcionDevolucion.setDocumentoCliente(folioCliente);
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
        descripcionDevolucion.setFactura(factura);
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
        descripcionDevolucion.setObservaciones(observaciones);
    }

    public Integer getCajas() {
        return cajas;
    }

    public void setCajas(Integer cajas) {
        this.cajas = cajas;
        descripcionDevolucion.setCajas(cajas);
    }

    public String getTransporte() {
        return transporte.getDescripcion();
    }

    public void setTransporte(String idTransporte) {
        AccionesTransporte at = new AccionesTransporte();
        at.loadDatabaseInstances();
        for (Shareable s : at.getEntityInstances()) {
            if (((Transporte) s).getCodigo().equals(idTransporte)) {
                this.transporte = ((Transporte) s);
            }
        }
        descripcionDevolucion.setIdTransporte(transporte.getCodigo());
    }

    public String getNombreCliente() {
        return cliente.getNombre();
    }

    public String getNombreSucursal() {
        return sucursal.getNombre();
    }

    public String getClaveCliente() {
        return sucursal.getCodigoSucursal();
    }

    public String getDivisionCliente() {
        return division.getDescripcion();
    }

    public Date getFechaCaptura() {
        return fechaCaptura;
    }

    public void setFechaCaptura(Date fechaCaptura) {
        this.fechaCaptura = fechaCaptura;
        descripcionDevolucion.setFechaCaptura(fechaCaptura);
    }

    public Tarima getTarima() {
        return tarima;
    }

    public void setTarima(Tarima tarima) {
        this.tarima = tarima;
    }

    public String getTarimaNombre() {
        return tarima.getNombre();
    }

    public void setTarimaNombre(String tarimaNombre) {
        tarima.setNombre(tarimaNombre);
    }

    public Integer getDestruccionFiscal() {
        return dxFiscal.getDestruccionFiscal();
    }

    public void setDestruccionFiscal(Integer destruccionFiscal) {
        List<DestruccionFiscal> lista = new ArrayList<>();
        AccionesDestruccionFiscal adf = new AccionesDestruccionFiscal();
        adf.loadDatabaseInstances();
        int ultimaDestruccion = 0;
        for (Shareable s : adf.getEntityInstances()) {
            lista.add((DestruccionFiscal) s);
            ultimaDestruccion = ((DestruccionFiscal) s).getIdDestruccionFiscal();
        }
        tarima.setIdDestruccionFiscal(ultimaDestruccion);
    }

    public String getAlmacen() {
        return almacen.getCv();
    }

    public void setAlmacen(String cvAlmacen) {
        AccionesAlmacen aa = new AccionesAlmacen();
        aa.loadDatabaseInstances();
        for (Shareable s : aa.getEntityInstances()) {
            if (((Almacen) s).getCv().equals(cvAlmacen)) {
                this.almacen = ((Almacen) s);
            }
        }
    }

    public Devolucion getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(Devolucion devolucion) {
        this.devolucion = devolucion;
    }

    public DescripcionDevolucion getDescripcionDevolucion() {
        return descripcionDevolucion;
    }

    public void setDescripcionDevolucion(DescripcionDevolucion descripcionDevolucion) {
        this.descripcionDevolucion = descripcionDevolucion;
    }

    public DescripcionDevolucionEstado getDescripcionDevolucionEstado() {
        return descripcionDevolucionEstado;
    }

    public void setDescripcionDevolucionEstado(DescripcionDevolucionEstado descripcionDevolucionEstado) {
        this.descripcionDevolucionEstado = descripcionDevolucionEstado;
    }

    public DevolucionHasInstanciaProducto getDevolucionHasInstanciaProducto() {
        return devolucionHasInstanciaProducto;
    }

    public void setDevolucionHasInstanciaProducto(DevolucionHasInstanciaProducto devolucionHasInstanciaProducto) {
        this.devolucionHasInstanciaProducto = devolucionHasInstanciaProducto;
    }

    public int getFolio() {
        return devolucion.getFolio();
    }

    @Override
    public void altaNuevaDevolucion() {
        accionesDevolucion = new AccionesDevolucion();
        accionesDevolucion.setCajas(cajas);
        accionesDevolucion.setDocumentoCliente(folioCliente);
        accionesDevolucion.setFactura(factura);
        accionesDevolucion.setFechaCaptura(fechaCaptura);
        accionesDevolucion.setFechaDevolucion(fechaRecibido);
        accionesDevolucion.setFolio(devolucion.getFolio());
        accionesDevolucion.setFolioAbbot(folioAbbott);
        accionesDevolucion.setIdStatus(descripcionDevolucionEstado.getStatus_idStatus());
        accionesDevolucion.setIdSubDivision(descripcionDevolucion.getIdSubDivision());
        accionesDevolucion.setIdTransporte(descripcionDevolucion.getIdTransporte());
        accionesDevolucion.setMotivo(motivo.getCodigo());
        accionesDevolucion.setObservaciones(observaciones);
        accionesDevolucion.alta();
    }


    public AccionesDevolucion getAccionesDevolucion() {
        return accionesDevolucion;
    }

    public void setAccionesDevolucion(AccionesDevolucion accionesDevolucion) {
        this.accionesDevolucion = accionesDevolucion;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public void setTransporte(Transporte transporte) {
        this.transporte = transporte;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Familia getDivision() {
        return division;
    }

    public void setDivision(Familia division) {
        this.division = division;
    }

    public SubDivision getCodRazon() {
        return codRazon;
    }

    public void setCodRazon(SubDivision codRazon) {
        this.codRazon = codRazon;
    }

    public DestruccionFiscal getDxFiscal() {
        return dxFiscal;
    }

    public void setDxFiscal(DestruccionFiscal dxFiscal) {
        this.dxFiscal = dxFiscal;
    }


    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

}

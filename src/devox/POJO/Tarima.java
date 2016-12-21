/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devox.POJO;

import java.sql.Date;

/**
 *
 * @author Ricardo
 */
public class Tarima implements POJO{
    
    private int idTarima;
    private String idAlmacen;
    private Date fechaCreacion;
    private Date fechaCierra;
    private String nombreTarima;

    public Tarima() {
    }

    public Tarima(int idTarima, String idAlmacen, Date fechaCreacion, Date fechaCierra, String nombreTarima) {
        this.idTarima = idTarima;
        this.idAlmacen = idAlmacen;
        this.fechaCreacion = fechaCreacion;
        this.fechaCierra = fechaCierra;
        this.nombreTarima = nombreTarima;
    }

    public int getIdTarima() {
        return idTarima;
    }

    public void setIdTarima(int idTarima) {
        this.idTarima = idTarima;
    }

    public String getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(String idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaCierra() {
        return fechaCierra;
    }

    public void setFechaCierra(Date fechaCierra) {
        this.fechaCierra = fechaCierra;
    }

    public String getNombreTarima() {
        return nombreTarima;
    }

    public void setNombreTarima(String nombreTarima) {
        this.nombreTarima = nombreTarima;
    }
    
    
}

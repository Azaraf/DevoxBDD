/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devox.POJO;

/**
 *
 * @author azaraf
 */
public class Almacen {
    private String idAlmacen;
    private String descripcion;

    public Almacen() {
    }

    public Almacen(String idAlmacen, String descripcion) {
        this.idAlmacen = idAlmacen;
        this.descripcion = descripcion;
    }

    public String getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(String idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}

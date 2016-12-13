/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devox.POJO;

/**
 *
 * @author Ricardo
 */
public class Producto {
    
    private String idProducto;
    private String descripcion;
    private String codigoBarra;

    public Producto() {
    }

    
    public Producto(String idProducto, String descripcion, String codigoBarra) {
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.codigoBarra = codigoBarra;
    }
    
    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }
    
    
}

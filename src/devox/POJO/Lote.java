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
public class Lote implements POJO{
    private String idLote;
    private Producto producto;

    public Lote() {
    }

    public Lote(String idLote, Producto producto) {
        this.idLote = idLote;
        this.producto = producto;
    }

    public String getIdLote() {
        return idLote;
    }

    public void setIdLote(String idLote) {
        this.idLote = idLote;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    
}

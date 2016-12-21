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
public class Motivo implements POJO{
    
    private int idMotivo;
    private String descripcion;

    public Motivo() {
    }

    public Motivo(int idMotivo, String descripcion) {
        this.idMotivo = idMotivo;
        this.descripcion = descripcion;
    }

    public int getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(int idMotivo) {
        this.idMotivo = idMotivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
}

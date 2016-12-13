/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devox.DAO;

import devox.POJO.Almacen;
import devox.POJO.POJO;

/**
 *
 * @author azaraf
 */
public class AlmacenDAO implements DAO{

    private POJO almacen = null;
            
    public AlmacenDAO() {
        almacen = new Almacen();
    }

    public AlmacenDAO(String idAlmacen, String descripcion){
        almacen = new Almacen(idAlmacen, descripcion);
    }
    
    
    @Override
    public void alta() {
        
    }

    @Override
    public void baja() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cambio() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devox.DAO;

import devox.POJO.POJO;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author azaraf
 */
public abstract class DAOClass implements DAO{
    private CallableStatement cstm = null;
    private ResultSet rs = null;
    public POJO obj;

    public void setCstm(CallableStatement cstm) {
        this.cstm = cstm;
    }

    public CallableStatement getCstm() {
        return cstm;
    }
    
    public ResultSet getRs() {
        return rs;
    }
    
    public void clean(){
        cstm = null;
        rs = null;
    }
    
    public void executeQuery(){
        try{
            rs = cstm.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public POJO getObj() {
        return obj;
    }

    public void setObj(POJO obj) {
        this.obj = obj;
    }
    
    
    
}

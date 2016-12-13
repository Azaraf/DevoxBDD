/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devox.DAO;

import devox.POJO.POJO;
import devox.database.DBC;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author azaraf
 */
public interface DAO {
    Connection con = DBC.getConnection();
    CallableStatement cstmt = null;
    ResultSet rs = null;
    public void alta();
    public void baja();
    public void cambio();
}

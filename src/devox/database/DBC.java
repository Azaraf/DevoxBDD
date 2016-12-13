/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devox.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author azaraf
 */
public class DBC {
    
    private static Connection con = null;
    private static String connectionUrl = "jdbc:sqlserver://"
                + "10.201.179.136\\SQLEXPRESS;"
                + "databaseName=Devox;"
                + "user=sa;"
                + "password=azaraf;";
    
    static {
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            DBC.con = DriverManager.getConnection(DBC.connectionUrl
            );
        }
        catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return con;
    }
    
    public static boolean closeConnection(){
        if(con!=null) try{con.close(); return true;}catch(SQLException e){e.printStackTrace();}
        return false;
    }
    
    
}

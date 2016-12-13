/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devox.POJO;

import devox.database.DBC;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author azaraf
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int tool = 1;
        if (tool == 0) {
            try {
                String SQL = "SELECT * FROM [Devox].[dbo].[Cliente]";
                Statement stm = DBC.getConnection().createStatement();
                ResultSet rs = stm.executeQuery(SQL);
                System.out.println("result: \n-------------------------------------------------------");
                while (rs.next()) {
                    System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                CallableStatement cstmt = null;
                ResultSet rs = null;
                cstmt = DBC.getConnection().prepareCall("{call sp_productos}");
                rs = cstmt.executeQuery();
                
                while(rs.next()){
                    System.out.println(rs.getString(1) + "\t" + rs.getString(2));
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

}

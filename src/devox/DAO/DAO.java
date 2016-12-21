/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devox.DAO;

import devox.database.DBC;
import java.sql.Connection;

/**
 *
 * @author azaraf
 */
public interface DAO {
    int NOPOJO = -1;
    int SUCCESS = 0;
    int FAIL = 1;
    Connection con = DBC.getConnection();
    public int alta();
    public int baja();
    public int cambio();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devox.DAO;

import devox.POJO.Almacen;
import java.sql.SQLException;

/**
 *
 * @author azaraf
 */
public class AlmacenDAO extends DAOClass {

    private String idAlmacenOLD;

    public AlmacenDAO() {
        obj = new Almacen();
    }

    public AlmacenDAO(Almacen almacen) {
        this.obj = almacen;
        if (almacen != null) {
            this.idAlmacenOLD = almacen.getIdAlmacen();
        }

    }

    public AlmacenDAO(String idAlmacen, String descripcion) {
        obj = new Almacen(idAlmacen, descripcion);
        this.idAlmacenOLD = idAlmacen;
    }

    @Override
    public int alta() {
        if (obj != null) {
            try {
                setCstm(con.prepareCall("{call sp_alta_almacen(?,?)}"));
                getCstm().setString(1, ((Almacen) obj).getIdAlmacen());
                getCstm().setString(2, ((Almacen) obj).getDescripcion());
                executeQuery();
                return SUCCESS;
            } catch (SQLException e) {
                e.printStackTrace();
                return FAIL;
            }
        } else {
            return NOPOJO;
        }
    }

    @Override
    public int baja() {
        if (obj != null) {
            try {
                setCstm(con.prepareCall("{call sp_baja_almacen(?)}"));
                getCstm().setString(1, ((Almacen) obj).getIdAlmacen());
                executeQuery();
                return SUCCESS;
            } catch (SQLException e) {
                e.printStackTrace();
                return FAIL;
            }
        } else {
            return NOPOJO;
        }
    }

    @Override
    public int cambio() {
        if (obj != null) {
            try {
                setCstm(con.prepareCall("{call sp_cambio_almacen(?,?,?)}"));
                getCstm().setString(1, this.idAlmacenOLD);
                getCstm().setString(2, ((Almacen) obj).getIdAlmacen());
                getCstm().setString(2, ((Almacen) obj).getDescripcion());
                executeQuery();
                return SUCCESS;
            } catch (SQLException e) {
                e.printStackTrace();
                return FAIL;
            }
        } else {
            return NOPOJO;
        }
    }

}

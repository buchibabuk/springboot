/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.RowSet;

import nic.vahan5.reg.form.dobj.Status_dobj;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.form.dobj.AxleDetailsDobj;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionManager;

/**
 *
 * @author tranC103
 */
@Service
public class AxleImpl {
@Autowired
TransactionManagerReadOnly tmgr;


    private static final Logger LOGGER = Logger.getLogger(AxleImpl.class);

    public AxleDetailsDobj setAxleVehDetails_db_to_dobj(String appl_no, String regnNo, String state_cd, int off_cd) {

        AxleDetailsDobj dobj = null;
       // TransactionManagerReadOnly tmgr = null;
        PreparedStatement ps = null;
        String sql = null;
        String condition = null;
        try {
          //  tmgr = new TransactionManagerReadOnly("setAxleVehDetails_db_to_dobj");
            if (appl_no != null) {
                condition = appl_no.toUpperCase();
                sql = "SELECT * FROM " + TableList.VA_AXLE + " WHERE appl_no=?";
                ps = tmgr.prepareStatement(sql);
                ps.setString(1, condition);
            }
            if (regnNo != null) {
                condition = regnNo.toUpperCase();
                sql = "SELECT * FROM " + TableList.VT_AXLE + " WHERE regn_no=? and state_cd= ? and off_cd= ?";
                ps = tmgr.prepareStatement(sql);
                ps.setString(1, condition);
                ps.setString(2, state_cd);
                ps.setInt(3, off_cd);
            }

            RowSet rs = tmgr.fetchDetachedRowSet();
            if (rs.next()) {
                dobj = new AxleDetailsDobj();
                dobj.setNoOfAxle(rs.getInt("no_of_axles"));
                dobj.setTf_Front(rs.getInt("f_axle_weight"));
                dobj.setTf_Front1(rs.getString("f_axle_descp"));

                dobj.setTf_Rear(rs.getInt("r_axle_weight"));
                dobj.setTf_Rear1(rs.getString("r_axle_descp"));

                dobj.setTf_Other(rs.getInt("o_axle_weight"));
                dobj.setTf_Other1(rs.getString("o_axle_descp"));

                dobj.setTf_Tandem(rs.getInt("t_axle_weight"));
                dobj.setTf_Tandem1(rs.getString("t_axle_descp"));

                dobj.setTf_Rear_Over(rs.getInt("r_overhang"));

                dobj.setTf_Front_tyre(rs.getInt("f_axle_tyre"));
                dobj.setTf_Rear_tyre(rs.getInt("r_axle_tyre"));
                dobj.setTf_Other_tyre(rs.getInt("o_axle_tyre"));
                dobj.setTf_Tandem_tyre(rs.getInt("t_axle_tyre"));

            }
        } catch (SQLException e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
        } finally {
            if (tmgr != null) {
                try {
                    tmgr.release();
                } catch (Exception e) {
                    LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
                }
            }
        }
        return dobj;
    }

   

  

    
    

    
  
    
   

    
}
package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.server.TableConstants;
@Service
public class HpaImpl {

	@Autowired
	TransactionManagerReadOnly tmgr;
    private static final Logger LOGGER = Logger.getLogger(HpaImpl.class);
    public int maxHypothecationNo(String applNo, String regnNo, int purCd,String stateCode,int offCode) {
       // TransactionManagerReadOnly tmgr = null;
        PreparedStatement ps = null;
        RowSet rs = null;
        int total = 1;
        String sql = null;
        try {
         //   tmgr = new TransactionManagerReadOnly("maxHypothecationNo");

            if (purCd == TableConstants.VM_TRANSACTION_MAST_ADD_HYPO) {
                sql = "SELECT count(1) as total FROM " + TableList.VA_HPA
                        + " WHERE regn_no=? and appl_no=?";
                ps = tmgr.prepareStatement(sql);
                ps.setString(1, regnNo);
                ps.setString(2, applNo);
            }

            if (purCd == TableConstants.VM_TRANSACTION_MAST_REM_HYPO) {
                sql = "SELECT count(1) as total FROM " + TableList.VA_HPT
                        + " WHERE regn_no=? and appl_no=?";
                ps = tmgr.prepareStatement(sql);
                ps.setString(1, regnNo);
                ps.setString(2, applNo);
            }

            if (purCd == TableConstants.VM_TRANSACTION_MAST_HPC) {
                sql = "SELECT count(1) as total FROM " + TableList.VT_HYPTH
                        + " WHERE regn_no=? and state_cd = ? and off_cd = ?";
                ps = tmgr.prepareStatement(sql);
                ps.setString(1, regnNo);
                ps.setString(2,stateCode);
                ps.setInt(3,offCode);
            }

            rs = tmgr.fetchDetachedRowSet();
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
        } finally {
            try {
                if (tmgr != null) {
                    tmgr.release();
                }
            } catch (Exception e) {
                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            }
        }
        return total;
    }

}

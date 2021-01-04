package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
@Service
public class PermitImpl {
	 private static final Logger LOGGER = Logger.getLogger(PermitImpl.class);
	 
	 public void verifyLoiDetails(String appl_number, String newLoiNo, String state_cd, int off_cd, TransactionManagerOne tmgr) throws VahanException {
	        PreparedStatement ps = null;
	        try {
	          //  tmgr = new TransactionManager("getVaPermitOwnerDetails");
	            String sql = "select b.*,a.offer_no from permit.va_permit a,permit.va_permit_owner b where a.appl_no=b.appl_no and a.offer_no=? and a.state_cd=? and a.off_cd=?";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, newLoiNo);
	            ps.setString(2, state_cd);
	            ps.setInt(3, off_cd);
	            RowSet rs = tmgr.fetchDetachedRowSet();
	            if (!rs.next()) {
	                throw new VahanException("Please Enter Valid Loi No");
	            }
	        } catch (VahanException e) {
	            throw e;
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Error in Getting LOI details");
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }

	    }

	 public void insertNewLoiDetails(String appl_number, String newLoiNo, String regn_no, TransactionManagerOne tmgr) throws VahanException {
	        PreparedStatement ps = null;
	        try {
	            String sql = "SELECT * FROM  permit.va_new_reg_loi WHERE appl_no=?";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, appl_number);
	            RowSet rs = tmgr.fetchDetachedRowSet_No_release();
	            if (!rs.next()) {
	                sql = "INSERT INTO permit.va_new_reg_loi(\n"
	                        + "            appl_no, offer_no ,regn_no)\n"
	                        + "    VALUES (?, ?, ?);";
	                ps = tmgr.prepareStatement(sql);
	                ps.setString(1, appl_number);
	                ps.setString(2, newLoiNo);
	                ps.setString(3, regn_no);
	                ps.executeUpdate();
	            }
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Error in Saving New Permit LOI details");
	        }

	    }

}

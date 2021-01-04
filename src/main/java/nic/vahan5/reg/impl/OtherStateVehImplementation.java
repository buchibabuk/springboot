package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
import nic.vahan5.reg.form.dobj.OtherStateVehDobj;
import nic.vahan5.reg.server.TableConstants;
@Service
public class OtherStateVehImplementation {
	  private static final Logger LOGGER = Logger.getLogger(OtherStateVehImplementation.class);
	  
	  public void insertUpdateOtherStateVeh(TransactionManagerOne tmgr, OtherStateVehDobj dobj,
	            String empCode, String userStateCode, int offCode) throws SQLException {
	        PreparedStatement ps = null;
	        String sql = null;

	        sql = "SELECT * FROM " + TableList.VA_OTHER_STATE_VEH + " WHERE appl_no = ?";
	        ps = tmgr.prepareStatement(sql);
	        ps.setString(1, dobj.getApplNo());
	        RowSet rs = tmgr.fetchDetachedRowSet_No_release();

	        if (rs.next()) { //if any record is exist then update otherwise insert it
	            insertIntoOtherStateVehHistory(tmgr, dobj.getApplNo(), empCode);
	            updateOtherStateVeh(tmgr, dobj, userStateCode, offCode);
	        } else {
	            insertIntoVaOtherStateVeh(tmgr, dobj, userStateCode, offCode);
	        }
	    } // end of insertUpdateOtherStateVeh
	  public void insertIntoOtherStateVehHistory(TransactionManagerOne tmgr, String appl_no, String empCode) throws SQLException {
	        PreparedStatement ps = null;
	        String sql = null;

	        sql = "INSERT INTO " + TableList.VHA_OTHER_STATE_VEH
	                + " SELECT current_timestamp as moved_on, ? moved_by,state_cd, off_cd,appl_no, old_regn_no, old_off_cd, old_state_cd, ncrb_ref, confirm_ref, "
	                + "       noc_dt, noc_no, state_entry_dt,op_dt "
	                + "  FROM " + TableList.VA_OTHER_STATE_VEH + " WHERE appl_no=?";
	        ps = tmgr.prepareStatement(sql);
	        ps.setString(1, empCode);
	        ps.setString(2, appl_no);
	        ps.executeUpdate();
	    } // end of insertIntoOtherStateVehHistory
	  
	  public void updateOtherStateVeh(TransactionManagerOne tmgr, OtherStateVehDobj dobj, String userStateCode,
	            int offCode) throws SQLException {
	        PreparedStatement ps = null;
	        String sql = null;
	        int pos = 1;

	        sql = "UPDATE " + TableList.VA_OTHER_STATE_VEH
	                + " SET state_cd = ?,off_cd = ?,old_regn_no=?, old_off_cd=?, old_state_cd=?, ncrb_ref=?,"
	                + "       confirm_ref=?, noc_dt=?, noc_no=?, state_entry_dt=?,op_dt = current_timestamp "
	                + " WHERE appl_no=?";
	        ps = tmgr.prepareStatement(sql);
	        ps.setString(pos++, userStateCode);
	        ps.setInt(pos++, offCode);
	        ps.setString(pos++, dobj.getOldRegnNo());
	        ps.setInt(pos++, dobj.getOldOffCD());
	        ps.setString(pos++, dobj.getOldStateCD());
	        ps.setString(pos++, dobj.getNcrbRef());
	        ps.setString(pos++, dobj.getConfirmRef());
	        if (dobj.getNocDate() == null) {
	            ps.setDate(pos++, new java.sql.Date(new Date().getTime()));
	        } else {
	            ps.setDate(pos++, new java.sql.Date(dobj.getNocDate().getTime()));
	        }
	        ps.setString(pos++, dobj.getNocNo());
	        if (dobj.getStateEntryDate() == null) {
	            ps.setDate(pos++, new java.sql.Date(new Date().getTime()));
	        } else {
	            ps.setDate(pos++, new java.sql.Date(dobj.getStateEntryDate().getTime()));
	        }
	        ps.setString(pos++, dobj.getApplNo());
	        ps.executeUpdate();
	    } // end of updateOtherStateVeh
	  public void insertIntoVaOtherStateVeh(TransactionManagerOne tmgr, OtherStateVehDobj dobj,
	            String userStateCode, int offCode) throws SQLException {
	        PreparedStatement ps = null;
	        String sql = null;
	        int pos = 1;

	        sql = "INSERT INTO " + TableList.VA_OTHER_STATE_VEH
	                + " (state_cd,off_cd,appl_no, old_regn_no, old_off_cd, old_state_cd, ncrb_ref, confirm_ref,"
	                + "            noc_dt, noc_no, state_entry_dt,op_dt)"
	                + "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,current_timestamp)";

	        ps = tmgr.prepareStatement(sql);
	        ps.setString(pos++, userStateCode);
	        ps.setInt(pos++, offCode);
	        ps.setString(pos++, dobj.getApplNo());
	        ps.setString(pos++, dobj.getOldRegnNo());
	        ps.setInt(pos++, dobj.getOldOffCD() == null ? 0 : dobj.getOldOffCD());
	        ps.setString(pos++, dobj.getOldStateCD() == null ? TableConstants.EMPTY_STRING : dobj.getOldStateCD());
	        ps.setString(pos++, dobj.getNcrbRef() == null ? TableConstants.EMPTY_STRING : dobj.getNcrbRef());
	        ps.setString(pos++, dobj.getConfirmRef() == null ? TableConstants.EMPTY_STRING : dobj.getConfirmRef());
	        if (dobj.getNocDate() == null) {
	            ps.setDate(pos++, new java.sql.Date(new Date().getTime()));
	        } else {
	            ps.setDate(pos++, new java.sql.Date(dobj.getNocDate().getTime()));
	        }
	        ps.setString(pos++, dobj.getNocNo() == null ? TableConstants.EMPTY_STRING : dobj.getNocNo());
	        if (dobj.getStateEntryDate() == null) {
	            ps.setDate(pos++, new java.sql.Date(new Date().getTime()));
	        } else {
	            ps.setDate(pos++, new java.sql.Date(dobj.getStateEntryDate().getTime()));
	        }
	        ps.executeUpdate();

	    } // end of insertIntoVaOtherStateVeh


}

package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.form.dobj.ExArmyDobj;
@Service
public class ExArmyImplementation {
	
	@Autowired
	TransactionManagerReadOnly tmg ;
	private static final Logger LOGGER = Logger.getLogger(ExArmyImplementation.class);
	 public  ExArmyDobj setExArmyDetails_db_to_dobj(String appl_no, String regn_no, String state_cd, int off_cd) {

	        String condition = null;
	        String sql = null;
	        ExArmyDobj dobj = null;
	      //  TransactionManagerOne tmgr = null;
	        PreparedStatement ps = null;
	        boolean vtFlag = false;

	        if (appl_no != null) {
	            vtFlag = false;
	            condition = appl_no.toUpperCase();
	            sql = "SELECT voucher_no,voucher_dt,place FROM " + TableList.VA_OWNER_EX_ARMY + " where appl_no = ?";
	        }

	        if (regn_no != null) {
	            vtFlag = true;
	            condition = regn_no.toUpperCase();
	            sql = "SELECT voucher_no,voucher_dt,place FROM " + TableList.VT_OWNER_EX_ARMY + " where regn_no = ?"
	                    + " and state_cd = ? and off_cd = ? ";
	        }


	        try {
	        //    tmgr = new TransactionManagerOne("setExArmyDetails_db_to_dobj");
	            ps = tmg.prepareStatement(sql);
	            if (vtFlag) {
	                ps.setString(1, condition);
	                ps.setString(2, state_cd);
	                ps.setInt(3, off_cd);
	            } else {
	                ps.setString(1, condition);
	            }
	            RowSet rs = tmg.fetchDetachedRowSet();
	            if (rs.next()) {
	                dobj = new ExArmyDobj();
	                dobj.setTf_Voucher_no(rs.getString("voucher_no"));
	                dobj.setTf_VoucherDate((java.util.Date) rs.getDate("voucher_dt"));
	                dobj.setTf_POP(rs.getString("place"));
	            }
	        } catch (SQLException e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        } finally {
	            if (tmg != null) {
	                try {
	                    tmg.release();
	                } catch (Exception e) {
	                    LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	                }
	            }
	        }
	        return dobj;
	    }
	 public  void saveExArmyVehicleDetails_Impl(ExArmyDobj dobj, String appl_no, TransactionManagerOne tmgr,
	            String empCode, String userStateCode, int offCode) {
	        PreparedStatement ps = null;
	        String sql = null;
	        try {
	            //inserting data into vha_insurance from va_insurance
	            sql = "SELECT appl_no FROM " + TableList.VA_OWNER_EX_ARMY + " where appl_no = ?";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, appl_no);
	            RowSet rs = tmgr.fetchDetachedRowSet_No_release();

	            if (rs.next()) { //if any record is exist then update otherwise insert it
	                insertIntoVhaExArmy(tmgr, appl_no, empCode);
	                updateExArmy(tmgr, dobj, appl_no, userStateCode, offCode);
	            } else {
	                insertExArmy(tmgr, dobj, appl_no,userStateCode,offCode);
	            }

	        } catch (SQLException e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        }
	    }
	 public  void insertIntoVhaExArmy(TransactionManagerOne tmgr, String appl_no, String empCode) {
	        PreparedStatement ps = null;
	        String sql = null;
	        try {

	            sql = "INSERT INTO " + TableList.VHA_OWNER_EX_ARMY + " \n"
	                    + "SELECT current_timestamp as moved_on, ? as moved_by, state_cd, off_cd,appl_no, voucher_no, voucher_dt, place,op_dt \n"
	                    + "FROM va_owner_ex_army where appl_no=?";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, empCode);
	            ps.setString(2, appl_no);
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        }
	    }
	 private  void updateExArmy(TransactionManagerOne tmgr, ExArmyDobj dobj, String appl_no,
	            String userStateCode, int offCode) {
	        PreparedStatement ps = null;
	        String sql = null;
	        try {

	            sql = "UPDATE " + TableList.VA_OWNER_EX_ARMY + " \n"
	                    + "   SET state_cd = ?,off_cd = ?,appl_no=?, voucher_no=?, voucher_dt=?, place=?,op_dt = current_timestamp\n"
	                    + " WHERE appl_no=?";
	            ps = tmgr.prepareStatement(sql);
	            int i = 1;
	            ps.setString(i++, userStateCode);
	            ps.setInt(i++, offCode);
	            ps.setString(i++, appl_no);
	            ps.setString(i++, dobj.getTf_Voucher_no());
	            ps.setDate(i++, new java.sql.Date(dobj.getTf_VoucherDate().getTime()));
	            ps.setString(i++, dobj.getTf_POP());
	            ps.setString(i++, appl_no);
	            ps.executeUpdate();

	        } catch (SQLException e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        }
	    }

	 private  void insertExArmy(TransactionManagerOne tmgr, ExArmyDobj dobj, String appl_no,
	            String userStateCode, int offCode) {
	        PreparedStatement ps = null;
	        String sql = null;
	        try {

	            sql = "INSERT INTO " + TableList.VA_OWNER_EX_ARMY + " (\n"
	                    + "     state_cd,off_cd,appl_no, voucher_no, voucher_dt, place,op_dt)\n"
	                    + "    VALUES (?,?,?, ?, ?, ?,current_timestamp)";
	            ps = tmgr.prepareStatement(sql);
	            int i = 1;
	            ps.setString(i++, userStateCode);
	            ps.setInt(i++, offCode);
	            ps.setString(i++, appl_no);
	            ps.setString(i++, dobj.getTf_Voucher_no());
	            ps.setDate(i++, new java.sql.Date(dobj.getTf_VoucherDate().getTime()));
	            ps.setString(i++, dobj.getTf_POP());
	            ps.executeUpdate();

	        } catch (SQLException e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        }
	    }
	 public  void deleteFromVaExArmy(TransactionManagerOne tmgr, String applNo) throws VahanException {
	        PreparedStatement ps;
	        String sql;
	        try {
	            sql = "Delete from " + TableList.VA_OWNER_EX_ARMY + " where appl_no = ?";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, applNo);
	            ps.executeUpdate();

	        } catch (SQLException e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException(e.getMessage());
	        }
	    }
	    
}

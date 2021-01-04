package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;
import java.util.Date;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.form.dobj.CdDobj;
@Service
public class CdImpl {
	  private static final Logger LOGGER = Logger.getLogger(CdImpl.class);
	  @Autowired
	 TransactionManagerReadOnly tmgr;
	  public CdDobj getVaCdDobj(String applNo) throws VahanException {
	        CdDobj dobj = null;
	       // TransactionManagerOne tmgr = null;
	        String sql = "Select * from " + TableList.VA_CD_REGN_DTL + " where appl_no=? ";
	        try {
	         //   tmgr = new TransactionManagerOne("getVaCdDobj");
	            PreparedStatement ps = tmgr.prepareStatement(sql);
	            ps.setString(1, applNo);
	            RowSet rs = tmgr.fetchDetachedRowSet_No_release();
	            if (rs.next()) {
	                dobj = new CdDobj();
	                dobj.setState_cd(rs.getString("state_cd"));
	                dobj.setOff_cd(rs.getInt("off_cd"));
	                dobj.setApplNo(rs.getString("appl_no"));
	                dobj.setRegNo(rs.getString("regn_no"));
	                dobj.setCdRegnNo(rs.getString("cd_regn_no"));
	                dobj.setSaleDate(rs.getDate("sale_dt"));
	            }
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Error in CD Vehicles Details");
	        } finally {
	            if (tmgr != null) {
	                try {
	                    tmgr.release();
	                } catch (Exception ex) {
	                    LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	                }
	            }
	        }

	        return dobj;
	    }
	  public void insertOrUpdateCdVehicleDtls(CdDobj dobj, TransactionManagerOne tmgr) throws VahanException {
	        try {
	            PreparedStatement ps = null;
	            RowSet rs = null;
	            String sql = " Select *  from " + TableList.VA_CD_REGN_DTL + " where appl_no=? ";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, dobj.getApplNo());
	            rs = tmgr.fetchDetachedRowSet();
	            if (rs.next()) {
	                updateVaCd(dobj, tmgr);
	            } else {
	                insertIntoVaCd(dobj, tmgr);
	            }
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Error in Insertion/Updation of CD details ");
	        }
	    }
	  public void updateVaCd(CdDobj dobj, TransactionManagerOne tmgr) throws VahanException {

	        String sql = "UPDATE " + TableList.VA_CD_REGN_DTL
	                + "   SET state_cd=?,off_cd=?, appl_no=?, regn_no=?, cd_regn_no=?, sale_dt=?,op_dt = current_timestamp"
	                + " WHERE appl_no=?";

	        try {
	            int i = 1;
	            PreparedStatement ps = tmgr.prepareStatement(sql);
	            ps.setString(i++, dobj.getState_cd());
	            ps.setInt(i++, dobj.getOff_cd());
	            ps.setString(i++, dobj.getApplNo());
	            ps.setString(i++, dobj.getRegNo());
	            ps.setString(i++, dobj.getCdRegnNo());
	            ps.setDate(i++, new java.sql.Date(dobj.getSaleDate().getTime()));
	            ps.setString(i++, dobj.getApplNo());
	            ps.executeUpdate();
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Error in Insertion of CD details ");
	        }


	    }
	  public void insertIntoVaCd(CdDobj dobj, TransactionManagerOne tmgr) throws VahanException {
	        String sql = "INSERT INTO " + TableList.VA_CD_REGN_DTL + "(state_cd,off_cd,appl_no, regn_no, cd_regn_no, sale_dt,op_dt)"
	                + " VALUES (?,?,?, ?, ?, ?,current_timestamp)";
	        try {
	            int i = 1;
	            PreparedStatement ps = tmgr.prepareStatement(sql);
	            ps.setString(i++, dobj.getState_cd());
	            ps.setInt(i++, dobj.getOff_cd());
	            ps.setString(i++, dobj.getApplNo());
	            ps.setString(i++, dobj.getRegNo());
	            ps.setString(i++, dobj.getCdRegnNo() == null ? "" : dobj.getCdRegnNo());
	            if (dobj.getSaleDate() == null) {
	                ps.setDate(i++, new java.sql.Date(new Date().getTime()));
	            } else {
	                ps.setDate(i++, new java.sql.Date(dobj.getSaleDate().getTime()));
	            }
	            ps.executeUpdate();
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Error in Insertion of CD details ");
	        }

	    }
}

package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
import nic.vahan5.reg.form.dobj.DefacementDobj;
import nic.vahan5.reg.server.TableConstants;
@Service
public class DefacementImplementation {
	 private static final Logger LOGGER = Logger.getLogger(DefacementImplementation.class);
	 public DefacementDobj getApplPaymentDetails(TransactionManagerOne tmgr, String appl_no, String state_cd) throws VahanException {
	        DefacementDobj defacementDobj = null;

	        PreparedStatement ps = null;
	        try {
	            String sql = "SELECT a.rcpt_no,b.instrument_no,b.instrument_type,b.instrument_amt from vp_appl_rcpt_mapping a\n"
	                    + "  INNER JOIN vt_instruments b on  a.instrument_cd=b.instrument_cd and a.state_cd=b.state_cd\n"
	                    + "  where a.state_cd=? and a.appl_no =?\n"
	                    + "  union all \n"
	                    + "  SELECT distinct b.return_rcpt_no,b.treasury_ref_no,'',b.rcpt_amt from vph_rcpt_cart a\n"
	                    + "  INNER JOIN vahanpgi.vp_pgi_details b on  a.transaction_no=b.payment_id \n"
	                    + "  where  a.state_cd= ? and a.appl_no = ?";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, state_cd);
	            ps.setString(2, appl_no);
	            ps.setString(3, state_cd);
	            ps.setString(4, appl_no);
	            RowSet rs = tmgr.fetchDetachedRowSet_No_release();
	            if (rs.next()) {
	                defacementDobj = new DefacementDobj();
	                defacementDobj.setGrn_no(rs.getString("instrument_no"));
	                defacementDobj.setAmount(rs.getInt("instrument_amt"));
	                defacementDobj.setInsturment_type(rs.getString("instrument_type"));
	                defacementDobj.setRcpt_no(rs.getString("rcpt_no"));
	                defacementDobj.setAppl_no(appl_no);
	            }
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException(TableConstants.SomthingWentWrong);
	        }
	        return defacementDobj;
	    }
	 public void insertIntoDefacement(TransactionManagerOne tmgr, DefacementDobj dobj, String regn_no, String stateCode, int offCode, String empCode) throws VahanException {
	        PreparedStatement ps = null;
	        String sql = null;
	        int i = 1;
	        try {
	            sql = "SELECT state_cd, off_cd, appl_no from " + TableList.VA_DEFACEMENT + "  where state_cd=? and  appl_no = ?";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, stateCode);
	            ps.setString(2, dobj.getAppl_no());
	            RowSet rs = tmgr.fetchDetachedRowSet_No_release();
	            if (!rs.next()) {
	                sql = "INSERT INTO " + TableList.VA_DEFACEMENT + " (\n"
	                        + "            state_cd ,off_cd ,regn_no ,appl_no ,grn_no ,rcpt_no ,insturment_type ,amount ,merchant_code ,"
	                        + " refrence_no  ,user_cd ,op_dt )\n"
	                        + "    VALUES (?, ?, ?, ?, ?, ? ,? ,? ,? ,? ,? ,current_timestamp)";

	                ps = tmgr.prepareStatement(sql);
	                ps.setString(i++, stateCode);
	                ps.setInt(i++, offCode);
	                ps.setString(i++, regn_no);
	                ps.setString(i++, dobj.getRefrence_no());
	                ps.setString(i++, dobj.getGrn_no());
	                ps.setString(i++, dobj.getRcpt_no());
	                ps.setString(i++, dobj.getInsturment_type());
	                ps.setInt(i++, dobj.getAmount());
	                ps.setString(i++, dobj.getMerchant_code());
	                ps.setString(i++, dobj.getRefrence_no());
	                ps.setString(i++, empCode);
	                ps.executeUpdate();
	            }
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException(TableConstants.SomthingWentWrong);
	        }
	    }
	
}

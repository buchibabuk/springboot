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
import nic.vahan5.reg.form.dobj.AxleDetailsDobj;
import nic.vahan5.reg.server.TableConstants;
@Service
public class AxleImplementation {
	  private static final Logger LOGGER = Logger.getLogger(AxleImplementation.class);
	 @Autowired
	 TransactionManagerReadOnly tmgr;
	  
	  public  AxleDetailsDobj setAxleVehDetails_db_to_dobj(String appl_no, String regnNo, String state_cd, int off_cd) {

	        AxleDetailsDobj dobj = null;
	      //  TransactionManagerReadOnly tmgr = null;
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
	  
	  public  void insertIntoVhaAxle(TransactionManagerOne tmgr, String appl_no, String empCode) throws SQLException {
	        PreparedStatement ps = null;
	        String sql = null;

	        sql = "INSERT INTO " + TableList.VHA_AXLE
	                + " (moved_on, moved_by, state_cd, off_cd, appl_no, f_axle_descp, r_axle_descp, o_axle_descp, "
	                + "   t_axle_descp, f_axle_weight, r_axle_weight, o_axle_weight,t_axle_weight, op_dt, no_of_axles,r_overhang, f_axle_tyre, r_axle_tyre, o_axle_tyre, t_axle_tyre ) "
	                + " SELECT current_timestamp as moved_on, ? as moved_by,state_cd, off_cd, appl_no, f_axle_descp, r_axle_descp, o_axle_descp, "
	                + "  t_axle_descp, f_axle_weight, r_axle_weight, o_axle_weight, t_axle_weight, op_dt, no_of_axles, r_overhang, f_axle_tyre, r_axle_tyre, o_axle_tyre, t_axle_tyre"
	                + "  FROM " + TableList.VA_AXLE + " where appl_no=?";

	        ps = tmgr.prepareStatement(sql);
	        ps.setString(1, empCode);
	        ps.setString(2, appl_no);
	        ps.executeUpdate();
	    }
	  public  void deleteFromVaAxle(TransactionManagerOne tmgr, String applNo) throws VahanException {
	        PreparedStatement ps;
	        String sql;
	        try {
	            sql = "Delete from " + TableList.VA_AXLE + " where appl_no = ?";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, applNo);
	            ps.executeUpdate();

	        } catch (SQLException e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException(e.getMessage());
	        }
	    }

	  public  void saveAxleDetails_Impl(AxleDetailsDobj dobj, String appl_no, TransactionManagerOne tmgr,
	            String empCode, String userStateCode, int offCode) throws SQLException, VahanException {
	        PreparedStatement ps = null;
	        String sql = null;
	        boolean allow = true;
	        sql = "SELECT appl_no FROM " + TableList.VA_AXLE + " where appl_no = ?";
	        ps = tmgr.prepareStatement(sql);
	        ps.setString(1, appl_no);
	        RowSet rs = tmgr.fetchDetachedRowSet_No_release();
	        
	        sql = "SELECT vh_class,regn_type FROM " + TableList.VA_OWNER + " where appl_no = ?";
	        ps = tmgr.prepareStatement(sql);
	        ps.setString(1, appl_no);
	        RowSet rs1 = tmgr.fetchDetachedRowSet_No_release();
	        if (rs1.next()) {
	            if ("CG".equals(userStateCode) && dobj.getNoOfAxle() == 1 && rs1.getInt("vh_class") == 89
	                    && (TableConstants.VM_REGN_TYPE_NEW.equals(rs1.getString("regn_type"))
	                    || TableConstants.VM_REGN_TYPE_TEMPORARY.equals(rs1.getString("regn_type")))) {
	                allow = false;
	            }
	        }

	        if (dobj.getNoOfAxle() < 2 && allow) {
	            throw new VahanException("No of Axle's should be equal to or more than 2.");
	        }
	        if (rs.next()) { //if any record is exist then update otherwise insert it
	            insertIntoVhaAxle(tmgr, appl_no, empCode);
	            updateVaAxle(tmgr, dobj, appl_no);
	        } else {
	            insertVaAxle(tmgr, dobj, appl_no, userStateCode, offCode);
	        }
	    }
	  private static void updateVaAxle(TransactionManagerOne tmgr, AxleDetailsDobj dobj, String appl_no) throws SQLException {
	        PreparedStatement ps = null;
	        String sql = null;

	        sql = "UPDATE " + TableList.VA_AXLE + " \n"
	                + "   SET f_axle_descp=?, r_axle_descp=?, o_axle_descp=?, t_axle_descp=?, "
	                + "       f_axle_weight=?, r_axle_weight=?, o_axle_weight=?, t_axle_weight=?, op_dt = current_timestamp,no_of_axles = ?, r_overhang=?, f_axle_tyre=?, r_axle_tyre=?, o_axle_tyre=?, t_axle_tyre=? "
	                + " WHERE appl_no=?";
	        ps = tmgr.prepareStatement(sql);
	        ps.setString(1, dobj.getTf_Front1());
	        ps.setString(2, dobj.getTf_Rear1());
	        ps.setString(3, dobj.getTf_Other1());
	        ps.setString(4, dobj.getTf_Tandem1());
	        ps.setInt(5, dobj.getTf_Front());
	        ps.setInt(6, dobj.getTf_Rear());
	        ps.setInt(7, dobj.getTf_Other());
	        ps.setInt(8, dobj.getTf_Tandem());
	        ps.setInt(9, dobj.getNoOfAxle());
	        ps.setInt(10, dobj.getTf_Rear_Over());
	        ps.setInt(11, dobj.getTf_Front_tyre());
	        ps.setInt(12, dobj.getTf_Rear_tyre());
	        ps.setInt(13, dobj.getTf_Other_tyre());
	        ps.setInt(14, dobj.getTf_Tandem_tyre());
	        ps.setString(15, appl_no);

	        ps.executeUpdate();
	    }

	  private static void insertVaAxle(TransactionManagerOne tmgr, AxleDetailsDobj dobj, String appl_no,
	            String userStateCode, int offCode) throws SQLException {
	        int i = 1;
	        PreparedStatement ps = null;
	        String sql = "INSERT INTO " + TableList.VA_AXLE + " ("
	                + "            state_cd, off_cd, appl_no, f_axle_descp, r_axle_descp, o_axle_descp, t_axle_descp,  \n"
	                + "            f_axle_weight, r_axle_weight, o_axle_weight, t_axle_weight, op_dt,no_of_axles, r_overhang, f_axle_tyre, r_axle_tyre, o_axle_tyre, t_axle_tyre)\n"
	                + "    VALUES (?, ?, ?, ?, ?, ?, ?, \n"
	                + "            ?, ?, ?, ?, current_timestamp,?,?,?,?,?,?)";

	        ps = tmgr.prepareStatement(sql);
	        ps.setString(i++, userStateCode);
	        ps.setInt(i++, offCode);
	        ps.setString(i++, appl_no);
	        ps.setString(i++, dobj.getTf_Front1());
	        ps.setString(i++, dobj.getTf_Rear1());
	        ps.setString(i++, dobj.getTf_Other1());
	        ps.setString(i++, dobj.getTf_Tandem1());
	        ps.setInt(i++, dobj.getTf_Front());
	        ps.setInt(i++, dobj.getTf_Rear());
	        ps.setInt(i++, dobj.getTf_Other());
	        ps.setInt(i++, dobj.getTf_Tandem());
	        ps.setInt(i++, dobj.getNoOfAxle());
	        ps.setInt(i++, dobj.getTf_Rear_Over());
	        ps.setInt(i++, dobj.getTf_Front_tyre());
	        ps.setInt(i++, dobj.getTf_Rear_tyre());
	        ps.setInt(i++, dobj.getTf_Other_tyre());
	        ps.setInt(i++, dobj.getTf_Tandem_tyre());

	        ps.executeUpdate();
	    }
	    
}

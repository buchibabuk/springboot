package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.java.util.CommonUtils;
import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.form.dobj.Trailer_dobj;
import nic.vahan5.reg.server.TableConstants;
@Service
public class TrailerImplementation {
	
	@Autowired
	TransactionManagerReadOnly tmgr;
	
	private static Logger LOGGER = Logger.getLogger(TrailerImplementation.class);
	public  void validationTrailer(Trailer_dobj dobj) throws VahanException {

        try {
            if (CommonUtils.isNullOrBlank(dobj.getBody_type())) {
                throw new VahanException("Enter Trailer Body Type");
            }

            if (dobj.getLd_wt() <= 0 || dobj.getUnld_wt() <= 0) {
                throw new VahanException("Enter Trailer Laden/UnLaden Weight");
            }

            if (CommonUtils.isNullOrBlank(dobj.getF_axle_descp())) {
                throw new VahanException("Enter Trailer Front Axle Desc");
            }

            if (CommonUtils.isNullOrBlank(dobj.getR_axle_descp())) {
                throw new VahanException("Enter Trailer Rear Axle Desc");
            }

            if (dobj.getR_axle_weight() <= 0 || dobj.getF_axle_weight() <= 0) {
                throw new VahanException("Enter Trailer Front/Rear Weight");
            }
        } catch (VahanException ex) {
            throw ex;
        } catch (Exception ex) {
            LOGGER.error(ex.toString() + "-" + ex.getStackTrace()[0]);
            throw new VahanException(TableConstants.SomthingWentWrong);
        }
    }
	public  Trailer_dobj checkTrailerChassis_owner(String chassis_no) {
        PreparedStatement ps = null;
        //TransactionManagerReadOnly tmgr = null;
        Trailer_dobj dobj = null;
        try {
           // tmgr = new TransactionManagerReadOnly("Trailer_Impl");
            ps = tmgr.prepareStatement(" select a.chasi_no,a.regn_no,a.state_cd,a.off_cd from"
                    + "((select chasi_no,regn_no,state_cd,off_cd from " + TableList.VT_OWNER
                    + " where chasi_no=? )"
                    + " UNION ALL "
                    + " (select chasi_no,regn_no,state_cd,off_cd from " + TableList.VA_OWNER
                    + " where chasi_no=? )"
                    + " ) a ");
            ps.setString(1, chassis_no);
            ps.setString(2, chassis_no);
            RowSet rs = tmgr.fetchDetachedRowSet();
            if (rs.next()) // found
            {
                dobj = new Trailer_dobj();
                dobj.setDup_chassis(rs.getString("chasi_no"));
                dobj.setRegn_no(rs.getString("regn_no"));
                dobj.setState_cd(rs.getString("state_cd"));
                dobj.setOff_cd(rs.getInt("off_cd"));
            }
        } catch (Exception ex) {
            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
        } finally {
            try {
                if (tmgr != null) {
                    tmgr.release();
                }
            } catch (Exception e) {
                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            }
        }
        return dobj;
    }
	public  void insertUpdateTrailer(TransactionManagerOne tmgr, String appl_no, String regn_no, String chasi_no, 
            Trailer_dobj trailer_dobj, String empCode, String userStateCode, int offCode) throws VahanException {
        PreparedStatement ps = null;
        String sql = null;
        try {

            sql = "SELECT * FROM va_trailer where appl_no = ?";
            ps = tmgr.prepareStatement(sql);
            ps.setString(1, appl_no);
            RowSet rs = tmgr.fetchDetachedRowSet_No_release();

            if (rs.next()) { //if any record is exist then update otherwise insert it
                if (trailer_dobj != null) {
                    insertIntoTrailerHistory(tmgr, appl_no, empCode);
                    updateTrailer(tmgr, trailer_dobj, appl_no);
                }

            } else {
                if (trailer_dobj != null) {
                    insertIntoTrailer(tmgr, trailer_dobj, appl_no, regn_no, chasi_no, userStateCode, offCode);
                }
            }

        } catch (SQLException ex) {
            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
            throw new VahanException(TableConstants.SomthingWentWrong);
        }
    } // end of insertUpdateTrailer

	public  Trailer_dobj checkTrailerChassis_trailer(String chassis_no) {
        PreparedStatement ps = null;
      //  TransactionManagerReadOnly tmgr = null;
        Trailer_dobj dobj = null;
        try {
         //   tmgr = new TransactionManagerReadOnly("Trailer_Impl");
            ps = tmgr.prepareStatement(" select a.chasi_no,a.regn_no,a.state_cd,a.off_cd from"
                    + "((select chasi_no,regn_no,state_cd,off_cd from " + TableList.VT_TRAILER
                    + " where chasi_no=? )"
                    + " UNION ALL "
                    + " (select chasi_no,regn_no,state_cd,off_cd from " + TableList.VA_TRAILER
                    + " where chasi_no=? )"
                    + " ) a ");
            ps.setString(1, chassis_no);
            ps.setString(2, chassis_no);
            RowSet rs = tmgr.fetchDetachedRowSet();
            if (rs.next()) // found
            {
                dobj = new Trailer_dobj();
                dobj.setDup_chassis(rs.getString("chasi_no"));
                dobj.setRegn_no(rs.getString("regn_no"));
                dobj.setState_cd(rs.getString("state_cd"));
                dobj.setOff_cd(rs.getInt("off_cd"));
            }
        } catch (Exception ex) {
            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
        } finally {
            try {
                if (tmgr != null) {
                    tmgr.release();
                }
            } catch (Exception e) {
                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            }
        }
        return dobj;
    }
	public static void insertIntoTrailerHistory(TransactionManagerOne tmgr, String appl_no, String empCode) throws VahanException {
        PreparedStatement ps = null;
        String sql = null;

        try {
            sql = "INSERT INTO vha_trailer "
                    + " SELECT current_timestamp as moved_on, ? as moved_by, state_cd, off_cd, appl_no, regn_no, sr_no, chasi_no, body_type, ld_wt, unld_wt, f_axle_descp, "
                    + "       r_axle_descp, o_axle_descp, t_axle_descp, f_axle_weight, r_axle_weight, "
                    + "       o_axle_weight, t_axle_weight, op_dt"
                    + "  FROM va_trailer where appl_no=?";
            ps = tmgr.prepareStatement(sql);
            ps.setString(1, empCode);
            ps.setString(2, appl_no);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new VahanException(sqle.getMessage());
        }
    } // end of insertIntoHPAHistory
	public static void updateTrailer(TransactionManagerOne tmgr, Trailer_dobj dobj, String appl_no) throws VahanException {
        PreparedStatement ps = null;
        String sql = null;
        try {
            sql = "UPDATE va_trailer"
                    + "   SET body_type=?, chasi_no=?, ld_wt=?, unld_wt=?,"
                    + "       f_axle_descp=?, r_axle_descp=?, o_axle_descp=?, t_axle_descp=?, "
                    + "       f_axle_weight=?, r_axle_weight=?, o_axle_weight=?, t_axle_weight=? "
                    + " WHERE appl_no=?";
            ps = tmgr.prepareStatement(sql);
            ps.setString(1, dobj.getBody_type());
            ps.setString(2, dobj.getChasi_no());
            ps.setInt(3, dobj.getLd_wt());
            ps.setInt(4, dobj.getUnld_wt());
            ps.setString(5, dobj.getF_axle_descp());
            ps.setString(6, dobj.getR_axle_descp());
            ps.setString(7, dobj.getO_axle_descp());
            ps.setString(8, dobj.getT_axle_descp());
            ps.setInt(9, dobj.getF_axle_weight());
            ps.setInt(10, dobj.getR_axle_weight());
            ps.setInt(11, dobj.getO_axle_weight());
            ps.setInt(12, dobj.getT_axle_weight());
            ps.setString(13, appl_no);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new VahanException(sqle.getMessage());
        }
    } // end of updateHPA
	public static void insertIntoTrailer(TransactionManagerOne tmgr, Trailer_dobj dobj, String appl_no, String regn_no, 
            String chasi_no, String userStateCode, int offCode) throws VahanException {
        PreparedStatement ps = null;
        String sql = null;

        try {


            sql = "INSERT INTO va_trailer(state_cd , off_cd, appl_no, regn_no, sr_no, chasi_no, body_type, ld_wt, unld_wt, f_axle_descp,"
                    + " r_axle_descp, o_axle_descp, t_axle_descp, f_axle_weight, r_axle_weight,"
                    + " o_axle_weight, t_axle_weight, op_dt) "
                    + " VALUES (?, ?, ?, ?, 1, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current_timestamp)";
            ps = tmgr.prepareStatement(sql);
            ps.setString(1, userStateCode);
            ps.setInt(2, offCode);
            ps.setString(3, appl_no);
            ps.setString(4, regn_no);
            ps.setString(5, dobj.getChasi_no());
            ps.setString(6, dobj.getBody_type());
            ps.setInt(7, dobj.getLd_wt());
            ps.setInt(8, dobj.getUnld_wt());
            ps.setString(9, dobj.getF_axle_descp());
            ps.setString(10, dobj.getR_axle_descp());
            ps.setString(11, dobj.getO_axle_descp());
            ps.setString(12, dobj.getT_axle_descp());
            ps.setInt(13, dobj.getF_axle_weight());
            ps.setInt(14, dobj.getR_axle_weight());
            ps.setInt(15, dobj.getO_axle_weight());
            ps.setInt(16, dobj.getT_axle_weight());
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new VahanException(sqle.getMessage());
        }
    } // end of insertIntoHPA

    
}

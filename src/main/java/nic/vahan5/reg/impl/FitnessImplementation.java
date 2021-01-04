/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.sql.RowSet;
import nic.java.util.DateUtils;
import nic.vahan.common.jsf.utils.JSFUtils;
import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.form.dobj.FitnessDobj;
import nic.vahan5.reg.form.dobj.ReflectiveTapeDobj;
import nic.vahan5.reg.form.dobj.RetroFittingDetailsDobj;
import nic.vahan5.reg.form.dobj.SpeedGovernorDobj;
import nic.vahan5.reg.form.dobj.VehicleTrackingDetailsDobj;
import nic.vahan5.reg.server.TableConstants;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kartikey Singh
 */
@Service
public class FitnessImplementation {

    private static final Logger LOGGER = Logger.getLogger(FitnessImplementation.class);
    public  void insertIntoVhaSpeedGovernor(String applNo, TransactionManagerOne tmgr, String empCode) throws VahanException {
        String sql = "INSERT INTO " + TableList.VHA_SPEED_GOVERNOR
                + " SELECT current_timestamp, ?, * FROM " + TableList.VA_SPEED_GOVERNOR + " WHERE appl_no=? ";
        try {
            int i = 1;
            PreparedStatement ps = tmgr.prepareStatement(sql);
            ps.setString(i++, empCode);
            ps.setString(i++, applNo);
            ps.executeUpdate();

        } catch (Exception ee) {
            LOGGER.error(ee.toString() + " " + ee.getStackTrace()[0]);
            throw new VahanException(TableConstants.SomthingWentWrong);
        }

    }


    public ReflectiveTapeDobj getVaReflectiveTapeDobj(String applNo, TransactionManagerOne tmgr) throws SQLException {
        ReflectiveTapeDobj refDobj = null;
        String sql = "SELECT * FROM " + TableList.VA_REFLECTIVE_TAPE + " where appl_no =?";
        PreparedStatement ps = tmgr.prepareStatement(sql);
        ps.setString(1, applNo);
        RowSet rs = tmgr.fetchDetachedRowSet_No_release();
        if (rs.next()) {
            refDobj = new ReflectiveTapeDobj();
            refDobj.setRegn_no(rs.getString("regn_no"));
            refDobj.setApplNo(rs.getString("appl_no"));
            refDobj.setCertificateNo(rs.getString("certificate_no"));
            refDobj.setFitmentDate(rs.getDate("fitment_date"));
            refDobj.setManuName(rs.getString("manu_name"));
        }
        return refDobj;
    }

    public ReflectiveTapeDobj getVtReflectiveTapeDobj(String regn_no, String stateCd, int offCd, TransactionManagerOne tmgr) throws SQLException {
        ReflectiveTapeDobj refDobj = null;
        String sql = "SELECT state_cd ,  off_cd ,  regn_no , certificate_no ,  fitment_date ,  manu_name "
                + " from " + TableList.VT_REFLECTIVE_TAPE + " where  regn_no=? order by op_dt desc limit 1";
        PreparedStatement ps = tmgr.prepareStatement(sql);
        ps.setString(1, regn_no);
        RowSet rs = tmgr.fetchDetachedRowSet_No_release();
        if (rs.next()) {
            refDobj = new ReflectiveTapeDobj();
            refDobj.setStateCd(rs.getString("state_cd"));
            refDobj.setOffCd(rs.getInt("off_cd"));
            refDobj.setRegn_no(rs.getString("regn_no"));
            refDobj.setCertificateNo(rs.getString("certificate_no"));
            refDobj.setFitmentDate(rs.getDate("fitment_date"));
            refDobj.setManuName(rs.getString("manu_name"));
        }
        return refDobj;
    }
    public void insertIntoVhaReflectiveTape(TransactionManagerOne tmgr, String applNo, String empCode) throws VahanException {
        String sql = null;
        try {
            int i = 1;
            sql = "INSERT INTO " + TableList.VHA_REFLECTIVE_TAPE
                    + " SELECT current_timestamp, ?, * FROM " + TableList.VA_REFLECTIVE_TAPE + " WHERE appl_no=?";
            PreparedStatement ps = tmgr.prepareStatement(sql);
            ps.setString(i++, empCode);
            ps.setString(i++, applNo);
            ps.executeUpdate();
        } catch (Exception ee) {
            LOGGER.error(ee.toString() + "[appNo-" + applNo + "] " + ee.getStackTrace()[0]);
            throw new VahanException(TableConstants.SomthingWentWrong);
        }
    }
    
    public  void deleteVaSpeedGovernor(String applNo, TransactionManagerOne tmgr) throws VahanException {
        String sql = "DELETE FROM " + TableList.VA_SPEED_GOVERNOR + "  WHERE appl_no=? ";
        try {
            int i = 1;
            PreparedStatement ps = tmgr.prepareStatement(sql);
            ps.setString(i++, applNo);
            ps.executeUpdate();

        } catch (Exception ee) {
            LOGGER.error(ee.toString() + " " + ee.getStackTrace()[0]);
            throw new VahanException(TableConstants.SomthingWentWrong);
        }

    }

    public void deleteVaReflectiveTape(String applNo, TransactionManagerOne tmgr) throws VahanException {
        String sql = "DELETE FROM " + TableList.VA_REFLECTIVE_TAPE + "  WHERE appl_no=? ";
        try {
            int i = 1;
            PreparedStatement ps = tmgr.prepareStatement(sql);
            ps.setString(i++, applNo);
            ps.executeUpdate();
        } catch (Exception ee) {
            LOGGER.error(ee.toString() + " " + ee.getStackTrace()[0]);
            throw new VahanException(TableConstants.SomthingWentWrong);
        }
    }
    public  void insertUpdateVaSpeedGovernor(SpeedGovernorDobj dobj, TransactionManagerOne tmgr, String empCode) throws VahanException {

        try {
            if (dobj == null) {
                return;
            }
            String sql = "SELECT * FROM " + TableList.VA_SPEED_GOVERNOR + " WHERE appl_no=?";
            PreparedStatement ps = tmgr.prepareStatement(sql);
            ps.setString(1, dobj.getAppl_no());
            RowSet rs = tmgr.fetchDetachedRowSet_No_release();
            if (rs.next()) {
                insertIntoVhaSpeedGovernor(dobj.getAppl_no(), tmgr, empCode);
                updateVaSpeedGovernor(dobj, tmgr);
            } else {
                insertIntoVaSpeedGovernor(dobj, tmgr, empCode);
            }
        } catch (VahanException ve) {
            throw ve;
        } catch (Exception ee) {
            LOGGER.error(ee.toString() + " " + ee.getStackTrace()[0]);
            throw new VahanException(TableConstants.SomthingWentWrong);
        }
    }
    public static void updateVaSpeedGovernor(SpeedGovernorDobj dobj, TransactionManagerOne tmgr) throws VahanException {
        String sql = "UPDATE " + TableList.VA_SPEED_GOVERNOR
                + " SET appl_no=?, state_cd=?, off_cd=?,  sg_no=?, sg_fitted_on=?, "
                + " sg_fitted_at=?,op_dt=current_timestamp,"
                + " sg_type=?,sg_type_approval_no=?,sg_test_report_no=?,sg_fitment_cert_no=? "
                + " WHERE appl_no=? ";
        try {
            int i = 1;
            PreparedStatement ps = tmgr.prepareStatement(sql);
            ps.setString(i++, dobj.getAppl_no());
            ps.setString(i++, dobj.getState_cd());
            ps.setInt(i++, dobj.getOff_cd());
            //ps.setString(i++, dobj.getRegn_no());
            ps.setString(i++, dobj.getSg_no());
            ps.setDate(i++, new java.sql.Date(dobj.getSg_fitted_on().getTime()));
            ps.setString(i++, dobj.getSg_fitted_at());
            ps.setInt(i++, dobj.getSgGovType());
            ps.setString(i++, dobj.getSgTypeApprovalNo());
            ps.setString(i++, dobj.getSgTestReportNo());
            ps.setString(i++, dobj.getSgFitmentCerticateNo());

            ps.setString(i++, dobj.getAppl_no());
            ps.executeUpdate();

        } catch (Exception ee) {
            LOGGER.error(ee.toString() + " " + ee.getStackTrace()[0]);
            throw new VahanException("Error in Update Speed Governor Details");
        }
    }

    public static void insertIntoVaSpeedGovernor(SpeedGovernorDobj dobj, TransactionManagerOne tmgr, String empCode) throws VahanException {

        String sql = "INSERT INTO " + TableList.VA_SPEED_GOVERNOR
                + " (appl_no, state_cd, off_cd, regn_no, sg_no, sg_fitted_on, sg_fitted_at,op_dt, emp_cd,"
                + "  sg_type,sg_type_approval_no,sg_test_report_no,sg_fitment_cert_no) "
                + " VALUES (?, ?, ?, ?, ?, ?, ?, current_timestamp, ?,"
                + "?,?,?,?)";
        try {
            int i = 1;
            PreparedStatement ps = tmgr.prepareStatement(sql);
            ps.setString(i++, dobj.getAppl_no());
            ps.setString(i++, dobj.getState_cd());
            ps.setInt(i++, dobj.getOff_cd());
            ps.setString(i++, dobj.getRegn_no());
            ps.setString(i++, dobj.getSg_no());
            ps.setDate(i++, new java.sql.Date(dobj.getSg_fitted_on().getTime()));
            ps.setString(i++, dobj.getSg_fitted_at());
            ps.setString(i++, empCode);
            ps.setInt(i++, dobj.getSgGovType());
            ps.setString(i++, dobj.getSgTypeApprovalNo());
            ps.setString(i++, dobj.getSgTestReportNo());
            ps.setString(i++, dobj.getSgFitmentCerticateNo());
            ps.executeUpdate();

        } catch (Exception ee) {
            LOGGER.error(ee.toString() + " " + ee.getStackTrace()[0]);
            throw new VahanException(TableConstants.SomthingWentWrong);
        }
    }
    public void insertOrUpdateVaReflectiveTape(TransactionManagerOne tmgr, ReflectiveTapeDobj refDobj, String empCode) throws Exception {

        if (refDobj == null) {
            return;
        }
        ReflectiveTapeDobj refDobjExist = getVaReflectiveTapeDobj(refDobj.getApplNo(), tmgr);
        if (refDobjExist != null) {
            insertIntoVhaReflectiveTape(tmgr, refDobj.getApplNo(), empCode);
            updateVaReflectiveTape(tmgr, refDobj);
        } else {
            insertVaReflectiveTape(tmgr, refDobj);
        }
    }
    public void updateVaReflectiveTape(TransactionManagerOne tmgr, ReflectiveTapeDobj refDobj) throws VahanException {
        String sql = "UPDATE " + TableList.VA_REFLECTIVE_TAPE
                + " SET  certificate_no=?, fitment_date=?, manu_name=?,op_dt=current_timestamp  "
                + " WHERE appl_no=?";
        try {
            int i = 1;
            PreparedStatement ps = tmgr.prepareStatement(sql);
            ps.setString(i++, refDobj.getCertificateNo());
            ps.setDate(i++, new java.sql.Date(refDobj.getFitmentDate().getTime()));
            ps.setString(i++, refDobj.getManuName());
            ps.setString(i++, refDobj.getApplNo());
            ps.executeUpdate();
        } catch (Exception ee) {
            LOGGER.error(ee.toString() + "[appNo-" + refDobj.getRegn_no() + "] " + ee.getStackTrace()[0]);
            throw new VahanException(TableConstants.SomthingWentWrong);
        }
    }
    public void insertVaReflectiveTape(TransactionManagerOne tmgr, ReflectiveTapeDobj refDobj) throws VahanException {

        PreparedStatement ps = null;
        String sql = null;
        int pos = 1;
        try {
            sql = "INSERT INTO " + TableList.VA_REFLECTIVE_TAPE
                    + " (appl_no, certificate_no, fitment_date, manu_name, regn_no,op_dt)"
                    + "    VALUES (?, ?, ?, ?, ?,current_timestamp)";
            ps = tmgr.prepareStatement(sql);
            ps.setString(pos++, refDobj.getApplNo());
            ps.setString(pos++, refDobj.getCertificateNo());
            ps.setDate(pos++, new java.sql.Date(refDobj.getFitmentDate().getTime()));
            ps.setString(pos++, refDobj.getManuName());
            ps.setString(pos++, refDobj.getRegn_no());
            ps.executeUpdate();

        } catch (Exception ex) {
            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
            throw new VahanException(TableConstants.SomthingWentWrong);
        }
    }

    
}

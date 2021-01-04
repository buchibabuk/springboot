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
import nic.vahan5.reg.form.dobj.ImportedVehicleDobj;
@Service
public class ImportedVehicleImplementation {
	@Autowired
	TransactionManagerReadOnly tmg;
	private static final Logger LOGGER = Logger.getLogger(ImportedVehicleImplementation.class);
	public  ImportedVehicleDobj setImpVehDetails_db_to_dobj(String appl_no, String regn_no, String state_cd, int off_cd) {
        ImportedVehicleDobj dobj = null;
       
        PreparedStatement ps = null;
        String sql = null;
        String param = "";
        boolean vtFlag = false;
        if (appl_no != null) {
            vtFlag = false;
            param = appl_no.toUpperCase();
            sql = "SELECT contry_code, dealer, place, foreign_regno, manu_year\n"
                    + "  FROM " + TableList.VA_IMPORT_VEH + " where appl_no=?";
        }
        if (regn_no != null) {
            vtFlag = true;
            param = regn_no.toUpperCase();
            sql = "SELECT contry_code, dealer, place, foreign_regno, manu_year\n"
                    + "  FROM " + TableList.VT_IMPORT_VEH + " where regn_no=? and state_cd = ? and off_cd = ? ";
        }

        try {
         //   tmgr = new TransactionManager("setImpVehDetails_db_to_dobj");

            ps = tmg.prepareStatement(sql);
            if (vtFlag) {
                ps.setString(1, param);
                ps.setString(2, state_cd);
                ps.setInt(3, off_cd);
            } else {
                ps.setString(1, param);
            }

            RowSet rs = tmg.fetchDetachedRowSet();
            while (rs.next()) {
                dobj = new ImportedVehicleDobj();
                dobj.setCm_country_imp(rs.getInt("contry_code"));
                dobj.setTf_dealer_imp(rs.getString("dealer"));
                dobj.setTf_place_imp(rs.getString("place"));
                dobj.setTf_foreign_imp(rs.getString("foreign_regno"));
                dobj.setTf_YOM_imp(rs.getInt("manu_year"));

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
	
	public void saveImportedDetails_Impl(ImportedVehicleDobj dobj, String appl_no, TransactionManagerOne tmgr,
            String empCode, String userStateCode, int offCode) {
        PreparedStatement ps = null;
        String sql = null;
        try {

            sql = "SELECT * FROM " + TableList.VA_IMPORT_VEH + " where appl_no = ?";
            ps = tmgr.prepareStatement(sql);
            ps.setString(1, appl_no);
            RowSet rs = tmgr.fetchDetachedRowSet_No_release();

            if (rs.next()) { //if any record is exist then update otherwise insert it
                insertIntoVhaImpVeh(tmgr, appl_no, empCode);
                updateImpVeh(tmgr, dobj, appl_no, userStateCode, offCode);
            } else {
                insertImpVeh(tmgr, dobj, appl_no, userStateCode, offCode);
            }

        } catch (SQLException e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
        }
    }
	public  void insertIntoVhaImpVeh(TransactionManagerOne tmgr, String appl_no, String empCode) {
        PreparedStatement ps = null;
        String sql = null;
        try {

            sql = "INSERT INTO " + TableList.VHA_IMPORT_VEH + " \n"
                    + "SELECT current_timestamp as moved_on, ? as moved_by, state_cd, off_cd,appl_no, contry_code, dealer, place, foreign_regno, manu_year,op_dt \n"
                    + "  FROM va_import_veh where appl_no=?";

            ps = tmgr.prepareStatement(sql);
            ps.setString(1, empCode);
            ps.setString(2, appl_no);
            ps.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
        }
    }

	private static void updateImpVeh(TransactionManagerOne tmgr, ImportedVehicleDobj dobj, String appl_no,
            String userStateCode, int offCode) {
        PreparedStatement ps = null;
        String sql = null;
        try {

            sql = "UPDATE " + TableList.VA_IMPORT_VEH + " \n"
                    + "   SET state_cd = ?,off_cd = ?,appl_no=?, contry_code=?, dealer=?, place=?, foreign_regno=?, \n"
                    + "       manu_year=?,op_dt = current_timestamp\n"
                    + " WHERE appl_no=?";
            ps = tmgr.prepareStatement(sql);
            int i = 1;
            ps.setString(i++, userStateCode);
            ps.setInt(i++, offCode);
            ps.setString(i++, appl_no);
            ps.setInt(i++, dobj.getCm_country_imp());
            ps.setString(i++, dobj.getTf_dealer_imp());
            ps.setString(i++, dobj.getTf_place_imp());
            ps.setString(i++, dobj.getTf_foreign_imp());
            ps.setInt(i++, dobj.getTf_YOM_imp());
            ps.setString(i++, appl_no);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
        }
    }
	private static void insertImpVeh(TransactionManagerOne tmgr, ImportedVehicleDobj dobj, String appl_no, 
            String userStateCode, int offCode) {
        PreparedStatement ps = null;
        String sql = null;
        try {
            sql = "INSERT INTO " + TableList.VA_IMPORT_VEH + " (\n"
                    + "     state_cd,off_cd,appl_no, contry_code, dealer, place, foreign_regno, manu_year,op_dt)\n"
                    + "    VALUES (?, ?, ?, ?, ?, ?,?,?,current_timestamp)";
            ps = tmgr.prepareStatement(sql);
            int i = 1;
            ps.setString(i++, userStateCode);
            ps.setInt(i++, offCode);
            ps.setString(i++, appl_no);
            ps.setInt(i++, dobj.getCm_country_imp());
            ps.setString(i++, dobj.getTf_dealer_imp());
            ps.setString(i++, dobj.getTf_place_imp());
            ps.setString(i++, dobj.getTf_foreign_imp());
            ps.setInt(i++, dobj.getTf_YOM_imp());
            ps.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
        }
    }
	public  void deleteFromVaImp(TransactionManagerOne tmgr, String applNo) throws VahanException {
        PreparedStatement ps;
        String sql;
        try {
            sql = "Delete from " + TableList.VA_IMPORT_VEH + " where appl_no = ?";
            ps = tmgr.prepareStatement(sql);
            ps.setString(1, applNo);
            ps.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            throw new VahanException(e.getMessage());
        }
    }

}

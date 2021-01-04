package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.form.dobj.OtherStateVehDobj;
@Service
public class OtherStateVehImpl {
    private static final Logger LOGGER = Logger.getLogger(OtherStateVehImpl.class);
    @Autowired
    TransactionManagerOne tmgr ;
    public OtherStateVehDobj setOtherVehicleDetailsToDobj(String appl_no) throws VahanException {

       // TransactionManagerOne tmgr = null;
        PreparedStatement ps = null;
        String sql = null;
        OtherStateVehDobj dobj = null;

        try {
         //   tmgr = new TransactionManagerOne("setOtherVehicleDetailsToDobj");

            sql = "SELECT appl_no, old_regn_no, old_off_cd, old_state_cd, ncrb_ref, confirm_ref,"
                    + "       noc_dt, noc_no, state_entry_dt "
                    + "  FROM " + TableList.VA_OTHER_STATE_VEH + " where appl_no=?";
            ps = tmgr.prepareStatement(sql);
            ps.setString(1, appl_no);

            RowSet rs = tmgr.fetchDetachedRowSet();

            if (rs.next()) // found
            {
                dobj = new OtherStateVehDobj();

                dobj.setApplNo(rs.getString("appl_no"));
                dobj.setOldRegnNo(rs.getString("old_regn_no"));
                dobj.setOldOffCD(rs.getInt("old_off_cd"));
                dobj.setOldStateCD(rs.getString("old_state_cd"));
                dobj.setNcrbRef(rs.getString("ncrb_ref"));
                dobj.setConfirmRef(rs.getString("confirm_ref"));
                dobj.setNocDate(rs.getTimestamp("noc_dt"));
                dobj.setNocNo(rs.getString("noc_no"));
                dobj.setStateEntryDate(rs.getTimestamp("state_entry_dt"));
            }
        } catch (Exception e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            throw new VahanException("Error in Fetching of Other State Vehicle Details");
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



}

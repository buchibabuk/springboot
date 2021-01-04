package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
@Service
public class ScrappedVehicleImpl {
	private static final Logger LOGGER = Logger.getLogger(ScrappedVehicleImpl.class);
	public void updateApplNoForScrappedVeh(String oldRegno, String applNo, String stateCd, int offCd, TransactionManagerOne tmgr) throws VahanException {

        String sql = " Select  regn_appl_no from " + TableList.VT_SCRAP_VEHICLE
                + " where old_regn_no=? "
                + " and state_cd=? and off_cd=? and regn_appl_no is not null and "
                + " new_chasi_no is not null and new_regn_no is not null ";

        PreparedStatement pstmt = null;
        try {

            pstmt = tmgr.prepareStatement(sql);
            pstmt.setString(1, oldRegno);
            pstmt.setString(2, stateCd);
            pstmt.setInt(3, offCd);
            RowSet rs = tmgr.fetchDetachedRowSet_No_release();
            if (rs.next()) {
                throw new VahanException("Entry is already done for Registration No : " + oldRegno);
            }

            sql = " update  " + TableList.VT_SCRAP_VEHICLE
                    + " set regn_appl_no=? WHERE old_regn_no=? "
                    + " and state_cd=? and off_cd=? ";

            pstmt = tmgr.prepareStatement(sql);
            pstmt.setString(1, applNo);
            pstmt.setString(2, oldRegno);
            pstmt.setString(3, stateCd);
            pstmt.setInt(4, offCd);
            pstmt.executeUpdate();

            sql = " update  " + TableList.VT_SURRENDER_RETENTION
                    + " set regn_appl_no=? WHERE new_regn_no=? "
                    + " and state_cd=? and off_cd=? ";

            pstmt = tmgr.prepareStatement(sql);
            pstmt.setString(1, applNo);
            pstmt.setString(2, oldRegno);
            pstmt.setString(3, stateCd);
            pstmt.setInt(4, offCd);
            pstmt.executeUpdate();


        } catch (Exception e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            throw new VahanException("Error in Updation of Scrapped Vehicle");
        }

    }

    
}

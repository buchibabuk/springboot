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
import nic.vahan5.reg.form.dobj.Status_dobj;
import nic.vahan5.reg.server.TableConstants;
@Service
public class ApplicationInwardImplementation 
{
	 private static final Logger LOGGER = Logger.getLogger(ApplicationInwardImplementation.class);
	 @Autowired
	 TransactionManagerOne tmg;

	 public boolean isOnlineApplication(String applNo, int purCd) throws VahanException {
	        boolean isOnlineApp = false;
	        PreparedStatement ps = null;
	     //   TransactionManagerOne tmg = null;
	        String sql = null;
	        try {
	           // tmg = new TransactionManagerOne("isOnlineApplication");
	            sql = "SELECT a.appl_no,a.pur_cd FROM " + TableList.VA_STATUS_APPL
	                    + "  a inner join " + TableList.VA_DETAILS_APPL
	                    + "  b on a.appl_no=b.appl_no and a.pur_cd=b.pur_cd and a.moved_from_online=?"
	                    + "  WHERE b.appl_no=? and b.pur_cd=?";
	            ps = tmg.prepareStatement(sql);
	            ps.setString(1, TableConstants.MOVED_FROM_ONLINE_APPL_STATUS_YES);
	            ps.setString(2, applNo);
	            ps.setInt(3, purCd);

	            RowSet rs = tmg.fetchDetachedRowSet();
	            if (rs.next()) {
	                isOnlineApp = true;
	            }
	        } catch (SQLException e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Something Went Wrong, Please Contact to the System Administrator.");
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Something Went Wrong, Please Contact to the System Administrator.");
	        } finally {
	            try {
	                if (tmg != null) {
	                    tmg.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return isOnlineApp;
	    }
	 public void updateApprovedStatusForOnlineAppl(TransactionManagerOne tmgr, Status_dobj dobj, String clientIpAddress) throws SQLException {
	        PreparedStatement ps = null;
	        String sql = null;

	        sql = "UPDATE " + TableList.VA_DETAILS_APPL
	                + "   SET  entry_status=?,confirm_ip=?,confirm_date=current_timestamp"
	                + " WHERE  appl_no=? and pur_cd=?";

	        ps = tmgr.prepareStatement(sql);
	        ps.setString(1, dobj.getEntry_status());
	        ps.setString(2, clientIpAddress);
	        ps.setString(3, dobj.getAppl_no());
	        ps.setInt(4, dobj.getPur_cd());
	        ps.executeUpdate();
	    } // end of updateApprovedStatusForOnlineAppl
	 
}

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
import nic.vahan5.reg.server.TableConstants;
@Service
public class BlackListedVehicleImplementation {
	
	@Autowired
	TransactionManagerReadOnly tmgr;
	private static final Logger LOGGER = Logger.getLogger(BlackListedVehicleImplementation.class);
	
	 public boolean checkRegnNoForBlackList(String RegnNo, String userStateCode, int offCode) throws VahanException {
	        boolean isBlackListed = false;
	        //TransactionManager tmgr = null;
	        PreparedStatement ps;
	        RowSet rs;
	        String sqlchassis;
	        try {

	         //   tmgr = new TransactionManager("checkRegnNoForBlackList");
	            sqlchassis = "select regn_no from  " + TableList.VT_BLACKLIST
	                    + " where regn_no=? and state_cd=? and off_cd=?";
	            ps = tmgr.prepareStatement(sqlchassis);
	            ps.setString(1, RegnNo);
	            ps.setString(2, userStateCode);
	            ps.setInt(3, offCode);
	            rs = tmgr.fetchDetachedRowSet();
	            if (rs.next()) {
	                isBlackListed = true;
	            }
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException(TableConstants.SomthingWentWrong);
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return isBlackListed;
	    }
}

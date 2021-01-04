package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
@Service
public class OfficeCorrectionImpl {
	  private static final Logger LOGGER = Logger.getLogger(OfficeCorrectionImpl.class);
	  @Autowired
	  TransactionManagerReadOnly tmgr;
	    String[] vaTables = {
	        TableList.VA_OWNER,
	        TableList.VA_HPA,
	        TableList.VA_INSURANCE,
	        TableList.VA_FITNESS,
	        TableList.VA_OWNER_IDENTIFICATION,
	        TableList.VA_AXLE,
	        TableList.VA_RETROFITTING_DTLS,
	        TableList.VA_SIDE_TRAILER,
	        TableList.VA_SPEED_GOVERNOR,
	        TableList.VA_TRAILER,
	        TableList.VA_IMPORT_VEH,
	        TableList.VA_CA,
	        TableList.VA_TO,
	        TableList.VA_HPT,
	        TableList.VT_DSC_DOC_DETAILS,
	        TableList.VA_STATUS,
	        TableList.VHA_STATUS,
	        TableList.VHA_DETAILS,
	        TableList.VA_DETAILS,
	        TableList.VP_APPL_RCPT_MAPPING,
	        TableList.VPH_RCPT_CART,
	        TableList.VT_FEE,
	        TableList.VT_TAX,
	        TableList.VT_TAX_BREAKUP,
	        TableList.VT_TAX_BASED_ON,
	        TableList.VP_RCPT_CART,
	        TableList.VP_CART_TAX_BREAKUP,
	        TableList.VPH_RCPT_CART_FAIL,
	        TableList.VT_FEE_BREAKUP,
	        TableList.VA_FC_PRINT,
	        TableList.VH_FC_PRINT,
	        TableList.VP_BANK_SUBSIDY,
	        TableList.VA_OWNER_TEMP,
	        TableList.VT_OTP_VERIFY,
	        TableList.VA_STATUS_APPL,
	        TableList.VA_DETAILS_APPL,
	        TableList.VT_TEMP_APPL_TRANSACTION,
	        TableList.VP_ACCOUNT,
	        TableList.VP_DETAILS,
	        TableList.VH_TEMP_APPROVE,
	        TableList.VT_FACELESS_SERVICE
	    };
	    String[] vhaTables = {
	        TableList.VHA_OWNER,
	        TableList.VHA_HPA,
	        TableList.VHA_INSURANCE,
	        TableList.VHA_FITNESS,
	        TableList.VHA_OWNER_IDENTIFICATION,
	        TableList.VHA_AXLE,
	        TableList.VHA_RETROFITTING_DTLS,
	        TableList.VHA_SIDE_TRAILER,
	        TableList.VHA_SPEED_GOVERNOR,
	        TableList.VHA_TRAILER,
	        TableList.VHA_IMPORT_VEH,
	        TableList.VHA_CA,
	        TableList.VHA_TO,
	        TableList.VHA_HPT,
	        TableList.VH_DSC_DOC_DETAILS
	    };
	    String[] insertUpdateTablesArr = {
	        TableList.VA_OWNER,
	        TableList.VA_HPA,
	        TableList.VA_INSURANCE,
	        TableList.VA_FITNESS,
	        TableList.VA_OWNER_IDENTIFICATION,
	        TableList.VA_AXLE,
	        TableList.VA_RETROFITTING_DTLS,
	        TableList.VA_SIDE_TRAILER,
	        TableList.VA_SPEED_GOVERNOR,
	        TableList.VA_TRAILER,
	        TableList.VA_IMPORT_VEH,
	        TableList.VA_CA,
	        TableList.VA_TO,
	        TableList.VA_HPT,
	        TableList.VT_DSC_DOC_DETAILS
	    };
	    
	    public  String getRcptNoForFeePaidForPurpose(String applNo, int purCd) throws VahanException {
	       // TransactionManagerReadOnly tmgrReadOnly = null;
	        String rcptNo = null;
	        try {
	         //   tmgrReadOnly = new TransactionManagerReadOnly("checkFeePaidForPurpose");
	            String sql = "SELECT * from get_appl_rcpt_details(?) where pur_cd = ? ";
	            PreparedStatement psmt = tmgr.prepareStatement(sql);
	            psmt.setString(1, applNo);
	            psmt.setInt(2, purCd);
	            RowSet rs = tmgr.fetchDetachedRowSet();
	            if (rs.next()) {
	                rcptNo = rs.getString("rcpt_no");
	            }
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Problem in Fetching Fee details.");
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return rcptNo;
	    }

}

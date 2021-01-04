package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.server.CommonUtils;

public class PermitShowFeeDetailImpl {
	 private static final Logger LOGGER = Logger.getLogger(PermitShowFeeDetailImpl.class);
	 public String getPmtFeeValueFormSlab(String slabValue, TransactionManagerReadOnly tmgr) {
	        String fee = "0";
	        try {
	            if (slabValue != null && !slabValue.isEmpty()) {
	                String Query = "SELECT " + slabValue + " as feeDtls";
	                PreparedStatement ps = tmgr.prepareStatement(Query);
	                RowSet rs = tmgr.fetchDetachedRowSet_No_release();
	                if (rs.next()) {
	                    fee = rs.getString("feeDtls");
	                    if (CommonUtils.isNullOrBlank(fee) || fee.equalsIgnoreCase("0")) {
	                        fee = "0";
	                    }
	                }
	            }
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        }
	        return fee;
	    }

}

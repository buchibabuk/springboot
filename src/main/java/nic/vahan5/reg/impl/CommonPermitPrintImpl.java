package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
@Service
public class CommonPermitPrintImpl {
	@Autowired
	TransactionManagerReadOnly tmgr;
	 private static final Logger LOGGER = Logger.getLogger(CommonPermitPrintImpl.class);
	
	 public CommonPermitPrintImpl() {
	    }
	 
	 
	 public  Map<String,String> getVmPermitStateConfiguration(String stateCd) {
		 
	
		 System.out.println("CPPI"+stateCd);
	        Map<String, String> stateConfMap = new LinkedHashMap<String, String>();
	      //  TransactionManagerReadOnly tmgrs = null;
	        RowSet rs = null;
	        PreparedStatement ps = null;
	        try {
	          //  tmgrs = new TransactionManagerReadOnly("Private Service Permit Print");
	            String query = "SELECT * FROM " + TableList.VM_PERMIT_STATE_CONFIGURATION + " where state_cd = ?";
	            ps = tmgr.prepareStatement(query);
	            ps.setString(1, stateCd);
	            rs = tmgr.fetchDetachedRowSet();
	            ResultSetMetaData rsmd = rs.getMetaData();
	            if (rs.next()) {
	                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
	                    stateConfMap.put(rsmd.getColumnName(i), rs.getString(i));
	                }
	            }
	        } catch (SQLException e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return stateConfMap;
	    }

}

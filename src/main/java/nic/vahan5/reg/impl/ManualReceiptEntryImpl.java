package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.form.dobj.ManualReceiptEntryDobj;
import nic.vahan5.reg.server.TableConstants;
@Service
public class ManualReceiptEntryImpl {
	@Autowired
	TransactionManagerReadOnly tmgr;
	 private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ManualReceiptEntryImpl.class);
	 
	 public  List<ManualReceiptEntryDobj> getManualReceiptEntryDetails(String applNo,String statecode) throws VahanException {
	        String query;
	      //  TransactionManagerReadOnly tmgr = null;
	        PreparedStatement ps;
	        RowSet rs;
	        List<ManualReceiptEntryDobj> manualDtlslist = new ArrayList<>();
	        try {
	         //   tmgr = new TransactionManagerReadOnly("getManualReceiptEntryDetails");
	            query = " SELECT a.state_cd,a.off_cd,a.transaction_appl_no,b.descr,sum(amount) as amount from " + TableList.VT_MANUAL_RECEIPT + "  \n"
	                    + " a inner join TM_PURPOSE_MAST b on " + TableConstants.VM_MAST_MANUAL_RECEIPT + " = b.pur_cd \n"
	                    + " where transaction_appl_no =? and state_cd= ? group by 1,2,3,4 ";
	            ps = tmgr.prepareStatement(query);
	            ps.setString(1, applNo);
	            ps.setString(2,statecode);
	            rs = tmgr.fetchDetachedRowSet();
	            while (rs.next()) {
	                ManualReceiptEntryDobj obj = new ManualReceiptEntryDobj();

	                obj.setState_cd(rs.getString("state_cd"));
	                obj.setOff_cd(rs.getInt("off_cd"));
	                obj.setTrans_appl_no(applNo);
	                obj.setAmount(rs.getLong("amount"));
	                obj.setPur_cd((TableConstants.VM_MAST_MANUAL_RECEIPT));
	                manualDtlslist.add(obj);
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
	        return manualDtlslist;
	    }

	 
	 
	 
	 
}

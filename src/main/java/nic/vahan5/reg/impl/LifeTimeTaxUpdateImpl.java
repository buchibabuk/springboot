package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import nic.vahan5.reg.form.dobj.LifeTimeTaxUpdateDobj;
import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;


public class LifeTimeTaxUpdateImpl {
	@Autowired
	TransactionManagerReadOnly tmgr;
	 private static final Logger logger = Logger.getLogger(LifeTimeTaxUpdateImpl.class);
	 public long getLifeTaxUpdateAmt(LifeTimeTaxUpdateDobj lifeTimeTaxUpdateDobj) throws VahanException {
	       // TransactionManagerReadOnly tmgr = null;
	        long taxAmt = 0;
	        try {
	          //  tmgr = new TransactionManagerReadOnly("getLifeTaxAmt Method");
	            String qry = "select * from va_ltt_amt_update where regn_no=? and state_cd=?  and off_cd=?";
	            PreparedStatement pms = tmgr.prepareStatement(qry);
	            pms.setString(1, lifeTimeTaxUpdateDobj.getRegnNo());
	            pms.setString(2, lifeTimeTaxUpdateDobj.getStateCd());
	            pms.setInt(3, lifeTimeTaxUpdateDobj.getOffcd());
	            RowSet rs = tmgr.fetchDetachedRowSet();
	            if (rs.next()) {
	                taxAmt = rs.getLong("tax_amt");
	            }

	        } catch (Exception ex) {
	            logger.error(ex.toString() + " : " + ex.getStackTrace()[0]);
	            throw new VahanException("TaxExcepttion");
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception ex) {
	                logger.error(ex.toString() + " : " + ex.getStackTrace()[0]);
	            }
	        }


	        return taxAmt;
	    }
}

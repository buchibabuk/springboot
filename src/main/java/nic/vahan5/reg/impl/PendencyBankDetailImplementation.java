package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
import nic.vahan5.reg.form.dobj.PendencyBankDobj;
@Service
public class PendencyBankDetailImplementation {
	 private static final Logger LOGGER = Logger.getLogger(PendencyBankDetailImplementation.class);

@Autowired
TransactionManagerOne tmgr;

	public PendencyBankDobj getBankSubsidyData(String applNo, String stateCd, int offCd) throws VahanException {
       // TransactionManagerOne tmgr = null;
        PreparedStatement ps = null;
        PendencyBankDobj subsidyDobj = new PendencyBankDobj();;
        try {
        //    tmgr = new TransactionManagerOne("getBankDetailsData");
            String saveSQL = " SELECT * FROM " + TableList.VP_BANK_SUBSIDY + " where state_cd = ? and off_cd = ? and appl_no = ? ";
            ps = tmgr.prepareStatement(saveSQL);
            int i = 1;
            ps.setString(i++, stateCd);
            ps.setInt(i++, offCd);
            ps.setString(i++, applNo);
            RowSet rs = tmgr.fetchDetachedRowSet();
            if (rs.next()) {
                subsidyDobj.setBankCd(rs.getString("bank_code"));
                subsidyDobj.setIfscCode(rs.getString("bank_ifsc_cd"));
                subsidyDobj.setAccountNo(rs.getString("bank_ac_no"));
                subsidyDobj.setAadharNo(rs.getString("aadhar_no"));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage() + "  " + e.getStackTrace()[0]);
            throw new VahanException("problem in getting bank subsidy details.");
        } finally {
            try {
                if (tmgr != null) {
                    tmgr.release();
                }
            } catch (Exception e) {
                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            }
        }
        return subsidyDobj;
    }

}

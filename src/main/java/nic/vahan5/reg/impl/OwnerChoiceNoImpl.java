package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
@Service
public class OwnerChoiceNoImpl {
	
	@Autowired
	TransactionManagerReadOnly tmgr;
	 private static final Logger LOGGER = Logger.getLogger(OwnerChoiceNoImpl.class);
	public Map<String, String> getOwnerChoiceRegnNoDetails(String appl_no) throws VahanException {
        String sql = null;
        PreparedStatement ps = null;
      //  TransactionManagerReadOnly tmgr = null;
        Map<String, String> choiceDetails = null;
        try {
          //  tmgr = new TransactionManagerReadOnly("getOwnerChoiceRegnNoDetails");
            sql = "select regn_no, choice_fees from " + TableList.VA_OWNER_CHOICE_NO + " where appl_no = ? ";
            ps = tmgr.prepareStatement(sql);
            ps.setString(1, appl_no);
            RowSet rs = tmgr.fetchDetachedRowSet();
            if (rs.next()) {
                choiceDetails = new HashMap<>();
                choiceDetails.put("regn_no", rs.getString("regn_no"));
                choiceDetails.put("choice_fees", rs.getString("choice_fees"));
            }
        } catch (Exception e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            throw new VahanException("Unable to get Owner Choice No.");
        } finally {
            try {
                if (tmgr != null) {
                    tmgr.release();
                }
            } catch (Exception e) {
                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            }
        }
        return choiceDetails;
    }
}

package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.form.dobj.TmConfigurationOwnerIdentificationDobj;

@Service
public class OwnerIdentificationImpl {
	private static final Logger LOGGER = Logger.getLogger(OwnerIdentificationImpl.class);
	
	@Autowired
	TransactionManagerReadOnly tmgr;
	public TmConfigurationOwnerIdentificationDobj getTmConfigurationOwnerIdentification(String stateCd) throws VahanException {
        TmConfigurationOwnerIdentificationDobj configurationOwnerIdentificationDobj = null;
        PreparedStatement ps = null;
        //TransactionManagerReadOnly tmgr = null;
        try {
            String sql = "SELECT * FROM " + TableList.TM_CONFIGURATION_OWNER_IDENTIFICATION + " where state_cd = ? ";
          //  tmgr = new TransactionManagerReadOnly("TmConfigurationOwnerIdentificationDobj");
            ps = tmgr.prepareStatement(sql);
            ps.setString(1, stateCd);
            RowSet rs = tmgr.fetchDetachedRowSet();
            if (rs.next()) {
                configurationOwnerIdentificationDobj = new TmConfigurationOwnerIdentificationDobj();
                configurationOwnerIdentificationDobj.setState_cd((rs.getString("state_cd")));
                configurationOwnerIdentificationDobj.setDl_required((rs.getString("dl_required")));
                configurationOwnerIdentificationDobj.setDl_validation_required(rs.getBoolean("dl_validation_required"));
                configurationOwnerIdentificationDobj.setPan_card_mandatory(rs.getString("pancard_mandatory"));
            }
        } catch (Exception e) {
            LOGGER.error(e.toString() + " getTmConfigurationOwnerIdentification " + e.getStackTrace()[0]);
            throw new VahanException("Problem in fetching Configuration Details of Owner Identification, Please Contact to the System Administrator.");
        } finally {
            try {
                if (tmgr != null) {
                    tmgr.release();
                }
            } catch (Exception e) {
                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            }
        }
        return configurationOwnerIdentificationDobj;
    }

}

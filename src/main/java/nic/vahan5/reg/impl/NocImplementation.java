package nic.vahan5.reg.impl;

import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.MasterTableFiller;
import nic.vahan5.reg.db.State;
import nic.vahan5.reg.form.dobj.NocDobj;
import nic.vahan5.reg.form.dobj.Owner_dobj;
import nic.vahan5.reg.server.ServerUtility;
import nic.vahan5.reg.server.TableConstants;
@Service
public class NocImplementation {
	 private static final Logger LOGGER = Logger.getLogger(NocImplementation.class);
	 @Autowired
	 ServerUtility serverUtility;
	 @Autowired
	 MasterTableFiller masterTableFiller;

	public void checkForNocVerificationAndEndorsement(Owner_dobj owner_dobj, String regnNo, String userStateCode, int offCode,String userCatg) throws VahanException, Exception {
        NocDobj noc_dobj = serverUtility.getChasiNoExist(owner_dobj.getChasi_no());
        if (noc_dobj != null) {
            if (noc_dobj.getVt_owner_status().equals("N")) {
                Date vahan4StartDate = serverUtility.getVahan4StartDate(noc_dobj.getState_to(), noc_dobj.getOff_to(),userCatg);
                	//	serverUtility.getVahan4StartDate(stateCdTo, offCdTo, userCategory);
                if (vahan4StartDate == null) {
                    if (noc_dobj.getState_cd().equals(userStateCode) && offCode == noc_dobj.getOff_cd() || noc_dobj.getState_to().equals(userStateCode) && offCode == noc_dobj.getOff_to()) {
                        throw new VahanException(TableConstants.NOC_ENDORSEMENT);
                    } else if (!noc_dobj.getState_to().equals(userStateCode) || offCode != noc_dobj.getOff_to()) {
                        NocDobj nocVerifiedData = serverUtility.getNocVerifiedData(regnNo, owner_dobj.getChasi_no(),userStateCode,offCode);
                        if (nocVerifiedData == null) {
                            throw new VahanException(TableConstants.NOC_VERIFICATION);
                        }
                    }
                } else {
                    if (!noc_dobj.getState_to().equals(userStateCode) || offCode != noc_dobj.getOff_to()) {
                        State stateCd =masterTableFiller.state.get(noc_dobj.getState_to());
                        String offCdLabel = null;
                        if (stateCd != null) {
                            List<SelectItem> listOff = stateCd.getOffice();
                            for (SelectItem off : listOff) {
                                if (off.getValue().toString().equals(String.valueOf(noc_dobj.getOff_to()))) {
                                    offCdLabel = off.getLabel();
                                    break;
                                }
                            }
                        }
                        throw new VahanException("Vehicle has NOC issued to state : " + stateCd.getStateDescr() + " Office: " + offCdLabel);
                    }
                }
            } else {
                // if vt_owner status not equal to N and chassisno already exist bcz on that chassi no registration can be don using regntype is other statew/ other rto if vt_owner status equal to N
                throw new VahanException("Vehicle is already Registered!!!");
            }
        }
    }

}

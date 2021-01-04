package nic.vahan5.reg.CommonUtils;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.java.util.CommonUtils;
import nic.java.util.DateUtils;
import nic.vahan.common.jsf.utils.DateUtil;
import nic.vahan.common.jsf.utils.JSFUtils;
import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.db.connection.TransactionManagerLocal;
import nic.vahan5.reg.form.dobj.Owner_dobj;
import nic.vahan5.reg.form.model.SessionVariablesModel;
import nic.vahan5.reg.server.ServerUtil;
import nic.vahan5.reg.server.ServerUtility;
import nic.vahan5.reg.server.TableConstants;
@Service
public class FormulaUtilities {
	
	  private static final Logger LOGGER = Logger.getLogger(FormulaUtilities.class);
	  
	  public  Map TagFieldsMap = null;
	    public  Map TagFieldsMapVerify = null;
	    public  Map TagDescrDisplay = null;
	    @Autowired
	    ServerUtil serverUtil;
	    @Autowired
	    ServerUtility serverUtility;
	    
	    @Autowired
	    TransactionManagerOne tmgr;
	    @Autowired
	    TransactionManagerReadOnly tmg;
	    @Autowired
	    TransactionManagerLocal tmgl;
	    
	 //   static {
	   //     getTagFields();
	    //}
 @PostConstruct
public void init()
{
	getTagFields();
}
	    private  void getTagFields() {
	        if (TagFieldsMap == null) {
	            TagFieldsMap = new LinkedHashMap<String, String>();
	            if (TagFieldsMapVerify == null) {
	                TagFieldsMapVerify = new LinkedHashMap<String, String>();
	            }
	            if (TagDescrDisplay == null) {
	                TagDescrDisplay = new LinkedHashMap<String, String>();
	            }

	            String sql = "SELECT regexp_replace(code, '[^0-9]', '', 'g')::numeric as sr_no, code, descr, value_field, field_type FROM vm_tax_slab_fields order by 1";
	           // TransactionManagerOne tmgr = null;
	            try {
	             //   tmgr = new TransactionManagerOne("getTagFields");
	                tmg.prepareStatement(sql);
	                RowSet rs = tmg.fetchDetachedRowSet();
	                while (rs.next()) {
	                    TagFieldsMap.put(rs.getString("code"), rs.getString("value_field"));
	                    TagFieldsMapVerify.put(rs.getString("code"), rs.getString("field_type"));
	                    TagDescrDisplay.put(rs.getString("code"), rs.getString("descr"));
	                }
	                TagFieldsMap.put("<pDay>", "PMT_DAYS");
	                TagFieldsMap.put("<pMonth>", "PMT_MONTHS");
	                TagFieldsMap.put("<pCMonth>", "PMT_CEL_MONTH");
	                TagFieldsMap.put("<pYear>", "PMT_YEAR");
	                TagFieldsMap.put("<state_cd>", "STATE_CD");
	                TagFieldsMap.put("<regn_no>", "REGN_NO");
	                TagFieldsMap.put("<per_route>", "ROUTE_COUNT");
	                TagFieldsMap.put("<per_region>", "REGION_COUNT");
	                TagFieldsMap.put("<noc_ret>", "NOC_RETENTION");
	                TagFieldsMap.put("<tmp_purpose>", "TMP_PURPOSE");
	                TagFieldsMap.put("<exem_amount>", "EXEM_AMOUNT");
	                TagFieldsMap.put("<fine_to_be_taken>", "FINE_TO_BE_TAKEN");
	                TagFieldsMap.put("<multi_region>", "MULTI_REGION");
	                TagFieldsMap.put("<multi_doc>", "MULTI_DOC");
	                TagFieldsMap.put("<vehicle_hypth>", "VEHICLE_HYPTH");
	                TagFieldsMap.put("<FIT_UPTO>", "FIT_UPTO");
	                TagFieldsMap.put("<own_catg>", "OWN_CATG");
	                TagFieldsMap.put("<to_reason>", "TO_REASON");
	                TagFieldsMap.put("<fit_status>", "FIT_STATUS");
	                TagFieldsMap.put("<pAmount>", "PMT_AMOUNT");
	                TagFieldsMap.put("<excem_flag>", "EXCEM_FLAG");
	                TagFieldsMap.put("<pOffer_Letter>", "OFFER_LETTER");
	                TagFieldsMap.put("<pdup_reason>", "DUP_PMT_REASON");
	                TagFieldsMap.put("<online_permit>", "ONLINE_PERMIT");
	                TagFieldsMap.put("<permit_expired>", "PERMIT_EXPIRED");
	                TagFieldsMap.put("<APPLICANT_TYPE>", "APPLICANT_TYPE");
	                TagFieldsMap.put("<APPLICATION_TYPE>", "APPLICATION_TYPE");
	                TagFieldsMap.put("<insp_rqrd>", "INSP_RQRD");
	                TagFieldsMap.put("<PREV_OFF_CD>", "PREV_OFF_CD");
	                TagFieldsMap.put("<region_descr>", "REGION_DESCR");
	                TagFieldsMap.put("<regn_upto>", "REGN_UPTO");
	                TagFieldsMap.put("<OWNER_CATG>", "OWNER_CATG");
	                TagFieldsMap.put("<daysWithinState>", "DAYSWITHINSTATE");
	                TagFieldsMap.put("<blackListCode>", "BLACKLISTCODE");
	                TagFieldsMap.put("<manufactur_date>", "MANUFACTUR_DATE");
	                TagFieldsMap.put("<authYear>", "AUTHYEAR");
	                TagFieldsMap.put("<action_cd>", "ACTION_CD");
	                TagFieldsMap.put("<oldVchType>", "OLD_VCH_TYPE");
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            } finally {
	                try {
	                    if (tmg != null) {
	                        tmg.release();
	                    }
	                } catch (Exception e) {
	                    LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	                }
	            }

	        }
	    }

	    public  String replaceTagValues(String inputString, VehicleParameters dobj) {
	        String retString = inputString;
	        if (TagFieldsMap == null) {
	            getTagFields();
	        }
	        Set entries = TagFieldsMap.entrySet();
	        Iterator entryIter = entries.iterator();
	        while (entryIter.hasNext()) {
	            Map.Entry entry = (Map.Entry) entryIter.next();
	            Object key = entry.getKey();  // Get the key from the entry.
	            Object value = entry.getValue();  // Get the value.

	            try {
	                Method method = findMethod(dobj.getClass(), "get" + value.toString().substring(0, 1).toUpperCase() + value.toString().substring(1));
	                Class ab = method.getReturnType();

	                Object retObj = method.invoke(dobj, null);
	                if (ab.isInstance(new String())) {
	                    if (retObj == null) {
	                        retString = retString.replace(key.toString(), "null");
	                    } else {
	                        retString = retString.replace(key.toString(), "'" + retObj.toString().toUpperCase() + "'");
	                    }
	                } else {
	                    retString = retString.replace(key.toString(), retObj.toString().toUpperCase());
	                }
	            } catch (Exception e) {
	                //LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }

	        }
	        return retString;
	    }

	 public  VehicleParameters fillVehicleParametersFromDobj(Owner_dobj ownerDobj, SessionVariablesModel sessionVariables) {
	        Integer userLoginOffCode = sessionVariables.getUserLoginOffCode();
	        VehicleParameters taxParameters = new VehicleParameters();
	        try {

	            if (ownerDobj == null) {
	                return taxParameters;
	            }
	            if (sessionVariables != null) {
	                taxParameters.setOFF_CD(sessionVariables.getOffCodeSelected());
	            }
	            if (userLoginOffCode != null && userLoginOffCode >= 0 && "RJ".equals(ownerDobj.getState_cd())) {
	                switch (sessionVariables.getUserCatgForLoggedInUser()) {
	                    case TableConstants.USER_CATG_DEALER:
	                        if (sessionVariables.getSelectedWork() != null && TableConstants.TM_ROLE_DEALER_APPROVAL == sessionVariables.getActionCodeSelected()) {
	                            taxParameters.setLOGIN_OFF_CD(sessionVariables.getOffCodeSelected());
	                        } else {
	                            taxParameters.setLOGIN_OFF_CD(userLoginOffCode);
	                        }
	                        break;
	                    case TableConstants.USER_CATG_OFF_STAFF:
	                        if (!CommonUtils.isNullOrBlank(ownerDobj.getAppl_no())) {
	                            int loginOffcd = serverUtility.getOfficeCdForDealerTempAppl(ownerDobj.getAppl_no(), ownerDobj.getState_cd(), "", sessionVariables.getOffCodeSelected());
	                            taxParameters.setLOGIN_OFF_CD(loginOffcd);
	                            if (sessionVariables.getActionCodeSelected() == TableConstants.TM_NEW_RC_VERIFICATION && taxParameters.getOFF_CD() != null && taxParameters.getOFF_CD() == loginOffcd) {
	                                taxParameters.setPREV_OFF_CD(loginOffcd);
	                            }
	                        }
	                        break;
	                }
	            } else {
	                taxParameters.setLOGIN_OFF_CD(userLoginOffCode);
	            }
	            if (ownerDobj.getOff_cd() > 0) {
	                taxParameters.setOFF_CD(ownerDobj.getOff_cd());
	            }
	            taxParameters.setAC_FITTED(ownerDobj.getAc_fitted());
	            // taxParameters.setCALENDARMONTH(ownerDobj.getc);
	            taxParameters.setCC(ownerDobj.getCubic_cap() == null ? 0.0f : ownerDobj.getCubic_cap());//float type
	            // taxParameters.setDELAYDAYS(ownerDo);
	            taxParameters.setFLOOR_AREA(ownerDobj.getFloor_area());
	            taxParameters.setFUEL(ownerDobj.getFuel());
	            taxParameters.setHP(ownerDobj.getHp() == null ? 0.0f : ownerDobj.getHp());
	            taxParameters.setLD_WT(ownerDobj.getLd_wt());
	            taxParameters.setOWNER_CD(ownerDobj.getOwner_cd());
	            if (ownerDobj.getDob_temp() != null
	                    && ownerDobj.getDob_temp().getTemp_regn_type() != null
	                    && ownerDobj.getDob_temp().getTemp_regn_type().trim().length() > 0) {
	                taxParameters.setREGN_TYPE(ownerDobj.getDob_temp().getTemp_regn_type());
	            } else {
	                taxParameters.setREGN_TYPE(ownerDobj.getRegn_type());
	            }
	            taxParameters.setSALE_AMT(ownerDobj.getSale_amt());
	            taxParameters.setSEAT_CAP(ownerDobj.getSeat_cap());
	            taxParameters.setSLEEPAR_CAP(ownerDobj.getSleeper_cap());
	            taxParameters.setSTAND_CAP(ownerDobj.getStand_cap());
	            taxParameters.setSTATE_CD(ownerDobj.getState_cd());
	            taxParameters.setUNLD_WT(ownerDobj.getUnld_wt());
	            taxParameters.setVH_CLASS(ownerDobj.getVh_class());
	            if (sessionVariables.getStateCodeSelected() != null && "RJ".equals(sessionVariables.getStateCodeSelected())) {
	                String reqdTaxMode = ownerDobj.getRqrd_tax_modes();
	                if (reqdTaxMode != null) {
	                    String taxMode = ServerUtility.getTaxModeFromReqdTaxMode(reqdTaxMode);
	                    taxParameters.setTAX_MODE(taxMode);
	                }
	            } else {
	            taxParameters.setTAX_MODE(ownerDobj.getTax_mode());
	            }
	            if (ownerDobj.getRegn_dt() != null) {
	                taxParameters.setREGN_DATE(DateUtils.parseDate(ownerDobj.getRegn_dt()));
	                taxParameters.setTAX_DUE_FROM_DATE(DateUtils.parseDate(ownerDobj.getRegn_dt()));
	            } else {
	                taxParameters.setREGN_DATE(DateUtils.parseDate(new Date()));
	                taxParameters.setTAX_DUE_FROM_DATE(DateUtils.parseDate(new Date()));
	            }

	            taxParameters.setVCH_CATG(ownerDobj.getVch_catg() != null ? ownerDobj.getVch_catg().trim() : null);
	            taxParameters.setVH_CLASS(ownerDobj.getVh_class());
	            taxParameters.setSEAT_CAP(ownerDobj.getSeat_cap());
	            if (ownerDobj.getPurchase_dt() != null) {
	                taxParameters.setVCH_AGE((int) Math.ceil(DateUtils.getDate1MinusDate2_Months(ownerDobj.getPurchase_dt(), new Date()) / 12.0));
	            }
	            taxParameters.setAUDIO_FITTED(ownerDobj.getAudio_fitted());
	            taxParameters.setVIDEO_FITTED(ownerDobj.getVideo_fitted());

	            if (ownerDobj.getVehType() <= 0) {
	                taxParameters.setVCH_TYPE(serverUtil.VehicleClassType(ownerDobj.getVh_class()));
	            } else {
	                taxParameters.setVCH_TYPE(ownerDobj.getVehType());
	            }
	            taxParameters.setREGN_NO(ownerDobj.getRegn_no());
	            taxParameters.setPERMIT_TYPE(ownerDobj.getPmt_type());
	            taxParameters.setPERMIT_SUB_CATG(ownerDobj.getPmt_catg());
	            taxParameters.setOTHER_CRITERIA(ownerDobj.getOther_criteria());
	            taxParameters.setFIT_UPTO(ownerDobj.getFit_upto() != null ? JSFUtils.getDateInDD_MMM_YYYY(DateUtils.parseDate(ownerDobj.getFit_upto())) : null);
	            if (taxParameters.getFIT_UPTO() == null || taxParameters.getFIT_UPTO().isEmpty() || taxParameters.getFIT_UPTO().trim().length() <= 0) {
	                taxParameters.setFIT_UPTO(JSFUtils.getDateInDD_MMM_YYYY(DateUtils.parseDate(new Date())));
	            }
	            taxParameters.setNORMS(ownerDobj.getNorms());
	            taxParameters.setVEH_PURCHASE_AS(ownerDobj.getVch_purchase_as());
	            if (ownerDobj.getPurchase_dt() != null) {
	                taxParameters.setPURCHASE_DATE(DateUtil.parseDateYYYYMMDDToString(ownerDobj.getPurchase_dt()));
	            }
	            if (ownerDobj.getOwner_identity() != null) {
	                taxParameters.setOWNER_CATG(ownerDobj.getOwner_identity().getOwnerCatg());
	            }
	            if (ownerDobj.getManu_yr() != null && ownerDobj.getManu_mon() != null) {
	                taxParameters.setMANUFACTUR_DATE(ownerDobj.getManu_yr().toString() + ((ownerDobj.getManu_mon() < 10) ? "0" + ownerDobj.getManu_mon() : ownerDobj.getManu_mon().toString()));
	            }
	            if (ownerDobj.getDob_temp() != null) {
	                taxParameters.setTMP_PURPOSE(ownerDobj.getDob_temp().getPurpose());
	            }
	        } catch (VahanException e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        }
	        return taxParameters;
	    }
	 private static Method findMethod(Class<?> clazz, String methodName) throws NoSuchMethodException {
	        for (Method method : clazz.getMethods()) {
	            if (method.getName().equals(methodName)) {
	                return method;
	            }
	        }
	        throw new NoSuchMethodException();
	    }
	 public  boolean isCondition(String inputString, String whereIam) {
	        boolean status = false;
	       // TransactionManagerLocal tmgr = null;
	        try {
	            if (inputString != null && !inputString.isEmpty() && inputString.trim().length() > 0) {
	                String sql = "select COALESCE((" + inputString + "), false)::boolean as ret";
	               // tmgr = new TransactionManagerLocal("isCondition-" + whereIam);
	                
	                System.out.println("SQL..."+sql);
	                tmgl.prepareStatement(sql);
	                RowSet rs = tmgl.fetchDetachedRowSet();
	                if (rs.next()) {
	                    status = rs.getBoolean("ret");
	                }
	            }
	        } catch (SQLException e) {
	            status = false;
	            LOGGER.error(whereIam + "  " + e.toString() + " " + e.getStackTrace()[0]);
	            //throw new VahanException("Error in Configuration of set for state");

	        } finally {
	            try {
	                if (tmgl != null) {
	                    tmgl.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return status;
	    }
	 
	 public  VehicleParameters fillVehicleParametersFromDobj(Owner_dobj ownerDobj, SessionVariablesModel sessionVariables,
	            Integer userLoginOffCode) {

	        VehicleParameters taxParameters = new VehicleParameters();
	        try {

	            if (ownerDobj == null) {
	                return taxParameters;
	            }
	            if (sessionVariables != null) {
	                taxParameters.setOFF_CD(sessionVariables.getOffCodeSelected());
	            }
	            if (userLoginOffCode != null && userLoginOffCode >= 0 && "RJ".equals(ownerDobj.getState_cd())) {
	                switch (sessionVariables.getUserCatgForLoggedInUser()) {
	                    case TableConstants.USER_CATG_DEALER:
	                        if (sessionVariables.getSelectedWork() != null && TableConstants.TM_ROLE_DEALER_APPROVAL == sessionVariables.getActionCodeSelected()) {
	                            taxParameters.setLOGIN_OFF_CD(sessionVariables.getOffCodeSelected());
	                        } else {
	                            taxParameters.setLOGIN_OFF_CD(userLoginOffCode);
	                        }
	                        break;
	                    case TableConstants.USER_CATG_OFF_STAFF:
	                        if (!CommonUtils.isNullOrBlank(ownerDobj.getAppl_no())) {
	                            int loginOffcd = serverUtility.getOfficeCdForDealerTempAppl(ownerDobj.getAppl_no(), ownerDobj.getState_cd(), "",userLoginOffCode);
	                            taxParameters.setLOGIN_OFF_CD(loginOffcd);
	                            if (sessionVariables.getActionCodeSelected() == TableConstants.TM_NEW_RC_VERIFICATION && taxParameters.getOFF_CD() != null && taxParameters.getOFF_CD() == loginOffcd) {
	                                taxParameters.setPREV_OFF_CD(loginOffcd);
	                            }
	                        }
	                        break;
	                }
	            } else {
	                taxParameters.setLOGIN_OFF_CD(userLoginOffCode);
	            }
	            if (ownerDobj.getOff_cd() > 0) {
	                taxParameters.setOFF_CD(ownerDobj.getOff_cd());
	            }
	            taxParameters.setAC_FITTED(ownerDobj.getAc_fitted());
	            // taxParameters.setCALENDARMONTH(ownerDobj.getc);
	            taxParameters.setCC(ownerDobj.getCubic_cap() == null ? 0.0f : ownerDobj.getCubic_cap());//float type
	            // taxParameters.setDELAYDAYS(ownerDo);
	            taxParameters.setFLOOR_AREA(ownerDobj.getFloor_area());
	            taxParameters.setFUEL(ownerDobj.getFuel());
	            taxParameters.setHP(ownerDobj.getHp() == null ? 0.0f : ownerDobj.getHp());
	            taxParameters.setLD_WT(ownerDobj.getLd_wt());
	            taxParameters.setOWNER_CD(ownerDobj.getOwner_cd());
	            if (ownerDobj.getDob_temp() != null
	                    && ownerDobj.getDob_temp().getTemp_regn_type() != null
	                    && ownerDobj.getDob_temp().getTemp_regn_type().trim().length() > 0) {
	                taxParameters.setREGN_TYPE(ownerDobj.getDob_temp().getTemp_regn_type());
	            } else {
	                taxParameters.setREGN_TYPE(ownerDobj.getRegn_type());
	            }
	            taxParameters.setSALE_AMT(ownerDobj.getSale_amt());
	            taxParameters.setSEAT_CAP(ownerDobj.getSeat_cap());
	            taxParameters.setSLEEPAR_CAP(ownerDobj.getSleeper_cap());
	            taxParameters.setSTAND_CAP(ownerDobj.getStand_cap());
	            taxParameters.setSTATE_CD(ownerDobj.getState_cd());
	            taxParameters.setUNLD_WT(ownerDobj.getUnld_wt());
	            taxParameters.setVH_CLASS(ownerDobj.getVh_class());
	            if (sessionVariables.getStateCodeSelected() != null && "RJ".equals(sessionVariables.getStateCodeSelected())) {
	                String reqdTaxMode = ownerDobj.getRqrd_tax_modes();
	                if (reqdTaxMode != null) {
	                    String taxMode = ServerUtility.getTaxModeFromReqdTaxMode(reqdTaxMode);
	                    taxParameters.setTAX_MODE(taxMode);
	                }
	            } else {
	            taxParameters.setTAX_MODE(ownerDobj.getTax_mode());
	            }
	            if (ownerDobj.getRegn_dt() != null) {
	                taxParameters.setREGN_DATE(DateUtils.parseDate(ownerDobj.getRegn_dt()));
	                taxParameters.setTAX_DUE_FROM_DATE(DateUtils.parseDate(ownerDobj.getRegn_dt()));
	            } else {
	                taxParameters.setREGN_DATE(DateUtils.parseDate(new Date()));
	                taxParameters.setTAX_DUE_FROM_DATE(DateUtils.parseDate(new Date()));
	            }

	            taxParameters.setVCH_CATG(ownerDobj.getVch_catg() != null ? ownerDobj.getVch_catg().trim() : null);
	            taxParameters.setVH_CLASS(ownerDobj.getVh_class());
	            taxParameters.setSEAT_CAP(ownerDobj.getSeat_cap());
	            if (ownerDobj.getPurchase_dt() != null) {
	                taxParameters.setVCH_AGE((int) Math.ceil(DateUtils.getDate1MinusDate2_Months(ownerDobj.getPurchase_dt(), new Date()) / 12.0));
	            }
	            taxParameters.setAUDIO_FITTED(ownerDobj.getAudio_fitted());
	            taxParameters.setVIDEO_FITTED(ownerDobj.getVideo_fitted());

	            if (ownerDobj.getVehType() <= 0) {
	                taxParameters.setVCH_TYPE(serverUtil.VehicleClassType(ownerDobj.getVh_class()));
	            } else {
	                taxParameters.setVCH_TYPE(ownerDobj.getVehType());
	            }
	            taxParameters.setREGN_NO(ownerDobj.getRegn_no());
	            taxParameters.setPERMIT_TYPE(ownerDobj.getPmt_type());
	            taxParameters.setPERMIT_SUB_CATG(ownerDobj.getPmt_catg());
	            taxParameters.setOTHER_CRITERIA(ownerDobj.getOther_criteria());
	            taxParameters.setFIT_UPTO(ownerDobj.getFit_upto() != null ? JSFUtils.getDateInDD_MMM_YYYY(DateUtils.parseDate(ownerDobj.getFit_upto())) : null);
	            if (taxParameters.getFIT_UPTO() == null || taxParameters.getFIT_UPTO().isEmpty() || taxParameters.getFIT_UPTO().trim().length() <= 0) {
	                taxParameters.setFIT_UPTO(JSFUtils.getDateInDD_MMM_YYYY(DateUtils.parseDate(new Date())));
	            }
	            taxParameters.setNORMS(ownerDobj.getNorms());
	            taxParameters.setVEH_PURCHASE_AS(ownerDobj.getVch_purchase_as());
	            if (ownerDobj.getPurchase_dt() != null) {
	                taxParameters.setPURCHASE_DATE(DateUtil.parseDateYYYYMMDDToString(ownerDobj.getPurchase_dt()));
	            }
	            if (ownerDobj.getOwner_identity() != null) {
	                taxParameters.setOWNER_CATG(ownerDobj.getOwner_identity().getOwnerCatg());
	            }
	            if (ownerDobj.getManu_yr() != null && ownerDobj.getManu_mon() != null) {
	                taxParameters.setMANUFACTUR_DATE(ownerDobj.getManu_yr().toString() + ((ownerDobj.getManu_mon() < 10) ? "0" + ownerDobj.getManu_mon() : ownerDobj.getManu_mon().toString()));
	            }
	            if (ownerDobj.getDob_temp() != null) {
	                taxParameters.setTMP_PURPOSE(ownerDobj.getDob_temp().getPurpose());
	            }
	        } catch (VahanException ve) {
	        	 LOGGER.error(ve.toString() + " " + ve.getStackTrace()[0]);
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        }
	        return taxParameters;
	    }

}

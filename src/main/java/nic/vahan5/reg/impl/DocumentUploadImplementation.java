/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.vahan5.reg.impl;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.List;
import javax.sql.RowSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.java.util.CommonUtils;
import nic.vahan.common.jsf.utils.JSFUtils;
import nic.vahan5.reg.CommonUtils.FormulaUtilities;
import nic.vahan5.reg.CommonUtils.VehicleParameters;
import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.form.dobj.FitnessDobj;
import nic.vahan5.reg.form.dobj.HpaDobj;
import nic.vahan5.reg.form.dobj.InsDobj;
import nic.vahan5.reg.form.dobj.OwnerDetailsDobj;
import nic.vahan5.reg.form.dobj.OwnerIdentificationDobj;
import nic.vahan5.reg.form.dobj.Owner_dobj;
import nic.vahan5.reg.form.dobj.Status_dobj;
import nic.vahan5.reg.form.model.SessionVariablesModel;
import nic.vahan5.reg.form.model.VTDocumentModel;
import nic.vahan5.reg.server.ServerUtil;
import nic.vahan5.reg.server.ServerUtility;
import nic.vahan5.reg.server.TableConstants;
import nic.vahan5.reg.services.DmsDocCheckUtils;

/**
 *
 * @author Kartikey Singh
 */
@Service
public class DocumentUploadImplementation implements Serializable {
	

    private static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(DocumentUploadImplementation.class);
   
@Autowired
TransactionManagerOne tmgr;

@Autowired
TransactionManagerReadOnly tmg;
   @Autowired
   ServerUtil serverUtil;
   @Autowired
   ServerUtility serverUtility;
   @Autowired
   OwnerImplementation ownerImpl;
   
   @Autowired
   FormulaUtilities formulaUtils;
   
   @Autowired
   DmsDocCheckUtils dmsDocCheckUtils;
    public OwnerDetailsDobj getVaOwnerDetailsForDocumentUpload(String applNo, String stateCd, String regn_no) throws VahanException {
        OwnerDetailsDobj dobj = null;
     //   TransactionManagerReadOnly tmgr = null;
        PreparedStatement ps = null;
        String searchType = "owner.appl_no = ? ";
        try {
            if (CommonUtils.isNullOrBlank(applNo)) {
                searchType = "owner.regn_no = ?";
            }
            String sql = " SELECT owner.*,type.descr as regn_type_descr,owcode.descr as owner_cd_descr,o.mobile_no "
                    + " FROM " + TableList.VIEW_VVA_OWNER + " owner "
                    + " left join vm_regn_type type on owner.regn_type=type.regn_typecode "
                    + " left join vm_owcode owcode on owner.owner_cd = owcode.ow_code "
                    + " left join va_owner_identification o on owner.appl_no=o.appl_no and owner.state_cd = o.state_cd "
                    + " WHERE " + searchType + " and owner.state_cd=? ";
        //    tmgr = new TransactionManagerReadOnly("getVaOwnerDetailsForDocumentUpload");
            ps = tmg.prepareStatement(sql);
            if (CommonUtils.isNullOrBlank(applNo)) {
                ps.setString(1, regn_no);
            } else {
                ps.setString(1, applNo);
            }
            ps.setString(2, stateCd);
            RowSet rs = tmg.fetchDetachedRowSet();
            if (rs.next()) {
                dobj = new OwnerDetailsDobj();
                dobj.setState_cd(rs.getString("state_cd"));
                dobj.setOff_cd(rs.getInt("off_cd"));
                dobj.setApplNo(rs.getString("appl_no"));
                dobj.setState_name(rs.getString("state_name"));
                dobj.setRegn_no(rs.getString("regn_no"));
                dobj.setRegn_dt(rs.getString("regn_dt"));
                dobj.setPurchase_dt(rs.getString("purchase_dt"));
                dobj.setPurchase_date(rs.getDate("purchase_dt"));
                dobj.setOwner_name(rs.getString("owner_name"));
                dobj.setF_name(rs.getString("f_name"));
                dobj.setRegn_type(rs.getString("regn_type"));
                dobj.setRegn_type_descr(rs.getString("regn_type_descr"));
                dobj.setChasi_no(rs.getString("chasi_no"));
                dobj.setEng_no(rs.getString("eng_no"));
                dobj.setVh_class_desc(rs.getString("vh_class_desc"));
                dobj.setFuel_descr(rs.getString("fuel_descr"));
                dobj.setVh_class(rs.getInt("vh_class"));
                if (!CommonUtils.isNullOrBlank(dobj.getRegn_dt())) {
                    dobj.setRegnDateDescr(JSFUtils.convertToStandardDateFormat(JSFUtils.getStringToDateyyyyMMdd(dobj.getRegn_dt())));
                }
                dobj.setMaker(rs.getInt("maker"));
                dobj.setVch_catg(rs.getString("vch_catg"));
                dobj.setSale_amt(rs.getInt("sale_amt"));
                dobj.setDealer_cd(rs.getString("dealer_cd"));
                if (!CommonUtils.isNullOrBlank(rs.getString("fit_upto"))) {
                    dobj.setFitUptoDescr(JSFUtils.convertToStandardDateFormat(JSFUtils.getStringToDateyyyyMMdd(rs.getString("fit_upto"))));
                }
                if (dobj.getOwnerIdentity() == null) {
                    dobj.setOwnerIdentity(new OwnerIdentificationDobj());
                    dobj.getOwnerIdentity().setMobile_no(rs.getLong("mobile_no"));
                }

                dobj.setPurchaseDateDescr(JSFUtils.convertToStandardDateFormat(JSFUtils.getStringToDateyyyyMMdd(dobj.getPurchase_dt())));
                if (serverUtil.isTransport(dobj.getVh_class(), null)) {
                    dobj.setVehTypeDescr(TableConstants.VM_VEHTYPE_TRANSPORT_DESCR);
                } else {
                    dobj.setVehTypeDescr(TableConstants.VM_VEHTYPE_NON_TRANSPORT_DESCR);
                }
            }
        } catch (VahanException ve) {
            throw ve;
        } catch (Exception ex) {
            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
            throw new VahanException(TableConstants.SomthingWentWrong);
        } finally {
            try {
                if (tmg != null) {
                    tmg.release();
                }
            } catch (Exception e) {
                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            }
        }

        return dobj;
    }
    public List<VTDocumentModel> uploadedDocsDetails(String applNo, String stateCd, int offCd) throws VahanException {
       // TransactionManagerReadOnly tmgr = null;
        PreparedStatement ps = null;
        List<VTDocumentModel> docUploadDobjList = null;
        int mandatoryFileCount = 0;
        try {
           // tmgr = new TransactionManagerReadOnly("fileUploadImpl");
            ps = tmg.prepareStatement("select pur_cd from va_details where appl_no = '" + applNo + "' and state_cd = '" + stateCd + "' order by pur_cd");
            RowSet rsPurCd = tmg.fetchDetachedRowSet();
            if (rsPurCd.next()) {
                mandatoryFileCount = dmsDocCheckUtils.getMandatoryDocumentCount(stateCd, rsPurCd.getInt("pur_cd"));
                if (mandatoryFileCount <= 0) {
                    throw new VahanException("Please contact to system Adminstrator to configure the DMS utility.");
                }
                docUploadDobjList = dmsDocCheckUtils.getUploadedDocumentList(applNo);
                if (docUploadDobjList != null && !docUploadDobjList.isEmpty()) {
                    if ((docUploadDobjList.size() == mandatoryFileCount || docUploadDobjList.size() >= mandatoryFileCount)) {
                        return docUploadDobjList;
                    } else {
                        throw new VahanException("Uploaded documents Total: " + docUploadDobjList.size() + " is less then mandatory documents Total: " + mandatoryFileCount);
                    }
                }
            }
        } catch (VahanException ve) {
            throw ve;
        } catch (Exception e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            throw new VahanException(TableConstants.SomthingWentWrong);
        } finally {
            try {
                if (tmg != null) {
                    tmg.release();
                }
            } catch (Exception e) {
                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            }
        }
        return docUploadDobjList;
    }

    public  boolean fileFlowAfterDocumentUpload(String applNo, int actionCd, int purCd, String remarks, SessionVariablesModel sessionVariablesModel, 
            boolean allowFacelessService, boolean defacement) throws VahanException {
        //ServerUtility ServerUtility=new ServerUtility();
        String empCode = sessionVariablesModel.getEmpCodeLoggedIn();
        int selectedOffCode = sessionVariablesModel.getOffCodeSelected();
        String userStatecode = sessionVariablesModel.getStateCodeSelected();
        String selectedRoleCode = sessionVariablesModel.getSelectedRoleCode();
        String clientIpAddress = sessionVariablesModel.getClientIpAddress();
        
        Status_dobj status_dobj = new Status_dobj();
      //  TransactionManager tmgr = null;
        boolean flag = false;
        try {
        //    tmgr = new TransactionManager("DocumentUploadImpl");
            if (actionCd == 0) {
                throw new VahanException("Problem in File Movement, Please go to home page and try again.");
            }
            status_dobj.setAction_cd(actionCd);
            status_dobj.setAppl_no(applNo);
            status_dobj.setPur_cd(purCd);
            status_dobj.setStatus(TableConstants.STATUS_COMPLETE);
            Owner_dobj owner_dobj = ownerImpl.set_Owner_appl_db_to_dobj(null, applNo, "", purCd, empCode, selectedOffCode);
            VehicleParameters vehParameters = formulaUtils.fillVehicleParametersFromDobj(owner_dobj, sessionVariablesModel);
            status_dobj.setVehicleParameters(vehParameters);
            serverUtility.webServiceForNextStage(status_dobj, TableConstants.STATUS_COMPLETE, null, applNo, actionCd, purCd, null, empCode, userStatecode, selectedOffCode);
            status_dobj.setOffice_remark(remarks == null ? "" : remarks);
            status_dobj.setPublic_remark("");
            status_dobj.setCntr_id("");
            if (status_dobj.getFlow_slno() == 0) {
                throw new VahanException("Problem in file movement, Please go to Home Page and try again.");
            }
            //  ServerUtil.fileFlow(tmgr, status_dobj);
            serverUtility.fileFlow(tmgr, status_dobj, actionCd, selectedRoleCode, userStatecode, selectedOffCode, empCode, clientIpAddress, allowFacelessService, defacement);
            tmgr.commit();
            flag = true;
        } catch (VahanException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            throw new VahanException("Problem in file mevement after documents upload.");
        } finally {
            try {
                if (tmgr != null) {
                    tmgr.release();
                }
            } catch (Exception e) {
                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            }
        }
        return flag;
    }
    public OwnerDetailsDobj getOwnerDetails(String applNo, String stateCd, String regn_no, int purCd) throws VahanException {
        OwnerDetailsDobj dobj = null;
        TransactionManagerReadOnly tmgr = null;
        PreparedStatement ps = null;

        try {
            if (purCd == TableConstants.VM_TRANSACTION_MAST_AUCTION) {
                String sql = "  select a.owner_name,a.f_name,a.c_add1,a.c_add2,a.c_add3,a.c_district_name,a.c_state_name,a.c_pincode, a.p_add1, a.p_add2, a.p_add3, a.p_district_name ,a.p_state_name ,a.p_pincode ,\n"
                        + " e.ins_type_descr as ins_type, e.ins_company_name as company_name, e.policy_no as policy_no,e.ins_from as ins_from, e.ins_upto as ins_upto  ,f.fncr_name,f.hp_type_descr,p.pucc_no,p.pucc_upto as pucc_val\n"
                        + " FROM   " + TableList.VIEW_VV_OWNER + " a \n"
                        + " LEFT OUTER JOIN vv_insurance e ON e.regn_no = a.regn_no\n"
                        + "  LEFT OUTER JOIN vv_hypth_details f ON f.regn_no = a.regn_no   \n"
                        + " LEFT OUTER JOIN vt_pucc p ON p.regn_no = a.regn_no  \n"
                        + " WHERE a.regn_no = ? ";
                tmgr = new TransactionManagerReadOnly("getOwnerDetails");
                ps = tmgr.prepareStatement(sql);
                ps.setString(1, regn_no);
            } else {
                String sql = " select a.owner_name,a.f_name,a.c_add1,a.c_add2,a.c_add3,a.c_district_name,a.c_state_name,a.c_pincode, a.p_add1, a.p_add2, a.p_add3, a.p_district_name ,a.p_state_name ,a.p_pincode ,\n"
                        + " e.ins_type_descr as ins_type, e.ins_company_name as company_name, e.policy_no as policy_no,e.ins_from as ins_from, e.ins_upto as ins_upto  ,f.fncr_name,f.hp_type_descr,p.pucc_no,p.pucc_val\n"
                        + " FROM  " + TableList.VIEW_VVA_OWNER + " a \n"
                        + " LEFT OUTER JOIN vva_insurance e ON e.appl_no = a.appl_no\n"
                        + " LEFT OUTER JOIN vva_hpa f ON f.appl_no = a.appl_no and f.sr_no=1  \n"
                        + " LEFT OUTER JOIN " + TableList.VA_FITNESS + " p ON p.appl_no = a.appl_no  \n"
                        + " WHERE a.appl_no = ? and a.state_cd=? ";
                tmgr = new TransactionManagerReadOnly("getOwnerDetails");
                ps = tmgr.prepareStatement(sql);
                ps.setString(1, applNo);
                ps.setString(2, stateCd);
            }


            RowSet rs = tmgr.fetchDetachedRowSet();
            if (rs.next()) {
                dobj = new OwnerDetailsDobj();
                dobj.setOwner_name(rs.getString("owner_name"));
                dobj.setF_name(rs.getString("f_name"));
                dobj.setC_add1(rs.getString("c_add1"));
                dobj.setC_add2(rs.getString("c_add2"));
                dobj.setC_add3(rs.getString("c_add3"));
                dobj.setC_district_name(rs.getString("c_district_name"));
                dobj.setC_pincode(rs.getInt("c_pincode"));
                dobj.setC_state_name(rs.getString("c_state_name"));
                dobj.setP_add1(rs.getString("p_add1"));
                dobj.setP_add2(rs.getString("p_add2"));
                dobj.setP_add3(rs.getString("p_add3"));
                dobj.setP_district_name(rs.getString("p_district_name"));
                dobj.setP_pincode(rs.getInt("p_pincode"));
                dobj.setP_state_name(rs.getString("p_state_name"));
                if (rs.getString("fncr_name") != null && rs.getString("hp_type_descr") != null && !rs.getString("fncr_name").isEmpty() && !rs.getString("hp_type_descr").isEmpty()) {
                    HpaDobj hpaDobj = new HpaDobj();
                    hpaDobj.setFncr_name(rs.getString("fncr_name"));
                    hpaDobj.setHp_type_descr(rs.getString("hp_type_descr"));
                    dobj.setHpaDobj(hpaDobj);
                }

                if (rs.getString("ins_type") != null && !rs.getString("ins_type").isEmpty()) {
                    InsDobj ins_dobj = new InsDobj();
                    ins_dobj.setInsTypeDescr(rs.getString("ins_type"));
                    ins_dobj.setIns_from(rs.getDate("ins_from"));
                    ins_dobj.setIns_upto(rs.getDate("ins_upto"));
                    ins_dobj.setPolicy_no(rs.getString("policy_no"));
                    ins_dobj.setInsCompName(rs.getString("company_name"));
                    dobj.setInsDobj(ins_dobj);
                }

                if (rs.getString("pucc_no") != null && !rs.getString("pucc_no").isEmpty() && rs.getDate("pucc_val") != null) {
                    FitnessDobj fitnessDobj = new FitnessDobj();
                    fitnessDobj.setPucc_no(rs.getString("pucc_no"));
                    fitnessDobj.setPucc_val(rs.getDate("pucc_val"));
                    dobj.setFitnessDobj(fitnessDobj);
                }

            }
        } catch (Exception ex) {
            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
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

        return dobj;
    }
   
}

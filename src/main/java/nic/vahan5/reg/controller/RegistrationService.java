package nic.vahan5.reg.controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nic.java.util.DateUtils;
import nic.vahan.common.jsf.utils.DateUtil;
import nic.vahan5.reg.CommonUtils.FormulaUtilities;
import nic.vahan5.reg.CommonUtils.VehicleParameters;
import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.MasterTableFiller;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
import nic.vahan5.reg.form.bean.fancy.RetenRegnNo_dobj;
import nic.vahan5.reg.form.dobj.AxleDetailsDobj;
import nic.vahan5.reg.form.dobj.ExArmyDobj;
import nic.vahan5.reg.form.dobj.FasTagDetailsDobj;
import nic.vahan5.reg.form.dobj.HpaDobj;
import nic.vahan5.reg.form.dobj.ImportedVehicleDobj;
import nic.vahan5.reg.form.dobj.InsDobj;
import nic.vahan5.reg.form.dobj.OtherStateVehDobj;

import nic.vahan5.reg.form.dobj.Owner_dobj;
import nic.vahan5.reg.form.dobj.PendencyBankDobj;
import nic.vahan5.reg.form.dobj.RetroFittingDetailsDobj;
import nic.vahan5.reg.form.dobj.Status_dobj;

import nic.vahan5.reg.form.dobj.TmConfigurationDealerDobj;
import nic.vahan5.reg.form.dobj.TmConfigurationDobj;
import nic.vahan5.reg.form.dobj.TmConfigurationOwnerIdentificationDobj;
import nic.vahan5.reg.form.dobj.Trailer_dobj;
import nic.vahan5.reg.form.model.AdvanceRegnNoDobjModel;
import nic.vahan5.reg.form.model.AxleBeanModel;
import nic.vahan5.reg.form.model.ComparisonBeanModel;

import nic.vahan5.reg.form.model.ExArmyBeanModel;
import nic.vahan5.reg.form.model.HpaBeanModel;
import nic.vahan5.reg.form.model.ImportedVehicleBeanModel;
import nic.vahan5.reg.form.model.InsBeanModel;
import nic.vahan5.reg.form.model.OwnerBeanModel;
import nic.vahan5.reg.form.model.RetroFittingDetailsBeanModel;
import nic.vahan5.reg.form.model.SessionVariablesModel;
import nic.vahan5.reg.form.model.VTDocumentModel;
import nic.vahan5.reg.form.model.WorkBenchModel;
//import nic.vahan5.reg.form.model.WrapperModel;
import nic.vahan5.reg.impl.AxleImplementation;
import nic.vahan5.reg.impl.BlackListedVehicleImplementation;
import nic.vahan5.reg.impl.CdImpl;
import nic.vahan5.reg.impl.DocumentUploadImplementation;
import nic.vahan5.reg.impl.ExArmyImplementation;
import nic.vahan5.reg.impl.FasTagImpl;
import nic.vahan5.reg.impl.FitnessImplementation;

import nic.vahan5.reg.impl.HpaImplementation;
import nic.vahan5.reg.impl.ImportedVehicleImplementation;

import nic.vahan5.reg.impl.InsImplementation;
import nic.vahan5.reg.impl.NewImplementation;
import nic.vahan5.reg.impl.NewVehicleFitnessImplentation;
import nic.vahan5.reg.impl.NocImplementation;
import nic.vahan5.reg.impl.OtherStateVehImplementation;
import nic.vahan5.reg.impl.OwnerIdentificationImpl;
import nic.vahan5.reg.impl.OwnerImpl;
import nic.vahan5.reg.impl.OwnerImplementation;
import nic.vahan5.reg.impl.PendencyBankDetailImpl;
import nic.vahan5.reg.impl.PermitImpl;
import nic.vahan5.reg.impl.RetroFittingDetailsImplementation;
import nic.vahan5.reg.impl.ScrappedVehicleImpl;
import nic.vahan5.reg.impl.TrailerImplementation;

import nic.vahan5.reg.rest.model.ContextMessageModel;
import nic.vahan5.reg.rest.model.ContextMessageModel.MessageContext;
import nic.vahan5.reg.rest.model.ContextMessageModel.MessageSeverity;
import nic.vahan5.reg.rest.model.WrapperModel;
import nic.vahan5.reg.server.CommonUtils;
import nic.vahan5.reg.server.ServerUtility;
import nic.vahan5.reg.server.TableConstants;



@RestController
public class RegistrationService {

	
	
	@Autowired
	ServerUtility serverUtility;
	@Autowired
	TransactionManagerOne tmgr;
	 @Autowired
     DocumentUploadImplementation documentUploadImpl;
	
	@Autowired
	PendencyBankDetailImpl pendencyBankDetailImpl;
	
	@Autowired
	NocImplementation nocImpl;
	
	@Autowired
	OtherStateVehImplementation otherStateImpl;
	@Autowired
	FormulaUtilities formulaUtils;
	
	@Autowired
	CdImpl cdImpl;
	@Autowired
	ScrappedVehicleImpl scrapImpl;
	
	@Autowired
	PermitImpl permitImpl;
	
	@Autowired
	NewImplementation newImpl;
	@Autowired
	BlackListedVehicleImplementation blkImpl;
	//@Autowired
	 //OwnerImplementation owImpl;
	
	@Autowired
	NewVehicleFitnessImplentation newVehicleFitnessImpl;
	@Autowired
	OwnerImplementation ownerImpl;
	@Autowired
	RetroFittingDetailsImplementation RetroFittingImpl;
	@Autowired
	AxleImplementation axleImpl;
	@Autowired
	FitnessImplementation fitnessImplementation;
	
	@Autowired
	InsImplementation insImpl;
	
	@Autowired
	TrailerImplementation trailerImpl;
	
	@Autowired
	HpaImplementation hpaImpl;
	@Autowired
	ExArmyImplementation exarmyImpl;
	@Autowired
	ImportedVehicleImplementation importedImpl;
	@Autowired
	OwnerIdentificationImpl identificationImpl;
	@Autowired
	MasterTableFiller masterTableFiller;
	@Autowired
	OwnerImpl ownerimpl;
	
	@Autowired
	FasTagImpl fasTagImpl;
	
	
	@GetMapping("/reg")
	public String getUpload( @RequestParam String saveType,
            @RequestParam String changedData, @RequestParam Integer userLoginOffCode)
	{
		System.out.println("kkkk");
		return "document upload";
	}

	
    @PostMapping("/save/partial")
    public WrapperModel saveDataPartially(@RequestBody WrapperModel wrapperModel,@RequestParam String saveType,
            @RequestParam String changedData, @RequestParam Integer userLoginOffCode,
            @RequestParam String clientIpAddress, @RequestParam String selectedRoleCode) throws SQLException, VahanException, Exception {
    	
    	System.out.print(wrapperModel.toString());
    	//Creating the ObjectMapper object
        ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String jsonString = mapper.writeValueAsString(wrapperModel);
        System.out.println(jsonString);
        
    	ComparisonBeanModel comparisonBean = wrapperModel.getComparisonBean();
        String purCd = comparisonBean.getPUR_CD();
        String applNo = comparisonBean.getAPPL_NO();
        String counterId = comparisonBean.getCOUNTER_ID();

      WorkBenchModel workBench = wrapperModel.getWorkBench();

        SessionVariablesModel sessionVariables = wrapperModel.getSessionVariables();
        String empCode = sessionVariables.getEmpCodeLoggedIn();
        String userStateCode = sessionVariables.getStateCodeSelected();
        int offCode = sessionVariables.getSelectedWork().getOff_cd();
        int actionCode = sessionVariables.getActionCodeSelected();
        String userId = sessionVariables.getUserIdForLoggedInUser();
        String stateCodeSelected = sessionVariables.getStateCodeSelected();
        String userCategory = sessionVariables.getUserCatgForLoggedInUser();
       // Owner_dobj ownerDobj = wrapperModel.getOwnerDobj();
        OwnerBeanModel ownerBean = wrapperModel.getOwnerBean();
        

       
       HpaBeanModel hpaBean = wrapperModel.getHpaBeanModel();
        ExArmyBeanModel exArmyBean = wrapperModel.getExArmyBean();
       ImportedVehicleBeanModel importedVehicleBean = wrapperModel.getImportedVehicleBean();
        AxleBeanModel axleBean = wrapperModel.getAxleBean();
        RetroFittingDetailsBeanModel cngDetailsBean = wrapperModel.getCngDetailsBean();
        InsBeanModel insBean = wrapperModel.getIns_bean();
        Trailer_dobj trailerDobj = wrapperModel.getTrailerDobj();
      //  InsDobj insDobj = wrapperModel.getInsDobj();


        //TransactionManager tmgr = new TransactionManager("save_ActionListener");

        TmConfigurationDealerDobj dealerConfigDobj = null;
       TmConfigurationDobj tmConfigDobj = wrapperModel.getTmConfigDobj();
       

        if (tmConfigDobj == null) {
            throw new VahanException(TableConstants.SomthingWentWrong);
        } else {
            dealerConfigDobj = tmConfigDobj.getTmConfigDealerDobj();
        }
        // Not tested
        if (ownerBean.getOwnerDobj() != null) {
            if (Integer.parseInt(purCd) == TableConstants.VM_TRANSACTION_MAST_DEALER_TEMP_VEHICLE && !tmConfigDobj.getTmConfigDealerDobj().isTempRegnAllowNonTransVeh()) {
                String exception = ownerBean.allowTempRegistrationForDealer(tmConfigDobj, userCategory, userStateCode);
                if (exception != null) {
                    throw new VahanException("Temporary Registration not allowed for Non-Transport vehicle within State. Please select the option 'Entry-New Registration' at Home Page.");
                }
            }
            if (Integer.parseInt(purCd) == TableConstants.VM_TRANSACTION_MAST_DEALER_NEW_VEHICLE) {
                boolean flag = ownerBean.transportCatgValidation(String.valueOf(ownerBean.getOwnerDobj().getVehType()));
                if (flag) {
                    throw new VahanException(TableConstants.VEH_CATG_ERR_MESS);
                }
            }
        }

        Owner_dobj ownerDobj = wrapperModel.getOwnerDobj();
        //newly added
        ownerBean.setHomoDobj(ownerDobj);

        // Not tested
        // Add for PB
        if (ownerBean.getOwner_dobj_prv() != null && ownerDobj.getVehType() == TableConstants.VM_VEHTYPE_NON_TRANSPORT && ownerBean.getOwner_dobj_prv().getVehType() != ownerDobj.getVehType()) {
            ownerDobj.setPmt_type(-1);
            ownerDobj.setPmt_catg(-1);
            ownerDobj.setServicesType("");
        }
        //end
        // Not tested
        if (ownerDobj != null && ownerDobj.getVh_class() == TableConstants.ERICKSHAW_VCH_CLASS && Integer.parseInt(purCd) == TableConstants.VM_TRANSACTION_MAST_DEALER_NEW_VEHICLE && "DL".equals(sessionVariables.getStateCodeSelected()) && ownerDobj.getOwner_cd() == TableConstants.OWNER_CODE_INDIVIDUAL) {
            if (ownerDobj.getOwner_identity() != null && CommonUtils.isNullOrBlank(ownerDobj.getOwner_identity().getAadhar_no())) {
                throw new VahanException("Please enter the owner's aadhar no. for E-rickshaw.");
            }
            String aadharNo = ownerDobj.getOwner_identity().getAadhar_no();
            ownerDobj.getOwner_identity().setAadhar_no(null);
            
            if (!saveType.equalsIgnoreCase(TableConstants.NEW_APPL_SAVETYPE_PARTIAL)) {
                PendencyBankDobj bankSubsidyDetail = workBench.getBankSubsidyDetailsDobj(ownerDobj, workBench.getBankSubsidyDetail(), stateCodeSelected, purCd, applNo);
                if (bankSubsidyDetail != null) {
                    bankSubsidyDetail.setAadharNo(aadharNo);
                    pendencyBankDetailImpl.saveOrUpdatePendencyBankDtls(tmgr, stateCodeSelected, offCode, bankSubsidyDetail, empCode);
                }
            }
        }
        
        // Not tested
  ownerimpl.tradeCertificateValidation(ownerDobj.getDealer_cd(), ownerDobj.getVch_catg(), tmConfigDobj,userCategory, userStateCode, offCode);
        // Not tested
        if (tmConfigDobj.getTmConfigDmsDobj() != null && tmConfigDobj.getTmConfigDmsDobj().getPurCd().contains("," + purCd + ",") && tmConfigDobj.getTmConfigDmsDobj().getUploadActionCd().contains("," + actionCode + ",")
                && !TableConstants.NEW_APPL_SAVETYPE_PARTIAL.equalsIgnoreCase(saveType) && ownerDobj != null && TableConstants.VM_REGN_TYPE_NEW.equals(ownerDobj.getRegn_type()) && !tmConfigDobj.getTmConfigDmsDobj().isDocsUploadAtOffice()) {
            List<VTDocumentModel> docsDetailsList = documentUploadImpl.uploadedDocsDetails(applNo, sessionVariables.getStateCodeSelected(), sessionVariables.getOffCodeSelected());
            if (docsDetailsList == null || docsDetailsList.isEmpty()) {
                throw new VahanException("Document upload is pending. Upload your document first.");
            }
        }
        // Not tested
        if (!ownerBean.isRenderOwnerDtlsPartialBtn()) {//OR
            ownerBean.checkLadenWeight(ownerBean.getOwnerDobj().getVch_catg(), ownerBean.getOwnerDobj().getLd_wt(), Integer.parseInt(purCd), ownerBean.getVch_purchase_as());
        }

        // code for noc endorsement
        // for different registration no
        if ((ownerDobj.getRegn_type() != null
                && !ownerDobj.getRegn_type().equals(""))
                && (ownerDobj.getRegn_type().equalsIgnoreCase(TableConstants.VM_REGN_TYPE_OTHER_STATE_VEHICLE)
                || ownerDobj.getRegn_type().equalsIgnoreCase(TableConstants.VM_REGN_TYPE_OTHER_DISTRICT_VEHICLE))) {
         //   NocImplementation noc_impl = new NocImplementation();
            nocImpl.checkForNocVerificationAndEndorsement(ownerDobj, null, userStateCode, offCode,userCategory);
        }

        ownerBean.validateTempRegistrationData(ownerDobj, Integer.parseInt(purCd), ownerDobj.getRegn_type(), sessionVariables);

        // Checkes if data exists in the HOMOLOGATION portal 
        if (ownerBean.getIsHomologationData() != null && !ownerBean.getIsHomologationData().equals("")) {
            if (ownerBean.getHomoUnLdWt() > ownerDobj.getUnld_wt()) {
                throw new VahanException(TableConstants.ULD_WT_ERR_MESS + " {" + ownerBean.getHomoUnLdWt() + " kgs}");
            }
        }

        // Tested
        if (applNo == null || applNo.isEmpty()) {
            applNo = serverUtility.getUniqueApplNo(tmgr, stateCodeSelected);
//                ServerUtility.saveUniqueApplNo(tmgr, applNo);
        }
        String regnNo = "NEW";
        comparisonBean.setAPPL_NO(applNo);
        comparisonBean.setREGN_NO(regnNo);

        // Populating the ownerDobj object
        ownerDobj.setAppl_no(applNo);
        ownerDobj.setRegn_no(regnNo);
        ownerDobj.getOwner_identity().setRegn_no(regnNo);
        ownerDobj.getOwner_identity().setAppl_no(applNo);
        ownerDobj.setFit_dt(new Date());
        ownerDobj.setRegn_dt(new Date());

        String regnType = workBench.getRegnType();
        String chasiNo = workBench.getChasiNo();
        String engineNo = workBench.getEngineNo();
        OtherStateVehDobj otherStateVehDobj = workBench.getOtherStateVehDobj();

        if (!CommonUtils.isNullOrBlank(saveType) && saveType.equalsIgnoreCase(TableConstants.NEW_APPL_SAVETYPE_PARTIAL)
                && !(regnType.equalsIgnoreCase(TableConstants.VM_REGN_TYPE_OTHER_STATE_VEHICLE)
                || regnType.equalsIgnoreCase(TableConstants.VM_REGN_TYPE_OTHER_DISTRICT_VEHICLE))) {
            if (CommonUtils.isNullOrBlank(ownerDobj.getChasi_no())) {
                ownerDobj.setChasi_no(chasiNo);
            }
            if (CommonUtils.isNullOrBlank(ownerDobj.getEng_no())) { // This one works
                ownerDobj.setEng_no(engineNo);
            }
        }

//        if (dealerConfigDobj != null && owner_bean.getHomoDobj() != null) {
//            if (owner_bean.getHomoDobj().getSale_amt() > owner_dobj.getSale_amt()
//                    && (owner_dobj.getOwner_cd() == TableConstants.VEH_TYPE_STATE_GOVT || owner_dobj.getOwner_cd() == TableConstants.VEH_TYPE_GOVT)
//                    && (ACTION_CODE == TableConstants.TM_ROLE_NEW_APPL
//                    || ACTION_CODE == TableConstants.TM_ROLE_NEW_APPL_TEMP
//                    || ACTION_CODE == TableConstants.TM_ROLE_DEALER_NEW_APPL
//                    || ACTION_CODE == TableConstants.TM_ROLE_DEALER_TEMP_APPL)) {
//                ServerUtil.Compare("Home_sale_amt", owner_bean.getHomoDobj().getSale_amt(), owner_dobj.getSale_amt(), compBeanList);
//            }
//            if (dealerConfigDobj.isValidateHomoSaleAmt() && owner_dobj.getOwner_cd() != TableConstants.VEH_TYPE_STATE_GOVT && owner_dobj.getOwner_cd() != TableConstants.VEH_TYPE_GOVT) {
//                workBench.checkSaleAmount(owner_bean.getHomoDobj().getSale_amt(), owner_dobj.getSale_amt());
//            }
//        }

        // Tested
        //      Insert changed data into VHA       
        serverUtility.insertIntoVhaChangedData(tmgr, applNo, changedData, empCode, userStateCode, offCode, tmConfigDobj.isAllowFacelessService(),userCategory);

        if (tmConfigDobj != null && tmConfigDobj.getTmConfigOtpDobj() != null && tmConfigDobj.getTmConfigOtpDobj().isOwner_mobile_verify_with_otp() && !CommonUtils.isNullOrBlank(saveType)
                && saveType.equalsIgnoreCase(TableConstants.NEW_APPL_SAVETYPE_PARTIAL) && (Integer.parseInt(purCd) == TableConstants.VM_TRANSACTION_MAST_DEALER_NEW_VEHICLE
                || Integer.parseInt(purCd) == TableConstants.VM_TRANSACTION_MAST_NEW_VEHICLE)) {
            serverUtility.saveUpdateOTPVerifyDetails(tmgr, ownerDobj);
        }
        if (ownerBean.getIsHomologationData() != null && !ownerBean.getIsHomologationData().equals("")
                && ownerBean.getIsHomologationData().equals(TableConstants.HOMOLOGATION_DATA)) {
            ownerDobj.setLaser_code(TableConstants.HOMOLOGATION_DATA);
            NewImplementation.insertHomologationDetails(tmgr, ownerBean.getHomoDobj(), applNo, sessionVariables.getStateCodeSelected(), sessionVariables.getOffCodeSelected());

            ownerBean.validateHomoGCW(ownerBean.getHomoDobj(), ownerDobj);
        }

        // Not tested
        if (regnType.equalsIgnoreCase(TableConstants.VM_REGN_TYPE_OTHER_STATE_VEHICLE)
                || regnType.equalsIgnoreCase(TableConstants.VM_REGN_TYPE_OTHER_DISTRICT_VEHICLE)) {
            //check for Other State and Other District vehicles when data is not available in Vahan4
            if (otherStateVehDobj != null) {
                if (regnType.equalsIgnoreCase(TableConstants.VM_REGN_TYPE_OTHER_STATE_VEHICLE)) {
                    if (otherStateVehDobj.getOldStateCD() != null && otherStateVehDobj.getOldStateCD().equalsIgnoreCase(userStateCode)) {
                        throw new VahanException("Invalid Registration Number for Other State Registration.");
                    }
                } else if (regnType.equalsIgnoreCase(TableConstants.VM_REGN_TYPE_OTHER_DISTRICT_VEHICLE)) {
                    if (otherStateVehDobj.getOldStateCD() != null && !otherStateVehDobj.getOldStateCD().equalsIgnoreCase(userStateCode)) {
                        throw new VahanException("Invalid Registration Number for Other RTO Registration.");
                    }
                }
            }

            //OtherStateVehImplementation impl = new OtherStateVehImplementation();
            otherStateVehDobj.setApplNo(applNo);
            otherStateImpl.insertUpdateOtherStateVeh(tmgr, otherStateVehDobj, empCode, userStateCode, offCode);
            //impl.insertIntoVaOtherStateVeh(tmgr, this.otherStateVehDobj);
            if (regnType.equalsIgnoreCase(TableConstants.VM_REGN_TYPE_OTHER_DISTRICT_VEHICLE)
                    || tmConfigDobj.isRegnNoNotAssignOthState()) {
            	
                VehicleParameters vehParameters = formulaUtils.fillVehicleParametersFromDobj(ownerDobj, sessionVariables);
                if (otherStateVehDobj != null) {
                    vehParameters.setLOGIN_OFF_CD(offCode);
                    if (otherStateVehDobj.getOldOffCD() != null) {
                        vehParameters.setPREV_OFF_CD(otherStateVehDobj.getOldOffCD());
                    }
                }

                if (!formulaUtils.isCondition(formulaUtils.replaceTagValues(tmConfigDobj.getOther_rto_number_change(), vehParameters), "Comparison Bean Other/RTO")) {
                    regnNo = otherStateVehDobj.getOldRegnNo();
                    comparisonBean.setREGN_NO(regnNo);
                    ownerDobj.setRegn_no(regnNo);
                    ownerDobj.getOwner_identity().setRegn_no(regnNo);
                }
            }
            ownerDobj.setRegn_dt(ownerBean.getOwnerDobj().getRegn_dt());
            ownerDobj.setRegn_upto(ownerBean.getOwnerDobj().getRegn_upto());
            ownerDobj.setFit_upto(ownerBean.getOwnerDobj().getFit_upto());
        } else if (workBench.getRegnType().equalsIgnoreCase(TableConstants.VM_REGN_TYPE_EXARMY) && ownerBean.getOwnerDobj().getAuctionDobj() != null) {
        	
        	 if (ownerBean.getOwnerDobj().getAuctionDobj().getAuctionBy() != null && ownerBean.getOwnerDobj().getAuctionDobj().getAuctionBy().equals("C")) {
                 ownerDobj.setRegn_dt(ownerBean.getOwnerDobj().getAuctionDobj().getRegnDt());
             } else {
            ownerDobj.setRegn_dt(ownerBean.getOwnerDobj().getRegn_dt());
             }
            if (tmConfigDobj.isRegnNoNotAssignOthState()) {
                regnNo = ownerBean.getOwnerDobj().getAuctionDobj().getRegnNo();
                ownerDobj.setRegn_no(regnNo);
                ownerDobj.getOwner_identity().setRegn_no(regnNo);
            }
        } else {
            // Tested
            //Check Duplicate Chassis No Except in case of Vehicle from Other State / Other Office
            serverUtility.checkChasiNoExist(ownerDobj.getChasi_no().toUpperCase());
        }
        // Not tested
        if (regnType.equalsIgnoreCase(TableConstants.VM_REGN_TYPE_CD)) {
           // CdImpl cdImpl = new CdImpl();
            ownerDobj.getCdDobj().setState_cd(ownerDobj.getState_cd());
            ownerDobj.getCdDobj().setOff_cd(ownerDobj.getOff_cd());
            ownerDobj.getCdDobj().setRegNo(regnNo);
            ownerDobj.getCdDobj().setApplNo(applNo);
            cdImpl.insertOrUpdateCdVehicleDtls(ownerDobj.getCdDobj(), tmgr);
        }
        // Not tested
        if (Integer.parseInt(purCd) == TableConstants.VM_TRANSACTION_MAST_TEMP_REG
                || Integer.parseInt(purCd) == TableConstants.VM_TRANSACTION_MAST_DEALER_TEMP_VEHICLE) {
            ownerDobj.setRegn_no("TEMPREG");
            ownerDobj.getOwner_identity().setRegn_no("TEMPREG");
            ownerDobj.getDob_temp().setAppl_no(applNo);
            ownerDobj.getDob_temp().setTemp_regn_no("TEMPREG");
        }
        // Not tested
        if (regnType.equalsIgnoreCase(TableConstants.VM_REGN_TYPE_SCRAPPED)
                || ownerBean.isIsScrapVehicleNORetain()) {
            VehicleParameters vehParameters = formulaUtils.fillVehicleParametersFromDobj(ownerDobj, sessionVariables);
            if (tmConfigDobj != null && formulaUtils.isCondition(formulaUtils.replaceTagValues(tmConfigDobj.getScrap_veh_type(), vehParameters), "getScrapVehType")) {
              //  ScrappedVehicleImpl scrapImpl = new ScrappedVehicleImpl();
                scrapImpl.updateApplNoForScrappedVeh(regnType, applNo, stateCodeSelected,
                        offCode, tmgr);
            } else {
                throw new VahanException("This  Vehicle Type Can't be Scrapped Registered For This State .");
            }
        }
        // Not tested
        // Nitin Kumar: For Saving Permit Loi Details at the time of new registration when vehicle  class is Auto.
        if (!CommonUtils.isNullOrBlank(ownerDobj.getNewLoiNo()) && ownerBean.getTmConfDobj().isNew_reg_loi() && purCd.equalsIgnoreCase(String.valueOf(TableConstants.VM_TRANSACTION_MAST_NEW_VEHICLE)) && ownerDobj.getRegn_type().equalsIgnoreCase(TableConstants.VM_REGN_TYPE_NEW) && ownerDobj.getVh_class() == 57) {
         //   PermitImpl permitImpl = new PermitImpl();
            permitImpl.verifyLoiDetails(applNo, ownerDobj.getNewLoiNo(), ownerDobj.getState_cd(), ownerDobj.getOff_cd(), tmgr);
            permitImpl.insertNewLoiDetails(applNo, ownerDobj.getNewLoiNo(), regnNo, tmgr);
        }
        // Tested
        //      Fill vehicle parameters and find the available series number for the vehicle        
        //**********Save Advanced Number Booking
        String seriesAvailMess = "";
        VehicleParameters vehicleParameters = formulaUtils.fillVehicleParametersFromDobj(ownerDobj, sessionVariables, userLoginOffCode);

        comparisonBean.setVehicleParameters(vehicleParameters);
        // Not tested
        // Update tables for Advance Registration based on AdvanceRegNo or RetenRegNo
        if (ownerBean.getAdvanceRegnNoDobj() != null) {
            AdvanceRegnNoDobjModel advanceRegNoDobj = ownerBean.getAdvanceRegnNoDobj();
            advanceRegNoDobj.setRegn_appl_no(applNo);
            newImpl.updateAdvanceRegNoDetails(advanceRegNoDobj, ownerDobj, tmgr);
            seriesAvailMess = "Vehicle Registration No " + advanceRegNoDobj.getRegn_no() + " will be allotted (as per booked Fancy/Advance Registration No)";

         //   BlackListedVehicleImplementation blkImpl = new BlackListedVehicleImplementation();
            if (blkImpl.checkRegnNoForBlackList(advanceRegNoDobj.getRegn_no(), userStateCode, offCode)) {
                throw new VahanException(advanceRegNoDobj.getRegn_no() + " : is blacklisted ");
            }
          //  OwnerImplementation owImpl = new OwnerImplementation();
            if (ownerImpl.getOwnerDetails(advanceRegNoDobj.getRegn_no(), stateCodeSelected, String.valueOf(offCode),
	    
 tmConfigDobj.isAllow_fitness_all_RTO()) != null) {
                throw new VahanException(advanceRegNoDobj.getRegn_no() + " : is already Assigned ");
            }
            if (ownerImpl.getVaOwnerDetailsForRegnNo(advanceRegNoDobj.getRegn_no()) != null) {
                throw new VahanException(" Application is already inwarded for Regn No: " + advanceRegNoDobj.getRegn_no());
            }
        } else if (ownerBean.getRetenRegNoDobj() != null) {
            RetenRegnNo_dobj retenRegNoDobj = ownerBean.getRetenRegNoDobj();
            retenRegNoDobj.setRegn_appl_no(applNo);
            newImpl.updateRetenRegNoDetails(retenRegNoDobj, tmgr);
            seriesAvailMess = "Vehicle Registration No " + retenRegNoDobj.getRegn_no() + " will be allotted (as per Retention No)";

            BlackListedVehicleImplementation blkImpl = new BlackListedVehicleImplementation();
            if (blkImpl.checkRegnNoForBlackList(retenRegNoDobj.getRegn_no(), userStateCode, offCode)) {
                throw new VahanException(retenRegNoDobj.getRegn_no() + " : is blacklisted ");
            }

          //  OwnerImplementation owImpl = new OwnerImplementation();
            if (ownerImpl.getOwnerDetails(retenRegNoDobj.getRegn_no(), stateCodeSelected, String.valueOf(offCode),
                    tmConfigDobj.isAllow_fitness_all_RTO()) != null) {
                throw new VahanException(retenRegNoDobj.getRegn_no() + " : is already Assigned ");
            }
            if (ownerImpl.getVaOwnerDetailsForRegnNo(retenRegNoDobj.getRegn_no()) != null) {
                throw new VahanException(" Application is already inwarded for Regn No: " + retenRegNoDobj.getRegn_no());
            }
        } else {
            // Tested
            seriesAvailMess = serverUtility.getAvailablePrefixSeries(vehicleParameters, userStateCode, offCode);
            if (!seriesAvailMess.equals(TableConstants.SERIES_EXHAUST_MESSAGE) && !seriesAvailMess.equals("")) {
                seriesAvailMess = "Vehicle Registration No will be Generated from the Series " + seriesAvailMess + ".";
}
            }
  if (TableConstants.USER_CATG_DEALER.equals(sessionVariables.getUserCatgForLoggedInUser())) {
                vehicleParameters.setPUR_CD(Integer.parseInt(purCd));
                if (formulaUtils.isCondition(formulaUtils.replaceTagValues(tmConfigDobj.getTmConfigDealerDobj().getRegnRestrictionAtDealer(), vehicleParameters), "getRegnRestrictionAtDealer")) {
                    throw new VahanException(tmConfigDobj.getTmConfigDealerDobj().getRegnRestrictionMessage());
                
        }
}
//        }
        comparisonBean.setSeriesAvailMess(seriesAvailMess);
        // Not tested
        // Check if temp registration dates are correct/valid
        if (ownerDobj.getTempReg() != null) {
            ownerDobj.getTempReg().setRegn_no(regnNo);
            ownerDobj.getTempReg().setAppl_no(applNo);
            if (DateUtils.compareDates(ownerDobj.getTempReg().getTmp_valid_upto(),
                    ownerDobj.getTempReg().getTmp_regn_dt()) <= 1) {
                throw new VahanException("Temp Valid Upto can not be less than Temp Regn Dt ");
            }
        }
        // Ttested
        //     Check fitness for the new vehicle       
       // NewVehicleFitnessImplentation newVehicleFitnessImpl = new NewVehicleFitnessImplentation();
        boolean isNewVehicleFitness = newVehicleFitnessImpl.checkForPreRegFitness(chasiNo, engineNo, userStateCode);
        // Not tested
        if (isNewVehicleFitness && (Integer.parseInt(purCd) == TableConstants.VM_TRANSACTION_MAST_NEW_VEHICLE
                || Integer.parseInt(purCd) == TableConstants.VM_TRANSACTION_MAST_DEALER_NEW_VEHICLE)) {

            Owner_dobj preRegFitowner_dobj = null;
           // OwnerImplementation owner_Impl = new OwnerImplementation();
            preRegFitowner_dobj = ownerImpl.set_Owner_appl_db_to_dobj(null, null, ownerDobj.getChasi_no(), Integer.parseInt(purCd),stateCodeSelected,offCode);
            newVehicleFitnessImpl.saveInVtFitnessFromVtFitnessChassis(tmgr, ownerDobj.getChasi_no(), ownerDobj.getEng_no(), applNo,empCode);
            newImpl.insertIntoVhaOwner(tmgr, preRegFitowner_dobj.getAppl_no(), empCode);
            serverUtility.deleteFromTable(tmgr, "", preRegFitowner_dobj.getAppl_no(), TableList.VA_OWNER);
            serverUtility.deleteFromTable(tmgr, "", preRegFitowner_dobj.getAppl_no(), TableList.VA_TMP_REGN_DTL);
            serverUtility.deleteFromTableByChassis(tmgr, preRegFitowner_dobj.getChasi_no(), TableList.VT_FITNESS_CHASSIS);
            newImpl.insertintoVhaHomologationDetails(tmgr, preRegFitowner_dobj.getAppl_no(), empCode);
            serverUtility.deleteFromTable(tmgr, "", preRegFitowner_dobj.getAppl_no(), TableList.VA_HOMO_DETAILS);
            RetroFittingImpl.insertIntoVhaCng(tmgr, preRegFitowner_dobj.getAppl_no(), empCode);
            axleImpl.insertIntoVhaAxle(tmgr, preRegFitowner_dobj.getAppl_no(), empCode);
            fitnessImplementation.insertIntoVhaSpeedGovernor(preRegFitowner_dobj.getAppl_no(), tmgr, empCode);
            fitnessImplementation.insertIntoVhaReflectiveTape(tmgr, preRegFitowner_dobj.getAppl_no(), empCode);
            RetroFittingImpl.deleteFromVaRetroFittingDetails(tmgr, preRegFitowner_dobj.getAppl_no());
            axleImpl.deleteFromVaAxle(tmgr, preRegFitowner_dobj.getAppl_no());
            fitnessImplementation.deleteVaSpeedGovernor(preRegFitowner_dobj.getAppl_no(), tmgr);
            fitnessImplementation.deleteVaReflectiveTape(preRegFitowner_dobj.getAppl_no(), tmgr);
            ownerDobj.setAppl_no(applNo);
        }

        String regionStr = "";
        if (ownerDobj.getRegion_covered() != null) {
            for (String region : ownerDobj.getRegion_covered()) {
                regionStr += region + ",";
            }
        }
        if (!CommonUtils.isNullOrBlank(regionStr)) {
            ownerDobj.setRegion_covered_str(regionStr);
        } else {
            ownerDobj.setRegion_covered_str(null);
        }

        // Tested
        //      Insert/Update vehicle owner     
        newImpl.insertOrUpdateVaOwner(tmgr, ownerDobj, empCode, Integer.parseInt(purCd), offCode, userStateCode, actionCode);
        if (userStateCode.equals("KL") && TableConstants.VHC_TRANSPORT_CATG.contains("," + String.valueOf(ownerDobj.getVh_class()) + ",")) {
            newImpl.insertOrUpdateVaOwnerOther(tmgr, ownerDobj, empCode, userStateCode, offCode);
        }
        //      Check validity/authenticity of ownerShipType and Insurance      
InsDobj insDobj = wrapperModel.getInsDobj();
       
     //   System.out.print(insDobj.getPolicy_no());
        // Tested
        if (!CommonUtils.isNullOrBlank(insDobj.getPolicy_no())) {
            if (insImpl.validateOwnerCodeWithInsType(ownerDobj.getOwner_cd(), insDobj.getIns_type())) {
                insImpl.insertUpdateInsurance(tmgr, applNo, regnNo, insDobj, stateCodeSelected, offCode, empCode);
            } else {
                throw new VahanException("Invalid Combination of Ownership Type & Insurance Type.");
            }
        }
        // Not tested
        if (trailerDobj != null && trailerDobj.getChasi_no() != null && !trailerDobj.getChasi_no().trim().isEmpty()) {
            // Checks if values aren't empty
        	trailerImpl.validationTrailer(trailerDobj);
            Trailer_dobj validateTrailerChassisDobj = trailerImpl.checkTrailerChassis_owner(trailerDobj.getChasi_no());
            if (validateTrailerChassisDobj != null) {
                throw new VahanException("Duplicate Trailer Chassis No." + validateTrailerChassisDobj.getDup_chassis()
                        + " against the registration no " + validateTrailerChassisDobj.getRegn_no() + " State "
                        + serverUtility.getStateNameByStateCode(validateTrailerChassisDobj.getState_cd()) + " and Office "
                        + serverUtility.getOfficeName(validateTrailerChassisDobj.getOff_cd(), validateTrailerChassisDobj.getState_cd()));
            } else {
                validateTrailerChassisDobj = trailerImpl.checkTrailerChassis_trailer(trailerDobj.getChasi_no());
                if (validateTrailerChassisDobj != null) {
                    throw new VahanException("Duplicate Trailer Chassis No." + validateTrailerChassisDobj.getDup_chassis()
                            + " against the registration no " + validateTrailerChassisDobj.getRegn_no() + " State "
                            + serverUtility.getStateNameByStateCode(validateTrailerChassisDobj.getState_cd()) + " and Office "
                            + serverUtility.getOfficeName(validateTrailerChassisDobj.getOff_cd(), validateTrailerChassisDobj.getState_cd()));
                } else {
                	trailerImpl.insertUpdateTrailer(tmgr, applNo, regnNo, ownerDobj.getChasi_no(), trailerDobj,
                            empCode, userStateCode, offCode);
                }
            }
        }

        
         // Not Tested HPA Bean Details
         
        if (hpaBean.getHpaDobj().getHp_type() != null && !hpaBean.getHpaDobj().getHp_type().equalsIgnoreCase("-1")) {
            hpaBean.getHpaDobj().setRegn_no(regnNo);
            hpaBean.getHpaDobj().setAppl_no(applNo);
            String errorHpa = validateHPAEntry(hpaBean.getHpaDobj());
            ArrayList listHpaDobj = new ArrayList();
            listHpaDobj.add(hpaBean.getHpaDobj());
            if (errorHpa.length() > 0) {
                throw new VahanException(errorHpa);
            } else {
                if (!workBench.isHypothecated()) {// delete from va_hpa during Hypothication not Selected
                    hpaImpl.insertDeleteFromVaHpa(tmgr, hpaBean.getHpaDobj().getAppl_no(), empCode);
                } else {
                    if (listHpaDobj.size() > 1) {
                        throw new VahanException("Multiple Hypothecation not allowed.");
                    }
                   hpaImpl.insertUpdateHPA(tmgr, listHpaDobj, stateCodeSelected, offCode, empCode);// use hpa_entry_bean
                }
            }
        }

        
         // Not Tested ExArmyVehicle Details
         
        if (ownerBean.isExArmyVehicle_Visibility_tab()) {
            String error_exArmy = validateExArmyForm(exArmyBean);
            if (error_exArmy.length() > 0) {
                wrapperModel.setContextMessageModel(new ContextMessageModel(MessageContext.FACES, MessageSeverity.INFO, false, error_exArmy, "Error..."));
            } else {
                ExArmyDobj exArmy_dobj = exArmyBean.setExArmyBean_To_Dobj();
                exarmyImpl.saveExArmyVehicleDetails_Impl(exArmy_dobj, applNo, tmgr, empCode, userStateCode, offCode);
            }
        } else {// Tested
        	exarmyImpl.insertIntoVhaExArmy(tmgr, applNo, empCode);
        	exarmyImpl.deleteFromVaExArmy(tmgr, applNo);
        }

         //Not tested Imported Vehicle Details
         
        if (ownerBean.isImportedVehicle_Visibility_tab() && !ownerBean.isRenderOwnerDtlsPartialBtn()) {
            String error_impVehicle = validateImpVehicleForm(importedVehicleBean);
            if (error_impVehicle.length() > 0) {
                wrapperModel.setContextMessageModel(new ContextMessageModel(MessageContext.FACES, MessageSeverity.INFO, false, error_impVehicle, "Error..."));
            } else {
                ImportedVehicleDobj impDobj = importedVehicleBean.setBean_to_Dobj();
                importedImpl.saveImportedDetails_Impl(impDobj, applNo, tmgr, empCode, userStateCode, offCode);
            }
        } else {// Tested
        	importedImpl.insertIntoVhaImpVeh(tmgr, applNo, empCode);
        	importedImpl.deleteFromVaImp(tmgr, applNo);
        }

          // Not tested Axle Details
         
        if ((ownerBean.isAxleDetail_Visibility_tab()
                && !ownerBean.isRenderOwnerDtlsPartialBtn()) || (isNewVehicleFitness && axleBean.getTf_Other1() != null && !"".equalsIgnoreCase(axleBean.getTf_Other1()))) {
            String error_axleDetail = validateAxleForm(axleBean);
            if (axleBean.getTf_Other1() != null && axleBean.getTf_Other1().length() != 0 && axleBean.getTf_Other_tyre() == 0) {
                wrapperModel.setContextMessageModel(new ContextMessageModel(MessageContext.REQUEST, MessageSeverity.INFO, true, "Alert!", " Please fill Other Tyre!  "));
                return wrapperModel;
            }
            if (axleBean.getTf_Other_tyre() > 0 && (axleBean.getTf_Other1() == null || axleBean.getTf_Other1().equals(""))) {
                wrapperModel.setContextMessageModel(new ContextMessageModel(MessageContext.REQUEST, MessageSeverity.INFO, true, "Alert!", " Please fill Other !  "));
//               RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Alert!", " Please fill Other !  "));
                return wrapperModel;
            }
            if (axleBean.getTf_Tandem1() != null && axleBean.getTf_Tandem1().length() != 0 && axleBean.getTf_Tandem_tyre() == 0) {
                wrapperModel.setContextMessageModel(new ContextMessageModel(MessageContext.REQUEST, MessageSeverity.INFO, true, "Alert!", " Please fill Tandem Tyre!  "));
                return wrapperModel;
            }
            if (axleBean.getTf_Tandem_tyre() > 0 && (axleBean.getTf_Tandem1() == null || axleBean.getTf_Tandem1().equals(""))) {
                wrapperModel.setContextMessageModel(new ContextMessageModel(MessageContext.REQUEST, MessageSeverity.INFO, true, "Alert!", " Please fill Tandem !  "));
                return wrapperModel;
            }

            if (error_axleDetail.length() > 0) {
                wrapperModel.setContextMessageModel(new ContextMessageModel(MessageContext.FACES, MessageSeverity.INFO, false, error_axleDetail, "Error..."));
            } else {
                AxleDetailsDobj axleDobj = axleBean.setBean_To_Dobj();
                axleImpl.saveAxleDetails_Impl(axleDobj, applNo, tmgr, empCode, userStateCode, offCode);
            }
        } else { // Tested
        	axleImpl.insertIntoVhaAxle(tmgr, applNo, empCode);
        	axleImpl.deleteFromVaAxle(tmgr, applNo);
        }

        //Note tested Retro Fitting Details
         
        if ((ownerBean.isCngDetails_Visibility_tab() && !ownerBean.isRenderOwnerDtlsPartialBtn()) || (isNewVehicleFitness && !(CommonUtils.isNullOrBlank(cngDetailsBean.getTf_kit_no())))) {
            String error_cngDetail = validateCngForm(cngDetailsBean);
            if (error_cngDetail.length() > 0) {
                wrapperModel.setContextMessageModel(new ContextMessageModel(MessageContext.FACES, MessageSeverity.INFO, false, error_cngDetail, "Error..."));
            } else {
                RetroFittingDetailsDobj cng_dobj = cngDetailsBean.setBean_To_Dobj();
                RetroFittingImpl.saveCngVehicleDetails_Impl(cng_dobj, applNo, tmgr, empCode, userStateCode, offCode);
            }
        } else { // Tested
        	RetroFittingImpl.insertIntoVhaCng(tmgr, applNo, empCode);
        	RetroFittingImpl.deleteFromVaRetroFittingDetails(tmgr, applNo);
        }
        // Tested
        //This check is used to restrict the Diseel Vehicle Registration in Delhi Only 
        String isCriteriaMatchMsg = serverUtility.isNewRegnNotAllowed(vehicleParameters, userStateCode, offCode);
        if (isCriteriaMatchMsg != null && !isCriteriaMatchMsg.isEmpty()) {
            throw new VahanException(isCriteriaMatchMsg);
        }
        // Tested
        //  check for DL validation according to the state configuration    
        //OwnerIdentificationImpl identificationImpl = new OwnerIdentificationImpl();
        TmConfigurationOwnerIdentificationDobj tmConfigOwnerId = identificationImpl.getTmConfigurationOwnerIdentification(userStateCode);
        //      Check if DL is valid if DL is mandatory for the given state.        
        if (tmConfigOwnerId != null) {
            VehicleParameters vehParameters = formulaUtils.fillVehicleParametersFromDobj(ownerDobj, sessionVariables, userLoginOffCode);
            if (tmConfigOwnerId.isDl_required() != null
                    && formulaUtils.isCondition(formulaUtils.replaceTagValues(tmConfigOwnerId.isDl_required(), vehParameters), "isDl_required()")) {
                if (ownerDobj.getOwner_identity() != null
                        && (ownerDobj.getOwner_identity().getDl_no() == null || ownerDobj.getOwner_identity().getDl_no().trim().isEmpty())) {
                    throw new VahanException("Invalid Driving Licence No, It is Mandatory to Fill Proper DL No on this Vehicle.");
                }
            }
        }
        // Tested
        //      Validate Purchase       
        ownerImpl.validatePurchaseAs(ownerDobj, regnType, Integer.parseInt(purCd));

        // Validating Vehicle Norms
        ArrayList<Status_dobj> applicationDetails = serverUtility.applicationStatusByApplNo(applNo, sessionVariables.getStateCodeSelected());
        Date applDt = new Date();
        if (!applicationDetails.isEmpty()) {
            vehicleParameters.setAPPL_DATE(applicationDetails.get(0).getAppl_dt());
            applDt = DateUtil.parseDate(DateUtil.convertStringYYYYMMDDToDDMMYYYY(applicationDetails.get(0).getAppl_dt()));
        } else {
            vehicleParameters.setAPPL_DATE(DateUtil.getCurrentDate_YYYY_MM_DD());
        }
        if (!CommonUtils.isNullOrBlank(saveType)) {
            if ((saveType.equalsIgnoreCase(TableConstants.NEW_APPL_SAVETYPE_PARTIAL) && ownerBean.getHomoDobj() != null) || saveType.equalsIgnoreCase(TableConstants.NEW_APPL_SAVETYPE_COMPLETE)) {
                vehicleParameters.setPUR_CD(Integer.parseInt(purCd));
                vehicleParameters.setACTION_CD(actionCode);
                boolean isValidForRegn = serverUtility.validateVehicleNorms(ownerDobj, Integer.parseInt(purCd), vehicleParameters, tmConfigDobj.getTmConfigDealerDobj());
                if (!isValidForRegn) {
                    throw new VahanException("State Transport Department has not authorized you to enter NEW Registration Application for '" +masterTableFiller.loadMasterTables().VM_VH_CLASS.getDesc(ownerDobj.getVh_class() + "") + "' with emission norms " + masterTableFiller.loadMasterTables().VM_NORMS.getDesc(ownerDobj.getNorms() + "") + " , Purchase Date: " + DateUtil.parseDateToString(ownerDobj.getPurchase_dt()) + " , Application Date: " + DateUtil.parseDateToString(applDt) + ", please contact respective Registering Authority regarding this.");
                }
     ownerBean.validatePurchaseDate(applDt); // Validate purchase date.
                    // fasTag validation here 
                    if (tmConfigDobj != null && tmConfigDobj.getTmConfigurationFasTag() != null) 
                    {
                    	FasTagDetailsDobj fasTagDobjByService = null;
                    	FasTagDetailsDobj fasTagDobj=null;
                        if (formulaUtils.isCondition(formulaUtils.replaceTagValues(tmConfigDobj.getTmConfigurationFasTag().getFasTagCondition(), vehicleParameters), "getFasTagCondition()")) {
                            workBench.setRenderFasTagDialog(true);
                            
                             fasTagDobj = fasTagImpl.getFasTagDetails(ownerDobj.getChasi_no(), ownerDobj.getRegn_no(), ownerDobj.getRegn_type());
                            if (fasTagDobj == null) {
                                try {
                                    fasTagDobjByService = fasTagImpl.getFasTagDetailsByService(ownerDobj.getChasi_no(), ownerDobj.getEng_no());
                                } catch (VahanException ve) {
                                    if (tmConfigDobj.getTmConfigurationFasTag().isFasTagMandatory()) {
                                        throw ve;
                                    }
                                }
            }
        } else {
                                workBench.setIsFasTagInstalled(true);
                                
				
				
				
				if (!TableConstants.VM_REGN_TYPE_NEW.equals(ownerDobj.getRegn_type()) && !TableConstants.VM_REGN_TYPE_TEMPORARY.equals(ownerDobj.getRegn_type())
                                        && !TableConstants.VM_REGN_TYPE_EXARMY.equals(ownerDobj.getRegn_type())) {
                                    fasTagImpl.moveToHistory(tmgr, ownerDobj, sessionVariables.getEmpCodeLoggedIn());
                                }
                            }
                            if (fasTagDobj == null && fasTagDobjByService == null) {
                                if (tmConfigDobj.getTmConfigurationFasTag().isFasTagMandatory()) {
                                    throw new VahanException("FASTag installation is mandatory, first install the FASTag then try again !!!");
                                }
                            } else if (fasTagDobjByService != null) {
                                workBench.setIsFasTagInstalled(true);
                                fasTagImpl.insertFasTagDetails(tmgr, ownerDobj, sessionVariables.getEmpCodeLoggedIn(), fasTagDobjByService);
                            }
                        }
                    }
                }
            
	
	
else {
            throw new VahanException("Something Went Wrong, Please go to HOME page and try again.");
        }

        // Not tested
        //validation of seating capicity based on vehicle class
        if (!CommonUtils.isNullOrBlank(saveType)
                && !saveType.equalsIgnoreCase(TableConstants.NEW_APPL_SAVETYPE_PARTIAL)) {
          //  OwnerImplementation ownerImpl = new OwnerImplementation();
            ownerImpl.seatingCapacityValidation(ownerDobj.getSeat_cap(), ownerDobj.getVh_class(), TableConstants.VH_CLASS_ALLOWED_ZERO_SEAT_CAP);

            serverUtility.saveVaOwnerDisclaimerDetails(ownerDobj, tmgr, sessionVariables.getEmpCodeLoggedIn(), actionCode);

            if (ownerBean.getHomoDobj() == null) {
            	
            	System.out.println("ownerobj..."+ownerDobj.toString());
            	ownerBean.validateHomoMaker(ownerDobj);
            }
            serverUtility.validateMinInsuranceValidity(insBean.getSelectedYear(), ownerDobj.getVh_class(), ownerDobj.getRegn_type(), Integer.parseInt(purCd), insBean.getInsType());
        }
        // Tested
        //      Get the status object       
        Status_dobj status = new Status_dobj();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String dt = sdf.format(new Date());
        status.setAppl_dt(dt);
        status.setAppl_no(applNo);
        status.setRegn_no(regnNo);
        status.setPur_cd(Integer.parseInt(purCd));
        if (Integer.parseInt(purCd) == TableConstants.VM_TRANSACTION_MAST_NEW_VEHICLE
                && actionCode == TableConstants.TM_ROLE_NEW_APPL) {
            //    status.setAction_cd(TableConstants.TM_ROLE_NEW_REGISTRATION_FEE);
            status.setEmp_cd(0);
        } else if (Integer.parseInt(purCd) == TableConstants.VM_TRANSACTION_MAST_DEALER_NEW_VEHICLE
                && actionCode == TableConstants.TM_ROLE_DEALER_NEW_APPL) {
            //     status.setAction_cd(TableConstants.TM_ROLE_DEALER_NEW_REGISTRATION_FEE);
            status.setEmp_cd(Long.valueOf(empCode));
            serverUtility.insertDealerHSRPPendencyDetails(tmgr, ownerDobj, ownerDobj.getRegn_no(), tmConfigDobj);
        } else if (Integer.parseInt(purCd) == TableConstants.VM_TRANSACTION_MAST_TEMP_REG
                && actionCode == TableConstants.TM_ROLE_NEW_APPL_TEMP) {
            //    status.setAction_cd(TableConstants.TM_ROLE_TEMP_REGISTRATION_FEE);
            status.setRegn_no("TEMPREG");
        } else if (Integer.parseInt(purCd) == TableConstants.VM_TRANSACTION_MAST_DEALER_TEMP_VEHICLE
                && actionCode == TableConstants.TM_ROLE_DEALER_TEMP_APPL) {
            status.setEmp_cd(Long.valueOf(empCode));
            status.setRegn_no("TEMPREG");
        }
        // Not tested
        // Insert or update speed governor Dobj in VA
        if (ownerDobj.getSpeedGovernorDobj() != null) {
            ownerDobj.getSpeedGovernorDobj().setAppl_no(applNo);
            ownerDobj.getSpeedGovernorDobj().setRegn_no(status.getRegn_no());
            ownerDobj.getSpeedGovernorDobj().setState_cd(sessionVariables.getStateCodeSelected());
            ownerDobj.getSpeedGovernorDobj().setOff_cd(sessionVariables.getOffCodeSelected());
            fitnessImplementation.insertUpdateVaSpeedGovernor(ownerDobj.getSpeedGovernorDobj(), tmgr, empCode);
        }
        // Not tested
        // Insert or update reflective tape Dobj in VA
        if (ownerDobj.getReflectiveTapeDobj() != null) {
            ownerDobj.getReflectiveTapeDobj().setApplNo(applNo);
            ownerDobj.getReflectiveTapeDobj().setRegn_no(status.getRegn_no());
            ownerDobj.getReflectiveTapeDobj().setStateCd(sessionVariables.getStateCodeSelected());
            ownerDobj.getReflectiveTapeDobj().setOffCd(sessionVariables.getOffCodeSelected());
            fitnessImplementation.insertOrUpdateVaReflectiveTape(tmgr, ownerDobj.getReflectiveTapeDobj(), empCode);
        }
        // Tested
        status.setOffice_remark("");
        status.setPublic_remark("");
        status.setStatus("C");
        status.setOff_cd(offCode);
        status.setState_cd(stateCodeSelected);
        int initialFlow[] = serverUtility.getInitialAction(tmgr, status.getState_cd(), Integer.parseInt(purCd), vehicleParameters);
        // Tested
        if (initialFlow != null) {
            status.setFlow_slno(initialFlow[1]);
            status.setFile_movement_slno(initialFlow[1]);
            status.setAction_cd(initialFlow[0]);
        } else {
            throw new VahanException("Please contact to System Administrator to configure the file flow for the Purpose " + masterTableFiller.masterTables.TM_PURPOSE_MAST.getDesc(purCd));
        }
        // Not tested
        if (otherStateVehDobj != null && vehicleParameters != null) {
            vehicleParameters.setLOGIN_OFF_CD(offCode);
            if (otherStateVehDobj.getOldOffCD() != null) {
                vehicleParameters.setPREV_OFF_CD(otherStateVehDobj.getOldOffCD());
            }
        }
        status.setVehicleParameters(vehicleParameters);
        // Tested
        //  Insert the application in the VA_STATUS table if it doesn't exist there  
        ArrayList<Status_dobj> applicationStatus = serverUtility.applicationStatusByApplNo(applNo, status.getState_cd());
        if (applicationStatus.isEmpty()) {
            serverUtility.fileFlowForNewApplication(tmgr, status, userId, clientIpAddress);
        }
        // Tested
        if (!CommonUtils.isNullOrBlank(applNo) && !CommonUtils.isNullOrBlank(saveType) && saveType.equalsIgnoreCase(TableConstants.NEW_APPL_SAVETYPE_PARTIAL)) {
        } else {
            serverUtility.webServiceForNextStage(status, counterId, applNo, actionCode, Integer.parseInt(purCd), null, tmgr,
                    selectedRoleCode, empCode, offCode);
           serverUtility.fileFlow(tmgr, status, actionCode, selectedRoleCode, userStateCode, offCode, empCode, clientIpAddress,
                    tmConfigDobj.isAllowFacelessService(), tmConfigDobj.isDefacement());
        }

        tmgr.commit();
        tmgr.release();

        workBench.setOtherStateVehDobj(otherStateVehDobj);

        wrapperModel.setTmConfigDobj(tmConfigDobj);
        wrapperModel.setWorkBench(workBench);
        wrapperModel.setComparisonBean(comparisonBean);
        wrapperModel.setOwnerDobj(ownerDobj);
        wrapperModel.setStatusDobj(status);
       
 
        
System.out.print(wrapperModel.toString());
        return  wrapperModel;
    	    


    }
    
    private String validateHPAEntry(HpaDobj hpaDobj) {
        String errorMsg = "";
        if (hpaDobj == null) {
            return errorMsg = "HPA Details are blank";
        }
        if (CommonUtils.isNullOrBlank(hpaDobj.getRegn_no())) {
            errorMsg = "Blank Registration Number";
        } else if (CommonUtils.isNullOrBlank(hpaDobj.getHp_type())) {
            errorMsg = "Blank HP Type";
        } else if (CommonUtils.isNullOrBlank(hpaDobj.getFncr_name())) {
            errorMsg = "Blank Financer Name";
        } else if (CommonUtils.isNullOrBlank(hpaDobj.getFncr_add1())) {
            errorMsg = "Blank Financer Address";
        } else if (CommonUtils.isNullOrBlank(hpaDobj.getFncr_state())) {
            errorMsg = "Blank Financer State";
        } else if (hpaDobj.getFncr_district() == 0) {
            errorMsg = "Blank Financer District";
        } else if (hpaDobj.getFncr_pincode() == null || hpaDobj.getFncr_pincode() == 0) {
            errorMsg = "Blank Financer Pin Code";
        }
        return errorMsg;
    }
    
    private String validateExArmyForm(ExArmyBeanModel exArmyBean) {
        String errorMsg = "";
        if (exArmyBean.getTf_Voucher_no().equalsIgnoreCase("")) {
            errorMsg = "Blank Voucher number.";
        } else if (exArmyBean.getTf_VoucherDate() == null) {
            errorMsg = "Blank Voucher Date.";
        } else if (exArmyBean.getTf_POP().equalsIgnoreCase("")) {
            errorMsg = "Blank Place of Purchase.";
        }
        return errorMsg;
    }
    private String validateImpVehicleForm(ImportedVehicleBeanModel importedVehicleBean) {
        String errorMsg = "";
        if (importedVehicleBean.getCm_country_imp() == 0) {
            errorMsg = "Blank Country Name.";
        } else if (importedVehicleBean.getTf_dealer_imp().equalsIgnoreCase("")) {
            errorMsg = "Blank Dealer Name.";
        } else if (importedVehicleBean.getTf_foreign_imp().equalsIgnoreCase("")) {
            errorMsg = "Blank Foreign Registration Number.";
        } else if (importedVehicleBean.getTf_place_imp().equalsIgnoreCase("")) {
            errorMsg = "Blank Place of RTO Office/Purchase.";
        } else if (importedVehicleBean.getTf_YOM_imp() == null) {
            errorMsg = "Blank Year of Manufacture.";
        }
        return errorMsg;
    }
    
    private String validateAxleForm(AxleBeanModel axleBean) {
        String errorMsg = "";
        if (axleBean.getTf_Front1().equalsIgnoreCase("")) {
            errorMsg = "Blank Front Axle Detail";
        } else if (axleBean.getTf_Rear1().equalsIgnoreCase("")) {
            errorMsg = "Blank Rear Axle Detail";
        } else if (axleBean.getTf_Front() == null) {
            errorMsg = "Blank Front Axle Detail";
        } else if (axleBean.getTf_Rear() == null) {
            errorMsg = "Blank Rear Axle Detail";
        }
        return errorMsg;
    }
    private String validateCngForm(RetroFittingDetailsBeanModel cngDetailsBean) {
        String errorMsg = "";
        if (CommonUtils.isNullOrBlank(cngDetailsBean.getTf_kit_no())) {
            errorMsg = "Blank Kit Number Detail";
        } else if (CommonUtils.isNullOrBlank(cngDetailsBean.getTf_kit_type())) {
            errorMsg = "Blank Kit Type Detail";
        } else if (CommonUtils.isNullOrBlank(cngDetailsBean.getTf_workshop())) {
            errorMsg = "Blank Workshop Detail";
        } else if (CommonUtils.isNullOrBlank(cngDetailsBean.getTf_license_no())) {
            errorMsg = "Blank Workshop License Number";
        } else if (CommonUtils.isNullOrBlank(cngDetailsBean.getTf_manu_name())) {
            errorMsg = "Blank Manufacture Name";
        } else if (CommonUtils.isNullOrBlank(cngDetailsBean.getTf_cyc_sr_no())) {
            errorMsg = "Blank Cyclinder Sr. Number";
        } else if (CommonUtils.isNullOrBlank(cngDetailsBean.getTf_poll_norms())) {
            errorMsg = "Blank Pollution Norms Details";
        } else if (CommonUtils.isNullOrBlank(cngDetailsBean.getTf_approv_lettr_no())) {
            errorMsg = "Blank Approval Letter Number";
        } else if (cngDetailsBean.getCal_approv_dt() == null) {
            errorMsg = "Blank Approval Date";
        }
        return errorMsg;
    }
    
    @PostMapping("/save/final")
    public Map<String, String> saveFinalDataRecords(@RequestBody OwnerBeanModel ownerModel) {
        Map<String, String> saveDetails = new TreeMap<String, String>();

        ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String jsonString;
		try {
			jsonString = mapper.writeValueAsString(ownerModel);
			 System.out.println(jsonString);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        if (Integer.parseInt(ownerModel.getVehType()) == TableConstants.VM_VEHTYPE_TRANSPORT) {
            saveDetails.put("Vehicle Type", "Transport");
        }
        if (Integer.parseInt(ownerModel.getVehType()) == TableConstants.VM_VEHTYPE_NON_TRANSPORT) {
            saveDetails.put("Vehicle Type", "Non-Transport");
        }
        if (ownerModel.getSale_amt() != null) {
            saveDetails.put("Sale Amount", ownerModel.getSale_amt().toString());
        }

        if (ownerModel.getOwnerDobj().getVch_catg() != null) {
            String vmCatgLabel = ServerUtility.getCustomLableFromSelectedListToShow(ownerModel.getList_vm_catg(), ownerModel.getOwnerDobj().getVch_catg());
            saveDetails.put("Vehicle Category", vmCatgLabel);
        }
        if (ownerModel.getOwnerDobj().getVh_class() > 0) {
            String vhClassLabel = ServerUtility.getCustomLableFromSelectedListToShow(ownerModel.getList_vh_class(), String.valueOf(ownerModel.getOwnerDobj().getVh_class()));

            saveDetails.put("Vehicle Class", vhClassLabel);
        }
        return saveDetails;
    }
    
    

}

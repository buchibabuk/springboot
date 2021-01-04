package nic.vahan5.reg.form.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import nic.java.util.CommonUtils;
import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.form.dobj.DocumentDetailsDobj;
import nic.vahan5.reg.form.dobj.DscDobj;
import nic.vahan5.reg.form.dobj.OwnerDetailsDobj;
import nic.vahan5.reg.form.dobj.Status_dobj;
import nic.vahan5.reg.form.dobj.TmConfigurationDMS;
import nic.vahan5.reg.form.dobj.TmConfigurationDobj;
import nic.vahan5.reg.impl.DocumentUploadImplementation;
import nic.vahan5.reg.server.ServerUtil;
import nic.vahan5.reg.server.ServerUtility;
import nic.vahan5.reg.server.TableConstants;
import nic.vahan5.reg.services.DmsDocCheckUtils;
//import nic.vahan5.reg.services.NonUploadedList;
//import nic.vahan5.reg.services.ResponseDocument;
import nic.vahan5.reg.services.SubCategoryMasterData;
//import nic.vahan5.reg.services.SubCategoryMasterDataList;
import nic.vahan5.reg.services.UploadedList;

/**
 *
 * @author Bhuvan
 */
public class DocumentUploadBeanModel {
	
	@Autowired
	ServerUtility serverUtility;
	@Autowired
	ServerUtil serverUtil;
	@Autowired
	DocumentUploadImplementation DocumentUploadImpl;
	
	

    private String applNo;
    private String dmsFileUploadUrl;
    private String stateCd;
    private int offCd;
    private TmConfigurationDobj tmconf;

   
    private String userCatg;
    private String successUplodedMsg;
    private String noteMsg;
    private int actionCd;
    private List<VTDocumentModel> docDescrList;
    private String dealerMobileNo;
    private TmConfigurationDMS confgDMS;
    private int purCd;
    private OwnerDetailsDobj ownerDetailsDobj;
    private long userCode;
    private String dmsURL;
    private String remarks;
    private String emailMessage;
    private boolean renderedMailButton;
    private String regnNo, applDt, purposeDescr;
    private boolean uploadOwnerAdminDoc;
    private int totalCountUploadDoc;
    private boolean renderverifyDocUpload;
    //komal work done
    private List<DocumentDetailsDobj> docDetailsList;
    private List<DocumentDetailsDobj> mandatoryList;
    private List<DocumentDetailsDobj> uploadedList;
    private boolean renderApplicationInputPanel;
    private boolean renderApplicatioDetailsAndCarouselPanel;
    private boolean showFileFlowBtn;
    private String displayVerifyBtnText;
    private DocumentDetailsDobj selectedDoc;
    private int index;
    private boolean showNextBtn;
    private boolean showPrevBtn;
    private String printImageNo;
    private String ownerDetails;
    private boolean showDigitalSignPanel;
    private boolean pendingWorkFlowCall;
    private boolean showHomeBtn;
    private String noteMessasge = " .png, .jpeg, .jpg, .pdf";
    // Digital Signature related
    private String certificatexml;
    private List<DscDobj> dscServiceCertificateList;
    private String registrationxml;
    private String registrationResponseXml;
    private String pdfSignXML;
    private String pdfSignResponseXML;
    private Map<String, Object> pdfSignxML;
    private boolean dscConnected;
    private boolean showDigitalSignLabel;
    private boolean renderApiBasedDMSDocPanel;
    private boolean renderUiBasedDMSDocPanel;
    // Created for REST
    private ArrayList<Status_dobj> applStatus = null;
    private OwnerDetailsDobj ownerDobj = null;
    private String docStatus = null;
    private int fileCount = 0;
    private String clientipAddress;
    private String empCode;
    private String seatCode;

    public String getClientipAddress() {
        return clientipAddress;
    }

    public void setClientipAddress(String clientipAddress) {
        this.clientipAddress = clientipAddress;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public DocumentUploadBeanModel() {
    }

  /* public DocumentUploadBeanModel(DocumentUploadBean documentUploadBean) {
        this.applNo = documentUploadBean.getApplNo();
        this.dmsFileUploadUrl = documentUploadBean.getDmsFileUploadUrl();
//        this.stateCd = documentUploadBean.getStateCd();
//        this.offCd = documentUploadBean.getOffCd();
        this.userCatg = documentUploadBean.getUserCatg();
        this.successUplodedMsg = documentUploadBean.getSuccessUplodedMsg();
        this.noteMsg = documentUploadBean.getNoteMsg();
        this.actionCd = documentUploadBean.getActionCd();
        this.docDescrList = documentUploadBean.getDocDescrList();
        this.dealerMobileNo = documentUploadBean.getDealerMobileNo();
//        this.confgDMS = documentUploadBean.getConfgDMS();
//        this.purCd = documentUploadBean.getPurCd();
//        this.ownerDetailsDobj = documentUploadBean.getOwnerDetailsDobj();
//        this.userCode = documentUploadBean.getUserCode();
        this.dmsURL = documentUploadBean.getDmsURL();
        this.remarks = documentUploadBean.getRemarks();
        this.emailMessage = documentUploadBean.getEmailMessage();
        this.renderedMailButton = documentUploadBean.isRenderedMailButton();
        this.regnNo = documentUploadBean.getRegnNo();
        // Changes @24Aug2020
        this.applDt = documentUploadBean.getApplDt();
        this.purposeDescr = documentUploadBean.getPurposeDescr();
        this.uploadOwnerAdminDoc = documentUploadBean.isUploadOwnerAdminDoc();
        this.totalCountUploadDoc = documentUploadBean.getTotalCountUploadDoc();
        this.renderverifyDocUpload = documentUploadBean.isRenderverifyDocUpload();
        this.docDetailsList = documentUploadBean.getDocDetailsList();
        this.mandatoryList = documentUploadBean.getMandatoryList();
        this.uploadedList = documentUploadBean.getUploadedList();
        this.renderApplicationInputPanel = documentUploadBean.isRenderApplicationInputPanel();
        this.renderApplicatioDetailsAndCarouselPanel = documentUploadBean.isRenderApplicatioDetailsAndCarouselPanel();
        this.showFileFlowBtn = documentUploadBean.isShowFileFlowBtn();
        this.displayVerifyBtnText = documentUploadBean.getDisplayVerifyBtnText();
        this.selectedDoc = documentUploadBean.getSelectedDoc();
        this.index = documentUploadBean.getIndex();
        this.showNextBtn = documentUploadBean.isShowNextBtn();
        this.showPrevBtn = documentUploadBean.isShowPrevBtn();
        this.printImageNo = documentUploadBean.getPrintImageNo();
        this.ownerDetails = documentUploadBean.getOwnerDetails();
        this.showDigitalSignPanel = documentUploadBean.isShowDigitalSignPanel();
        this.pendingWorkFlowCall = documentUploadBean.isPendingWorkFlowCall();
        this.showHomeBtn = documentUploadBean.isShowHomeBtn();
        this.certificatexml = documentUploadBean.getCertificatexml();
        this.dscServiceCertificateList = documentUploadBean.getDscServiceCertificateList();
        this.registrationxml = documentUploadBean.getRegistrationxml();
        this.registrationResponseXml = documentUploadBean.getRegistrationResponseXml();
        this.pdfSignXML = documentUploadBean.getPdfSignXML();
        this.pdfSignResponseXML = documentUploadBean.getPdfSignResponseXML();
        this.pdfSignxML = documentUploadBean.getPdfSignxML();
        this.dscConnected = documentUploadBean.isDscConnected();
        this.showDigitalSignLabel = documentUploadBean.isShowDigitalSignLabel();
        this.renderApiBasedDMSDocPanel = documentUploadBean.isRenderApiBasedDMSDocPanel();
        this.renderUiBasedDMSDocPanel = documentUploadBean.isRenderUiBasedDMSDocPanel();
    }*/
 public TmConfigurationDobj getTmconf() {
        return tmconf;
    }

    public void setTmconf(TmConfigurationDobj tmconf) {
        this.tmconf = tmconf;
    }
  /*  public DocumentUploadBean populateBeanFromModel(DocumentUploadBean documentUploadBean) {
        documentUploadBean.setApplNo(this.applNo);
        documentUploadBean.setDmsFileUploadUrl(this.dmsFileUploadUrl);
//          documentUploadBean.setStateCd(this.stateCd);
//          documentUploadBean.setOffCd(this.offCd);
        documentUploadBean.setUserCatg(this.userCatg);
        documentUploadBean.setSuccessUplodedMsg(this.successUplodedMsg);
        documentUploadBean.setNoteMsg(this.noteMsg);
        documentUploadBean.setActionCd(this.actionCd);
        documentUploadBean.setDocDescrList(this.docDescrList);
        documentUploadBean.setDealerMobileNo(this.dealerMobileNo);
//          documentUploadBean.setConfgDMS(this.confgDMS);
//          documentUploadBean.setPurCd(this.purCd);
//          documentUploadBean.setOwnerDetailsDobj(this.ownerDetailsDobj);
//          documentUploadBean.setUserCode(this.userCode);
        documentUploadBean.setDmsURL(this.dmsURL);
        documentUploadBean.setRemarks(this.remarks);
        documentUploadBean.setEmailMessage(this.emailMessage);
        documentUploadBean.setRenderedMailButton(this.renderedMailButton);
        documentUploadBean.setRegnNo(this.regnNo);
        // Changes @24Aug2020
        documentUploadBean.setApplDt(this.applDt);
        documentUploadBean.setPurposeDescr(this.purposeDescr);
        documentUploadBean.setUploadOwnerAdminDoc(this.uploadOwnerAdminDoc);
        documentUploadBean.setTotalCountUploadDoc(this.totalCountUploadDoc);
        documentUploadBean.setRenderverifyDocUpload(this.renderverifyDocUpload);
        documentUploadBean.setDocDetailsList(this.docDetailsList);
        documentUploadBean.setMandatoryList(this.mandatoryList);
        documentUploadBean.setUploadedList(this.uploadedList);
        documentUploadBean.setRenderApplicationInputPanel(this.renderApplicationInputPanel);
        documentUploadBean.setRenderApplicatioDetailsAndCarouselPanel(this.renderApplicatioDetailsAndCarouselPanel);
        documentUploadBean.setShowFileFlowBtn(this.showFileFlowBtn);
        documentUploadBean.setDisplayVerifyBtnText(this.displayVerifyBtnText);
        documentUploadBean.setSelectedDoc(this.selectedDoc);
        documentUploadBean.setIndex(this.index);
        documentUploadBean.setShowNextBtn(this.showNextBtn);
        documentUploadBean.setShowPrevBtn(this.showPrevBtn);
        documentUploadBean.setPrintImageNo(this.printImageNo);
        documentUploadBean.setOwnerDetails(this.ownerDetails);
        documentUploadBean.setShowDigitalSignPanel(this.showDigitalSignPanel);
        documentUploadBean.setPendingWorkFlowCall(this.pendingWorkFlowCall);
        documentUploadBean.setShowHomeBtn(this.showHomeBtn);
        documentUploadBean.setCertificatexml(this.certificatexml);
        documentUploadBean.setDscServiceCertificateList(this.dscServiceCertificateList);
        documentUploadBean.setRegistrationxml(this.registrationxml);
        documentUploadBean.setRegistrationResponseXml(this.registrationResponseXml);
        documentUploadBean.setPdfSignXML(this.pdfSignXML);
        documentUploadBean.setPdfSignResponseXML(this.pdfSignResponseXML);
        documentUploadBean.setPdfSignxML(this.pdfSignxML);
        documentUploadBean.setDscConnected(this.dscConnected);
        documentUploadBean.setShowDigitalSignLabel(this.showDigitalSignLabel);
        documentUploadBean.setRenderApiBasedDMSDocPanel(this.renderApiBasedDMSDocPanel);
        documentUploadBean.setRenderUiBasedDMSDocPanel(this.renderUiBasedDMSDocPanel);

        return documentUploadBean;
    }
*/
    /*public void callDocumentApiToGetUploadedDocList() throws VahanException {
        DmsDocCheckUtils docCheckUtils = new DmsDocCheckUtils();
        try {
            if (actionCd != TableConstants.TM_ROLE_UPLOAD_MODIFY_DOCUMENT && actionCd != TableConstants.TM_ROLE_DEALER_DOCUMENT_UPLOAD && actionCd != TableConstants.TM_ROLE_DEALER_DOCUMENT_MODIFY) {
                ArrayList<Status_dobj> applStatus = serverUtility.applicationStatusByApplNo(getApplNo(), getStateCd());
                if (applStatus != null && !applStatus.isEmpty()) {
                    if (applStatus.size() == 2) {
                        setPurCd(TableConstants.VM_TRANSACTION_MAST_DEALER_NEW_VEHICLE);
                    } else {
                        setPurCd(applStatus.get(0).getPur_cd());
                    }
                }
            }
            String offName = serverUtility.getOfficeName(getOffCd(), getStateCd());
            if (offName != null) {
                offName = offName.replaceAll("\\s", "");
            }
            ResponseDocument docListResponse = docCheckUtils.callDocumentApiToGetDocListToBeUpload(applNo, stateCd, regnNo, offName, purCd, false);

            if (docListResponse != null && docListResponse.getUploadedList() != null && !docListResponse.getUploadedList().isEmpty()) {
                for (UploadedList upload : docListResponse.getUploadedList()) {
                    DocumentDetailsDobj docDetails = this.setCommonDataForUploadedList(upload);
                    if ((actionCd == TableConstants.TM_NEW_RC_VERIFICATION || actionCd == TableConstants.TM_NEW_RC_APPROVAL || actionCd == TableConstants.TM_TMP_RC_VERIFICATION || actionCd == TableConstants.TM_TMP_RC_APPROVAL || actionCd == TableConstants.TM_ROLE_DEALER_APPROVAL || actionCd == TableConstants.TM_ROLE_DEALER_TEMP_APPROVAL || actionCd == TableConstants.TM_NEW_RC_FITNESS_INSPECTION || actionCd == TableConstants.TM_NEW_RC_FITNESS_INSPECTION_APPROVAL)) {
                        docDetails.setRenderModifyButton(false);
                    } else {
                        docDetails.setRenderModifyButton(true);
                    }
                    uploadedList.add(docDetails);
                }
            }
        } catch (VahanException vex) {
            throw vex;
        } catch (Exception e) {
         //   getLOGGER().error(e.toString() + " " + e.getStackTrace()[0]);
        	System.out.print(e.toString() + " " + e.getStackTrace()[0]);
            throw new VahanException(TableConstants.SomthingWentWrong);
        }
    }
*/
    public DocumentDetailsDobj setCommonDataForUploadedList(UploadedList upload) throws VahanException, Exception {
        DocumentDetailsDobj docDetails = new DocumentDetailsDobj();
        List<SubCategoryMasterData> subCategoryMasterDataList = new ArrayList<>();
        docDetails.setCatId(upload.getCatId());
        docDetails.setCatName(upload.getCatName());
        docDetails.setMandatory(upload.getMandatory());
        docDetails.setSubCatg(upload.getSubcategoryMasterData().getSub_cat_id());
        subCategoryMasterDataList.add(upload.getSubcategoryMasterData());
        docDetails.setSubcategoryMasterDataList(subCategoryMasterDataList);
        docDetails.setRenderUploadButton(false);
        docDetails.setDisableSubCatg(true);
        docDetails.setStateCd(stateCd);
        docDetails.setRegnNo(regnNo);
        docDetails.setApplNo(applNo);
        docDetails.setObjectId(upload.getObjectId());
        String dmsViewUrl = this.callDocumentApiForViewDocument(upload.getObjectId());
        dmsViewUrl = dmsViewUrl + "?" + Math.random();
        docDetails.setShowImage(dmsViewUrl);
        docDetails.setDocVerified(false);
        docDetails.setUploadedFileName(upload.getDocUrl());
        String extension = this.getExtension(docDetails.getUploadedFileName());
        if ((TableConstants.PDF_EXTENSION).contains(extension)) {
            docDetails.setPdfExtension(true);
            docDetails.setImageExtension(false);
        } else if ((TableConstants.IMAGE_EXTENSION).contains(extension)) {
            docDetails.setPdfExtension(false);
            docDetails.setImageExtension(true);
        }
        return docDetails;
    }

    public String callDocumentApiForViewDocument(String objectId) throws VahanException {
        DmsDocCheckUtils docCheckUtils = new DmsDocCheckUtils();
        String viewResponse = null;
        try {
            viewResponse = docCheckUtils.callDocumentApiForViewDocument(objectId);
            if (viewResponse == null) {
                throw new VahanException("Unable to get response from DMS.");
            }
        } catch (VahanException vex) {
            throw vex;
        } catch (Exception e) {
          //  getLOGGER().error(e.toString() + " " + e.getStackTrace()[0]);
      System.out.println(e.toString() + " " + e.getStackTrace()[0]);
            throw new VahanException(TableConstants.SomthingWentWrong);
        }
        return viewResponse;
    }

    public String getExtension(String file) throws Exception {
        String[] fileName = file.split("[.]");
        String extension = fileName[fileName.length - 1];
        return extension;
    }

  /*  public void callDocumentApiToGetDocListToBeUpload() throws VahanException {
        try {
            this.callDocumentApiToGetNonUploadedListAndUploadedList(false);
        } catch (VahanException vex) {
            throw vex;
        } catch (Exception e) {
          //  getLOGGER().error(e.toString() + " " + e.getStackTrace()[0]);
           System.out.println(e.toString() + " " + e.getStackTrace()[0]);
            throw new VahanException(TableConstants.SomthingWentWrong);
        }
    }*/

   /* public void callDocumentApiToGetNonUploadedListAndUploadedList(boolean docUploadAtRTOAtVerification) throws VahanException, Exception {
        DmsDocCheckUtils docCheckUtils = new DmsDocCheckUtils();
        String offName = serverUtility.getOfficeName(getOffCd(), getStateCd());
        if (offName != null) {
            offName = offName.replaceAll("\\s", "");
        }
        ResponseDocument docListResponse = docCheckUtils.callDocumentApiToGetDocListToBeUpload(applNo, stateCd, regnNo, offName, purCd, docUploadAtRTOAtVerification);
        if (docListResponse != null && docListResponse.getNonUploadedList() != null && !docListResponse.getNonUploadedList().isEmpty()) {
            for (NonUploadedList nonUpload : docListResponse.getNonUploadedList()) {
                DocumentDetailsDobj docDetails = new DocumentDetailsDobj();
                docDetails.setCatId(nonUpload.getCatId());
                docDetails.setCatName(nonUpload.getCatName());
                docDetails.setMandatory(nonUpload.getMandatory());
                List<SubCategoryMasterData> subCategoryMasterDataList = new ArrayList<>();
                for (SubCategoryMasterDataList scList : nonUpload.getSubcategoryMasterDataList()) {
                    SubCategoryMasterData sc = new SubCategoryMasterData();
                    sc.setSub_cat_id(String.valueOf(scList.getSub_cat_id()));
                    sc.setSub_cat_name(scList.getSub_cat_name());
                    subCategoryMasterDataList.add(sc);
                }
                docDetails.setSubcategoryMasterDataList(subCategoryMasterDataList);
                docDetails.setRenderUploadButton(true);
                docDetails.setRenderModifyButton(false);
                docDetails.setDisableSubCatg(false);
                docDetails.setStateCd(stateCd);
                docDetails.setRegnNo(regnNo);
                docDetails.setApplNo(applNo);
                if (nonUpload.getMandatory() != null && nonUpload.getMandatory().equals("Y")) {
                    mandatoryList.add(docDetails);
                }
                getDocDetailsList().add(docDetails);
            }
        }
        if (docListResponse != null && docListResponse.getUploadedList() != null && !docListResponse.getUploadedList().isEmpty()) {
            for (UploadedList upload : docListResponse.getUploadedList()) {
                DocumentDetailsDobj docDetails = this.setCommonDataForUploadedList(upload);
                if (actionCd == TableConstants.TM_ADMIN_OWNER_DATA_CHANGE_APPROVAL) {
                    docDetails.setRenderModifyButton(false);
                } else {
                    docDetails.setRenderModifyButton(true);
                }
                if (upload.getMandatory() != null && upload.getMandatory().equals("Y")) {
                    mandatoryList.add(docDetails);
                }
                uploadedList.add(docDetails);
                getDocDetailsList().add(docDetails);
            }
        }

        if (mandatoryList.size() <= 0) {
            throw new VahanException("Please contact to system Adminstrator to configure the DMS utility.");
        }
    }
*/
    public void setDocumentObjectToDisplay() {
        index = 0;
        selectedDoc = docDetailsList.get(0);
        setShowNextBtn(true);
        setShowPrevBtn(true);
        printImageNo = String.valueOf(index + 1) + " of " + String.valueOf(docDetailsList.size());
    }

    public void setOwnerDetailsValue() throws VahanException {
        DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        OwnerDetailsDobj ownerDobj = DocumentUploadImpl.getOwnerDetails(applNo, stateCd, regnNo, purCd);
        if (ownerDobj != null) {
            String ownerDetailsVal = "<b><u>Owner Details</u></b><br/>";
            if (!CommonUtils.isNullOrBlank(ownerDobj.getOwner_name())) {
                ownerDetailsVal = ownerDetailsVal + ownerDobj.getOwner_name() + "<br/> ";
            }
            if (!CommonUtils.isNullOrBlank(ownerDobj.getF_name())) {
                ownerDetailsVal = ownerDetailsVal + "S/W/D of " + ownerDobj.getF_name() + "<br/>";
            }
            if (!CommonUtils.isNullOrBlank(ownerDobj.getC_add1())) {
                ownerDetailsVal = ownerDetailsVal + "R/o " + ownerDobj.getC_add1();
            }

            if (!CommonUtils.isNullOrBlank(ownerDobj.getC_add2())) {
                ownerDetailsVal = ownerDetailsVal + "<br/>" + ownerDobj.getC_add2();
            }
            if (!CommonUtils.isNullOrBlank(ownerDobj.getC_add3())) {
                ownerDetailsVal = ownerDetailsVal + "<br/>" + ownerDobj.getC_add3();
            }

            if (!CommonUtils.isNullOrBlank(ownerDobj.getC_district_name())) {
                ownerDetailsVal = ownerDetailsVal + "<br/>" + ownerDobj.getC_district_name();
            }

            if (!CommonUtils.isNullOrBlank(ownerDobj.getC_state_name())) {
                ownerDetailsVal = ownerDetailsVal + "<br/>" + ownerDobj.getC_state_name() + "-" + ownerDobj.getC_pincode() + "<br/><br/>";
            }

            if (ownerDobj.getInsDobj() != null) {
                ownerDetailsVal = ownerDetailsVal + "<b><u>Insurance Details</u></b><br/>";
                if (!CommonUtils.isNullOrBlank(ownerDobj.getInsDobj().getInsCompName())) {
                    ownerDetailsVal = ownerDetailsVal + "From " + ownerDobj.getInsDobj().getInsCompName() + "<br/> ";
                }
                if (!CommonUtils.isNullOrBlank(ownerDobj.getInsDobj().getPolicy_no())) {
                    ownerDetailsVal = ownerDetailsVal + " vide policy no " + ownerDobj.getInsDobj().getPolicy_no() + "<br/> ";
                }
                if (ownerDobj.getInsDobj().getIns_from() != null) {
                    ownerDetailsVal = ownerDetailsVal + "valid from " + format.format(ownerDobj.getInsDobj().getIns_from());
                }
                if (ownerDobj.getInsDobj().getIns_upto() != null) {
                    ownerDetailsVal = ownerDetailsVal + " to " + format.format(ownerDobj.getInsDobj().getIns_upto()) + "<br/><br/>";
                }

            }

            if (ownerDobj.getHpaDobj() != null) {
                ownerDetailsVal = ownerDetailsVal + "<b><u>Hypothecation Details</u></b><br/>" + ownerDobj.getHpaDobj().getHp_type_descr() + "<br/> " + " from " + ownerDobj.getHpaDobj().getFncr_name() + "<br/><br/>";
            }

            if (ownerDobj.getFitnessDobj() != null) {
                ownerDetailsVal = ownerDetailsVal + "<b><u>PUCC Details</u></b><br/>";
                if (!CommonUtils.isNullOrBlank(ownerDobj.getFitnessDobj().getPucc_no())) {
                    ownerDetailsVal = ownerDetailsVal + ownerDobj.getFitnessDobj().getPucc_no() + "<br/> ";
                }
                if (ownerDobj.getFitnessDobj().getPucc_val() != null) {
                    ownerDetailsVal = ownerDetailsVal + " valid upto " + format.format(ownerDobj.getFitnessDobj().getPucc_val()) + "<br/><br/>";
                }
            }
            this.setOwnerDetails(ownerDetailsVal);
        }
    }

    public ArrayList<Status_dobj> getApplStatus() {
        return applStatus;
    }

    public void setApplStatus(ArrayList<Status_dobj> applStatus) {
        this.applStatus = applStatus;
    }

    public boolean isRenderedMailButton() {
        return renderedMailButton;
    }

    public void setRenderedMailButton(boolean renderedMailButton) {
        this.renderedMailButton = renderedMailButton;
    }

    public String getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public String getApplNo() {
        return applNo;
    }

    public void setApplNo(String applNo) {
        this.applNo = applNo;
    }

    @Override
    public String toString() {
        return "DocumentUpoadBeanModel{" + "applNo=" + applNo + ", dmsFileUploadUrl=" + dmsFileUploadUrl + ", stateCd=" + stateCd + ", offCd=" + offCd + ", userCatg=" + userCatg + ", successUplodedMsg=" + successUplodedMsg + ", noteMsg=" + noteMsg + ", actionCd=" + actionCd + ", docDescrList=" + docDescrList + ", dealerMobileNo=" + dealerMobileNo + ", confgDMS=" + confgDMS + ", purCd=" + purCd + ", ownerDetailsDobj=" + ownerDetailsDobj + ", userCode=" + userCode + ", dmsURL=" + dmsURL + ", remarks=" + remarks + '}';
    }

    public String getDmsFileUploadUrl() {
        return dmsFileUploadUrl;
    }

    public void setDmsFileUploadUrl(String dmsFileUploadUrl) {
        this.dmsFileUploadUrl = dmsFileUploadUrl;
    }

    public String getStateCd() {
        return stateCd;
    }

    public void setStateCd(String stateCd) {
        this.stateCd = stateCd;
    }

    public int getOffCd() {
        return offCd;
    }

    public void setOffCd(int offCd) {
        this.offCd = offCd;
    }

    public String getUserCatg() {
        return userCatg;
    }

    public void setUserCatg(String userCatg) {
        this.userCatg = userCatg;
    }

    public String getSuccessUplodedMsg() {
        return successUplodedMsg;
    }

    public void setSuccessUplodedMsg(String successUplodedMsg) {
        this.successUplodedMsg = successUplodedMsg;
    }

    public String getNoteMsg() {
        return noteMsg;
    }

    public void setNoteMsg(String noteMsg) {
        this.noteMsg = noteMsg;
    }

    public int getActionCd() {
        return actionCd;
    }

    public void setActionCd(int actionCd) {
        this.actionCd = actionCd;
    }

    public List<VTDocumentModel> getDocDescrList() {
        return docDescrList;
    }

    public void setDocDescrList(List<VTDocumentModel> docDescrList) {
        this.docDescrList = docDescrList;
    }

    public String getDealerMobileNo() {
        return dealerMobileNo;
    }

    public void setDealerMobileNo(String dealerMobileNo) {
        this.dealerMobileNo = dealerMobileNo;
    }

    public TmConfigurationDMS getConfgDMS() {
        return confgDMS;
    }

    public void setConfgDMS(TmConfigurationDMS confgDMS) {
        this.confgDMS = confgDMS;
    }

    public int getPurCd() {
        return purCd;
    }

    public void setPurCd(int purCd) {
        this.purCd = purCd;
    }

    public OwnerDetailsDobj getOwnerDetailsDobj() {
        return ownerDetailsDobj;
    }

    public void setOwnerDetailsDobj(OwnerDetailsDobj ownerDetailsDobj) {
        this.ownerDetailsDobj = ownerDetailsDobj;
    }

    public long getUserCode() {
        return userCode;
    }

    public void setUserCode(long userCode) {
        this.userCode = userCode;
    }

    public String getDmsURL() {
        return dmsURL;
    }

    public void setDmsURL(String dmsURL) {
        this.dmsURL = dmsURL;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRegnNo() {
        return regnNo;
    }

    public void setRegnNo(String regnNo) {
        this.regnNo = regnNo;
    }

    public String getApplDt() {
        return applDt;
    }

    public void setApplDt(String applDt) {
        this.applDt = applDt;
    }

    public String getPurposeDescr() {
        return purposeDescr;
    }

    public void setPurposeDescr(String purposeDescr) {
        this.purposeDescr = purposeDescr;
    }

    public boolean isUploadOwnerAdminDoc() {
        return uploadOwnerAdminDoc;
    }

    public void setUploadOwnerAdminDoc(boolean uploadOwnerAdminDoc) {
        this.uploadOwnerAdminDoc = uploadOwnerAdminDoc;
    }

    public int getTotalCountUploadDoc() {
        return totalCountUploadDoc;
    }

    public void setTotalCountUploadDoc(int totalCountUploadDoc) {
        this.totalCountUploadDoc = totalCountUploadDoc;
    }

    public boolean isRenderverifyDocUpload() {
        return renderverifyDocUpload;
    }

    public void setRenderverifyDocUpload(boolean renderverifyDocUpload) {
        this.renderverifyDocUpload = renderverifyDocUpload;
    }

    public List<DocumentDetailsDobj> getDocDetailsList() {
        return docDetailsList;
    }

    public void setDocDetailsList(List<DocumentDetailsDobj> docDetailsList) {
        this.docDetailsList = docDetailsList;
    }

    public List<DocumentDetailsDobj> getMandatoryList() {
        return mandatoryList;
    }

    public void setMandatoryList(List<DocumentDetailsDobj> mandatoryList) {
        this.mandatoryList = mandatoryList;
    }

    public List<DocumentDetailsDobj> getUploadedList() {
        return uploadedList;
    }

    public void setUploadedList(List<DocumentDetailsDobj> uploadedList) {
        this.uploadedList = uploadedList;
    }

    public boolean isRenderApplicationInputPanel() {
        return renderApplicationInputPanel;
    }

    public void setRenderApplicationInputPanel(boolean renderApplicationInputPanel) {
        this.renderApplicationInputPanel = renderApplicationInputPanel;
    }

    public boolean isRenderApplicatioDetailsAndCarouselPanel() {
        return renderApplicatioDetailsAndCarouselPanel;
    }

    public void setRenderApplicatioDetailsAndCarouselPanel(boolean renderApplicatioDetailsAndCarouselPanel) {
        this.renderApplicatioDetailsAndCarouselPanel = renderApplicatioDetailsAndCarouselPanel;
    }

    public boolean isShowFileFlowBtn() {
        return showFileFlowBtn;
    }

    public void setShowFileFlowBtn(boolean showFileFlowBtn) {
        this.showFileFlowBtn = showFileFlowBtn;
    }

    public String getDisplayVerifyBtnText() {
        return displayVerifyBtnText;
    }

    public void setDisplayVerifyBtnText(String displayVerifyBtnText) {
        this.displayVerifyBtnText = displayVerifyBtnText;
    }

    public DocumentDetailsDobj getSelectedDoc() {
        return selectedDoc;
    }

    public void setSelectedDoc(DocumentDetailsDobj selectedDoc) {
        this.selectedDoc = selectedDoc;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isShowNextBtn() {
        return showNextBtn;
    }

    public void setShowNextBtn(boolean showNextBtn) {
        this.showNextBtn = showNextBtn;
    }

    public boolean isShowPrevBtn() {
        return showPrevBtn;
    }

    public void setShowPrevBtn(boolean showPrevBtn) {
        this.showPrevBtn = showPrevBtn;
    }

    public String getPrintImageNo() {
        return printImageNo;
    }

    public void setPrintImageNo(String printImageNo) {
        this.printImageNo = printImageNo;
    }

    public String getOwnerDetails() {
        return ownerDetails;
    }

    public void setOwnerDetails(String ownerDetails) {
        this.ownerDetails = ownerDetails;
    }

    public boolean isShowDigitalSignPanel() {
        return showDigitalSignPanel;
    }

    public void setShowDigitalSignPanel(boolean showDigitalSignPanel) {
        this.showDigitalSignPanel = showDigitalSignPanel;
    }

    public boolean isPendingWorkFlowCall() {
        return pendingWorkFlowCall;
    }

    public void setPendingWorkFlowCall(boolean pendingWorkFlowCall) {
        this.pendingWorkFlowCall = pendingWorkFlowCall;
    }

    public boolean isShowHomeBtn() {
        return showHomeBtn;
    }

    public void setShowHomeBtn(boolean showHomeBtn) {
        this.showHomeBtn = showHomeBtn;
    }

    public String getNoteMessasge() {
        return noteMessasge;
    }

    public void setNoteMessasge(String noteMessasge) {
        this.noteMessasge = noteMessasge;
    }

    public String getCertificatexml() {
        return certificatexml;
    }

    public void setCertificatexml(String certificatexml) {
        this.certificatexml = certificatexml;
    }

    public List<DscDobj> getDscServiceCertificateList() {
        return dscServiceCertificateList;
    }

    public void setDscServiceCertificateList(List<DscDobj> dscServiceCertificateList) {
        this.dscServiceCertificateList = dscServiceCertificateList;
    }

    public String getRegistrationxml() {
        return registrationxml;
    }

    public void setRegistrationxml(String registrationxml) {
        this.registrationxml = registrationxml;
    }

    public String getRegistrationResponseXml() {
        return registrationResponseXml;
    }

    public void setRegistrationResponseXml(String registrationResponseXml) {
        this.registrationResponseXml = registrationResponseXml;
    }

    public String getPdfSignXML() {
        return pdfSignXML;
    }

    public void setPdfSignXML(String pdfSignXML) {
        this.pdfSignXML = pdfSignXML;
    }

    public String getPdfSignResponseXML() {
        return pdfSignResponseXML;
    }

    public void setPdfSignResponseXML(String pdfSignResponseXML) {
        this.pdfSignResponseXML = pdfSignResponseXML;
    }

    public Map<String, Object> getPdfSignxML() {
        return pdfSignxML;
    }

    public void setPdfSignxML(Map<String, Object> pdfSignxML) {
        this.pdfSignxML = pdfSignxML;
    }

    public boolean isDscConnected() {
        return dscConnected;
    }

    public void setDscConnected(boolean dscConnected) {
        this.dscConnected = dscConnected;
    }

    public boolean isShowDigitalSignLabel() {
        return showDigitalSignLabel;
    }

    public void setShowDigitalSignLabel(boolean showDigitalSignLabel) {
        this.showDigitalSignLabel = showDigitalSignLabel;
    }

    public boolean isRenderApiBasedDMSDocPanel() {
        return renderApiBasedDMSDocPanel;
    }

    public void setRenderApiBasedDMSDocPanel(boolean renderApiBasedDMSDocPanel) {
        this.renderApiBasedDMSDocPanel = renderApiBasedDMSDocPanel;
    }

    public boolean isRenderUiBasedDMSDocPanel() {
        return renderUiBasedDMSDocPanel;
    }

    public void setRenderUiBasedDMSDocPanel(boolean renderUiBasedDMSDocPanel) {
        this.renderUiBasedDMSDocPanel = renderUiBasedDMSDocPanel;
    }

    public OwnerDetailsDobj getOwnerDobj() {
        return ownerDobj;
    }

    public void setOwnerDobj(OwnerDetailsDobj ownerDobj) {
        this.ownerDobj = ownerDobj;
    }
}

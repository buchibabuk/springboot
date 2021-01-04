/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.vahan5.reg.rest.model;

import java.util.List;

import nic.vahan5.reg.form.dobj.InsDobj;
import nic.vahan5.reg.form.dobj.Owner_dobj;
import nic.vahan5.reg.form.dobj.Status_dobj;
import nic.vahan5.reg.form.dobj.TmConfigurationDobj;
import nic.vahan5.reg.form.dobj.Trailer_dobj;
import nic.vahan5.reg.form.model.AxleBeanModel;
import nic.vahan5.reg.form.model.ComparisonBeanModel;
import nic.vahan5.reg.form.model.ExArmyBeanModel;
import nic.vahan5.reg.form.model.HpaBeanModel;
import nic.vahan5.reg.form.model.ImportedVehicleBeanModel;
import nic.vahan5.reg.form.model.InsBeanModel;
import nic.vahan5.reg.form.model.OwnerBeanModel;
import nic.vahan5.reg.form.model.RetroFittingDetailsBeanModel;
import nic.vahan5.reg.form.model.SessionVariablesModel;
import nic.vahan5.reg.form.model.WorkBenchModel;




/**
 *
 * @author Kartikey Singh
 */
public class WrapperModel {
	//NR
   // ApplicationDisposeBeanModel applicationDisposeBeanModel;
    //List<EpayDobj> mandatoryFeeList;
	  private InsBeanModel ins_bean;
    ComparisonBeanModel comparisonBean;
    SessionVariablesModel sessionVariables;
   WorkBenchModel workBench;
    Owner_dobj ownerDobj;
    InsDobj insDobj;
    TmConfigurationDobj tmConfigDobj;
    HpaBeanModel hpaBeanModel;
    ExArmyBeanModel exArmyBean;
    ImportedVehicleBeanModel importedVehicleBean;
   

	AxleBeanModel axleBean;
   RetroFittingDetailsBeanModel cngDetailsBean;
    Trailer_dobj trailerDobj;
    OwnerBeanModel ownerBean;
    Status_dobj statusDobj;
    ContextMessageModel contextMessageModel;
  //NR
   // NewVehicleFeeBeanModel newVehicleFeeBeanModel;
//    DocumentUploadBeanModel documentUploadBeanModel;
  //NR
//    VahanTaxParameters vahanTaxParameters;
//    VehicleParameters vehicleParameters;
//    PassengerPermitDetailDobj passengerPermitDetailDobj;

   
    //Created by Sai    
 //   private InsBeanModel ins_bean;
 //   private List<OwnerDetailsDobj> tempRegnDetailsList = null;
  //NR
   // private TrailerBeanModel trailerBeanModel;
  //NR
   // private FitnessBeanModel fitnessBeanModel;
  //NR
    //private EpayBean epayBean;
    
  //  private String hypothecationStatus;
  //  OwnerDetailsDobj ownerdetailsdobj;
  //NR 5
   // private PaymentGatewayModel gatewayModel;
    //private CashReceiptReportModel cashReceiptReportModel;
    //private OwnerDisclaimerreportModel ownerDisclaimerreportModel;
    //private FeeCompareModel feeCompareModel;
    //private EReceiptModel eModel;

    /*
    public PaymentGatewayModel getGatewayModel() {
        return gatewayModel;
    }

    public void setGatewayModel(PaymentGatewayModel gatewayModel) {
        this.gatewayModel = gatewayModel;
    }
    
    public CashReceiptReportModel getCashReceiptReportModel() {
        return cashReceiptReportModel;
    }

    public void setCashReceiptReportModel(CashReceiptReportModel cashReceiptReportModel) {
        this.cashReceiptReportModel = cashReceiptReportModel;
    }

    public OwnerDisclaimerreportModel getOwnerDisclaimerreportModel() {
        return ownerDisclaimerreportModel;
    }

    public void setOwnerDisclaimerreportModel(OwnerDisclaimerreportModel ownerDisclaimerreportModel) {
        this.ownerDisclaimerreportModel = ownerDisclaimerreportModel;
    }

    public FeeCompareModel getFeeCompareModel() {
        return feeCompareModel;
    }

    public void setFeeCompareModel(FeeCompareModel feeCompareModel) {
        this.feeCompareModel = feeCompareModel;
    }

    public EReceiptModel geteModel() {
        return eModel;
    }

    public void seteModel(EReceiptModel eModel) {
        this.eModel = eModel;
    }
    
    
    
    public OwnerDetailsDobj getOwnerdetailsdobj() {
        return ownerdetailsdobj;
    }

    public void setOwnerdetailsdobj(OwnerDetailsDobj ownerdetailsdobj) {
        this.ownerdetailsdobj = ownerdetailsdobj;
    }
    
    public ApplicationDisposeBeanModel getApplicationDisposeBeanModel() {
        return applicationDisposeBeanModel;
    }

    public void setApplicationDisposeBeanModel(ApplicationDisposeBeanModel applicationDisposeBeanModel) {
        this.applicationDisposeBeanModel = applicationDisposeBeanModel;
    }
    
    
    
    
     public PassengerPermitDetailDobj getPassengerPermitDetailDobj() {
        return passengerPermitDetailDobj;
    }

    public void setPassengerPermitDetailDobj(PassengerPermitDetailDobj passengerPermitDetailDobj) {
        this.passengerPermitDetailDobj = passengerPermitDetailDobj;
    }

    public List<EpayDobj> getMandatoryFeeList() {
        return mandatoryFeeList;
    }

    public void setMandatoryFeeList(List<EpayDobj> mandatoryFeeList) {
        this.mandatoryFeeList = mandatoryFeeList;
    }

    public String getHypothecationStatus() {
        return hypothecationStatus;
    }

    public void setHypothecationStatus(String hypothecationStatus) {
        this.hypothecationStatus = hypothecationStatus;
    }
*/
  /*  public EpayBean getEpayBean() {
        return epayBean;
    }

    public void setEpayBean(EpayBean epayBean) {
        this.epayBean = epayBean;
    }

    public TrailerBeanModel getTrailerBeanModel() {
        return trailerBeanModel;
    }

    public void setTrailerBeanModel(TrailerBeanModel trailerBeanModel) {
        this.trailerBeanModel = trailerBeanModel;
    }

    public FitnessBeanModel getFitnessBeanModel() {
        return fitnessBeanModel;
    }

    public void setFitnessBeanModel(FitnessBeanModel fitnessBeanModel) {
        this.fitnessBeanModel = fitnessBeanModel;
    }

    public NewVehicleFeeBeanModel getNewVehicleFeeBeanModel() {
        return newVehicleFeeBeanModel;
    }

    public void setNewVehicleFeeBeanModel(NewVehicleFeeBeanModel newVehicleFeeBeanModel) {
        this.newVehicleFeeBeanModel = newVehicleFeeBeanModel;
    }

    public WrapperModel(ComparisonBeanModel comparisonBean, SessionVariablesModel sessionVariables, WorkBenchModel workBench, Owner_dobj ownerDobj, InsDobj insDobj, TmConfigurationDobj tmConfigDobj) {
        this.comparisonBean = comparisonBean;
        this.sessionVariables = sessionVariables;
        this.workBench = workBench;
        this.ownerDobj = ownerDobj;
        this.insDobj = insDobj;
        this.tmConfigDobj = tmConfigDobj;
    }
*/
    public WrapperModel() {
    }

   

    public ComparisonBeanModel getComparisonBean() {
        return comparisonBean;
    }

    public void setComparisonBean(ComparisonBeanModel comparisonBean) {
        this.comparisonBean = comparisonBean;
    }
    
    public ContextMessageModel getContextMessageModel() {
        return contextMessageModel;
    }

    public void setContextMessageModel(ContextMessageModel contextMessageModel) {
        this.contextMessageModel = contextMessageModel;
    }

    public SessionVariablesModel getSessionVariables() {
        return sessionVariables;
    }

    public void setSessionVariables(SessionVariablesModel sessionVariables) {
        this.sessionVariables = sessionVariables;
    }

    public Owner_dobj getOwnerDobj() {
        return ownerDobj;
    }

    public void setOwnerDobj(Owner_dobj ownerDobj) {
        this.ownerDobj = ownerDobj;
    }

    public WorkBenchModel getWorkBench() {
        return workBench;
    }

    public void setWorkBench(WorkBenchModel workBench) {
        this.workBench = workBench;
    }
    
/*
    public InsDobj getInsDobj() {
        return insDobj;
    }

    public void setInsDobj(InsDobj insDobj) {
        this.insDobj = insDobj;
    }
*/
    public TmConfigurationDobj getTmConfigDobj() {
        return tmConfigDobj;
    }

    public void setTmConfigDobj(TmConfigurationDobj tmConfigDobj) {
        this.tmConfigDobj = tmConfigDobj;
    }

/*    public Status_dobj getStatusDobj() {
        return statusDobj;
    }

    public void setStatusDobj(Status_dobj statusDobj) {
        this.statusDobj = statusDobj;
    }
*/
    public OwnerBeanModel getOwnerBean() {
        return ownerBean;
    }

    public void setOwnerBean(OwnerBeanModel ownerBean) {
        this.ownerBean = ownerBean;
    }

    public HpaBeanModel getHpaBeanModel() {
        return hpaBeanModel;
    }

    public void setHpaBeanModel(HpaBeanModel hpaBeanModel) {
        this.hpaBeanModel = hpaBeanModel;
    }

    public ExArmyBeanModel getExArmyBean() {
        return exArmyBean;
    }

    public void setExArmyBean(ExArmyBeanModel exArmyBean) {
        this.exArmyBean = exArmyBean;
    }

    public ImportedVehicleBeanModel getImportedVehicleBean() {
        return importedVehicleBean;
    }

    public void setImportedVehicleBean(ImportedVehicleBeanModel importedVehicleBean) {
        this.importedVehicleBean = importedVehicleBean;
    }

    public AxleBeanModel getAxleBean() {
        return axleBean;
    }

    public void setAxleBean(AxleBeanModel axleBean) {
        this.axleBean = axleBean;
    }

    public RetroFittingDetailsBeanModel getCngDetailsBean() {
        return cngDetailsBean;
    }

    public void setCngDetailsBean(RetroFittingDetailsBeanModel cngDetailsBean) {
        this.cngDetailsBean = cngDetailsBean;
    }

    public Trailer_dobj getTrailerDobj() {
        return trailerDobj;
    }

    public void setTrailerDobj(Trailer_dobj trailerDobj) {
        this.trailerDobj = trailerDobj;
    }
/*
    public ContextMessageModel getContextMessageModel() {
        return contextMessageModel;
    }

    public void setContextMessageModel(ContextMessageModel contextMessageModel) {
        this.contextMessageModel = contextMessageModel;
    }
*/
    public InsBeanModel getIns_bean() {
        return ins_bean;
    }

    public void setIns_bean(InsBeanModel ins_bean) {
        this.ins_bean = ins_bean;
    }
    public InsDobj getInsDobj() {
        return insDobj;
    }

    public void setInsDobj(InsDobj insDobj) {
        this.insDobj = insDobj;
    }
/*
    public List<OwnerDetailsDobj> getTempRegnDetailsList() {
        return tempRegnDetailsList;
    }

    public void setTempRegnDetailsList(List<OwnerDetailsDobj> tempRegnDetailsList) {
        this.tempRegnDetailsList = tempRegnDetailsList;
    }

    public DocumentUploadBeanModel getDocumentUploadBeanModel() {
        return documentUploadBeanModel;
    }

    public void setDocumentUploadBeanModel(DocumentUploadBeanModel documentUploadBeanModel) {
        this.documentUploadBeanModel = documentUploadBeanModel;
    }

    public VahanTaxParameters getVahanTaxParameters() {
        return vahanTaxParameters;
    }

    public void setVahanTaxParameters(VahanTaxParameters vahanTaxParameters) {
        this.vahanTaxParameters = vahanTaxParameters;
    }

    public VehicleParameters getVehicleParameters() {
        return vehicleParameters;
    }

    public void setVehicleParameters(VehicleParameters vehicleParameters) {
        this.vehicleParameters = vehicleParameters;
    }
*/
    public Status_dobj getStatusDobj() {
        return statusDobj;
    }

    public void setStatusDobj(Status_dobj statusDobj) {
        this.statusDobj = statusDobj;
    }



	@Override
	public String toString() {
		return "WrapperModel [ins_bean=" + ins_bean + ", comparisonBean=" + comparisonBean + ", sessionVariables="
				+ sessionVariables + ", workBench=" + workBench + ", ownerDobj=" + ownerDobj + ", insDobj=" + insDobj
				+ ", tmConfigDobj=" + tmConfigDobj + ", hpaBeanModel=" + hpaBeanModel + ", exArmyBean=" + exArmyBean
				+ ", importedVehicleBean=" + importedVehicleBean + ", axleBean=" + axleBean + ", cngDetailsBean="
				+ cngDetailsBean + ", trailerDobj=" + trailerDobj + ", ownerBean=" + ownerBean + ", statusDobj="
				+ statusDobj + ", contextMessageModel=" + contextMessageModel + "]";
	}
    
    
}

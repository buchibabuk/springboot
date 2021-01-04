package nic.vahan5.reg.rest.model;

import java.util.List;
import java.util.Map;

/**
 *
 * @author bhuvan
 */
public class ApplicationDisposeBeanModel {
    
     private String regn_no;
    private String appl_no;
    private String stateCd;
    private String empCd;
    private int pur_cd;
    private int offCode;
    private String reason;
    private Map<String, Integer> purCodeList;
    private String userCategory;
        boolean isTradeCert = true;
        boolean isAgentCert = true;
        boolean isCommonCarrierCert = true;
         boolean isSameDayDispose = false;
        boolean isOnlinePaymentOpted=false;
        private String ipaddress;

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }
List<String> selectedPurposeCode;

    public List<String> getSelectedPurposeCode() {
        return selectedPurposeCode;
    }

    public void setSelectedPurposeCode(List<String> selectedPurposeCode) {
        this.selectedPurposeCode = selectedPurposeCode;
    }
    public boolean isIsSameDayDispose() {
        return isSameDayDispose;
    }

    public void setIsSameDayDispose(boolean isSameDayDispose) {
        this.isSameDayDispose = isSameDayDispose;
    }

    public boolean isIsOnlinePaymentOpted() {
        return isOnlinePaymentOpted;
    }

    public void setIsOnlinePaymentOpted(boolean isOnlinePaymentOpted) {
        this.isOnlinePaymentOpted = isOnlinePaymentOpted;
    }

    public boolean isIsTradeCert() {
        return isTradeCert;
    }

    public void setIsTradeCert(boolean isTradeCert) {
        this.isTradeCert = isTradeCert;
    }

    public boolean isIsAgentCert() {
        return isAgentCert;
    }

    public void setIsAgentCert(boolean isAgentCert) {
        this.isAgentCert = isAgentCert;
    }

    public boolean isIsCommonCarrierCert() {
        return isCommonCarrierCert;
    }

    public void setIsCommonCarrierCert(boolean isCommonCarrierCert) {
        this.isCommonCarrierCert = isCommonCarrierCert;
    }
        
        
private int  action_cd;

    public int getAction_cd() {
        return action_cd;
    }

    public void setAction_cd(int action_cd) {
        this.action_cd = action_cd;
    }
    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }
    public String getRegn_no() {
        return regn_no;
    }

    public void setRegn_no(String regn_no) {
        this.regn_no = regn_no;
    }

    public String getAppl_no() {
        return appl_no;
    }

    public void setAppl_no(String appl_no) {
        this.appl_no = appl_no;
    }

    public String getStateCd() {
        return stateCd;
    }

    public void setStateCd(String stateCd) {
        this.stateCd = stateCd;
    }

    public String getEmpCd() {
        return empCd;
    }

    public void setEmpCd(String empCd) {
        this.empCd = empCd;
    }

    public int getPur_cd() {
        return pur_cd;
    }

    public void setPur_cd(int pur_cd) {
        this.pur_cd = pur_cd;
    }

    public int getOffCode() {
        return offCode;
    }

    public void setOffCode(int offCode) {
        this.offCode = offCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Map<String, Integer> getPurCodeList() {
        return purCodeList;
    }

    public void setPurCodeList(Map<String, Integer> purCodeList) {
        this.purCodeList = purCodeList;
    }
    
    
}

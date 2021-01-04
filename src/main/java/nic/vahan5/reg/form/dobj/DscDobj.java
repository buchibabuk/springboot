package nic.vahan5.reg.form.dobj;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import app.eoffice.dsc.xml.common.Revocation;
import app.eoffice.dsc.xml.response.ChainCertificates.CertificateDetail;
import java.util.Date;
import java.util.List;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Niraj
 */
public class DscDobj {

    private String serialNo;
    private String userName;
    private String certValidUpto;
    private Revocation revocationSt;
    private String rtoUser;
    private int offcd;
    private String stateCd;
    private String revocstr;
    private String certlevel;
    private String cdpPoint;
    private byte[] data;
    private StreamedContent doFile;
    private String chainCertXml;
    private String activeStatus;
    private List<CertificateDetail> chainCertificates;
    private Date certValidDate;
  

    public String getCertlevel() {
        return certlevel;
    }

    public void setCertlevel(String certlevel) {
        this.certlevel = certlevel;
    }

    public String getCdpPoint() {
        return cdpPoint;
    }

    public void setCdpPoint(String cdpPoint) {
        this.cdpPoint = cdpPoint;
    }

    public String getChainCertXml() {
        return chainCertXml;
    }

    public void setChainCertXml(String chainCertXml) {
        this.chainCertXml = chainCertXml;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getRevocstr() {
        return revocstr;
    }

    public void setRevocstr(String revocstr) {
        this.revocstr = revocstr;
    }

    public String getRtoUser() {
        return rtoUser;
    }

    public void setRtoUser(String rtoUser) {
        this.rtoUser = rtoUser;
    }

    public int getOffcd() {
        return offcd;
    }

    public void setOffcd(int offcd) {
        this.offcd = offcd;
    }

    public String getStateCd() {
        return stateCd;
    }

    public void setStateCd(String stateCd) {
        this.stateCd = stateCd;
    }

    public Revocation getRevocationSt() {
        return revocationSt;
    }

    public void setRevocationSt(Revocation revocationSt) {
        this.revocationSt = revocationSt;
    }

    public String getCertValidUpto() {
        return certValidUpto;
    }

    public void setCertValidUpto(String certValidUpto) {
        this.certValidUpto = certValidUpto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * @return the doFile
     */
    public StreamedContent getDoFile() {
        return doFile;
    }

    /**
     * @param doFile the doFile to set
     */
    public void setDoFile(StreamedContent doFile) {
        this.doFile = doFile;
    }

    /**
     * @return the chainCertificates
     */
    public List<CertificateDetail> getChainCertificates() {
        return chainCertificates;
    }

    /**
     * @param chainCertificates the chainCertificates to set
     */
    public void setChainCertificates(List<CertificateDetail> chainCertificates) {
        this.chainCertificates = chainCertificates;
    }

    /**
     * @return the certValidDate
     */
    public Date getCertValidDate() {
        return certValidDate;
    }

    /**
     * @param certValidDate the certValidDate to set
     */
    public void setCertValidDate(Date certValidDate) {
        this.certValidDate = certValidDate;
    }

  
}

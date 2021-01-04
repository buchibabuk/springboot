/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.vahan5.reg.form.dobj;

import java.io.Serializable;

/**
 *
 * @author acer
 */
public class TmConfigurationDMS implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean docsUpload;
    private boolean docsVerify;
    private boolean docsApprove;
    private boolean tempDocsUpload;
    private boolean docsUploadAtOffice;
    private String docUploadAllotedOff;
    private String uploadActionCd;
    private String verfyActionCd;
    private String approveActionCd;
    private String purCd;
    private String tempApproveActionCd;
    private boolean docsFolwRequired = false;

    /**
     * @return the docsUpload
     */
    public boolean isDocsUpload() {
        return docsUpload;
    }

    /**
     * @param docsUpload the docsUpload to set
     */
    public void setDocsUpload(boolean docsUpload) {
        this.docsUpload = docsUpload;
    }

    /**
     * @return the docsVerify
     */
    public boolean isDocsVerify() {
        return docsVerify;
    }

    /**
     * @param docsVerify the docsVerify to set
     */
    public void setDocsVerify(boolean docsVerify) {
        this.docsVerify = docsVerify;
    }

    /**
     * @return the docsApprove
     */
    public boolean isDocsApprove() {
        return docsApprove;
    }

    /**
     * @param docsApprove the docsApprove to set
     */
    public void setDocsApprove(boolean docsApprove) {
        this.docsApprove = docsApprove;
    }

    /**
     * @return the tempDocsUpload
     */
    public boolean isTempDocsUpload() {
        return tempDocsUpload;
    }

    /**
     * @param tempDocsUpload the tempDocsUpload to set
     */
    public void setTempDocsUpload(boolean tempDocsUpload) {
        this.tempDocsUpload = tempDocsUpload;
    }

    /**
     * @return the docUploadAllotedOff
     */
    public String getDocUploadAllotedOff() {
        return docUploadAllotedOff;
    }

    /**
     * @param docUploadAllotedOff the docUploadAllotedOff to set
     */
    public void setDocUploadAllotedOff(String docUploadAllotedOff) {
        this.docUploadAllotedOff = docUploadAllotedOff;
    }

    /**
     * @return the docsUploadAtOffice
     */
    public boolean isDocsUploadAtOffice() {
        return docsUploadAtOffice;
    }

    /**
     * @param docsUploadAtOffice the docsUploadAtOffice to set
     */
    public void setDocsUploadAtOffice(boolean docsUploadAtOffice) {
        this.docsUploadAtOffice = docsUploadAtOffice;
    }

    /**
     * @return the uploadActionCd
     */
    public String getUploadActionCd() {
        return uploadActionCd;
    }

    /**
     * @param uploadActionCd the uploadActionCd to set
     */
    public void setUploadActionCd(String uploadActionCd) {
        this.uploadActionCd = uploadActionCd;
    }

    /**
     * @return the verfyActionCd
     */
    public String getVerfyActionCd() {
        return verfyActionCd;
    }

    /**
     * @param verfyActionCd the verfyActionCd to set
     */
    public void setVerfyActionCd(String verfyActionCd) {
        this.verfyActionCd = verfyActionCd;
    }

    /**
     * @return the approveActionCd
     */
    public String getApproveActionCd() {
        return approveActionCd;
    }

    /**
     * @param approveActionCd the approveActionCd to set
     */
    public void setApproveActionCd(String approveActionCd) {
        this.approveActionCd = approveActionCd;
    }

    /**
     * @return the purCd
     */
    public String getPurCd() {
        return purCd;
    }

    /**
     * @param purCd the purCd to set
     */
    public void setPurCd(String purCd) {
        this.purCd = purCd;
    }

    /**
     * @return the tempApproveActionCd
     */
    public String getTempApproveActionCd() {
        return tempApproveActionCd;
    }

    /**
     * @param tempApproveActionCd the tempApproveActionCd to set
     */
    public void setTempApproveActionCd(String tempApproveActionCd) {
        this.tempApproveActionCd = tempApproveActionCd;
    }

    /**
     * @return the docsFolwRequired
     */
    public boolean isDocsFolwRequired() {
        return docsFolwRequired;
    }

    /**
     * @param docsFolwRequired the docsFolwRequired to set
     */
    public void setDocsFolwRequired(boolean docsFolwRequired) {
        this.docsFolwRequired = docsFolwRequired;
    }
}

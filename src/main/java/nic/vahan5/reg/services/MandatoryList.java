/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.vahan5.reg.services;

import java.util.List;

public class MandatoryList {

    private String catId;
    private String catName;
    private String mandatory;
    private Boolean docUploaded;
    private List<SubCategoryMasterDataList> subcategoryMasterDataList;

    public String getCatId() {
        return catId;
    }

    public String getCatName() {
        return catName;
    }

    public String getMandatory() {
        return mandatory;
    }

    public Boolean getDocUploaded() {
        return docUploaded;
    }

    public List<SubCategoryMasterDataList> getSubcategoryMasterDataList() {
        return subcategoryMasterDataList;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public void setDocUploaded(Boolean docUploaded) {
        this.docUploaded = docUploaded;
    }

    public void setSubcategoryMasterDataList(List<SubCategoryMasterDataList> subcategoryMasterDataList) {
        this.subcategoryMasterDataList = subcategoryMasterDataList;
    }
}

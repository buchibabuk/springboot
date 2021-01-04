package nic.vahan5.reg.form.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;

import nic.java.util.CommonUtils;
import nic.vahan5.reg.form.dobj.ImportedVehicleDobj;


/**
 *
 * @author Kartikey Singh
 */
public class ImportedVehicleBeanModel {

    private int cm_country_imp;
    private List cm_country_imp_list;
    private String tf_dealer_imp;
    private String tf_place_imp;
    private String tf_foreign_imp;
    private Integer tf_YOM_imp;
    private ImportedVehicleDobj imp_dobj_prv;
    private List<ComparisonBeanModel> compBeanList = new ArrayList<>();

    public ImportedVehicleBeanModel() {
    }

	/*
	 * public ImportedVehicleBeanModel(ImportedVehicleBean importedVehicleBean) {
	 * this.cm_country_imp = importedVehicleBean.getCm_country_imp();
	 * this.cm_country_imp_list = importedVehicleBean.getCm_country_imp_list();
	 * this.tf_dealer_imp = importedVehicleBean.getTf_dealer_imp();
	 * this.tf_place_imp = importedVehicleBean.getTf_place_imp();
	 * this.tf_foreign_imp = importedVehicleBean.getTf_foreign_imp();
	 * this.tf_YOM_imp = importedVehicleBean.getTf_YOM_imp(); this.imp_dobj_prv =
	 * importedVehicleBean.getImp_dobj_prv(); }
	 */
    
    public ImportedVehicleDobj setBean_to_Dobj() {
        ImportedVehicleDobj obj = null;
        if (cm_country_imp != 0 && tf_YOM_imp != 0 && tf_YOM_imp != null
                && !CommonUtils.isNullOrBlank(tf_dealer_imp)
                && !CommonUtils.isNullOrBlank(tf_place_imp) && !CommonUtils.isNullOrBlank(tf_foreign_imp)) {
            obj = new ImportedVehicleDobj();
            obj.setCm_country_imp(cm_country_imp);
            obj.setTf_dealer_imp(tf_dealer_imp.trim());
            obj.setTf_YOM_imp(tf_YOM_imp);
            obj.setTf_foreign_imp(tf_foreign_imp.trim());
            obj.setTf_place_imp(tf_place_imp.trim());
        }
        return obj;
    }
    
    public int getCm_country_imp() {
        return cm_country_imp;
    }

    public void setCm_country_imp(int cm_country_imp) {
        this.cm_country_imp = cm_country_imp;
    }

    public List getCm_country_imp_list() {
        return cm_country_imp_list;
    }

    public void setCm_country_imp_list(List cm_country_imp_list) {
        this.cm_country_imp_list = cm_country_imp_list;
    }

    public String getTf_dealer_imp() {
        return tf_dealer_imp;
    }

    public void setTf_dealer_imp(String tf_dealer_imp) {
        this.tf_dealer_imp = tf_dealer_imp;
    }

    public String getTf_place_imp() {
        return tf_place_imp;
    }

    public void setTf_place_imp(String tf_place_imp) {
        this.tf_place_imp = tf_place_imp;
    }

    public String getTf_foreign_imp() {
        return tf_foreign_imp;
    }

    public void setTf_foreign_imp(String tf_foreign_imp) {
        this.tf_foreign_imp = tf_foreign_imp;
    }

    public Integer getTf_YOM_imp() {
        return tf_YOM_imp;
    }

    public void setTf_YOM_imp(Integer tf_YOM_imp) {
        this.tf_YOM_imp = tf_YOM_imp;
    }

    public ImportedVehicleDobj getImp_dobj_prv() {
        return imp_dobj_prv;
    }

    public void setImp_dobj_prv(ImportedVehicleDobj imp_dobj_prv) {
        this.imp_dobj_prv = imp_dobj_prv;
    }

    public List<ComparisonBeanModel> getCompBeanList() {
        return compBeanList;
    }

    public void setCompBeanList(List<ComparisonBeanModel> compBeanList) {
        this.compBeanList = compBeanList;
    }
    
    public void setDobj_to_Bean(ImportedVehicleDobj obj) {
        if (obj != null) {
            setCm_country_imp(obj.getCm_country_imp());
            setTf_YOM_imp(obj.getTf_YOM_imp());
            setTf_dealer_imp(obj.getTf_dealer_imp());
            setTf_foreign_imp(obj.getTf_foreign_imp());
            setTf_place_imp(obj.getTf_place_imp());
        }
    }

    @Override
    public String toString() {
        return "ImportedVehicleBeanModel{" + "cm_country_imp=" + cm_country_imp + ", cm_country_imp_list=" + cm_country_imp_list + ", tf_dealer_imp=" + tf_dealer_imp + ", tf_place_imp=" + tf_place_imp + ", tf_foreign_imp=" + tf_foreign_imp + ", tf_YOM_imp=" + tf_YOM_imp + ", imp_dobj_prv=" + imp_dobj_prv + ", compBeanList=" + compBeanList + '}';
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.vahan5.reg.form.dobj;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nic5912
 */
@XmlRootElement(name = "DOTaxDetails")
public class DOTaxDetails {
    
    
    private List<DOTaxDetail> taxList=new ArrayList<DOTaxDetail>();

    public DOTaxDetails(){}
    
    public DOTaxDetails(List<DOTaxDetail> doList ){
        this.taxList=doList;
    }
    @XmlElement(name = "DOTaxDetail", type = DOTaxDetail.class)
    public List<DOTaxDetail> getTaxList() {
        return taxList;
    }

    public void setTaxList(List<DOTaxDetail> taxList) {
        this.taxList = taxList;
    }
    
    
    
}

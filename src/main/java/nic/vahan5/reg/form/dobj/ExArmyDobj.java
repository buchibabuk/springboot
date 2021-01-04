package nic.vahan5.reg.form.dobj;

import java.io.Serializable;
import java.util.Date;

public class ExArmyDobj implements Serializable, Cloneable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tf_Voucher_no = "";
	    private Date tf_VoucherDate;
	    private String tf_POP = "";
	    private boolean exArmyInsertUpdateFlag;

	    public String getTf_Voucher_no() {
	        return tf_Voucher_no;
	    }

	    public void setTf_Voucher_no(String tf_Voucher_no) {
	        this.tf_Voucher_no = tf_Voucher_no;
	    }

	    public Date getTf_VoucherDate() {
	        return tf_VoucherDate;
	    }

	    public void setTf_VoucherDate(Date tf_VoucherDate) {
	        this.tf_VoucherDate = tf_VoucherDate;
	    }

	    public String getTf_POP() {
	        return tf_POP;
	    }

	    public void setTf_POP(String tf_POP) {
	        this.tf_POP = tf_POP;
	    }

	    @Override
	    public Object clone() throws CloneNotSupportedException {
	        return super.clone();
	    }

	    public boolean isExArmyInsertUpdateFlag() {
	        return exArmyInsertUpdateFlag;
	    }

	    public void setExArmyInsertUpdateFlag(boolean exArmyInsertUpdateFlag) {
	        this.exArmyInsertUpdateFlag = exArmyInsertUpdateFlag;
	    }
	    
}

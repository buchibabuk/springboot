package nic.vahan5.reg.form.dobj;

import java.io.Serializable;

public class FasTagResponseDobj implements Serializable{
	private String action;
    private String status;
    private String data;
    private String Signature;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String Signature) {
        this.Signature = Signature;
    }
}

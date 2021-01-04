package nic.vahan5.reg.form.dobj;

import java.io.Serializable;

public class FasTagRequestDobj implements Serializable{
	 private String vin;
	    private String engineno;

	    public String getVin() {
	        return vin;
	    }

	    public void setVin(String vin) {
	        this.vin = vin;
	    }

	    public String getEngineno() {
	        return engineno;
	    }

	    public void setEngineno(String engineno) {
	        this.engineno = engineno;
	    }
}

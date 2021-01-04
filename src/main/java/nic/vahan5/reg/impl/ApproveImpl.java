package nic.vahan5.reg.impl;

import org.primefaces.component.selectonemenu.SelectOneMenu;

public class ApproveImpl {
	
	 private SelectOneMenu prevAction = new SelectOneMenu();
	  public SelectOneMenu getPrevAction() {
	        return prevAction;
	    }

	    /**
	     * @param prevAction the prevAction to set
	     */
	    public void setPrevAction(SelectOneMenu prevAction) {
	        this.prevAction = prevAction;
	    }

	    
}

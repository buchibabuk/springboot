/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.vahan5.reg.form.model;

import java.io.Serializable;

//import nic.vahan.form.impl.Util;

public final class SessionVariables implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String empCodeLoggedIn;
    private final String stateCodeSelected;
    private final int offCodeSelected;
    private final String userCatgForLoggedInUser;
    private final int actionCodeSelected;
    private final String empNameLoggedIn;
    private final SeatAllotedDetails selectedWork;
    private String userIdForLoggedInUser;

    public SessionVariables() {
        this.empCodeLoggedIn = null;
        		//Util.getEmpCode();
        this.stateCodeSelected = null;
        		//Util.getUserStateCode();
        this.userCatgForLoggedInUser = null;
        		//Util.getUserCategory();
        this.empNameLoggedIn = null;
        		//Util.getUserName();
        this.userIdForLoggedInUser =null;
        		//Util.getUserId();
        this.selectedWork =null; 
        		//Util.getSelectedSeat();
        if (selectedWork != null) {
            this.offCodeSelected = selectedWork.getOff_cd();
            this.actionCodeSelected = selectedWork.getAction_cd();
        } else {
            this.offCodeSelected = 0;
            this.actionCodeSelected = 0;
        }
    }    

    public String getEmpCodeLoggedIn() {
        return empCodeLoggedIn;
    }

    public String getStateCodeSelected() {
        return stateCodeSelected;
    }

    public int getOffCodeSelected() {
        return offCodeSelected;
    }

    public String getUserCatgForLoggedInUser() {
        return userCatgForLoggedInUser;
    }

    public int getActionCodeSelected() {
        return actionCodeSelected;
    }

    /**
     * @return the empNameLoggedIn
     */
    public String getEmpNameLoggedIn() {
        return empNameLoggedIn;
    }

    /**
     * @return the selectedWork
     */
    public SeatAllotedDetails getSelectedWork() {
        return selectedWork;
    }

    /**
     * @return the userIdForLoggedInUser
     */
    public String getUserIdForLoggedInUser() {
        return userIdForLoggedInUser;
    }

    /**
     * @param userIdForLoggedInUser the userIdForLoggedInUser to set
     */
    public void setUserIdForLoggedInUser(String userIdForLoggedInUser) {
        this.userIdForLoggedInUser = userIdForLoggedInUser;
    }
}

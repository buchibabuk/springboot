package nic.vahan5.reg.server;

import java.sql.PreparedStatement;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.sql.RowSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.java.util.DateUtils;
import nic.vahan.common.jsf.utils.DateUtil;
import nic.vahan5.reg.impl.ApplicationInwardImplementation;
import nic.vahan5.reg.impl.ApproveImpl;
//import nic.vahan5.reg.impl.ApproveImpl;
import nic.vahan5.reg.impl.DefacementImplementation;
import nic.vahan5.reg.impl.Home_Impl;
import nic.vahan5.reg.impl.OfficeCorrectionImpl;
import nic.vahan5.reg.form.dobj.VmSmartCardHsrpDobj;
import nic.vahan5.reg.CommonUtils.FormulaUtilities;
import nic.vahan5.reg.CommonUtils.VehicleParameters;
import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.MasterTableFiller;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.form.dobj.DefacementDobj;
import nic.vahan5.reg.form.dobj.EpayDobj;
import nic.vahan5.reg.form.dobj.NocDobj;
import nic.vahan5.reg.form.dobj.Owner_dobj;
import nic.vahan5.reg.form.dobj.Status_dobj;
import nic.vahan5.reg.form.dobj.TmConfigurationDealerDobj;
import nic.vahan5.reg.form.dobj.TmConfigurationDobj;
//import static nic.vahan.CommonUtils.FormulaUtils.replaceTagValues;
//import static nic.vahan.CommonUtils.FormulaUtils.isCondition;
@Service
public class ServerUtility {
	
	
	@Autowired
	TransactionManagerReadOnly tmgr;
	
	@Autowired
	TransactionManagerOne tmg;
	
	@Autowired
	Home_Impl implObj;
	
	@Autowired
	FormulaUtilities formulaUtilities;
	
	@Autowired
	NextStageWS nextStageWs;
	
	@Autowired
	ApplicationInwardImplementation applicationInwardImpl;
	@Autowired
	 DefacementImplementation defaceImpl;
	@Autowired
	MasterTableFiller masterTableFiller;
	@Autowired
	OfficeCorrectionImpl offcorimpl;
	
	 private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ServerUtility.class);

	public int VehicleClassType(int vh_class) throws VahanException {
        int returnType = 0;
        boolean isTranport = isTransport(vh_class, null);
        if (isTranport) {
            returnType = 1;
        } else {
            returnType = 2;
        }
        return returnType;
    }
	public  boolean isTransport(int vh_class, Owner_dobj ownerDobj) throws VahanException {
        Boolean isTransport = null;
        PreparedStatement psmt = null;
       // TransactionManagerReadOnly tmgr = null;
        try {
            if (vh_class > 0) {
                if (ownerDobj != null && ownerDobj.getVehType() > 0) {
                    if (ownerDobj.getVehType() == TableConstants.VM_VEHTYPE_TRANSPORT) {
                        isTransport = true;
                    } else {
                        isTransport = false;
                    }
                } else {
                  //  tmgr = new TransactionManagerReadOnly("isTransport");
                    String strSQL = "SELECT case when class_type = 1 then true else false end as isTransport FROM " + TableList.VM_VH_CLASS
                            + " where vh_class = ?";
                    psmt = tmgr.prepareStatement(strSQL);
                    psmt.setInt(1, vh_class);
                    RowSet rs = tmgr.fetchDetachedRowSet();
                    if (rs.next()) {
                        isTransport = rs.getBoolean("isTransport");
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
        } finally {
            try {
                if (tmgr != null) {
                    tmgr.release();
                }
            } catch (Exception e) {
                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            }
        }

        if (isTransport == null) {
            throw new VahanException("No Vehicle Type Found (VchClass:" + vh_class + ")");
        }
        return isTransport;
    }
	
	  public List getOffCode(String selectedEmp) {
	        List off_cd = new ArrayList();
	        PreparedStatement ps = null;
	      //  TransactionManagerReadOnly tmgr = null;
	        RowSet rs;
	        String sql = null;
	        String offList = "";
	        try {
	         //   tmgr = new TransactionManagerReadOnly("getOffCode");
	            sql = "Select assigned_office from " + TableList.TM_USER_PERMISSIONS + " where user_cd=?";
	            ps = tmgr.prepareStatement(sql);
	            ps.setInt(1, Integer.parseInt(selectedEmp));
	            rs = tmgr.fetchDetachedRowSet();
	            if (rs.next()) {
	                offList = rs.getString("assigned_office");
	            }
	            off_cd = makeList(offList);

	        } catch (SQLException ex) {
	            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return off_cd;
	    }
	  
	  public static List makeList(String code) {
	        List codeInList = null;
	        if (!CommonUtils.isNullOrBlank(code)) {
	            String[] codeInString = code.split(",");
	            codeInList = new ArrayList();
	            for (int i = 0; i < codeInString.length; i++) {
	                codeInList.add(codeInString[i]);
	            }
	        }
	        return codeInList;
	    }
	  
	  public static Date dateRange(Date date, int year, int month, int day_of_month) {
	        java.util.Calendar cal = new GregorianCalendar();
	        cal.setTime(date);
	        cal.add(java.util.Calendar.YEAR, year);
	        cal.add(java.util.Calendar.MONTH, month);
	        cal.add(java.util.Calendar.DAY_OF_MONTH, day_of_month);
	        return cal.getTime();
	    }
	  
	  public static String parseDateToString(Date dt) {
	        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	        sdf.applyPattern("dd-MMM-yyyy");
	        String nDate = sdf.format(dt);
	        return nDate;
	    }
	  public  VmSmartCardHsrpDobj getVmSmartCardHsrpParameters(String state_cd, int off_cd) throws VahanException {
	        PreparedStatement ps = null;
	       // TransactionManagerReadOnly tmgr = null;
	        String sql = null;
	        VmSmartCardHsrpDobj dobj = null;

	        try {
	          //  tmgr = new TransactionManagerReadOnly("getVmSmartCardHsrpParameters");
	            sql = "SELECT * FROM " + TableList.VM_SMART_CARD_HSRP
	                    + " WHERE state_cd = ? and off_cd=?";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, state_cd);
	            ps.setInt(2, off_cd);
	            RowSet rs = tmgr.fetchDetachedRowSet();
	            if (rs.next()) {
	                dobj = new VmSmartCardHsrpDobj();
	                dobj.setState_cd(rs.getString("state_cd"));
	                dobj.setSmart_card(rs.getBoolean("smart_card"));
	                dobj.setHsrp(rs.getBoolean("hsrp"));
	                dobj.setDay_begin(rs.getDate("day_begin"));
	                dobj.setCash_counter_closed(rs.getBoolean("cash_counter_closed"));
	                dobj.setLast_working_day(rs.getDate("last_working_day"));
	                dobj.setNew_regn_not_allowed(rs.getString("new_regn_not_allowed"));
	                dobj.setNew_regn_not_allowed_msg(rs.getString("new_regn_not_allowed_msg"));
	                dobj.setOld_veh_hsrp(rs.getBoolean("old_veh_hsrp"));
	                dobj.setPaper_rc(rs.getString("paper_rc"));
	                dobj.setAutomaticFitness(rs.getString("automatic_fitness_formula"));
	            }
	            if (dobj == null && state_cd != null && !state_cd.isEmpty() && off_cd > 0) {
	                throw new VahanException("Failed to Load Cash Counter/Smart Card/HSRP Configuration (" + state_cd + "-" + off_cd + ")");
	            }

	        } catch (SQLException e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Failed to Load Cash Counter/Smart Card/HSRP Configuration (" + state_cd + "-" + off_cd + ").Please Contact to the System Administrator");
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }

	        return dobj;
	    }
	  public  int getOfficeCdForDealerTempAppl(String applNo, String stateCd, String callFrom, int offCode) throws VahanException {
	        String sql = null;
	        PreparedStatement ps = null;
	     //  TransactionManagerReadOnly tmgr = null;
	        int offCd = 0;
	        try {
	            if (!"offCorrection".equals(callFrom) && !"taxMode".equals(callFrom)) {
	                offCd = offCode;
	            }
	         //   tmgr = new TransactionManagerReadOnly("getOfficeCdForFileFlow");
	            sql = " select a.appl_no,a.pur_cd, a.off_cd  from va_details a where a.appl_no in(select  b.appl_no from va_details b where a.appl_no = b.appl_no and b.pur_cd in (" + TableConstants.VM_TRANSACTION_MAST_DEALER_NEW_VEHICLE + "," + TableConstants.VM_TRANSACTION_MAST_DEALER_TEMP_VEHICLE + ") group by 1 having count(b.appl_no)> 1) "
	                    + " AND a.appl_no = ? and a.state_cd = ? order by a.pur_cd desc limit 1";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, applNo);
	            ps.setString(2, stateCd);
	            RowSet rs = tmgr.fetchDetachedRowSet();
	            while (rs.next()) {
	                offCd = rs.getInt("off_cd");
	            }
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException(TableConstants.SomthingWentWrong);
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return offCd;
	    }
	  
	  public  String getTaxHead(int pur_cd) {
	        String whereiam = "Get Tax Head";
	        String taxHead = "";
	        PreparedStatement psmt = null;
	       // TransactionManagerReadOnly tmgr = null;
	        try {
	          //  tmgr = new TransactionManagerReadOnly(whereiam);
	            String strSQL = "SELECT descr FROM tm_purpose_mast where pur_cd = ?;";
	            psmt = tmgr.prepareStatement(strSQL);
	            psmt.setInt(1, pur_cd);
	            RowSet rs = tmgr.fetchDetachedRowSet();
	            if (rs.next()) {
	                taxHead = rs.getString("descr");
	            }
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return taxHead;
	    }
	  public  int getOfficeCodeType(int offCode, String stateCode) {
	        int officeName = 0;
	      //  TransactionManagerReadOnly tmgr = null;
	        String ChasiSQL = "select off_type_cd from tm_office where off_cd=? and state_cd = ?";
	        try {
	           // tmgr = new TransactionManager("getOfficeName");
	            PreparedStatement ps = tmgr.prepareStatement(ChasiSQL);
	            ps.setInt(1, offCode);
	            ps.setString(2, stateCode);
	            RowSet rs = tmgr.fetchDetachedRowSet();
	            if (rs.next()) {
	                officeName = rs.getInt("off_type_cd");
	            }
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return officeName;
	    }
	  public  ArrayList<Status_dobj> applicationStatusByApplNo(String applNo, String state_cd) throws VahanException, Exception {
	        String sql = null;
	        PreparedStatement ps = null;
	       // TransactionManagerOne tmgr = null;
	        ArrayList<Status_dobj> list = new ArrayList<>();

	        if (applNo != null && state_cd != null) {

	            try {
	            //    tmgr = new TransactionManagerOne("applicationStatusByApplNo");

	                sql = " SELECT distinct a.*,b.regn_no,c.descr,d.off_name,e.descr as state_name, to_char(b.appl_dt, 'YYYY-MM-DD') as appl_dt FROM " + TableList.VA_STATUS + " a "
	                        + " INNER JOIN " + TableList.VA_DETAILS + " b on b.pur_cd=a.pur_cd and b.appl_no=a.appl_no "
	                        + " LEFT  JOIN " + TableList.TM_PURPOSE_MAST + " c on c.pur_cd=b.pur_cd "
	                        + " LEFT  JOIN " + TableList.TM_OFFICE + " d on d.state_cd=a.state_cd and d.off_cd=a.off_cd "
	                        + " LEFT  JOIN " + TableList.TM_STATE + " e on e.state_code=a.state_cd "
	                        + " WHERE b.appl_no=? and a.state_cd =? and b.entry_status <> ? ORDER BY a.op_dt desc";

	                ps = tmgr.prepareStatement(sql);
	                ps.setString(1, applNo);
	                ps.setString(2, state_cd);
	                ps.setString(3, TableConstants.STATUS_APPROVED);

	                RowSet rs = tmgr.fetchDetachedRowSet();

	                while (rs.next()) {
	                    Status_dobj status_dobj = new Status_dobj();
	                    status_dobj.setAppl_dt(rs.getString("appl_dt"));
	                    status_dobj.setAppl_no(rs.getString("appl_no"));
	                    status_dobj.setRegn_no(rs.getString("regn_no"));
	                    status_dobj.setPur_cd(rs.getInt("pur_cd"));
	                    status_dobj.setAction_cd(rs.getInt("action_cd"));
	                    status_dobj.setState_cd(rs.getString("state_cd"));
	                    status_dobj.setOff_cd(rs.getInt("off_cd"));
	                    status_dobj.setPurCdDescr(rs.getString("descr"));
	                    status_dobj.setOffName(rs.getString("off_name"));
	                    status_dobj.setStateName(rs.getString("state_name"));
	                    list.add(status_dobj);
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	                throw new VahanException("Error in getting Application Status.");
	            } finally {
	                try {
	                    if (tmgr != null) {
	                        tmgr.release();
	                    }
	                } catch (Exception e) {
	                    LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	                }
	            }
	        }
	        return list;
	    }//end of applicationStatusByApplNo
	  public  String getDealerCode(long userCode, String stateCd, int offCd,TmConfigurationDobj tmconf) throws VahanException {
	        PreparedStatement ps = null;
	        String sql = null;
	        String dealerCode = "";
	       // TransactionManagerReadOnly tmgr = null;
	        try {
	           // tmgr = new TransactionManagerReadOnly("getDealerCode");
	            if (validateDealerUserForAllOffice(userCode, stateCd,tmconf)) {
	                sql = "select m.dealer_cd \n"
	                        + "from  vm_dealer_mast m left outer join tm_user_permissions up\n"
	                        + "on m.dealer_cd = up.dealer_cd \n"
	                        + "where m.state_cd = ? and up.user_cd = ? ";

	                ps = tmgr.prepareStatement(sql);
	                ps.setString(1, stateCd);
	                ps.setLong(2, userCode);
	            } else {
	                sql = "select m.dealer_cd \n"
	                        + "from  vm_dealer_mast m left outer join tm_user_permissions up\n"
	                        + "on m.dealer_cd = up.dealer_cd \n"
	                        + "where m.state_cd = ? and m.off_cd = ? and up.user_cd = ? ";

	                ps = tmgr.prepareStatement(sql);
	                ps.setString(1, stateCd);
	                ps.setInt(2, offCd);
	                ps.setLong(3, userCode);
	            }
	            RowSet rs = tmgr.fetchDetachedRowSet();

	            if (rs.next()) {
	                dealerCode = rs.getString("dealer_cd");
	            }
	        } catch (VahanException vex) {
	            throw vex;
	        } catch (SQLException e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Something went wrong during getting details of dealer code, Please contact to the system administrator.");
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Something went wrong during getting details of dealer code, Please contact to the system administrator.");
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return dealerCode;
	    }
	  
	  public  boolean validateDealerUserForAllOffice(long userCode, String userStateCode,TmConfigurationDobj tmConfigDobj) throws VahanException {
	        boolean status = false;
	        boolean stateAllow = false;
	        boolean dealerAuth = false;
	        try {
	            String userCatg =getUserCategory(userCode);
	          //  TmConfigurationDobj tmConfigDobj = Utility.getTmConfiguration(null, userStateCode);
	           // TmConfigurationDobj tmConfigDobj=null;
	            if (tmConfigDobj != null) {
	                stateAllow = tmConfigDobj.isDealer_auth_for_all_office();
	            }
	           // Home_Impl implObj = new Home_Impl();
	            dealerAuth = implObj.getDealerAuthority(userCode);
	            if (userCatg != null && userCatg.equalsIgnoreCase(TableConstants.USER_CATG_DEALER) && stateAllow && dealerAuth) {
	                status = true;
	            }
	        } catch (VahanException e) {
	            throw e;
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Something went wrong during validation of dealer.");
	        }
	        return status;
	    }
	  
	  public  String getUserCategory(long emp_cd) throws VahanException {
	        String user_catg = null;
	        PreparedStatement ps = null;
	       // TransactionManagerReadOnly tmgr = null;
	        String sql = null;
	        try {
	         //   tmgr = new TransactionManagerReadOnly("getUserCategory");
	            sql = "Select user_catg from " + TableList.TM_USER_INFO + " where user_cd=?";
	            ps = tmgr.prepareStatement(sql);
	            ps.setLong(1, emp_cd);

	            RowSet rs = tmgr.fetchDetachedRowSet();
	            if (rs.next()) {
	                user_catg = rs.getString("user_catg");
	            }

	        } catch (SQLException ex) {
	            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	            throw new VahanException("getUserCategory" + ex.getMessage());
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception e) {
	                throw new VahanException("getUserCategory" + e.getMessage());
	            }
	        }
	        return user_catg;
	    }
	  
	  public  void webServiceForNextStage(Status_dobj status, String newStatus, String cntr_id, String appl_no, int action_cd,
	            int purcd, String approveImplPrevAction, String empCode, String userStateCode, int selectedOffCode) throws Exception {

	        NextStageRequest request = new NextStageRequest();
	        request.setCntr_id(cntr_id);
	        request.setAppl_no(appl_no);
	        request.setEmp_cd(Long.parseLong(empCode.trim())); // getting from session
	        int prevAction = 0;

	      //  TransactionManagerOne tmgr = null;

	        try {
	            if (status.getStatus().equalsIgnoreCase(TableConstants.STATUS_REVERT)) {
	                prevAction = Integer.parseInt(approveImplPrevAction);//for selectng radiobutton
	                request.setFile_movement_type(TableConstants.BACKWARD);
	                request.setAction_cd(prevAction);
	            } else {
	                request.setFile_movement_type(TableConstants.FORWARD);
	                request.setAction_cd(action_cd);
	            }

	            request.setPur_cd(purcd);
	            request.setState_cd(userStateCode); //getting from session
	            request.setOff_cd(selectedOffCode); // getting from session
	            NextStageResponse response = null;

	            if (TableConstants.isNextStageWebService) {

	                response = nextStageWs.getNextStageResponse(request);
	                status.setCntr_id(response.getCntr_id());
	                status.setAction_cd(response.getAction_cd());
	                status.setOff_cd(response.getOff_cd());
	                status.setEmp_cd(response.getEmp_cd());
	                status.setFlow_slno(response.getFlow_slno());
	                status.setRto_code(response.getRto_code());

	            } else {

	                String sql = null;
	             //   tmgr = new TransactionManagerOne("webServiceForNextStage");
	                PreparedStatement ps = null;
	                RowSet rs = null;

	                if (request.getFile_movement_type().equals(TableConstants.BACKWARD)) {

	                    sql = "select a.flow_srno, a.action_cd\n"
	                            + " from tm_purpose_action_flow a\n"
	                            + " where a.state_cd = ? and a.action_cd = ? and a.pur_cd = ?";
	                    ps = tmg.prepareStatement(sql);
	                    ps.setString(1, request.getState_cd());
	                    ps.setInt(2, request.getAction_cd());
	                    ps.setInt(3, request.getPur_cd());
	                    rs = tmg.fetchDetachedRowSet_No_release();

	                    if (rs.next()) {
	                        status.setAction_cd(prevAction);
	                        status.setOff_cd(request.getOff_cd());
	                        status.setFlow_slno(rs.getInt("flow_srno"));
	                        status.setRto_code(request.getRto_code());
	                    }

	                } else {
	                    int cntr = 1;
	                    while (true) {

	                        sql = "select a.flow_srno, a.action_cd,a.condition_formula,seat_cd  "
	                                + " from tm_purpose_action_flow a, va_status b "
	                                + " where a.state_cd = b.state_cd and a.pur_cd = b.pur_cd and a.state_cd = ? "
	                                + " and b.appl_no = ? and a.flow_srno = b.flow_slno + ? and a.pur_cd =?";
	                        ps = tmg.prepareStatement(sql);
	                        ps.setString(1, request.getState_cd());
	                        ps.setString(2, request.getAppl_no());
	                        ps.setInt(3, cntr);
	                        ps.setInt(4, purcd);

	                        rs = tmg.fetchDetachedRowSet_No_release();

	                        if (rs.next()) {
	                            if (!CommonUtils.isNullOrBlank(rs.getString("seat_cd")) && rs.getString("seat_cd").trim().split(",").length > 1) {
	                                String[] actionCdWithFileFlow = rs.getString("seat_cd").trim().split(",");
	                                status.setAction_cd(Integer.parseInt(actionCdWithFileFlow[0]));
	                                status.setOff_cd(request.getOff_cd());
	                                status.setFlow_slno(Integer.parseInt(actionCdWithFileFlow[1]));
	                                status.setRto_code(request.getRto_code());
	                                break;
	                            } else if (status.getVehicleParameters() == null || formulaUtilities.isCondition(formulaUtilities.replaceTagValues(rs.getString("condition_formula"), status.getVehicleParameters()), "webServiceForNextStage")) {
	                                status.setAction_cd(rs.getInt("action_cd"));
	                                status.setOff_cd(request.getOff_cd());
	                                status.setFlow_slno(rs.getInt("flow_srno"));
	                                status.setRto_code(request.getRto_code());
	                                break;
	                            } else {
	                                cntr++;
	                            }
	                        } else {
	                            status.setAction_cd(0);
	                            status.setFlow_slno(0);
	                            break;
	                        }

	                    }
	                }
	            }
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        } finally {
	            try {
	                if (tmg != null) {
	                    tmg.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }

	    }//end of webServiceForNextStage
	  public  void fileFlow(TransactionManagerOne tmg, Status_dobj status_dobj, int actionCode, String selectedRoleCode,
	            String userStateCode, int offCode, String empCode, String clientIpAddress,
	            boolean allowFacelessService, boolean defacement) throws VahanException {
	     
	        PreparedStatement ps = null;
	        String sql = null;
	        String updateSeatCd = "";
	        String seatCd = "";
	        try {
	            if (status_dobj.getStatus() != null && status_dobj.getStatus().equals(TableConstants.STATUS_HOLD)) {
	                seatCd = "seat_cd = '" + status_dobj.getStatus() + "',";
	            }
	            sql = "update " + TableList.VA_STATUS + " set "
	                    + "status = ?,"
	                    + "" + seatCd + ""
	                    + "office_remark=?,"
	                    + "public_remark=? "
	                    //+ ",op_dt=current_timestamp "
	                    + " where appl_no=? and pur_cd=? and action_cd =?  ";
	            ps = tmg.prepareStatement(sql);

	            ps.setString(1, status_dobj.getStatus());
	            ps.setString(2, status_dobj.getOffice_remark());
	            ps.setString(3, status_dobj.getPublic_remark());
	            ps.setString(4, status_dobj.getAppl_no());
	            ps.setInt(5, status_dobj.getPur_cd());
	            if (actionCode == TableConstants.TM_ROLE_SKIP_FEE) {
	                ps.setInt(6, status_dobj.getPrev_action_cd());
	            } else {
	                ps.setInt(6, actionCode);
	            }
	            int fileMoved = ps.executeUpdate();
	            if (fileMoved == 0) {
	                //LOGGER.info("File has already Moved for Appl No-" + status_dobj.getAppl_no() + "{AC:" + Util.getSelectedSeat().getAction_cd() + "-PC:" + status_dobj.getPur_cd() + "-S:" + status_dobj.getStatus() + "}");
	                throw new VahanException("File has already Moved for Appl No-" + status_dobj.getAppl_no());
	            }

	            if (status_dobj.getStatus().equalsIgnoreCase(TableConstants.STATUS_COMPLETE)
	                    || status_dobj.getStatus().equalsIgnoreCase(TableConstants.STATUS_REVERT)) {

	                if (TableConstants.isNextStageWebService) {

	                    sql = "  INSERT INTO " + TableList.VHA_STATUS + " "
	                            + "  SELECT appl_no, appl_no_map, pur_cd, flow_slno, file_movement_slno,"
	                            + "  action_cd, seat_cd, cntr_id, status, office_remark, public_remark,"
	                            + "  emp_cd, op_dt, state_cd, rto_code, off_cd "
	                            + "  FROM " + TableList.VA_STATUS + " where appl_no=? and pur_cd=?";

	                    ps = tmg.prepareStatement(sql);
	                    ps.setString(1, status_dobj.getAppl_no());
	                    ps.setInt(2, status_dobj.getPur_cd());
	                    ps.executeUpdate();

	                    sql = "update " + TableList.VA_STATUS + " "
	                            + "set status='N',"
	                            + "file_movement_slno=file_movement_slno+1,"
	                            + "office_remark='',"
	                            + "public_remark='',"
	                            + "cntr_id=?,"
	                            + "emp_cd=? ,"
	                            + "action_cd=?,"
	                            + "rto_code=?,"
	                            + "flow_slno=?,"
	                            + "op_dt=current_timestamp"
	                            + " where appl_no=? and pur_cd=?";

	                    ps = tmg.prepareStatement(sql);
	                    ps.setString(1, status_dobj.getCntr_id());
	                    //Added by Afzal on 12-Jan,2015
	                    if (selectedRoleCode == null) {
	                        ps.setInt(2, 0);
	                    } else {
	                        ps.setLong(2, status_dobj.getEmp_cd());
	                    }
	                    ps.setInt(3, status_dobj.getAction_cd());
	                    ps.setString(4, status_dobj.getRto_code());
	                    ps.setInt(5, status_dobj.getFlow_slno());
	                    ps.setString(6, status_dobj.getAppl_no());
	                    ps.setInt(7, status_dobj.getPur_cd());
	                    ps.executeUpdate();

	                } else {
	                    // To Store Actual,paid,fine amount for future reference -----
	                    if (status_dobj.getStatus().equalsIgnoreCase(TableConstants.STATUS_COMPLETE)) {
	                        long paid_amt = 0l;
	                        long actual_amt = 0l;
	                        long total_amt = 0l;
	                        long paid_fine = 0l;
	                        long actual_fine = 0l;
	                        if (status_dobj.getListFeeTaxDifference() != null) {
	                            for (EpayDobj epayDobj : status_dobj.getListFeeTaxDifference()) {
	                                paid_amt = epayDobj.getE_TaxFee() + epayDobj.getE_FinePenalty();
	                                actual_amt = epayDobj.getAct_TaxFee() + epayDobj.getAct_FinePenalty();
	                                paid_fine = epayDobj.getE_FinePenalty();
	                                actual_fine = epayDobj.getAct_FinePenalty();
	                                total_amt = epayDobj.getE_total() - epayDobj.getAct_total();
	                                if (total_amt > 0 || total_amt < 0) {
	                                    sql = "Insert into " + TableList.VH_FEE_FINE_DIFF_AMT + " (appl_no,state_cd,off_cd, regn_no,pur_cd,rcpt_no,paid_amt,actual_amt,paid_fine,actual_fine,moved_by,moved_on)  VALUES (?,?,?,?,?,?,?,?,?,?,?,current_timestamp) ";
	                                    ps = tmg.prepareStatement(sql);
	                                    ps.setString(1, status_dobj.getAppl_no());
	                                    ps.setString(2, userStateCode);
	                                    ps.setInt(3, offCode);
	                                    ps.setString(4, status_dobj.getRegn_no());
	                                    ps.setInt(5, epayDobj.getPurCd());
	                                    if (CommonUtils.isNullOrBlank(epayDobj.getRcpt_no())) {
	                                        ps.setString(6, "NA");
	                                    } else {
	                                        ps.setString(6, epayDobj.getRcpt_no());
	                                    }
	                                    ps.setLong(7, paid_amt);
	                                    ps.setLong(8, actual_amt);
	                                    ps.setLong(9, paid_fine);
	                                    ps.setLong(10, actual_fine);
	                                    ps.setString(11, empCode);
	                                    ps.executeUpdate();
	                                }
	                            }
	                        }
	                    }
	                    //----
	                    // If (forward or backward) execute this
	                    
	                       
	                              
	            
	          
	            
	                   sql = "INSERT INTO " + TableList.VHA_STATUS + " "
	                            + " SELECT state_cd, off_cd, appl_no, pur_cd, flow_slno, file_movement_slno, \n"
	                            + "  action_cd, seat_cd, cntr_id, status, office_remark, public_remark, \n"
	                            + "  file_movement_type, ? , op_dt, current_timestamp, ? "
	                            + "  from  " + TableList.VA_STATUS + " where appl_no=? and pur_cd=? ";

	                    ps = tmg.prepareStatement(sql);
	                    ps.setLong(1, Long.parseLong(empCode));//this should be as status_dobj.getEmp_cd() need to be updated in future...
	                    ps.setString(2, clientIpAddress);
	                    ps.setString(3, status_dobj.getAppl_no());
	                    ps.setInt(4, status_dobj.getPur_cd());
	                    int vhaStatusUpdated = ps.executeUpdate();
	                    // end
	                    int vaStatusUpdated = 0;
	                    if ((status_dobj.getEntry_status() == null || status_dobj.getEntry_status().equals("")) && status_dobj.getAction_cd() > 0) {
	                        if (status_dobj.getStatus().equalsIgnoreCase(TableConstants.STATUS_REVERT) && CommonUtils.isNullOrBlank(status_dobj.getSeat_cd())) {
	                            updateSeatCd = "seat_cd = action_cd || ',' || flow_slno || ',' || '" + status_dobj.getStatus() + "',";
	                        } else {
	                            updateSeatCd = "seat_cd = '" + status_dobj.getStatus() + "',";
	                        }
	                        
	                        
	                      
	                        
	                        
	                        sql = "update " + TableList.VA_STATUS + " "
	                                + "set status='N',"
	                                + "file_movement_slno=file_movement_slno+1,"
	                                + "office_remark='',"
	                                + "public_remark='',"
	                                + "cntr_id=?,"
	                                + "emp_cd=? ,"
	                                + "action_cd=?,"
	                                + "flow_slno=?,"
	                                + " " + updateSeatCd + " "
	                                + "op_dt=current_timestamp"
	                                + " where appl_no=? and pur_cd=?";

	                        ps = tmg.prepareStatement(sql);
	                        ps.setString(1, status_dobj.getCntr_id());
	                        ps.setLong(2, status_dobj.getEmp_cd());
	                        ps.setInt(3, status_dobj.getAction_cd());
	                        ps.setInt(4, status_dobj.getFlow_slno());
	                        ps.setString(5, status_dobj.getAppl_no());
	                        ps.setInt(6, status_dobj.getPur_cd());
	                        vaStatusUpdated = ps.executeUpdate();

	                        if (allowFacelessService) {
	                            if (status_dobj.getAppl_no() != null && offCode != 0) {
	                                updateRtoOpenDateInVtFaceLessService(tmg, status_dobj.getAppl_no(), userStateCode, offCode);
	                            } else {
	                                throw new Exception("Problem in getting office code/applNo");
	                            }
	                        }
	                    } else if (((status_dobj.getAction_cd() == 0 && !status_dobj.getStatus().equalsIgnoreCase(TableConstants.STATUS_REVERT)) //it must execute based at the time of Approval
	                            || (status_dobj.getAction_cd() != 0 && status_dobj.getStatus().equals(TableConstants.STATUS_COMPLETE)))
	                            && (status_dobj.getEntry_status() != null && status_dobj.getEntry_status().equalsIgnoreCase(TableConstants.STATUS_APPROVED))) {

	                        if (defacement) {
	                            //DefacementImplementation defaceImpl = new DefacementImplementation();
	                            DefacementDobj defacementDobj = defaceImpl.getApplPaymentDetails(tmg, status_dobj.getAppl_no(), userStateCode);
	                            if (defacementDobj != null) {
	                                defacementDobj.setMerchant_code("5003");
	                                defacementDobj.setRefrence_no(status_dobj.getAppl_no());
	                                defacementDobj.setState_cd(userStateCode);
	                                defacementDobj.setOff_cd(offCode);
	                                defaceImpl.insertIntoDefacement(tmg, defacementDobj, status_dobj.getRegn_no(), userStateCode, offCode, empCode);
	                            }
	                        }
	                        sql = "Delete From " + TableList.VA_STATUS + " WHERE appl_no=? and pur_cd=?";
	                        ps = tmg.prepareStatement(sql);
	                        ps.setString(1, status_dobj.getAppl_no());
	                        ps.setInt(2, status_dobj.getPur_cd());
	                        vaStatusUpdated = ps.executeUpdate();
	                       // ApplicationInwardImplementation applicationInwardImpl = new ApplicationInwardImplementation();
	                        boolean isOnlineAppl = applicationInwardImpl.isOnlineApplication(status_dobj.getAppl_no(), status_dobj.getPur_cd());
	                        if (isOnlineAppl) {
	                          
	                            

	                            applicationInwardImpl.updateApprovedStatusForOnlineAppl(tmg, status_dobj, clientIpAddress);

	                            sql = "INSERT INTO " + TableList.VHA_STATUS_APPL
	                                    + " SELECT state_cd, off_cd, appl_no, pur_cd, flow_slno, file_movement_slno,"
	                                    + "       action_cd, seat_cd, cntr_id, status, office_remark, public_remark,"
	                                    + "       file_movement_type, ? as emp_cd, op_dt, moved_from_online, current_timestamp as moved_on"
	                                    + "  FROM " + TableList.VA_STATUS_APPL + " where appl_no=? and pur_cd=? ";
	                            ps = tmg.prepareStatement(sql);
	                            ps.setLong(1, Long.parseLong(empCode));
	                            ps.setString(2, status_dobj.getAppl_no());
	                            ps.setInt(3, status_dobj.getPur_cd());
	                            ps.executeUpdate();

	                            sql = "Delete FROM " + TableList.VA_STATUS_APPL + " WHERE appl_no=? and pur_cd=?";
	                            ps = tmg.prepareStatement(sql);
	                            ps.setString(1, status_dobj.getAppl_no());
	                            ps.setInt(2, status_dobj.getPur_cd());
	                            ps.executeUpdate();
	                        }
	                    }

	                    if (vhaStatusUpdated == 0 || (vaStatusUpdated == 0 && (status_dobj.getPur_cd() != TableConstants.VM_TRANSACTION_MAST_HPC && status_dobj.getPur_cd() != TableConstants.VM_DUPLICATE_TO_TAX_CARD))) {
	                        LOGGER.info("File-Flow-" + status_dobj.getAppl_no() + "-" + status_dobj.getPur_cd() + "-" + status_dobj.getAction_cd() + "-" + status_dobj.getStatus() + "-" + status_dobj.getEntry_status());
	                        throw new VahanException("Problem in File Movement for Appl No-" + status_dobj.getAppl_no() + ",Please go to home page and try again.");
	                    }
	                    if (status_dobj.getAction_cd() == 0 && status_dobj.getStatus().equalsIgnoreCase(TableConstants.STATUS_REVERT)) {
	                        throw new VahanException("Please Press Button Only Once for Appl No-" + status_dobj.getAppl_no());
	                    }
	                }

	            }
	        } catch (VahanException ve) {
	            throw ve;
	        } catch (SQLException sqle) {
	            LOGGER.error(sqle.toString() + " " + sqle.getStackTrace()[0]);
	            LOGGER.info("File-Flow-" + status_dobj.getAppl_no() + "-" + status_dobj.getPur_cd() + "-" + status_dobj.getAction_cd() + "-" + status_dobj.getStatus() + "-" + status_dobj.getEntry_status());
	            throw new VahanException(TableConstants.SomthingWentWrong);
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException(TableConstants.SomthingWentWrong);
	        }
	    }
	  public static void updateRtoOpenDateInVtFaceLessService(TransactionManagerOne tmgr, String applNo, String stateCode, int offCd) throws SQLException, VahanException, Exception {
	        String sql = "select * from " + TableList.VT_FACELESS_SERVICE + " where appl_no = ? and state_cd = ? and rto_open_dt is null";
	        PreparedStatement ps = tmgr.prepareStatement(sql);
	        ps.setString(1, applNo);
	        ps.setString(2, stateCode);
	        RowSet rs = tmgr.fetchDetachedRowSet_No_release();
	        if (rs.next()) {
	            sql = "update " + TableList.VT_FACELESS_SERVICE + " set rto_open_dt=current_timestamp "
	                    + " where appl_no=? and state_cd = ?";

	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, applNo);
	            ps.setString(2, stateCode);
	            ServerUtility.validateQueryResult(tmgr, ps.executeUpdate(), ps);
	        }
	    }
	  public static void validateQueryResult(TransactionManagerOne tmgr, int count, PreparedStatement ps) throws VahanException {
	        if (count > 0) {
	        } else {
	            LOGGER.error("Tmgr:" + tmgr.getWhereiam() + ",Error in Query:" + ps);
	            throw new VahanException("There is some problem while processing your request, please try again or contact Administrator with Transaction details.");
	        }
	    }
	  public  String getVahanPgiUrl(String Conn_Type) {
	        String sql = null;
	        PreparedStatement ps = null;
	     //   TransactionManagerOne tmgr = null;
	        String vahan_url = "";

	        try {
	          //  tmgr = new TransactionManagerOne("inside getVahanPgiUrl of ServerUtil");
	            sql = "select conn_dblink from tm_dblink_list where conn_type = ? ";
	            ps = tmg.prepareStatement(sql);
	            ps.setString(1, Conn_Type);
	            RowSet rs = tmg.fetchDetachedRowSet();
	            if (rs.next()) {
	                vahan_url = rs.getString("conn_dblink");
	            }
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        } finally {
	            try {
	                if (tmg != null) {
	                    tmg.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return vahan_url;
	    }
	  
	
	  public  Date getVahan4StartDate(String stateCdTo, int offCdTo, String userCategory) {
	        Date vahan4StrtDt = null;
	       // TransactionManager tmgr = null;
	        String sql = "";
	        if (userCategory.equals(TableConstants.USER_CATG_STATE_ADMIN)) {//forstateadmin
	            sql = "select min(vow4) as vow4 from " + TableList.TM_OFFICE + " where state_cd = ? ";
	        } else {
	            sql = "select vow4 from " + TableList.TM_OFFICE + " where state_cd = ? and off_cd = ? ";
	        }

	        try {
	        //    tmgr = new TransactionManager("getVahan4StartDate");
	            PreparedStatement ps = tmg.prepareStatement(sql);
	            ps.setString(1, stateCdTo);
	            if (!userCategory.equals(TableConstants.USER_CATG_STATE_ADMIN)) {//forstateadmin
	                ps.setInt(2, offCdTo);
	            }
	            RowSet rs = tmg.fetchDetachedRowSet();
	            if (rs.next()) {
	                vahan4StrtDt = rs.getDate("vow4");
	            }
	        } catch (SQLException e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        } catch (Exception ex) {
	            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	        } finally {
	            try {
	                if (tmg != null) {
	                    tmg.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return vahan4StrtDt;
	    }
	  public  Boolean getRegisteredDealerInfo(String stateCode, int offCode, String dealerCd) {
	        PreparedStatement ps = null;
	        RowSet rs;
	      //  TransactionManager tmgr = null;
	        String sql = null;
	        boolean status = false;
	        try {
	       //     tmgr = new TransactionManager("getRegisteredDealerInfo");
	            sql = "Select * from " + TableList.TM_USER_PERMISSIONS + " Where state_cd = ? and assigned_office = ? and dealer_cd = ?";
	            ps = tmg.prepareStatement(sql);
	            ps.setString(1, stateCode);
	            ps.setString(2, String.valueOf(offCode));
	            ps.setString(3, dealerCd);
	            rs = tmg.fetchDetachedRowSet();
	            if (rs.next()) {
	                status = true;
	            }
	        } catch (SQLException e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        } finally {
	            try {
	                if (tmg != null) {
	                    tmg.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return status;
	    }
	  public  String getDealerTradeCertificateDetails(String dealerCd, String vchCatg, String stateCd, TmConfigurationDobj tmConfig)
			  //throws VahanException {
	  {
		  
		  
	        PreparedStatement ps = null;
	      //  TransactionManagerReadOnly tmgr = null;
	        Date validUptoTradeCert = null;
	        Date validUptoDealerValidity = null;
	        String sql = null;
	        RowSet rs = null;
	        try {
	            if (tmConfig != null && tmConfig.getTmTradeCertConfigDobj() != null) {
	                if (tmConfig.isConsiderTradeCert()) {
	                   // tmgr = new TransactionManagerReadOnly("getDealerTradeCertificateDetails");
	                    Date currentDate = new Date();
	                    if (!CommonUtils.isNullOrBlank(vchCatg)) {
	                        if (tmConfig.getTmTradeCertConfigDobj().isCmvrVchCatgApplicable()) {
	                            sql = "select b.valid_upto from " + TableList.VM_TRADE_VCH_CATG_MAPPING + " a \n"
	                                    + " inner join " + TableList.VT_TRADE_CERTIFICATE + " b on b.vch_catg = any(string_to_array(a.v4_veh_catg,','))\n"
	                                    + " where  b.dealer_cd = ? and b.state_cd = ? and ? = any(string_to_array(a.v4_veh_catg,',')) order by 1 desc";
	                        } else {
	                            sql = "select no_of_vch,valid_upto,no_of_vch_used from " + TableList.VT_TRADE_CERTIFICATE + " where dealer_cd = ? and state_cd = ? and vch_catg = ? order by valid_upto desc";
	                        }
	                    } else {
	                        sql = "select no_of_vch,valid_upto,no_of_vch_used from " + TableList.VT_TRADE_CERTIFICATE + " where dealer_cd = ? and state_cd = ? order by valid_upto desc";
	                    }
	                    ps = tmgr.prepareStatement(sql);
	                    ps.setString(1, dealerCd);
	                    ps.setString(2, stateCd);
	                    if (!CommonUtils.isNullOrBlank(vchCatg)) {
	                        ps.setString(3, vchCatg);
	                    }
	                    rs = tmgr.fetchDetachedRowSet();
	                    if (rs.next()) {
	                        validUptoTradeCert = new Date(rs.getDate("valid_upto").getTime());
	                    }
	                    if (validUptoTradeCert != null) {
	                        if (DateUtils.compareDates(currentDate, validUptoTradeCert) == 2) {
	                            if (!CommonUtils.isNullOrBlank(vchCatg)) {
	                                return "Trade Certificate has been expired for the Vehicle Category " + masterTableFiller.loadMasterTables().VM_VCH_CATG.getDesc(vchCatg) + " . Last Validity was " + DateUtil.parseDateToString(validUptoTradeCert);
	                            } else {
	                                return TableConstants.TRADE_CERT_EXPIRED + DateUtil.parseDateToString(validUptoTradeCert);
	                            }
	                        }
	                    } else {
	                        if (!CommonUtils.isNullOrBlank(vchCatg)) {
	                            return "Trade Certificate details not found for the Vehicle Category " + masterTableFiller.loadMasterTables().VM_VCH_CATG.getDesc(vchCatg) + ".";
	                        } else {
	                            return "Trade Certificate has been expired, Kindly Renew Trade Certificate.";
	                        }
	                    }
	                }
	                if (tmConfig.getTmConfigDealerDobj() != null && tmConfig.getTmConfigDealerDobj().isDealerValidityRequired()) {
	                    tmgr = new TransactionManagerReadOnly("getDealerTradeCertificateDetails");
	                    sql = "select valid_upto from " + TableList.VM_DEALER_MAST + " where dealer_cd = ? and state_cd = ? ";
	                    ps = tmgr.prepareStatement(sql);
	                    ps.setString(1, dealerCd);
	                    ps.setString(2, stateCd);
	                    rs = tmgr.fetchDetachedRowSet();
	                    if (rs.next()) {
	                        if (rs.getDate("valid_upto") != null) {
	                            validUptoDealerValidity = new Date(rs.getDate("valid_upto").getTime());
	                            if (DateUtils.compareDates(new Date(), validUptoDealerValidity) == 2) {
	                                return TableConstants.DEALER_VALIDITY_EXPIRED + DateUtil.parseDateToString(validUptoDealerValidity);
	                            }
	                        } else {
	                            return "Dealer Validity has been expired, Please contact to respective Office Admin to update the validity.";
	                        }
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return null;
	    }
	  
	  public  String getOfficeName(int offCode, String stateCode) {
	        String officeName = "";
	   //     TransactionManager tmgr = null;
	        String ChasiSQL = "select off_name from tm_office where off_cd=? and state_cd = ?";
	        try {
	        //    tmgr = new TransactionManager("getOfficeName");
	            PreparedStatement ps = tmgr.prepareStatement(ChasiSQL);
	            ps.setInt(1, offCode);
	            ps.setString(2, stateCode);
	            RowSet rs = tmgr.fetchDetachedRowSet();
	            if (rs.next()) {
	                officeName = rs.getString("off_name");
	            }
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return officeName;
	    }
	  public  NocDobj getChasiNoExist(String chassisNo) throws VahanException {
	        String chasi_no = null;
	       // TransactionManager tmgr = null;
	        NocDobj nocDobj = null;
	        String sql = "select b.state_cd,b.off_cd, a.state_to,a.off_to,b.status,a.noc_dt from  " + TableList.VT_NOC + " a left join " + TableList.VT_OWNER + " b on b.regn_no=a.regn_no and b.state_cd=a.state_cd and b.off_cd=a.off_cd where b.chasi_no = ? order by noc_dt desc limit 1";
	        try {
	        //    tmgr = new TransactionManager("getChasiNoExist");
	            PreparedStatement ps = tmg.prepareStatement(sql);
	            ps.setString(1, chassisNo);
	            RowSet rs = tmg.fetchDetachedRowSet();
	            if (rs.next()) {
	                nocDobj = new NocDobj();
	                nocDobj.setState_cd(rs.getString("state_cd"));
	                nocDobj.setOff_cd(rs.getInt("off_cd"));
	                nocDobj.setState_to(rs.getString("state_to"));
	                nocDobj.setOff_to(rs.getInt("off_to"));
	                nocDobj.setVt_owner_status(rs.getString("status"));
	                nocDobj.setNoc_dt(rs.getDate("noc_dt"));
	            }
	        } catch (SQLException e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        } catch (Exception ex) {
	            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return nocDobj;
	    }
	  public  NocDobj getNocVerifiedData(String regnNo, String chassiNo,String userStateCode,int offcode) throws VahanException {
	      //  TransactionManager tmgr = null;
	        PreparedStatement ps = null;
	        RowSet rs = null;
	        String query = "";
	        NocDobj nocVerifiedData = null;

	        if (regnNo != null) {
	            regnNo = regnNo.toUpperCase();
	        }
	        if (chassiNo != null) {
	            chassiNo = chassiNo.toUpperCase();
	        }
	        try {
	        //    tmgr = new TransactionManager("getNocEndorsementData");

	            query = "select * from  " + TableList.VT_NOC_VERIFICATION
	                    + " where regn_no = ? and state_cd=? and off_cd=? order by noc_dt,entered_on desc limit 1";
	            ps = tmgr.prepareStatement(query);
	            ps.setString(1, regnNo);
	            ps.setString(2,userStateCode );
	            		//Util.getUserStateCode());
	            ps.setInt(3,offcode);
	            		//Util.getSelectedSeat().getOff_cd());
	            rs = tmgr.fetchDetachedRowSet_No_release();
	            if (rs.next()) {
	                nocVerifiedData = new NocDobj();
	                nocVerifiedData.setRegn_no(rs.getString("regn_no"));
	                nocVerifiedData.setState_from(rs.getString("from_state_cd"));
	                nocVerifiedData.setOff_from(rs.getInt("from_off_cd"));
	                nocVerifiedData.setNoc_dt(rs.getDate("noc_dt"));
	                nocVerifiedData.setNoc_no(rs.getString("noc_no"));
	                nocVerifiedData.setNcrb_ref(rs.getString("ncrb_ref"));
	            } else {
	                query = "select * from  " + TableList.VT_NOC_VERIFICATION
	                        + " where chasi_no  = ? and state_cd=? and off_cd=? order by noc_dt desc limit 1";
	                ps = tmgr.prepareStatement(query);
	                ps.setString(1, chassiNo);
	                ps.setString(2, userStateCode);
	                		//Util.getUserStateCode());
	                ps.setInt(3,offcode);
	                		//Util.getSelectedSeat().getOff_cd());
	                rs = tmgr.fetchDetachedRowSet();
	                if (rs.next()) {
	                    nocVerifiedData = new NocDobj();
	                    nocVerifiedData.setRegn_no(rs.getString("regn_no"));
	                    nocVerifiedData.setState_from(rs.getString("from_state_cd"));
	                    nocVerifiedData.setOff_from(rs.getInt("from_off_cd"));
	                    nocVerifiedData.setNoc_dt(rs.getDate("noc_dt"));
	                    nocVerifiedData.setNoc_no(rs.getString("noc_no"));
	                    nocVerifiedData.setNcrb_ref(rs.getString("ncrb_ref"));
	                }
	            }

	        } catch (Exception ex) {
	            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return nocVerifiedData;
	    }
	  public  String getUniqueApplNo(TransactionManagerOne tmgr, String stateCd) throws VahanException {
	        String applNo = null;
	        try {
	            if (stateCd == null || stateCd.isEmpty() || stateCd.length() > 2 || stateCd.length() < 2) {
	                throw new VahanException("Something went wrong, please try again.");
	            }
	            String strSQL = "SELECT ? || to_char(CURRENT_DATE,'YYMMDD') || lpad((floor(random() * 9) + 1)::varchar || nextval('appl_no_v4_seq')::varchar, 8, '0') AS appl_no";

	            PreparedStatement psmt = tmgr.prepareStatement(strSQL);
	            psmt.setString(1, stateCd);
	            try (RowSet rs = tmgr.fetchDetachedRowSet_No_release()) {
	                if (rs.next()) {
	                    applNo = rs.getString("appl_no");
	                    strSQL = "INSERT INTO vt_unique_appl_nos VALUES (?, current_timestamp)";
	                    psmt = tmgr.prepareStatement(strSQL);
	                    psmt.setString(1, applNo);
	                    psmt.executeUpdate();
	                }
	            }
	            psmt.close();
	            psmt = null;
	        } catch (SQLException e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Something went wrong, please try again.");
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Something went wrong, please try again.");
	        }
	        return applNo;
	    }

	    /*
	     * Author: Kartikey Singh
	     * To save ApplNo at the end of transaction from Rest     
	     */
	    public static String saveUniqueApplNo(TransactionManagerOne tmgr, String applNo) throws VahanException {
	        try {
	            String strSQL = "INSERT INTO vt_unique_appl_nos VALUES (?, current_timestamp)";
	            try (PreparedStatement psmt = tmgr.prepareStatement(strSQL)) {
	                psmt.setString(1, applNo);
	                psmt.executeUpdate();
	            }
	        } catch (SQLException e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Something went wrong, please try again.");
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Something went wrong, please try again.");
	        }
	        return applNo;
	    }
	    
	    public  void insertIntoVhaChangedData(TransactionManagerOne tmgr, String appl_no, String changedData, String empCode,
	            String userStateCode, int officeCode, boolean allowFacelessService,String userCategory) throws VahanException {
	        PreparedStatement ps = null;
	        String sql = null;

	        try {
	            //for saving the data into table those are changed by the user
	            if (changedData != null && !changedData.equals("")) {
	                sql = "INSERT INTO VHA_CHANGED_DATA (APPL_NO,EMP_CD,CHANGED_DATA,OP_DT,STATE_CD,OFF_CD) "
	                        + " VALUES(?,?,?,CURRENT_TIMESTAMP,?,?)";
	                ps = tmgr.prepareStatement(sql);
	                ps.setString(1, appl_no);
	                ps.setLong(2, Long.parseLong(empCode));
	                if (changedData.length() <= 2000) {
	                    ps.setString(3, changedData);
	                } else {
	                    ps.setString(3, changedData.substring(0, 2000));
	                }
	                ps.setString(4, userStateCode);
	                ps.setInt(5, officeCode);
	                ps.executeUpdate();

	                if (allowFacelessService) {
	                    if (appl_no != null && (officeCode != 0|| userCategory.equals(TableConstants.USER_CATG_STATE_ADMIN))) {
	                        ServerUtility.updateRtoOpenDateInVtFaceLessService(tmgr, appl_no, userStateCode, officeCode);
	                    } else {
	                        throw new Exception("Problem in getting office code/applNo");
	                    }
	                }
	            }
	        } catch (SQLException sqle) {
	            LOGGER.error(sqle.toString() + " " + sqle.getStackTrace()[0]);
	            throw new VahanException("Something Went Wrong during Saving Changed Data by User, Please Contact to the System Administrator.");
	        } catch (Exception ex) {
	            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	            throw new VahanException("Something Went Wrong during Saving Changed Data by User, Please Contact to the System Administrator.");
	        }

	    }
	    public  void saveUpdateOTPVerifyDetails(TransactionManagerOne tmgr, Owner_dobj owner_dobj) throws VahanException {
	        PreparedStatement ps = null;
	        String sql = "";
	        RowSet rs = null;
	        try {
	            if (owner_dobj != null && owner_dobj.getOwner_identity() != null) {
	                sql = "select appl_no from " + TableList.VT_OTP_VERIFY + " where state_cd = ? and off_cd=? and appl_no=?";
	                ps = tmgr.prepareStatement(sql);
	                ps.setString(1, owner_dobj.getState_cd());
	                ps.setInt(2, owner_dobj.getOff_cd());
	                ps.setString(3, owner_dobj.getAppl_no());
	                rs = tmgr.fetchDetachedRowSet_No_release();
	                if (rs.next()) {
	                    sql = "UPDATE " + TableList.VT_OTP_VERIFY + " SET regn_no=? WHERE state_cd = ? and off_cd=? and appl_no=?";
	                    ps = tmgr.prepareStatement(sql);
	                    ps.setString(1, owner_dobj.getRegn_no());
	                    ps.setString(2, owner_dobj.getState_cd());
	                    ps.setInt(3, owner_dobj.getOff_cd());
	                    ps.setString(4, owner_dobj.getAppl_no());
	                    ps.executeUpdate();
	                } else {
	                    sql = "INSERT INTO " + TableList.VT_OTP_VERIFY + "(state_cd, off_cd, appl_no, regn_no, mobile_no, mobile_verified_on, email_id, \n"
	                            + " email_verified_on, op_dt) VALUES (?, ?, ?, ?, ?, current_timestamp, ?, ?, current_timestamp);";
	                    ps = tmgr.prepareStatement(sql);
	                    int i = 1;
	                    ps.setString(i++, owner_dobj.getState_cd());
	                    ps.setInt(i++, owner_dobj.getOff_cd());
	                    ps.setString(i++, owner_dobj.getAppl_no());
	                    ps.setString(i++, owner_dobj.getRegn_no());
	                    if (owner_dobj.getOwner_identity().getMobile_no() != 0L) {
	                        ps.setLong(i++, owner_dobj.getOwner_identity().getMobile_no());
	                    } else {
	                        throw new VahanException("Mobile no. can't be blank.");
	                    }
	                    if (!CommonUtils.isNullOrBlank(owner_dobj.getOwner_identity().getEmail_id())) {
	                        ps.setString(i++, owner_dobj.getOwner_identity().getEmail_id());
	                        ps.setTimestamp(i++, new Timestamp(new Date().getTime()));
	                    } else {
	                        ps.setString(i++, owner_dobj.getOwner_identity().getEmail_id());
	                        ps.setNull(i++, java.sql.Types.DATE);
	                    }
	                    ps.executeUpdate();
	                }
	            }
	        } catch (VahanException ve) {
	            throw ve;
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Problem in saving OTP verification details.");
	        }
	    }
	    
	    public  void checkChasiNoExist(String chasiNo) throws VahanException {
	        String NocNotIssuedChassisNoExist = getChassisNoExist(chasiNo);
	        if (NocNotIssuedChassisNoExist != null && NocNotIssuedChassisNoExist.length() > 0 && !NocNotIssuedChassisNoExist.isEmpty()) {
	            throw new VahanException("Chassis No already exist - " + NocNotIssuedChassisNoExist);
	        }
	    }
	    
	    public  String getChassisNoExist(String chassisNo) throws VahanException {
	        String chasiNoExist = "";
	      //  TransactionManager tmgr = null;
	        String sql = "select state_cd, off_cd, regn_no, chasi_no from " + TableList.VT_OWNER + " where chasi_no = ? and status NOT IN ('N','C') ";
	        try {
	        //    tmgr = new TransactionManager("getChasiNoExist");
	            PreparedStatement ps = tmg.prepareStatement(sql);
	            ps.setString(1, chassisNo);
	            RowSet rs = tmg.fetchDetachedRowSet();
	            while (rs.next()) {
	                chasiNoExist = chasiNoExist + rs.getString("regn_no") + "[" + rs.getString("state_cd") + "-" + rs.getInt("off_cd") + "],";
	            }
	        } catch (Exception ex) {
	            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	            throw new VahanException("Error in Getting Chassis Details");
	        } finally {
	            try {
	                if (tmg != null) {
	                    tmg.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return chasiNoExist;
	    }
	    public  String getAvailablePrefixSeries(VehicleParameters vehParameters, String userStateCode, int offCode) throws VahanException {
	        PreparedStatement ps = null;
	       // TransactionManager tmgr = null;
	        String sql = null;
	        boolean isSeriesAvailable = false;
	        String stateCode;
	        int OffCode;
	        String retValue = "";
	        boolean isStopProcess = false;
	        try {
	            stateCode = userStateCode;
	            OffCode = offCode;
	          //  tmgr = new TransactionManager("getAvailableRegnNoList");
	            sql = "select * from vm_regn_gen_action  where action_cd in (99991,12302) and state_cd=?";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, stateCode);
	            RowSet rs = tmgr.fetchDetachedRowSet_No_release();
	            if (rs.next()) {
	                isStopProcess = true;
	            }

	            sql = "select distinct a.criteria_formula, a.upper_range_no, a.running_no, a.prefix_series,no_gen_type, \n"
	                    + "  (a.upper_range_no - a.running_no) as diff,COALESCE(COALESCE(a.next_prefix_series,b.prefix_series),'') as next_prefix_series,a.series_id \n"
	                    + "  from vm_regn_series a \n"
	                    + "  left outer join vm_regn_series_future b on b.state_cd=a.state_cd and b.off_cd=a.off_cd and b.series_id=a.series_id \n"
	                    + "  where a.state_cd = ? and a.off_cd = ?"
	                    + "  order by 1";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, stateCode);
	            ps.setInt(2, OffCode);
	            rs = tmgr.fetchDetachedRowSet_No_release();
	            while (rs.next()) {
	                if (formulaUtilities.isCondition(formulaUtilities.replaceTagValues(rs.getString("criteria_formula"), vehParameters), "getAvailableRegnNoList")) {
	                    isSeriesAvailable = true;
	                    if (rs.getString("no_gen_type").equals(TableConstants.RANDOM_NUMBER_STATUS_M)) {
	                        sql = "select count(1) as total from vm_regn_available where status = ? and state_cd = ? and off_cd = ? and series_id = ? ";
	                        ps = tmgr.prepareStatement(sql);
	                        ps.setString(1, TableConstants.RANDOM_NUMBER_STATUS_A);
	                        ps.setString(2, stateCode);
	                        ps.setInt(3, OffCode);
	                        ps.setInt(4, rs.getInt("series_id"));
	                        RowSet rs1 = tmgr.fetchDetachedRowSet();
	                        if (rs1.next()) {
	                            int total = rs.getInt("diff") + rs1.getInt("total");
	                            if (total < 1) {
	                                retValue = TableConstants.SERIES_EXHAUST_MESSAGE;
	                                isSeriesAvailable = false;//error
	                            } else {
	                                retValue = rs.getString("prefix_series");//allow
	                                break;
	                            }
	                        }
	                    }

	                    if (rs.getInt("upper_range_no") < rs.getInt("running_no") && (rs.getString("next_prefix_series") == null || rs.getString("next_prefix_series").isEmpty())) {
	                        retValue = TableConstants.SERIES_EXHAUST_MESSAGE;
	                        isSeriesAvailable = false;
	                    } else {
	                        if (rs.getInt("diff") < 0) {
	                            retValue = TableConstants.SERIES_EXHAUST_MESSAGE;
	                            isSeriesAvailable = false;
	                        } else {
	                            isSeriesAvailable = true;
	                            if (rs.getInt("upper_range_no") < rs.getInt("running_no")) {
	                                retValue = rs.getString("next_prefix_series");
	                            } else {
	                                retValue = rs.getString("prefix_series");
	                            }
	                        }
	                    }
	                }
	            }

	            if (!isStopProcess && retValue.equals(TableConstants.SERIES_EXHAUST_MESSAGE)) {
	                retValue = "";
	            }

	        } catch (SQLException ex) {
	            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return retValue;
	    }
	    public  void deleteFromTable(TransactionManagerOne tmgr, String regnNo, String applNo, String tableName) throws SQLException {
	        PreparedStatement ps = null;
	        String sql = null;
	        String param = null;

	        if (regnNo != null && regnNo.trim().length() > 0 && !regnNo.equalsIgnoreCase("NEW")) {
	            param = regnNo.toUpperCase().trim();
	            sql = "DELETE FROM " + tableName + " WHERE regn_no=?";
	        } else if (applNo != null && applNo.trim().length() > 0) {
	            param = applNo.toUpperCase().trim();
	            sql = "DELETE FROM " + tableName + " WHERE appl_no=?";
	        }

	        if (param != null && sql != null) {
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, param);
	            ps.executeUpdate();
	        }
	    }
	    public  void deleteFromTableByChassis(TransactionManagerOne tmgr, String Chassis_no, String tableName) throws SQLException {
	        PreparedStatement ps = null;
	        String sql = null;
	        String param = null;

	        if (Chassis_no != null && Chassis_no.trim().length() > 0) {
	            param = Chassis_no.toUpperCase().trim();
	            sql = "DELETE FROM " + tableName + " WHERE chasi_no=?";
	        }

	        ps = tmgr.prepareStatement(sql);
	        ps.setString(1, param);
	        ps.executeUpdate();

	    }
	    
	    public static void insertIntoVhaTable(TransactionManagerOne tmgr, String applNo, String empCode, String tableNameVha, String tableNameVa) throws SQLException {
	        PreparedStatement ps = null;
	        String sql = null;
	        if (applNo != null && applNo.trim().length() > 0) {
	            applNo = applNo.toUpperCase().trim();
	            sql = " INSERT INTO " + tableNameVha
	                    + "  SELECT current_timestamp as moved_on, ? as moved_by, a.* FROM " + tableNameVa
	                    + "  a WHERE a.appl_no=?";
	        }

	        ps = tmgr.prepareStatement(sql);
	        ps.setString(1, empCode);
	        ps.setString(2, applNo);
	        ps.executeUpdate();

	    }

	    public  String getStateNameByStateCode(String stateCode) {
	       // TransactionManagerReadOnly tmgr = null;
	        PreparedStatement ps = null;
	        String stateName = null;
	        String sql = null;

	        try {
	          //  tmgr = new TransactionManagerReadOnly("getStateName");
	            sql = "select descr as state_name from tm_state where state_code = ? ";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, stateCode);
	            RowSet rs = tmgr.fetchDetachedRowSet();
	            if (rs.next()) {
	                stateName = rs.getString("state_name");
	            }
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return stateName;
	    }
	    public String isNewRegnNotAllowed(VehicleParameters vehParam, String userStateCode, int officeCode) {
	        String sql = "select new_regn_not_allowed,new_regn_not_allowed_msg from vm_smart_card_hsrp where state_cd = ? and off_cd = ?";
	       // TransactionManager tmgr = null;
	        String msg = "";
	        try {
	         //   tmgr = new TransactionManager("getCriteriaForParticularOffice");
	            PreparedStatement ps = tmgr.prepareStatement(sql);
	            ps.setString(1, userStateCode);
	            ps.setInt(2, officeCode);
	            RowSet rs = tmgr.fetchDetachedRowSet();
	            if (rs.next()) {
	                if (formulaUtilities.isCondition(formulaUtilities.replaceTagValues(rs.getString("new_regn_not_allowed"), vehParam), "isNewRegnNotAllowed")) {
	                    msg = rs.getString("new_regn_not_allowed_msg");
	                }
	            }
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return msg;
	    }
	    public  boolean validateVehicleNorms(Owner_dobj ownerDobj, int purCd, VehicleParameters vehParameters, TmConfigurationDealerDobj tmConfig) throws VahanException {
	        try {
	            if (ownerDobj != null && ownerDobj.getNorms() != 0 && ownerDobj.getPurchase_dt() != null && vehParameters != null && tmConfig != null
	                    && ownerDobj.getVh_class() != 0 && purCd != 0 && !CommonUtils.isNullOrBlank(ownerDobj.getRegn_type())&& !CommonUtils.isNullOrBlank(vehParameters.getAPPL_DATE())) {
	                if (TableConstants.VM_REGN_TYPE_OTHER_STATE_VEHICLE.equals(ownerDobj.getRegn_type()) || TableConstants.VM_REGN_TYPE_OTHER_DISTRICT_VEHICLE.equals(ownerDobj.getRegn_type())
	                        || TableConstants.VM_REGN_TYPE_SCRAPPED.equals(ownerDobj.getRegn_type()) || TableConstants.VM_REGN_TYPE_EXARMY.equals(ownerDobj.getRegn_type())
	                        || !(purCd == TableConstants.VM_TRANSACTION_MAST_NEW_VEHICLE || purCd == TableConstants.VM_TRANSACTION_MAST_DEALER_NEW_VEHICLE
	                        || purCd == TableConstants.VM_TRANSACTION_MAST_DEALER_TEMP_VEHICLE || purCd == TableConstants.VM_TRANSACTION_MAST_TEMP_REG)) {
	                    return true;
	                }
	                if (",15,17,19,20,21,22,23,".contains("," + ownerDobj.getNorms() + ",")) {
//	                    if ((ownerDobj.getNorms() != 15 && "Y".equals(ownerDobj.getImported_vch())) || (ownerDobj.getNorms() == 15)) {
//	                        return true;
//	                    }
	                    return true;
	                } else if (",5,6,12,13,14,18,".contains("," + ownerDobj.getNorms() + ",")) {
	                    if (",8,9,10,11,12,13,14,20,21,22,24,25,26,27,29,62,63,80,81,83,88,90,92,".contains("," + ownerDobj.getVh_class() + ",") || (ownerDobj.getPurchase_dt().before(DateUtil.parseDate("01-04-2017")) && purCd == TableConstants.ADMIN_OWNER_DATA_CHANGE)) {
	                        return true;
	                    }
//	                } else if (",4,10,16,".contains("," + ownerDobj.getNorms() + ",") && isCondition(replaceTagValues(tmConfig.getNormsConditionFormulas(), vehParameters), "validateVehicleNorms()") && new Date().before(DateUtil.parseDate("01-05-2020"))) {
//	                    return true;
	                } else if (ownerDobj.getPurchase_dt().before(DateUtil.parseDate("01-04-2017")) && purCd == TableConstants.ADMIN_OWNER_DATA_CHANGE) {
	                    return true;
	                } else if (ownerDobj.getNorms() == 99) {
	                    if (",4,99,".contains("," + ownerDobj.getFuel() + ",")) {
	                        return true;
	                    }
	                    
	                } else if (TableConstants.VM_REGN_TYPE_EXARMY.equals(ownerDobj.getRegn_type()) || TableConstants.VM_REGN_TYPE_CD.equals(ownerDobj.getRegn_type()) || ownerDobj.getRegn_type().equalsIgnoreCase(TableConstants.VM_REGN_TYPE_CONFISCATED_AUCTION)) {
	                    if (ownerDobj.getAuctionDobj() != null && ownerDobj.getAuctionDobj().getRegnNo() != null) {
	                        if (!ownerDobj.getAuctionDobj().getRegnNo().equals("NEW") || (ownerDobj.getAuctionDobj().getRegnNo().equals("NEW") && checkChassisInVtOwnerTemp(ownerDobj.getChasi_no()))) {
	                            return true;
	                        }
	                    }
	                    if (ownerDobj.getManu_mon() != 0 && ownerDobj.getManu_yr() != 0) {
	                        String month = (ownerDobj.getManu_mon() + "").length() < 2 ? "0" + ownerDobj.getManu_mon() : ownerDobj.getManu_mon() + "";
	                        if (Integer.parseInt(ownerDobj.getManu_yr() + "" + month) < 202001) {
	                            return true;
	                        } else {
	                            return false;
	                        }
	                    } else {
	                        throw new VahanException("Vehicle Manufacturing Month/Year should not be blank !!");
	                    }
	                } else if (",4,10,16,".contains("," + ownerDobj.getNorms() + ",")) {
	                    String reason = checkBSIVChassisAllowedForReg(ownerDobj.getChasi_no());
	                    if (!CommonUtils.isNullOrBlank(reason)) {
	                    	 if (ownerDobj.getPurchase_dt().before(DateUtil.parseDate("01-04-2020"))) {
	                        return true;
	                    	 } else {
	                             throw new VahanException("As per Supreme Court Order the Vehicle must be sold before 31-Mar-2020, so Purchase Date for this vehicle should be before 31-Mar-2020.");
	  
	                    }
	                }
	                    if (ownerDobj.getPurchase_dt().before(DateUtil.parseDate("01-04-2020"))) {
	                        if (DateUtil.parseDateFromYYYYMMDD(vehParameters.getAPPL_DATE()).before(DateUtil.parseDate("01-05-2020"))
	                                && ((TableConstants.VM_TRANSACTION_MAST_DEALER_TEMP_VEHICLE == purCd
	                                || TableConstants.VM_TRANSACTION_MAST_TEMP_REG == purCd)
	                                || (TableConstants.VM_REGN_TYPE_NEW.equals(ownerDobj.getRegn_type())
	                                && (TableConstants.VM_TRANSACTION_MAST_DEALER_NEW_VEHICLE == purCd
	                                || TableConstants.VM_TRANSACTION_MAST_NEW_VEHICLE == purCd)))) {
	                            int payPurCd = (purCd == TableConstants.VM_TRANSACTION_MAST_DEALER_TEMP_VEHICLE
	                                    || TableConstants.VM_TRANSACTION_MAST_TEMP_REG == purCd) ? TableConstants.VM_TRANSACTION_MAST_TEMP_REG : TableConstants.VM_TRANSACTION_MAST_NEW_VEHICLE;
	                            String isPayDone = offcorimpl.getRcptNoForFeePaidForPurpose(ownerDobj.getAppl_no(), payPurCd);
	                            if (!CommonUtils.isNullOrBlank(isPayDone)) {
	                                vehParameters.setISPAYMENTDONE("TRUE");
	            } else {
	            	
	            	 vehParameters.setISPAYMENTDONE("FALSE");
                }
                if (formulaUtilities.isCondition(formulaUtilities.replaceTagValues(tmConfig.getNormsConditionFormulas(), vehParameters), "validateVehicleNorms()")) {
                    return true;
                } else {
                    throw new VahanException("As per State Transport Department Order, only those BS-IV vehicles are allowed which have already paid Registration Fee / MV Tax.");
                }
            } else if ((TableConstants.VM_TRANSACTION_MAST_NEW_VEHICLE == purCd || purCd == TableConstants.VM_TRANSACTION_MAST_DEALER_NEW_VEHICLE) && TableConstants.VM_REGN_TYPE_TEMPORARY.equals(ownerDobj.getRegn_type())) {
                return true;
            }
        }
    }
} else {
	                throw new VahanException("Vehicle details not found to validate emission norms.");
	            }
	        } catch (VahanException e) {
	            LOGGER.error(e.toString() + " validateVehicleNorms " + e.getStackTrace()[0]);
	            throw e;
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " validateVehicleNorms " + e.getStackTrace()[0]);
	            throw new VahanException("Problem in validating vehicle emission Norms.");
	        }
	        return false;
	    }
	    public static boolean checkChassisInVtOwnerTemp(String chasiNo) throws VahanException {
	        String sql = null;
	        PreparedStatement ps = null;
	        RowSet rs;
	        TransactionManagerReadOnly tmgr = null;
	        boolean isChassisExist = false;
	        try {
	            if (!CommonUtils.isNullOrBlank(chasiNo)) {
	                tmgr = new TransactionManagerReadOnly("checkChassisInVtOwnerTemp");
	                sql = "SELECT chasi_no,appl_no,temp_regn_no,purpose  FROM " + TableList.VT_OWNER_TEMP + " WHERE chasi_no=? order by op_dt desc limit 1 ";
	                ps = tmgr.prepareStatement(sql);
	                ps.setString(1, chasiNo);
	                rs = tmgr.fetchDetachedRowSet();
	                if (rs.next()) {
	                    isChassisExist = true;
	                }
	            } else {
	                throw new VahanException("Chassis number found blank !!!");
	            }
	        } catch (VahanException e) {
	            throw e;
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Problem in validating  chassis number !!!");
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	                if (ps != null) {
	                    ps = null;
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return isChassisExist;
	    }
	    public  String checkBSIVChassisAllowedForReg(String chasiNo) throws VahanException {
	        String sql = null;
	        PreparedStatement ps = null;
	        RowSet rs;
	     //   TransactionManagerReadOnly tmgr = null;
	        try {
	            if (!CommonUtils.isNullOrBlank(chasiNo)) {
	             //   tmgr = new TransactionManagerReadOnly("checkBSIVChassisAllowedForReg");
	                sql = "select reason from " + TableList.VT_BSIV_CHASSIS_ALLOWED_BY_SC_ORDER + " WHERE chasi_no = ? and (new_regn_appl_no is null OR temp_regn_appl_no is null) ";
	                ps = tmgr.prepareStatement(sql);
	                ps.setString(1, chasiNo);
	                rs = tmgr.fetchDetachedRowSet();
	                if (rs.next()) {
	                    return rs.getString("reason");
	                }
	            } else {
	                throw new VahanException("Chassis number found blank !!!");
	            }
	        } catch (VahanException e) {
	            throw e;
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Problem in validating BS-IV chassis number !!!");
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	                if (ps != null) {
	                    ps = null;
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return null;
	    }
	    public  void saveVaOwnerDisclaimerDetails(Owner_dobj dobj, TransactionManagerOne tmgr, String empCd, int actionCd) throws VahanException {
	        PreparedStatement ps = null;
	        try {
	            if (dobj != null && !CommonUtils.isNullOrBlank(dobj.getRegn_type()) && ",4,10,16,".contains("," + dobj.getNorms() + ",") && (TableConstants.VM_REGN_TYPE_NEW.equals(dobj.getRegn_type()) || TableConstants.VM_REGN_TYPE_EXARMY.equals(dobj.getRegn_type())
	                    || TableConstants.VM_REGN_TYPE_TEMPORARY.equals(dobj.getRegn_type()) || TableConstants.VM_REGN_TYPE_CD.equals(dobj.getRegn_type()))) {
	                String sql = "INSERT INTO va_owner_disclaimer(state_cd, off_cd, appl_no, action_cd, emp_cd, op_dt)\n"
	                        + "    VALUES (?, ?, ?, ?, ?, current_timestamp);";
	                ps = tmgr.prepareStatement(sql);
	                ps.setString(1, dobj.getState_cd());
	                ps.setInt(2, dobj.getOff_cd());
	                ps.setString(3, dobj.getAppl_no());
	                ps.setInt(4, actionCd);
	                ps.setInt(5, Integer.parseInt(empCd));
	                ServerUtility.validateQueryResult(tmgr, ps.executeUpdate(), ps);
	            }
	        } catch (VahanException ve) {
	            throw ve;
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " saveVaOwnerDisclaimerDetails " + e.getStackTrace()[0]);
	            throw new VahanException("Problem in saving owner disclaimer.");
	        }
	    }
	    public  void validateMinInsuranceValidity(int insPeriodYears, int vhClass, String regnType, int purCd, int insType) throws VahanException {
	        if (Integer.parseInt(TableConstants.INS_TYPE_NA) != insType && (purCd == TableConstants.VM_TRANSACTION_MAST_DEALER_NEW_VEHICLE || purCd == TableConstants.VM_TRANSACTION_MAST_NEW_VEHICLE && (TableConstants.VM_REGN_TYPE_NEW.equals(regnType) || TableConstants.VM_REGN_TYPE_TEMPORARY.equals(regnType)))) {
	            if (vhClass > 0 && insPeriodYears > 0) {
	                String[] dataVhClass = masterTableFiller.loadMasterTables().VM_VH_CLASS.getRow(vhClass + "");
	                if (dataVhClass != null && dataVhClass.length > 0 && !CommonUtils.isNullOrBlank(dataVhClass[5]) && Integer.parseInt(dataVhClass[5]) > 0) {
	                    if (insType != TableConstants.INS_TYPE_THIRD_PARTY) {
	                        throw new VahanException("Please select Insurance Type as 'Third Party' only.");
	                    } else if (insPeriodYears < Integer.parseInt(dataVhClass[5])) {
	                        throw new VahanException("As per order dated 29-08-2018, the MoRTH has directed that the Third Party Insurance Cover of New Cars and Two Wheelers should mandatorily be for a period of Three Years and Five Years respectively. This may be taken and treated as a separate product. Please select Insurance Type as 'Third Party' and Insurance Period accordingly as per the Vehicle Class.");
	                    }
	                }
	            } else {
	                throw new VahanException("Invalid insurance data, please check insurance details !!!");
	            }
	        }
	    }
	    public  void insertDealerHSRPPendencyDetails(TransactionManagerOne tmgr, Owner_dobj dobj, String regnNo, TmConfigurationDobj tmConfDobj) throws VahanException {
	        PreparedStatement ps = null;
	        String sql = null;
	        RowSet rs = null;
	        boolean validForInsert = false;
	        try {
	            if (dobj != null && !CommonUtils.isNullOrBlank(dobj.getDealer_cd())) {
	                sql = "select regn_no from " + TableList.VA_DEALER_PENDENCY + " WHERE appl_no = ? ";
	                ps = tmgr.prepareStatement(sql);
	                ps.setString(1, dobj.getAppl_no());
	                rs = tmgr.fetchDetachedRowSet_No_release();
	                if (!rs.next()) {
	                    validForInsert = true;
	                }
	                if (tmConfDobj != null && !tmConfDobj.isRc_after_hsrp() && validForInsert) {
	                    String manuMonth = dobj.getManu_mon() < 10 ? "0" + String.valueOf(dobj.getManu_mon()) : String.valueOf(dobj.getManu_mon());
	                    String manuYearMonth = String.valueOf(dobj.getManu_yr()) + manuMonth;
	                    if (Integer.parseInt(manuYearMonth) >= TableConstants.CHECK_MANU_MONTH_YEAR_FOR_HSRP) {
	                        sql = "INSERT INTO " + TableList.VA_DEALER_PENDENCY + "(op_dt, dealer_cd, appl_no, regn_no, state_cd)\n"
	                                + " VALUES (current_timestamp, ?, ?, ?, ?)";
	                        ps = tmgr.prepareStatement(sql);
	                        ps.setString(1, dobj.getDealer_cd());
	                        ps.setString(2, dobj.getAppl_no());
	                        ps.setString(3, regnNo);
	                        ps.setString(4, dobj.getState_cd());
	                        ps.executeUpdate();
	                    }
	                } else if (validForInsert) {
	                    sql = "INSERT INTO " + TableList.VA_DEALER_PENDENCY + "(op_dt, dealer_cd, appl_no, regn_no, state_cd)\n"
	                            + " VALUES (current_timestamp, ?, ?, ?, ?)";
	                    ps = tmgr.prepareStatement(sql);
	                    ps.setString(1, dobj.getDealer_cd());
	                    ps.setString(2, dobj.getAppl_no());
	                    ps.setString(3, regnNo);
	                    ps.setString(4, dobj.getState_cd());
	                    ps.executeUpdate();
	                } else {
	                    sql = "UPDATE " + TableList.VA_DEALER_PENDENCY + " SET op_dt=current_timestamp, regn_no = ? WHERE appl_no= ? ";
	                    ps = tmgr.prepareStatement(sql);
	                    ps.setString(1, regnNo);
	                    ps.setString(2, dobj.getAppl_no());
	                    ps.executeUpdate();
	                }
	            } else {
	                throw new VahanException("Dealer Code is blank, Please go to the Home page then try again.");
	            }
	        } catch (VahanException ve) {
	            throw ve;
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Problem occurred during updating the dealer pendency details.");
	        }
	    }

	    public  int[] getInitialAction(TransactionManagerOne tmgr, String state_cd, int pur_cd, VehicleParameters parameters) throws VahanException {
	        PreparedStatement ps = null;
	        String sql = null;
	        int action_cd = 0;
	        int flowsr_no = 1;
	        int[] retArr = null;
	        try {

	            while (true) {
	                sql = "select flow_srno,action_cd,condition_formula from tm_purpose_action_flow "
	                        + " where pur_cd=? and state_cd=? and flow_srno=? order by 1";
	                ps = tmgr.prepareStatement(sql);
	                ps.setInt(1, pur_cd);
	                ps.setString(2, state_cd);
	                ps.setInt(3, flowsr_no);
	                RowSet rs = tmgr.fetchDetachedRowSet_No_release();

	                if (rs.next()) {

	                    if (parameters != null) {
	                        if (formulaUtilities.isCondition(formulaUtilities.replaceTagValues(rs.getString("condition_formula"), parameters), "getInitialAction")) {
	                            //action_cd = rs.getInt("action_cd");
	                            flowsr_no = rs.getInt("flow_srno");
	                            retArr = new int[2];
	                            retArr[0] = rs.getInt("action_cd");
	                            retArr[1] = flowsr_no;
	                            break;
	                        } else {
	                            flowsr_no++;
	                        }

	                    } else {
	                        //action_cd = rs.getInt("action_cd");
	                        flowsr_no = rs.getInt("flow_srno");
	                        retArr = new int[2];
	                        retArr[0] = rs.getInt("action_cd");
	                        retArr[1] = flowsr_no;
	                        break;
	                    }
	                } else {
	                    break;
	                }
	            }

	        } catch (SQLException sqle) {
	            LOGGER.error(sqle.toString() + " " + sqle.getStackTrace()[0]);
	            throw new VahanException(sqle.getMessage());
	        }

	        return retArr;
	    } // end of getInitialAction
	   
	    public void fileFlowForNewApplication(TransactionManagerOne tmgr, Status_dobj status_dobj,
	            String userId, String clientIpAddress) throws VahanException {
	        PreparedStatement ps = null;
	        String sql = null;
	        try {
	            sql = "insert into " + TableList.VA_STATUS
	                    + "(status,state_cd,off_cd,appl_no,pur_cd,flow_slno,file_movement_slno,action_cd,seat_cd,"
	                    + " cntr_id,office_remark,public_remark,file_movement_type,emp_cd,op_dt) "
	                    + " values('N',?,?,?,?,?,?,?,'N','','','','F',?,current_timestamp)";

	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, status_dobj.getState_cd());
	            ps.setInt(2, status_dobj.getOff_cd());
	            ps.setString(3, status_dobj.getAppl_no());
	            ps.setInt(4, status_dobj.getPur_cd());
	            ps.setInt(5, status_dobj.getFlow_slno());
	            ps.setInt(6, status_dobj.getFile_movement_slno());
	            ps.setInt(7, status_dobj.getAction_cd());
	            ps.setLong(8, status_dobj.getEmp_cd());
	            ps.executeUpdate();

	            /**
	             * Remove this check for two flow for same application in case of
	             * Temp fee in New regn.
	             */
	            if (status_dobj.getAction_cd() != TableConstants.TM_TMP_RC_APPROVAL && status_dobj.getAction_cd() != TableConstants.TM_ROLE_DEALER_TEMP_APPROVAL && status_dobj.getAction_cd() != TableConstants.TM_TMP_RC_VERIFICATION) {
	                sql = "SELECT appl_no from " + TableList.VA_DETAILS + " where appl_no = ? and regn_no != ?";
	                ps = tmgr.prepareStatement(sql);
	                ps.setString(1, status_dobj.getAppl_no());
	                ps.setString(2, status_dobj.getRegn_no());
	                RowSet rs = tmgr.fetchDetachedRowSet_No_release();
	                if (rs.next()) {
	                    LOGGER.error("WhereAmI:" + tmgr.getWhereiam() + ", Application No " + status_dobj.getAppl_no() + " already exist.");
	                    throw new VahanException("Application No already exist, please try again.");
	                }
	            }

	            String ins_va_details = "INSERT INTO " + TableList.VA_DETAILS
	                    + " ( appl_no,pur_cd,appl_dt,regn_no,user_id,user_type,state_cd,off_cd,entry_status,entry_ip)"
	                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	            PreparedStatement ps_va_details = tmgr.prepareStatement(ins_va_details);
	            ps_va_details.setString(1, status_dobj.getAppl_no()); // actual application no
	            ps_va_details.setInt(2, status_dobj.getPur_cd());
	            ps_va_details.setTimestamp(3,getSystemDateInPostgres());
	            ps_va_details.setString(4, status_dobj.getRegn_no());
	            ps_va_details.setString(5, userId);
	            ps_va_details.setString(6, ""); // blank user type.
	            ps_va_details.setString(7, status_dobj.getState_cd());
	            ps_va_details.setInt(8, status_dobj.getOff_cd());
	            ps_va_details.setString(9, "Y");
	            ps_va_details.setString(10, clientIpAddress);
	            ps_va_details.executeUpdate();

	        } catch (SQLException sqle) {
	            LOGGER.error(sqle.toString() + " " + sqle.getStackTrace()[0]);
	            throw new VahanException(sqle.getMessage());
	        }

	    }
	    public static Timestamp getSystemDateInPostgres() {
	        Date cur_date = new Date();
	        SimpleDateFormat sdf_dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String currnet_date_time = sdf_dt.format(cur_date);
	        Timestamp date_time = Timestamp.valueOf(currnet_date_time);
	        return date_time;
	    }
	    public  void webServiceForNextStage(Status_dobj status, String cntr_id,
	            String appl_no, int action_cd, int purcd, ApproveImpl approveImpl, TransactionManagerOne tmgr,
	            String selectedRoleCode, String empCode, int offCode) throws Exception {
	        String newStatus = status.getStatus();
	        NextStageRequest request = new NextStageRequest();
	        request.setCntr_id(cntr_id);
	        request.setAppl_no(appl_no);
	        if (selectedRoleCode != null) {
	            request.setEmp_cd(Long.parseLong(empCode)); // getting from session
	        } else {
	            request.setEmp_cd(0); // getting from session
	        }

	        if (newStatus.equalsIgnoreCase(TableConstants.STATUS_REVERT)) {
	            String prevAction = approveImpl.getPrevAction().getValue().toString();//for selectng radiobutton
	            request.setFile_movement_type(TableConstants.BACKWARD);
	            request.setAction_cd(Integer.parseInt(prevAction));
	        } else {
	            request.setFile_movement_type(TableConstants.FORWARD);
	            request.setAction_cd(action_cd);
	        }

	        request.setPur_cd(purcd);
	        //getting from session

	        request.setState_cd(status.getState_cd());
	        if (selectedRoleCode == null) {
	            request.setOff_cd(status.getOff_cd());
	        } else {
	            // Removed the ternary operator condition as all those methods point to the same thing
	            request.setOff_cd(offCode); // getting from session
	        }
	        NextStageResponse response = null;

	        if (TableConstants.isNextStageWebService) {

	            response = nextStageWs.getNextStageResponse(request);
	            status.setCntr_id(response.getCntr_id());
	            status.setAction_cd(response.getAction_cd());
	            status.setOff_cd(response.getOff_cd());
	            status.setEmp_cd(response.getEmp_cd());
	            status.setFlow_slno(response.getFlow_slno());
	            status.setRto_code(response.getRto_code());

	        } else {

	            String sql = null;
	            PreparedStatement ps = null;
	            RowSet rs = null;

	            if (request.getFile_movement_type().equals(TableConstants.BACKWARD)) {

	                sql = "select a.flow_srno, a.action_cd \n"
	                        + " from tm_purpose_action_flow a \n"
	                        + " where a.state_cd = ? and a.action_cd = ? and a.pur_cd = ?";
	                ps = tmgr.prepareStatement(sql);
	                ps.setString(1, request.getState_cd());
	                ps.setInt(2, request.getAction_cd());
	                ps.setInt(3, request.getPur_cd());
	                rs = tmgr.fetchDetachedRowSet_No_release();

	                if (rs.next()) {
	                    status.setAction_cd(rs.getInt("action_cd"));
	                    status.setOff_cd(request.getOff_cd());
	                    status.setFlow_slno(rs.getInt("flow_srno"));
	                    status.setRto_code(request.getRto_code());
	                }
	            } else {
	                int cntr = 1;
	                while (true) {

	                    sql = "select a.flow_srno, a.action_cd,a.condition_formula,seat_cd "
	                            + " from tm_purpose_action_flow a, va_status b "
	                            + " where a.state_cd = b.state_cd and a.pur_cd = b.pur_cd and a.state_cd = ? "
	                            + " and b.appl_no = ? and a.flow_srno = b.flow_slno + ? and a.pur_cd =?";
	                    ps = tmgr.prepareStatement(sql);
	                    ps.setString(1, request.getState_cd());
	                    ps.setString(2, request.getAppl_no());
	                    ps.setInt(3, cntr);
	                    ps.setInt(4, purcd);

	                    rs = tmgr.fetchDetachedRowSet_No_release();

	                    if (rs.next()) {
	                        if (!CommonUtils.isNullOrBlank(rs.getString("seat_cd")) && rs.getString("seat_cd").trim().split(",").length > 1) {
	                            String[] actionCdWithFileFlow = rs.getString("seat_cd").trim().split(",");
	                            status.setAction_cd(Integer.parseInt(actionCdWithFileFlow[0]));
	                            status.setOff_cd(request.getOff_cd());
	                            status.setFlow_slno(Integer.parseInt(actionCdWithFileFlow[1]));
	                            status.setRto_code(request.getRto_code());
	                            break;
	                        } else if (status.getVehicleParameters() == null || formulaUtilities.isCondition(formulaUtilities.replaceTagValues(rs.getString("condition_formula"), status.getVehicleParameters()), "webServiceForNextStage-3")) {
	                            status.setAction_cd(rs.getInt("action_cd"));
	                            status.setOff_cd(request.getOff_cd());
	                            status.setFlow_slno(rs.getInt("flow_srno"));
	                            status.setRto_code(request.getRto_code());
	                            break;
	                        } else {
	                            cntr++;
	                        }
	                    } else {
	                        status.setAction_cd(0);
	                        status.setFlow_slno(0);
	                        break;
	                    }
	                }
	            }
	        }
	    }//end of webServiceForNextStage

	    public static String getCustomLableFromSelectedListToShow(List list, String selectedValue) {
	        String lable = null;
	        String str = "";
	        for (int i = 0; i < list.size(); i++) {
	            HashMap map = new HashMap();
	            map = ((HashMap) list.get(i));
	            str = (String) map.get("value");
	            if (str.equalsIgnoreCase(selectedValue)) {
	                lable = (String) map.get("label");
	                break;// outer;
	            }
	        }
	        return lable;
	    }
	    public static String getTaxModeFromReqdTaxMode(String reqdTaxMode) {
	        String taxMode = null;
	        if (!CommonUtils.isNullOrBlank(reqdTaxMode)) {
	            if (reqdTaxMode.contains(",")) {
	                String[] str = reqdTaxMode.split(",");
	                for (String sd : str) {
	                    if (sd.contains("58")) {
	                        taxMode = sd.substring(3);
	                        break;
	                    }
	                }
	            } else if (reqdTaxMode.contains("58")) {
	                taxMode = reqdTaxMode.substring(3);
	            }
	        }
	        return taxMode;
	    }

	    
}

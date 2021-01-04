package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.MasterTableFiller;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
import nic.vahan5.reg.form.dobj.AuctionDobj;
import nic.vahan5.reg.form.dobj.AxleDetailsDobj;
import nic.vahan5.reg.form.dobj.CdDobj;
import nic.vahan5.reg.form.dobj.ExArmyDobj;
import nic.vahan5.reg.form.dobj.InsDobj;
import nic.vahan5.reg.form.dobj.OtherStateVehDobj;
import nic.vahan5.reg.form.dobj.OwnerIdentificationDobj;
import nic.vahan5.reg.form.dobj.Owner_dobj;
import nic.vahan5.reg.form.dobj.Owner_temp_dobj;
import nic.vahan5.reg.form.dobj.PendencyBankDobj;
import nic.vahan5.reg.form.dobj.ReflectiveTapeDobj;
import nic.vahan5.reg.form.dobj.SpeedGovernorDobj;
import nic.vahan5.reg.form.dobj.TempRegDobj;
import nic.vahan5.reg.form.dobj.TmConfigurationDobj;
import nic.vahan5.reg.form.model.OwnerBeanModel;
import nic.vahan5.reg.form.model.SessionVariablesModel;
import nic.vahan5.reg.server.CommonUtils;
import nic.vahan5.reg.server.ServerUtil;
import nic.vahan5.reg.server.ServerUtility;
import nic.vahan5.reg.server.TableConstants;
@Service
public class OwnerImpl {
	
	@Autowired
	TransactionManagerOne tmgr;
	@Autowired
	ServerUtil serverUtil;
	@Autowired
	CdImpl cdImpl;
	@Autowired
	FitnessImplementation fitImpl;
	@Autowired
	ExArmyImplementation ExArmyImpl;
	@Autowired
	InsImplementation InsImpl;
	@Autowired
	AxleImplementation AxleImpl;
	@Autowired
	PendencyBankDetailImpl pendencyBankDetailImpl;
	@Autowired
	ServerUtility serverUtility;
	@Autowired
	MasterTableFiller masterTableFiller;
	
	  private static final Logger LOGGER = Logger.getLogger(OwnerImpl.class);
	
	  
	  public void tradeCertificateValidation(String dealerCd, String vchCatg, TmConfigurationDobj tmConfDobj,
	            String userCategory, String userStateCode, int offCode) throws VahanException {
	    	
	        if (userCategory != null && !userCategory.equals("") && dealerCd != null && !dealerCd.equals("") && vchCatg != null && !vchCatg.equals("") && tmConfDobj != null) {
	            boolean isConsiderTradeCert = false;
	            if (userCategory.equals(TableConstants.USER_CATG_OFF_STAFF) && tmConfDobj.isConsiderTradeCert()) {
	                boolean isRegisteredDealer = serverUtility.getRegisteredDealerInfo(userStateCode, offCode, dealerCd);
	                if (isRegisteredDealer) {
	                    isConsiderTradeCert = true;
	                }
	            }

	            if (isConsiderTradeCert || TableConstants.User_Dealer.equals(userCategory)) {
	            String response=	serverUtility.getDealerTradeCertificateDetails(dealerCd, vchCatg, userStateCode, tmConfDobj);
	           OwnerBeanModel ownerbeanmodel= new  OwnerBeanModel();
	            
	           ownerbeanmodel.setTradeCertExpireMess(response);
	                if (!CommonUtils.isNullOrBlank(ownerbeanmodel.getTradeCertExpireMess())) {
	                    throw new VahanException(ownerbeanmodel.getTradeCertExpireMess());
	                }
	            }
	        }
	    }

	  public void validateHomoMaker(Owner_dobj dobj) throws VahanException, Exception {
	        if (dobj != null && !TableConstants.ALLOW_VH_CHASS_WITH_HOMO_MAKER.contains(dobj.getVh_class() + "") && dobj.getMaker() <= 999
	                && (TableConstants.VM_REGN_TYPE_NEW.equals(dobj.getRegn_type()) || TableConstants.VM_REGN_TYPE_TEMPORARY.equals(dobj.getRegn_type()))) {
	            throw new VahanException("Only Vehicle Class 'Trailer (Agricultural)' can be selected as manufacturer [" + masterTableFiller.masterTables.VM_MAKER.getDesc(dobj.getMaker() + "") + "] is registered on Homologation Portal and he has not uploaded the inventory details on Homologation Portal against entered Chassis/Engine combination, either you have wrongly entered chassis/engine no combination or this vehicle does not belong to this maker.");
	        }
	    }
	  
/*	public Owner_dobj set_Owner_appl_db_to_dobj(String regn_no, String appl_no, String chasi_no, int pur_cd) throws Exception {
      //  TransactionManager tmgr = null;
        PreparedStatement ps = null;
        Owner_dobj owner_dobj = null;
        RowSet rs = null;

        if (regn_no != null) {
            regn_no = regn_no.toUpperCase();
        }
        if (appl_no != null) {
            appl_no = appl_no.toUpperCase();
        }
        if (chasi_no != null) {
            chasi_no = chasi_no.toUpperCase();
        }

        try {
          //  tmgr = new TransactionManager("Owner_Impl");
            String query = "";
            String parameterValue = "";
            if (TableConstants.VM_TRANSACTION_MAST_NEW_VEHICLE == pur_cd
                    || TableConstants.VM_TRANSACTION_MAST_DEALER_NEW_VEHICLE == pur_cd
                    || TableConstants.VM_TRANSACTION_MAST_VEH_CONVERSION == pur_cd
                    || TableConstants.VM_TRANSACTION_MAST_NEW_VEHICLE_FITNESS == pur_cd) {
                if (appl_no != null && !appl_no.equalsIgnoreCase("")) {
                    query = "select *,state.descr as current_state_name,dist.descr as current_district_name from " + TableList.VA_OWNER + " owner "
                            + " left join " + TableList.VA_SIDE_TRAILER + " trailer on owner.appl_no = trailer.appl_no "
                            + " left join " + TableList.TM_STATE + " state on owner.c_state = state.state_code "
                            + " left join " + TableList.TM_DISTRICT + " dist on owner.c_district = dist.dist_cd "
                            + " where owner.appl_no=? ";
                    parameterValue = appl_no;

                    ps = tmgr.prepareStatement(query);
                    ps.setString(1, parameterValue);
                    rs = tmgr.fetchDetachedRowSet_No_release();
                    owner_dobj = fillFrom_Va_OwnerDobj(rs);

                } else if (chasi_no != null && !chasi_no.equalsIgnoreCase("")) {
                    query = "select *,state.descr as current_state_name,dist.descr as current_district_name from  " + TableList.VA_OWNER + " owner "
                            + " left join " + TableList.TM_STATE + " state on owner.c_state = state.state_code "
                            + " left join " + TableList.TM_DISTRICT + " dist on owner.c_district = dist.dist_cd "
                            + " where owner.chasi_no=? ";
                    parameterValue = chasi_no;

                    ps = tmgr.prepareStatement(query);
                    ps.setString(1, parameterValue);
                    rs = tmgr.fetchDetachedRowSet_No_release();
                    owner_dobj = fillFrom_Va_OwnerDobj(rs);

                    if (owner_dobj == null) {
                        query = "select *,state.descr as current_state_name,dist.descr as current_district_name from  " + TableList.VT_OWNER + " owner "
                                + " left join " + TableList.TM_STATE + " state on owner.c_state = state.state_code "
                                + " left join " + TableList.TM_DISTRICT + " dist on owner.c_district = dist.dist_cd "
                                + " where owner.chasi_no=? ";

                        ps = tmgr.prepareStatement(query);
                        ps.setString(1, parameterValue);
                        rs = tmgr.fetchDetachedRowSet_No_release();
                        owner_dobj = fillFrom_VT_OwnerDobj(rs);
                    }

                } else if (regn_no != null && !regn_no.equalsIgnoreCase("")) {
                    query = "select *,state.descr as current_state_name,dist.descr as current_district_name from  " + TableList.VT_OWNER + " owner "
                            + " left join " + TableList.TM_STATE + " state on owner.c_state = state.state_code "
                            + " left join " + TableList.TM_DISTRICT + " dist on owner.c_district = dist.dist_cd "
                            + " where owner.regn_no=? ";
                    parameterValue = regn_no;
                    ps = tmgr.prepareStatement(query);
                    ps.setString(1, parameterValue);
                    rs = tmgr.fetchDetachedRowSet_No_release();
                    owner_dobj = fillFrom_VT_OwnerDobj(rs);
                }
            } else if (TableConstants.VM_TRANSACTION_MAST_TEMP_REG == pur_cd || TableConstants.VM_TRANSACTION_MAST_DEALER_TEMP_VEHICLE == pur_cd) {
                if (appl_no != null && !appl_no.equalsIgnoreCase("")) {
                    query = "select *,state.descr as current_state_name,dist.descr as current_district_name from " + TableList.VA_OWNER + " owner "
                            + " left join " + TableList.VA_SIDE_TRAILER + " trailer on owner.appl_no = trailer.appl_no "
                            + " left join " + TableList.TM_STATE + " state on owner.c_state = state.state_code "
                            + " left join " + TableList.TM_DISTRICT + " dist on owner.c_district = dist.dist_cd "
                            + " where owner.appl_no=? ";
                    parameterValue = appl_no;

                    ps = tmgr.prepareStatement(query);
                    ps.setString(1, parameterValue);
                    rs = tmgr.fetchDetachedRowSet_No_release();
                    owner_dobj = fillFrom_Va_OwnerDobj(rs);
                    if (owner_dobj != null) {
                        ps = tmgr.prepareStatement("select * from va_owner_temp where appl_no=? ");
                        ps.setString(1, parameterValue);
                        rs = tmgr.fetchDetachedRowSet_No_release();
                        Owner_temp_dobj temp = fillFrom_va_owner_temp(rs);
                        if (temp != null) {
                            temp.setAppl_no(appl_no);
                        }
                        owner_dobj.setDob_temp(temp);
                    }

                } else if (chasi_no != null && !chasi_no.equalsIgnoreCase("")) {
                    query = "select *,state.descr as current_state_name,dist.descr as current_district_name from  " + TableList.VA_OWNER + " owner "
                            + " left join " + TableList.TM_STATE + " state on owner.c_state = state.state_code "
                            + " left join " + TableList.TM_DISTRICT + " dist on owner.c_district = dist.dist_cd "
                            + " where owner.chasi_no=?";
                    parameterValue = chasi_no;

                    ps = tmgr.prepareStatement(query);
                    ps.setString(1, parameterValue);
                    rs = tmgr.fetchDetachedRowSet_No_release();
                    owner_dobj = fillFrom_Va_OwnerDobj(rs);
                    if (owner_dobj != null) {
                        ps = tmgr.prepareStatement("select * from va_owner_temp where appl_no=? ");
                        ps.setString(1, owner_dobj.getAppl_no());
                        rs = tmgr.fetchDetachedRowSet_No_release();
                        Owner_temp_dobj temp = fillFrom_va_owner_temp(rs);
                        if (temp != null) {
                            temp.setAppl_no(appl_no);
                        }
                        owner_dobj.setDob_temp(temp);
                    }

                    //validate from vt_owner_temp
                    if (owner_dobj == null) {
                        query = "select *,state.descr as current_state_name,dist.descr as current_district_name from  " + TableList.VT_OWNER_TEMP + " owner "
                                + " left join " + TableList.TM_STATE + " state on owner.c_state = state.state_code "
                                + " left join " + TableList.TM_DISTRICT + " dist on owner.c_district = dist.dist_cd "
                                + " where owner.chasi_no=? ";

                        ps = tmgr.prepareStatement(query);
                        ps.setString(1, parameterValue);
                        rs = tmgr.fetchDetachedRowSet_No_release();
                        owner_dobj = fillFrom_vt_owner_temp(rs);
                    }

                } else if (regn_no != null && !regn_no.equalsIgnoreCase("")) {
                    query = "select *,state.descr as current_state_name,dist.descr as current_district_name from  " + TableList.VT_OWNER_TEMP + " owner "
                            + " left join " + TableList.TM_STATE + " state on owner.c_state = state.state_code "
                            + " left join " + TableList.TM_DISTRICT + " dist on owner.c_district = dist.dist_cd "
                            + " where owner.temp_regn_no=? ";
                    parameterValue = regn_no;
                    ps = tmgr.prepareStatement(query);
                    ps.setString(1, parameterValue);
                    rs = tmgr.fetchDetachedRowSet_No_release();
                    owner_dobj = fillFrom_vt_owner_temp(rs);
                }
            } else {
                if (regn_no != null) {
                    query = "select *,state.descr as current_state_name,dist.descr as current_district_name from  " + TableList.VT_OWNER + " owner "
                            + " left join " + TableList.TM_STATE + " state on owner.c_state = state.state_code "
                            + " left join " + TableList.TM_DISTRICT + " dist on owner.c_district = dist.dist_cd "
                            + " where owner.regn_no=? order by op_dt desc limit 1";
                    parameterValue = regn_no;
                    ps = tmgr.prepareStatement(query);
                    ps.setString(1, parameterValue);
                    rs = tmgr.fetchDetachedRowSet_No_release();
                    owner_dobj = fillFrom_VT_OwnerDobj(rs);
                } else if (chasi_no != null) {
                    query = "select *,state.descr as current_state_name,dist.descr as current_district_name from  " + TableList.VT_OWNER + " owner "
                            + " left join " + TableList.TM_STATE + " state on owner.c_state = state.state_code "
                            + " left join " + TableList.TM_DISTRICT + " dist on owner.c_district = dist.dist_cd "
                            + " where owner.chasi_no=? ";
                    parameterValue = chasi_no;
                    ps = tmgr.prepareStatement(query);
                    ps.setString(1, parameterValue);
                    rs = tmgr.fetchDetachedRowSet_No_release();
                    owner_dobj = fillFrom_VT_OwnerDobj(rs);

                }
            }
        } finally {
            if (tmgr != null) {
                try {
                    tmgr.release();
                } catch (Exception e) {
                    LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
                }
            }
        }

        return owner_dobj;
    }*/
	public Owner_dobj fillFrom_Va_OwnerDobj(RowSet rsOwner) throws SQLException {
        Owner_dobj owner_dobj = null;
      //  TransactionManager tmgrOwner = null;
        RowSet rsOwnerId = null;
        try {
            if (rsOwner.next()) // found
            {
                owner_dobj = new Owner_dobj();
                OwnerIdentificationDobj own_identity = new OwnerIdentificationDobj();
                owner_dobj.setOwner_identity(own_identity);
                owner_dobj.setState_cd(rsOwner.getString("state_cd"));
                owner_dobj.setOff_cd(rsOwner.getInt("off_cd"));
                owner_dobj.setAppl_no(rsOwner.getString("appl_no"));
                owner_dobj.setRegn_no(rsOwner.getString("regn_no"));
                owner_dobj.setRegn_dt(rsOwner.getDate("regn_dt"));
                owner_dobj.setPurchase_dt(rsOwner.getDate("purchase_dt"));
                owner_dobj.setOwner_sr(rsOwner.getInt("owner_sr"));
                owner_dobj.setOwner_name(rsOwner.getString("owner_name"));
                owner_dobj.setF_name(rsOwner.getString("f_name"));
                owner_dobj.setC_add1(rsOwner.getString("c_add1"));
                owner_dobj.setC_add2(rsOwner.getString("c_add2"));
                owner_dobj.setC_add3(rsOwner.getString("c_add3"));
                owner_dobj.setC_district(rsOwner.getInt("c_district"));
                owner_dobj.setC_pincode(rsOwner.getInt("c_pincode"));
                owner_dobj.setC_state(rsOwner.getString("c_state"));
                owner_dobj.setP_add1(rsOwner.getString("p_add1"));
                owner_dobj.setP_add2(rsOwner.getString("p_add2"));
                owner_dobj.setP_add3(rsOwner.getString("p_add3"));
                owner_dobj.setP_district(rsOwner.getInt("p_district"));
                owner_dobj.setP_pincode(rsOwner.getInt("p_pincode"));
                owner_dobj.setP_state(rsOwner.getString("p_state"));
                owner_dobj.setOwner_cd(rsOwner.getInt("owner_cd"));
                owner_dobj.setRegn_type(rsOwner.getString("regn_type"));
                owner_dobj.setVh_class(rsOwner.getInt("vh_class"));
                owner_dobj.setChasi_no(rsOwner.getString("chasi_no"));
                owner_dobj.setEng_no(rsOwner.getString("eng_no"));
                owner_dobj.setMaker(rsOwner.getInt("maker"));
                owner_dobj.setMaker_model(rsOwner.getString("maker_model"));
                owner_dobj.setBody_type(rsOwner.getString("body_type"));
                owner_dobj.setNo_cyl(rsOwner.getInt("no_cyl"));
                owner_dobj.setHp(rsOwner.getFloat("hp"));
                owner_dobj.setSeat_cap(rsOwner.getInt("seat_cap"));
                owner_dobj.setStand_cap(rsOwner.getInt("stand_cap"));
                owner_dobj.setSleeper_cap(rsOwner.getInt("sleeper_cap"));
                owner_dobj.setUnld_wt(rsOwner.getInt("unld_wt"));
                owner_dobj.setLd_wt(rsOwner.getInt("ld_wt"));
                owner_dobj.setGcw(rsOwner.getInt("gcw"));
                owner_dobj.setFuel(rsOwner.getInt("fuel"));
                owner_dobj.setColor(rsOwner.getString("color"));
                owner_dobj.setManu_mon(rsOwner.getInt("manu_mon"));
                owner_dobj.setManu_yr(rsOwner.getInt("manu_yr"));
                owner_dobj.setNorms(rsOwner.getInt("norms"));
                owner_dobj.setWheelbase(rsOwner.getInt("wheelbase"));
                owner_dobj.setCubic_cap(rsOwner.getFloat("cubic_cap"));
                owner_dobj.setFloor_area(rsOwner.getFloat("floor_area"));
                owner_dobj.setAc_fitted(rsOwner.getString("ac_fitted"));
                owner_dobj.setAudio_fitted(rsOwner.getString("audio_fitted"));
                owner_dobj.setVideo_fitted(rsOwner.getString("video_fitted"));
                owner_dobj.setVch_purchase_as(rsOwner.getString("vch_purchase_as"));
                owner_dobj.setVch_catg(rsOwner.getString("vch_catg"));
                owner_dobj.setDealer_cd(rsOwner.getString("dealer_cd"));
                owner_dobj.setSale_amt(rsOwner.getInt("sale_amt"));
                owner_dobj.setLaser_code(rsOwner.getString("laser_code"));
                owner_dobj.setGarage_add(rsOwner.getString("garage_add"));
                owner_dobj.setLength(rsOwner.getInt("length"));
                owner_dobj.setWidth(rsOwner.getInt("width"));
                owner_dobj.setHeight(rsOwner.getInt("height"));
                owner_dobj.setRegn_upto(rsOwner.getDate("regn_upto"));
                owner_dobj.setFit_upto(rsOwner.getDate("fit_upto"));
                owner_dobj.setAnnual_income(rsOwner.getInt("annual_income"));
                owner_dobj.setImported_vch(rsOwner.getString("imported_vch"));
                owner_dobj.setOther_criteria(rsOwner.getInt("other_criteria"));
                owner_dobj.setPmt_type(rsOwner.getInt("pmt_type"));
                owner_dobj.setPmt_catg(rsOwner.getInt("pmt_catg"));
                owner_dobj.setRqrd_tax_modes(rsOwner.getString("rqrd_tax_modes"));
                owner_dobj.setOp_dt(rsOwner.getString("op_dt"));
                owner_dobj.setVehType(serverUtil.VehicleClassType(owner_dobj.getVh_class()));
                if (CommonUtils.isColumnExist(rsOwner, "link_regn_no") && rsOwner.getString("link_regn_no") != null) {
                    owner_dobj.setLinkedRegnNo(rsOwner.getString("link_regn_no"));
                }
                owner_dobj.setC_district_name(rsOwner.getString("current_district_name"));
                owner_dobj.setC_state_name(rsOwner.getString("current_state_name"));
                owner_dobj.setModelNameOnTAC(serverUtil.getMakerModelName(rsOwner.getString("maker_model")));

              //  tmgrOwner = new TransactionManager("Owner_Id");
                String sql = "SELECT * FROM " + TableList.VA_OWNER_IDENTIFICATION
                        + " where appl_no =?";
                PreparedStatement ps = tmgr.prepareStatement(sql);
                ps.setString(1, owner_dobj.getAppl_no());
                rsOwnerId = tmgr.fetchDetachedRowSet_No_release();
                own_identity = fillOwnerIdentityDobj(rsOwnerId);
                own_identity.setAppl_no(owner_dobj.getAppl_no());
                owner_dobj.setOwner_identity(own_identity);
                String sqll = "SELECT * FROM " + TableList.VA_OWNER_OTHER
                        + " where appl_no =?";
                ps = tmgr.prepareStatement(sqll);
                ps.setString(1, owner_dobj.getAppl_no());
                rsOwnerId = tmgr.fetchDetachedRowSet_No_release();
                if (rsOwnerId.next()) {
                    owner_dobj = fillVaOwnerOther(owner_dobj, rsOwnerId);
                }
                if (owner_dobj.getRegn_type().equals(TableConstants.VM_REGN_TYPE_TEMPORARY)) {
                    sql = "SELECT * FROM " + TableList.VA_TMP_REGN_DTL + " where appl_no =?";
                    ps = tmgr.prepareStatement(sql);
                    ps.setString(1, owner_dobj.getAppl_no());
                    rsOwnerId = tmgr.fetchDetachedRowSet_No_release();

                    if (rsOwnerId.next()) {
                        TempRegDobj dobj = new TempRegDobj();
                        dobj.setAppl_no(rsOwnerId.getString("appl_no"));
                        dobj.setRegn_no(rsOwnerId.getString("regn_no"));
                        dobj.setTmp_off_cd(rsOwnerId.getInt("tmp_off_cd"));
                        dobj.setRegn_auth(rsOwnerId.getString("regn_auth"));
                        dobj.setTmp_state_cd(rsOwnerId.getString("tmp_state_cd"));
                        dobj.setTmp_regn_no(rsOwnerId.getString("tmp_regn_no"));
                        dobj.setTmp_regn_dt(rsOwnerId.getDate("tmp_regn_dt"));
                        dobj.setTmp_valid_upto(rsOwnerId.getDate("tmp_valid_upto"));
                        dobj.setDealer_cd(rsOwnerId.getString("dealer_cd"));
                        owner_dobj.setTempReg(dobj);
                    }

                }

                if (owner_dobj.getRegn_type().equals(TableConstants.VM_REGN_TYPE_CD)) {
                   // CdImpl cdImpl = new CdImpl();
                    CdDobj cdDobj = cdImpl.getVaCdDobj(owner_dobj.getAppl_no());
                    owner_dobj.setCdDobj(cdDobj);
                }


                OtherStateVehImpl otherStateVehImpl = new OtherStateVehImpl();
                OtherStateVehDobj otherStateVehDobj = otherStateVehImpl.setOtherVehicleDetailsToDobj(owner_dobj.getAppl_no());
                owner_dobj.setOtherStateVehDobj(otherStateVehDobj);

                sql = "SELECT * FROM " + TableList.VA_SPEED_GOVERNOR + " where appl_no =?";
                ps = tmgr.prepareStatement(sql);
                ps.setString(1, owner_dobj.getAppl_no());
                RowSet rs = tmgr.fetchDetachedRowSet_No_release();
                if (rs.next()) {
                    SpeedGovernorDobj sgDobj = new SpeedGovernorDobj();
                    sgDobj.setAppl_no(rs.getString("appl_no"));
                    sgDobj.setState_cd(rs.getString("state_cd"));
                    sgDobj.setOff_cd(rs.getInt("off_cd"));
                    sgDobj.setRegn_no(rs.getString("regn_no"));
                    sgDobj.setSg_no(rs.getString("sg_no"));
                    sgDobj.setSg_fitted_at(rs.getString("sg_fitted_at"));
                    sgDobj.setSg_fitted_on(rs.getDate("sg_fitted_on"));

                    sgDobj.setSgGovType(rs.getInt("sg_type"));
                    sgDobj.setSgTypeApprovalNo(rs.getString("sg_type_approval_no"));
                    sgDobj.setSgTestReportNo(rs.getString("sg_test_report_no"));
                    sgDobj.setSgFitmentCerticateNo(rs.getString("sg_fitment_cert_no"));

                    owner_dobj.setSpeedGovernorDobj(sgDobj);
                }

             //   FitnessImplementation fitImpl = new FitnessImplementation();
                ReflectiveTapeDobj refDobj = fitImpl.getVaReflectiveTapeDobj(owner_dobj.getAppl_no(), tmgr);
                owner_dobj.setReflectiveTapeDobj(refDobj);

                ExArmyDobj exArmy = ExArmyImpl.setExArmyDetails_db_to_dobj(rsOwner.getString("appl_no"), null, null, 0);
                owner_dobj.setExArmy_dobj(exArmy);

                InsDobj insDobj = InsImpl.set_ins_dtls_db_to_dobj(null, rsOwner.getString("appl_no"), null, 0);
                owner_dobj.setInsDobj(insDobj);

                AxleDetailsDobj axleDetailsDobj = AxleImpl.setAxleVehDetails_db_to_dobj(rsOwner.getString("appl_no"), null, null, 0);
                if (axleDetailsDobj != null) {
                    owner_dobj.setAxleDobj(axleDetailsDobj);
                    owner_dobj.setNumberOfTyres(axleDetailsDobj.getTf_Front_tyre() + axleDetailsDobj.getTf_Other_tyre() + axleDetailsDobj.getTf_Rear_tyre() + axleDetailsDobj.getTf_Tandem_tyre());
                }
                if ("DL".equals(owner_dobj.getState_cd()) && owner_dobj.getVh_class() == TableConstants.ERICKSHAW_VCH_CLASS) {
                    PendencyBankDobj dobj = pendencyBankDetailImpl.getBankSubsidyData(rsOwner.getString("appl_no"), rsOwner.getString("state_cd"), rsOwner.getInt("off_cd"));
                    if (dobj != null) {
                        owner_dobj.getOwner_identity().setAadhar_no(dobj.getAadharNo());
                    }
                }
                // auction Details
                AuctionDobj auctionDobj = new AuctionImpl().getDetailsFromVtAuction(owner_dobj.getChasi_no().trim().toUpperCase(), null);
                owner_dobj.setAuctionDobj(auctionDobj);
            }
        } catch (VahanException e) {
        } catch (Exception e) {
            LOGGER.error(owner_dobj == null ? "" : owner_dobj.getAppl_no() + "-" + e.toString() + " " + e.getStackTrace()[0]);
        } finally {
            if (tmgr != null) {
                try {
                    tmgr.release();
                } catch (Exception e) {
                    LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
                }
            }
        }

        return owner_dobj;
    }
	public OwnerIdentificationDobj fillOwnerIdentityDobj(RowSet rsOwnerId) throws SQLException {
        OwnerIdentificationDobj dobj = new OwnerIdentificationDobj();

        if (rsOwnerId.next()) {
            dobj.setState_cd(rsOwnerId.getString("state_cd"));
            dobj.setOff_cd(rsOwnerId.getInt("off_cd"));
            dobj.setRegn_no(rsOwnerId.getString("regn_no"));
            dobj.setMobile_no(rsOwnerId.getLong("mobile_no"));
            dobj.setEmail_id(rsOwnerId.getString("email_id"));
            dobj.setPan_no(rsOwnerId.getString("pan_no"));
            dobj.setAadhar_no(rsOwnerId.getString("aadhar_no"));
            dobj.setPassport_no(rsOwnerId.getString("passport_no"));
            dobj.setRation_card_no(rsOwnerId.getString("ration_card_no"));
            dobj.setVoter_id(rsOwnerId.getString("voter_id"));
            dobj.setDl_no(rsOwnerId.getString("dl_no"));
            dobj.setVerified_on(rsOwnerId.getDate("verified_on"));
            dobj.setOwnerCatg(rsOwnerId.getInt("owner_ctg"));
            dobj.setOwnerCdDept(rsOwnerId.getInt("dept_cd"));
        }
        return dobj;
    }
	 public Owner_dobj fillVaOwnerOther(Owner_dobj owner_dobj, RowSet rsOwnerId) throws SQLException {
	        owner_dobj.setPush_bk_seat(rsOwnerId.getInt("push_back_seat"));
	        owner_dobj.setOrdinary_seat(rsOwnerId.getInt("ordinary_seat"));
	        return owner_dobj;
	    }
	 public Owner_dobj fillFrom_VT_OwnerDobj(RowSet rsOwner) throws SQLException, VahanException {
	        Owner_dobj owner_dobj = null;
	        //TransactionManager tmgrOwner = null;
	        RowSet rs = null;
	        try {
	            if (rsOwner.next()) // found
	            {
	                owner_dobj = new Owner_dobj();

	                owner_dobj.setState_cd(rsOwner.getString("state_cd"));
	                owner_dobj.setOff_cd(rsOwner.getInt("off_cd"));
	                owner_dobj.setRegn_no(rsOwner.getString("regn_no"));
	                owner_dobj.setRegn_dt(rsOwner.getDate("regn_dt"));
	                owner_dobj.setPurchase_dt(rsOwner.getDate("purchase_dt"));
	                owner_dobj.setOwner_sr(rsOwner.getInt("owner_sr"));
	                owner_dobj.setOwner_name(rsOwner.getString("owner_name"));
	                owner_dobj.setF_name(rsOwner.getString("f_name"));
	                owner_dobj.setC_add1(rsOwner.getString("c_add1"));
	                owner_dobj.setC_add2(rsOwner.getString("c_add2"));
	                owner_dobj.setC_add3(rsOwner.getString("c_add3"));
	                owner_dobj.setC_district(rsOwner.getInt("c_district"));
	                owner_dobj.setC_pincode(rsOwner.getInt("c_pincode"));
	                owner_dobj.setC_state(rsOwner.getString("c_state"));
	                owner_dobj.setP_add1(rsOwner.getString("p_add1"));
	                owner_dobj.setP_add2(rsOwner.getString("p_add2"));
	                owner_dobj.setP_add3(rsOwner.getString("p_add3"));
	                owner_dobj.setP_district(rsOwner.getInt("p_district"));
	                owner_dobj.setP_pincode(rsOwner.getInt("p_pincode"));
	                owner_dobj.setP_state(rsOwner.getString("p_state"));
	                owner_dobj.setOwner_cd(rsOwner.getInt("owner_cd"));
	                owner_dobj.setRegn_type(rsOwner.getString("regn_type"));
	                owner_dobj.setVh_class(rsOwner.getInt("vh_class"));
	                owner_dobj.setChasi_no(rsOwner.getString("chasi_no"));
	                owner_dobj.setEng_no(rsOwner.getString("eng_no"));
	                owner_dobj.setMaker(rsOwner.getInt("maker"));
	                owner_dobj.setMaker_model(rsOwner.getString("maker_model"));
	                owner_dobj.setBody_type(rsOwner.getString("body_type"));
	                owner_dobj.setNo_cyl(rsOwner.getInt("no_cyl"));
	                owner_dobj.setHp(rsOwner.getFloat("hp"));
	                owner_dobj.setSeat_cap(rsOwner.getInt("seat_cap"));
	                owner_dobj.setStand_cap(rsOwner.getInt("stand_cap"));
	                owner_dobj.setSleeper_cap(rsOwner.getInt("sleeper_cap"));
	                owner_dobj.setUnld_wt(rsOwner.getInt("unld_wt"));
	                owner_dobj.setLd_wt(rsOwner.getInt("ld_wt"));
	                owner_dobj.setGcw(rsOwner.getInt("gcw"));
	                owner_dobj.setFuel(rsOwner.getInt("fuel"));
	                owner_dobj.setColor(rsOwner.getString("color"));
	                owner_dobj.setManu_mon(rsOwner.getInt("manu_mon"));
	                owner_dobj.setManu_yr(rsOwner.getInt("manu_yr"));
	                owner_dobj.setNorms(rsOwner.getInt("norms"));
	                owner_dobj.setWheelbase(rsOwner.getInt("wheelbase"));
	                owner_dobj.setCubic_cap(rsOwner.getFloat("cubic_cap"));
	                owner_dobj.setFloor_area(rsOwner.getFloat("floor_area"));
	                owner_dobj.setAc_fitted(rsOwner.getString("ac_fitted"));
	                owner_dobj.setAudio_fitted(rsOwner.getString("audio_fitted"));
	                owner_dobj.setVideo_fitted(rsOwner.getString("video_fitted"));
	                owner_dobj.setVch_purchase_as(rsOwner.getString("vch_purchase_as"));
	                owner_dobj.setVch_catg(rsOwner.getString("vch_catg"));
	                owner_dobj.setDealer_cd(rsOwner.getString("dealer_cd"));
	                owner_dobj.setSale_amt(rsOwner.getInt("sale_amt"));
	                owner_dobj.setLaser_code(rsOwner.getString("laser_code"));
	                owner_dobj.setGarage_add(rsOwner.getString("garage_add"));
	                owner_dobj.setLength(rsOwner.getInt("length"));
	                owner_dobj.setWidth(rsOwner.getInt("width"));
	                owner_dobj.setHeight(rsOwner.getInt("height"));
	                owner_dobj.setRegn_upto(rsOwner.getDate("regn_upto"));
	                owner_dobj.setFit_upto(rsOwner.getDate("fit_upto"));
	                owner_dobj.setAnnual_income(rsOwner.getInt("annual_income"));
	                owner_dobj.setImported_vch(rsOwner.getString("imported_vch"));
	                owner_dobj.setOther_criteria(rsOwner.getInt("other_criteria"));
	                owner_dobj.setStatus(rsOwner.getString("status"));
	                owner_dobj.setOp_dt(rsOwner.getString("op_dt"));
	                owner_dobj.setVehType(serverUtil.VehicleClassType(owner_dobj.getVh_class()));
	                owner_dobj.setC_district_name(rsOwner.getString("current_district_name"));
	                owner_dobj.setC_state_name(rsOwner.getString("current_state_name"));
	               // tmgrOwner = new TransactionManager("Owner_Id");
	                String sql = "SELECT * FROM " + TableList.VT_OWNER_IDENTIFICATION
	                        + " where regn_no =? and state_cd=? and off_cd=?";
	                PreparedStatement ps = tmgr.prepareStatement(sql);
	                ps.setString(1, owner_dobj.getRegn_no());
	                ps.setString(2, owner_dobj.getState_cd());
	                ps.setInt(3, owner_dobj.getOff_cd());
	                rs = tmgr.fetchDetachedRowSet_No_release();
	                OwnerIdentificationDobj own_identity = fillOwnerIdentityDobj(rs);
	                if (own_identity != null) {
	                    own_identity.setAppl_no(owner_dobj.getAppl_no());
	                    owner_dobj.setOwner_identity(own_identity);
	                }

	                sql = "SELECT state_cd, off_cd, regn_no, sg_no, sg_fitted_on, sg_fitted_at, op_dt, emp_cd,"
	                        + "sg_type,sg_type_approval_no,sg_test_report_no,sg_fitment_cert_no "
	                        + "  FROM vt_speed_governor where regn_no=? and state_cd=? and off_cd=? ";
	                ps = tmgr.prepareStatement(sql);
	                ps.setString(1, owner_dobj.getRegn_no());
	                ps.setString(2, owner_dobj.getState_cd());
	                ps.setInt(3, owner_dobj.getOff_cd());
	                rs = tmgr.fetchDetachedRowSet_No_release();
	                if (rs.next()) {
	                    SpeedGovernorDobj spDobj = new SpeedGovernorDobj();
	                    spDobj.setState_cd(rs.getString("state_cd"));
	                    spDobj.setOff_cd(rs.getInt("off_cd"));
	                    spDobj.setRegn_no(rs.getString("regn_no"));
	                    spDobj.setSg_no(rs.getString("sg_no"));
	                    spDobj.setSg_fitted_on(rs.getDate("sg_fitted_on"));
	                    spDobj.setSg_fitted_at(rs.getString("sg_fitted_at"));

	                    spDobj.setSgGovType(rs.getInt("sg_type"));
	                    spDobj.setSgTypeApprovalNo(rs.getString("sg_type_approval_no"));
	                    spDobj.setSgTestReportNo(rs.getString("sg_test_report_no"));
	                    spDobj.setSgFitmentCerticateNo(rs.getString("sg_fitment_cert_no"));
	                    owner_dobj.setSpeedGovernorDobj(spDobj);
	                }

	              //  FitnessImplementation fitImpl = new FitnessImplementation();
	                ReflectiveTapeDobj refDobj = fitImpl.getVtReflectiveTapeDobj(owner_dobj.getRegn_no(), owner_dobj.getState_cd(), owner_dobj.getOff_cd(), tmgr);
	                owner_dobj.setReflectiveTapeDobj(refDobj);

	                AxleDetailsDobj axleDetailsDobj = AxleImpl.setAxleVehDetails_db_to_dobj(null, owner_dobj.getRegn_no(), owner_dobj.getState_cd(), owner_dobj.getOff_cd());
	                if (axleDetailsDobj != null) {
	                    owner_dobj.setAxleDobj(axleDetailsDobj);
	                    owner_dobj.setNumberOfTyres(axleDetailsDobj.getTf_Front_tyre() + axleDetailsDobj.getTf_Other_tyre() + axleDetailsDobj.getTf_Rear_tyre() + axleDetailsDobj.getTf_Tandem_tyre());
	                }
	                InsDobj insDobj = InsImpl.set_ins_dtls_db_to_dobj(owner_dobj.getRegn_no(), null, owner_dobj.getState_cd(), owner_dobj.getOff_cd());
	                if (insDobj != null) {
	                    owner_dobj.setInsDobj(insDobj);
	                }
	                // auction Details
	                AuctionDobj auctionDobj = new AuctionImpl().getDetailsFromVtAuction(owner_dobj.getChasi_no().trim().toUpperCase(), null);
	                owner_dobj.setAuctionDobj(auctionDobj);
	            }
	        } finally {
	            if (tmgr != null) {
	                try {
	                    tmgr.release();
	                } catch (Exception e) {
	                    LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	                }
	            }
	        }

	        return owner_dobj;
	    }
	 
	 public String isChasiAlreadyExist(TransactionManagerOne tmgr, String chasiNo, String regnType) throws VahanException {
	        PreparedStatement ps = null;
	        RowSet rs = null;
	        String message = null;
	        String sql = null;
	        if (chasiNo != null) {
	            chasiNo = chasiNo.toUpperCase();
	        }
	        String appl_no = null;
	        String regn_no = null;
	        try {
	            sql = "SELECT chasi_no,appl_no,regn_no FROM " + TableList.VA_OWNER + " WHERE chasi_no=? limit 1";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, chasiNo);
	            rs = tmgr.fetchDetachedRowSet_No_release();
	            if (rs.next()) {
	                appl_no = rs.getString("appl_no");
	                regn_no = rs.getString("regn_no");
	                if (!regn_no.equals("NEW")) {
	                    message = ("Chassis No [" + chasiNo + "] is Already in Use [Appl.No: " + appl_no + "],[ Regn.No: " + regn_no + "],Please Use Different Chassis NO");
	                } else {
	                    message = ("Chassis NO [" + chasiNo + "] is Already in Use [ Appl.No: " + appl_no + "],Please Use Different Chassis NO");
	                }
	                return message;

	            } else {
	                if (!(TableConstants.VM_REGN_TYPE_OTHER_STATE_VEHICLE + "," + TableConstants.VM_REGN_TYPE_OTHER_DISTRICT_VEHICLE).contains(regnType)) {

	                    sql = "SELECT chasi_no,regn_no FROM " + TableList.VT_OWNER + " WHERE chasi_no=? and status != 'C' limit 1";
	                    ps = tmgr.prepareStatement(sql);
	                    ps.setString(1, chasiNo);
	                    rs = tmgr.fetchDetachedRowSet_No_release();
	                    if (rs.next()) {
	                        regn_no = rs.getString("regn_no");
	                        message = ("Chassis NO [" + chasiNo + "] is Already in Use [Regn.No: " + regn_no + "],Please Use Different Chassis NO");
	                        return message;
	                    }
	                }
	                if (!(TableConstants.VM_REGN_TYPE_OTHER_STATE_VEHICLE + "," + TableConstants.VM_REGN_TYPE_OTHER_DISTRICT_VEHICLE + "," + TableConstants.VM_REGN_TYPE_TEMPORARY).contains(regnType)) {
	                    //Except Stock Transfer Cases
	                    sql = "SELECT chasi_no,appl_no,temp_regn_no,purpose  FROM " + TableList.VT_OWNER_TEMP + " WHERE chasi_no=? order by op_dt desc limit 1";
	                    ps = tmgr.prepareStatement(sql);
	                    ps.setString(1, chasiNo);
	                    rs = tmgr.fetchDetachedRowSet_No_release();
	                    if (rs.next() && rs.getString("purpose") != null && !rs.getString("purpose").equalsIgnoreCase(TableConstants.STOCK_TRANSFER)) {
	                        appl_no = rs.getString("appl_no");
	                        String tempRegno = rs.getString("temp_regn_no");
	                        message = ("Chassis NO [" + chasiNo + "] is Already in Use [Appl.No: " + appl_no + "] ,[Temp Regn.No: " + tempRegno + "],Please Use Different Chassis NO");
	                        return message;
	                    }
	                }
	            }


	        } catch (Exception ex) {
	            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	            throw new VahanException("Something Went Wrong during Fetching Details of CHASI NO, Please Contact to the System Administrator.");
	        }

	        return message;
	    }

}

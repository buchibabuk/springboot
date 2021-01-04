package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
import nic.vahan5.reg.form.bean.fancy.RetenRegnNo_dobj;
import nic.vahan5.reg.form.dobj.OwnerIdentificationDobj;
import nic.vahan5.reg.form.dobj.Owner_dobj;
import nic.vahan5.reg.form.dobj.Owner_temp_dobj;
import nic.vahan5.reg.form.dobj.TempRegDobj;
import nic.vahan5.reg.form.model.AdvanceRegnNoDobjModel;
import nic.vahan5.reg.server.CommonUtils;
import nic.vahan5.reg.server.ServerUtil;
import nic.vahan5.reg.server.ServerUtility;
import nic.vahan5.reg.server.TableConstants;
@Service
public class NewImplementation {
	
	@Autowired
	ServerUtil serverUtil;
	
	@Autowired
	ServerUtility serverUtility;
	@Autowired
	 OwnerImpl ownerImpl;
	 private static final Logger LOGGER = Logger.getLogger(NewImplementation.class);
	 public static String getTaxModes(Owner_dobj dobj) {
	        String rqrd_tax_modes = null;
	        if (dobj != null && dobj.getTaxModList() != null && !dobj.getTaxModList().isEmpty()) {
	            rqrd_tax_modes = dobj.getTaxModList().toString();
	            rqrd_tax_modes = rqrd_tax_modes.replace("[", TableConstants.EMPTY_STRING).replace("]", TableConstants.EMPTY_STRING).replaceAll(" ", TableConstants.EMPTY_STRING);
	        }
	        return rqrd_tax_modes;
	    }
	 
	 
	 public static void insertHomologationDetails(TransactionManagerOne tmgr, Owner_dobj owner_dobj, String applNo, String stateCd, int offCd) throws VahanException {
	        PreparedStatement ps = null;
	        String sql = null;

	        try {
	            if (owner_dobj != null && applNo != null && !applNo.equals(TableConstants.EMPTY_STRING)) {
	                String selectSQL = "SELECT * FROM " + TableList.VA_HOMO_DETAILS + " where  appl_no = ? ";
	                ps = tmgr.prepareStatement(selectSQL);
	                ps.setString(1, applNo);
	                ResultSet rs = tmgr.fetchDetachedRowSet_No_release();
	                if (!rs.next()) {
	                    sql = "INSERT INTO " + TableList.VA_HOMO_DETAILS + "( \n"
	                            + " state_cd, off_cd, appl_no, chasi_no, eng_no, maker, maker_model, \n"
	                            + " vch_purchase_as, hp, seat_cap, unld_wt, ld_wt, fuel, body_type, \n"
	                            + " no_cyl, wheelbase, norms, cubic_cap, length, width, height, color, \n"
	                            + " manu_mon, manu_yr, sale_amt, f_axle_descp, r_axle_descp, o_axle_descp, \n"
	                            + " t_axle_descp, f_axle_weight, r_axle_weight, o_axle_weight, t_axle_weight, \n"
	                            + " op_dt, model_manu_loc, gcw)\n"
	                            + " VALUES (?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current_timestamp, ?, ?) ";

	                    ps = tmgr.prepareStatement(sql);

	                    int i = 1;

	                    ps.setString(i++, stateCd);
	                    ps.setInt(i++, offCd);
	                    ps.setString(i++, applNo);
	                    ps.setString(i++, owner_dobj.getChasi_no());
	                    ps.setString(i++, owner_dobj.getEng_no());
	                    ps.setInt(i++, owner_dobj.getMaker());
	                    ps.setString(i++, owner_dobj.getMaker_model());
	                    ps.setString(i++, owner_dobj.getVch_purchase_as());
	                    ps.setFloat(i++, owner_dobj.getHp() == null ? 0l : owner_dobj.getHp());
	                    ps.setInt(i++, owner_dobj.getSeat_cap());
	                    ps.setInt(i++, owner_dobj.getUnld_wt());
	                    ps.setInt(i++, owner_dobj.getLd_wt());
	                    ps.setInt(i++, owner_dobj.getFuel());
	                    ps.setString(i++, owner_dobj.getBody_type());
	                    ps.setInt(i++, owner_dobj.getNo_cyl());
	                    ps.setInt(i++, owner_dobj.getWheelbase());
	                    ps.setInt(i++, owner_dobj.getNorms());
	                    ps.setFloat(i++, owner_dobj.getCubic_cap());
	                    ps.setInt(i++, owner_dobj.getLength());
	                    ps.setInt(i++, owner_dobj.getWidth());
	                    ps.setInt(i++, owner_dobj.getHeight());
	                    ps.setString(i++, owner_dobj.getColor());
	                    ps.setInt(i++, owner_dobj.getManu_mon() == null ? 0 : owner_dobj.getManu_mon());
	                    ps.setInt(i++, owner_dobj.getManu_yr() == null ? 0 : owner_dobj.getManu_yr());
	                    ps.setInt(i++, owner_dobj.getSale_amt());
	                    if (owner_dobj.getAxleDobj() != null) {
	                        ps.setString(i++, owner_dobj.getAxleDobj().getTf_Front1());
	                        ps.setString(i++, owner_dobj.getAxleDobj().getTf_Rear1());
	                        ps.setString(i++, owner_dobj.getAxleDobj().getTf_Other1());
	                        ps.setString(i++, owner_dobj.getAxleDobj().getTf_Tandem1());
	                        ps.setInt(i++, owner_dobj.getAxleDobj().getTf_Front());
	                        ps.setInt(i++, owner_dobj.getAxleDobj().getTf_Rear());
	                        ps.setInt(i++, owner_dobj.getAxleDobj().getTf_Other());
	                        ps.setInt(i++, owner_dobj.getAxleDobj().getTf_Tandem());
	                    } else {
	                        ps.setString(i++, TableConstants.EMPTY_STRING);
	                        ps.setString(i++, TableConstants.EMPTY_STRING);
	                        ps.setString(i++, TableConstants.EMPTY_STRING);
	                        ps.setString(i++, TableConstants.EMPTY_STRING);
	                        ps.setInt(i++, 0);
	                        ps.setInt(i++, 0);
	                        ps.setInt(i++, 0);
	                        ps.setInt(i++, 0);

	                    }
	                    ps.setString(i++, owner_dobj.getModelManuLocCode());
	                    ps.setInt(i++, owner_dobj.getGcw());
	                    ps.executeUpdate();
	                }
	            }
	        } catch (SQLException ex) {
	            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	            throw new VahanException("Error in inserting homo details");
	        }
	    }
	 public  void updateAdvanceRegNoDetails(AdvanceRegnNoDobjModel dobj, Owner_dobj ownDobj, TransactionManagerOne tmgr) throws VahanException {

	        try {
	            String sql = " Select * from " + TableList.FANCY_VT_FANCY_REGISTER
	                    + " where regn_no=? ";
	            PreparedStatement ps = tmgr.prepareStatement(sql);
	            ps.setString(1, dobj.getRegn_no());
	            RowSet rs = tmgr.fetchDetachedRowSet_No_release();
	            if (rs.next()) {
	                if (rs.getString("veh_type") != null && !rs.getString("veh_type").isEmpty()) {
	                    int vehType = Integer.parseInt(rs.getString("veh_type"));
	                    if (!(vehType == 0 || vehType == -1) && vehType != serverUtil.VehicleClassType(ownDobj.getVh_class())) {
	                        String message = TableConstants.EMPTY_STRING;
	                        if (vehType == 1) {
	                            message = "Fancy Number is Booked For Transport Vehicle";
	                        } else {
	                            message = "Fancy Number is Booked For Non-Transport Vehicle";
	                        }
	                        throw new VahanException(message);
	                    }
	                }

	            }

	            sql = "Update " + TableList.VT_ADVANCE_REGN_NO
	                    + " set regn_appl_no=? where regn_no=?";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, dobj.getRegn_appl_no());
	            ps.setString(2, dobj.getRegn_no());
	            ps.executeUpdate();


	        } catch (VahanException ex) {
	            throw ex;
	        } catch (Exception ex) {
	            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	            throw new VahanException("Error in Updation of Fancy Details");
	        }

	    }
	 public void updateRetenRegNoDetails(RetenRegnNo_dobj dobj, TransactionManagerOne tmgr) throws Exception {
	        String sql = "Update " + TableList.VT_SURRENDER_RETENTION
	                + " set regn_appl_no=? where old_regn_no=? and rcpt_no=? ";
	        PreparedStatement ps = tmgr.prepareStatement(sql);
	        ps.setString(1, dobj.getRegn_appl_no());
	        ps.setString(2, dobj.getRegn_no());
	        ps.setString(3, dobj.getRecp_no());
	        ps.executeUpdate();

	    }
	 public void insertIntoVhaOwner(TransactionManagerOne tmgr, String appl_no, String empCode) throws VahanException {
	        PreparedStatement ps = null;
	        String sql = null;

	        try {

	            sql = "INSERT INTO  " + TableList.VHA_OWNER
	                    + " SELECT current_timestamp as moved_on , ? as moved_by, state_cd, off_cd, appl_no, regn_no, regn_dt, purchase_dt, owner_sr, "
	                    + "        owner_name, f_name, c_add1, c_add2, c_add3, c_district, c_pincode, "
	                    + "        c_state, p_add1, p_add2, p_add3, p_district, p_pincode, p_state, "
	                    + "        owner_cd, regn_type, vh_class, chasi_no, eng_no, maker, maker_model, "
	                    + "        body_type, no_cyl, hp, seat_cap, stand_cap, sleeper_cap, unld_wt, "
	                    + "        ld_wt, gcw, fuel, color, manu_mon, manu_yr, norms, wheelbase, "
	                    + "        cubic_cap, floor_area, ac_fitted, audio_fitted, video_fitted, "
	                    + "        vch_purchase_as, vch_catg, dealer_cd, sale_amt, laser_code, garage_add, "
	                    + "        length, width, height, regn_upto, fit_upto, annual_income, imported_vch, "
	                    + "        other_criteria, pmt_type, pmt_catg, rqrd_tax_modes, op_dt "
	                    + "  FROM  " + TableList.VA_OWNER
	                    + " where appl_no=?";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, empCode);
	            ps.setString(2, appl_no);
	            ps.executeUpdate();

	            insertIntoVhaOwnerId(tmgr, appl_no, empCode);
	            insertIntoVhaOwnerTemp(tmgr, appl_no, empCode);
	            insertIntoTempRegnDetails(tmgr, appl_no, empCode);
	            insertIntoVhaSideTrailer(tmgr, appl_no, empCode);
	        } catch (Exception ex) {
	            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	            throw new VahanException(TableConstants.SomthingWentWrong);
	        }
	    }//end of insertIntoVhaOwner

	 public static void insertIntoVhaOwnerId(TransactionManagerOne tmgr, String appl_no, String empCode) throws SQLException {

	        PreparedStatement ps = null;

	        String sql = "INSERT INTO  " + TableList.VHA_OWNER_IDENTIFICATION
	                + "  SELECT current_timestamp as moved_on, ? moved_by,state_cd,off_cd,appl_no, regn_no, mobile_no, email_id, pan_no, aadhar_no, passport_no, "
	                + "  ration_card_no, voter_id, dl_no, verified_on, owner_ctg,op_dt,dept_cd "
	                + "  FROM  " + TableList.VA_OWNER_IDENTIFICATION
	                + " where appl_no=?";
	        ps = tmgr.prepareStatement(sql);
	        ps.setString(1, empCode);
	        ps.setString(2, appl_no);
	        ps.executeUpdate();
	    }
	 public static void insertIntoVhaOwnerTemp(TransactionManagerOne tmgr, String appl_no, String empCode) throws SQLException {

	        PreparedStatement ps = null;

	        String sql = "INSERT INTO  vha_owner_temp"
	                + "  SELECT current_timestamp as moved_on, ? as moved_by, state_cd, off_cd,appl_no, temp_regn_no, state_cd_to, off_cd_to,"
	                + "  purpose,body_building,op_dt,temp_regn_type"
	                + "  FROM  va_owner_temp"
	                + " where appl_no=?";
	        ps = tmgr.prepareStatement(sql);
	        ps.setString(1, empCode);
	        ps.setString(2, appl_no);
	        ps.executeUpdate();
	    }

	  public static void insertIntoTempRegnDetails(TransactionManagerOne tmgr, String appl_no, String empCode) throws SQLException {
	        PreparedStatement ps = null;

	        String sql = "INSERT INTO  " + TableList.VHA_TMP_REGN_DTL
	                + "  SELECT current_timestamp as moved_on ,? as moved_by,state_cd,off_cd,appl_no, regn_no, tmp_off_cd, regn_auth, tmp_state_cd, tmp_regn_no, "
	                + "       tmp_regn_dt, tmp_valid_upto, dealer_cd,  op_dt"
	                + "  FROM  " + TableList.VA_TMP_REGN_DTL
	                + " where appl_no=?";
	        ps = tmgr.prepareStatement(sql);
	        ps.setString(1, empCode);
	        ps.setString(2, appl_no);
	        ps.executeUpdate();

	    }

	  public static void insertIntoVhaSideTrailer(TransactionManagerOne tmgr, String appl_no, String empCode) throws SQLException {
	        PreparedStatement ps = null;
	        String query = "INSERT INTO " + TableList.VHA_SIDE_TRAILER + "(\n"
	                + "   moved_on, moved_by, state_cd, off_cd, appl_no, regn_no, link_regn_no,  op_dt) \n"
	                + "   SELECT current_timestamp,?, state_cd, off_cd, appl_no, regn_no, link_regn_no, op_dt\n"
	                + "  FROM " + TableList.VA_SIDE_TRAILER + " where appl_no = ?";
	        ps = tmgr.prepareStatement(query);
	        ps.setString(1, empCode);
	        ps.setString(2, appl_no);
	        ps.executeUpdate();
	    }
	  public void insertintoVhaHomologationDetails(TransactionManagerOne tmgr, String applNo, String empCode) throws VahanException {
	        PreparedStatement ps = null;
	        String sql = null;

	        try {

	            sql = "INSERT INTO " + TableList.VHA_HOMO_DETAILS + "(moved_on, moved_by, state_cd, off_cd, appl_no, chasi_no, eng_no, \n"
	                    + " maker, maker_model, vch_purchase_as, hp, seat_cap, unld_wt, ld_wt, \n"
	                    + " fuel, body_type, no_cyl, wheelbase, norms, cubic_cap, length, \n"
	                    + " width, height, color, manu_mon, manu_yr, sale_amt, f_axle_descp, \n"
	                    + " r_axle_descp, o_axle_descp, t_axle_descp, f_axle_weight, r_axle_weight, \n"
	                    + " o_axle_weight, t_axle_weight, op_dt, model_manu_loc, gcw) \n"
	                    + " SELECT current_timestamp, ? , state_cd, off_cd, appl_no, chasi_no, eng_no,  maker, maker_model, vch_purchase_as, hp, seat_cap, unld_wt, ld_wt, \n"
	                    + " fuel, body_type, no_cyl, wheelbase, norms, cubic_cap, length, width, height, color, manu_mon, manu_yr, sale_amt, f_axle_descp, \n "
	                    + " r_axle_descp, o_axle_descp, t_axle_descp, f_axle_weight, r_axle_weight, o_axle_weight, t_axle_weight, op_dt, model_manu_loc, gcw FROM " + TableList.VA_HOMO_DETAILS + " where appl_no = ? ";


	            ps = tmgr.prepareStatement(sql);

	            int i = 1;

	            ps.setString(i++, empCode);
	            ps.setString(i++, applNo);
	            ps.executeUpdate();
	        } catch (SQLException ex) {
	            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	            throw new VahanException("Error in inserting homo details");
	        }
	    }

	  public  void insertOrUpdateVaOwner(TransactionManagerOne tmgr, Owner_dobj owner_dobj, String empCode,
	            int purCd, int offCode, String userStateCode, int actionCode) throws VahanException, SQLException {

	        PreparedStatement ps = tmgr.prepareStatement("Select * from " + TableList.VA_OWNER
	                + " where appl_no=?");
	        ps.setString(1, owner_dobj.getAppl_no());
	        RowSet rs = tmgr.fetchDetachedRowSet_No_release();

	        if (rs.next()) {
	            insertIntoVhaOwner(tmgr, owner_dobj.getAppl_no(), empCode);
	            updateVaOwner(tmgr, owner_dobj, purCd, offCode, userStateCode);
	        } else {
	            insertVaOwner(tmgr, owner_dobj, actionCode, userStateCode, offCode);
	        }
	        if (owner_dobj.getPmt_type() > 0 || owner_dobj.getPmt_catg() > 0 || (owner_dobj.getServicesType() != null && !owner_dobj.getServicesType().equals("") && Integer.parseInt(owner_dobj.getServicesType()) > 0)) {
	            insertOrUpdateVaPermitNewRegn(tmgr, owner_dobj, empCode);
	        }

	    }
	  public void insertOrUpdateVaPermitNewRegn(TransactionManagerOne tmgr, Owner_dobj owner_dobj, String empCode) throws VahanException {
	        try {
	            String qry = "select * from va_permit_new_regn where appl_no=?";
	            PreparedStatement ps = tmgr.prepareStatement(qry);
	            ps.setString(1, owner_dobj.getAppl_no());
	            RowSet rs = tmgr.fetchDetachedRowSet_No_release();
	            if (rs.next()) {
	                serverUtility.insertIntoVhaTable(tmgr, owner_dobj.getAppl_no(), empCode, "vha_permit_new_regn", "va_permit_new_regn");
	                qry = "UPDATE vahan4.va_permit_new_regn\n"
	                        + "   SET pmt_type=?, pmt_catg=?, service_type=?, \n"
	                        + "       emp_code=?, op_dt=current_timestamp,region_covered=?\n"
	                        + " WHERE appl_no=?";
	                ps = tmgr.prepareStatement(qry);
	                ps.setInt(1, owner_dobj.getPmt_type());
	                ps.setInt(2, owner_dobj.getPmt_catg());
	                if (owner_dobj.getServicesType() != null && !owner_dobj.getServicesType().equals("")) {
	                    ps.setInt(3, Integer.parseInt(owner_dobj.getServicesType()));
	                } else {
	                    ps.setInt(3, -1);
	                }
	                ps.setString(4, empCode);
	                ps.setString(5, owner_dobj.getRegion_covered_str());
	                ps.setString(6, owner_dobj.getAppl_no());
	                ps.executeUpdate();
	            } else {
	                insertIntoVaPermitNew(tmgr, owner_dobj, empCode);
	            }

	        } catch (Exception ex) {
	            LOGGER.error(ex.toString() + "" + ex.getStackTrace()[0]);
	            throw new VahanException(TableConstants.SomthingWentWrong);
	        }

	    }
	  public static void insertIntoVaPermitNew(TransactionManagerOne tmgr, Owner_dobj owner_dobj, String empCode) throws VahanException {
	        try {
	            String qry = "INSERT INTO vahan4.va_permit_new_regn(\n"
	                    + "            state_cd, off_cd, appl_no, pmt_type, pmt_catg, service_type, \n"
	                    + "            emp_code, op_dt, region_covered)\n"
	                    + "    VALUES (?, ?, ?, ?, ?, ?, \n"
	                    + "            ?, current_timestamp,?);";
	            PreparedStatement ps = tmgr.prepareStatement(qry);
	            ps.setString(1, owner_dobj.getState_cd());
	            ps.setInt(2, owner_dobj.getOff_cd());
	            ps.setString(3, owner_dobj.getAppl_no());
	            ps.setInt(4, owner_dobj.getPmt_type());
	            ps.setInt(5, owner_dobj.getPmt_catg());
	            if (owner_dobj.getServicesType() != null && !owner_dobj.getServicesType().equals("")) {
	                ps.setInt(6, Integer.parseInt(owner_dobj.getServicesType()));
	            } else {
	                ps.setInt(6, -1);
	            }
	            ps.setString(7, empCode);
	            ps.setString(8, owner_dobj.getRegion_covered_str());
	            ps.executeUpdate();
	        } catch (Exception ex) {
	            LOGGER.error(ex.toString() + "" + ex.getStackTrace()[0]);
	            throw new VahanException(TableConstants.SomthingWentWrong);
	        }
	    }

	    
	    
	  public  void updateVaOwner(TransactionManagerOne tmgr, Owner_dobj owner_dobj, int purCd, int offCode, String userStateCode) throws VahanException {
	        PreparedStatement ps = null;
	        String sql = null;
	        String rqrdTaxModesVar = " , rqrd_tax_modes = ?";
	        boolean flgOtherState = false;

	        try {

	            if ((owner_dobj.getRegn_type().equals(TableConstants.VM_REGN_TYPE_OTHER_STATE_VEHICLE)
	                    || owner_dobj.getRegn_type().equals(TableConstants.VM_REGN_TYPE_OTHER_DISTRICT_VEHICLE))
	                    && owner_dobj.getRegn_dt() != null) {
	                flgOtherState = true;
	            }

	            /**
	             * For other state vehicle/other district Regn Dt will previous regn
	             * date
	             */
	            if (flgOtherState) {
	                sql = "UPDATE " + TableList.VA_OWNER
	                        + "   SET state_cd=?, off_cd=?, appl_no=?,"
	                        + "       regn_dt=?,"
	                        + "  purchase_dt=?, "
	                        + "       owner_sr=?, owner_name=?, f_name=?, c_add1=?, c_add2=?, c_add3=?, "
	                        + "       c_district=?, c_pincode=?, c_state=?, p_add1=?, p_add2=?, p_add3=?, "
	                        + "       p_district=?, p_pincode=?, p_state=?, owner_cd=?, regn_type=?, "
	                        + "       vh_class=?, chasi_no=?, eng_no=?, maker=?, maker_model=?, body_type=?, "
	                        + "       no_cyl=?, hp=?, seat_cap=?, stand_cap=?, sleeper_cap=?, unld_wt=?, "
	                        + "       ld_wt=?, gcw=?, fuel=?, color=?, manu_mon=?, manu_yr=?, norms=?, "
	                        + "       wheelbase=?, cubic_cap=?, floor_area=?, ac_fitted=?, audio_fitted=?, "
	                        + "       video_fitted=?, vch_purchase_as=?, vch_catg=?, dealer_cd=?, sale_amt=?, "
	                        + "       laser_code=?, garage_add=?, length=?, width=?, height=?, regn_upto=?, "
	                        + "       fit_upto=?, annual_income=?, imported_vch=?, other_criteria=?,"
	                        + "       pmt_type=?,pmt_catg=? ,rqrd_tax_modes=?,op_dt=current_timestamp"
	                        + " WHERE appl_no=?";


	                ps = tmgr.prepareStatement(sql);
	                int i = 1;
	                ps.setString(i++, owner_dobj.getState_cd());
	                ps.setInt(i++, owner_dobj.getOff_cd());
	                ps.setString(i++, owner_dobj.getAppl_no());
	                ps.setDate(i++, new java.sql.Date(owner_dobj.getRegn_dt().getTime()));

	                ps.setDate(i++, owner_dobj.getPurchase_dt() == null ? null : new java.sql.Date(owner_dobj.getPurchase_dt().getTime()));
	                ps.setInt(i++, owner_dobj.getOwner_sr());
	                ps.setString(i++, owner_dobj.getOwner_name());
	                ps.setString(i++, owner_dobj.getF_name());
	                ps.setString(i++, owner_dobj.getC_add1());
	                ps.setString(i++, owner_dobj.getC_add2());
	                ps.setString(i++, owner_dobj.getC_add3());
	                ps.setInt(i++, owner_dobj.getC_district());
	                ps.setInt(i++, owner_dobj.getC_pincode());
	                ps.setString(i++, owner_dobj.getC_state());
	                ps.setString(i++, owner_dobj.getP_add1());
	                ps.setString(i++, owner_dobj.getP_add2());
	                ps.setString(i++, owner_dobj.getP_add3());
	                ps.setInt(i++, owner_dobj.getP_district());
	                ps.setInt(i++, owner_dobj.getP_pincode());
	                ps.setString(i++, owner_dobj.getP_state());
	                ps.setInt(i++, owner_dobj.getOwner_cd());
	                ps.setString(i++, owner_dobj.getRegn_type());
	                ps.setInt(i++, owner_dobj.getVh_class());
	                ps.setString(i++, owner_dobj.getChasi_no());
	                ps.setString(i++, owner_dobj.getEng_no());
	                ps.setInt(i++, owner_dobj.getMaker());
	                ps.setString(i++, owner_dobj.getMaker_model());
	                ps.setString(i++, owner_dobj.getBody_type());
	                ps.setInt(i++, owner_dobj.getNo_cyl());
	                ps.setFloat(i++, owner_dobj.getHp());
	                ps.setInt(i++, owner_dobj.getSeat_cap());
	                ps.setInt(i++, owner_dobj.getStand_cap());
	                ps.setInt(i++, owner_dobj.getSleeper_cap());
	                ps.setInt(i++, owner_dobj.getUnld_wt());
	                ps.setInt(i++, owner_dobj.getLd_wt());
	                ps.setInt(i++, owner_dobj.getGcw());
	                ps.setInt(i++, owner_dobj.getFuel());
	                ps.setString(i++, owner_dobj.getColor());
	                ps.setInt(i++, owner_dobj.getManu_mon());
	                ps.setInt(i++, owner_dobj.getManu_yr());
	                ps.setInt(i++, owner_dobj.getNorms());
	                ps.setInt(i++, owner_dobj.getWheelbase());
	                ps.setFloat(i++, owner_dobj.getCubic_cap());
	                ps.setFloat(i++, owner_dobj.getFloor_area());
	                ps.setString(i++, owner_dobj.getAc_fitted());
	                ps.setString(i++, owner_dobj.getAudio_fitted());
	                ps.setString(i++, owner_dobj.getVideo_fitted());
	                ps.setString(i++, owner_dobj.getVch_purchase_as());
	                ps.setString(i++, owner_dobj.getVch_catg());
	                ps.setString(i++, owner_dobj.getDealer_cd());
	                ps.setInt(i++, owner_dobj.getSale_amt());
	                ps.setString(i++, owner_dobj.getLaser_code());
	                ps.setString(i++, owner_dobj.getGarage_add());
	                ps.setInt(i++, owner_dobj.getLength());
	                ps.setInt(i++, owner_dobj.getWidth());
	                ps.setInt(i++, owner_dobj.getHeight());
	                ps.setDate(i++, owner_dobj.getRegn_upto() == null ? null : new java.sql.Date(owner_dobj.getRegn_upto().getTime()));
	                ps.setDate(i++, owner_dobj.getFit_upto() == null ? null : new java.sql.Date(owner_dobj.getFit_upto().getTime()));
	                ps.setInt(i++, owner_dobj.getAnnual_income());
	                ps.setString(i++, owner_dobj.getImported_vch() == null ? "N" : owner_dobj.getImported_vch());
	                ps.setInt(i++, owner_dobj.getOther_criteria());
	                ps.setInt(i++, owner_dobj.getPmt_type());
	                ps.setInt(i++, owner_dobj.getPmt_catg());
	                ps.setString(i++, owner_dobj.getRqrd_tax_modes());
	                ps.setString(i++, owner_dobj.getAppl_no());
	                ps.executeUpdate();

	            } else {
	                if (purCd == TableConstants.VM_TRANSACTION_MAST_DEALER_TEMP_VEHICLE) {
	                    int newApplOffCd = serverUtility.getOfficeCdForDealerTempAppl(owner_dobj.getAppl_no(), owner_dobj.getState_cd(), "taxMode", offCode);
	                    if (newApplOffCd != 0) {
	                        rqrdTaxModesVar = "";
	                    }
	                }
	                sql = "UPDATE " + TableList.VA_OWNER
	                        + "   SET appl_no=?,"
	                        //                        + "       regn_dt=?,"
	                        + "  purchase_dt=?, "
	                        + "       owner_sr=?, owner_name=?, f_name=?, c_add1=?, c_add2=?, c_add3=?, "
	                        + "       c_district=?, c_pincode=?, c_state=?, p_add1=?, p_add2=?, p_add3=?, "
	                        + "       p_district=?, p_pincode=?, p_state=?, owner_cd=?, regn_type=?, "
	                        + "       vh_class=?, chasi_no=?, eng_no=?, maker=?, maker_model=?, body_type=?, "
	                        + "       no_cyl=?, hp=?, seat_cap=?, stand_cap=?, sleeper_cap=?, unld_wt=?, "
	                        + "       ld_wt=?, gcw=?, fuel=?, color=?, manu_mon=?, manu_yr=?, norms=?, "
	                        + "       wheelbase=?, cubic_cap=?, floor_area=?, ac_fitted=?, audio_fitted=?, "
	                        + "       video_fitted=?, vch_purchase_as=?, vch_catg=?, dealer_cd=?, sale_amt=?, "
	                        + "       laser_code=?, garage_add=?, length=?, width=?, height=?, regn_upto=?, "
	                        + "       fit_upto=?, annual_income=?, imported_vch=?, other_criteria=?,"
	                        + "       pmt_type=?,pmt_catg=? " + rqrdTaxModesVar + " ,op_dt=current_timestamp"
	                        + " WHERE appl_no=?";


	                ps = tmgr.prepareStatement(sql);
	                int i = 1;
	                ps.setString(i++, owner_dobj.getAppl_no());
	                // ps.setDate(i++, new java.sql.Date(owner_dobj.getRegn_dt().getTime()));
	                ps.setDate(i++, owner_dobj.getPurchase_dt() == null ? null : new java.sql.Date(owner_dobj.getPurchase_dt().getTime()));
	                ps.setInt(i++, owner_dobj.getOwner_sr());
	                ps.setString(i++, owner_dobj.getOwner_name());
	                ps.setString(i++, owner_dobj.getF_name());
	                ps.setString(i++, owner_dobj.getC_add1());
	                ps.setString(i++, owner_dobj.getC_add2());
	                ps.setString(i++, owner_dobj.getC_add3());
	                ps.setInt(i++, owner_dobj.getC_district());
	                ps.setInt(i++, owner_dobj.getC_pincode());
	                ps.setString(i++, owner_dobj.getC_state());
	                ps.setString(i++, owner_dobj.getP_add1());
	                ps.setString(i++, owner_dobj.getP_add2());
	                ps.setString(i++, owner_dobj.getP_add3());
	                ps.setInt(i++, owner_dobj.getP_district());
	                ps.setInt(i++, owner_dobj.getP_pincode());
	                ps.setString(i++, owner_dobj.getP_state());
	                ps.setInt(i++, owner_dobj.getOwner_cd());
	                if (CommonUtils.isNullOrBlank(owner_dobj.getRegn_type()) || "0".equals(owner_dobj.getRegn_type())) {
	                    throw new VahanException("Registration Type can not be blank.");
	                }
	                ps.setString(i++, owner_dobj.getRegn_type());
	                ps.setInt(i++, owner_dobj.getVh_class());
	                ps.setString(i++, owner_dobj.getChasi_no());
	                ps.setString(i++, owner_dobj.getEng_no());
	                ps.setInt(i++, owner_dobj.getMaker());
	                ps.setString(i++, owner_dobj.getMaker_model());
	                ps.setString(i++, owner_dobj.getBody_type());
	                ps.setInt(i++, owner_dobj.getNo_cyl());
	                ps.setFloat(i++, owner_dobj.getHp());
	                ps.setInt(i++, owner_dobj.getSeat_cap());
	                ps.setInt(i++, owner_dobj.getStand_cap());
	                ps.setInt(i++, owner_dobj.getSleeper_cap());
	                ps.setInt(i++, owner_dobj.getUnld_wt());
	                ps.setInt(i++, owner_dobj.getLd_wt());
	                ps.setInt(i++, owner_dobj.getGcw());
	                ps.setInt(i++, owner_dobj.getFuel());
	                ps.setString(i++, owner_dobj.getColor());
	                if (owner_dobj.getManu_mon() != null) {
	                    ps.setInt(i++, owner_dobj.getManu_mon());
	                } else {
	                    ps.setInt(i++, 0);
	                }
	                if (owner_dobj.getManu_yr() != null) {
	                    ps.setInt(i++, owner_dobj.getManu_yr());
	                } else {
	                    ps.setInt(i++, 0);
	                }
	                ps.setInt(i++, owner_dobj.getNorms());
	                ps.setInt(i++, owner_dobj.getWheelbase());
	                ps.setFloat(i++, owner_dobj.getCubic_cap());
	                ps.setFloat(i++, owner_dobj.getFloor_area());
	                ps.setString(i++, owner_dobj.getAc_fitted());
	                ps.setString(i++, owner_dobj.getAudio_fitted());
	                ps.setString(i++, owner_dobj.getVideo_fitted());
	                ps.setString(i++, owner_dobj.getVch_purchase_as());
	                ps.setString(i++, owner_dobj.getVch_catg());
	                ps.setString(i++, owner_dobj.getDealer_cd());
	                ps.setInt(i++, owner_dobj.getSale_amt());
	                ps.setString(i++, owner_dobj.getLaser_code());
	                ps.setString(i++, owner_dobj.getGarage_add());
	                ps.setInt(i++, owner_dobj.getLength());
	                ps.setInt(i++, owner_dobj.getWidth());
	                ps.setInt(i++, owner_dobj.getHeight());
	                ps.setDate(i++, owner_dobj.getRegn_upto() == null ? null : new java.sql.Date(owner_dobj.getRegn_upto().getTime()));
	                ps.setDate(i++, owner_dobj.getFit_upto() == null ? null : new java.sql.Date(owner_dobj.getFit_upto().getTime()));
	                ps.setInt(i++, owner_dobj.getAnnual_income());
	                ps.setString(i++, owner_dobj.getImported_vch() == null ? "N" : owner_dobj.getImported_vch());
	                ps.setInt(i++, owner_dobj.getOther_criteria());
	                ps.setInt(i++, owner_dobj.getPmt_type());
	                ps.setInt(i++, owner_dobj.getPmt_catg());
	                if (!CommonUtils.isNullOrBlank(rqrdTaxModesVar)) {
	                    ps.setString(i++, owner_dobj.getRqrd_tax_modes());
	                }
	                ps.setString(i++, owner_dobj.getAppl_no());

	                ps.executeUpdate();
	            }

	            updateVaOwnerId(tmgr, owner_dobj.getOwner_identity());
	            if (owner_dobj.getDob_temp() != null) {
	                updateVaOwnerTemp(tmgr, owner_dobj.getDob_temp());
	            }

	            if (owner_dobj.getTempReg() != null) {
	                insertOrupdateVaTempRegnDetails(tmgr, owner_dobj.getTempReg(), userStateCode, offCode);
	            }
	            if (!CommonUtils.isNullOrBlank(owner_dobj.getLinkedRegnNo())) {
	                String query = "SELECT appl_no from " + TableList.VA_SIDE_TRAILER + " where appl_no = ?";
	                ps = tmgr.prepareStatement(query);
	                ps.setString(1, owner_dobj.getAppl_no());
	                RowSet rs = tmgr.fetchDetachedRowSet_No_release();
	                if (rs.next()) {
	                    updateVaSideTrailer(tmgr, owner_dobj);
	                } else {
	                    insertVaSideTrailer(tmgr, owner_dobj);
	                }
	            }

	        } catch (VahanException ve) {
	            throw ve;
	        } catch (SQLException sqle) {
	            LOGGER.error(sqle.toString() + " " + sqle.getStackTrace()[0]);
	            throw new VahanException(TableConstants.SomthingWentWrong);
	        } catch (Exception e) {
	            LOGGER.error(e.getMessage());
	        }
	    }//end of updateVaOwner

	  public  void insertVaOwner(TransactionManagerOne tmgr, Owner_dobj owner_dobj, int actionCd, String userStateCode,
	            int offCode) throws VahanException {

	        PreparedStatement ps = null;
	        String sql = null;
	        String chasiNoExistMessage = null;

	        try {
	            if (owner_dobj == null) {
	                return;
	            }
	            if (owner_dobj.getChasi_no() != null
	                    && owner_dobj.getRegn_type() != null
	                    && owner_dobj.getChasi_no().length() > 0
	                    && owner_dobj.getRegn_type().length() > 0
	                    && owner_dobj.getAuctionDobj() == null) {
	               // OwnerImpl ownerImpl = new OwnerImpl();
	                chasiNoExistMessage = ownerImpl.isChasiAlreadyExist(tmgr, owner_dobj.getChasi_no(), owner_dobj.getRegn_type());
	            }
	            if (chasiNoExistMessage != null) {
	                throw new VahanException(chasiNoExistMessage);
	            }

	            if (actionCd != TableConstants.TM_ROLE_DEALER_NEW_APPL
	                    && actionCd != TableConstants.TM_ROLE_NEW_APPL
	                    && actionCd != TableConstants.TM_ROLE_NEW_APPL_TEMP
	                    && actionCd != TableConstants.TM_ROLE_DEALER_TEMP_APPL
	                    && actionCd != TableConstants.TM_NEW_VEHICLE_FITNESS_INSPECTION
	                    && actionCd != TableConstants.TM_NEW_VEHICLE_FITNESS_INSPECTION_VERIFICATION
	                    && actionCd != TableConstants.TM_NEW_VEHICLE_FITNESS_INSPECTION_APPROVAL) {
	                serverUtil.validateOwnerDobj(owner_dobj);//Need to change this on the basis of action throug parameter
	            }

	            sql = "INSERT INTO " + TableList.VA_OWNER
	                    + " (  state_cd, off_cd, appl_no, regn_no, regn_dt, purchase_dt, owner_sr, "
	                    + "     owner_name, f_name, c_add1, c_add2, c_add3, c_district, c_pincode, "
	                    + "     c_state, p_add1, p_add2, p_add3, p_district, p_pincode, p_state, "
	                    + "     owner_cd, regn_type, vh_class, chasi_no, eng_no, maker, maker_model, "
	                    + "     body_type, no_cyl, hp, seat_cap, stand_cap, sleeper_cap, unld_wt, "
	                    + "     ld_wt, gcw, fuel, color, manu_mon, manu_yr, norms, wheelbase, "
	                    + "     cubic_cap, floor_area, ac_fitted, audio_fitted, video_fitted, "
	                    + "     vch_purchase_as, vch_catg, dealer_cd, sale_amt, laser_code, garage_add, "
	                    + "     length, width, height, regn_upto, fit_upto, annual_income, imported_vch, "
	                    + "     other_criteria,pmt_type,pmt_catg,rqrd_tax_modes,op_dt)"
	                    + "    VALUES (?, ?, ?, ?, ?, ?, ?, "
	                    + "            ?, ?, ?, ?, ?, ?, ?, "
	                    + "            ?, ?, ?, ?, ?, ?, ?, "
	                    + "            ?, ?, ?, ?, ?, ?, ?, "
	                    + "            ?, ?, ?, ?, ?, ?, ?, "
	                    + "            ?, ?, ?, ?, ?, ?, ?, ?, "
	                    + "            ?, ?, ?, ?, ?, "
	                    + "            ?, ?, ?, ?, ?, ?, "
	                    + "            ?, ?, ?, ?, ?, ?, ?, "
	                    + "            ?,?,?,?, current_timestamp)";

	            ps = tmgr.prepareStatement(sql);

	            int i = 1;

	            ps.setString(i++, owner_dobj.getState_cd());
	            ps.setInt(i++, owner_dobj.getOff_cd());
	            ps.setString(i++, owner_dobj.getAppl_no());
	            ps.setString(i++, owner_dobj.getRegn_no());
	            ps.setDate(i++, owner_dobj.getRegn_dt() == null ? null : new java.sql.Date(owner_dobj.getRegn_dt().getTime()));
	            ps.setDate(i++, owner_dobj.getPurchase_dt() == null ? null : new java.sql.Date(owner_dobj.getPurchase_dt().getTime()));
	            ps.setInt(i++, owner_dobj.getOwner_sr());
	            ps.setString(i++, owner_dobj.getOwner_name());
	            ps.setString(i++, owner_dobj.getF_name());
	            ps.setString(i++, owner_dobj.getC_add1());
	            ps.setString(i++, owner_dobj.getC_add2());
	            ps.setString(i++, owner_dobj.getC_add3());
	            ps.setInt(i++, owner_dobj.getC_district());
	            ps.setInt(i++, owner_dobj.getC_pincode());
	            ps.setString(i++, owner_dobj.getC_state());
	            ps.setString(i++, owner_dobj.getP_add1());
	            ps.setString(i++, owner_dobj.getP_add2());
	            ps.setString(i++, owner_dobj.getP_add3());
	            ps.setInt(i++, owner_dobj.getP_district());
	            ps.setInt(i++, owner_dobj.getP_pincode());
	            ps.setString(i++, owner_dobj.getP_state());
	            ps.setInt(i++, owner_dobj.getOwner_cd());
	            if ((CommonUtils.isNullOrBlank(owner_dobj.getRegn_type()) || "0".equals(owner_dobj.getRegn_type()))
	                    && actionCd != TableConstants.TM_NEW_VEHICLE_FITNESS_INSPECTION
	                    && actionCd != TableConstants.TM_NEW_VEHICLE_FITNESS_INSPECTION_VERIFICATION
	                    && actionCd != TableConstants.TM_NEW_VEHICLE_FITNESS_INSPECTION_APPROVAL) {
	                throw new VahanException("Registration Type can not be blank.");
	            }
	            ps.setString(i++, owner_dobj.getRegn_type());
	            ps.setInt(i++, owner_dobj.getVh_class());
	            ps.setString(i++, owner_dobj.getChasi_no() == null ? TableConstants.EMPTY_STRING : owner_dobj.getChasi_no());
	            ps.setString(i++, owner_dobj.getEng_no() == null ? TableConstants.EMPTY_STRING : owner_dobj.getEng_no());
	            ps.setInt(i++, owner_dobj.getMaker());
	            ps.setString(i++, owner_dobj.getMaker_model() == null ? TableConstants.EMPTY_STRING : owner_dobj.getMaker_model());
	            ps.setString(i++, owner_dobj.getBody_type() == null ? TableConstants.EMPTY_STRING : owner_dobj.getBody_type());
	            ps.setInt(i++, owner_dobj.getNo_cyl());

	            if (owner_dobj.getHp() != null) {
	                ps.setFloat(i++, owner_dobj.getHp());
	            } else {
	                ps.setNull(i++, java.sql.Types.FLOAT);
	            }

	            ps.setInt(i++, owner_dobj.getSeat_cap());
	            ps.setInt(i++, owner_dobj.getStand_cap());
	            ps.setInt(i++, owner_dobj.getSleeper_cap());
	            ps.setInt(i++, owner_dobj.getUnld_wt());
	            ps.setInt(i++, owner_dobj.getLd_wt());
	            ps.setInt(i++, owner_dobj.getGcw());
	            ps.setInt(i++, owner_dobj.getFuel());
	            ps.setString(i++, owner_dobj.getColor() == null ? TableConstants.EMPTY_STRING : owner_dobj.getColor());
	            ps.setInt(i++, owner_dobj.getManu_mon() == null ? 0 : owner_dobj.getManu_mon());
	            ps.setInt(i++, owner_dobj.getManu_yr() == null ? 0 : owner_dobj.getManu_yr());
	            ps.setInt(i++, owner_dobj.getNorms());
	            ps.setInt(i++, owner_dobj.getWheelbase());
	            ps.setFloat(i++, owner_dobj.getCubic_cap());
	            ps.setFloat(i++, owner_dobj.getFloor_area());
	            ps.setString(i++, owner_dobj.getAc_fitted());
	            ps.setString(i++, owner_dobj.getAudio_fitted());
	            ps.setString(i++, owner_dobj.getVideo_fitted());
	            ps.setString(i++, owner_dobj.getVch_purchase_as() == null ? TableConstants.EMPTY_STRING : owner_dobj.getVch_purchase_as());
	            ps.setString(i++, owner_dobj.getVch_catg());
	            ps.setString(i++, owner_dobj.getDealer_cd() == null ? TableConstants.EMPTY_STRING : owner_dobj.getDealer_cd());
	            ps.setInt(i++, owner_dobj.getSale_amt());
	            ps.setString(i++, owner_dobj.getLaser_code() == null ? TableConstants.EMPTY_STRING : owner_dobj.getLaser_code());
	            ps.setString(i++, owner_dobj.getGarage_add() == null ? TableConstants.EMPTY_STRING : owner_dobj.getGarage_add());
	            ps.setInt(i++, owner_dobj.getLength());
	            ps.setInt(i++, owner_dobj.getWidth());
	            ps.setInt(i++, owner_dobj.getHeight());

	            ps.setDate(i++, owner_dobj.getRegn_upto() == null ? null : new java.sql.Date(owner_dobj.getRegn_upto().getTime()));
	            ps.setDate(i++, owner_dobj.getFit_upto() == null ? null : new java.sql.Date(owner_dobj.getFit_upto().getTime()));
	            ps.setInt(i++, owner_dobj.getAnnual_income());
	            ps.setString(i++, owner_dobj.getImported_vch() == null ? TableConstants.EMPTY_STRING : owner_dobj.getImported_vch());
	            ps.setInt(i++, owner_dobj.getOther_criteria());
	            ps.setInt(i++, owner_dobj.getPmt_type());
	            ps.setInt(i++, owner_dobj.getPmt_catg());
	            ps.setString(i++, owner_dobj.getRqrd_tax_modes() == null ? TableConstants.EMPTY_STRING : owner_dobj.getRqrd_tax_modes());
	            ps.executeUpdate();

	            /*
	             * insertVaOwnerId() used here is just for reference, that we have made changes to the existing code
	             */
	            if (actionCd != TableConstants.TM_NEW_VEHICLE_FITNESS_INSPECTION
	                    && actionCd != TableConstants.TM_NEW_VEHICLE_FITNESS_INSPECTION_VERIFICATION
	                    && actionCd != TableConstants.TM_NEW_VEHICLE_FITNESS_INSPECTION_APPROVAL) {
	                insertVaOwnerId(tmgr, owner_dobj.getOwner_identity(), userStateCode, offCode);
	            }


	            if (owner_dobj.getDob_temp() != null) {
	                if (owner_dobj.getDob_temp().getState_cd_to() == null) {
	                    throw new VahanException("State to & Office to is not selected for Temporary Registration.");
	                }
	                insertVaOwnerTemp(tmgr, owner_dobj.getDob_temp(), userStateCode, offCode);
	            }

	            if (owner_dobj.getTempReg() != null) {
	                insertVaTempRegnDetails(tmgr, owner_dobj.getTempReg(), userStateCode, offCode);
	            }

	            if (!CommonUtils.isNullOrBlank(owner_dobj.getLinkedRegnNo())) {
	                insertVaSideTrailer(tmgr, owner_dobj);
	            }

	        } catch (SQLException sqle) {
	            LOGGER.error(sqle.toString() + " " + sqle.getStackTrace()[0]);
	            throw new VahanException("Something Went Wrong During Saving of Data into Database. Please Contact to the System Administrator");
	        }

	    }     
	  
	  public static void insertVaOwnerId(TransactionManagerOne tmgr, OwnerIdentificationDobj dobj, String userStateCode, int offCode) throws SQLException {

	        PreparedStatement ps = null;
	        String sql = "INSERT INTO " + TableList.VA_OWNER_IDENTIFICATION
	                + "(state_cd,off_cd,appl_no, regn_no, mobile_no, email_id, pan_no, aadhar_no, passport_no, "
	                + "            ration_card_no, voter_id, dl_no, verified_on,owner_ctg,op_dt,dept_cd)"
	                + "  VALUES (?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current_timestamp,?,current_timestamp,?)";

	        ps = tmgr.prepareStatement(sql);
	        int i = 1;
	        ps.setString(i++, userStateCode);
	        ps.setInt(i++, offCode);
	        ps.setString(i++, dobj.getAppl_no());
	        ps.setString(i++, dobj.getRegn_no());
	        ps.setLong(i++, dobj.getMobile_no());
	        ps.setString(i++, dobj.getEmail_id());
	        ps.setString(i++, dobj.getPan_no());
	        ps.setString(i++, dobj.getAadhar_no());
	        ps.setString(i++, dobj.getPassport_no());
	        ps.setString(i++, dobj.getRation_card_no());
	        ps.setString(i++, dobj.getVoter_id());
	        ps.setString(i++, dobj.getDl_no());
	        ps.setInt(i++, dobj.getOwnerCatg());
	        ps.setInt(i++, dobj.getOwnerCdDept());
	        //ps.setDate(i++, dobj.getVerified_on() == null ? null : new java.sql.Date(dobj.getVerified_on().getTime()));
	        ps.executeUpdate();

	    }//end of insertVaOwnerId
	  public static void insertVaOwnerTemp(TransactionManagerOne tmgr, Owner_temp_dobj dobj, String userStateCode, int offCode) throws SQLException {

	        PreparedStatement ps = null;
	        String sql = "INSERT INTO va_owner_temp(state_cd,off_cd,appl_no, temp_regn_no, state_cd_to, off_cd_to,purpose,body_building,op_dt,temp_regn_type)"
	                + "    VALUES (?,?,?, ?, ?, ?,?,?,current_timestamp,?)";

	        ps = tmgr.prepareStatement(sql);
	        int i = 1;
	        ps.setString(i++, userStateCode);
	        ps.setInt(i++, offCode);
	        ps.setString(i++, dobj.getAppl_no());
	        ps.setString(i++, dobj.getTemp_regn_no());
	        ps.setString(i++, dobj.getState_cd_to());
	        ps.setInt(i++, dobj.getOff_cd_to());
	        ps.setString(i++, dobj.getPurpose());
	        ps.setString(i++, dobj.getBodyBuilding());
	        ps.setString(i++, dobj.getTemp_regn_type());
	        ps.executeUpdate();

	    }
	  public static void insertVaTempRegnDetails(TransactionManagerOne tmgr, TempRegDobj dobj, String userStateCode,
	            int offCode) throws SQLException {

	        PreparedStatement ps = null;
	        String sql = "INSERT INTO " + TableList.VA_TMP_REGN_DTL
	                + "(state_cd,off_cd,appl_no, regn_no, tmp_off_cd, regn_auth, tmp_state_cd, tmp_regn_no,tmp_regn_dt, tmp_valid_upto, dealer_cd,op_dt)"
	                + "  VALUES (?,?,?, ?, ?, ?, ?, ?, ?, ?, ?,current_timestamp)";

	        ps = tmgr.prepareStatement(sql);
	        int i = 1;
	        ps.setString(i++, userStateCode);
	        ps.setInt(i++, offCode);
	        ps.setString(i++, dobj.getAppl_no());
	        ps.setString(i++, dobj.getRegn_no());
	        ps.setInt(i++, dobj.getTmp_off_cd());
	        ps.setString(i++, dobj.getRegn_auth() == null ? TableConstants.EMPTY_STRING : dobj.getRegn_auth());
	        ps.setString(i++, dobj.getTmp_state_cd());
	        ps.setString(i++, dobj.getTmp_regn_no() == null ? TableConstants.EMPTY_STRING : dobj.getTmp_regn_no());
	        if (dobj.getTmp_regn_dt() != null) {
	            ps.setDate(i++, new java.sql.Date(dobj.getTmp_regn_dt().getTime()));
	        } else {
	            ps.setNull(i++, java.sql.Types.DATE);
	        }
	        if (dobj.getTmp_valid_upto() != null) {
	            ps.setDate(i++, new java.sql.Date(dobj.getTmp_valid_upto().getTime()));
	        } else {
	            ps.setNull(i++, java.sql.Types.DATE);
	        }
	        ps.setString(i++, dobj.getDealer_cd());

	        ps.executeUpdate();
	    }
	  public static void insertVaSideTrailer(TransactionManagerOne tmgr, Owner_dobj owner_dobj) throws SQLException {
	        PreparedStatement ps = null;
	        String query = "INSERT INTO " + TableList.VA_SIDE_TRAILER + "(\n"
	                + "            state_cd, off_cd, appl_no, regn_no, link_regn_no,  op_dt)\n"
	                + "    VALUES (?, ?, ?, ?, ?, current_timestamp)";
	        ps = tmgr.prepareStatement(query);
	        ps.setString(1, owner_dobj.getState_cd());
	        ps.setInt(2, owner_dobj.getOff_cd());
	        ps.setString(3, owner_dobj.getAppl_no());
	        ps.setString(4, owner_dobj.getRegn_no());
	        ps.setString(5, owner_dobj.getLinkedRegnNo());

	        ps.executeUpdate();
	    }
	  public static void updateVaOwnerId(TransactionManagerOne tmgr, OwnerIdentificationDobj dobj) throws SQLException {
	        PreparedStatement ps = null;

	        String sql = "UPDATE " + TableList.VA_OWNER_IDENTIFICATION
	                + "   SET "
	                + " mobile_no=?, email_id=?, pan_no=?, aadhar_no=?, "
	                + "       passport_no=?, ration_card_no=?, voter_id=?, dl_no=?, verified_on=current_timestamp, owner_ctg = ? , op_dt = current_timestamp,dept_cd=?"
	                + " WHERE appl_no=?";

	        ps = tmgr.prepareStatement(sql);

	        int i = 1;

	        ps.setLong(i++, dobj.getMobile_no());
	        ps.setString(i++, dobj.getEmail_id());
	        ps.setString(i++, dobj.getPan_no());
	        ps.setString(i++, dobj.getAadhar_no());
	        ps.setString(i++, dobj.getPassport_no());
	        ps.setString(i++, dobj.getRation_card_no());
	        ps.setString(i++, dobj.getVoter_id());
	        ps.setString(i++, dobj.getDl_no());
	        ps.setInt(i++, dobj.getOwnerCatg());
	        ps.setInt(i++, dobj.getOwnerCdDept());
	        ps.setString(i++, dobj.getAppl_no());
	        ps.executeUpdate();
	    }//end of updateVaOwnerId
	  public static void updateVaOwnerTemp(TransactionManagerOne tmgr, Owner_temp_dobj dobj) throws SQLException {
	        PreparedStatement ps = null;

	        String sql = "UPDATE va_owner_temp"
	                + "   SET appl_no=?, temp_regn_no=?, state_cd_to=?, off_cd_to=?,purpose=?,body_building=?,op_dt = current_timestamp"
	                + " WHERE appl_no=?";

	        ps = tmgr.prepareStatement(sql);

	        int i = 1;

	        ps.setString(i++, dobj.getAppl_no());
	        ps.setString(i++, dobj.getTemp_regn_no());
	        ps.setString(i++, dobj.getState_cd_to());
	        ps.setInt(i++, dobj.getOff_cd_to());
	        ps.setString(i++, dobj.getPurpose());
	        ps.setString(i++, dobj.getBodyBuilding());
	        //ps.setDate(i++, dobj.getVerified_on() == null ? null : new java.sql.Date(dobj.getVerified_on().getTime()));
	        ps.setString(i++, dobj.getAppl_no());
	        ps.executeUpdate();

	    }//end of updateVaOwnerId
	  public static void insertOrupdateVaTempRegnDetails(TransactionManagerOne tmgr, TempRegDobj dobj, String userStateCode, int offCode) throws SQLException {
	        PreparedStatement ps = null;

	        String sql = "SELECT * FROM " + TableList.VA_TMP_REGN_DTL + " where appl_no=?";
	        ps = tmgr.prepareStatement(sql);
	        ps.setString(1, dobj.getAppl_no());
	        RowSet rs = tmgr.fetchDetachedRowSet_No_release();
	        if (rs.next()) {
	            updateVaTempRegnDetails(tmgr, dobj);
	        } else {
	            insertVaTempRegnDetails(tmgr, dobj, userStateCode, offCode);
	        }
	    }
	  public static void updateVaTempRegnDetails(TransactionManagerOne tmgr, TempRegDobj dobj) throws SQLException {
	        PreparedStatement ps = null;

	        String sql = "UPDATE " + TableList.VA_TMP_REGN_DTL
	                + "   SET  regn_no=?, tmp_off_cd=?, regn_auth=?,"
	                + " tmp_state_cd=?, tmp_regn_no=?, tmp_regn_dt=?, tmp_valid_upto=?, dealer_cd=?,op_dt = current_timestamp"
	                + " where appl_no=?";

	        ps = tmgr.prepareStatement(sql);

	        int i = 1;

	        ps.setString(i++, dobj.getRegn_no());
	        ps.setInt(i++, dobj.getTmp_off_cd());
	        ps.setString(i++, dobj.getRegn_auth());
	        ps.setString(i++, dobj.getTmp_state_cd());
	        ps.setString(i++, dobj.getTmp_regn_no());
	        ps.setDate(i++, new java.sql.Date(dobj.getTmp_regn_dt().getTime()));
	        ps.setDate(i++, new java.sql.Date(dobj.getTmp_valid_upto().getTime()));
	        ps.setString(i++, dobj.getDealer_cd());
	        ps.setString(i++, dobj.getAppl_no());
	        ps.executeUpdate();

	    }
	  public static void updateVaSideTrailer(TransactionManagerOne tmgr, Owner_dobj owner_dobj) throws SQLException {
	        PreparedStatement ps = null;
	        String query = "UPDATE " + TableList.VA_SIDE_TRAILER + "\n"
	                + "   SET link_regn_no=?, op_dt=current_timestamp\n"
	                + " WHERE appl_no = ?";
	        ps = tmgr.prepareStatement(query);
	        ps.setString(1, owner_dobj.getLinkedRegnNo());
	        ps.setString(2, owner_dobj.getAppl_no());
	        ps.executeUpdate();
	    }
	  public  void insertOrUpdateVaOwnerOther(TransactionManagerOne tmgr, Owner_dobj owner_dobj,
	            String empCode, String userStateCode, int offCode) throws VahanException, SQLException {
	        PreparedStatement ps = tmgr.prepareStatement("Select * from " + TableList.VA_OWNER_OTHER
	                + " where appl_no=?");
	        ps.setString(1, owner_dobj.getAppl_no());
	        RowSet rs = tmgr.fetchDetachedRowSet_No_release();
	        if (rs.next()) {
	            insertVhaOwnerOther(tmgr, owner_dobj.getAppl_no(), empCode);
	            updateVaOwnerOther(tmgr, owner_dobj);
	        } else {
	            insertVaOwnerOther(tmgr, owner_dobj, userStateCode, offCode);
	        }
	    }
	  public  void insertVhaOwnerOther(TransactionManagerOne tmgr, String appl_no, String empCode) throws SQLException {
	        PreparedStatement ps = null;
	        String sql = "INSERT INTO  " + TableList.VHA_OWNER_OTHER
	                + "  SELECT current_timestamp as moved_on, ? moved_by,state_cd,off_cd,appl_no, regn_no, "
	                + "  push_back_seat,ordinary_seat,op_dt "
	                + "  FROM  " + TableList.VA_OWNER_OTHER
	                + " where appl_no=?";
	        ps = tmgr.prepareStatement(sql);
	        ps.setString(1, empCode);
	        ps.setString(2, appl_no);
	        ps.executeUpdate();
	    }
	  
	  public  void updateVaOwnerOther(TransactionManagerOne tmgr, Owner_dobj dobj) throws SQLException {
	        PreparedStatement ps = null;
	        String sql = "UPDATE " + TableList.VA_OWNER_OTHER
	                + "   SET push_back_seat=?, ordinary_seat=?,op_dt = current_timestamp"
	                + " WHERE appl_no=?";
	        ps = tmgr.prepareStatement(sql);
	        int i = 1;
	        ps.setInt(i++, dobj.getPush_bk_seat());
	        ps.setInt(i++, dobj.getOrdinary_seat());
	        ps.setString(i++, dobj.getAppl_no());
	        ps.executeUpdate();
	    }
	  public static void insertVaOwnerOther(TransactionManagerOne tmgr, Owner_dobj dobj, String userStateCode,
	            int offCode) throws SQLException {
	        PreparedStatement ps = null;
	        String sql = "INSERT INTO " + TableList.VA_OWNER_OTHER
	                + "(state_cd,off_cd,appl_no,regn_no,push_back_seat, ordinary_seat,op_dt)"
	                + "  VALUES (?,?,?,?,?,?,current_timestamp)";
	        ps = tmgr.prepareStatement(sql);
	        int i = 1;
	        ps.setString(i++, userStateCode);
	        ps.setInt(i++, offCode);
	        ps.setString(i++, dobj.getAppl_no());
	        ps.setString(i++, dobj.getRegn_no());
	        ps.setInt(i++, dobj.getPush_bk_seat());
	        ps.setInt(i++, dobj.getOrdinary_seat());
	        ps.executeUpdate();
	    }


}

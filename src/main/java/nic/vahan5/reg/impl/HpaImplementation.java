package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.form.dobj.HpaDobj;
import nic.vahan5.reg.server.TableConstants;

@Service
public class HpaImplementation {
	
	@Autowired
	TransactionManagerOne tmgr;
	@Autowired
	TransactionManagerReadOnly tmg;
	
	private static final Logger LOGGER = Logger.getLogger(HpaImplementation.class);
	public HpaDobj set_HPA_appl_db_to_dobj(String appl_no, String regn_no, int pur_cd, String stateCd, int offCd) throws VahanException {

        PreparedStatement ps = null;
      //  TransactionManager tmgr = null;
        HpaDobj hpa_dobj = null;
        VahanException vahanexecption = null;
        String query;
        String parameterValue;
        boolean vtFlag = false;
        try {

            if (TableConstants.VM_TRANSACTION_MAST_NEW_VEHICLE == pur_cd || TableConstants.VM_TRANSACTION_MAST_DEALER_NEW_VEHICLE == pur_cd) {
                query = "Select appl_no, regn_no, sr_no, hp_type, fncr_name, fncr_add1, fncr_add2, "
                        + "       fncr_add3, fncr_district, fncr_pincode, fncr_state, from_dt, op_dt"
                        + "  FROM  " + TableList.VA_HPA
                        + " where appl_no=?";
                parameterValue = appl_no;
                vtFlag = false;
            } else if (TableConstants.VM_TRANSACTION_MAST_TEMP_REG == pur_cd || TableConstants.VM_TRANSACTION_MAST_DEALER_TEMP_VEHICLE == pur_cd) {
//                query = "SELECT   sr_no, hp_type, fncr_name, fncr_add1, "
//                        + "       fncr_add2, fncr_village, fncr_taluk, fncr_district, fncr_pincode, "
//                        + "       from_dt, op_dt, state_cd, off_cd "
//                        + "  FROM va_hypth_temp"
//                        + " where appl_no=?";
//                parameterValue = appl_no;

                query = "Select appl_no, regn_no, sr_no, hp_type, fncr_name, fncr_add1, fncr_add2, "
                        + "       fncr_add3, fncr_district, fncr_pincode, fncr_state, from_dt, op_dt"
                        + "  FROM  " + TableList.VA_HPA
                        + " where appl_no=?";
                parameterValue = appl_no;
                vtFlag = false;
            } else {
                query = "SELECT   regn_no, sr_no, hp_type, fncr_name, fncr_add1, fncr_add2, fncr_add3,"
                        + "  fncr_district, fncr_pincode, fncr_state, from_dt, op_dt "
                        + "  FROM  " + TableList.VT_HYPTH
                        + " where regn_no=? and state_cd= ? and off_cd= ? ";
                parameterValue = regn_no;
                vtFlag = true;
            }
       //     tmgr = new TransactionManager("HPA_Impl");
            ps = tmg.prepareStatement(query);
            if (vtFlag) {
                ps.setString(1, parameterValue);
                ps.setString(2, stateCd);
                ps.setInt(3, offCd);
            } else {
                ps.setString(1, parameterValue);
            }
            RowSet rs = tmg.fetchDetachedRowSet();

            if (rs.next()) // found
            {
                hpa_dobj = new HpaDobj();
                // hpa_dobj.setAppl_no(rs.getString("appl_no"));
                hpa_dobj.setRegn_no(rs.getString("regn_no"));
                hpa_dobj.setSr_no(rs.getInt("sr_no"));
                hpa_dobj.setHp_type(rs.getString("hp_type"));
                hpa_dobj.setFncr_name(rs.getString("fncr_name"));
                hpa_dobj.setFncr_add1(rs.getString("fncr_add1"));
                hpa_dobj.setFncr_add2(rs.getString("fncr_add2"));
                hpa_dobj.setFncr_add3(rs.getString("fncr_add3"));
                hpa_dobj.setFncr_district(rs.getInt("fncr_district"));
                hpa_dobj.setFncr_pincode(rs.getInt("fncr_pincode"));
                hpa_dobj.setFncr_state(rs.getString("fncr_state"));
                hpa_dobj.setFrom_dt(rs.getDate("from_dt"));

            }

        } catch (Exception ex) {
            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
            vahanexecption = new VahanException("Error in fetching details for [ " + appl_no + "]");
            throw vahanexecption;
        } finally {
            try {
                if (tmg != null) {
                    tmg.release();
                }
            } catch (Exception e) {
                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            }
        }

        return hpa_dobj;
    }
	public  void insertDeleteFromVaHpa(TransactionManagerOne tmgr, String applNo, String empCode) throws SQLException {
        PreparedStatement psVaHpa = null;
        PreparedStatement psVhaHpa = null;
        String vaHpasql = null;
        String vhaHpasql = null;


        vhaHpasql = "INSERT INTO " + TableList.VHA_HPA
                + " SELECT  current_timestamp as moved_on, ? as moved_by, state_cd , off_cd, appl_no, regn_no, sr_no, hp_type, fncr_name, fncr_add1, fncr_add2, "
                + " fncr_add3, fncr_district, fncr_pincode, fncr_state, from_dt, op_dt FROM " + TableList.VA_HPA + " WHERE appl_no = ? ";

        psVhaHpa = tmgr.prepareStatement(vhaHpasql);
        psVhaHpa.setString(1, empCode);
        psVhaHpa.setString(2, applNo);
        psVhaHpa.executeUpdate();

        vaHpasql = "DELETE FROM " + TableList.VA_HPA + " WHERE appl_no=?";
        psVaHpa = tmgr.prepareStatement(vaHpasql);
        psVaHpa.setString(1, applNo);
        psVaHpa.executeUpdate();
    }
	public  void insertUpdateHPA(TransactionManagerOne tmgr, List<HpaDobj> hpa_dobj_list, String stateCd, int offCd,
            String empCode) throws VahanException {
        PreparedStatement ps = null;
        String sql = null;
        try {
            if (hpa_dobj_list == null) {
                return;
            }
            sql = "SELECT regn_no FROM  " + TableList.VA_HPA + " where appl_no = ?";
            ps = tmgr.prepareStatement(sql);
            ps.setString(1, hpa_dobj_list.get(0).getAppl_no());

            RowSet rs = tmgr.fetchDetachedRowSet_No_release();

            if (rs.next()) { //if any record is exist then update otherwise insert it

                for (int i = 0; i < hpa_dobj_list.size(); i++) {
                    if (hpa_dobj_list.get(i).getSr_no() == 0) { // if new record added HPA_dobj_list.get(i).setSr_no(old+1);
                        insertIntoHPA(tmgr, hpa_dobj_list.get(i), stateCd, offCd);
                    } else {
                        insertIntoHPAHistory(tmgr, hpa_dobj_list.get(i).getAppl_no(), hpa_dobj_list.get(i).getSr_no(), empCode);
                        updateHPA(tmgr, hpa_dobj_list.get(i), hpa_dobj_list.get(i).getAppl_no());
                    }
                }
            } else {
                for (int i = 0; i < hpa_dobj_list.size(); i++) {
                    insertIntoHPA(tmgr, hpa_dobj_list.get(i), stateCd, offCd);
                }
            }
        } catch (VahanException vex) {
            throw vex;
        } catch (Exception ex) {
            LOGGER.error(ex.toString() + "-" + ex.getStackTrace()[0]);
            throw new VahanException(TableConstants.SomthingWentWrong);
        }
    } // end of insertUpdateHPA

	 public static void insertIntoHPA(TransactionManagerOne tmgr, HpaDobj dobj, String stateCd, int offCd) throws VahanException {
	        PreparedStatement ps = null;
	        String sql = null;
	        try {
	            HpaImplementation hpaImpl = new HpaImplementation();
	            int maxSrNo = hpaImpl.getHpaMaxSrNo(dobj.getAppl_no(), tmgr);

	            sql = "INSERT INTO " + TableList.VA_HPA + " ( state_cd, off_cd, appl_no, regn_no, sr_no, hp_type, fncr_name, fncr_add1, fncr_add2, "
	                    + "            fncr_add3, fncr_district, fncr_pincode, fncr_state, from_dt, op_dt) "
	                    + "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current_timestamp) ";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, stateCd);
	            ps.setInt(2, offCd);
	            ps.setString(3, dobj.getAppl_no());
	            ps.setString(4, dobj.getRegn_no());
	            ps.setInt(5, maxSrNo + 1);
	            ps.setString(6, dobj.getHp_type());
	            ps.setString(7, dobj.getFncr_name());
	            ps.setString(8, dobj.getFncr_add1());
	            ps.setString(9, dobj.getFncr_add2());
	            ps.setString(10, dobj.getFncr_add3());
	            ps.setInt(11, dobj.getFncr_district());
	            ps.setInt(12, dobj.getFncr_pincode() == null ? 0 : dobj.getFncr_pincode());
	            ps.setString(13, dobj.getFncr_state());
	            ps.setDate(14, new java.sql.Date(dobj.getFrom_dt().getTime()));
	            ps.executeUpdate();
	        } catch (VahanException vex) {
	            throw vex;
	        } catch (Exception ex) {
	            LOGGER.error(ex.toString() + "-" + ex.getStackTrace()[0]);
	            throw new VahanException(TableConstants.SomthingWentWrong);
	        }
	    } // end of insertIntoHPA
	 public int getHpaMaxSrNo(String applNo, TransactionManagerOne tmgr) throws VahanException {
	        PreparedStatement ps = null;
	        int maxSrNo = 0;
	        try {
	            String query = "SELECT max(sr_no) as max_sr_no FROM " + TableList.VA_HPA + " where appl_no=?";
	            ps = tmgr.prepareStatement(query);
	            ps.setString(1, applNo);
	            RowSet rs = tmgr.fetchDetachedRowSet_No_release();

	            if (rs.next()) // found
	            {
	                maxSrNo = rs.getInt("max_sr_no");
	            }

	        } catch (Exception ex) {
	            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	            throw new VahanException("Something went wrong during fetching Max Sr. No of Hypothecation Details of Appl. No-" + applNo);
	        }
	        return maxSrNo;
	    }
	 public static void insertIntoHPAHistory(TransactionManagerOne tmgr, String appl_no, int sr_no,
	            String empCode) throws SQLException, Exception {
	        PreparedStatement ps = null;
	        String sql = null;

	        sql = "INSERT INTO " + TableList.VHA_HPA
	                + " SELECT  current_timestamp as moved_on, ? as moved_by, state_cd, off_cd,  appl_no, regn_no, sr_no, hp_type, fncr_name, fncr_add1, fncr_add2, "
	                + "        fncr_add3, fncr_district, fncr_pincode, fncr_state, from_dt, "
	                + "        op_dt FROM " + TableList.VA_HPA
	                + "  WHERE appl_no = ? and sr_no=?";

	        ps = tmgr.prepareStatement(sql);
	        ps.setString(1, empCode);
	        ps.setString(2, appl_no);
	        ps.setInt(3, sr_no);
	        ps.executeUpdate();

	    } // end of insertIntoHPAHistory
    
	 /**
     *
     *
     * @param tmgr
     * @param dobj
     * @param appl_no
     *
     * Note : This method is work under the registration of new vwhicle so
     * commit / rollback is managed by the parent process i.e New_Impl.java who
     * is basically passing the transacation manager object to this function
     *
     */
    public static void updateHPA(TransactionManagerOne tmgr, HpaDobj dobj, String appl_no) throws SQLException, Exception {
        PreparedStatement ps = null;
        String sql = null;

        sql = "UPDATE " + TableList.VA_HPA
                + "   SET hp_type=?, fncr_name=?, fncr_add1=?, fncr_add2=?, fncr_add3=?, "
                + "       fncr_state=?, fncr_district=?, fncr_pincode=?,"
                + "       from_dt=?, op_dt=current_timestamp "
                + " WHERE appl_no=? and sr_no = ?";
        ps = tmgr.prepareStatement(sql);
        ps.setString(1, dobj.getHp_type());
        ps.setString(2, dobj.getFncr_name());
        ps.setString(3, dobj.getFncr_add1());
        ps.setString(4, dobj.getFncr_add2());
        ps.setString(5, dobj.getFncr_add3());
        ps.setString(6, dobj.getFncr_state());
        ps.setInt(7, dobj.getFncr_district());
        ps.setInt(8, dobj.getFncr_pincode());
        ps.setDate(9, new java.sql.Date(dobj.getFrom_dt().getTime()));
        ps.setString(10, appl_no);
        ps.setInt(11, dobj.getSr_no());
        ps.executeUpdate();
    } // end of updateHPA

    
}

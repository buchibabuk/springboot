package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import nic.java.util.CommonUtils;
import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.form.dobj.InsDobj;
import nic.vahan5.reg.server.TableConstants;
@Service
public class InsImplementation {
	 private static final Logger LOGGER = Logger.getLogger(InsImplementation.class);
	 
	 public static InsDobj set_ins_dtls_db_to_dobj(String regn_no, String appl_no, String state_cd, int off_cd) throws VahanException {
	        InsDobj ins_dobj = null;
	        TransactionManagerReadOnly tmgr = null;
	        PreparedStatement ps = null;
	        String pvalue = null;
	        String query = "";
	        try {
	            if ((regn_no != null && !regn_no.isEmpty() && !regn_no.equalsIgnoreCase("NEW")) || (appl_no != null && !appl_no.isEmpty())) {
	                tmgr = new TransactionManagerReadOnly("set_ins_dtls_db_to_dobj");
	                if (appl_no != null && !appl_no.isEmpty()) {
	                    pvalue = appl_no.toUpperCase();
	                    query = "select regn_no,comp_cd,descr, "
	                            + "ins_type, "
	                            + "ins_from, "
	                            + "ins_upto,policy_no,idv,op_dt "
	                            + " from " + TableList.VA_INSURANCE + " a left join  " + TableList.VM_ICCODE
	                            + " b on a.comp_cd=b.ic_code"
	                            + " where appl_no = ? ";
	                    ps = tmgr.prepareStatement(query);
	                    ps.setString(1, pvalue);
	                }

	                if (regn_no != null && !regn_no.isEmpty()) {
	                    pvalue = regn_no.toUpperCase();
	                    query = "select regn_no,comp_cd,descr, "
	                            + "ins_type, "
	                            + "ins_from, "
	                            + "ins_upto,policy_no,idv, case when op_dt is null then '1900-01-01'::timestamp else op_dt end as op_dt "
	                            + " from " + TableList.VT_INSURANCE + " a left join  " + TableList.VM_ICCODE
	                            + " b on a.comp_cd=b.ic_code";
	                    if (state_cd != null) {
	                        query = query + " where regn_no = ? and state_cd = ? order by op_dt desc";
	                    } else {
	                        query = query + " where regn_no = ? order by op_dt desc";
	                    }

	                    ps = tmgr.prepareStatement(query);
	                    ps.setString(1, pvalue);
	                    if (state_cd != null) {
	                        ps.setString(2, state_cd);
	                    }

	                }
	                RowSet rs = tmgr.fetchDetachedRowSet();

	                if (rs.next()) {
	                    ins_dobj = new InsDobj();
	                    ins_dobj.setComp_cd(rs.getInt("comp_cd"));
	                    ins_dobj.setIns_type(rs.getInt("ins_type"));
	                    ins_dobj.setIns_from(rs.getDate("ins_from"));
	                    ins_dobj.setIns_upto(rs.getDate("ins_upto"));
	                    ins_dobj.setPolicy_no(rs.getString("policy_no"));
	                    ins_dobj.setIdv(rs.getLong("idv"));
	                    ins_dobj.setInsCompName(rs.getString("descr"));
	                    ins_dobj.setRegn_no(rs.getString("regn_no"));
	                    ins_dobj.setOp_dt(rs.getDate("op_dt"));
	                }
	            }
	        } catch (SQLException sqle) {
	            LOGGER.error(sqle.toString() + "-" + sqle.getStackTrace()[0]);
	            throw new VahanException("Something went wrong during update of insurance details, Please contact to the System Administrator.");
	        } catch (Exception ex) {
	            LOGGER.error(ex.toString() + "-" + ex.getStackTrace()[0]);
	            throw new VahanException("Something went wrong during update of insurance details, Please contact to the System Administrator.");
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + "-" + e.getStackTrace()[0]);
	            }
	        }

	        return ins_dobj;
	    }
	 public  boolean validateOwnerCodeWithInsType(int ownerCode, int insType) {
	        if (ownerCode == TableConstants.VEH_TYPE_GOVT || ownerCode == TableConstants.VEH_TYPE_STATE_GOVT
	                || ownerCode == TableConstants.VEH_TYPE_GOVT_UNDERTAKING
	                || ownerCode == TableConstants.VEH_TYPE_POLICE_DEPT
	                || ownerCode == TableConstants.VEH_TYPE_STATE_TRANS_DEPT
	                || ownerCode == TableConstants.VEH_TYPE_LOCAL_AUTHORITY) {
	            return true;
	        } else if (insType == Integer.parseInt(TableConstants.INS_TYPE_NA)) {
	            return false;
	        } else {
	            return true;
	        }
	    }
	 public  void insertUpdateInsurance(TransactionManagerOne tmgr, String appl_no, String regn_no, InsDobj ins_dobj,
	            String stateCd, int offCd, String empCode) throws VahanException {
	        PreparedStatement ps = null;
	        String sql = null;
	        try {
	            sql = "SELECT regn_no FROM " + TableList.VA_INSURANCE + " where appl_no = ?";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, appl_no);
	            RowSet rs = tmgr.fetchDetachedRowSet_No_release();
	            if (rs.next()) { //if any record is exist then update otherwise insert it
	                insertIntoInsuranceHistory(tmgr, appl_no, regn_no, empCode);
	                if (ins_dobj != null && !CommonUtils.isNullOrBlank(ins_dobj.getPolicy_no())) {
	                    updateInsurance(tmgr, ins_dobj, appl_no, regn_no);
	                } else {
	                    throw new VahanException("Policy No Can't be Blank");
	                }
	            } else {
	                insertIntoInsurance(tmgr, ins_dobj, appl_no, regn_no, stateCd, offCd);
	            }
	        } catch (VahanException ve) {
	            throw ve;
	        } catch (SQLException sqle) {
	            LOGGER.error(sqle.toString() + "-" + sqle.getStackTrace()[0]);
	            throw new VahanException("Something went wrong during update of insurance details, Please contact to the System Administrator.");
	        } catch (Exception ex) {
	            LOGGER.error(ex.toString() + "-" + ex.getStackTrace()[0]);
	            throw new VahanException("Something went wrong during update of insurance details, Please contact to the System Administrator.");
	        }
	    }
	 public static void insertIntoInsuranceHistory(TransactionManagerOne tmgr, String appl_no, String regn_no,
	            String empCode) throws VahanException {
	        PreparedStatement ps = null;
	        String sql = null;
	        try {
	            sql = "INSERT INTO " + TableList.VHA_INSURANCE + ""
	                    + "     SELECT current_timestamp as moved_on,? as moved_by, state_cd, off_cd, appl_no, regn_no, comp_cd, ins_type, ins_from, "
	                    + " ins_upto, policy_no, idv, op_dt"
	                    + " FROM " + TableList.VA_INSURANCE + " where appl_no = ?";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, empCode);
	            ps.setString(2, appl_no);
	            ps.executeUpdate();
	        } catch (SQLException sqle) {
	            LOGGER.error(sqle.toString() + "-" + sqle.getStackTrace()[0]);
	            throw new VahanException("Something went wrong during update of insurance details, Please contact to the System Administrator.");
	        } catch (Exception ex) {
	            LOGGER.error(ex.toString() + "-" + ex.getStackTrace()[0]);
	            throw new VahanException("Something went wrong during update of insurance details, Please contact to the System Administrator.");
	        }
	    }

	 public static void updateInsurance(TransactionManagerOne tmgr, InsDobj dobj, String appl_no, String regn_no) throws VahanException {
	        PreparedStatement ps = null;
	        String sql = null;
	        try {
	            sql = "UPDATE " + TableList.VA_INSURANCE + " "
	                    + "   SET comp_cd=?,"
	                    + "       ins_type=?,"
	                    + "       ins_from=?,"
	                    + "       ins_upto=?,"
	                    + "       policy_no=?,"
	                    + "       idv=?,"
	                    + "       op_dt=current_timestamp"
	                    + " WHERE appl_no=? ";
	            ps = tmgr.prepareStatement(sql);
	            ps.setInt(1, dobj.getComp_cd());
	            ps.setInt(2, dobj.getIns_type());
	            if (dobj.getIns_from() != null) {
	                ps.setDate(3, new java.sql.Date(dobj.getIns_from().getTime()));
	            } else {
	                ps.setNull(3, java.sql.Types.DATE);
	            }
	            if (dobj.getIns_upto() != null) {
	                ps.setDate(4, new java.sql.Date(dobj.getIns_upto().getTime()));
	            } else {
	                ps.setNull(4, java.sql.Types.DATE);
	            }
	            ps.setString(5, dobj.getPolicy_no());
	            ps.setLong(6, dobj.getIdv());
	            ps.setString(7, appl_no);
	            ps.executeUpdate();
	        } catch (SQLException sqle) {
	            LOGGER.error(sqle.toString() + "-" + sqle.getStackTrace()[0]);
	            throw new VahanException("Something went wrong during update of insurance details, Please contact to the System Administrator.");
	        } catch (Exception ex) {
	            LOGGER.error(ex.toString() + "-" + ex.getStackTrace()[0]);
	            throw new VahanException("Something went wrong during update of insurance details, Please contact to the System Administrator.");
	        }
	    } // end of updateInsurance
	 public static void insertIntoInsurance(TransactionManagerOne tmgr, InsDobj dobj, String appl_no, String regn_no,
	            String stateCd, int offCd) throws VahanException {
	        PreparedStatement ps = null;
	        String sql = null;
	        try {
	            sql = "INSERT INTO " + TableList.VA_INSURANCE + " (state_cd, off_cd, appl_no, regn_no, comp_cd, ins_type, ins_from, ins_upto, policy_no, idv, op_dt)"
	                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current_timestamp)";
	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, stateCd);
	            ps.setInt(2, offCd);
	            ps.setString(3, appl_no);
	            ps.setString(4, regn_no);
	            ps.setInt(5, dobj.getComp_cd());
	            ps.setInt(6, dobj.getIns_type());
	            if (dobj.getIns_from() != null) {
	                ps.setDate(7, new java.sql.Date(dobj.getIns_from().getTime()));
	            } else {
	                ps.setNull(7, java.sql.Types.DATE);
	            }
	            if (dobj.getIns_upto() != null) {
	                ps.setDate(8, new java.sql.Date(dobj.getIns_upto().getTime()));
	            } else {
	                ps.setNull(8, java.sql.Types.DATE);
	            }
	            ps.setString(9, dobj.getPolicy_no() == null ? "" : dobj.getPolicy_no());
	            ps.setLong(10, dobj.getIdv());
	            ps.executeUpdate();
	        } catch (SQLException sqle) {
	            LOGGER.error(sqle.toString() + "-" + sqle.getStackTrace()[0]);
	            throw new VahanException("Something went wrong during update of insurance details, Please contact to the System Administrator.");
	        } catch (Exception ex) {
	            LOGGER.error(ex.toString() + "-" + ex.getStackTrace()[0]);
	            throw new VahanException("Something went wrong during update of insurance details, Please contact to the System Administrator.");
	        }
	    } // end of insertIntoInsurance

}

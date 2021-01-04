package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.java.util.CommonUtils;
import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.form.dobj.PendencyBankDobj;
import nic.vahan5.reg.server.ServerUtil;
@Service
public class PendencyBankDetailImpl {
	
	@Autowired
	ServerUtil serverUtil;
	@Autowired
	TransactionManagerReadOnly tmgr;
	private static final Logger LOGGER = Logger.getLogger(PendencyBankDetailImpl.class);

	public boolean saveOrUpdatePendencyBankDtls(TransactionManagerOne tmgr, String stateCd, int offCd, PendencyBankDobj dobj, String empCd) throws VahanException {
        PreparedStatement psSelectUpdate = null;
        String sqlQuery = "";
        boolean flag = false;
        String whereCond = "";
        String searchType = " appl_no";
        int i = 1;
        try {
            if (dobj != null && !dobj.equals("")) {
                if (dobj.getRegnNo() != null && !dobj.getRegnNo().equals("")) {
                    searchType = " regn_no ";
                }
                i = 1;
                if (dobj.getAccountNo() != null && (dobj.getAadharNo() != null && !dobj.getAadharNo().equals(""))) {
                    whereCond = " (bank_ac_no = ? or aadhar_no= ?) ";
                } else if (dobj.getAccountNo() != null && (dobj.getAadharNo() == null || dobj.getAadharNo().equals(""))) {
                    whereCond = " bank_ac_no = ? ";
                }
                sqlQuery = "select * from " + TableList.VP_BANK_SUBSIDY + " where " + whereCond + " and " + searchType + " != ? ";
                PreparedStatement psExistAgainstBankOrAddhar = tmgr.prepareStatement(sqlQuery);
                if (dobj.getAccountNo() != null && (dobj.getAadharNo() != null && !dobj.getAadharNo().equals(""))) {
                    psExistAgainstBankOrAddhar.setString(i++, dobj.getAccountNo());
                    psExistAgainstBankOrAddhar.setString(i++, dobj.getAadharNo());
                } else if (dobj.getAccountNo() != null && (dobj.getAadharNo() == null || dobj.getAadharNo().equals(""))) {
                    psExistAgainstBankOrAddhar.setString(i++, dobj.getAccountNo());
                }
                if (dobj.getRegnNo() != null && !dobj.getRegnNo().equals("")) {
                    psExistAgainstBankOrAddhar.setString(i++, dobj.getRegnNo());
                } else if (dobj.getApplNo() != null && !dobj.getApplNo().equals("")) {
                    psExistAgainstBankOrAddhar.setString(i++, dobj.getApplNo());
                }

                RowSet rs2 = tmgr.fetchDetachedRowSet_No_release();
                if (!rs2.next()) {
                    i = 1;
                    sqlQuery = "select * from " + TableList.VP_BANK_SUBSIDY + " where state_cd = ? and off_cd = ? and " + searchType + " = ? ";
                    psSelectUpdate = tmgr.prepareStatement(sqlQuery);
                    psSelectUpdate.setString(i++, stateCd);
                    psSelectUpdate.setInt(i++, offCd);
                    if (dobj.getRegnNo() != null && !dobj.getRegnNo().equals("")) {
                        psSelectUpdate.setString(i++, dobj.getRegnNo());
                    } else if (dobj.getApplNo() != null && !dobj.getApplNo().equals("")) {
                        psSelectUpdate.setString(i++, dobj.getApplNo());
                    }
                    RowSet rs = tmgr.fetchDetachedRowSet_No_release();
                    if (rs.next()) {
                        sqlQuery = "INSERT INTO " + TableList.VPH_BANK_SUBSIDY + "(moved_on, moved_by, state_cd, off_cd, appl_no, regn_no, ll_dl_no, bank_code, "
                                + " bank_ifsc_cd, bank_ac_no, aadhar_no, subsidy_amount, status, op_dt) SELECT CURRENT_TIMESTAMP AS moved_on, ? AS moved_by, state_cd, off_cd, appl_no, regn_no, ll_dl_no, "
                                + " bank_code, bank_ifsc_cd, bank_ac_no, aadhar_no, subsidy_amount, status, op_dt "
                                + " FROM " + TableList.VP_BANK_SUBSIDY + " where state_cd = ? and off_cd = ? and " + searchType + " = ? ";
                        i = 1;
                        psSelectUpdate = tmgr.prepareStatement(sqlQuery);
                        psSelectUpdate.setString(i++, empCd);
                        psSelectUpdate.setString(i++, stateCd);
                        psSelectUpdate.setInt(i++, offCd);
                        if (dobj.getRegnNo() != null && !dobj.getRegnNo().equals("")) {
                            psSelectUpdate.setString(i++, dobj.getRegnNo());
                        } else if (dobj.getApplNo() != null && !dobj.getApplNo().equals("")) {
                            psSelectUpdate.setString(i++, dobj.getApplNo());
                        }
                        ServerUtil.validateQueryResult(tmgr, psSelectUpdate.executeUpdate(), psSelectUpdate);
                        flag = true;
                        i = 1;
                        sqlQuery = " UPDATE " + TableList.VP_BANK_SUBSIDY + " SET bank_code = ?, bank_ifsc_cd = ?,"
                                + " bank_ac_no = ?, aadhar_no = ?, subsidy_amount = ?, status = ? WHERE state_cd = ? and off_cd = ? and " + searchType + " = ? ";
                        psSelectUpdate = tmgr.prepareStatement(sqlQuery);
                        psSelectUpdate.setString(i++, dobj.getBankCd());
                        psSelectUpdate.setString(i++, dobj.getIfscCode().toUpperCase());
                        psSelectUpdate.setString(i++, dobj.getAccountNo());
                        if (CommonUtils.isNullOrBlank(dobj.getAadharNo())) {
                            psSelectUpdate.setString(i++, "");
                        } else {
                            psSelectUpdate.setString(i++, dobj.getAadharNo());
                        }
                        psSelectUpdate.setInt(i++, dobj.getSubsidyAmount());
                        psSelectUpdate.setString(i++, dobj.getStatusCode());
                        psSelectUpdate.setString(i++, stateCd);
                        psSelectUpdate.setInt(i++, offCd);
                        if (dobj.getRegnNo() != null && !dobj.getRegnNo().equals("")) {
                            psSelectUpdate.setString(i++, dobj.getRegnNo());
                        } else if (dobj.getApplNo() != null && !dobj.getApplNo().equals("")) {
                            psSelectUpdate.setString(i++, dobj.getApplNo());
                        }
                        psSelectUpdate.executeUpdate();
                    } else {
                        i = 1;
                        sqlQuery = " INSERT INTO " + TableList.VP_BANK_SUBSIDY + "(state_cd, off_cd, appl_no, regn_no, ll_dl_no, bank_code, bank_ifsc_cd, "
                                + "bank_ac_no, aadhar_no, status, op_dt) "
                                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
                        psSelectUpdate = tmgr.prepareStatement(sqlQuery);
                        psSelectUpdate.setString(i++, stateCd);
                        psSelectUpdate.setInt(i++, offCd);
                        psSelectUpdate.setString(i++, dobj.getApplNo());
                        if (dobj.getRegnNo() != null && !dobj.getRegnNo().equals("")) {
                            psSelectUpdate.setString(i++, dobj.getRegnNo());
                        } else {
                            psSelectUpdate.setString(i++, "NEW");
                        }
                        if (!CommonUtils.isNullOrBlank(dobj.getDlLlNo())) {
                            psSelectUpdate.setString(i++, dobj.getDlLlNo());
                        } else {
                            throw new VahanException("Blank DL/LL no.");
                        }
                        psSelectUpdate.setString(i++, dobj.getBankCd());
                        psSelectUpdate.setString(i++, dobj.getIfscCode().toUpperCase());
                        psSelectUpdate.setString(i++, dobj.getAccountNo());
                        if (!CommonUtils.isNullOrBlank(dobj.getAadharNo())) {
                            psSelectUpdate.setString(i++, dobj.getAadharNo());
                        } else {
                            psSelectUpdate.setString(i++, "");
                        }
                        psSelectUpdate.setString(i++, dobj.getStatusCode());
                        ServerUtil.validateQueryResult(tmgr, psSelectUpdate.executeUpdate(), psSelectUpdate);
                        flag = true;
                    }
                } else {
                    throw new VahanException("Duplicate aadhar/account number.");
                }
            }
        } catch (VahanException ve) {
            throw ve;
        } catch (Exception e) {
            LOGGER.error(e.toString() + " saveOrUpdatePendencyBankDtls " + e.getStackTrace()[0]);
            throw new VahanException("problem in updating bank subsidy details.");
        }
        return flag;
    }
	 public PendencyBankDobj getBankSubsidyData(String applNo, String stateCd, int offCd) throws VahanException {
	       // TransactionManager tmgr = null;
	        PreparedStatement ps = null;
	        PendencyBankDobj subsidyDobj = new PendencyBankDobj();;
	        try {
	        //    tmgr = new TransactionManager("getBankDetailsData");
	            String saveSQL = " SELECT * FROM " + TableList.VP_BANK_SUBSIDY + " where state_cd = ? and off_cd = ? and appl_no = ? ";
	            ps = tmgr.prepareStatement(saveSQL);
	            int i = 1;
	            ps.setString(i++, stateCd);
	            ps.setInt(i++, offCd);
	            ps.setString(i++, applNo);
	            RowSet rs = tmgr.fetchDetachedRowSet();
	            if (rs.next()) {
	                subsidyDobj.setBankCd(rs.getString("bank_code"));
	                subsidyDobj.setIfscCode(rs.getString("bank_ifsc_cd"));
	                subsidyDobj.setAccountNo(rs.getString("bank_ac_no"));
	                subsidyDobj.setAadharNo(rs.getString("aadhar_no"));
	            }
	        } catch (Exception e) {
	            LOGGER.error(e.getMessage() + "  " + e.getStackTrace()[0]);
	            throw new VahanException("problem in getting bank subsidy details.");
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	        return subsidyDobj;
	    }
}

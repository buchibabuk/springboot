/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.vahan5.reg.impl;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.sql.RowSet;
import nic.java.util.DateUtils;
import nic.vahan5.reg.form.dobj.OwnerDetailsDobj;
import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.form.dobj.InsDobj;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsImpl implements Serializable {
	
	@Autowired
	TransactionManagerReadOnly tmgr;

    private static final Logger LOGGER = Logger.getLogger(InsImpl.class);

    //Insurance details from vt_insurance if regn_no available or from va_insurance if appl_no available
    public  InsDobj set_ins_dtls_db_to_dobj(String regn_no, String appl_no, String state_cd, int off_cd) throws VahanException {
        InsDobj ins_dobj = null;
       // TransactionManagerReadOnly tmgr = null;
        PreparedStatement ps = null;
        String pvalue = null;
        String query = "";
        try {
            if ((regn_no != null && !regn_no.isEmpty() && !regn_no.equalsIgnoreCase("NEW")) || (appl_no != null && !appl_no.isEmpty())) {
              //  tmgr = new TransactionManagerReadOnly("set_ins_dtls_db_to_dobj");
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

    //Insurance details from va_insurance when regn_no available
 
}//end of Ins_Impl

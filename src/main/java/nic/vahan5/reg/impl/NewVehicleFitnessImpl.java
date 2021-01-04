/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.sql.RowSet;
import nic.java.util.DateUtils;

import nic.vahan.common.jsf.utils.JSFUtils;

//import nic.vahan.db.connection.TransactionManagerReadOnly;
//import nic.vahan.form.bean.ComparisonBean;
//import nic.vahan.form.dobj.AxleDetailsDobj;
//import nic.vahan.form.dobj.FitnessDobj;

//import nic.vahan.form.dobj.RetroFittingDetailsDobj;
import nic.vahan5.reg.form.dobj.Status_dobj;
import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.server.ServerUtil;
//import static nic.vahan5.reg.server.ServerUtil.insertIntoVhaChangedData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class NewVehicleFitnessImpl {
	@Autowired
	TransactionManagerReadOnly tmgr;

    private static final Logger LOGGER = Logger.getLogger(NewVehicleFitnessImpl.class);

    public boolean isFailedFitness(String appl_no, int purCd, int offCd, String stateCd) throws VahanException {
        boolean isFitnessFailed = false;
       // nic.vahan5.reg.db.connection.TransactionManagerReadOnly tmgr = null;

        try {
           // tmgr = new nic.vahan5.reg.db.connection.TransactionManagerReadOnly("isFailedFitness");

            String sql = " SELECT regn_no, op_dt FROM " + TableList.VA_FITNESS_CHASSIS
                    + " WHERE appl_no = ? and fit_result=? order by op_dt desc limit 1";
            PreparedStatement ps = tmgr.prepareStatement(sql);

            ps.setString(1, appl_no);
            ps.setString(2, nic.vahan5.reg.server.TableConstants.FitnessResultFail);

            RowSet rs = tmgr.fetchDetachedRowSet_No_release();
            if (rs.next()) {
                isFitnessFailed = true;
            }
        } catch (SQLException e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            throw new VahanException(nic.vahan5.reg.server.TableConstants.SomthingWentWrong);
        } finally {
            if (tmgr != null) {
                try {
                    tmgr.release();
                } catch (Exception ex) {
                    LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
                }
            }
        }

        return isFitnessFailed;
    }

}

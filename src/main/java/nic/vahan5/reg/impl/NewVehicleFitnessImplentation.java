package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
@Service
public class NewVehicleFitnessImplentation {
	
	@Autowired
	TransactionManagerOne tmgr;
	@Autowired
	TransactionManagerReadOnly tmg;
	 private static final Logger LOGGER = Logger.getLogger(NewVehicleFitnessImplentation.class);
	 
	 
	 public boolean checkForPreRegFitness(String chasiNo, String engineNo, String userStateCode) throws VahanException {
	        boolean isPreRegFitness = false;
	        VahanException vahanexecption = null;
	     //   TransactionManager tmgr = null;
	        try {
	        //    tmgr = new TransactionManager("checkForPreRegFitness");
	            PreparedStatement ps = tmg.prepareStatement("Select * from " + TableList.VT_FITNESS_CHASSIS + " where chasi_no=? and state_cd=?");
	            ps.setString(1, chasiNo);
	            ps.setString(2, userStateCode);
	            RowSet rs = tmg.fetchDetachedRowSet_No_release();
	            if (rs.next()) {
	                isPreRegFitness = true;
	            }
	        } catch (Exception ex) {
	            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	            vahanexecption = new VahanException("Error in fetching details for Fitness");
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
	        return isPreRegFitness;
	    }
	 
	 public void saveInVtFitnessFromVtFitnessChassis(TransactionManagerOne tmgr, String chasi_no, String eng_no, String appl_no,String empcode) throws VahanException {
	        PreparedStatement ps = null;

	        try {
	            ps = tmgr.prepareStatement("Select * from " + TableList.VA_FITNESS + " where appl_no=?");
	            ps.setString(1, appl_no);
	            RowSet rs = tmgr.fetchDetachedRowSet_No_release();
	            if (!rs.next()) {
	                String sql = "INSERT INTO vahan4.va_fitness(\n"
	                        + "            state_cd, off_cd, appl_no, regn_no, chasi_no, fit_chk_dt, fit_chk_tm, \n"
	                        + "            fit_result, fit_valid_to, fit_nid, fit_off_cd1, fit_off_cd2, \n"
	                        + "            remark, pucc_no, pucc_val, fare_mtr_no, brake, steer, susp, engin, \n"
	                        + "            tyre, horn, lamp, embo, speed, paint, wiper, dimen, body, fare, \n"
	                        + "            elec, finis, road, poll, transm, glas, emis, rear, others, op_dt)\n"
	                        + "    SELECT state_cd, off_cd,?, regn_no, chasi_no,  fit_chk_dt, fit_chk_tm, \n"
	                        + "       fit_result, fit_valid_to, fit_nid, fit_off_cd1, fit_off_cd2, \n"
	                        + "       remark, pucc_no, pucc_val, fare_mtr_no, brake, steer, susp, engin, \n"
	                        + "       tyre, horn, lamp, embo, speed, paint, wiper, dimen, body, fare, \n"
	                        + "       elec, finis, road, poll, transm, glas, emis, rear, others, op_dt\n"
	                        + "  FROM vahan4.vt_fitness_chassis where chasi_no=?";

	                ps = tmgr.prepareStatement(sql);
	                ps.setString(1, appl_no);
	                ps.setString(2, chasi_no);
	                ps.executeUpdate();
	                saveInVhFitnessChassisFromVtFitnessChassis(tmgr, chasi_no, eng_no,empcode);
	            }

	        } catch (Exception ex) {
	            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	            throw new VahanException("Error: Unable to  Insert Record of New Vehicle  Fitness");

	        }
	    }
	 public void saveInVhFitnessChassisFromVtFitnessChassis(TransactionManagerOne tmgr, String chasi_no, String eng_no,String empCode) throws VahanException {
	        PreparedStatement ps = null;
	        String sql = null;

	        try {
	            sql = "INSERT INTO vh_fitness_chassis(\n"
	                    + "            moved_on, moved_by, state_cd, off_cd, regn_no, chasi_no, eng_no, \n"
	                    + "            fit_chk_dt, fit_chk_tm, fit_result, fit_valid_to, fit_nid, fit_off_cd1, \n"
	                    + "            fit_off_cd2, remark, pucc_no, pucc_val, fare_mtr_no, brake, steer, \n"
	                    + "            susp, engin, tyre, horn, lamp, embo, speed, paint, wiper, dimen, \n"
	                    + "            body, fare, elec, finis, road, poll, transm, glas, emis, rear, \n"
	                    + "            others, op_dt)\n"
	                    + "    SELECT current_timestamp as moved_on ,? as moved_by , state_cd, off_cd, regn_no, chasi_no, eng_no, fit_chk_dt, fit_chk_tm, \n"
	                    + "       fit_result, fit_valid_to, fit_nid, fit_off_cd1, fit_off_cd2, \n"
	                    + "       remark, pucc_no, pucc_val, fare_mtr_no, brake, steer, susp, engin, \n"
	                    + "       tyre, horn, lamp, embo, speed, paint, wiper, dimen, body, fare, \n"
	                    + "       elec, finis, road, poll, transm, glas, emis, rear, others, op_dt\n"
	                    + "  FROM vt_fitness_chassis where chasi_no=?";

	            ps = tmgr.prepareStatement(sql);
	            ps.setString(1, empCode);
	            ps.setString(2, chasi_no);
	            ps.executeUpdate();


	        } catch (Exception ex) {
	            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	            throw new VahanException("Error: Unable to  Insert Record of New Vehicle  Fitness in History");

	        }
	    }
}

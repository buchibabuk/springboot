package nic.vahan5.reg.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.common.ApplicationConfiguration;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
@Service
public class UserDAO {
	
	@Autowired
	TransactionManagerReadOnly tmgr;
	  private static final Logger LOGGER = Logger.getLogger(UserDAO.class);
	  
	/*  public  Map<String, String> getNonRtoRecords(String sqlQuery, boolean isMultipleRowData, boolean stateWise) {
	      //  TransactionManagerReadOnly tmgr = null;
	        String whereiam = "DashboardImpl.getNonRtoRecords";
	        Map hm = new LinkedHashMap();
	        PreparedStatement psmt = null;
	        RowSet rs = null;
	        String sql = null;
	        int totalNonVahanStates = 0;
	        int totalnonVahanOffices = 0;
	        try {
	         //   tmgr = new TransactionManagerReadOnly(whereiam);
	            sql = "select o.state_cd as x_axis,case when state_cd in ('MP','TS','AP') then count(*) else 0 end as y_axis from tm_office o inner join tm_state s on s.state_code=o.state_cd  group by o.state_cd order by o.state_cd asc";
	            psmt = tmgr.prepareStatement(sql);
	            rs = tmgr.fetchDetachedRowSet();
	            while (rs.next()) {
	                hm.put(rs.getString("x_axis"), rs.getString("y_axis"));
	                totalnonVahanOffices = totalnonVahanOffices + rs.getInt("y_axis");
	                if (rs.getInt("y_axis") > 0) {
	                    totalNonVahanStates = totalNonVahanStates + 1;
	                }
	            }
	            ApplicationConfiguration.setTotalnonVahan_states(totalNonVahanStates);
	            ApplicationConfiguration.setTotalnonVahan_offices(totalnonVahanOffices);
	        } catch (SQLException ex) {
	            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception ex) {
	                LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	            }
	        }
	        return hm;
	    }*/
	  
	/*  public  Map<String, String> getRunningRtoRecords(String sqlQuery, boolean isMultipleRowData, boolean stateWise) {
	       // TransactionManagerReadOnly tmgr = null;
	        String whereiam = "DashboardImpl.getRunningRtoRecords";
	        Map hm = new LinkedHashMap();
	        PreparedStatement psmt = null;
	        RowSet rs = null;
	        String sql = null;
	        int totalV4States = 0;
	        int totalV4 = 0;
	        try {
	          //  tmgr = new TransactionManagerReadOnly(whereiam);
	            sql = "select state_cd as x_axis,case when state_cd in ('MP','TS','AP') then 0 else sum(started)  end as y_axis from (select o.state_cd, count(*) as started,0 as notstarted from tm_office o inner join tm_state s on s.state_code=o.state_cd where o.vow4 is not null group by o.state_cd union select o.state_cd, 0 as started,count(*) as notstarted from tm_office o inner join tm_state s on s.state_code=o.state_cd where o.vow4 is null group by o.state_cd) as a group by state_cd order by state_cd";
	            psmt = tmgr.prepareStatement(sql);
	            rs = tmgr.fetchDetachedRowSet();
	            while (rs.next()) {
	                hm.put(rs.getString("x_axis"), rs.getString("y_axis"));
	                totalV4 = totalV4 + rs.getInt("y_axis");
	                if (rs.getInt("y_axis") > 0) {
	                    totalV4States = totalV4States + 1;
	                }
	            }
	            ApplicationConfiguration.totalRunningV4_offices = "(" + totalV4States + " states & " + totalV4 + " offices)";
	            ApplicationConfiguration.totalVahan4_offices = totalV4;
	        } catch (SQLException ex) {
	            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception ex) {
	                LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	            }
	        }
	        return hm;
	    }*/
	  
	/*  public  Map<String, String> getTotalRtoRecords(String sqlQuery, boolean isMultipleRowData, boolean stateWise) {
	       // TransactionManagerReadOnly tmgr = null;
	        String whereiam = "DashboardImpl.getTotalRtoRecords";
	        Map hm = new LinkedHashMap();
	        PreparedStatement psmt = null;
	        RowSet rs = null;
	        String sql = null;
	        int totalVahan_offices = 0;
	        try {
	          //  tmgr = new TransactionManagerReadOnly(whereiam);
	            sql = "select o.state_cd as x_axis,count(*) as y_axis from tm_office o inner join tm_state s on s.state_code=o.state_cd  group by o.state_cd order by o.state_cd asc";
	            psmt = tmgr.prepareStatement(sql);
	            rs = tmgr.fetchDetachedRowSet();
	            while (rs.next()) {
	                hm.put(rs.getString("x_axis"), rs.getString("y_axis"));
	                totalVahan_offices = totalVahan_offices + rs.getInt("y_axis");
	            }
	            ApplicationConfiguration.totalVahan_offices = totalVahan_offices;
	            ApplicationConfiguration.totalOlderVahan_offices = ApplicationConfiguration.totalVahan_offices - (ApplicationConfiguration.totalVahan4_offices + ApplicationConfiguration.getTotalnonVahan_offices());
	        } catch (SQLException ex) {
	            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	        } finally {
	            try {
	                if (tmgr != null) {
	                    tmgr.release();
	                }
	            } catch (Exception ex) {
	                LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
	            }
	        }
	        return hm;
	    }*/
}

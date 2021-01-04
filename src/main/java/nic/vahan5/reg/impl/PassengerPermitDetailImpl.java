package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.form.dobj.PassengerPermitDetailDobj;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.server.CommonUtils;
import nic.vahan5.reg.server.TableConstants;
@Service
public class PassengerPermitDetailImpl {
	
	@Autowired
	TransactionManagerReadOnly tmgr;
	//CommonPermitPrintImpl CommonPermitPrintImpl;
	private static final Logger LOGGER = Logger.getLogger(PassengerPermitDetailImpl.class);
	public Map<String, String> confige;
	public String statecode;
	
	public String getStatecode() {
		return statecode;
	}
	public void setStatecode(String statecode) {
		this.statecode = statecode;
	}
		/*
	 * //bhuvan 
	 * public PassengerPermitDetailImpl() {
	 * 
	 * }
	 */
	 //bhuvan
	    public PassengerPermitDetailImpl() {
	    	System.out.println("sc"+statecode);
	    	
	    	//System.out.println("sc"+CommonPermitPrintImpl.hashCode());
	    	
	     //   confige = getVmPermitStateConfiguration();
	    }
	    public  Map<String,String> getVmPermitStateConfiguration() {
			 
	    	String stateCd=getStatecode();
			 System.out.println("CPPI"+stateCd);
		        Map<String, String> stateConfMap = new LinkedHashMap<String, String>();
		      //  TransactionManagerReadOnly tmgrs = null;
		        RowSet rs = null;
		        PreparedStatement ps = null;
		        try {
		          //  tmgrs = new TransactionManagerReadOnly("Private Service Permit Print");
		            String query = "SELECT * FROM " + TableList.VM_PERMIT_STATE_CONFIGURATION + " where state_cd = ?";
		            ps = tmgr.prepareStatement(query);
		            ps.setString(1, stateCd);
		            rs = tmgr.fetchDetachedRowSet();
		            ResultSetMetaData rsmd = rs.getMetaData();
		            if (rs.next()) {
		                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
		                    stateConfMap.put(rsmd.getColumnName(i), rs.getString(i));
		                }
		            }
		        } catch (SQLException e) {
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
		        return stateConfMap;
		    }

	
	    public PassengerPermitDetailDobj set_vt_permit_regnNo_to_dobj(String regn_no, String pmt_no) {
	       // TransactionManagerReadOnly tmgr = null;
	        PassengerPermitDetailDobj permit_dobj = null;
	        PreparedStatement ps;
	        try {
	          //  tmgr = new TransactionManagerReadOnly("set_vt_permit_regnNo_to_dobj");
	            String query;
	            query = "select *,to_char(issue_dt,'DD-Mon-YYYY') as issue_dt_descr from " + TableList.VT_PERMIT + " where regn_no=?";
	            if (!CommonUtils.isNullOrBlank(pmt_no)) {
	                query = query + " and pmt_no =? ";
	            }
	            ps = tmgr.prepareStatement(query);
	            ps.setString(1, regn_no);
	            if (!CommonUtils.isNullOrBlank(pmt_no)) {
	                ps.setString(2, pmt_no);
	            }
	            RowSet rs = tmgr.fetchDetachedRowSet();
	            if (rs.next()) {
	                permit_dobj = new PassengerPermitDetailDobj();
	                permit_dobj.setState_cd(rs.getString("state_cd"));
	                permit_dobj.setOff_cd(String.valueOf(rs.getInt("off_cd")));
	                permit_dobj.setApplNo(rs.getString("appl_no"));
	                permit_dobj.setRegnNo(rs.getString("regn_no"));
	                permit_dobj.setPmt_type(String.valueOf(rs.getInt("pmt_type")));
	                permit_dobj.setPmt_type_code(String.valueOf(rs.getInt("pmt_type")));
	                permit_dobj.setPmtCatg(String.valueOf(rs.getInt("pmt_catg")));
	                permit_dobj.setDomain_CODE(String.valueOf(rs.getInt("domain_cd")));
	                permit_dobj.setServices_TYPE(String.valueOf(rs.getInt("service_type")));
	                permit_dobj.setGoods_TO_CARRY(rs.getString("goods_to_carry"));
	                permit_dobj.setJoreny_PURPOSE(rs.getString("jorney_purpose"));
	                permit_dobj.setRegion_covered(rs.getString("region_covered"));
	                permit_dobj.setParking(rs.getString("parking"));
	                permit_dobj.setPaction(String.valueOf(rs.getInt("pur_cd")));
	                permit_dobj.setStart_POINT(rs.getString("remarks"));
	                permit_dobj.setPmt_no(rs.getString("pmt_no"));
	                permit_dobj.setValid_from(rs.getDate("valid_from"));
	                permit_dobj.setValid_upto(rs.getDate("valid_upto"));
	                permit_dobj.setReplaceDate(rs.getDate("replace_date"));
	                permit_dobj.setIssuePmtDateDescr(rs.getString("issue_dt_descr"));
	                permit_dobj.setIssuePmtDate(rs.getDate("issue_dt"));

	                if (!(permit_dobj.getPmt_type().equalsIgnoreCase(TableConstants.NATIONAL_PERMIT)
	                        || permit_dobj.getPmt_type().equalsIgnoreCase(TableConstants.AITP)
	                        || permit_dobj.getPmt_type().equalsIgnoreCase(TableConstants.GOODS_PERMIT))) {
	                  //  query = "select vt_permit_route.no_of_trips from " + TableList.vt_permit_route + "\n"
	                   //         + " where vt_permit_route.appl_no=? limit 1";
	                    query = "select a.no_of_trips,b.rlength from " + TableList.vt_permit_route + " a "
	                            + "left outer join permit.vm_route_master b "
	                            + " on  a.state_cd=b.state_cd and a.off_cd=b.off_cd and a.route_cd=b.code   "
	                            + " where a.appl_no=? limit 1";
	                   // tmgr = new TransactionManagerReadOnly("set_vt_permit_regnNo_to_dobj1");
	                    ps = tmgr.prepareStatement(query);
	                    ps.setString(1, rs.getString("appl_no"));
	                    rs = tmgr.fetchDetachedRowSet();
	                    if (rs.next()) {
	                        permit_dobj.setNumberOfTrips(String.valueOf(rs.getInt("no_of_trips")));
	                        permit_dobj.setRout_length(String.valueOf(rs.getInt("rlength")));
	                    } else {
	                        permit_dobj.setNumberOfTrips("0");
	                    }
	                }
	            }
	        } catch (SQLException e) {
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
	        return permit_dobj;
	    }
	
	    public PassengerPermitDetailDobj getPermitHistory(String regnNo, String stateCd) {
	       // TransactionManagerReadOnly tmgr = null;
	        PassengerPermitDetailDobj permit_dobj = null;
	        RowSet rs = null;
	        PreparedStatement ps = null;
	        try {
	        //    tmgr = new TransactionManagerReadOnly("getPermitHistory");
	            String query = "SELECT * FROM  " + TableList.VH_PERMIT
	                    + " WHERE regn_no=? and state_cd=? order by moved_on desc limit 1";
	            ps = tmgr.prepareStatement(query);
	            ps.setString(1, regnNo);
	            ps.setString(2, stateCd);
	            rs = tmgr.fetchDetachedRowSet();
	            if (rs.next()) {
	                if (!"HP".contains(stateCd)) {
	                    query = "SELECT * FROM  permit.vha_permit_transaction  WHERE regn_no=? and state_cd=? order by moved_on desc limit 1";
	                    ps = tmgr.prepareStatement(query);
	                    ps.setString(1, regnNo);
	                    ps.setString(2, stateCd);
	                    RowSet rs1 = tmgr.fetchDetachedRowSet();
	                    if (rs1.next()) {
	                        if ((rs1.getInt("pur_cd") == TableConstants.VM_PMT_SURRENDER_PUR_CD && rs1.getInt("trans_pur_cd") == TableConstants.VM_PMT_CANCELATION_PUR_CD)) {
	                            return null;
	                        }
	                    }
	                }
	                permit_dobj = new PassengerPermitDetailDobj();
	                permit_dobj.setApplNo(rs.getString("appl_no"));
	                permit_dobj.setRegnNo(rs.getString("regn_no"));
	                permit_dobj.setPmt_type(String.valueOf(rs.getInt("pmt_type")));
	                permit_dobj.setPmt_type_code(String.valueOf(rs.getInt("pmt_type")));
	                permit_dobj.setPmtCatg(String.valueOf(rs.getInt("pmt_catg")));
	                permit_dobj.setDomain_CODE(String.valueOf(rs.getInt("domain_cd")));
	                permit_dobj.setServices_TYPE(String.valueOf(rs.getInt("service_type")));
	                permit_dobj.setGoods_TO_CARRY(rs.getString("goods_to_carry"));
	                permit_dobj.setJoreny_PURPOSE(rs.getString("jorney_purpose"));
	                permit_dobj.setRegion_covered(rs.getString("region_covered"));
	                permit_dobj.setParking(rs.getString("parking"));
	                permit_dobj.setPaction(String.valueOf(rs.getInt("pur_cd")));
	                permit_dobj.setStart_POINT(rs.getString("remarks"));
	                permit_dobj.setPmt_no(rs.getString("pmt_no"));
	                permit_dobj.setValid_from(rs.getDate("valid_from"));
	                permit_dobj.setValid_upto(rs.getDate("valid_upto"));
	                permit_dobj.setReplaceDate(rs.getDate("replace_date"));
	                if (!(permit_dobj.getPmt_type().equalsIgnoreCase(TableConstants.NATIONAL_PERMIT)
	                        || permit_dobj.getPmt_type().equalsIgnoreCase(TableConstants.AITP)
	                        || permit_dobj.getPmt_type().equalsIgnoreCase(TableConstants.GOODS_PERMIT))) {
	                    query = "select vt_permit_route.no_of_trips from " + TableList.vt_permit_route + "\n"
	                            + " where vt_permit_route.appl_no=? limit 1";
	                    tmgr = new TransactionManagerReadOnly("getPermitHistory1");
	                    ps = tmgr.prepareStatement(query);
	                    ps.setString(1, rs.getString("appl_no"));
	                    rs = tmgr.fetchDetachedRowSet();
	                    if (rs.next()) {
	                        permit_dobj.setNumberOfTrips(String.valueOf(rs.getInt("no_of_trips")));
	                    } else {
	                        permit_dobj.setNumberOfTrips("0");
	                    }
	                }
	            }
	        } catch (SQLException e) {
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
	        return permit_dobj;
	    }
}

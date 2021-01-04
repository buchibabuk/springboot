package nic.vahan5.reg.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.RowSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.codehaus.jackson.map.ObjectMapper;

import nic.vahan5.reg.common.VahanException.VahanException;

import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.form.dobj.FasTagDetailsDobj;
import nic.vahan5.reg.form.dobj.FasTagRequestDobj;
import nic.vahan5.reg.form.dobj.FasTagResponseDobj;
import nic.vahan5.reg.form.dobj.Owner_dobj;
import nic.vahan5.reg.server.CommonUtils;
import nic.vahan5.reg.server.ServerUtil;
import nic.vahan5.reg.server.TableConstants;
@Service
public class FasTagImpl {
	 private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(FasTagImpl.class);
	 
	 @Autowired
	 TransactionManagerReadOnly tmgr;
	 @Autowired
	 ServerUtil serverUtil;
	 public FasTagDetailsDobj getFasTagDetails(String chasiNo, String regn_no, String regnType) {
	        PreparedStatement ps = null;
	        String sql = null;
	        FasTagDetailsDobj dobj = null;
	      //  TransactionManagerReadOnly tmgr = null;
	        try {
	         //   tmgr = new TransactionManagerReadOnly("getFasTagDetails");
	            if (!TableConstants.VM_REGN_TYPE_NEW.equals(regnType) && !TableConstants.VM_REGN_TYPE_TEMPORARY.equals(regnType)
	                    && !TableConstants.VM_REGN_TYPE_EXARMY.equals(regnType) && !CommonUtils.isNullOrBlank(regn_no) && !"NEW".equals(regn_no)) {
	                sql = "SELECT tagid, chasi_no, eng_no, regn_no  FROM " + TableList.VT_FASTAG + " where chasi_no = ? and regn_no = ?";
	                ps = tmgr.prepareStatement(sql);
	                ps.setString(1, chasiNo);
	                ps.setString(2, regn_no);
	            } else {
	                sql = "SELECT tagid, chasi_no, eng_no, regn_no  FROM " + TableList.VT_FASTAG + " where chasi_no = ?";
	                ps = tmgr.prepareStatement(sql);
	                ps.setString(1, chasiNo);
	            }
	            RowSet rs = tmgr.fetchDetachedRowSet();
	            if (rs.next()) {
	                dobj = new FasTagDetailsDobj();
	                dobj.setTagid(rs.getString("tagid"));
	                dobj.setVin(rs.getString("chasi_no"));
	                dobj.setEngineno(rs.getString("eng_no"));
	                dobj.setRegnumber(rs.getString("regn_no"));
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
	        return dobj;
	    }
	 public  FasTagDetailsDobj getFasTagDetailsByService(String chasiNo, String engineNo) throws VahanException {
	        HttpURLConnection connection = null;
	        OutputStream outputStream = null;
	        BufferedReader bufferReader = null;
	        try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            FasTagRequestDobj requestDobj = new FasTagRequestDobj();
	            requestDobj.setVin(chasiNo);
	            requestDobj.setEngineno(engineNo);
	            String requestData = objectMapper.writeValueAsString(requestDobj);
	            final String fasTagURL = serverUtil.getVahanPgiUrl(TableConstants.FASTAG_URL);
	            if (!CommonUtils.isNullOrBlank(fasTagURL)) {
	                URL url = new URL(fasTagURL);
	                connection = (HttpURLConnection) url.openConnection();
	                connection.setConnectTimeout(3000);
	                connection.setReadTimeout(5000);
	                connection.setRequestMethod("POST");
	                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	                connection.setRequestProperty("Accept", "application/json");
	                connection.setDoOutput(true);
	                outputStream = connection.getOutputStream();
	                outputStream.write(requestData.getBytes("UTF-8"));
	                outputStream.flush();
	                outputStream.close();
	                int responseCode = connection.getResponseCode();
	                if (responseCode == 200) {
	                    bufferReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	                    String inputLine;
	                    StringBuilder responseData = new StringBuilder();
	                    while ((inputLine = bufferReader.readLine()) != null) {
	                        responseData.append(inputLine);
	                    }
	                    //Gson gson = new Gson();
	                    Gson gson=new GsonBuilder().create();
	                    FasTagResponseDobj respose = gson.fromJson(responseData.toString(), FasTagResponseDobj.class);
	                    if (respose != null) {
	                        if (Integer.parseInt(respose.getStatus()) == 0) {
	                            return objectMapper.readValue(new StringReader(respose.getData()), FasTagDetailsDobj.class);
	                        } else {
	                            throw new VahanException("No records found for provided Chassis (" + chasiNo + ") and Engine (" + engineNo + ") Number combination at NETC service.");
	                        }
	                    } else {
	                        throw new VahanException("No records found for provided Chassis (" + chasiNo + ") and Engine (" + engineNo + ") Number combination at NETC service.");
	                    }
	                } else {
	                    throw new VahanException("Unable to get response from service !!!");
	                }
	            } else {
	                throw new VahanException("FASTag service URL not found.");
	            }
	        } catch (VahanException ve) {
	            throw ve;
	        } catch (SocketTimeoutException se) {
	            LOGGER.error(se.toString() + " " + se.getStackTrace()[0]);
	            throw new VahanException("Unable to get response from the FASTag service. Please try again.");
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Problem in getting FASTag details through service.");
	        } finally {
	            try {
	                if (connection != null) {
	                    connection.disconnect();
	                }
	                if (bufferReader != null) {
	                    bufferReader.close();
	                }
	            } catch (Exception e) {
	                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            }
	        }
	    }
	 
	 public static void insertFasTagDetails(TransactionManagerOne tmgr, Owner_dobj ownerDobj, String empCD, FasTagDetailsDobj fasTagDobj) throws VahanException {
	        try {
	            String sql = "INSERT INTO " + TableList.VT_FASTAG + "(state_cd, off_cd, regn_no, chasi_no, eng_no, tagid, tid, op_dt, inserted_by) "
	                    + " VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?);";
	            PreparedStatement ps = tmgr.prepareStatement(sql);
	            ps.setString(1, ownerDobj.getState_cd());
	            ps.setInt(2, ownerDobj.getOff_cd());
	            ps.setString(3, ownerDobj.getRegn_no());
	            ps.setString(4, ownerDobj.getChasi_no());
	            ps.setString(5, ownerDobj.getEng_no());
	            ps.setString(6, fasTagDobj.getTagid());
	            ps.setString(7, fasTagDobj.getTid());
	            ps.setString(8, empCD);
	            ServerUtil.validateQueryResult(tmgr, ps.executeUpdate(), ps);
	        } catch (VahanException ve) {
	            throw ve;
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Problem in saving FASTag details.");
	        }
	    }
	 public void moveToHistory(TransactionManagerOne tmgr, Owner_dobj dobj, String empCd) throws VahanException {
	        PreparedStatement psInsert = null;
	        String conditionVar = "";
	        PreparedStatement psUpdate = null;
	        try {
	            if (!"NEW".equals(dobj.getRegn_no()) && !"TEMPREG".equals(dobj.getRegn_no())) {
	                conditionVar = " and regn_no = ? ";
	            }
	            String sql = "INSERT INTO " + TableList.VH_FASTAG + "(moved_on, moved_by, state_cd, off_cd, regn_no, chasi_no, eng_no, "
	                    + " tagid, tid, op_dt, inserted_by)"
	                    + " SELECT current_timestamp, ?, state_cd, off_cd, regn_no, chasi_no, eng_no, tagid, tid, op_dt, inserted_by"
	                    + " FROM " + TableList.VT_FASTAG + " where chasi_no= ? " + conditionVar;
	            psInsert = tmgr.prepareStatement(sql);
	            psInsert.setString(1, empCd);
	            psInsert.setString(2, dobj.getChasi_no());
	            if (!CommonUtils.isNullOrBlank(conditionVar)) {
	                psInsert.setString(3, dobj.getRegn_no());
	            }
	            psInsert.executeUpdate();

	            if (!CommonUtils.isNullOrBlank(conditionVar)) {
	                sql = "update " + TableList.VT_FASTAG + " set state_cd = ? , off_cd = ? where chasi_no = ? " + conditionVar;
	                psUpdate = tmgr.prepareStatement(sql);
	                psUpdate.setString(1, dobj.getState_cd());
	                psUpdate.setInt(2, dobj.getOff_cd());
	                psUpdate.setString(3, dobj.getChasi_no());
	                psUpdate.setString(4, dobj.getRegn_no());
	            } else {
	                sql = "update " + TableList.VT_FASTAG + " set regn_no = ?, state_cd = ? , off_cd = ? where chasi_no = ? ";
	                psUpdate = tmgr.prepareStatement(sql);
	                psUpdate = tmgr.prepareStatement(sql);
	                psUpdate.setString(1, dobj.getRegn_no());
	                psUpdate.setString(2, dobj.getState_cd());
	                psUpdate.setInt(3, dobj.getOff_cd());
	                psUpdate.setString(4, dobj.getChasi_no());
	            }
	            psUpdate.executeUpdate();
	        } catch (Exception e) {
	            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
	            throw new VahanException("Exception occured during maintaining history of FASTag details.");
	        } finally {
	            if (psUpdate != null) {
	                psUpdate = null;
	            }
	            if (psInsert != null) {
	                psInsert = null;
	            }
	        }
	    }
}

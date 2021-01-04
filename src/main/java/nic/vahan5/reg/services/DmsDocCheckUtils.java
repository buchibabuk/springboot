/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.vahan5.reg.services;

import nic.java.util.CommonUtils;

/**
 *
 * @author nicsi
 */


import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.form.model.VTDocumentModel;
import nic.vahan5.reg.server.ServerUtil;
import nic.vahan5.reg.server.ServerUtility;
import nic.vahan5.reg.server.TableConstants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
@Service
public class DmsDocCheckUtils extends Throwable {

    private static final Logger LOGGER = Logger.getLogger(DmsDocCheckUtils.class);
@Autowired
TransactionManagerReadOnly tmgr;
@Autowired
ServerUtility serverUtil;
   

    public  List<VTDocumentModel> getUploadedDocumentList(String applNos) throws VahanException {
       // TransactionManagerReadOnly tmgr = null;
        PreparedStatement ps = null;
        List<VTDocumentModel> documentList = new ArrayList<>();
        VTDocumentModel vtDocumentDobj = null;
        try {
            String sql = " select appl_no, regn_no, doc_url, doc_verified, doc_approved, temp_doc_approved, doc_desc from get_dms_doc_details(?)";
        //    tmgr = new TransactionManagerReadOnly("getUploadedDocumentList");
            ps = tmgr.prepareStatement(sql);
            ps.setString(1, applNos);
            RowSet rs = tmgr.fetchDetachedRowSet();
            while (rs.next()) {
                vtDocumentDobj = new VTDocumentModel();
                vtDocumentDobj.setAppl_no(rs.getString("appl_no"));
                vtDocumentDobj.setRegn_no(rs.getString("regn_no"));
                vtDocumentDobj.setDoc_url(rs.getString("doc_url"));
                vtDocumentDobj.setDoc_verified(rs.getBoolean("doc_verified"));
                vtDocumentDobj.setDoc_approved(rs.getBoolean("doc_approved"));
                vtDocumentDobj.setTemp_doc_approved(rs.getBoolean("temp_doc_approved"));
                vtDocumentDobj.setDoc_desc(rs.getString("doc_desc"));
                documentList.add(vtDocumentDobj);
            }
        } catch (Exception ex) {
            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
            throw new VahanException("Problem in getting Uploaded Documents details.");
        } finally {
            try {
                if (tmgr != null) {
                    tmgr.release();
                }
            } catch (Exception e) {
                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            }
        }
        return documentList;
    }/*
    public ResponseDocument callDocumentApiToGetDocListToBeUpload(String applNo, String stateCd, String regnNo, String offName, int purCd, boolean docUploadAtRTO) throws VahanException {
       // Gson gson = new Gson();
    	Gson gson=new GsonBuilder().create();
        ResponseDocument docListResponse = null;
        try {
            String dmsGetDocListURL = serverUtil.getVahanPgiUrl(TableConstants.DMS_GET_DOCLIST);

            if (!CommonUtils.isNullOrBlank(dmsGetDocListURL)) {
                dmsGetDocListURL = dmsGetDocListURL.replace("ApplNo", applNo).replace("state_cd", stateCd).replace("regn_no", regnNo)
                        .replace("off_name", offName).replace("pur_cd", purCd + "");

                if (docUploadAtRTO) {
                    dmsGetDocListURL = dmsGetDocListURL + "&docFrom=R" + TableConstants.SECURITY_KEY;
                } else {
                    dmsGetDocListURL = dmsGetDocListURL + TableConstants.SECURITY_KEY;
                }
            } else {
                throw new VahanException("DMS Get Doc List Service URL not found.");
            }
            if (dmsGetDocListURL != null) {
                DocumentUploadClient docClient = new DocumentUploadClient();
                String docList = docClient.getDocumentListToBeUpload(dmsGetDocListURL);
                if (docList != null && !docList.isEmpty()) {
                    docListResponse = gson.fromJson(docList, ResponseDocument.class);
                    if (docListResponse == null) {
                        throw new VahanException("Unable to get response from DMS.");
                    }
                } else {
                    throw new VahanException("Unable to get response from DMS.");
                }
            }
        } catch (VahanException vex) {
            throw vex;
        } catch (Exception e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            throw new VahanException(TableConstants.SomthingWentWrong);
        }
        return docListResponse;
    }*/
    public String callDocumentApiForViewDocument(String objectId) throws VahanException {
        String dmsViewURL = null;
        try {
            dmsViewURL = serverUtil.getVahanPgiUrl(TableConstants.DMS_VIEW_URL);

            if (!CommonUtils.isNullOrBlank(dmsViewURL)) {
                dmsViewURL = dmsViewURL + objectId;
            } else {
                throw new VahanException("DMS View Service URL not found.");
            }
        } catch (VahanException vex) {
            throw vex;
        } catch (Exception e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            throw new VahanException(TableConstants.SomthingWentWrong);
        }
        return dmsViewURL;
    }
    public int getMandatoryDocumentCount(String stateCd, int pur_cd) throws VahanException {
        //TransactionManagerReadOnly tmgr = null;
        PreparedStatement ps = null;
        int mandatoryCount = 0;
        try {
            String sql = "select count(distinct doc_catg) AS totalMandatory from get_dms_required_documents(?, ?) where mandatory_doc='Y';";

          //  tmgr = new TransactionManagerReadOnly("getMandatoryDocumentCount");
            ps = tmgr.prepareStatement(sql);
            ps.setString(1, stateCd);
            ps.setInt(2, pur_cd);
            RowSet rs = tmgr.fetchDetachedRowSet();
            while (rs.next()) {
                mandatoryCount = rs.getInt("totalMandatory");
            }
        } catch (Exception ex) {
            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
            throw new VahanException("Problem in getting Mandatory documents details.");
        } finally {
            try {
                if (tmgr != null) {
                    tmgr.release();
                }
            } catch (Exception e) {
                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            }
        }
        return mandatoryCount;
    }
}

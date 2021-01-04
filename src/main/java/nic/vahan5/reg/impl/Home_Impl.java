/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.sql.RowSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.db.TableList;

import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;


/**
 *
 * @author nic5912
 */
@Service
public class Home_Impl {
	
	@Autowired
	TransactionManagerReadOnly tmgr;

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(Home_Impl.class);

   
    public boolean getDealerAuthority(Long empCode) {
       // TransactionManagerReadOnly tmgr = null;
        PreparedStatement ps = null;
        String sql = null;
        boolean dealerAuth = false;
        try {
        //    tmgr = new TransactionManagerReadOnly("getDealerAuthority()");
            sql = "Select all_office_auth from " + TableList.TM_USER_PERMISSIONS + " Where user_cd = ?";
            ps = tmgr.prepareStatement(sql);
            ps.setLong(1, empCode);
            RowSet rs = tmgr.fetchDetachedRowSet();
            {
                if (rs.next()) {
                    dealerAuth = rs.getBoolean("all_office_auth");
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
        return dealerAuth;
    }

   
}
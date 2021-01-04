/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.vahan5.reg.impl;

import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import javax.sql.RowSet;



//import static nic.vahan.server.ServerUtil.insertIntoVhaChangedData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.form.dobj.AuctionDobj;
@Service
public class AuctionImpl {

    private static final Logger LOGGER = Logger.getLogger(AuctionImpl.class);

   @Autowired
   TransactionManagerReadOnly tmgr;

    public AuctionDobj getDetailsFromVtAuction(String chasiNo, String regnNo) throws VahanException {
      //  TransactionManagerReadOnly tmgr = null;
        PreparedStatement ps = null;
        AuctionDobj auctionDobj = null;
        String sql = "select *  from " + TableList.VT_AUCTION + " ";
        try {
        //    tmgr = new TransactionManagerReadOnly("getDetailsFromVtAuction");
            if (chasiNo != null) {
                sql = sql + "where chasi_no = ?";
            } else if (regnNo != null) {
                sql = sql + "where regn_no = ?";
            }
            ps = tmgr.prepareStatement(sql);
            if (chasiNo != null) {
                ps.setString(1, chasiNo);
            } else if (regnNo != null) {
                ps.setString(1, regnNo);
            }


            RowSet rs = tmgr.fetchDetachedRowSet();
            if (rs.next()) {
                auctionDobj = new AuctionDobj();
                auctionDobj.setAuctionDate(rs.getDate("auction_dt"));
                auctionDobj.setRegnNo(rs.getString("regn_no"));
                auctionDobj.setFirDate(rs.getDate("fir_dt"));
                auctionDobj.setFirNo(rs.getString("fir_no"));
                auctionDobj.setStateCdFrom(rs.getString("state_cd_from"));
                auctionDobj.setOffCdFrom(rs.getInt("off_cd_from"));
                auctionDobj.setReason(rs.getString("reason"));
                auctionDobj.setOrderNo(rs.getString("order_no"));
                auctionDobj.setStateCd(rs.getString("state_cd"));
                auctionDobj.setOffCd(rs.getInt("off_cd"));
            }
        } catch (Exception e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            throw new VahanException("Problem In getting Details of Auction");
        } finally {
            try {
                if (tmgr != null) {
                    tmgr.release();
                }
            } catch (Exception e) {
                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            }
        }
        return auctionDobj;
    }

   
}
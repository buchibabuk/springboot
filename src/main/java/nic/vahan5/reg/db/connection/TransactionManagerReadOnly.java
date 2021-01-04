package nic.vahan5.reg.db.connection;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.*;
import java.util.*;

import javax.annotation.PostConstruct;

import nic.vahan5.reg.CommonUtils.CachedRowSetImpl_New;
import nic.vahan5.reg.common.VahanException.VahanException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class TransactionManagerReadOnly {

    private String whereiam;
    private int transactionID;
    private static int transactionCounter = 0;
    private static int releasedTransactionCounter = 0;
    private Connection con;
    private PreparedStatement prstmt;
    private List sqlList = new ArrayList();
    private boolean transactionSuccessfull;
    private boolean commitHasBeenCalledAlready;
    private boolean released;
    private static final Logger LOGGER = Logger.getLogger(TransactionManagerReadOnly.class);
    
    @Autowired
    ConnectionPooling connectionPooling;
    
    public javax.sql.RowSet fetchDetachedRowSet() throws SQLException {

        ResultSet rs = null;
        CachedRowSetImpl_New rowSet = null;
        try {
            rowSet = new CachedRowSetImpl_New();
            rs = prstmt.executeQuery();
            rowSet.populate(rs);
        } catch (SQLException ex) {
            LOGGER.error(ex + "- " + prstmt);
            throw ex;
        } finally {
            try {
                release();
            } catch (Exception ex) {
                LOGGER.error(ex + "- " + prstmt);
            }
        }

        return rowSet;
    }

    public javax.sql.RowSet fetchDetachedRowSetWithoutTrim() throws SQLException {

        ResultSet rs = null;
        CachedRowSetImpl rowSet = null;
        try {
            rowSet = new CachedRowSetImpl();
            rs = prstmt.executeQuery();
            rowSet.populate(rs);
        } catch (SQLException ex) {
            LOGGER.error(ex + "- " + prstmt);
            throw ex;
        } finally {
            try {
                release();
            } catch (Exception ex) {
                LOGGER.error(ex + "- " + prstmt);
            }
        }

        return rowSet;
    }

    public javax.sql.RowSet fetchDetachedRowSet_No_release() throws SQLException {

        ResultSet rs = null;
        CachedRowSetImpl_New rowSet = null;
        try {
            rowSet = new CachedRowSetImpl_New();
            rs = prstmt.executeQuery();
            rowSet.populate(rs);

        } catch (SQLException ex) {
            LOGGER.error(ex + "- " + prstmt);
            throw ex;
        }
        return rowSet;
    }

    public javax.sql.RowSet fetchDetachedRowSetWithoutTrim_No_release() throws SQLException {

        ResultSet rs = null;
        CachedRowSetImpl rowSet = null;
        try {
            rowSet = new CachedRowSetImpl();
            rs = prstmt.executeQuery();
            rowSet.populate(rs);
        } catch (SQLException ex) {
            LOGGER.error(ex + "- " + prstmt);
            throw ex;
        }
        return rowSet;
    }

    public TransactionManagerReadOnly() throws SQLException {
    }
    
	@PostConstruct
	private void init() throws SQLException {
        initialize("getOnwerDobjFromHomologation");
	}

    
    public TransactionManagerReadOnly(String whereiam) throws SQLException {
        initialize(whereiam);
    }

    @Override
    public void finalize() throws Throwable {
        if (!released) {
            LOGGER.info(whereiam + " - DEV_ERROR - V4-R Developer has not called tmgr.release() explicitly.");
            System.out.println("Inside Vahan5 connecion debugging connection not released = "+this.transactionID);
        }        
        super.finalize();
    }

    private void initialize(String whereiam) throws SQLException {
    	System.out.println("Inside Vahan5 connecion debugging TransactionManagerReadOnly.initialize---"+whereiam+" start");
        transactionID = ++transactionCounter;
        SQLException ex = null;
        try {
            con =connectionPooling.getDBConnectionReadOnly();
        } catch (SQLException e) {
            ex = e;
        }
        if (con != null) {
            this.whereiam = whereiam + " {ReadOnly-DbConn}";
            con.setAutoCommit(false);
            sqlList.clear();
            transactionSuccessfull = false;
            // Initialize the already committed transaction flag
            commitHasBeenCalledAlready = false;
            // Initialize flag that the resoureces are assigned
            released = false;
        } else {
            this.whereiam = whereiam + " {ReadOnly-DbConnNotRcvd-R}";
//            ApplicationConfiguration.allowConnReadOnly = false;
//            if (ApplicationConfiguration.deferConnTimeReadOnly == null) {
//                ApplicationConfiguration.deferConnTimeReadOnly = new java.util.Date();
//            }
            if (ex != null) {
                throw new SQLException("Problem in getting the DB Connection.");
            }
        }
        System.out.println("Inside Vahan5 connecion debugging TransactionManagerReadOnly.initialize---transaction detailss "+this.transactionID);
        System.out.println("Inside Vahan5 connecion debugging TransactionManagerReadOnly.initialize---"+this.toString());
        System.out.println("Inside Vahan5 connecion debugging TransactionManagerReadOnly.initialize---"+whereiam+" start");
    }

    public void commit() throws SQLException, VahanException {
        // Check that this method is called once only

        throw new VahanException("Information can't be saved for Read Only Database Connection");

//        if (commitHasBeenCalledAlready) {
//            String msg = "DEV_ERROR : Please call TransactionManager.commit()" + " only once. You already have called it on the" + " same object of TransactionManager. If you want to" + " call commit() again then create a fresh object" + " -OR- call reset() on this object first.";
//            throw new SQLException(msg);
//        }


        // If the commit has been called only when the transaction invoves
        // only one DML, then inform the developer by logging the message.
        //if (sqlList.size() <= 1) {
        //    LOGGER.info(" 0.o.^.o.0   This transaction" + " has only one DML. Better use" + " TransactionManagerReadOnly.executeDMLIndividualTnx(sql)" + "   0.o.^.o.0 " + whereiam);
        //}
        // Commit has been called, so set the commitHasBeenCalledAlready
        //commitHasBeenCalledAlready = true;

        // Commit now
        //con.commit();

        // Control reaches here means transaction is successfull
        //transactionSuccessfull = true;
        //con.setAutoCommit(true);
    }

    public void release() throws Exception {
    	System.out.println("Inside Vahan5 connecion debugging TransactionManagerReadOnly.release---"+whereiam+" start");
        // Increment the release counter
        releasedTransactionCounter++;

        // Set the flag that the release method has been called
        released = true;

        // If the PreparedStatement is created by the user then close it.
        try {
            if (prstmt != null) {
                prstmt.close();
            }
            if (con != null) {
                con.close();
            }
        } finally {
            prstmt = null;
            con = null;
        }
        System.out.println("Inside Vahan5 connecion debugging TransactionManagerReadOnly.release---transaction detailss "+this.transactionID);
        System.out.println("Inside Vahan5 connecion debugging TransactionManagerReadOnly.release---"+this.toString());
        System.out.println("Inside Vahan5 connecion debugging TransactionManagerReadOnly.release---"+whereiam+" end");

    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        if (con == null) {
            initialize(whereiam);
        }
        prstmt = null;
        prstmt = con.prepareStatement(sql);
        return prstmt;
    }

    public PreparedStatement prepareStatement(String sql, int scroll, int conqyirency) throws SQLException {

        prstmt = null;
        prstmt = con.prepareStatement(sql, scroll, conqyirency);
        return prstmt;
    }

    public void reset(String whereIAm) throws SQLException, Exception {
        // Release the resources if it is not released when the reset is called
        if (!released) {
            release();
        }

    }

    /**
     * Return the transactionSuccessfull flag.
     *
     * @return True if the transaction was comitted successfully else false.
     */
    public boolean isTransactionSuccessfull() {
        return transactionSuccessfull;
    }

    public boolean isReleased() {
        return released;
    }

    public String getWhereiam() {
        return whereiam;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public static int getReleasedTransactionCounter() {
        return releasedTransactionCounter;
    }

    public static int getTransactionCounter() {
        return transactionCounter;
    }

    public Connection getConnectionRef() {
        return con;
    }

    @Override
    public String toString() {

        StringBuffer sqls = new StringBuffer();
        int sqlCount = sqlList.size();
        for (int i = 0; i < sqlCount; i++) {
            sqls.append((String) sqlList.get(i));
            if (i != sqlCount - 1) {
                sqls.append("\n");
            }
        }

        // Make toString
        String str = "whereiam=" + whereiam + " transactionSuccessfull=" + transactionSuccessfull + " commitHasBeenCalledAlready=" + commitHasBeenCalledAlready + " transactionID=" + transactionID + " transactionCounter=" + transactionCounter + " releasedTransactionCounter=" + releasedTransactionCounter + " sqlCount=" + sqlCount + "\n" + sqls.toString();
        return str;
    }
}

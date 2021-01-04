package nic.vahan5.reg.db.connection;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.*;
import java.util.*;

import javax.annotation.PostConstruct;

import nic.vahan5.reg.CommonUtils.CachedRowSetImpl_New;
import nic.vahan5.reg.common.ApplicationConfiguration;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class TransactionManagerOne {

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
    private static final Logger LOGGER = Logger.getLogger(TransactionManagerOne.class);
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
            LOGGER.error(ex + "{" + whereiam + "}" + prstmt);
            throw ex;
        } finally {
            try {
                release();
            } catch (Exception ex) {
                LOGGER.error(ex + "{" + whereiam + "}" + prstmt);
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
            LOGGER.error(ex + "{" + whereiam + "}" + prstmt);
            throw ex;
        } finally {
            try {
                release();
            } catch (Exception ex) {
                LOGGER.error(ex + "{" + whereiam + "}" + prstmt);
            }
        }

        return rowSet;
    }

    public javax.sql.RowSet fetchDetachedRowSet_No_release() {

        ResultSet rs = null;
        CachedRowSetImpl_New rowSet = null;
        try {
            rowSet = new CachedRowSetImpl_New();
            rs = prstmt.executeQuery();
            rowSet.populate(rs);

        } catch (SQLException ex) {
            LOGGER.error(ex + "{" + whereiam + "}" + prstmt);
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
            LOGGER.error(ex + "{" + whereiam + "}" + prstmt);
            throw ex;
        }
        return rowSet;
    }

    public TransactionManagerOne(String whereiam) throws SQLException {
        initialize(whereiam);
    }
    public TransactionManagerOne() throws SQLException {
       // initialize(whereiam);
    }
    
    @PostConstruct
	private void init() throws SQLException {
        initialize("getDocumentUPload");
	}

    @Override
    public void finalize() throws Throwable {
        if (!released) {
            LOGGER.info(whereiam + " - DEV_ERROR - V4 Developer has not called tmgr.release() explicitly.");
            LOGGER.info("Inside Vahan5 connecion debugging connection not released = " + this.transactionID);
        }
        super.finalize();
    }

    private void initialize(String whereiam) throws SQLException {
        LOGGER.info("Inside Vahan5 connecion debugging TransactionManager.initialize---" + whereiam + " start");

        transactionID = ++transactionCounter;
        Exception ex = null;
        try {
            con = connectionPooling.getDBConnection();
        } catch (Exception e) {
            ex = e;
        }
        if (con != null) {
            con.setAutoCommit(false);
            sqlList.clear();
            this.whereiam = whereiam;
            transactionSuccessfull = false;
            // Initialize the already committed transaction flag
            commitHasBeenCalledAlready = false;
            // Initialize flag that the resoureces are assigned
            released = false;
        } else {
            this.whereiam = whereiam + " {DbConnNotRcvd}";
            ApplicationConfiguration.allowConn = false;
            if (ApplicationConfiguration.lastConnTime == null) {
                ApplicationConfiguration.lastConnTime = new java.util.Date();
            }
            if (ex != null) {
                throw new SQLException("Problem in getting the DB Connection.");
            }
        }
        LOGGER.info("Inside Vahan5 connecion debugging TransactionManager.initialize---transaction detailss " + this.transactionID);
        LOGGER.info("Inside Vahan5 connecion debugging TransactionManager.initialize---" + this.toString());
        LOGGER.info("Inside Vahan5 connecion debugging TransactionManager.initialize---" + whereiam + " end");
    }

    public void commit() throws SQLException {
        // Check that this method is called once only
        if (commitHasBeenCalledAlready) {
            String msg = "DEV_ERROR : Please call TransactionManager.commit()" + " only once. You already have called it on the" + " same object of TransactionManager. If you want to" + " call commit() again then create a fresh object" + " -OR- call reset() on this object first.";
            throw new SQLException(msg);
        }

        // If the commit has been called only when the transaction invoves
        // only one DML, then inform the developer by logging the message.
        //if (sqlList.size() <= 1) {
        //    LOGGER.info(" 0.o.^.o.0   This transaction" + " has only one DML. Better use" + " TransactionManager.executeDMLIndividualTnx(sql)" + "   0.o.^.o.0 " + whereiam);
        //}
        // Commit has been called, so set the commitHasBeenCalledAlready
        commitHasBeenCalledAlready = true;

        // Commit now
        con.commit();

        // Control reaches here means transaction is successfull
        transactionSuccessfull = true;
        con.setAutoCommit(true);
    }

    public void release() throws Exception {
        LOGGER.info("Inside Vahan5 connecion debugging TransactionManager.release---" + whereiam + " start");
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
        LOGGER.info("Inside Vahan5 connecion debugging TransactionManager.release---transaction detailss " + this.transactionID);
        LOGGER.info("Inside Vahan5 connecion debugging TransactionManager.release---" + this.toString());
        LOGGER.info("Inside Vahan5 connecion debugging TransactionManager.release---" + whereiam + " end");

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

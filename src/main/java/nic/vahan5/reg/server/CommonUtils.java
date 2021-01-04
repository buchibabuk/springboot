package nic.vahan5.reg.server;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.RowSet;

/**
 *
 * @author sushil
 */
public class CommonUtils {

    private static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(CommonUtils.class);

    public static boolean isNullOrBlank(String strCheck) {
        if ((strCheck == null) || ("null".equalsIgnoreCase(strCheck)) || (strCheck.trim().length() <= 0)) {
            return true;
        } else {
            return false;
        }
    }
    
    
    public static boolean isColumnExist(RowSet rs, String colName) throws SQLException {
        boolean status = false;
        ResultSetMetaData rsm = rs.getMetaData();
        int colNum = rsm.getColumnCount();
        for (int i = 1; i <= colNum; i++) {
            if (rsm.getColumnName(i).equalsIgnoreCase(colName)) {
                status = true;
            }
        }

        return status;
    }
}

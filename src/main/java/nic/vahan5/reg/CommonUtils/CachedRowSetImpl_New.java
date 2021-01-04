package nic.vahan5.reg.CommonUtils;

import java.sql.SQLException;
import java.util.Hashtable;

public class CachedRowSetImpl_New extends com.sun.rowset.CachedRowSetImpl {

    public CachedRowSetImpl_New() throws SQLException {
        super();
    }

    public CachedRowSetImpl_New(Hashtable hshtbl) throws SQLException {
        super(hshtbl);
    }

    @Override
    public String getString(String colName) throws SQLException {
        String ret = super.getString(colName);
        if (ret == null) {
            ret = "";
        }
        return ret.trim();
    }

    @Override
    public String getString(int colNumber) throws SQLException {
        String ret = super.getString(colNumber);
        if (ret == null) {
            ret = "";
        }
        return ret.trim();
    }
}

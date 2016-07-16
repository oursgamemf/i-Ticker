package model;

/**
 * @author emanuele
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.DBtk.driverConn;

public class DBtkEvo {

    private static String sDBname;// = "tk";
    private static final String sJdbc = "jdbc:sqlite";
    private static String sDbUrl = sJdbc + ":" + sDBname + ".db";
    private static int iTimeout = 30;
    private static String sDropTable = "DROP TABLE " + sDBname + ";";
    private static String sFieldTableCreate;
    private static String sTable;
    private static String sMakeTable = "CREATE TABLE " + sTable + "(" + sFieldTableCreate + ")";
    private static String sInsertWhere;
    private static String sInsertValue;
    private static String sMakeInsert = "INSERT INTO " + sDBname + "(" + sInsertWhere + ")" + " VALUES(" + sInsertValue + ")";

    public boolean driverConn() {
        // register the driver 
        try {
            Class.forName(sJdbc);
            return true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBtk.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Connection connectOrCreate() {
        if (sDBname == null)
            return null;
        driverConn();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(sDbUrl);
            return conn;
        } catch (SQLException ex) {
            Logger.getLogger(DBtkEvo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public String getsDBname() {
        return sDBname;
    }

    public void setsDBname(String sDBname) {
        DBtkEvo.sDBname = sDBname;
    }

    public int getiTimeout() {
        return iTimeout;
    }

    public void setiTimeout(int iTimeout) {
        DBtkEvo.iTimeout = iTimeout;
    }

    public String getsFieldTableCreate() {
        return sFieldTableCreate;
    }

    public void setsFieldTableCreate(String sFieldTableCreate) {
        DBtkEvo.sFieldTableCreate = sFieldTableCreate;
    }

    public String getsTable() {
        return sTable;
    }

    public void setsTable(String sTable) {
        DBtkEvo.sTable = sTable;
    }

    public String getsInsertWhere() {
        return sInsertWhere;
    }

    public void setsInsertWhere(String sInsertWhere) {
        DBtkEvo.sInsertWhere = sInsertWhere;
    }

    public String getsInsertValue() {
        return sInsertValue;
    }

    public void setsInsertValue(String sInsertValue) {
        DBtkEvo.sInsertValue = sInsertValue;
    }

    public boolean createTable() {
        if ((sTable==null)||(sFieldTableCreate==null))
            return false;
        Connection conn = connectOrCreate();
        try (Statement stat = conn.createStatement();) {
            stat.executeUpdate(sMakeTable);
            conn.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBtkEvo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int fillTable(String DB, String nameTable, String insertColName, String insertValue[]) {
        setsTable(nameTable);
        setsInsertWhere(insertColName);
        int numInsRow = 0;
        Connection conn = connectOrCreate();
        try (Statement stat = conn.createStatement();) {
            for (String tempString : insertValue) {
                setsInsertValue(tempString);
                stat.executeUpdate(sMakeInsert);
                numInsRow += 1;
            }
            conn.close();
            return numInsRow;
        } catch (SQLException ex) {
            Logger.getLogger(DBtkEvo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numInsRow; // nows inserted

    }

}

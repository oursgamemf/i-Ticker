package model;

/**
 * @author emanuele
 */
import controller.RowChoosenTks;
import controller.RowTicker;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sqlite.SQLite;

public class DBtkEvo {

    private static String sDBname;// = "tk";
    private static final String sJdbc = "org.sqlite.JDBC";
    private static String sDbUrl = "jdbc:sqlite:";
    private static final String DB_EXTENSION = ".db";
    private static int iTimeout = 30;
    private static String sDropTable = "DROP TABLE ";
    private static String sFieldTableCreate;
    private static String sTable;
    private static String sMakeTable = "CREATE TABLE ";
    private static String sInsertWhere;
    private static String sInsertValue;
    private static String sMakeInsert = "INSERT INTO " + sDBname + "(" + sInsertWhere + ")" + " VALUES(" + sInsertValue + ")";
    private static String query = null;
    private static final String INSERT = "INSERT INTO ";
    private static final String SELECT_ALL = "SELECT * FROM ";
    private static String ALREADY_IN_DB = "SELECT tk_name FROM ? WHERE tk_name = '?' ";

    public Boolean checkIfAlreadyIn(String tableName, String tkName) {
        Connection conn = connectOrCreate();
        String ifExist = "SELECT tk_name FROM " + tableName + " WHERE tk_name = '" + tkName.trim() + "';";
        try (Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery(ifExist);
            while (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(DBtkEvo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        DBtkEvo.query = query;
    }

    public boolean driverConn() {
        // register the driver 
        try {
            Class.forName(sJdbc);
            return true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBtkEvo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Connection connectOrCreate() {
        if (sDBname == null) {
            return null;
        }
        if (!driverConn()) {
            System.out.println("No DB driver found");
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(sDbUrl + getsDBname() + DB_EXTENSION);

            return conn;
        } catch (SQLException ex) {
            System.out.println("No DB driver found");
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
        if ((sTable == null) || (sFieldTableCreate == null)) {
            System.out.println("nullVAlue");
            return false;
        }
        Connection conn = connectOrCreate();
        String createTableDML = sMakeTable + getsTable() + " (" + getsFieldTableCreate() + ");";
        try (PreparedStatement pstmt = conn.prepareStatement(createTableDML);) {
            pstmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBtkEvo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean createTable(String table, String fieldOfTheTable) {
        if ((table == null) || (fieldOfTheTable == null)) {
            System.out.println("nullVAlue");
            return false;
        }
        Connection conn = connectOrCreate();
        String createTableDML = sMakeTable + table + " (" + fieldOfTheTable + ");";
        try (PreparedStatement pstmt = conn.prepareStatement(createTableDML);) {
            pstmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBtkEvo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void dropTable() {
        Connection conn = connectOrCreate();
        String dropTableDML = sDropTable + getsTable() + ";";
        try (PreparedStatement pstmt = conn.prepareStatement(dropTableDML);) {
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(" Table " + getsTable() + "not exist");
        }

    }

    public void dropTable(String tableToBeDrop) {
        Connection conn = connectOrCreate();
        String dropTableDML = sDropTable + tableToBeDrop + ";";
        try (PreparedStatement pstmt = conn.prepareStatement(dropTableDML);) {
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(" Table " + getsTable() + " not exist");
        }

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

    public RowTicker getAllFromDBData() {
        RowTicker rtFromDB = new RowTicker();
        Connection conn = connectOrCreate();
        String pstmtSelect = SELECT_ALL + getsTable() + ";";
        try (Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery(pstmtSelect);
            while (rs.next()) {
                rtFromDB.setAdjCloseTk(rs.getDouble("adjTk"));
                rtFromDB.setCloseTk(rs.getDouble("closeTk"));
                rtFromDB.setHighTk(rs.getDouble("highTk"));
                rtFromDB.setLowTk(rs.getDouble("lowtk"));
                rtFromDB.setOpenTk(rs.getDouble("openTk"));
                rtFromDB.setDateTk(rs.getDate("dateTk"));
                rtFromDB.setVolumeTk(rs.getDouble("volumeTk"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBtkEvo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rtFromDB;
    }

    public ArrayList<RowChoosenTks> getAllRowChoosenDBData(String tableName) {
        ArrayList<RowChoosenTks> outputData = new ArrayList<>();
        RowChoosenTks rtFromDB = new RowChoosenTks();
        Connection conn = connectOrCreate();
        String pstmtSelect = SELECT_ALL + tableName + ";";
        try (Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery(pstmtSelect);
            while (rs.next()) {
                rtFromDB.setTickerName(rs.getString("tk_name"));
                rtFromDB.setLastDownloadDate(rs.getDate("date_last_dwl"));
                rtFromDB.setAutomaticRefresh(rs.getBoolean("self_dwl"));
                rtFromDB.setRefreshPeriod(rs.getInt("NEXT_DWL"));
                outputData.add(rtFromDB);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBtkEvo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outputData;
    }

    public boolean insertRowTKinDB(ArrayList<RowTicker> information, String query) {
        Connection conn = connectOrCreate();
        boolean createSuccessful = false;
        String pstmtUpdate = INSERT + getsTable() + query + ";";
        try (PreparedStatement pstmt = conn.prepareStatement(pstmtUpdate);) {
            Date a = new Date(Calendar.getInstance().getTime().getTime());
            java.sql.Date sysDate = new java.sql.Date(a.getTime());
            for (RowTicker rowTT : information) {
                pstmt.setDate(1, sysDate);
                pstmt.setDate(2, rowTT.getDateTk());
                pstmt.setDouble(3, rowTT.getOpenTk());
                pstmt.setDouble(4, rowTT.getHighTk());
                pstmt.setDouble(5, rowTT.getLowTk());
                pstmt.setDouble(6, rowTT.getCloseTk());
                pstmt.setDouble(7, rowTT.getVolumeTk());
                pstmt.setDouble(8, rowTT.getAdjCloseTk());
                pstmt.execute();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBtkEvo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return createSuccessful;
    }

    public boolean insRowChoosenTKinDB(ArrayList<RowChoosenTks> information, String query, String targetTable) {
        Connection conn = connectOrCreate();
        boolean createSuccessful = false;
        String pstmtUpdate = INSERT + targetTable + " " + query + ";";
        try (PreparedStatement pstmt = conn.prepareStatement(pstmtUpdate);) {
            Date a = new Date(iTimeout);
            java.sql.Date sysDate = new java.sql.Date(a.getTime());
            for (RowChoosenTks rowTT : information) {
                // date_last_dwl,self_dwl ,NEXT_DWL
                pstmt.setString(1, rowTT.getTickerName());
                pstmt.setDate(2, rowTT.getLastDownloadDate());
                pstmt.setBoolean(3, rowTT.getAutomaticRefresh());
                pstmt.setInt(4, rowTT.getRefreshPeriod());
                pstmt.execute();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBtkEvo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return createSuccessful;
    }

}

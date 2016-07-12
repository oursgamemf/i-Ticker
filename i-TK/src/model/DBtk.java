/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author emanuele
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBtk {

    /**
     * Connect to a sample database
     *
     * @param fileName the database file name
     */
    private static String sTempDb = "tk.db";
    private static String sJdbc = "jdbc:sqlite";
    private static String sDbUrl = sJdbc + ":" + sTempDb;
    private static int iTimeout = 30;
    private static String sDropTable = "DROP TABLE " +  sTempDb + ";";
    private static String sMakeTable = "CREATE TABLE "+ sTempDb  +
                                        " (ID INT PRIMARY KEY NOT NULL," +
                                        " DATE_TK DATE, " + 
                                        " OPEN_TK NUMBER(8,2), " + 
                                        " HIGH_TK NUMBER(8,2), " + 
                                        " LOW_TK NUMBER(8,2), " + 
                                        " CLOSE_TK NUMBER(8,2), " + 
                                        " VOLUME_TK NUMBER(8,2), " + 
                                        " ADJ_TK NUMBER(8,2))"; 
    private static String sMakeInsert = "INSERT INTO dummy VALUES(1,'Hello from the database')";
    private static String sMakeSelect = "SELECT response from dummy";

    public static void preConn() {
        // register the driver 
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBtk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void dropTable() {
        preConn();
        Statement stat = null;
        try (Connection conn = DriverManager.getConnection(sDbUrl)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                stat = conn.createStatement();
                stat.executeUpdate(sDropTable);
                System.out.println("A table has been deleted.");
            }
            else
                System.out.println("No DB detected.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void createNewDatabase() {
        preConn();
        try (Connection conn = DriverManager.getConnection(sDbUrl);) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void connect() {
        Connection conn = null;
        try {
            preConn();
            // create a connection to the database
            conn = DriverManager.getConnection(sDbUrl);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            createNewDatabase();
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
       
    public static void fillDB(String insertData){
        preConn(); 
        try(Connection conn = DriverManager.getConnection(sDbUrl);){
            try(Statement stat = conn.createStatement();){
                stat.executeUpdate(insertData);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBtk.class.getName()).log(Level.SEVERE, null, ex);
        }
     
       
            
            
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        connect();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.ManageExcel.getAllDataFromFile;
import static controller.ManageExcel.setInputFile;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.Date;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.DBtkEvo;

/**
 * @author emanuele
 */
public class TickerController {

    private static final String URL_TEST_CONN = "https://www.google.it";
    private static final String PATH_TO_CONFIG = "i_tk.config";

    // NO
    public static ArrayList<String> getColumnFromIndex(int colIndex, ArrayList<ArrayList<String>> datas) {
        // Retun all values of a column except the first one (header -> name of the column)

        ArrayList<String> values = new ArrayList<String>();
        for (int row = 1; datas.get(row).get(colIndex) != null; row++) {
            values.add(datas.get(row).get(colIndex));
            try {
                datas.get(row + 1).get(colIndex);
            } catch (NullPointerException e) {
                return values;
            } catch (IndexOutOfBoundsException e) {
                return values;
            }
        }
        return values;

    }

    //NO
    public static ArrayList<Double> string2DoubleArray(int colIndex, ArrayList<String> datas) {
        // Change type of the elements of the array into Double

        ArrayList<Double> values = new ArrayList<>();
        for (int row = 0; datas.get(row) != null; row++) {

            Double value = Double.valueOf(datas.get(row));
            values.add(value);
            try {
                datas.get(row + 1);
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                return values;
            }
        }
        return values;
    }

    //N0
    public static ArrayList<Date> string2DateArray(int colIndex, ArrayList<String> datas) {
        // Change type of the elements of the array into Date

        ArrayList<Date> values = new ArrayList<>();
        for (int row = 0; datas.get(row) != null; row++) {

            Date value = Date.valueOf(datas.get(row));
            values.add(value);
            try {
                datas.get(row + 1);
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                return values;
            }
        }
        return values;

    }

    public static String getTicker(String tk) {
        // Remove space outside words

        String newTk = tk.trim();
        return newTk;

    }

    public boolean getWebConnection() {
        try {
            //make a URL to a known source
            URL url = new URL("http://www.google.com");
            //open a connection to that source
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
            //trying to retrieve data from the source. If there is no connection, this line will fail
            Object objData = urlConnect.getContent();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    public String makeURL(String tk) {
        // Compose full URL
        return "";
    }

    public String makeURL(String tk, Date startDate, Date stopDate) {
        // Compose full URL
        return "";
    }

    public Boolean searchTK(String myUrl) {
        // connect and try to download
        // add to BD
        // run Manage File
        return false;
    }

    public boolean addTKToDB(RowTicker TK, DBtkEvo dbInterface) {
        // Date sysDate, int dayNextUpDate
        // Campi del DB : id, tickerName, InsertDateWithMS, 
        return false;
    }

    // ManageFile
    public static DBtkEvo runMeAtStart() {
        //Linux
        String opSys = System.getProperty("os.name");  
        Path curPath = Paths.get(System.getProperty("user.dir"));
        String sourceFullPath = curPath.getParent().toString()+ File.separator + PATH_TO_CONFIG; //.getParent()
        System.out.println(sourceFullPath);
        setInputFile(curPath.getParent().toString()+ File.separator + PATH_TO_CONFIG);
        ArrayList<ArrayList<String>> configData = getAllDataFromFile(';');
        DBtkEvo sessionDB = new DBtkEvo();
        // Data from file
        System.out.println("size config" + configData.size());
        sessionDB.setsDBname(configData.get(0).get(1));
        sessionDB.setsTable(configData.get(1).get(1));
        sessionDB.setsFieldTableCreate(configData.get(2).get(1));
        //sessionDB.createTable();
        System.out.println(sessionDB.getsDBname());
        System.out.println(sessionDB.getsTable());
        System.out.println(sessionDB.getsFieldTableCreate());
        return sessionDB;
    }

}

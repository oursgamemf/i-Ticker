/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.ManageExcel.getAllDataFromFile;
import static controller.ManageExcel.getColNumFromTxt;
import static controller.ManageExcel.getHeaderList;
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
    private static final String PATH_TO_CSV = "table.csv";
    private static final String opSys = System.getProperty("os.name");  
    private static final Path curPath = Paths.get(System.getProperty("user.dir"));
    private static final String configFullPath = curPath.getParent().toString()+ File.separator + PATH_TO_CONFIG;
    private static final String csvFullPath = curPath.getParent().toString()+ File.separator + PATH_TO_CSV;

    public static String getConfigFullPath() {
        return configFullPath;
    }

    public static String getCsvFullPath() {
        return csvFullPath;
    }


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

        String url = "real-chart.finance.yahoo.com/table.csv?s=";
        url = url + tk;
        url = url + "&a=01&b=01&c=1900&d=12&e=12&f=2116&g=m&ignore=.csv";
        
        return url;
    }

    public String makeURL(String tk, Date startDate, Date stopDate) {
        // Compose full URL
        String url = "real-chart.finance.yahoo.com/table.csv?s=";
        url = url + tk;
        String starting, ending;
        url = url + "&a=01&b=01&c=1900&d=12&e=12&f=2116&g=m&ignore=.csv";
        
        return url;
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
         //.getParent()
        ArrayList<ArrayList<String>> configData = getAllDataFromFile(configFullPath,';');
        DBtkEvo sessionDB = new DBtkEvo();
        sessionDB.setsDBname(configData.get(0).get(1));
        sessionDB.setsTable(configData.get(1).get(1));
        sessionDB.setsFieldTableCreate(configData.get(2).get(1));
        sessionDB.setQuery(configData.get(3).get(1));    
        
        sessionDB.connectOrCreate();
        sessionDB.dropTable();
        sessionDB.createTable();
 
        return sessionDB;
    }
    
    public static ArrayList<RowTicker> getRowTickerArray(ArrayList<ArrayList<String>> datas) {
        // Return an Array whose elements are instances of the class RowTicker.
        
        ArrayList<RowTicker> myTicker = new ArrayList<RowTicker>();
        ArrayList<String> headers = getHeaderList(datas);
        Integer colNumber = headers.size();
        
        for (int row = 1; datas.get(row) != null; row++) {
            
            RowTicker myRowTicker = new RowTicker();
            
            for (String h: headers){
                Integer col = getColNumFromTxt(h, datas);
                switch (h.toLowerCase().trim()){
                    case "date":
                        Date dateVal = Date.valueOf(datas.get(row).get(col));
                        myRowTicker.setDateTk(dateVal);
                        break;
                    case "open":
                        Double openVal = Double.valueOf(datas.get(row).get(col));
                        myRowTicker.setOpenTk(openVal);
                        break;
                    case "high":
                        Double highVal = Double.valueOf(datas.get(row).get(col));
                        myRowTicker.setHighTk(highVal);
                        break;
                    case "low":
                        Double lowVal = Double.valueOf(datas.get(row).get(col));
                        myRowTicker.setLowTk(lowVal);
                        break;
                    case "close":
                        Double closeVal = Double.valueOf(datas.get(row).get(col));
                        myRowTicker.setCloseTk(closeVal);
                        break;
                    case "volume":
                        Double volumeVal = Double.valueOf(datas.get(row).get(col));
                        myRowTicker.setVolumeTk(volumeVal);
                        break;
                    case "adj close":
                        Double adjCloseVal = Double.valueOf(datas.get(row).get(col));
                        myRowTicker.setAdjCloseTk(adjCloseVal);
                        break;
                }
            }
            
            myTicker.add(myRowTicker);
            try {
                datas.get(row + 1);
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                break;
            }
        }
        return myTicker;
    }

}

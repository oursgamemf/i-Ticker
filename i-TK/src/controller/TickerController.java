/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.ManageExcel.getColNumFromTxt;
import static controller.ManageExcel.getHeaderList;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import model.DBtkEvo;
import org.apache.commons.io.FileUtils;
import static controller.ManageExcel.getAllDataFromFile;
import java.util.Collections;
import javax.swing.JTextField;

/**
 * @author emanuele
 */
public class TickerController {

    private static final String URL_TEST_CONN = "https://www.google.it";
    private static final String PATH_TO_CONFIG = "i_tk.config";
    private static final String PATH_TO_TEMP_CONFIG = "myTempFile.config";
    private static final String PATH_TO_CSV = "table.csv";
    private static final String PATH_TO_DWL = "myDwlFile.csv";
    private static final String PATH_TO_XLS = "renameME.xls";
    private static final String URL_TO_SRC_DATA = "http://real-chart.finance.yahoo.com/table.csv?s=";//=PHAU.MI&a=05&b=20&c=2007&d=06&e=21&f=2016&g=m&ignore=.csv";
    private static final String FULL_TEST_URL = "http://real-chart.finance.yahoo.com/table.csv?s=PHAU.MI&a=05&b=20&c=2007&d=06&e=21&f=2016&g=m&ignore=.csv";
    private static final String opSys = System.getProperty("os.name");
    private static final Path curPath = Paths.get(System.getProperty("user.dir"));
    private static final String insideFullPath = curPath.toString() + File.separator;//curPath.getParent().toString() + File.separator
    private static final String configFullPath = insideFullPath + PATH_TO_CONFIG;
    private static final String configTempFullPath = insideFullPath + PATH_TO_TEMP_CONFIG;

//    public TickerController(){
//        try {
//         
//        } catch (IOException ex) {
//            Logger.getLogger(TickerController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public static String getInsideFullPath() {
        return insideFullPath;
    }

    public static String getConfigTempFullPath() {
        return configTempFullPath;
    }
    private static final String csvFullPath = insideFullPath + PATH_TO_CSV;
    private static final String dwlFullPath = insideFullPath + PATH_TO_DWL;
    private static final String outExlFullPath = insideFullPath + PATH_TO_XLS;

    public static String getDwlFullPath() {
        return dwlFullPath;
    }

    public static String getConfigFullPath() {
        return configFullPath;
    }

    public static String getCsvFullPath() {
        return csvFullPath;
    }

    public static String getTicker(String tk) {
        // Remove space outside words

        String newTk = tk.trim();
        return newTk;

    }

    public static boolean getWebConnection() {
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

    public boolean addTKToDB(RowTicker TK, DBtkEvo dbInterface) {
        // Date sysDate, int dayNextUpDate
        // Campi del DB : id, tickerName, InsertDateWithMS, 
        return false;
    }

    // ManageFile
    public static ArrayList<Object> runMeAtStart() {
        //Linux
        //.getParent()
        ArrayList<Object> loadSet = new ArrayList<>();
        ArrayList<ArrayList<String>> configData = getAllDataFromFile(configFullPath, ';');
        DBtkEvo sessionDB = new DBtkEvo();
        sessionDB.setsDBname(configData.get(0).get(1));
        sessionDB.setsTable(configData.get(1).get(1));
        sessionDB.setsFieldTableCreate(configData.get(2).get(1));
        sessionDB.setQuery(configData.get(3).get(1));
        
        sessionDB.connectOrCreate();
        //sessionDB.dropTable(); // Remove it!!!
        sessionDB.createTable();

        // Second table
        sessionDB.setsFieldTableCreate(configData.get(4).get(1));
        //sessionDB.dropTable(configData.get(4).get(1)); // Remove it!!!
        Boolean testSecDB = sessionDB.createTable(configData.get(4).get(1), configData.get(5).get(1));

        loadSet.add(sessionDB);
        loadSet.add(configData.get(0).get(1));
        loadSet.add(configData.get(1).get(1));
        loadSet.add(configData.get(2).get(1));
        loadSet.add(configData.get(3).get(1));
        loadSet.add(configData.get(4).get(1));
        loadSet.add(configData.get(5).get(1));
        loadSet.add(configData.get(6).get(1));
        loadSet.add(configData.get(7).get(1));
        loadSet.add(configData.get(8).get(1));
        return loadSet;
    }

    public static ArrayList<RowTicker> getRowTickerArray(ArrayList<ArrayList<String>> datas) {
        // Return an Array whose elements are instances of the class RowTicker.

        ArrayList<RowTicker> myTicker = new ArrayList<>();
        ArrayList<String> headers = getHeaderList(datas);
        Integer colNumber = headers.size();
        for (int row = 1; datas.get(row) != null; row++) {

            RowTicker myRowTicker = new RowTicker();
            ////////////////////////////////////////////////////////////////////
            // Remove the second row of each case statement (but date one)
            ////////////////////////////////////////////////////////////////////
            for (String h : headers) {
                Integer col = getColNumFromTxt(h, datas);
                switch (h.toLowerCase().trim()) {
                    case "date":
                        Date dateVal = Date.valueOf(datas.get(row).get(col));
                        myRowTicker.setDateTk(dateVal);
                        break;
                    case "open":
                        Double openVal = Double.valueOf(datas.get(row).get(col));
                        //openVal = openVal * Math.random();
                        myRowTicker.setOpenTk(openVal);
                        break;
                    case "high":
                        Double highVal = Double.valueOf(datas.get(row).get(col));
                        //highVal = highVal * Math.random();
                        myRowTicker.setHighTk(highVal);
                        break;
                    case "low":
                        Double lowVal = Double.valueOf(datas.get(row).get(col));
                        //lowVal = lowVal * Math.random();
                        myRowTicker.setLowTk(lowVal);
                        break;
                    case "close":
                        Double closeVal = Double.valueOf(datas.get(row).get(col));
                        //closeVal = closeVal * Math.random();
                        myRowTicker.setCloseTk(closeVal);
                        break;
                    case "volume":
                        Double volumeVal = Double.valueOf(datas.get(row).get(col));
                        //volumeVal = volumeVal * Math.random();
                        myRowTicker.setVolumeTk(volumeVal);
                        break;
                    case "adj close":
                        Double adjCloseVal = Double.valueOf(datas.get(row).get(col));
                        //adjCloseVal = adjCloseVal * Math.random();
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

    public static String makeURL(String tk) {
        // Compose full URL
        String url = URL_TO_SRC_DATA + tk;
        url = url + "&a=01&b=01&c=1900&d=12&e=12&f=2116&g=m&ignore=.csv";
        return url;
    }

    public static String makeURL(String tk, Date startDate, Date stopDate) throws UnsupportedEncodingException {
        // Compose full URL
        String url = URL_TO_SRC_DATA + tk;

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        String startYear = String.valueOf(startCal.get(Calendar.YEAR));
        String startMonth = String.valueOf(startCal.get(Calendar.MONTH));
        if (startMonth.length() == 1) {
            startMonth = "0" + startMonth;
        }
        String startDay = String.valueOf(startCal.get(Calendar.DAY_OF_MONTH));
        if (startDay.length() == 1) {
            startDay = "0" + startDay;
        }

        Calendar stopCal = Calendar.getInstance();
        stopCal.setTime(stopDate);
        String stopYear = String.valueOf(stopCal.get(Calendar.YEAR));
        String stopMonth = String.valueOf(stopCal.get(Calendar.MONTH));
        if (stopMonth.length() == 1) {
            stopMonth = "0" + stopMonth;
        }
        String stopDay = String.valueOf(stopCal.get(Calendar.DAY_OF_MONTH));
        if (stopDay.length() == 1) {
            stopDay = "0" + stopDay;
        }

        url = url + "&a=" + startDay + "&b=" + startMonth + "&c=" + startYear;
        url = url + "&d=" + stopDay + "&e=" + stopMonth + "&f=" + stopYear + "&g=m&ignore=.csv";
        url = java.net.URLEncoder.encode(url, "UTF-8");

        return url;
    }

    public static RowChoosenTks addTkChoosenInOBJ(DBtkEvo usingDB, String table, String ticker) {
        RowChoosenTks rct = new RowChoosenTks();
        RowChoosenTks rctAlreadyIn = usingDB.checkIfAlreadyIn(table, ticker);
        if ((rctAlreadyIn == null) || (!rctAlreadyIn.getTickerName().equalsIgnoreCase(ticker))) {
            Date a = new Date(Calendar.getInstance().getTime().getTime());
            java.sql.Date sysDate = new java.sql.Date(a.getTime());
            rct.setTickerName(ticker.trim());
            rct.setAutomaticRefresh(false);
            rct.setLastDownloadDate(sysDate);
            rct.setRefreshPeriod(10);
            return rct;
        }
        return rctAlreadyIn;
    }
    
     public static RowChoosenTks updateTkChoosenInOBJ(RowChoosenTks rct) {
        if (rct != null) {
            Date a = new Date(Calendar.getInstance().getTime().getTime());
            java.sql.Date sysDate = new java.sql.Date(a.getTime());
            rct.setLastDownloadDate(sysDate);
            System.out.println("Date Updated");
            return rct;
        }
        return null;
    }
     
    public static void searchSaveTK(String fileUrl, String nameTK, JTextField txtField) {
        //Code to download
        InputStream input;
        String pathNameDwlCSV = insideFullPath + nameTK.trim() + ".csv";
        File myFile = new File(pathNameDwlCSV);

        try {
            URL myUrl = new URL(fileUrl);
            input = myUrl.openStream();
            FileUtils.copyURLToFile(myUrl, myFile);
            Reader reader = new InputStreamReader(input, "UTF-8");

        } catch (MalformedURLException ex) {
            // Logger.getLogger(TickerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unable to find this Ticker, correct it please.");
            String outMsg = "Impossibile scaricare il file: \'" + nameTK + "\'. Ticker non valido";
            OutputMessage.setOutputText(outMsg, txtField, 2);
        } catch (IOException ex) {
            Logger.getLogger(TickerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unable to find this Ticker, correct it please1.");
            String outMsg = "Impossibile scaricare il file: \'" + nameTK + "\'. Ticker non valido";
            OutputMessage.setOutputText(outMsg, txtField, 2);
        }

    }

    public static ArrayList<RowTicker> sortTicker(ArrayList<RowTicker> myTicker) {
        Collections.sort(myTicker, (RowTicker tk1, RowTicker tk2) -> tk1.getDateTk().compareTo(tk2.getDateTk()));
        return myTicker;
    }

    public static ArrayList<RowTicker> getQuarterlyTicker(ArrayList<RowTicker> myTicker) {

        ArrayList<RowTicker> myQuartTicker = new ArrayList<>();

        myTicker.stream().forEach((myRowTk) -> {
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(myRowTk.getDateTk());
            int month = (calDate.get(Calendar.MONTH));
            if (month == 2 || month == 5 || month == 8 || month == 11) {
                myQuartTicker.add(myRowTk);
            }
        });
        return myQuartTicker;
    }

    public static ArrayList<RowTicker> getAnnualTicker(ArrayList<RowTicker> myTicker) {

        ArrayList<RowTicker> myAnnualTicker = new ArrayList<>();

        myTicker.stream().forEach((myRowTk) -> {
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(myRowTk.getDateTk());
            int month = (calDate.get(Calendar.MONTH));
            if (month == 11) {
                myAnnualTicker.add(myRowTk);
            }
        });
        return myAnnualTicker;
    }
    
 

}

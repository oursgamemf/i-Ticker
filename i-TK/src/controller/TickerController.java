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
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.sql.Date;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import model.DBtkEvo;
import org.apache.commons.io.FileUtils;
import static controller.ManageExcel.getAllDataFromFile;

/**
 * @author emanuele
 */
public class TickerController {

    private static final String URL_TEST_CONN = "https://www.google.it";
    private static final String PATH_TO_CONFIG = "i_tk.config";
    private static final String PATH_TO_CSV = "table.csv";
    private static final String PATH_TO_DWL = "myDwlFile.csv";
    private static final String PATH_TO_XLS = "renameME.xls";
    private static final String URL_TO_SRC_DATA = "http://real-chart.finance.yahoo.com/table.csv?s=";//=PHAU.MI&a=05&b=20&c=2007&d=06&e=21&f=2016&g=m&ignore=.csv";
    private static final String FULL_TEST_URL = "http://real-chart.finance.yahoo.com/table.csv?s=PHAU.MI&a=05&b=20&c=2007&d=06&e=21&f=2016&g=m&ignore=.csv";
    private static final String opSys = System.getProperty("os.name");
    private static final Path curPath = Paths.get(System.getProperty("user.dir"));
    private static final String configFullPath = curPath.getParent().toString() + File.separator + PATH_TO_CONFIG;
    private static final String csvFullPath = curPath.getParent().toString() + File.separator + PATH_TO_CSV;
    private static final String dwlFullPath = curPath.getParent().toString() + File.separator + PATH_TO_DWL;
    private static final String outExlFullPath = curPath.getParent().toString() + File.separator + PATH_TO_XLS;

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

    public boolean addTKToDB(RowTicker TK, DBtkEvo dbInterface) {
        // Date sysDate, int dayNextUpDate
        // Campi del DB : id, tickerName, InsertDateWithMS, 
        return false;
    }

    // ManageFile
    public static DBtkEvo runMeAtStart() {
        //Linux
        //.getParent()
        ArrayList<ArrayList<String>> configData = getAllDataFromFile(configFullPath, ';');
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

            for (String h : headers) {
                Integer col = getColNumFromTxt(h, datas);
                switch (h.toLowerCase().trim()) {
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

    public static void searchSaveTK(String fileUrl,String nameTK ) {
        //Code to download
        InputStream input;
        String pathNameDwlCSV = curPath.getParent().toString() + File.separator + nameTK + ".csv";
        File myFile = new File(pathNameDwlCSV);
        
        try {
            URL myUrl = new URL(fileUrl); 
            input = myUrl.openStream();
            FileUtils.copyURLToFile(myUrl, myFile);
            Reader reader = new InputStreamReader(input, "UTF-8");
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(TickerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TickerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

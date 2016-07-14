/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author emanuele
 */
public class TickerController {
    
    private static String hostnameOrIP = "https://www.google.it";
    
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
    
    
    
    
    public static String getTicker(String tk){
        // Remove space outside words
        
        String newTk = tk.trim();
        return newTk;
        
    }
      
    public boolean getConnection() {
        try {
                //make a URL to a known source
                URL url = new URL("http://www.google.com");

                //open a connection to that source
                HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();

                //trying to retrieve data from the source. If there
                //is no connection, this line will fail
                Object objData = urlConnect.getContent();

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
                return false;
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
                return false;
            }
            return true;
    }
    
    public String makeURL(String tk){
        // Compose full URL
        return "";
    }
    
    public String makeURL(String tk, Date startDate, Date stopDate){
        // Compose full URL
        return "";
    }
    
    public Boolean searchTK(String myUrl){
        // connect and try to download
        // add to BD
        // run Manage File
        return false;
    }
    
    public boolean addToDB(String TK){
        // Date sysDate, int dayNextUpDate
        return false;
    }
    
    // ManageFile
    
    
}

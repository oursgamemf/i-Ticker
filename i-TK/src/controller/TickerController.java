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

/**
 * @author emanuele
 */
public class TickerController {
    
    private static String hostnameOrIP = "https://www.google.it";
    public static String getTicker(String tk){
        // Remove space outside words
        
        String newTk = tk.trim();
        return newTk;
        
    }
    
    public static boolean isInternetReachable()
        {
            try {

                InetAddress address = InetAddress.getByName("www.google.it");

                if(address == null)
                {
                    return false;
                }

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
            return true;
        }
    
    public boolean getConnection() throws UnknownHostException, IOException{
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
                e.printStackTrace();
                return false;
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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

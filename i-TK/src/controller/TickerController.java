/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.sql.Date;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.InetAddress;
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
    
    public boolean getConnection() throws UnknownHostException, IOException{
        boolean connection = false;
        InetAddress[] addresses = InetAddress.getAllByName("www.google.com");
        for (InetAddress address : addresses) {
           if (address.isReachable(10000))
           {   
              connection = true;
           }
           else
           {
              connection = false;
           }
       } 
        return connection;
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

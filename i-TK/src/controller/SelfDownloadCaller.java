/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import model.DBtkEvo;
import view.ViewTicker;

/**
 *
 * @author emanuele
 */
public class SelfDownloadCaller {

    private Thread t;
    private int count;

    public SelfDownloadCaller(final String mesg, int n, String tableDBName, DBtkEvo myDB, ViewTicker vt) {
        count = n;
        t = new Thread(new Runnable() {
            public void run() {
                
                while (count-- > 0) {
                    System.out.println(mesg);
                    try {
                        Thread.sleep(10000);	// 100 msec
                        // If sysdate is > of lastDWL + days 
                        updateChTKsetTRUE(tableDBName, myDB, vt); 
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                System.out.println(mesg + " thread all done.");
            }
        });
        t.setName(mesg + " thread running.");
        t.start();
    }

    public Boolean updateChTKsetTRUE(String tableDBName, DBtkEvo myDB, ViewTicker vt) {
        ArrayList<RowChoosenTks> allChosenTKsToBeDWL = myDB.getAllRowChoosenDownlodableDBData(tableDBName);
        if (allChosenTKsToBeDWL.size() < 1) {
            System.out.println("No Ticker need to be update");
            return false;
        }
        for (RowChoosenTks rct : allChosenTKsToBeDWL) {
            vt.downloadTickerForUpdate(rct.getTickerName()); // Deve aggiornare la data ultimo DWL + sysDate
            System.out.println("Ticker Updated need to be update");
        }
        return true;

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Date;

/**
 *
 * @author virginia
 */
public class RowChoosenTks {
    
    private String tickerName;
    private Date lastDownloadDate;
    private Boolean automaticRefresh;
    private int refreshPeriod;

    public Date getDateTk() {
        return lastDownloadDate;
    }

    public void setLastDownloadDate(Date dateTk) {
        this.lastDownloadDate = lastDownloadDate;
    }

    public Boolean getAutomaticRefresh() {
        return automaticRefresh;
    }

    public void setAutomaticRefresh(Double openTk) {
        this.automaticRefresh = automaticRefresh;
    }

    public int getRefreshPeriod() {
        return refreshPeriod;
    }

    public void setRefreshPeriod(Double highTk) {
        this.refreshPeriod = refreshPeriod;
    }

    public String getTickerName() {
        return tickerName;
    }

    public void setTickerName(Double lowTk) {
        this.tickerName = tickerName;
    }
   
}

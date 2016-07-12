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
public class Ticker {
    private Date[] dateTk;
    private double[] openTk;
    private double[] highTk;
    private double[] lowTk;
    private double[] closeTk;
    private double[] volumeTk;

    public Date[] getDateTk() {
        return dateTk;
    }

    public void setDateTk(Date[] dateTk) {
        this.dateTk = dateTk;
    }

    public double[] getOpenTk() {
        return openTk;
    }

    public void setOpenTk(double[] openTk) {
        this.openTk = openTk;
    }

    public double[] getHighTk() {
        return highTk;
    }

    public void setHighTk(double[] highTk) {
        this.highTk = highTk;
    }

    public double[] getLowTk() {
        return lowTk;
    }

    public void setLowTk(double[] lowTk) {
        this.lowTk = lowTk;
    }

    public double[] getCloseTk() {
        return closeTk;
    }

    public void setCloseTk(double[] closeTk) {
        this.closeTk = closeTk;
    }

    public double[] getVolumeTk() {
        return volumeTk;
    }

    public void setVolumeTk(double[] volumeTk) {
        this.volumeTk = volumeTk;
    }

    public double[] getAdjCloseTk() {
        return adjCloseTk;
    }

    public void setAdjCloseTk(double[] adjCloseTk) {
        this.adjCloseTk = adjCloseTk;
    }
    private double[] adjCloseTk;
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;



import com.opencsv.CSVReader;
import static controller.TickerController.getColumnFromIndex;
import static controller.TickerController.string2DoubleArray;
import static controller.TickerController.string2DateArray;
import controller.RowTicker;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author emanuele.calabro
 */
public class ManageExcel {

    private static String inputFile;
    private static ArrayList<ArrayList<String>> allData = new ArrayList<ArrayList<String>>();

    public static void setInputFile(String inputFile) {
        ManageExcel.inputFile = inputFile;
    }

    public void read() throws IOException {

    }
    
   
    public static int getColNumFromTxt(String headerName, ArrayList<ArrayList<String>> datas) {
        //Return the index of the column whose header name is "headerName"
        
        int i = 0;
        for (i = 0; i < datas.get(0).size(); i++) {
            //System.out.println(datas.get(0).get(i));
            if (datas.get(0).get(i).toString().toLowerCase().equals(headerName.toLowerCase())) {
                return i;
            }
        }
        return 9999;
    }

    public static HashSet<String> getListOfValue(int colSel, ArrayList<ArrayList<String>> datas) {
        // Return all values of a column (colSel) without repetitions
        
        HashSet<String> typeOf = new HashSet<String>();
        for (int i = 1; datas.get(i).get(colSel) != null; i++) {
            //System.out.println(datas.get(i).get(colSel));
            typeOf.add(datas.get(i).get(colSel));
            try {
                datas.get(i + 1).get(colSel);
            } catch (NullPointerException e) {
                return typeOf;
            } catch (IndexOutOfBoundsException e) {
                return typeOf;
            }
        }
        return typeOf;
    }
    
    public static int getRowsData(ArrayList<ArrayList<String>> datas){
        // Get number of rows in "datas" (header included)
        
        int i = 0;
        int retVal = 0;
        while ((datas.get(i).get(0) != null) && (datas.get(i).get(0) != "")){
            retVal += 1;
        }
        return retVal;
    }
    
    public static ArrayList<ArrayList<String>> getAllDataFromFile() {
        // Get datas from csv file to ArrayList of ArrayList
        
        try (CSVReader reader = new CSVReader(new FileReader(inputFile), ',');) {
            String[] nextLine;
            int numRow = 0;
            while ((nextLine = reader.readNext()) != null && numRow < 8000) {
                ArrayList<String> allRow = new ArrayList<String>();
                for (String cell : nextLine) {
                    allRow.add(cell);
                }
                allData.add(allRow);
                numRow += 1;

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManageExcel.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(ManageExcel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return allData;
    }
    
    public static ArrayList<String> getHeaderList(ArrayList<ArrayList<String>> allData){
        // Return an array storing each header (column name) of "allData"
        
        ArrayList<String> myHeader = new ArrayList<>();
        for (int i = 0; i < allData.get(0).size(); i++) {
            if ((allData.get(0).get(i) != null) && (!"".equals(allData.get(0).get(i))) ){
            myHeader.add(allData.get(0).get(i));
            }
        }
        return myHeader;
    }
    
    public static String getColNameFromIndex(int index, ArrayList<ArrayList<String>> datas) {
        //Return the name of the column whose column index is "index"
        
        String columnHeaderName = datas.get(0).get(index);
        return columnHeaderName;
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        setInputFile("C:\\Users\\virginia\\Desktop\\Progettino finanza\\table.csv");
        ArrayList<ArrayList<String>> data = getAllDataFromFile();
//        HashSet<String> setOfData = getListOfValue(getColNumFromTxt("descrizione intervento", data), data);
//        System.out.println(setOfData.size() + "\n -----------");
//        for (String tip : setOfData) {
//            System.out.println(tip);
//        }
        ArrayList<String> headers = getHeaderList(data);
        //for (String in : headers ){
        //    System.out.println(in);
        //}
        //System.out.println(headers.size());
        
        ArrayList<RowTicker> myTicker = new ArrayList<RowTicker>();
        Integer colNumber = headers.size();
        
        for (int row = 1; data.get(row) != null; row++) {
            
            RowTicker myRowTicker = new RowTicker();
            
            for (String h: headers){
                Integer col = getColNumFromTxt(h, data);
                switch (h.toLowerCase()){
                    case "date":
                        Date dateVal = Date.valueOf(data.get(row).get(col));
                        myRowTicker.setDateTk(dateVal);
                        break;
                    case "open":
                        Double openVal = Double.valueOf(data.get(row).get(col));
                        myRowTicker.setOpenTk(openVal);
                        break;
                    case "high":
                        Double highVal = Double.valueOf(data.get(row).get(col));
                        myRowTicker.setHighTk(highVal);
                        break;
                    case "low":
                        Double lowVal = Double.valueOf(data.get(row).get(col));
                        myRowTicker.setLowTk(lowVal);
                        break;
                    case "close":
                        Double closeVal = Double.valueOf(data.get(row).get(col));
                        myRowTicker.setCloseTk(closeVal);
                        break;
                    case "volume":
                        Double volumeVal = Double.valueOf(data.get(row).get(col));
                        myRowTicker.setVolumeTk(volumeVal);
                        break;
                    case "adj close":
                        Double adjCloseVal = Double.valueOf(data.get(row).get(col));
                        myRowTicker.setAdjCloseTk(adjCloseVal);
                        break;
                }
            }
            
            myTicker.add(myRowTicker);
            try {
                data.get(row + 1);
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                break;
            }
        }
    
    Integer index = 80;
    System.out.println("Date: " + myTicker.get(index).getDateTk());
    System.out.println("Open: " + myTicker.get(index).getOpenTk());
    System.out.println("High: " + myTicker.get(index).getHighTk());
    System.out.println("Low: " + myTicker.get(index).getLowTk());
    System.out.println("Close: " + myTicker.get(index).getCloseTk());
    System.out.println("Volume: " + myTicker.get(index).getVolumeTk());
    System.out.println("AdjClose: " + myTicker.get(index).getAdjCloseTk());
    }
}

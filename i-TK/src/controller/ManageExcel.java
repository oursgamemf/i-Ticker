/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.opencsv.CSVReader;
import static controller.RowExcel.addSheet2Excel;

import static controller.TickerController.getRowTickerArray;//??
import controller.RowTicker;
import static controller.TickerController.getAnnualTicker;
import static controller.TickerController.getQuarterlyTicker;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * @author emanuele.calabro
 */
public class ManageExcel {

    private static String inputFile;

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

    public static int getRowsData(ArrayList<ArrayList<String>> datas) {
        // Get number of rows in "datas" (header included)

        int i = 0;
        int retVal = 0;
        while ((datas.get(i).get(0) != null) && (datas.get(i).get(0) != "")) {
            retVal += 1;
        }
        return retVal;
    }

    public static ArrayList<ArrayList<String>> getAllDataFromFile(char sep) {
        ArrayList<ArrayList<String>> allData = new ArrayList<>();
        // Get datas from csv file to ArrayList of ArrayList
        try (CSVReader reader = new CSVReader(new FileReader(inputFile), sep);) {
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

    public static ArrayList<ArrayList<String>> getAllDataFromFile(String csvInputPath, char sep) {
        // Get datas from csv file to ArrayList of ArrayList
        ArrayList<ArrayList<String>> allData = new ArrayList<>();         
        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(csvInputPath)), sep, '"', '|');) {
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

    public static ArrayList<ArrayList<String>> getAllDataFromTKFile(String csvInputName, char sep) {
        String csvInputPath = TickerController.getInsideFullPath() + csvInputName + ".csv";
        // Get datas from csv file to ArrayList of ArrayList
        ArrayList<ArrayList<String>> allData = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(csvInputPath), sep);) {
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

    public static ArrayList<String> getHeaderList(ArrayList<ArrayList<String>> allData) {
        // Return an array storing each header (column name) of "allData"

        ArrayList<String> myHeader = new ArrayList<>();
        for (int i = 0; i < allData.get(0).size(); i++) {
            if ((allData.get(0).get(i) != null) && (!"".equals(allData.get(0).get(i)))) {
                myHeader.add(allData.get(0).get(i).toLowerCase().trim());
                // Comtempla caso più spazi tra "adj close"
            }
        }
        return myHeader;
    }

    public static String getColNameFromIndex(int index, ArrayList<ArrayList<String>> datas) {
        //Return the name of the column whose column index is "index"

        String columnHeaderName = datas.get(0).get(index);
        return columnHeaderName;
    }

    public void save(ArrayList<String[]> list, String pathSaveDwlCSV) {
        File file;
        String pathFileName = pathSaveDwlCSV; // Add TK name
        file = new File(pathFileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Unable to create File " + e);
            }
        }
        try {
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < list.size(); i++) {
                String[] row = list.get(i);
                for (int j = 0; j < row.length; j++) {
                    writer.write(row[j]);
                    if (j != (row.length - 1)) {
                        writer.write(',');
                    } else {
                        writer.write('\n');
                    }
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Unable to write to File " + e);
        }
    }

       public static void createExcel(ArrayList<RowTicker> myTicker, String userSavePath, String fileName){
        Workbook myWb = new XSSFWorkbook();
        CreationHelper myCreateHelper = myWb.getCreationHelper();
      
        String mySheetName = "Mensile";
        addSheet2Excel(myWb, myCreateHelper, mySheetName, myTicker);
        
        ArrayList<RowTicker> myQuarterTicker = getQuarterlyTicker(myTicker);
        String myQuarterSheetName = "Trimestrale";
        addSheet2Excel(myWb, myCreateHelper, myQuarterSheetName, myQuarterTicker);
        
        ArrayList<RowTicker> myAnnualTicker = getAnnualTicker(myTicker);
        String myAnnualSheetName = "Annuale";
        addSheet2Excel(myWb, myCreateHelper, myAnnualSheetName, myAnnualTicker);

        // Write the output to a file
        String outputFilePath = userSavePath + File.separator +fileName + ".xlsx";
        // System.out.println(outputFilePath);
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(outputFilePath);
            myWb.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(ManageExcel.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unable to write the file");
        } catch (IOException ex) {
            //Logger.getLogger(ManageExcel.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unable to write the file I/O Ex");
        }
        
        
       }
 

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<ArrayList<String>> data = getAllDataFromFile(',');
//        HashSet<String> setOfData = getListOfValue(getColNumFromTxt("descrizione intervento", data), data);
//        System.out.println(setOfData.size() + "\n -----------");
//        for (String tip : setOfData) {
//            System.out.println(tip);
//        }
        
    ArrayList<RowTicker> myTicker = getRowTickerArray(data);
    
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

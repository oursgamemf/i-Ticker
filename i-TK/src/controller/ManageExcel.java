/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;



import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    
   
    public static int getColNumFromTxt(String hederName, ArrayList<ArrayList<String>> datas) {
        int i = 0;
        for (i = 0; i < datas.get(0).size(); i++) {
            //System.out.println(datas.get(0).get(i));
            if (datas.get(0).get(i).toString().toLowerCase().equals(hederName.toLowerCase())) {
                return i;
            }
        }
        return 9999;
    }

    public static HashSet<String> getListOfValue(int colSel, ArrayList<ArrayList<String>> datas) {
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

    public static int getRowNumFromData() {
        return 1;
    }

    public static ArrayList<String> getTT(String numInterv, String incAttiv, ArrayList<ArrayList<String>> datas) {
        ArrayList<String> aTT = new ArrayList<String>();
        String myParam = "";
        if (numInterv != null) {
            myParam = numInterv;
        } else {
            myParam = incAttiv;
        }
        // Here call fun to get index of a TT getRow(String aa, Int col);
        return aTT;
    }
    
    public static int getRowsData(ArrayList<ArrayList<String>> datas){
        int i = 0;
        int retVal = 0;
        while ((datas.get(i).get(0) != null) && (datas.get(i).get(0) != "")){
            retVal += 1;
        }
        return retVal;
    }
    
    public static ArrayList<ArrayList<String>> getAllDataFromFile() {
        try (CSVReader reader = new CSVReader(new FileReader(inputFile), ';');) {
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
        ArrayList<String> myHeader = new ArrayList<>();
        for (int i = 0; i < allData.get(0).size(); i++) {
            if ((allData.get(0).get(i) != null) && (!"".equals(allData.get(0).get(i))) ){
            myHeader.add(allData.get(0).get(i));
            }
        }
        return myHeader;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        setInputFile("C:\\Users\\emanuele.calabro\\Documents\\SVG\\Data Fix\\MyTool\\dataSource.csv");
        ArrayList<ArrayList<String>> data = getAllDataFromFile();
//        HashSet<String> setOfData = getListOfValue(getColNumFromTxt("descrizione intervento", data), data);
//        System.out.println(setOfData.size() + "\n -----------");
//        for (String tip : setOfData) {
//            System.out.println(tip);
//        }
        ArrayList<String> headers = getHeaderList(data);
        for (String in : headers ){
            System.out.println(in);
        }
        System.out.println(headers.size());
    }
}

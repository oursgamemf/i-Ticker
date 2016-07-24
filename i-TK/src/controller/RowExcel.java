/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.sl.usermodel.MasterSheet;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author virginia
 */
public class RowExcel {
    
    
    public static void setExcelHeader(Row myHeaderRow){
        myHeaderRow.createCell(0).setCellValue("Date");
        myHeaderRow.createCell(1).setCellValue("High");
        myHeaderRow.createCell(2).setCellValue("Low");
        myHeaderRow.createCell(3).setCellValue("Adj Close");
    }
    
    
    public static void addRow2Excel(Workbook myWb, CreationHelper myCreateHelper, Row myRow, RowTicker myRowTk){
        Cell myCell = myRow.createCell(0);
        CellStyle cellStyle = myWb.createCellStyle();
        cellStyle.setDataFormat( myCreateHelper.createDataFormat().getFormat("yyyy-MM-dd"));
        myCell.setCellValue(myRowTk.getDateTk());
        myCell.setCellStyle(cellStyle);
        myRow.createCell(1).setCellValue(myRowTk.getHighTk());
        myRow.createCell(2).setCellValue(myRowTk.getLowTk());
        myRow.createCell(3).setCellValue(myRowTk.getAdjCloseTk());
    }
    
    public static void addSheet2Excel(Workbook myWb, CreationHelper myCrHelper, String sheetName, ArrayList<RowTicker> myTicker){
        Sheet mySheet = myWb.createSheet(sheetName);
        Row myHeaderRow = mySheet.createRow(0);
        setExcelHeader(myHeaderRow);
        int lastRow = 1;
        for(RowTicker myRowTk: myTicker){
            Row myRow = mySheet.createRow(lastRow);
            addRow2Excel(myWb, myCrHelper, myRow, myRowTk);
            lastRow = lastRow + 1;
        }
    }
    
}

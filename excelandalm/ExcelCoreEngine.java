package com.albertsons.portal.automation.common;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;

import java.util.ArrayList;
import java.util.Iterator;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by bvatrapu on 6/23/2016.
 */
public class ExcelCoreEngine {

    private static String dataExtractorFilePath = null;
    private static String SheetName = null;
    private static String TestScriptName = null;

    private static String strHostOS = null;
    public  FileInputStream strInputdataExtractorFile = null;
    public static String strActualTestScriptVal = null;
    public static String strExecuteScriptVal = null;

    public static XSSFWorkbook objdataExtractorWKB = null;
    public static XSSFSheet objdataExtractorSheet = null;
    public static Row objdataExtractorRow = null;

 public static ExcelCoreEngine excelcore=new ExcelCoreEngine();

    public ExcelCoreEngine(String strdataExtractorFilePath, String strSheetName , String strTestScriptName) {

        this.dataExtractorFilePath = strdataExtractorFilePath;
        this.SheetName = strSheetName;
        this.TestScriptName = strTestScriptName;

    }
    public ExcelCoreEngine()
    {}


    public static ArrayList<Row> extractDesiredTestScriptRows () throws Exception {
        //Path to Excel filename

        File strExecutiondataExtractorFile = new File(dataExtractorFilePath);

        excelcore.strInputdataExtractorFile = new FileInputStream(strExecutiondataExtractorFile);
        objdataExtractorWKB = new XSSFWorkbook(excelcore.strInputdataExtractorFile);
        objdataExtractorSheet = objdataExtractorWKB.getSheet(SheetName);

        ArrayList<Row> targetTestScriptFilteredRows = new ArrayList<Row>();
        //Iterate Rowws
        for (int intRowNo = objdataExtractorSheet.getFirstRowNum(); intRowNo <= objdataExtractorSheet.getLastRowNum(); intRowNo++) {

            objdataExtractorRow = objdataExtractorSheet.getRow(intRowNo);

            if (objdataExtractorRow.getCell(1).getStringCellValue() != null
                    || objdataExtractorRow.getCell(2).getStringCellValue() != null) {
                strActualTestScriptVal = objdataExtractorRow.getCell(1)
                        .getStringCellValue();
                strExecuteScriptVal = objdataExtractorRow.getCell(3)
                        .getStringCellValue();
            }
            //Capture Rows matches with Target Test Script Name and ExecuteScript filed value is "Yes"
            if ((strActualTestScriptVal.equalsIgnoreCase(TestScriptName))) {
                    //&& (strExecuteScriptVal.equalsIgnoreCase("Yes"))) {
                //add matched rows here
                targetTestScriptFilteredRows.add(objdataExtractorRow);
                System.out.println("Executed::"+TestScriptName);

            }

        }


     //   excelcore.strInputdataExtractorFile.close();
        return targetTestScriptFilteredRows;


    }

    //public static ArrayList<Row> extractTestScriptRows () throws Exception {
    public static ArrayList<Row> extractTestScriptColumns () throws Exception {
        //Path to Excel filename
        File strExecutiondataExtractorFile = new File(dataExtractorFilePath);

        excelcore.strInputdataExtractorFile = new FileInputStream(strExecutiondataExtractorFile);
        objdataExtractorWKB = new XSSFWorkbook(excelcore.strInputdataExtractorFile);
        objdataExtractorSheet = objdataExtractorWKB.getSheet(SheetName);

        ArrayList<Row> targetTestScriptColumns = new ArrayList<Row>();
        objdataExtractorRow = objdataExtractorSheet.getRow(0);
//        System.out.println(objdataExtractorRow.getCell(1).getStringCellValue());
  //      System.out.println(objdataExtractorRow.getCell(5).getStringCellValue());

        targetTestScriptColumns.add(objdataExtractorRow);




      return targetTestScriptColumns;
    }

    public static ArrayList<Row> extractTotalData () throws Exception {

        //Path to Excel filename
        //System.out.println(dataExtractorFilePath+":::"+SheetName+":::"+TestScriptName);
        File strExecutiondataExtractorFile = new File(dataExtractorFilePath);

        excelcore.strInputdataExtractorFile = new FileInputStream(strExecutiondataExtractorFile);
        objdataExtractorWKB = new XSSFWorkbook(excelcore.strInputdataExtractorFile);

        objdataExtractorSheet = objdataExtractorWKB.getSheet(SheetName);

        ArrayList<Row> targetTestScripttotalRows = new ArrayList<Row>();

        for (int intRowNo = objdataExtractorSheet.getFirstRowNum(); intRowNo <= objdataExtractorSheet.getLastRowNum(); intRowNo++) {

            objdataExtractorRow = objdataExtractorSheet.getRow(intRowNo);

//            if (objdataExtractorRow.getCell(1).getStringCellValue() != null
//                    || objdataExtractorRow.getCell(2).getStringCellValue() != null) {
//                strActualTestScriptVal = objdataExtractorRow.getCell(1)
//                        .getStringCellValue();
//                strExecuteScriptVal = objdataExtractorRow.getCell(3)
//                        .getStringCellValue();
//            }
            //Capture Rows matches with Target Test Script Name and ExecuteScript filed value is "Yes"
//            if ((strActualTestScriptVal
//                    .equalsIgnoreCase(TestScriptName))
//                    && (strExecuteScriptVal.equalsIgnoreCase("Yes"))) {
                //add matched rows here
                targetTestScripttotalRows.add(objdataExtractorRow);
//            }

        }

        //excelcore.strInputdataExtractorFile.close();
        return targetTestScripttotalRows;

    }
    @AfterSuite
    public void closeexcelStream() throws Exception {
        excelcore.strInputdataExtractorFile.close();
    }

    }

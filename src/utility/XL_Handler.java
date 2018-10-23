package com.utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * This class is for handling all Excel related utilities.
 *
 * @author -
 * @since - 26/12/2016
 */

//http://poi.apache.org/spreadsheet/quick-guide.html

//http://poi.apache.org/spreadsheet/quick-guide.html

//http://poi.apache.org/spreadsheet/quick-guide.html

//http://poi.apache.org/spreadsheet/quick-guide.html

public class XL_Handler {

    FileInputStream file = null;
    Iterator<Row> rows = null;
    Iterator<Cell> cells = null;
    public org.apache.poi.ss.usermodel.Workbook wb = null;
    Sheet sheet = null;
    Row row = null;
    Cell cell = null;
    String path = "";
    FileOutputStream fout = null;

    public XL_Handler(String Path) throws IOException {
        try {
            this.path = Path;
            System.out.println(this.path);
            this.file = new FileInputStream(this.path);
            //this.wb = new XSSFWorkbook(this.file);
            this.wb = WorkbookFactory.create(this.file);
            this.fout = new FileOutputStream(this.path);
            saveExcel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void printXL() throws IOException {

        for (int i = 0; i < this.wb.getNumberOfSheets(); i++) {
            this.sheet = this.wb.getSheetAt(i);
            this.rows = this.sheet.iterator();

            while (this.rows.hasNext()) {
                this.row = this.rows.next();
                System.out.println();
                this.cells = this.row.cellIterator();

                while (this.cells.hasNext()) {
                    this.cell = this.cells.next();
                    System.out.print(this.stringValue(this.cell));
                }
            }

            System.out.println("\n\n");
        }


    }

    public void printXL(String Sheet) throws IOException {
        for (int i = 0; i < this.wb.getNumberOfSheets(); i++) {
            if (Sheet.equals(this.wb.getSheetAt(i).getSheetName())) {
                this.sheet = this.wb.getSheetAt(i);
                this.rows = this.sheet.iterator();

                while (this.rows.hasNext()) {
                    this.row = this.rows.next();
                    System.out.println();
                    this.cells = this.row.cellIterator();

                    while (this.cells.hasNext()) {
                        this.cell = this.cells.next();
                        System.out.print(this.stringValue(this.cell));
                    }
                }

                System.out.println("\n\n");
            }
        }

    }

    public int getRowcount(String Sheet) throws IOException {
        int rowCount = 0;

        this.sheet = this.wb.getSheetAt(wb.getSheetIndex(Sheet));
        this.rows = this.sheet.iterator();

        while (this.rows.hasNext()) {
            rowCount++;
            this.rows.next();
        }
        return rowCount;
    }

    public int col_Index(String Sheet, String colName) throws Exception {
        int rowIndex = 0;
        Sheet tempSheet = this.wb.getSheetAt(wb.getSheetIndex(Sheet));
        Iterator<Row> tempRows = tempSheet.iterator();
        Row tempRow = tempRows.next();
        Iterator<Cell> tempIt = tempRow.cellIterator();
        while (tempIt.hasNext()) {
            Cell tempCell = tempIt.next();
            //System.out.println(tempCell.getStringCellValue());
            if (tempCell.getStringCellValue().equalsIgnoreCase(colName))
                rowIndex = tempCell.getColumnIndex();
        }
        return rowIndex;
    }

    public String get_Cell(String Sheet, String colName, int rowNum) throws Exception {
        this.sheet = this.wb.getSheetAt(wb.getSheetIndex(Sheet));
        this.rows = this.sheet.iterator();
        int index = this.col_Index(Sheet, colName);
        String value = "";
        while (this.rows.hasNext()) {
            this.row = this.rows.next();
            this.cells = this.row.cellIterator();

            while (this.cells.hasNext()) {
                this.cell = this.cells.next();
                if (this.cell.getColumnIndex() == index && this.cell.getRowIndex() == rowNum)
                    value = this.stringValue(this.cell);
            }
        }
        return value;
    }


    public List<String> get_Row(String Sheet, int rowNum) throws Exception {
        this.sheet = this.wb.getSheetAt(wb.getSheetIndex(Sheet));
        this.rows = this.sheet.iterator();
        List<String> value = new ArrayList<String>();
        while (this.rows.hasNext()) {
            this.row = this.rows.next();
            this.cells = this.row.cellIterator();
            if (this.row.getRowNum() == rowNum)

                while (this.cells.hasNext()) {
                    this.cell = this.cells.next();
				  /* if(!this.cell.toString().isEmpty())
				   value.add(this.stringValue(this.cell));*/
                }
        }
        System.out.println(value.size());
        return value;
    }

    public Set<String> getCorrespondingCells(String Sheet, String refCol, String refData, String corresCol) throws Exception {
        this.sheet = this.wb.getSheetAt(wb.getSheetIndex(Sheet));
        this.rows = this.sheet.iterator();
        int refIndex = this.col_Index(Sheet, refCol);
        System.out.println(refIndex);
        int corIndex = this.col_Index(Sheet, corresCol);
        System.out.println(corIndex);
        Set<String> value = new LinkedHashSet<String>();

        while (this.rows.hasNext()) {
            this.row = this.rows.next();
            this.cells = this.row.cellIterator();

            while (this.cells.hasNext()) {
                this.cell = this.cells.next();
                if (this.cell.getColumnIndex() == refIndex && this.cell.toString().contains(refData)) {
                    Iterator<Cell> tempcells = this.row.cellIterator();
                    while (tempcells.hasNext()) {
                        Cell tempcell = tempcells.next();
                        if (tempcell.getColumnIndex() == corIndex)
                            value.add(this.stringValue(tempcell));
                        //System.out.println(tempcell.getStringCellValue());
                    }
                }
            }
        }

        return value;
    }

    public Set<String> getCorrespondingRow(String Sheet, String refCol, String refData) throws Exception {
        this.sheet = this.wb.getSheetAt(wb.getSheetIndex(Sheet));
        this.rows = this.sheet.iterator();
        int refIndex = this.col_Index(Sheet, refCol);
        System.out.println(refIndex);
        Set<String> value = new LinkedHashSet<String>();

        while (this.rows.hasNext()) {
            this.row = this.rows.next();
            this.cells = this.row.cellIterator();

            while (this.cells.hasNext()) {
                this.cell = this.cells.next();
                if (this.cell.getColumnIndex() == refIndex && this.cell.toString().contains(refData)) {
                    Iterator<Cell> tempcells = this.row.cellIterator();
                    while (tempcells.hasNext()) {
                        Cell tempcell = tempcells.next();
                        value.add(this.stringValue(tempcell));
                    }
                }
            }
        }

        return value;
    }


    public String getCorrespondingCell(String Sheet, String refCol, String refData, String corresCol) throws Exception {
        String value = "";
        for (String data : this.getCorrespondingCells(Sheet, refCol, refData, corresCol))
            value = data;

        return value;
    }

    public int rowindex_Col(String Sheet, String colData) {
        int value = 0;
        this.sheet = this.wb.getSheetAt(wb.getSheetIndex(Sheet));
        this.rows = this.sheet.iterator();

        while (this.rows.hasNext()) {
            this.row = this.rows.next();
            this.cells = this.row.cellIterator();

            while (this.cells.hasNext()) {
                this.cell = this.cells.next();
                if (this.cell.toString().equalsIgnoreCase(colData))
                    value = this.cell.getRowIndex();
            }
        }

        return value;
    }

    public Map<Integer, Set<String>> getRows(String Sheet) {
        Map<Integer, Set<String>> retVal = new LinkedHashMap<Integer, Set<String>>();
        int index = 0;

        this.sheet = this.wb.getSheetAt(wb.getSheetIndex(Sheet));
        this.rows = this.sheet.iterator();

        while (this.rows.hasNext()) {
            this.row = this.rows.next();
            this.cells = this.row.cellIterator();
            Set<String> value = new LinkedHashSet<String>();

            while (this.cells.hasNext()) {
                this.cell = this.cells.next();
                value.add(this.stringValue(this.cell));
            }
            retVal.put(index, value);
            index++;
        }

        return retVal;
    }

    public static void main(String[] args) throws Exception {

        XL_Handler xl = null;
        XL_Handler xl1 = null;

        System.out.println(System.getProperty("user.dir"));

        xl = new XL_Handler(System.getProperty("user.dir") + "\\sample.xlsx");

        try {

            xl = new XL_Handler(System.getProperty("user.dir") + "//Test_Data.xlsx");
            xl1 = new XL_Handler(System.getProperty("user.dir") + "//IG_DataSheet.xlsx");

            Map<String, String> sum = new LinkedHashMap<String, String>();
            sum = xl.combineMap(xl.get_Row("Data_Automation", 0), xl.get_Row("Data_Automation", 1));
            for (String value : sum.keySet())
                System.out.println(value + "\t" + sum.get(value));

            System.out.println(xl.getRowcount("TC_01"));

            System.out.println(xl.getRows("TC_01").size());
            xl.printXL("TC01");

            //xl1.append_Sheet("Client", "Client_Id", "sample", "new");


            xl1.saveExcel();

            xl.saveExcel();
        } catch (Exception e) {
            xl.saveExcel();
            xl1.saveExcel();
        }
    }

    public void saveExcel() throws Exception {
        this.wb.write(fout);
        fout.flush();
        fout.close();
    }

    public void append_Sheet(String Sheet, String colName, String value, String rowName) throws Exception {
        int colIndex = col_Index(Sheet, colName);

        Sheet temp_sheet = this.wb.getSheetAt(this.wb.getSheetIndex(Sheet));

        Iterator<Row> temp_rows = temp_sheet.iterator();
        Row trow = null;
        if (rowName.equalsIgnoreCase("new")) {
            trow = temp_sheet.createRow(this.getRowcount(Sheet));
            Cell newCell = trow.createCell(colIndex);
            newCell.setCellValue(value);
        } else {
            while (temp_rows.hasNext()) {
                trow = temp_rows.next();
                Row insRow = trow;
                int rowCt = trow.getLastCellNum();
				/*Iterator<Cell> temp_cells = trow.cellIterator();
				   while(temp_cells.hasNext())*/
                for (int i = 0; i < rowCt; i++) {
                    Cell tcell = trow.getCell(i);
                    if (tcell != null)
                        if (this.stringValue(tcell).equalsIgnoreCase(rowName)) {
                            Cell newCell = insRow.createCell(colIndex);
                            newCell.setCellValue(value);
                        }
                }
            }
        }

    }

    public void append_Sheet(String Sheet, String colName, String value, int rowNum) throws Exception {
        int colIndex = col_Index(Sheet, colName);

        Sheet temp_sheet = this.wb.getSheetAt(this.wb.getSheetIndex(Sheet));

        Iterator<Row> temp_rows = temp_sheet.iterator();
        Row trow = null;
        if (rowNum == -1) {
            trow = temp_sheet.createRow(this.getRowcount(Sheet));
            Cell newCell = trow.createCell(colIndex);
            newCell.setCellValue(value);
        } else {
            while (temp_rows.hasNext()) {
                trow = temp_rows.next();
                Row insRow = trow;
                if (trow.getRowNum() == rowNum) {
                    Cell newCell = insRow.createCell(colIndex);
                    newCell.setCellValue(value);
                }
            }
        }

    }

    public void find_Replace(String Sheet, String colName, String fValue, String rValue) throws Exception {
        int colIndex = col_Index(Sheet, colName);
        this.sheet = this.wb.getSheetAt(wb.getSheetIndex(Sheet));
        this.rows = this.sheet.iterator();

        while (this.rows.hasNext()) {
            this.row = this.rows.next();
            this.cells = this.row.cellIterator();

            while (this.cells.hasNext()) {
                this.cell = this.cells.next();
                if (this.cell.getColumnIndex() == colIndex && this.stringValue(this.cell).equalsIgnoreCase(fValue))
                    this.cell.setCellValue(rValue);

            }

        }
    }

    public Set<String> getColumn(String Sheet, String colName) throws Exception {
        this.sheet = this.wb.getSheetAt(wb.getSheetIndex(Sheet));
        this.rows = this.sheet.iterator();
        int index = this.col_Index(Sheet, colName);
        Set<String> value = new LinkedHashSet<String>();

        while (this.rows.hasNext()) {
            this.row = this.rows.next();
            this.cells = this.row.cellIterator();

            while (this.cells.hasNext()) {
                this.cell = this.cells.next();
                if (this.cell.getColumnIndex() == index && this.cell.getRowIndex() != 0)
                    value.add(this.stringValue(this.cell));
            }
        }
        return value;

    }

    public Map<String, String> combineMap(Set<String> keySet, Set<String> dataSet) {
        Object[] keyValue = keySet.toArray();
        Object[] dataValue = dataSet.toArray();
        Map<String, String> retVal = new LinkedHashMap<String, String>();
        int index = 0;

        if (keySet.size() <= dataSet.size())
            for (String key : keySet) {
                retVal.put(key, dataValue[index].toString());
                index++;
            }
        if (keySet.size() > dataSet.size())
            for (String data : dataSet) {
                retVal.put(keyValue[index].toString(), data);
                index++;
            }

        return retVal;
    }

    public Map<String, String> combineMap(List<String> keySet, List<String> dataSet) {
        Object[] keyValue = keySet.toArray();
        Object[] dataValue = dataSet.toArray();
        Map<String, String> retVal = new LinkedHashMap<String, String>();
        int index = 0;

        if (keySet.size() <= dataSet.size())
            for (String key : keySet) {
                retVal.put(key, dataValue[index].toString());
                index++;
            }
        if (keySet.size() > dataSet.size())
            for (String data : dataSet) {
                retVal.put(keyValue[index].toString(), data);
                index++;
            }

        return retVal;
    }

    private String stringValue(Cell data) {
        String value = "";

        if (data.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            int j = (int) data.getNumericCellValue();
            value = String.valueOf(j);
        } else
            value = data.toString();

        return value;
    }
}
	


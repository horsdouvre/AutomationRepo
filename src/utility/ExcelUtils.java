package utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	public static String path;
	public static FileInputStream fis;
	public static FileOutputStream fileOut;
	private static XSSFWorkbook workbook;
	private static XSSFSheet sheet;
	private static XSSFRow row;
	private static XSSFCell  cell;
	private static String testCaseColName = "TestCaseName";

	public static void openExcel (String path, String functionality) throws Exception {
		System.out.println(path);
		System.out.flush();
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);			
			sheet = workbook.getSheet(functionality);

		} catch (Exception e) {
			Log.error(e);
			throw(e);
		}
	}

	public static void closeExcelFile(){
		try {
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum()+2;
			return number;
		}
	}

	public static int getColumnCount(String sheetName) {
		if (!isSheetExist(sheetName))
			return -1;
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);
		if (row == null)
			return -1;
		return row.getLastCellNum();
	}

	public static String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";
			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim()
						.equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);

			if (cell == null)
				return "";
			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) 
			{
				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					double d = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR)))
							.substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/"
							+ cal.get(Calendar.MONTH) + 1 + "/" + cellText;
				}
				return cellText;
			} else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA)
			{
				String cellText = String.valueOf(cell.getStringCellValue());
				return cellText;
			}else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colName
					+ " does not exist in xls";
		}
	}

	public static String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null)
				return "";
			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) 
			{
				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR)))
							.substring(2);
					cellText = cal.get(Calendar.MONTH) + 1 + "/"
							+ cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;
				}
				return cellText;
			} else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA)
			{
				String cellText = String.valueOf(cell.getStringCellValue());
				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum
					+ " does not exist  in xls";
		}
	}

	public static boolean setCellData(String sheetName, String colName, int rowNum, String result, String path) {
		try {
			int colNum =-1;
			row = sheet.getRow(0);
			rowNum--;
			for (int i = 0; i<row.getLastCellNum();i++){
				cell = row.getCell(i);
				if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING ){
					String text = cell.getStringCellValue();
					if(colName.equals(text)){
						colNum = i;
						break;
					}
				}
			}

			row = sheet.getRow(rowNum);
			cell = row.getCell(colNum);

			if (cell == null) {
				cell = row.createCell(colNum);
				cell.setCellValue(result);
			} else {
				cell.setCellValue(result);
			}

			FileOutputStream fos = new FileOutputStream(path);
			workbook.write(fos);
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean isSheetExist(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			index = workbook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1)
				return false;
			else
				return true;
		} else
			return true;
	}

	//Implemented By Bhavdeep Patel
	public static String[][] getAllData(String sheetName) {
		try 
		{
			int row = getRowCount(sheetName);
			int col = getColumnCount(sheetName);

			String[][] testData = new String[row-2][col];
			int i,j;
			for(i=0;i<row-2;i++)
			{
				for(j=0;j<col;j++)
				{
					testData[i][j] = getCellData(sheetName, j, i+2);
				}
			}
			return testData;	
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			throw(e);
		}
	}

	public static int getRowIndex(String sheetName, String tcName){
		try{
			int index=0;

			if(sheetName.equals("Master")){
				for(int i=2; i<= getRowCount(sheetName);i++){
					if(getCellData(sheetName,"TestCaseName",i).equalsIgnoreCase(tcName)){
						index = i;
						break;
					}
				}
			}
			else {
				for (int i = 2; i <= getRowCount(sheetName); i++) {
					if (getCellData(sheetName, "TestCaseName", i).equalsIgnoreCase(tcName) && getCellData(sheetName,"Result",i).isEmpty()) {
						index = i;
						break;
					}
				}
			}
			return index;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			throw(e);
		}
	}

	public static int getTotal(String sheetName, String tcName){
		try{
			int count = 0;

			for(int i=2; i<=getRowCount(sheetName);i++){
				if(getCellData(sheetName,"TestCaseName",i).equalsIgnoreCase(tcName)){
					count++;
				}
			}
			return count;
		}
		catch (Exception e){
			throw(e);
		}
	}

	public String[] getRowData(String sheetName, String tcName){
		try{
			int row = getRowIndex(sheetName, tcName);
			int colCount = getColumnCount(sheetName);
			String [] rowValues = new String [colCount-3];
			sheet = workbook.getSheet(sheetName);

			for (int i=0;i<(colCount-3);i++){
				rowValues[i]= getCellData(sheetName,i+1,row);
			}
			return rowValues;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			throw(e);
		}
	}

	public String[] getColumnData(String sheetName){
		try
		{
			int row = getRowCount(sheetName);
			String [] colValues = new String [row-2];
			sheet = workbook.getSheet(sheetName);

			for (int i=0;i<row-2;i++){
				colValues[i]= getCellData(sheetName, testCaseColName, i+2);
			}

			return colValues;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			throw(e);
		}
	}

	public boolean addSheet(String sheetname) {
		FileOutputStream fileOut;
		try {
			workbook.createSheet(sheetname);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean removeSheet(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return false;
		FileOutputStream fileOut;
		try {
			workbook.removeSheetAt(index);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean addColumn(String sheetName, String colName) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1)
				return false;
			XSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			if (row == null)
				row = sheet.createRow(0);
			if (row.getLastCellNum() == -1)
				cell = row.createCell(0);
			else
				cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(colName);
			cell.setCellStyle(style);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean removeColumn(String sheetName, int colNum) {
		try {
			if (!isSheetExist(sheetName))
				return false;
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			XSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			//XSSFCreationHelper createHelper = workbook.getCreationHelper();
			style.setFillPattern(HSSFCellStyle.NO_FILL);
			for (int i = 0; i < getRowCount(sheetName); i++) {
				row = sheet.getRow(i);
				if (row != null) {
					cell = row.getCell(colNum);
					if (cell != null) {
						cell.setCellStyle(style);
						row.removeCell(cell);
					}
				}
			}
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean addHyperLink(String sheetName, String screenShotColName, String testCaseName, int index, String url, String message) {
		url = url.replace('\\', '/');
		if (!isSheetExist(sheetName))
			return false;
		sheet = workbook.getSheet(sheetName);
		for (int i = 2; i <= getRowCount(sheetName); i++) {
			if (getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)) {
				setCellData(sheetName, screenShotColName, i + index, message,
						url);
				break;
			}
		}
		return true;
	}

	public int getCellRowNum(String sheetName, String colName, String cellValue) {

		for (int i = 2; i <= getRowCount(sheetName); i++) {
			if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
				return i;
			}
		}
		return -1;
	}

	/*public static boolean setCellData(String sheetName, String colName, int rowNum, String data, String URL) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			if (rowNum <= 0)
				return false;
			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim()
						.equalsIgnoreCase(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;
			sheet.autoSizeColumn(colNum); 
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);
			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);
			cell.setCellValue(data);
			XSSFCreationHelper createHelper = workbook.getCreationHelper();
			CellStyle hlink_style = workbook.createCellStyle();
			XSSFFont hlink_font = workbook.createFont();
			hlink_font.setUnderline(XSSFFont.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());
			hlink_style.setFont(hlink_font);
			XSSFHyperlink link = createHelper
					.createHyperlink(XSSFHyperlink.LINK_FILE);
			link.setAddress(URL);
			cell.setHyperlink(link);
			cell.setCellStyle(hlink_style);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}*/
}






package com.accenture.aaft.excel.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.vo.ExcelTestDataVO;

/**
 * Class is used to read the test data from excel
 *
 * @author vijay.venkatappa
 */
public class ExcelTestDataReader{

  /**
   * Method is used to read the test data from excel
   *
   * @param testName - represents script name
   * @return LinkedHashMap contains script name and test data
   */
  public LinkedHashMap<String, List<ExcelTestDataVO>> readTestData(String testName) {

	//CTLogger.writeToLog("ExcelTestDataReader", "readWorkbook()", "started");
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String filePath = System.getProperty("user.dir") +System.getProperty("file.separator") + propertyFileReader.getValue("TEST_DATA_FILE");

	
	if(null!=propertyFileReader.getValue("EXCEL_EXTENSION") && !propertyFileReader.getValue("EXCEL_EXTENSION").equals("")
			&& propertyFileReader.getValue("EXCEL_EXTENSION").equalsIgnoreCase("xlsx")){
		filePath = filePath+"x";
	}
	
	LinkedHashMap<String, List<ExcelTestDataVO>> iterationDataMap = new LinkedHashMap<String, List<ExcelTestDataVO>>();
	File fileForScript = null;
	FileInputStream fileInputStreamForScript = null;
	Workbook workbookForScript = null;
	Sheet sheetDetailsForScript = null;

	try {
		fileForScript = new File(filePath);
		fileInputStreamForScript = new FileInputStream(fileForScript);
		
		if(filePath.contains("xlsx")) {
			workbookForScript = new XSSFWorkbook(fileInputStreamForScript);
		}else{
			workbookForScript = new HSSFWorkbook(fileInputStreamForScript);
		}
		sheetDetailsForScript = workbookForScript.getSheetAt(0);
		int rows = sheetDetailsForScript.getPhysicalNumberOfRows();

	  
	  for (int i = 6; i < rows; i++) {
		  
		GetExcelCellVaueUtil cellVaueUtil = new GetExcelCellVaueUtil();
		String scriptName = cellVaueUtil.getCellValue(sheetDetailsForScript.getRow(i).getCell(1));
		String value = cellVaueUtil.getCellValue(sheetDetailsForScript.getRow(i).getCell(2));

		if (value.equalsIgnoreCase("yes") && scriptName.equals(testName)) {
		  String value1 = cellVaueUtil.getCellValueForFirstColumn(sheetDetailsForScript.getRow(i).getCell(0));
		  int  lastColumn = sheetDetailsForScript.getRow(i).getLastCellNum();
		  List<ExcelTestDataVO> listExcelTestDataVOs = new ArrayList<ExcelTestDataVO>();
		  for(int j = 3; j < lastColumn; j++){
			  ExcelTestDataVO excelTestDataVO = new ExcelTestDataVO();
			  String rowData = sheetDetailsForScript.getRow(i).getCell(j).getStringCellValue();
			  if (rowData.contains("=")){
				  String[] nameValue = rowData.split("=");
				  excelTestDataVO.setName(nameValue[0].trim());
				  excelTestDataVO.setValue(nameValue[1].trim());
				  listExcelTestDataVOs.add(excelTestDataVO);
			  }
		  }
		  iterationDataMap.put(value1, listExcelTestDataVOs);
		}
	  }
	
	} catch (Exception e) {
	  System.out.println(testName);
	  e.printStackTrace();
	} finally {
	  //CTLogger.writeToLog("ExcelTestDataReader", "readWorkbook()", "finally block called");
		fileForScript = null;
		sheetDetailsForScript = null;
	  try {
		  workbookForScript.close();
		  fileInputStreamForScript.close();
		  workbookForScript = null;
	  } catch (IOException e) {
		e.printStackTrace();
	  }
	}

	return iterationDataMap;
  }
  
public static void main(String[] args) {
	ExcelTestDataReader dataExcelxReader = new ExcelTestDataReader();
	dataExcelxReader.readTestData("HotelReservation");
}
}

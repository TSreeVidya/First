package com.accenture.aaft.excel.utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import com.accenture.aaft.vo.ExcelTestDataVO;
import com.accenture.aaft.vo.ExcelTestScriptVO;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

/**
 * Class is used to read script from excel
 *
 * @author vijay.venkatappa
 */
public class ExcelTestScript {

  /**
   * Method is used to read script from excel
   *
   * @param filePath - represents test script excel file path
   * @return HashMap contains test script values and test data
   */

  public HashMap<List<ExcelTestScriptVO>, LinkedHashMap<String, List<ExcelTestDataVO>>> readWorkbook(String filePath) {
	HashMap<List<ExcelTestScriptVO>, LinkedHashMap<String, List<ExcelTestDataVO>>> hashMap = new HashMap<List<ExcelTestScriptVO>, LinkedHashMap<String, List<ExcelTestDataVO>>>();
	List<ExcelTestScriptVO> excelTestScriptVOs = new ArrayList<ExcelTestScriptVO>();
	LinkedHashMap<String, List<ExcelTestDataVO>> iterationDataMap = new LinkedHashMap<String, List<ExcelTestDataVO>>();

	try {
	  WorkbookSettings workbookSettingsForScript = null;
	  WorkbookSettings workbookSettingsForTestData = null;
	  Workbook workbookForScript = null;
	  Workbook workbookForTestData = null;
	  String testDataFilePath = filePath.replace(".xls", "_TestData.xls");
	  testDataFilePath = testDataFilePath.replace("testscripts", "testdata");
	  File fileForScript = new File(filePath);

	  FileInputStream fileInputStreamForScript = new FileInputStream(fileForScript);
	  workbookSettingsForScript = new WorkbookSettings();
	  workbookSettingsForScript.setLocale(new Locale("en", "EN"));
	  workbookForScript = Workbook.getWorkbook(fileInputStreamForScript, workbookSettingsForScript);
	  Sheet sheetDetailsForScript = workbookForScript.getSheet(0);
	  int rows = sheetDetailsForScript.getRows();

	  for (int i = 1; i < rows; i++) {
		ExcelTestScriptVO excelTestScriptVO = new ExcelTestScriptVO();
		excelTestScriptVO.setTestStepNo(sheetDetailsForScript.getCell(0, i).getContents().trim());
		excelTestScriptVO.setTestStepDetails(sheetDetailsForScript.getCell(1, i).getContents().trim());
		excelTestScriptVO.setMainWindow(sheetDetailsForScript.getCell(2, i).getContents().trim());
		excelTestScriptVO.setScreen(sheetDetailsForScript.getCell(3, i).getContents().trim());
		excelTestScriptVO.setKeyword(sheetDetailsForScript.getCell(4, i).getContents().trim());
		excelTestScriptVO.setControlName(sheetDetailsForScript.getCell(5, i).getContents().trim());
		excelTestScriptVO.setParam1(sheetDetailsForScript.getCell(6, i).getContents().trim());
		excelTestScriptVO.setParam2(sheetDetailsForScript.getCell(7, i).getContents().trim());
		excelTestScriptVO.setParam3(sheetDetailsForScript.getCell(8, i).getContents().trim());
		excelTestScriptVO.setCondition(sheetDetailsForScript.getCell(9, i).getContents().trim());
		excelTestScriptVOs.add(excelTestScriptVO);
	  }

	  File fileForTestData = new File(testDataFilePath);
	  FileInputStream fileInputStreamForTestData = new FileInputStream(fileForTestData);
	  workbookSettingsForTestData = new WorkbookSettings();
	  workbookSettingsForTestData.setLocale(new Locale("en", "EN"));
	  workbookForTestData = Workbook.getWorkbook(fileInputStreamForTestData, workbookSettingsForTestData);
	  Sheet sheetDetailsForTestData = workbookForTestData.getSheet(0);
	  int rowCount = sheetDetailsForTestData.getRows();

	  List<String> listIteration = new ArrayList<String>();
	  for (int i = 0; i < rowCount; i++) {
		String value = sheetDetailsForTestData.getCell(1, i).getContents();

		if (value.equalsIgnoreCase("yes")) {
		  String value1 = sheetDetailsForTestData.getCell(0, i).getContents();
		  listIteration.add(value1);

		}

	  }

	  int columnCount = sheetDetailsForTestData.getColumns();
	  List<ExcelTestDataVO> listExcelTestDataVOs = new ArrayList<ExcelTestDataVO>();

	  for (String iteration : listIteration) {
		for (int i = 2; i < columnCount; i++) {
		  String rowData1 = sheetDetailsForTestData.getCell(i, Integer.parseInt(iteration) - 1).getContents();
		  ExcelTestDataVO excelTestDataVO = new ExcelTestDataVO();
		  String[] nameValue = rowData1.split("=");
		  excelTestDataVO.setName(nameValue[0].trim());
		  excelTestDataVO.setValue(nameValue[1].trim());

		  listExcelTestDataVOs.add(excelTestDataVO);
		}
		iterationDataMap.put(iteration, listExcelTestDataVOs);
	  }

	} catch (Exception e) {
	  e.printStackTrace();
	}
	hashMap.put(excelTestScriptVOs, iterationDataMap);
	return hashMap;
  }

  /**
   * Main method
   *
   * @param args - string arguments
   */
  public static void main(String[] args) {
	ExcelTestScript exceTestScriptr = new ExcelTestScript();
	exceTestScriptr.readWorkbook("C:\\AAFT_Lighter_Version_Workspace\\AAFTLighterVesrion\\testscriptrepository\\testscript\\HotelReservationTestScript.xls");

  }

}

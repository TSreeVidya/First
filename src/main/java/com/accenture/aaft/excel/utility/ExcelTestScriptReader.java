package com.accenture.aaft.excel.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.vo.ExcelTestScriptVO;

/**
 * Class is used to read script from excel
 *
 * @author vijay.venkatappa
 */
public class ExcelTestScriptReader {

	/**
	 * Method is used to read script from excel
	 *
	 * @param scriptName
	 *            - represents script name
	 * @return List of ExcelTestScriptVO instances
	 */

	public List<ExcelTestScriptVO> readWorkbook(String scriptName) {

		PropertyFileReader propertyFileReader = new PropertyFileReader();
		propertyFileReader.getValue("EXCEL_EXTENSION");
		String filePath = System.getProperty("user.dir") + System.getProperty("file.separator")+ propertyFileReader.getValue("TEST_SCRIPT_FOLDER");
		if(null!=propertyFileReader.getValue("EXCEL_EXTENSION") && !propertyFileReader.getValue("EXCEL_EXTENSION").equals("")
				&& propertyFileReader.getValue("EXCEL_EXTENSION").equalsIgnoreCase("xlsx")){
			filePath = filePath + scriptName + ".xlsx";
		}else{
			filePath = filePath + scriptName + ".xls";
		}
		System.out.println("Test Script file: " + filePath);
		
		List<ExcelTestScriptVO> excelTestScriptVOsList = new ArrayList<ExcelTestScriptVO>();

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
			// int columns=sheetDetailsForScript.getRow(0).getLastCellNum();
			for (int i = 7; i < rows; i++) {

				GetExcelCellVaueUtil cellVaueUtil = new GetExcelCellVaueUtil();

				ExcelTestScriptVO excelTestScriptVO = new ExcelTestScriptVO();
				excelTestScriptVO.setTestStepNo(cellVaueUtil.getCellValueForFirstColumn(sheetDetailsForScript.getRow(i).getCell(0)));
				excelTestScriptVO.setTestStepDetails(cellVaueUtil.getCellValue(sheetDetailsForScript.getRow(i).getCell(1)));
				excelTestScriptVO.setMainWindow(cellVaueUtil.getCellValue(sheetDetailsForScript.getRow(i).getCell(2)));
				excelTestScriptVO.setScreen(cellVaueUtil.getCellValue(sheetDetailsForScript.getRow(i).getCell(3)));
				excelTestScriptVO.setKeyword(cellVaueUtil.getCellValue(sheetDetailsForScript.getRow(i).getCell(4)));
				excelTestScriptVO.setControlName(cellVaueUtil.getCellValue(sheetDetailsForScript.getRow(i).getCell(5)));
				excelTestScriptVO.setParam1(cellVaueUtil.getCellValue(sheetDetailsForScript.getRow(i).getCell(6)));
				excelTestScriptVO.setParam2(cellVaueUtil.getCellValue(sheetDetailsForScript.getRow(i).getCell(7)));
				excelTestScriptVO.setParam3(cellVaueUtil.getCellValue(sheetDetailsForScript.getRow(i).getCell(8)));
				excelTestScriptVO.setCondition(cellVaueUtil.getCellValue(sheetDetailsForScript.getRow(i).getCell(9)));
				excelTestScriptVOsList.add(excelTestScriptVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
			workbookForScript.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			workbookForScript = null;
			fileForScript = null;

			try {
				fileInputStreamForScript.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return excelTestScriptVOsList;
	}

	/**
	 * Main method
	 *
	 * @param args
	 *            - arguments
	 */
	public static void main(String[] args) {
		ExcelTestScriptReader excelTestScript = new ExcelTestScriptReader();
		excelTestScript.readWorkbook("HotelReservation");
		// excelTestScript.readWorkbook("");
	}
}

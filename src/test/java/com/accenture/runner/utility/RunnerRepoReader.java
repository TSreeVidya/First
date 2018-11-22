package com.accenture.runner.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.accenture.aaft.excel.utility.GetExcelCellVaueUtil;
import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.runner.vo.RunnerRepoVO;
/**
 * Class is used to read the runner classes from excel
 *
 * 
 * @author vijay.venkatappa
 */
public class RunnerRepoReader {

  /**
   * Method is used to read the runner classes from excel
   *
   * 
   * @return LinkedHashMap contains serial no and RunnerRepoVO
   */
  public LinkedHashMap<String, RunnerRepoVO> readRunnerRepo(String runnerType) {
	  
	  if(runnerType.equalsIgnoreCase("selenium")){
	      runnerType="SELENIUM_REPO_PATH";
	    }
	    else if(runnerType.equalsIgnoreCase("platform")){
	      runnerType="PLATFORM_REPO_PATH";
	    }
	    else if(runnerType.equalsIgnoreCase("api")){
	      runnerType="API_REPO_PATH";
	    }
	    else if(runnerType.equalsIgnoreCase("bdd")){
	      runnerType="BDD_REPO_PATH";
	    }
		CTLogger.writeToLog("RunnerRepoReader", "readRunnerRepo()", "started");
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String filePath = System.getProperty("user.dir") + propertyFileReader.getValue(runnerType);
		if(null!=propertyFileReader.getValue("EXCEL_EXTENSION") && !propertyFileReader.getValue("EXCEL_EXTENSION").equals("")
				&& propertyFileReader.getValue("EXCEL_EXTENSION").equalsIgnoreCase("xlsx")){
			filePath = filePath+"x";
		}
		LinkedHashMap<String, RunnerRepoVO> map = new LinkedHashMap<>();

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
			for (int i = 6; i < rows; i++) {
				
				GetExcelCellVaueUtil cellVaueUtil = new GetExcelCellVaueUtil();
				RunnerRepoVO runnerRepoVO = new RunnerRepoVO();
				runnerRepoVO.setSlName(cellVaueUtil.getCellValueForFirstColumn(sheetDetailsForScript.getRow(i).getCell(0)));
				runnerRepoVO.setRunnerClassName(cellVaueUtil.getCellValue(sheetDetailsForScript.getRow(i).getCell(1)));
				runnerRepoVO.setExecute(cellVaueUtil.getCellValue(sheetDetailsForScript.getRow(i).getCell(2)));
				
				map.put(cellVaueUtil.getCellValue(sheetDetailsForScript.getRow(i).getCell(0)), runnerRepoVO);
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

		return map;
	
	  
	 
	  
	  /*
	
	if(runnerType.equalsIgnoreCase("selenium")){
      runnerType="SELENIUM_REPO_PATH";
    }
    else if(runnerType.equalsIgnoreCase("platform")){
      runnerType="PLATFORM_REPO_PATH";
    }
    else if(runnerType.equalsIgnoreCase("api")){
      runnerType="API_REPO_PATH";
    }
    else if(runnerType.equalsIgnoreCase("bdd")){
      runnerType="BDD_REPO_PATH";
    }
	CTLogger.writeToLog("RunnerRepoReader", "readRunnerRepo()", "started");
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String filePath = System.getProperty("user.dir") + propertyFileReader.getValue(runnerType);
	
	LinkedHashMap<String, RunnerRepoVO> map = new LinkedHashMap<String, RunnerRepoVO>();

	WorkbookSettings workbookSettingsForScript = null;
	Workbook workbookForScript = null;
	File fileForScript = null;
	FileInputStream fileInputStreamForScript = null;

	try {
	  fileForScript = new File(filePath);
	  fileInputStreamForScript = new FileInputStream(fileForScript);
	  workbookSettingsForScript = new WorkbookSettings();
	  workbookSettingsForScript.setLocale(new Locale("en", "EN"));
	  workbookForScript = Workbook.getWorkbook(fileInputStreamForScript, workbookSettingsForScript);
	  Sheet sheetDetailsForScript = workbookForScript.getSheet(0);
	  int rows = sheetDetailsForScript.getRows();

	  for (int i = 6; i < rows; i++) {

		RunnerRepoVO runnerRepoVO = new RunnerRepoVO();
		runnerRepoVO.setSlName(sheetDetailsForScript.getCell(0, i).getContents().trim());
		runnerRepoVO.setRunnerClassName(sheetDetailsForScript.getCell(1, i).getContents().trim());
		runnerRepoVO.setExecute(sheetDetailsForScript.getCell(2, i).getContents().trim());
		
		map.put(sheetDetailsForScript.getCell(0, i).getContents().trim(), runnerRepoVO);

	  }

	} catch (Exception e) {
	  e.printStackTrace();
	} finally {
	  workbookSettingsForScript = null;
	  workbookForScript = null;
	  fileForScript = null;

	  try {
		fileInputStreamForScript.close();

	  } catch (IOException e) {
		e.printStackTrace();
	  }
	}

	return map;

  */}

}

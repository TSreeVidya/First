package com.accenture.aaft.excel.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.vo.ObjectMapVO;

/**
 * Class is used to read the object map from excel
 *
 * 
 * @author vijay.venkatappa
 */
public class ObjectMapReader {

  /**
   * Method is used to read the object map
   *
   * @param fileName - represents object map excel
   * @return LinkedHashMap contains logical name of the UI object and path value
   */
  public LinkedHashMap<String, ObjectMapVO> readObjectMap(String fileName) {
	  
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String filePath = System.getProperty("user.dir") +System.getProperty("file.separator") + propertyFileReader.getValue("OBJECT_MAP_FILE");
		filePath = filePath + fileName;
		
		LinkedHashMap<String, ObjectMapVO> map = new LinkedHashMap<String, ObjectMapVO>();

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
				ObjectMapVO objectMapVO = new ObjectMapVO();
				objectMapVO.setControlName(cellVaueUtil.getCellValue(sheetDetailsForScript.getRow(i).getCell(0)));
				objectMapVO.setObjectPath(cellVaueUtil.getCellValue(sheetDetailsForScript.getRow(i).getCell(1)));
				objectMapVO.setSelector(cellVaueUtil.getCellValue(sheetDetailsForScript.getRow(i).getCell(2)));
				map.put(cellVaueUtil.getCellValue(sheetDetailsForScript.getRow(i).getCell(0)), objectMapVO);
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
	
	  
	 }
  
	public static void main(String[] args) {
		ObjectMapReader excelxReader = new ObjectMapReader();
		excelxReader.readObjectMap("konakart-objectmap.xlsx");
		
	}
}

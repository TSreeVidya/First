package com.accenture.aaft.excel.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.accenture.aaft.vo.ExcelTestDataVO;

/**
 * Class is used as utility to read test data from excel
 *
 * @author vijay.venkatappa
 */
public class ExcelUtil {

  /**
   * Method is used to fetch the test data details
   *
   * @param scriptName - represents excel test data values
   * @return List of ExcelTestDataVO(test data values for each script)
   */
  public List<ExcelTestDataVO> getTestData(String scriptName) {

	List<ExcelTestDataVO> testDataVOs = new ArrayList<ExcelTestDataVO>();
	ExcelTestDataReader dataReader = new ExcelTestDataReader();
	LinkedHashMap<String, List<ExcelTestDataVO>> map = dataReader.readTestData("");

	Set<String> set = map.keySet();
	Iterator<String> it = set.iterator();
	while (it.hasNext()) {
	  String key = it.next();
	  List<ExcelTestDataVO> voList = (List<ExcelTestDataVO>) map.get(key);
	  for (ExcelTestDataVO vo : voList) {
		if (vo.getValue().equals(scriptName)) {
		  testDataVOs.add(vo);

		}
	  }
	}
	return testDataVOs;
  }

  /**
   * Main method
   *
   * @param args - string arguments
   */
  public static void main(String[] args) {

	ExcelUtil excelUtil = new ExcelUtil();
	List<ExcelTestDataVO> list = excelUtil.getTestData("HotelReservationTestScript");
	System.out.println(list.size());

  }
}

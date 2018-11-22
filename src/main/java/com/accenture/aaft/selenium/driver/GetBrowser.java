package com.accenture.aaft.selenium.driver;

import java.util.HashMap;
import java.util.Map;

import com.accenture.aaft.propertyreader.PropertyFileReader;

/**
 * Class is used get selenium driver(browse) instance
 * 
 * @author vijay.venkatappa
 *
 */
public class GetBrowser {

  private static String[] browserList;
  private static Map<String, Integer> classMap = new HashMap<>();

  static {
	PropertyFileReader reader = new PropertyFileReader();
	String browserListValues = reader.getValue("THREAD_BROWSER_LIST");
	if (browserListValues != null && !browserListValues.isEmpty()) {
	  browserList = browserListValues.split(",");
	}
  }

  /**
   * Method is used get browser name
   * 
   * @param className - represents browser class name
   * @return browser class instance
   */
  synchronized public static String getBrowserName(String className) {
	int index = 0;
	if (browserList != null) {
	  index = 0;
	  if (!classMap.containsKey(className)) {
		classMap.put(className, index);
	  } else {
		index = classMap.get(className);
		index++;
		if (index >= browserList.length) {
		  index = 0;
		}
		classMap.put(className, index);
	  }

	  return browserList[index];
	}
	return browserList[index];
  }

}

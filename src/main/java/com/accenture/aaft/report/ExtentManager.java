package com.accenture.aaft.report;

import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.relevantcodes.extentreports.ExtentReports;
import java.io.File;

/**
 * Class is used to generate the Extent reports
 *
 * @author vijay.venkatappa
 *
 */
@SuppressWarnings({ "static-access" })
public class ExtentManager {

  private static ExtentReports extent;
  private static ExtentManager extentManager;

  
	static PropertyFileReader propertyFileReader = new PropertyFileReader();
	static String Precommon_path = propertyFileReader.getValue("PRE_COMMON_PATH");

  //public final static String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") +"report"+ System.getProperty("file.separator")+"SolarTurbines_Centaur_Report_" + DateUtil.now("d MMM yyyy H.mm.ss.SSS") + ".html"; 
  
  public final static String filePath = Precommon_path+"Report"+"\\"+DateUtil.now("MMMM yyyy")+"\\"+DateUtil.now("d MMM yyyy")+"\\" +"SolarTurbines_Centaur_Report_" + DateUtil.now("d MMM yyyy H.mm.ss") + ".html";   
  public final static String Screenshot = Precommon_path+"Report"+"\\"+DateUtil.now("MMMM yyyy")+"\\"+DateUtil.now("d MMM yyyy") + "\\Images\\"; 

  
  /**
   * Method is used to get report
   *
   * @return ExtentReports
   */
  public synchronized static ExtentReports getReporter() {

	if (extentManager.extent == null) {
	  extent = new ExtentReports(filePath, false);
	  extent.loadConfig(new File(System.getProperty("user.dir")+ System.getProperty("file.separator") + "extent-config.xml"));
	}

	return extent;
  }

  /**
   * Method is used to ExtentManager
   *
   * @return ExtentReports
   */
  public static ExtentReports getExtentManager() {
	if (null == extentManager) {
	  extentManager = new ExtentManager();
	  extent = extentManager.getReporter();
	}
	return extent;
  }
}

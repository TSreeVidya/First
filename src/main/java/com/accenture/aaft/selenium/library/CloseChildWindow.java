package com.accenture.aaft.selenium.library;

import java.util.Set;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to close child browser window
 *
 * @author vijay.venkatappa
 *
 */
public class CloseChildWindow {

  /**
   * Method is used to close child browser window
   *
   * @param driver - represents WebDriver
   * @param maniWindowTitleToActive - represents main window title
   * @return status
   */
  public String closeChildWindow(WebDriver driver, String maniWindowTitleToActive, ExtentTest extentTest) {

	Set<String> availableWindows = driver.getWindowHandles();
	int windowscnt = availableWindows.size();
	if (windowscnt == 1){
		driver.switchTo().window(availableWindows.iterator().next());
		System.out.println("firstWindowHandle - " + maniWindowTitleToActive + "------------->");
		//driver.switchTo().window(maniWindowTitleToActive);
		extentTest.log(LogStatus.PASS, " switch window - " + maniWindowTitleToActive);
		return "true";
		
	}
	else
	{
		String childwindow = driver.getWindowHandle();
		for (String winHandle : availableWindows){
			if(childwindow==winHandle)
			{
				driver.switchTo().window(winHandle);
				System.out.println(" child window name -- " + driver.getTitle() + " ------------> ");
				driver.close();
				extentTest.log(LogStatus.PASS, " closed window - " + driver.getTitle());
				break;
			}
		}
		if(availableWindows.iterator().hasNext()){
			driver.switchTo().window(availableWindows.iterator().next());
			extentTest.log(LogStatus.PASS, " switched window - " + driver.getTitle());
		}
		return "true";
	}
  
  }
}

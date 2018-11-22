package com.accenture.aaft.selenium.library;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.WebDriver;

import com.accenture.aaft.logger.CTLogger;

/**
 * Class is used to perform window activate action
 *
 * @author vijay.venkatappa
 *
 */
@SuppressWarnings("rawtypes")
public class WindowActivate {

  /**
   * Method is used to perform window activate action
   *
   * @param webDriver - represents WebDriver
   * @param title - represents browser title
   * @return status
   */
  public String windowActivate(WebDriver webDriver, String title)

  {
	CTLogger.writeToLog("WindowActivate", "WindowActivate() ", "title - " + title);
	String status = "false";
	try {

	  Set<String> availableWindows = webDriver.getWindowHandles();
	  CTLogger.writeToLog("WindowActivate", "WindowActivate() ", "No Of windows available" + availableWindows.size());
	  CTLogger.writeToLog("SeleniumFunctionLib", "Html_WindowActivate() ", "No Of windows available" + webDriver.getWindowHandles().size());

	  if (!availableWindows.isEmpty()) {
		if (title.equals("ActivateLastWindow")) {
		  for (Iterator i = availableWindows.iterator(); i.hasNext();) {
			String selectedWindowHandle = i.next().toString();
			webDriver.switchTo().window(selectedWindowHandle);
			System.out.println("Window activated" + webDriver.switchTo().window(selectedWindowHandle).getTitle());
			CTLogger.writeToLog("WindowActivate", "WindowActivate() ", "'" + webDriver.switchTo().window(selectedWindowHandle).getTitle() + "' =>Document is activated successfully");
			status = "true";

		  }

		  return status;

		} else {
		  for (Iterator i = availableWindows.iterator(); i.hasNext();) {
			String selectedWindowHandle = i.next().toString();
			webDriver.switchTo().window(selectedWindowHandle);
			System.out.println("Window activated" + webDriver.switchTo().window(selectedWindowHandle).getTitle());
			status = "true";

		  }
		  for (String windowId : webDriver.getWindowHandles()) {

			CTLogger.writeToLog("SeleniumFunctionLib", "Html_WindowActivate() ", "in for title - " + webDriver.switchTo().window(windowId).getTitle());
			if (webDriver.switchTo().window(windowId).getTitle().equals(title)) {
			  CTLogger.writeToLog("SeleniumFunctionLib", "Html_WindowActivate() ", "'" + title + "' =>Document is activated successfully");
			  status = "true";
			  return status;
			}
		  }
		}

	  } else {
		CTLogger.writeToLog("SeleniumFunctionLib", "Html_WindowActivate() ", "Available windows size is zero");
		status = "Available windows size is zero";
		return status;
	  }

	} catch (Exception exception) {
	  exception.printStackTrace();
	  CTLogger.writeToLog("SeleniumFunctionLib", "Html_WindowActivate() ", "Exeption thrown");
	  CTLogger.writeToLog("SeleniumFunctionLib", "Html_WindowActivate() ", "'" + title + "'object doesnot exists");
	  String err[] = exception.getMessage().split("\n");
	  status = "Exception " + err[0].replaceAll("'", "") + " Occurred";
	  return status;
	}
	return status;
  }

}

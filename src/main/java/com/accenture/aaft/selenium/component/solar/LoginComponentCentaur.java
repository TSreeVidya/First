package com.accenture.aaft.selenium.component.solar;

import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.selenium.library.Click;
import com.accenture.aaft.selenium.library.Input;
import com.accenture.aaft.vo.ExcelTestDataVO;
import com.accenture.aaft.vo.ObjectMapVO;
import com.relevantcodes.extentreports.ExtentTest;

/**
 * Class is used to login to the Solar Centaur application
 *
 * @author prashant.gurunathan
 *
 */
public class LoginComponentCentaur {

  /**
   * Method is used to login to the Solar Centaur application
   *
   * @param webDriver - represents WebDriver
   * @param extentTest - represents ExtentTest
   * @param objecthashmap - represents LinkedHashMap<String, ObjectMapVO>
   * @param voList - represents List<ExcelTestDataVO>
   * @return status
   */
  public String executeLoginComponent(WebDriver webDriver, ExtentTest extentTest, LinkedHashMap<String, ObjectMapVO> objecthashmap, List<ExcelTestDataVO> voList) {

	CTLogger.writeToLog("LoginComponentCentaur", "loginComponent()", " method called");
	ObjectMapVO objectMapVO = null;
	String status = "false";
	String cwsid = "";
	String password = "";

	for (ExcelTestDataVO vo : voList) {
	  if (vo.getName().equals("cwsid")) {
		cwsid = vo.getValue();
	  }
	  else if (vo.getName().equals("password")) {
		password = vo.getValue();
	  }
	}

	try {
	  Input input = new Input();
	  objectMapVO = objecthashmap.get("txt_Login_CWSID");
	  input.enterText(webDriver, objectMapVO.getObjectPath(), objectMapVO.getSelector(), cwsid, extentTest, objectMapVO.getControlName());

	  objectMapVO = objecthashmap.get("txt_Login_Password");
	  input.enterText(webDriver, objectMapVO.getObjectPath(), objectMapVO.getSelector(), password, extentTest, objectMapVO.getControlName());

	  Click click = new Click();
	  objectMapVO = objecthashmap.get("btn_Login_Login");
	  click.click(webDriver, objectMapVO.getObjectPath(), objectMapVO.getSelector(), extentTest, objectMapVO.getControlName());

	  status = "true";

	} catch (Exception e) {
	  e.printStackTrace();
	  status = "false";
	}

	return status;
  }
}

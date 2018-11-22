package com.accenture.aaft.selenium.library.utility;

import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.accenture.aaft.selenium.component.konakart.EditAddressComponentKonakart;
import com.accenture.aaft.selenium.component.konakart.EditAddressComponentKonakartCP;
import com.accenture.aaft.selenium.component.konakart.LoginComponentKonakart;
import com.accenture.aaft.selenium.component.solar.LoginComponentCentaur;
import com.accenture.aaft.selenium.component.avactis.BillingDetails;
import com.accenture.aaft.selenium.component.t24.AmendPersonRecord;
import com.accenture.aaft.selenium.component.t24.DeletePersonRecord;
import com.accenture.aaft.selenium.component.t24.InputProspect;
import com.accenture.aaft.vo.ExcelTestDataVO;
import com.accenture.aaft.vo.ObjectMapVO;
import com.relevantcodes.extentreports.ExtentTest;

/**
 * Class is used to execute component
 *
 * @author vijay.venkatappa
 *
 */
public class ComponentExecutor {

  /**
   * Method is used to execute component
   *
   * @param driver - represents WebDriver
   * @param componentName - represents component name
   * @param extentTest - represents ExtentTest
   * @param objecthashmap - represents LinkedHashMap<String, ObjectMapVO>
   * @param dataValue - represents List<ExcelTestDataVO>
   * @return status
   */
  public String componentExecute(WebDriver driver, String componentName, ExtentTest extentTest, LinkedHashMap<String, ObjectMapVO> objecthashmap, List<ExcelTestDataVO> dataValue) {

	if (componentName.equals("InputProspect")) {
	  InputProspect inputProspect = new InputProspect();
	  inputProspect.executeInputProspect(driver, extentTest, objecthashmap);

	} else if (componentName.equals("AmendPerson")) {
	  AmendPersonRecord amendPersonRecord = new AmendPersonRecord();
	  amendPersonRecord.executeAmendPerson(driver, extentTest, objecthashmap);
	} else if (componentName.equals("DeleteRecord")) {
	  DeletePersonRecord deletePersonRecord = new DeletePersonRecord();
	  deletePersonRecord.executeDeletePerson(driver, extentTest, objecthashmap);
	} else if (componentName.equals("BillingDetails")) {
	  BillingDetails billingDetails = new BillingDetails();
	  billingDetails.enterBillingDetails(driver, extentTest, objecthashmap, dataValue);
	} else if (componentName.equals("logincomponent")) {
	  LoginComponentKonakart loginComponentKonakart = new LoginComponentKonakart();
	  loginComponentKonakart.executeLoginComponent(driver, extentTest, objecthashmap, dataValue);
	} else if (componentName.equals("loginComponentCentaur")) {
		  LoginComponentCentaur loginComponentCentaur = new LoginComponentCentaur();
		  loginComponentCentaur.executeLoginComponent(driver, extentTest, objecthashmap, dataValue);
	} else if (componentName.equals("editAddressComponent")) {
	  EditAddressComponentKonakart editAddressComponentKonakart = new EditAddressComponentKonakart();
	  editAddressComponentKonakart.executeEditAddressComponent(driver, extentTest, objecthashmap, dataValue);

	}else if (componentName.equals("editAddressComponentCP")) {
		  EditAddressComponentKonakartCP editAddressComponentKonakartCP = new EditAddressComponentKonakartCP();
		  editAddressComponentKonakartCP.executeEditAddressComponent(driver, extentTest, objecthashmap, dataValue);

		}
	return "true";
  }

}

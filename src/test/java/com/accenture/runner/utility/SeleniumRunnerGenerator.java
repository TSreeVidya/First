package com.accenture.runner.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Set;

public class SeleniumRunnerGenerator {
  static PrintStream printStream = null;
  static FileOutputStream fileOutputStream = null;

  public void generateRunnerClass(String currentDir, String fileName, String scriptName) {

	String runnerFileName = currentDir +System.getProperty("file.separator")+"src"+System.getProperty("file.separator")+"test"+
	System.getProperty("file.separator")+"java"+System.getProperty("file.separator")+"com"+
	System.getProperty("file.separator")+"accenture"+
	System.getProperty("file.separator")+"runner"+
	System.getProperty("file.separator")+"selenium" + System.getProperty("file.separator")+fileName + ".java";
	System.out.println("in generateRunnerClass runnerFileName - "+runnerFileName);
	File runnerFile = new File(runnerFileName);

	try {

	  // if file doesnt exists, then create it
	  if (!runnerFile.exists()) {
		runnerFile.createNewFile();
	  }

	  createFile(runnerFile);

	  printStream.println("package com.accenture.runner.selenium;");
	  printStream.println("\n");
	  printStream.println("import org.junit.Test;");
	  printStream.println("import com.accenture.runner.utility.ExecuteScriptRunner;");
	  printStream.println("\n");
	  printStream.println("/**");
	  printStream.println("* Class is used for selenium test");
	  printStream.println("* ");
	  printStream.println("* @author      Lalitha Yengaldas");
	  printStream.println("* @version     1.0");
	  printStream.println("*/");

	  printStream.println("\n");

	  printStream.println("public class " + fileName + " extends ExecuteScriptRunner{");
	  printStream.println("\tpublic " + fileName + "() {");
	 /* if(fileName.equals("ExecuteScriptKkartPurchaseElectronicsRunner")||fileName.equals("ExecuteScriptKkartPurchaseGamesRunner")||fileName.equals("ExecuteScriptKkartPurchaseGiftRunner")
			  ||fileName.equals("ExecuteScriptKkartPurchaseHomeAndGardenRunner")||fileName.equals("ExecuteScriptKkartPurchaseKeyboardRunner")
			  ||fileName.equals("ExecuteScriptKkartPurchaseSoftwareRunner")||fileName.equals("ExecuteScriptKkartPurchaseDVDMoviesRunner")){
		  printStream.println("\t\tsuper(\"" + scriptName + "\", \"com.accenture.runner.selenium." + fileName + "\", \"local\");");
		    
	  
	  }
	  else{
		  printStream.println("\t\tsuper(\"" + scriptName + "\", \"com.accenture.runner.selenium." + fileName + "\", \"saucelabs\");");
		  
	  }*/
	  printStream.println("\t\tsuper(\"" + scriptName + "\", \"com.accenture.runner.selenium." + fileName + "\", \"local\");");
	  printStream.println("\t}");
	  printStream.println("\t/**");
	  printStream.println("\t * Method is used to execute script");
	  printStream.println("\t *");
	  printStream.println("\t */");

	  printStream.println("\t@Test");
	  printStream.println("\tpublic void test() {");
	  if(fileName.equals("ExecuteScriptKkartPurchaseElectronicsRunner")||fileName.equals("ExecuteScriptKkartPurchaseGamesRunner")||fileName.equals("ExecuteScriptKkartPurchaseGiftRunner")
			  ||fileName.equals("ExecuteScriptKkartPurchaseHomeAndGardenRunner")||fileName.equals("ExecuteScriptKkartPurchaseKeyboardRunner")
			  ||fileName.equals("ExecuteScriptKkartPurchaseSoftwareRunner")||fileName.equals("ExecuteScriptKkartPurchaseDVDMoviesRunner")
			  ||fileName.equals("ExecuteScriptIPhoneRunner")||fileName.equals("ExecuteScriptIMacRunner")||fileName.equals("ExecuteScriptIPadRunner")){
		  printStream.println("\t\texecuteTest();");
	  
	  }
	  else{
		  printStream.println("\t\texecuteTestWithoutBrowser();");
	  }
	  //printStream.println("\t\texecuteTest();");
	  printStream.println("\t}");
	  printStream.println("}");
	  closeFile();

	} catch (FileNotFoundException fileNotFoundException) {
	  System.out.println("Test file not found - " + fileNotFoundException.getMessage());
	  fileNotFoundException.printStackTrace();
	} catch (IOException ioException) {
	  System.out.println("IOException in Test file creation - " + ioException.getMessage());
	  ioException.printStackTrace();
	}
  }

  private static void createFile(File runnerFile) throws FileNotFoundException {
	fileOutputStream = new FileOutputStream(runnerFile);
	printStream = new PrintStream(fileOutputStream);
  }

  private static void closeFile() throws IOException {
	fileOutputStream.flush();
	printStream.flush();
	fileOutputStream.close();
	printStream.close();
  }

  public static void main(String[] args) {
	SeleniumRunnerGenerator seleniumRunnerGenerator = new SeleniumRunnerGenerator();
	String currentDir = System.getProperty("user.dir");
	HashMap<String, String> scriptRunnerMap = new HashMap<>();
	
	scriptRunnerMap.put("ExecuteScriptIMacRunner", "Purchase IMac");
	scriptRunnerMap.put("ExecuteScriptIPadRunner", "Purchase IPad");
	scriptRunnerMap.put("ExecuteScriptIPhoneRunner", "Purchase IPhone");
	scriptRunnerMap.put("ExecuteScriptKkartAdvanceSearchInGiftRunner", "konakart AdvanceSearchInGift");
	scriptRunnerMap.put("ExecuteScriptKkartDVDFormatRunner", "konakart DVDFormat");
	scriptRunnerMap.put("ExecuteScriptKkartGameCategoriesRunner", "konakart CheckGamesCategories");
	scriptRunnerMap.put("ExecuteScriptKkartGameManufacturerRunner", "konakart SelectGameManufacturer");
	scriptRunnerMap.put("ExecuteScriptKkartMovieRatingRunner", "konakart MovieRating");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseBCD410Runner", "konakart purchaseBCD410");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseBundleServerRunner", "konakart purchaseBundleServer");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseCDRomsRunner", "konakart purchaseCDRoms");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseClockInHomeAndGardenRunner", "");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseComputerRunner", "konakart purchaseClockInHomeAndGarden");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseDVDMoviesRunner", "konakart purchaseDVDMovies");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseElectronicsRunner", "konakart purchaseElectronics");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseFoodProcessorRunner", "konakart purchaseFoodProcessor");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseGalaxyTabRunner", "konakart purchaseGalaxyTab");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseGamesRunner", "konakart purchaseGames");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseGiftRunner", "konakart purchaseGifts");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseGraphicsCardRunner", "konakart purchaseGraphicsCards");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseHDDVDformatMovieRunner", "konakart purchaseHDDVDFormat");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseHomeAndGardenRunner", "konakart purchaseHomeAndGarden");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseHomeAndOfficeSoftwareRunner", "konakart purchaseOfficeAndHomeSoftware");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseHorryPorterMovieRunner", "konakart purchaseHorryPorterMovie");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseHungerGameMovieRunner", "konakart purchaseHungerGameMovie");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseIpadRunner", "konakart purchaseTablets");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseKeyboardRunner", "konakart purchaseKeyboard");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseLawnMoversRunner", "konakart purchaseLawnMovers");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseMemoryRunner", "konakart purchaseMemory");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseMiceRunner", "konakart purchaseMice");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseMonitorRunner", "konakart purchaseComputerMonitor");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseParentalGuidanceMovieRunner", "konakart purchaseParentalGuidanceMovie");
	scriptRunnerMap.put("ExecuteScriptKkartPurchasePhonesRunner", "konakart purchasephones");
	scriptRunnerMap.put("ExecuteScriptKkartPurchasePrinterRunner", "konakart purchasePrinter");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseRestrictedMovieRunner", "konakart purchaseRestrictedMovie");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseSamsungPhoneRunner", "konakart purchaseSamsungPhone");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseSelectionGamesRunner", "konakart purchaseSelectionGames");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseSimulationGameRunner", "konakart purchaseSimulationGame");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseSoftwareRunner", "konakart purchaseSoftware");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseSonyXperiarRunner", "konakart purchaseSonyXperia");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseSpeedMovieRunner", "konakart purchaseSpeedMovie");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseStrategyGamesRunner", "konakart SelectStrategyGames");
	scriptRunnerMap.put("ExecuteScriptKkartPurchaseTabOnAmazonRunner", "konakart purchaseTabletsOnAmazon");
	scriptRunnerMap.put("ExecuteScriptKkartSelectComputerPheripheralManufacturerRunner", "konakart selectComputerPherperalManufaturer");
	scriptRunnerMap.put("ExecuteScriptKkartSelectIpadManufacturerRunner", "konakart selectTabletsManufacturer");
	scriptRunnerMap.put("ExecuteScriptKkartSelectLawnMoversManufacturerRunner", "konakart SelectLawnMoversManufacturer");
	scriptRunnerMap.put("ExecuteScriptKkartSelectPhoneManufacturerRunner", "konakart selectPhoneManufacturer");
	scriptRunnerMap.put("ExecuteScriptKkartStarburstClockRunner", "konakart purchaseStarBurstClock");
	scriptRunnerMap.put("ExecuteScriptKkartTestRunner", "konakart Test");
	
	
	if (null != scriptRunnerMap) {
	  System.out.println("display scriptRunnerMap >>>");
	  Set<String> fileSet = scriptRunnerMap.keySet();
	  for (String fileName : fileSet) {
		System.out.println("FileName - "+fileName + ".... Script Name - "+scriptRunnerMap.get(fileName));
		seleniumRunnerGenerator.generateRunnerClass(currentDir, fileName, scriptRunnerMap.get(fileName));
      	System.out.println("Runner generated for script - "+scriptRunnerMap.get(fileName));
	  }
	}
  }
}

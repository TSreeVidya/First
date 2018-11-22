package com.accenture.runner.selenium;

import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentManager;
import com.accenture.runner.utility.ListAllRunner;

public class Batch_Executor {

	String userNameValue = null;

	String passwordValue = null;
	
	String Image="./st.png";

	public Batch_Executor() throws Exception {
		JLabel jUserName = new JLabel("Username");
		JTextField userName = new JTextField();
		JLabel jPassword = new JLabel("Password");
		JTextField password = new JPasswordField();
		final ImageIcon icon = new ImageIcon(Image);

		Object[] ob = { icon,jUserName, userName, jPassword, password };
		
		JDialog.setDefaultLookAndFeelDecorated(true);
		//UIManager UI=new UIManager();
		//UI.put("OptionPane.background", Color.lightGray);
		//UI.put("Panel.background", Color.lightGray);

		int result = JOptionPane.showConfirmDialog(null, ob, "Enter your Credentials!",
				JOptionPane.OK_CANCEL_OPTION);

		
		if (result == JOptionPane.OK_OPTION) {

			userNameValue = userName.getText();
			passwordValue = password.getText();

			if ("".equalsIgnoreCase(userNameValue) || "".equalsIgnoreCase(passwordValue)) {

				final JPanel panel = new JPanel();
				JOptionPane.showMessageDialog(panel, "Check Username Password will not be empty!", "Error", JOptionPane.ERROR_MESSAGE, icon);
				result = JOptionPane.showConfirmDialog(null, ob, "Welcome to Solar Turbines Salesforce!",
						JOptionPane.OK_CANCEL_OPTION);
			
			} else {

				PropertyFileReader propertyFileReader = new PropertyFileReader();
				propertyFileReader.setValue("Salesforce_username", userNameValue);

				final String secretKey = propertyFileReader.getValue("SECRETKEY");
				String originalString = passwordValue;
				String encryptedString = AES_Encryption.encrypt(originalString, secretKey);

				propertyFileReader.setValue("Salesforce_password", encryptedString);
				System.out.println("User Successfully enter the Credentials!");
				
				SELENIUM_Executor aaftExecutor = new SELENIUM_Executor();
				System.out.println("Curr dir - " + System.getProperty("user.dir"));
				ListAllRunner listAllRunner = new ListAllRunner();
				String parallelExecution = propertyFileReader.getValue("PARALLEL_EXECUTION");
				
				if(null != parallelExecution && parallelExecution.equalsIgnoreCase("Yes")){
					aaftExecutor.distributeTests(listAllRunner.getAllRunnerClasses("selenium"));
				}else{
					for (Class clazz : listAllRunner.getAllRunnerClasses("selenium")) {
						Thread.sleep(10000); 
						JUnitCore junit = new JUnitCore();
						Result result1 = junit.run(clazz);
						for (Failure failure : result1.getFailures()) {
							System.out.println(failure.toString());
						}
					}
				}
			   	
				try {
					String browserName = propertyFileReader.getValue("BROWSER_NAME");
					if(browserName.equalsIgnoreCase("chrome")){
						Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");						
						Thread.sleep(10000);					
					}
					else if(browserName.equalsIgnoreCase("ie") || browserName.equalsIgnoreCase("internet")){
						Runtime.getRuntime().exec("taskkill /F /IM iedriverserver.exe");
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}

				String path = ExtentManager.filePath;
				propertyFileReader.setValue("SELENIUM_REPORT_FILENAME", path);
				//propertyFileReader.setValue("Salesforce_password", "t");
			  }
				
			}
		}


	public static void main(String[] args) throws Exception {

		new Batch_Executor();
			
	}
}

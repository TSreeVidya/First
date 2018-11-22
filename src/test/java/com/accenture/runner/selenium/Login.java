package com.accenture.runner.selenium;

import java.awt.Color;


import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.accenture.aaft.propertyreader.PropertyFileReader;

public class Login {

	String userNameValue = null;

	String passwordValue = null;
	
	String Image="./st.png";

	public Login() {
		JLabel jUserName = new JLabel("Username");
		JTextField userName = new JTextField();
		JLabel jPassword = new JLabel("Password");
		JTextField password = new JPasswordField();
		final ImageIcon icon = new ImageIcon(Image);
		


		Object[] ob = { icon,jUserName, userName, jPassword, password };
		
		JDialog.setDefaultLookAndFeelDecorated(true);
		 UIManager UI=new UIManager();
		 UI.put("OptionPane.background", Color.lightGray);
		 UI.put("Panel.background", Color.lightGray);

		int result = JOptionPane.showConfirmDialog(null, ob, "Enter Your Credentials!!!",
				JOptionPane.OK_CANCEL_OPTION);

		
		if (result == JOptionPane.OK_OPTION) {

			userNameValue = userName.getText();
			passwordValue = password.getText();

			if ("".equalsIgnoreCase(userNameValue) || "".equalsIgnoreCase(passwordValue)) {

				final JPanel panel = new JPanel();
				JOptionPane.showMessageDialog(panel, "Check Username Password will not be empty!", "Error", JOptionPane.ERROR_MESSAGE, icon);
				result = JOptionPane.showConfirmDialog(null, ob, "Enter Your Credentials!!!",
						JOptionPane.OK_CANCEL_OPTION);
			
			} else {

				PropertyFileReader propertyFileReader = new PropertyFileReader();
				propertyFileReader.setValue("SOLARTURBINES_USERNAME", userNameValue);

				final String secretKey = propertyFileReader.getValue("SOLARTURBINES_SECRETKEY");
				String originalString = passwordValue;
				String encryptedString = AES_Encryption.encrypt(originalString, secretKey);

				propertyFileReader.setValue("SOLARTURBINES_PASSWORD", encryptedString);
				
				System.out.println("User Successfully enter the Credentials!");

			}

		}

	}

	public static void main(String[] args) throws Exception {

		new Login();
			
	}
}

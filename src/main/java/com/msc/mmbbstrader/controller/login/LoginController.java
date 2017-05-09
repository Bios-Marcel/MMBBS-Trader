package com.msc.mmbbstrader.controller.login;

import com.msc.mmbbstrader.services.Service;
import com.msc.mmbbstrader.services.TraderService;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController
{
	private static boolean loggedIn = false;

	private static String username = "";

	// Input Fields
	@FXML
	private TextField usernameTextField;

	@FXML
	private PasswordField passwordTextField;

	@FXML
	private void onLoginOrRegister()
	{
		username = usernameTextField.getText();
		final String password = passwordTextField.getText();

		final LoginResult result = Service.lookup(TraderService.class).loginOrRegisterUser(username, password);

		switch (result)
		{
			case LOGGED_IN:
			case REGISTERED:
				loggedIn = true;
				break;
			case WRONG:
				loggedIn = false;
				break;
		}
	}

	@FXML
	public void closeApplication()
	{
		System.exit(0);
	}

	public static boolean isLoggedIn()
	{
		return loggedIn;
	}

	public static String getName()
	{
		return username;
	}
}

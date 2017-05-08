package com.msc.mmbbstrader.controller.login;

import java.rmi.UnexpectedException;

import com.msc.mmbbstrader.database.DatabaseProxy;

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
	private void onLoginOrRegister() throws UnexpectedException
	{
		username = usernameTextField.getText();
		final String password = passwordTextField.getText();

		final LoginResult result = DatabaseProxy.getInstance().loginOrRegisterUser(username, password);

		switch (result)
		{
			case LOGGED_IN:
			case REGISTERED:
				loggedIn = true;
				break;
			case WRONG:
				// TODO(MSC) Alert, telling the user that his password was wrong and he should
				// retry.
				loggedIn = false;
				break;
			default:
				// Shouldn't happen
				throw new UnexpectedException("Login returned invalid Result.");

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

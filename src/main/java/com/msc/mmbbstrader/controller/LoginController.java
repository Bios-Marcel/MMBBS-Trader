package com.msc.mmbbstrader.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

	private static String name = "";

	private static String password = "";

	// Input Fields
	@FXML
	private TextField usernameTextField;

	@FXML
	private PasswordField passwordTextField;

	@FXML
	private void onLoginOrRegister() {
		name = usernameTextField.getText();
		password = passwordTextField.getText();
	}

	@FXML
	public void closeApplication() {
		System.exit(0);
	}

	public static String getName() {
		return name;
	}

	public static String getPassword() {
		return password;
	}
}

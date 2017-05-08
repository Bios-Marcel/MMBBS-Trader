package com.msc.mmbbstrader.main;

import com.msc.mmbbstrader.controller.MainController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application
{

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/msc/mmbbstrader/views/Main.fxml"));
		loader.setController(new MainController());

		Parent root = loader.load();

		Scene scene = new Scene(root);
		stage.setTitle("MMBBS-Trader");
		stage.setScene(scene);
		stage.show();
	}

}

package com.msc.mmbbstrader.main;

import com.msc.mmbbstrader.controller.MainController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Client extends Application
{
	public static void main(final String[] args)
	{
		launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception
	{
		final FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/msc/mmbbstrader/views/Main.fxml"));
		loader.setController(new MainController(stage));

		final Parent root = loader.load();

		final Scene scene = new Scene(root);
		stage.getIcons().add(getIcon());
		stage.setTitle("MMBBS-Trader");
		stage.setScene(scene);
		stage.show();
	}

	public static Image getIcon()
	{
		return new Image(Client.class.getResourceAsStream("/com/msc/mmbbstrader/icons/icon.png"));
	}

}

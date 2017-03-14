package com.msc.mmbbstrader.main;

import com.msc.mmbbstrader.controller.MainController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("MMBBS-Trader");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/msc/mmbbstrader/views/Main.fxml"));
		MainController controller = new MainController();
		loader.setController(controller);

		Parent root = loader.load();

		controller.init();

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}

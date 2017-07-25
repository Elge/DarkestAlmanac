package de.sgoral.darkestalmanac.control;

import de.sgoral.darkestalmanac.ui.GuiController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by sebastianprivat on 11.07.17.
 */
public class DarkestAlmanac extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new GuiController(primaryStage, new File(System.getProperty("user.home")),
                "darkestalmanac.json",
                "json", "JSON file").start();
    }
}

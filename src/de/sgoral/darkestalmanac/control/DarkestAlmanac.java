package de.sgoral.darkestalmanac.control;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by sebastianprivat on 11.07.17.
 */
public class DarkestAlmanac extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new GuiController(primaryStage).start();
    }
}

package de.sgoral.darkestalmanac.ui;

import de.sgoral.darkestalmanac.data.dataobjects.DataStorage;
import de.sgoral.darkestalmanac.data.dataobjects.Location;
import de.sgoral.darkestalmanac.events.FileSelectedEvent;
import de.sgoral.darkestalmanac.events.LocationSelectedEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Created by sebastianprivat on 11.07.17.
 */
public class UiFactory {

    public void openDataLocationEditor(Stage stage, File currentStorageFile, EventHandler<? super FileSelectedEvent> eventHandler) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DataLocationEditor.fxml"));
        GridPane editor = null;
        try {
            editor = loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error loading DataLocationEditor", e);
        }
        editor.addEventHandler(FileSelectedEvent.EVENT_TYPE, eventHandler);

        DataLocationEditorController controller = loader.getController();
        controller.setOriginalStorageFile(currentStorageFile);

        Scene scene = new Scene(editor);

        stage.setTitle("Data Location");
        stage.setScene(scene);
        stage.show();
    }

    public void openMainWindow(Stage stage, DataStorage dataStorage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LocationList.fxml"));
        Parent mainWindow;
        try {
            mainWindow = loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error loading MainWindow", e);
        }
        mainWindow.addEventHandler(LocationSelectedEvent.EVENT_TYPE, event -> {
            switchToCurioList(stage, dataStorage, event.getLocation());
        });

        LocationListController controller = loader.getController();
        controller.setDataStorage(dataStorage);

        Scene scene = new Scene(mainWindow);

        stage.setTitle("Main Window");
        stage.setScene(scene);
        stage.show();
    }

    public void switchToCurioList(Stage stage, DataStorage dataStorage, Location location) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CurioList.fxml"));
        Parent curioList;
        try {
            curioList = loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error loading CurioList", e);
        }

        CurioListController controller = loader.getController();
        controller.setDataStorage(dataStorage);
        controller.setLocation(location);

        Scene scene = new Scene(curioList);

        stage.setTitle("Curios");
        stage.setScene(scene);
        stage.show();
    }

}

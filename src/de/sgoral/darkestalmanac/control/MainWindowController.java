package de.sgoral.darkestalmanac.control;

import de.sgoral.darkestalmanac.data.dataobjects.DataStorage;
import de.sgoral.darkestalmanac.data.dataobjects.Location;
import de.sgoral.darkestalmanac.events.LocationSelectedEvent;
import de.sgoral.darkestalmanac.ui.CurioListController;
import de.sgoral.darkestalmanac.ui.LocationListController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindowController {

    final private Stage primaryStage;

    public MainWindowController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void openMainWindow(DataStorage dataStorage) {
        GuiLoaderUtil.GuiLoader<LocationListController> loader = GuiLoaderUtil.getInstance().getLocationList();
        Parent locationList = loader.load();
        LocationListController controller = loader.getController();

        locationList.addEventHandler(LocationSelectedEvent.EVENT_TYPE, event -> {
            switchToCurioList(dataStorage, event.getLocation());
        });

        controller.setDataStorage(dataStorage);

        Scene scene = new Scene(locationList);
        this.primaryStage.setTitle("Main Window");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public void switchToCurioList(DataStorage dataStorage, Location location) {
        GuiLoaderUtil.GuiLoader<CurioListController> loader = GuiLoaderUtil.getInstance().getCurioList();
        Parent curioList = loader.load();
        CurioListController controller = loader.getController();

        controller.setDataStorage(dataStorage);
        controller.setLocation(location);

        Scene scene = new Scene(curioList);
        this.primaryStage.setTitle("Curios");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

}

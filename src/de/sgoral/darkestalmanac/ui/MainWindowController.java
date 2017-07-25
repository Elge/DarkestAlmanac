package de.sgoral.darkestalmanac.ui;

import de.sgoral.darkestalmanac.data.dataobjects.Curio;
import de.sgoral.darkestalmanac.data.dataobjects.DataStorage;
import de.sgoral.darkestalmanac.data.dataobjects.Experiment;
import de.sgoral.darkestalmanac.data.dataobjects.Location;
import de.sgoral.darkestalmanac.events.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;

public class MainWindowController {

    @FXML
    private MenuBar menuBar;
    @FXML
    private Pane contentPane;

    private String title;

    public void switchToLocationsList(DataStorage dataStorage) {
        GuiLoaderUtil.GuiLoader<LocationListController> loader = GuiLoaderUtil.getInstance().getLocationList();
        Parent locationList = loader.load();
        LocationListController controller = loader.getController();

        locationList.addEventHandler(LocationSelectedEvent.EVENT_TYPE, event -> switchToCuriosList(dataStorage, event.getLocation()));

        controller.setDataStorage(dataStorage);

        contentPane.getChildren().setAll(locationList);
        changeTitle("Locations");
    }

    public void switchToCuriosList(DataStorage dataStorage, Location location) {
        GuiLoaderUtil.GuiLoader<CurioListController> loader = GuiLoaderUtil.getInstance().getCurioList();
        Parent curioList = loader.load();
        CurioListController controller = loader.getController();

        curioList.addEventHandler(CurioSelectedEvent.EVENT_TYPE, event -> switchToCurioView(dataStorage, event.getCurio()));

        controller.setDataStorage(dataStorage);
        controller.setLocation(location);

        contentPane.getChildren().setAll(curioList);
        changeTitle("Curios in " + location.getName());
    }

    public void switchToCurioView(DataStorage dataStorage, Curio curio) {
        GuiLoaderUtil.GuiLoader<CurioController> loader = GuiLoaderUtil.getInstance().getCurio();
        Parent curioView = loader.load();
        CurioController controller = loader.getController();

        curioView.addEventHandler(ExperimentSelectedEvent.EVENT_TYPE, event -> switchToExperimentView(dataStorage, event.getExperiment()));

        controller.setCurio(curio);

        contentPane.getChildren().setAll(curioView);
        changeTitle(curio.getName());
    }

    private void switchToExperimentView(DataStorage dataStorage, Experiment experiment) {
        // TODO method stub
    }

    private void changeTitle(String title) {
        this.title = title;
        contentPane.fireEvent(new TitleChangeRequestedEvent(title));
    }

    public void addMenuListener(EventType<MenuItemActionEvent> eventType, EventHandler<MenuItemActionEvent> eventHandler) {
        menuBar.addEventHandler(eventType, eventHandler);
    }

    public void onMenuNew(ActionEvent actionEvent) {
        menuBar.fireEvent(new MenuItemActionEvent(MenuItemActionEvent.EVENT_TYPE_NEW));
    }

    public void onMenuOpen(ActionEvent actionEvent) {
        menuBar.fireEvent(new MenuItemActionEvent(MenuItemActionEvent.EVENT_TYPE_OPEN));
    }

    public void onMenuSave(ActionEvent actionEvent) {
        menuBar.fireEvent(new MenuItemActionEvent(MenuItemActionEvent.EVENT_TYPE_SAVE));
    }

    public void onMenuSaveAs(ActionEvent actionEvent) {
        menuBar.fireEvent(new MenuItemActionEvent(MenuItemActionEvent.EVENT_TYPE_SAVEAS));
    }

    public void onMenuExit(ActionEvent actionEvent) {
        menuBar.fireEvent(new MenuItemActionEvent(MenuItemActionEvent.EVENT_TYPE_EXIT));
    }
}

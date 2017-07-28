package de.sgoral.darkestalmanac.ui.controllers;

import de.sgoral.darkestalmanac.data.dataobjects.Curio;
import de.sgoral.darkestalmanac.data.dataobjects.DataStorage;
import de.sgoral.darkestalmanac.data.dataobjects.Experiment;
import de.sgoral.darkestalmanac.data.dataobjects.Location;
import de.sgoral.darkestalmanac.events.*;
import de.sgoral.darkestalmanac.ui.GuiLoaderUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private MenuBar menuBar;
    @FXML
    private Pane contentPane;

    private String title;
    private DataStorage dataStorage;

    private Parent locationList;
    private Parent locationEditor;
    private Parent curioList;
    private Parent curioView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialiseLocationsList();
        initialiseLocationEditor();
        initialiseCuriosList();
        initialiseCurioView();
        initialiseExperimentView();
    }

    public void setDataStorage(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    private void initialiseLocationsList() {
        GuiLoaderUtil.GuiLoader<LocationListController> loader = GuiLoaderUtil.getInstance().getLocationList();
        locationList = loader.load();
        LocationListController controller = loader.getController();

        locationList.addEventHandler(LocationSelectedEvent.EVENT_TYPE, event -> openCuriosList(event.getLocation()));
        locationList.addEventHandler(EditLocationEvent.EVENT_TYPE_ROOT, event -> {
            if (event.getEventType() == EditLocationEvent.EVENT_TYPE_NEW || event.getEventType() == EditLocationEvent.EVENT_TYPE_EDIT) {
                openLocationEditor(event.getLocation());
            } else if (event.getEventType() == EditLocationEvent.EVENT_TYPE_DELETE) {
                dataStorage.getLocations().remove(event.getLocation());
                controller.refreshTable();
            }
        });

        controller.setDataStorage(dataStorage);
    }

    private void initialiseLocationEditor() {
        GuiLoaderUtil.GuiLoader<LocationEditorController> loader = GuiLoaderUtil.getInstance().getLocationEditor();
        locationEditor = loader.load();
        LocationEditorController controller = loader.getController();

        locationEditor.addEventHandler(EditLocationEvent.EVENT_TYPE_ROOT, event -> {
            if (event.getEventType() == EditLocationEvent.EVENT_TYPE_SAVE || event.getEventType() == EditLocationEvent.EVENT_TYPE_CANCEL) {
                if (event.getEventType() == EditLocationEvent.EVENT_TYPE_SAVE) {
                    Location newValue = event.getLocation();
                    if (newValue.getId() == null) {
                        newValue.setId(dataStorage.generateId());
                        dataStorage.addLocation(newValue);
                    } else {
                        for (Location location : dataStorage.getLocations()) {
                            if (location.getId() == newValue.getId()) {
                                location.setName(newValue.getName());
                                break;
                            }
                        }
                    }
                }

                switchToLocationsList();
            }
        });
    }

    private void initialiseCuriosList() {
        GuiLoaderUtil.GuiLoader<CurioListController> loader = GuiLoaderUtil.getInstance().getCurioList();
        curioList = loader.load();
        CurioListController controller = loader.getController();

        curioList.addEventHandler(CurioSelectedEvent.EVENT_TYPE, event -> openCurioView(event.getCurio()));

        controller.setDataStorage(dataStorage);
    }

    private void initialiseCurioView() {
        GuiLoaderUtil.GuiLoader<CurioController> loader = GuiLoaderUtil.getInstance().getCurio();
        curioView = loader.load();
        CurioController controller = loader.getController();

        curioView.addEventHandler(ExperimentSelectedEvent.EVENT_TYPE, event -> switchToExperimentView(event.getExperiment()));
    }

    private void initialiseExperimentView() {
        // TODO method stub
    }

    public void switchToLocationsList() {
        contentPane.getChildren().setAll(locationList);
        changeTitle("Locations");
    }

    public void openLocationEditor(Location location) {
        contentPane.getChildren().setAll(locationEditor);
        if (location == null) {
            changeTitle("New location");
        } else {
            changeTitle("Edit " + location.getName());
        }
    }


    public void openCuriosList(Location location) {
        contentPane.getChildren().setAll(curioList);
        changeTitle("Curios in " + location.getName());
    }


    public void openCurioView(Curio curio) {
        contentPane.getChildren().setAll(curioView);
        changeTitle(curio.getName());
    }

    private void switchToExperimentView(Experiment experiment) {
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

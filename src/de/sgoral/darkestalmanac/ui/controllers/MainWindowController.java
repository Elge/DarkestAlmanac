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
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class MainWindowController implements Initializable {

    @FXML
    private MenuBar menuBar;
    @FXML
    private Pane contentPane;
    @FXML
    private Button backButton;

    private String title;
    private DataStorage dataStorage;
    private Stack<Parent> uiStack;
    private Stack<String> titleStack;

    private Parent locationList;
    private Parent locationEditor;
    private Parent curioList;
    private Parent curioEditor;
    private Parent curioView;
    private LocationListController locationListController;
    private LocationEditorController locationEditorController;
    private CurioListController curioListController;
    private CurioController curioViewController;
    private CurioEditorController curioEditorController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        uiStack = new Stack<>();
        titleStack = new Stack<>();

        backButton.setOnAction(event -> backOneLevel());

        initialiseLocationsList();
        initialiseLocationEditor();
        initialiseCuriosList();
        initialiseCurioView();
        initialiseCurioEditor();
        initialiseExperimentView();
    }

    public void setDataStorage(DataStorage dataStorage) {
        this.dataStorage = dataStorage;

        locationListController.setDataStorage(dataStorage);
    }

    private void initialiseLocationsList() {
        GuiLoaderUtil.GuiLoader<LocationListController> loader = GuiLoaderUtil.getInstance().getLocationList();
        locationList = loader.load();
        locationListController = loader.getController();

        locationList.addEventHandler(LocationSelectedEvent.EVENT_TYPE, event -> openCuriosList(event.getLocation()));
        locationList.addEventHandler(EditLocationEvent.EVENT_TYPE_ROOT, event -> {
            if (event.getEventType() == EditLocationEvent.EVENT_TYPE_NEW || event.getEventType() == EditLocationEvent.EVENT_TYPE_EDIT) {
                openLocationEditor(event.getLocation());
            } else if (event.getEventType() == EditLocationEvent.EVENT_TYPE_DELETE) {
                dataStorage.getLocations().remove(event.getLocation());
                locationListController.forceUiUpdate();
            }
        });
    }

    private void initialiseLocationEditor() {
        GuiLoaderUtil.GuiLoader<LocationEditorController> loader = GuiLoaderUtil.getInstance().getLocationEditor();
        locationEditor = loader.load();
        locationEditorController = loader.getController();

        locationEditor.addEventHandler(EditLocationEvent.EVENT_TYPE_ROOT, event -> {
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

            openLocationsList(true);
        });
    }

    private void initialiseCuriosList() {
        GuiLoaderUtil.GuiLoader<CurioListController> loader = GuiLoaderUtil.getInstance().getCurioList();
        curioList = loader.load();
        curioListController = loader.getController();

        curioList.addEventHandler(CurioSelectedEvent.EVENT_TYPE, event -> openCurioView(event.getCurio()));
        curioList.addEventHandler(CurioEditingEvent.EVENT_TYPE_ROOT, event -> {
            if (event.getEventType() == CurioEditingEvent.EVENT_TYPE_NEW || event.getEventType() == CurioEditingEvent.EVENT_TYPE_EDIT) {
                openCurioEditor(event.getLocation(), event.getCurio());
            } else if (event.getEventType() == CurioEditingEvent.EVENT_TYPE_DELETE) {
                dataStorage.getCurios().remove(event.getCurio());
                locationListController.forceUiUpdate();
            }
        });
    }

    private void initialiseCurioEditor() {
        GuiLoaderUtil.GuiLoader<CurioEditorController> loader = GuiLoaderUtil.getInstance().getCurioEditor();
        curioEditor = loader.load();
        curioEditorController = loader.getController();

        curioEditor.addEventHandler(CurioEditingEvent.EVENT_TYPE_ROOT, event -> {
            if (event.getEventType() == CurioEditingEvent.EVENT_TYPE_SAVE) {
                Curio newValue = event.getCurio();

                if (newValue.getId() == null) {
                    newValue.setId(dataStorage.generateId());
                    dataStorage.getCurios().add(newValue);
                    for (Location location : newValue.getLocations()) {
                        location.getCurios().add(newValue);
                    }
                } else {
                    for (Curio curio : dataStorage.getCurios()) {
                        if (curio.getId() == newValue.getId()) {
                            curio.setName(newValue.getName());
                            curio.setLocations(newValue.getLocations());

                            for (Location location : dataStorage.getLocations()) {
                                location.getCurios().remove(curio);
                            }
                            for (Location location : curio.getLocations()) {
                                location.addCurio(curio);
                            }
                            break;
                        }
                    }
                }
            }

            openCuriosList(event.getLocation(), true);
        });
    }

    private void initialiseCurioView() {
        GuiLoaderUtil.GuiLoader<CurioController> loader = GuiLoaderUtil.getInstance().getCurio();
        curioView = loader.load();
        curioViewController = loader.getController();

        curioView.addEventHandler(ExperimentSelectedEvent.EVENT_TYPE, event -> openExperimentView(event.getExperiment()));
    }

    private void initialiseExperimentView() {
        // TODO method stub
    }

    private void switchUiElement(Parent element, String title) {
        uiStack.push(element);
        titleStack.push(title);

        contentPane.getChildren().setAll(element);
        changeTitle(title);
    }

    public void openLocationsList() {
        openLocationsList(false);
    }

    private void openLocationsList(boolean goBack) {
        if (goBack) {
            locationListController.forceUiUpdate();
            backOneLevel();
        } else {
            switchUiElement(locationList, "Locations");
        }
    }

    public void openLocationEditor(Location location) {
        locationEditorController.setLocation(location);
        switchUiElement(locationEditor, location == null ? "New location" : "Edit " + location.getName());
    }

    public void openCuriosList(Location location) {
        openCuriosList(location, false);
    }

    private void openCuriosList(Location location, boolean goBack) {
        if (goBack) {
            curioListController.forceUiUpdate();
            backOneLevel();
        } else {
            curioListController.setData(dataStorage, location);
            switchUiElement(curioList, "Curios in " + location.getName());
        }
    }

    public void openCurioEditor(Location currentLocation, Curio curio) {
        curioEditorController.setData(dataStorage.getLocations(), currentLocation, curio);
        switchUiElement(curioEditor, curio == null ? "New curio" : "Edit " + curio.getName());
    }

    public void openCurioView(Curio curio) {
        openCurioView(curio, false);
    }

    private void openCurioView(Curio curio, boolean goBack) {
        if (goBack) {
            curioViewController.forceUiUpdate();
            backOneLevel();
        } else {
            curioViewController.setData(dataStorage, curio);
            switchUiElement(curioView, curio.getName());
        }
    }

    private void openExperimentView(Experiment experiment) {
        // TODO method stub
    }

    private void backOneLevel() {
        if (uiStack.size() > 1) {
            uiStack.pop();
            contentPane.getChildren().setAll(uiStack.peek());

            titleStack.pop();
            changeTitle(titleStack.peek());
        }
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

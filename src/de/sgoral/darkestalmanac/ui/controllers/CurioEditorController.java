package de.sgoral.darkestalmanac.ui.controllers;

import de.sgoral.darkestalmanac.data.dataobjects.Curio;
import de.sgoral.darkestalmanac.data.dataobjects.Location;
import de.sgoral.darkestalmanac.events.CurioEditingEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CurioEditorController implements Initializable {

    public VBox locationsList;
    public TextField nameField;
    public Button cancel;
    public Button save;

    private List<Location> locations;
    private Location currentLocation;
    private Curio curio;

    @Override
    public void initialize(URL loc, ResourceBundle resources) {
        cancel.setOnAction(event -> cancel.fireEvent(new CurioEditingEvent(CurioEditingEvent.EVENT_TYPE_CANCEL, this.currentLocation, curio)));
        save.setOnAction(event -> {
            curio.setName(nameField.getText());
            // Set locations
            curio.getLocations().clear();
            for (Node node : locationsList.getChildren()) {
                CheckBox checkbox = (CheckBox) node;
                if (checkbox.isSelected()) {
                    String locationName = checkbox.getText();
                    for (Location location : locations) {
                        if (location.getName().equals(locationName)) {
                            curio.addLocation(location);
                            break;
                        }
                    }
                }
            }

            save.fireEvent(new CurioEditingEvent(CurioEditingEvent.EVENT_TYPE_SAVE, this.currentLocation, curio));
        });
    }

    public void setData(List<Location> locations, Location currentLocation, Curio curio) {
        this.locations = locations;
        this.currentLocation = currentLocation;
        this.curio = curio;
        refreshView();
    }

    public void refreshView() {
        locationsList.getChildren().clear();

        for (Location location : locations) {
            CheckBox checkBox = new CheckBox(location.getName());
            if (curio.getLocations().contains(location) || currentLocation == location) {
                checkBox.setSelected(true);
            }
            locationsList.getChildren().add(checkBox);
        }

        nameField.setText(curio.getName());
    }
}

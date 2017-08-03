package de.sgoral.darkestalmanac.ui.controllers;

import de.sgoral.darkestalmanac.data.dataobjects.Location;
import de.sgoral.darkestalmanac.events.LocationEditEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LocationEditorController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private Button cancel;
    @FXML
    private Button save;
    private Location location = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cancel.setCancelButton(true);
        save.setDefaultButton(true);

        cancel.setOnAction(event -> {
            cancel.fireEvent(new LocationEditEvent(LocationEditEvent.EVENT_TYPE_CANCEL, null));
        });

        save.setOnAction(event -> {
            this.location.setName(nameField.getText());
            save.fireEvent(new LocationEditEvent(LocationEditEvent.EVENT_TYPE_SAVE, this.location));
        });
    }

    public void setLocation(Location location) {
        this.location = location;
        nameField.setText(location.getName());
    }
}

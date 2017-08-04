package de.sgoral.darkestalmanac.ui.controllers;

import de.sgoral.darkestalmanac.data.dataobjects.DataStorage;
import de.sgoral.darkestalmanac.data.dataobjects.Location;
import de.sgoral.darkestalmanac.events.LocationEditEvent;
import de.sgoral.darkestalmanac.events.LocationSelectedEvent;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.util.ResourceBundle;

public class LocationListController implements Initializable {

    @FXML
    TableView<Location> dataTable;
    @FXML
    TableColumn<Location, String> zoneColumn;
    @FXML
    TableColumn<Location, Integer> curioCountColumn;

    private DataStorage dataStorage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataTable.setRowFactory(param -> {
            TableRow<Location> row = new TableRow<>();

            ContextMenu emptyCM = new ContextMenu();
            ContextMenu fullCM = new ContextMenu();

            {
                MenuItem add = new MenuItem("Add location");
                add.setOnAction(event -> row.fireEvent(new LocationEditEvent(LocationEditEvent.EVENT_TYPE_NEW, new Location())));
                emptyCM.getItems().addAll(add);
            }

            {
                MenuItem add = new MenuItem("Add location");
                add.setOnAction(event -> row.fireEvent(new LocationEditEvent(LocationEditEvent.EVENT_TYPE_NEW, new Location())));

                MenuItem edit = new MenuItem("Edit location");
                edit.setOnAction(event -> row.fireEvent(new LocationEditEvent(LocationEditEvent.EVENT_TYPE_EDIT, row.getItem())));

                MenuItem delete = new MenuItem("Delete location");
                delete.setOnAction(event -> row.fireEvent(new LocationEditEvent(LocationEditEvent.EVENT_TYPE_DELETE, row.getItem())));

                fullCM.getItems().addAll(add, edit, delete);
            }

            row.contextMenuProperty().bind(
                    Bindings.when(
                            Bindings.isNull(row.itemProperty())
                    ).then(emptyCM).otherwise(fullCM)
            );

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    row.fireEvent(new LocationSelectedEvent(row.getItem()));
                }
            });

            return row;
        });

        MenuItem add = new MenuItem("Add location");
        add.setOnAction(event -> dataTable.fireEvent(new LocationEditEvent(LocationEditEvent.EVENT_TYPE_NEW, new Location())));
        dataTable.setContextMenu(new ContextMenu(add));

        zoneColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        curioCountColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getCurios().size()));
    }

    public DataStorage getDataStorage() {
        return dataStorage;
    }

    public void setDataStorage(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        forceUiUpdate();
    }

    public void forceUiUpdate() {
        dataTable.getItems().setAll(dataStorage.getLocations());
    }

}

package de.sgoral.darkestalmanac.ui;

import de.sgoral.darkestalmanac.data.dataobjects.DataStorage;
import de.sgoral.darkestalmanac.data.dataobjects.Location;
import de.sgoral.darkestalmanac.events.LocationSelectedEvent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
        dataTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> dataTable.getParent().fireEvent(new LocationSelectedEvent(newValue)));

        zoneColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        curioCountColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getCurios().size()));
    }

    public DataStorage getDataStorage() {
        return dataStorage;
    }

    public void setDataStorage(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        refreshTable();
    }

    private void refreshTable() {
        dataTable.getItems().setAll(dataStorage.getLocations());
    }

}

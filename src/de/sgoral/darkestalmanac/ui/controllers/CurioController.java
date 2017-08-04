package de.sgoral.darkestalmanac.ui.controllers;

import de.sgoral.darkestalmanac.data.dataobjects.*;
import de.sgoral.darkestalmanac.events.ExperimentSelectedEvent;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.util.ResourceBundle;

public class CurioController implements Initializable {

    @FXML
    private Label locations;
    @FXML
    private TableView<Consumable> experimentsTable;
    @FXML
    private TableColumn<Consumable, String> consumableColumn;
    @FXML
    private TableColumn<Consumable, String> effectsColumn;

    private DataStorage dataStorage;
    private Curio curio;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.consumableColumn.setCellValueFactory(param -> {
            Consumable consumable = param.getValue();
            if (consumable == null) {
                throw new IllegalStateException("Consumable must not be null");
            }
            return new SimpleStringProperty(consumable.getName());
        });

        this.effectsColumn.setCellValueFactory(param -> {
            StringBuilder builder = new StringBuilder();
            Experiment experiment = findExperiment(param.getValue());

            if (experiment == null) {
                return null;

            }
            for (Result result : experiment.getResults()) {
                if (builder.length() != 0) {
                    builder.append(',');
                    builder.append(' ');
                }

                if (result.getEffect() == null) {
                    throw new IllegalStateException("Effect must not be null");
                }
                builder.append(result.getEffect().getName());

                if (result.getComment() != null) {
                    builder.append(' ');
                    builder.append('(');
                    builder.append(result.getComment());
                    builder.append(')');
                }
            }
            return new SimpleStringProperty(builder.toString());
        });

        this.experimentsTable.setRowFactory(param -> {
            TableRow<Consumable> row = new TableRow<Consumable>() {
                @Override
                protected void updateItem(Consumable consumable, boolean empty) {
                    super.updateItem(consumable, empty);

                    if (empty || consumable == null) {
                        return;
                    }

                    boolean isUntested = true;
                    boolean isSuccess = false;
                    boolean isFailure = false;

                    for (Experiment experiment : curio.getExperiments()) {
                        if (consumable.equals(experiment.getConsumable())) {
                            isUntested = false;

                            for (Result result : experiment.getResults()) {
                                if (result.getEffect() != null) {
                                    if (result.getEffect().isPositive()) {
                                        isSuccess = true;
                                    }
                                    if (result.getEffect().isNegative()) {
                                        isFailure = true;
                                    }
                                }
                            }
                        }
                    }

                    String color = null;
                    if (isUntested) {
                        color = "gray";
                    } else if (isSuccess && isFailure) {
                        color = "yellow";
                    } else if (isSuccess) {
                        color = "palegreen";
                    } else {
                        color = "red";
                    }

                    this.setStyle("-fx-background-color: " + color);
                }
            };

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    for (Experiment experiment : curio.getExperiments()) {
                        if (experiment.getConsumable() == row.getItem()) {
                            row.fireEvent(new ExperimentSelectedEvent(experiment));
                            break;
                        }
                    }
                }
            });

            return row;
        });

    }

    public void setData(DataStorage dataStorage, Curio curio) {
        this.dataStorage = dataStorage;
        this.curio = curio;
        forceUiUpdate();
    }

    private Experiment findExperiment(Consumable consumable) {
        for (Experiment experiment : curio.getExperiments()) {
            if (consumable.equals(experiment.getConsumable())) {
                return experiment;
            }
        }

        return null;
    }

    public void forceUiUpdate() {
        StringBuilder locationsLabelBuilder = new StringBuilder();
        for (Location location : this.curio.getLocations()) {
            if (locationsLabelBuilder.length() > 0) {
                locationsLabelBuilder.append(',');
                locationsLabelBuilder.append(' ');
            }

            locationsLabelBuilder.append(location.getName());
        }
        this.locations.setText(locationsLabelBuilder.toString());

        this.experimentsTable.getItems().setAll(dataStorage.getConsumables());
        this.experimentsTable.setPrefHeight(this.experimentsTable.getItems().size() * 24 + 30);
    }


}

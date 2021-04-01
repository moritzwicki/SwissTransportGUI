package org.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.Core.Transport;
import org.Models.Connection;
import org.Models.Station;
import org.Models.Stations;
import org.TableViewObjects.ConnectionTableViewObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class ConnectionAndStationsController {

    @FXML TextField startPointTextFiled;
    @FXML JFXButton saveConnectionButton;
    @FXML TextField destinationTextField;
    @FXML JFXDatePicker targetDatePicker;
    @FXML JFXTimePicker departureTimePicker;
    @FXML TableView connectionsTableView;
    @FXML TextField stationsTextField;
    @FXML JFXButton stationSearchButton;
    @FXML JFXListView stationsListView;
    @FXML TableColumn<ConnectionTableViewObject, String> departureTimeColumn;
    @FXML TableColumn<ConnectionTableViewObject, String> departurePlatformColumn;
    @FXML TableColumn<ConnectionTableViewObject, String> arrivalTimeColumn;
    @FXML TableColumn<ConnectionTableViewObject, String> arrivalPlatformColumn;

    private Transport transportApi;
    ArrayList stationNameList = new ArrayList();
    public void initialize() {
        transportApi = new Transport();
        configureDatePicker();
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<ConnectionTableViewObject, String>("departureTime"));
        departurePlatformColumn.setCellValueFactory(new PropertyValueFactory<ConnectionTableViewObject, String>("departurePlatform"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<ConnectionTableViewObject, String>("arrivalTime"));
        arrivalPlatformColumn.setCellValueFactory(new PropertyValueFactory<ConnectionTableViewObject, String>("arrivalPlatform"));
        setDefaultDateAndTime();
    }

    private void setDefaultDateAndTime() {
        targetDatePicker.setValue(LocalDate.now());
        departureTimePicker.setValue(LocalTime.now());
    }

    @FXML
    private void searchConnection_Click() {
        connectionsTableView.getItems().clear();
        String time = departureTimePicker.getValue().toString();
        String date = targetDatePicker.getValue().toString();
        try {
            if (!startPointTextFiled.getText().equals("") && !destinationTextField.getText().equals(""))
            {
                for (Connection connection: transportApi.getConnections(startPointTextFiled.getText(), destinationTextField.getText(), time, date).ConnectionList) {
                    addConnectionToTableView(connection);
                }
            } else {
                showNothingIsInTextfileAlert();
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Es konnte keine Verbindung gefunden werden");
            alert.show();
            startPointTextFiled.clear();
            destinationTextField.clear();
        }

    }

    @FXML
    private void searchStations_Click() {
        if (!stationsTextField.getText().equals("")) {
            stationsListView.getItems().clear();
            Stations stations = transportApi.getStations(stationsTextField.getText());
            if (stations.StationList.size() <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Es konnte keine Station mit diesem Namen gefunden werden");
                alert.show();
                stationsTextField.clear();
            }
            for (Station s: stations.StationList) {
                stationsListView.getItems().add(s.Name);
            }
        } else {
            showNothingIsInTextfileAlert();
        }
    }
    @FXML
    private void submitSearchWithEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            searchStations_Click();
        }
    }

//    @FXML
//    private void autocompletionForStationSearch(KeyEvent keyEvent) {
//        if (stationsTextField.getText().length() > 0) {
//            if (!isKeyCodeTab(keyEvent)) {
//                for (Station station :transportApi.getStations(stationsTextField.getText()).StationList) {
//                    stationNameList.add(station.Name);
//                }
//            }
//        }
//    }


    private boolean isKeyCodeTab(KeyEvent keyEvent) {
        return keyEvent.getCode().equals(KeyCode.TAB);
    }

    private void addConnectionToTableView(Connection connection) throws ParseException {
        SimpleDateFormat dateAndHourFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat hourAndMinutesFormat = new SimpleDateFormat("HH:mm");

        dateAndHourFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        hourAndMinutesFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date departure = dateAndHourFormat.parse(connection.From.Departure);
        Date arrival = dateAndHourFormat.parse(connection.To.Arrival);
        ConnectionTableViewObject connectionTableViewObject = new ConnectionTableViewObject(
                hourAndMinutesFormat.format(departure),
                connection.From.Platform,
                hourAndMinutesFormat.format(arrival),
                connection.To.Platform);
        connectionsTableView.getItems().add(connectionTableViewObject);
    }

    private void showNothingIsInTextfileAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Sie haben nichts ins Eingabefeld eingegeben");
        alert.show();
    }

    @FXML
    private void removeSpecialCharacters(KeyEvent keyEvent) {
        try {
            final char c = keyEvent.getCharacter().charAt(0);
            if (c != ','){
                if (!Character.isLetter(c) && !Character.isSpaceChar(c)) {
                    keyEvent.consume();
                }
            }
        } catch (Exception e) {

        }
    }

    private void configureDatePicker() {
        targetDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }

    @FXML
    private void selectConnection_SelectedIndexChanged() {
        saveConnectionButton.setDisable(false);
    }

    @FXML
    private void saveConnectionInFile_Click() {
        //TODO: Save The Selected Connection in a File
    }

    @FXML
    private void sendConnectionWithMail() {
        //TODO: Send Connetion via Mail
    }
}

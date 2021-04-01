package org.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.Core.Transport;
import org.Models.Station;
import org.Models.StationBoard;
import org.Models.StationBoardRoot;
import org.TableViewObjects.StationBoardTableViewObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepartureBoardController {
    @FXML JFXTextField stationTextField;
    @FXML JFXButton stationSearchButton;
    @FXML TableView stationBoardTableView;
    @FXML TableColumn<StationBoardTableViewObject, String> departureTimeColumn;
    @FXML TableColumn<StationBoardTableViewObject, String> arrivalStationColumn;

    private Transport transportApi;
    public void initialize() {
        transportApi = new Transport();

        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<StationBoardTableViewObject, String>("departureTime"));
        arrivalStationColumn.setCellValueFactory(new PropertyValueFactory<StationBoardTableViewObject, String>("arrivalStation"));
    }

    @FXML
    private void searchStationBoard_Click() throws ParseException {
        stationBoardTableView.getItems().clear();
        List<Station> list = transportApi.getStations(stationTextField.getText()).StationList;
        if (!stationTextField.getText().equals("")) {
            StationBoardRoot stationBoardRoot = transportApi.getStationBoard(stationTextField.getText(), list.get(0).Id);
            for (StationBoard stationBoard: stationBoardRoot.Entries) {
                addStationBoardToTableView(stationBoard);
            }
        }
    }

    private void addStationBoardToTableView(StationBoard stationBoard) throws ParseException {
        SimpleDateFormat dateAndHourFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat hourAndMinutesFormat = new SimpleDateFormat("HH:mm");

        dateAndHourFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        hourAndMinutesFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date departure = dateAndHourFormat.parse(stationBoard.Stop.Departure);
        StationBoardTableViewObject stationBoardTableViewObject = new StationBoardTableViewObject(
                hourAndMinutesFormat.format(departure),
                stationBoard.To);
        stationBoardTableView.getItems().add(stationBoardTableViewObject);
    }
}

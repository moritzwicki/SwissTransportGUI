package org.TableViewObjects;

public class StationBoardTableViewObject {

    private final String departureTime;
    private final String arrivalStation;

    public StationBoardTableViewObject(String departureTime, String arrivalStation) {
        this.departureTime = departureTime;
        this.arrivalStation = arrivalStation;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }
}

package org.TableViewObjects;

public class ConnectionTableViewObject {
    private final String departureTime;
    private final String departurePlatform;
    private final String arrivalTime;
    private final String arrivalPlatform;

    public ConnectionTableViewObject(String departureTime,
                                     String departurePlatform,
                                     String arrivalTime,
                                     String arrivalPlatform) {

        this.departureTime = departureTime;
        this.departurePlatform = departurePlatform;
        this.arrivalTime = arrivalTime;
        this.arrivalPlatform = arrivalPlatform;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getDeparturePlatform() {
        return departurePlatform;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getArrivalPlatform() {
        return arrivalPlatform;
    }
}

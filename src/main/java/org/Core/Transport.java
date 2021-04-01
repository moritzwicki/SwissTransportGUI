package org.Core;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.Models.*;
import java.io.IOException;
public class Transport {
    private HttpClientService http = new HttpClientService();
    private String host = "http://transport.opendata.ch/v1/";
    public Stations getStations(String query) {
        query = transformQuery(query);
        String jsonString = "";
        try {
            if(!query.equals(""))
                jsonString = http.makeRequest(host + "locations?query=" + query);
            else
                throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        Stations stations = null;
        try{
            stations = mapper.readValue(jsonString, Stations.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return stations;
    }

    public StationBoardRoot getStationBoard(String station, String id) {
        station = transformQuery(station);
        String jsonString = "";
        try {
            if(!station.equals("") && !id.equals(""))
                jsonString = http.makeRequest(host +"stationboard?station="+ station +"&id=" + id);
            else
                throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        StationBoardRoot stationBoardRoot = null;
        try{
            stationBoardRoot = mapper.readValue(jsonString, StationBoardRoot.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return stationBoardRoot;
    }
    public Connections getConnections(String fromStation, String toStation, String time, String date) {
        fromStation = transformQuery(fromStation);
        toStation = transformQuery(toStation);
        time = transformQuery(time);
        date = transformQuery(date);
        String jsonString = "";
        try {
            if(!fromStation.equals("") && !toStation.equals(""))
                jsonString = http.makeRequest(host +"connections?from="+ fromStation + "&to="+ toStation + "&date" + date + "&time="+ time);
            else
                throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        Connections connections = null;
        try{
            connections = mapper.readValue(jsonString, Connections.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return connections;
    }
    private String transformQuery(String query) {
        return query.replaceAll("\\s+", "%20");
    }

}

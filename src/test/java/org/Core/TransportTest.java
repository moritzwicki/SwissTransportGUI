package org.Core;

import junit.framework.TestCase;
import org.Models.Connections;
import org.Models.StationBoardRoot;
import org.Models.Stations;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

public class TransportTest extends TestCase {

    Transport testee = new Transport();

    @Test
    public void testGetStations() {
        Stations stations = testee.getStations("Basel");

        assertEquals(10, stations.StationList.size());
    }

    public void testGetStationBoard() {
        StationBoardRoot stationBoardRoot = testee.getStationBoard("Sursee", "8502007");

        assertNotNull(stationBoardRoot);
    }

    public void testGetConnections() {

        Connections connections = testee.getConnections("Sursee", "Luzern", LocalTime.now().toString(), LocalDate.now().toString());

        assertNotNull(connections);
    }
}

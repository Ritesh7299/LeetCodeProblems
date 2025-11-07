package leetcode.solution.LC3607;

import java.util.*;

public class PowerGridMaintenance {
    public int[] processQueries(int c, int[][] connections, int[][] queries) {
        Set<Integer> isOnline = new HashSet<>();
        Map<Integer, List<Integer>> connectionPaths = new HashMap<>();
        List<Integer> queryResults = new ArrayList<>();

        // Mark all stations online
        for (int i = 1; i <= c; i++) {
            isOnline.add(i);
        }

        // Create a map of grid connections
        for (int[] connection : connections) {
            connectionPaths.computeIfAbsent(connection[0], k -> new ArrayList<>()).add(connection[1]);
            connectionPaths.computeIfAbsent(connection[1], k -> new ArrayList<>()).add(connection[0]);
        }
        // System.out.println("Connection Map: " + connectionPaths);

        // Run queries
        for (int[] query : queries) {
            int queryType = query[0];
            int stationId = query[1];

            // System.out.println("Query: " + queryType + ", " + stationId);

            if (queryType == 1) {
                // System.out.println("    " + "Added: " + stationId);

                if (isOnline.contains(stationId)) {
                    queryResults.add(stationId);
                } else {
                    TreeSet<Integer> onlineStationsInGrid = new TreeSet<>();
                    Set<Integer> exploredStations = new HashSet<>();

                    runMaintenanceCheckForGrid(isOnline, connectionPaths, stationId, onlineStationsInGrid,
                            exploredStations);

                    if (onlineStationsInGrid.isEmpty()) {
                        queryResults.add(-1);
                    } else {
                        queryResults.add(onlineStationsInGrid.first());
                    }
                }
            } else {
                // System.out.println("    " + "Taken Offline: " + stationId);
                isOnline.remove(stationId);
            }
        }

        return queryResults.stream().mapToInt(Integer::intValue).toArray();
    }

    public void runMaintenanceCheckForGrid(
            Set<Integer> isOnline,
            Map<Integer, List<Integer>> connectionPaths,
            int stationId,
            TreeSet<Integer> onlineStationsInGrid,
            Set<Integer> exploredStations) {

        if (exploredStations.contains(stationId) || !connectionPaths.containsKey(stationId)) {
            return;
        }

        exploredStations.add(stationId);

        for (int nextStation : connectionPaths.get(stationId)) {

            if (isOnline.contains(nextStation)) {
                // System.out.println("        " + "Added to Online Stations: " + nextStation);
                onlineStationsInGrid.add(nextStation);
            }

            runMaintenanceCheckForGrid(isOnline, connectionPaths, nextStation, onlineStationsInGrid, exploredStations);
        }
    }
}

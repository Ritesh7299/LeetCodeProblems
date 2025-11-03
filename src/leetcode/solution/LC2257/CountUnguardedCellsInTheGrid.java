package leetcode.solution.LC2257;

import java.util.Arrays;

public class CountUnguardedCellsInTheGrid {
    public int countUnguarded(int m, int n, int[][] guards, int[][] walls) {
        if (guards.length + walls.length == m * n) {
            return 0;
        }

        char[][] map = new char[m][n];

        // Mark all cells as unprotected
        for (char[] row : map) {
            Arrays.fill(row, 'U');
        }


        // Put Guards on map
        for (int[] guardPosition : guards) {
            map[guardPosition[0]][guardPosition[1]] = 'G';
        }

        // Put Walls on map
        for (int[] wallPosition : walls) {
            map[wallPosition[0]][wallPosition[1]] = 'W';
        }

        for (int[] guardPosition : guards) {
            explore(map, guardPosition);
        }

        // Calculate the unprotected cells
        int count = 0;
        for (char[] row : map) {
            for (char cell : row) {
                if (cell == 'U') {
                    count++;
                }
            }
        }

        return count;
    }

    public void explore(char[][] map, int[] guardPosition) {
        int row = guardPosition[0] + 1;
        int col = guardPosition[1];

        // Explore East and West of guard position
        while (row < map.length && map[row][col] != 'W' && map[row][col] != 'G') {
            map[row++][col] = 'P'; // Mark cells as protected until the guard hits wall
        }

        row = guardPosition[0] - 1;

        while (row >= 0 && map[row][col] != 'W' && map[row][col] != 'G') {
            map[row--][col] = 'P'; // Mark cells as protected until the guard hits wall
        }

        row = guardPosition[0];
        col++;

        // Explore north and south of guard position
        while (col < map[0].length && map[row][col] != 'W' && map[row][col] != 'G') {
            map[row][col++] = 'P'; // Mark cells as protected until the guard hits wall
        }

        col = guardPosition[1] - 1;

        while (col >= 0 && map[row][col] != 'W' && map[row][col] != 'G') {
            map[row][col--] = 'P'; // Mark cells as protected until the guard hits wall
        }
    }
}

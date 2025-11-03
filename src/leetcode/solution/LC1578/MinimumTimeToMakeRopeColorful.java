package leetcode.solution.LC1578;

public class MinimumTimeToMakeRopeColorful {
    public int minCost(String colors, int[] neededTime) {
        if (colors.isEmpty() || neededTime.length == 0) {
            return 0;
        }

        int fastestTime = 0;
        int len = colors.length();

        // Iterate through colors
        for (int i = 0; i < len; i++) {
            int fast = i;
            int maxIndex = i;

            // Iterate through same adjacent colors and get the index of local maxima
            for (int j = i + 1; j < len; j++) {
                // Continue through loop if same adjacent colors found else break the loop
                if (colors.charAt(j - 1) == colors.charAt(j)) {
                    if (neededTime[maxIndex] < neededTime[j]) {
                        maxIndex = j;
                    }
                    fast = j;
                } else {
                    break;
                }
            }

            // Add times to result excluding local maxima time if adjacent colors found
            // If fast == i no adjacents were found in above loop
            if (fast > i) {
                for (int k = i; k <= fast; k++) {
                    if (k != maxIndex) {
                        fastestTime += neededTime[k];
                    }
                }

                i = fast;
            }
        }

        return fastestTime;
    }
}

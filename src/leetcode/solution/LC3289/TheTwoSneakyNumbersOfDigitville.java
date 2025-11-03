package leetcode.solution.LC3289;

public class TheTwoSneakyNumbersOfDigitville {
    public int[] getSneakyNumbers(int[] nums) {
        int[] frequency = new int[nums.length];
        int[] sneakyNumbers = new int[2];
        int i = 0;

        // Update the frequency of each number
        for (int num : nums) {
            // Add num to result if it has occurred already
            if (frequency[num]++ == 1) {
                sneakyNumbers[i++] = num;

                // Stop when both sneaky numbers are found
                if (i > 1) {
                    break;
                }
            }
        }

        return sneakyNumbers;
    }
}

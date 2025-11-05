package leetcode.solution.LC3318;

import java.util.*;

public class FindXSumOfAllKLongSubarraysI {
        public int[] findXSum(int[] nums, int k, int x) {
            if (k == 1) {
                return nums.clone();
            }

            int[] result = new int[nums.length - k + 1];

            for (int i = 0; i < result.length; i++) {
                result[i] = findSum(nums, i, k, x);
            }

            return result;
        }

        public int findSum(int[] nums, int current, int k, int x) {
            Map<Integer, Integer> frequencyMap = new HashMap<>();

            int maxFreq = 0;
            for (int i = current; i < current + k; i++) {
                frequencyMap.put(nums[i], frequencyMap.getOrDefault(nums[i], 0) + 1);
                maxFreq = Math.max(maxFreq, frequencyMap.get(nums[i]));
            }

            // Create buckets by frequency
            List<Integer>[] bucketList = new ArrayList[maxFreq + 1];

            for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
                int num = entry.getKey();
                int freq = entry.getValue();

                if (bucketList[freq] == null) {
                    bucketList[freq] = new ArrayList<>();
                }

                bucketList[freq].add(num);
            }

            // Now collect top x numbers (highest freq, break ties by larger number)
            List<Integer> topX = new ArrayList<>();
            for (int freq = maxFreq; freq >= 1 && topX.size() < x; freq--) {
                if (bucketList[freq] == null) continue;

                // Sort descending by value for tie-breaking
                bucketList[freq].sort(Collections.reverseOrder());

                for (int num : bucketList[freq]) {
                    if (topX.size() < x) {
                        topX.add(num);
                    } else {
                        break;
                    }
                }
            }

            // If there are fewer than x distinct numbers, take all (handled naturally)
            int sum = 0;
            for (int num : topX) {
                sum += num * frequencyMap.get(num);
            }

            return sum;
        }
}

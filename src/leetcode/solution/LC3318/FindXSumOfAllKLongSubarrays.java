package leetcode.solution.LC3318;

import java.util.*;

public class FindXSumOfAllKLongSubarrays {
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

        List<Integer>[] bucketList = new ArrayList[maxFreq + 1];

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            int num = entry.getKey();
            int freq = entry.getValue();

            if (bucketList[freq] == null) {
                bucketList[freq] = new ArrayList<>();
            }

            bucketList[freq].add(num);
        }

        List<Integer> topX = new ArrayList<>();
        int i = maxFreq;

        while (topX.size() < x && i >= 1) {
            System.out.println("topX: " + topX);
            System.out.println("i: " + i);

            List<Integer> list = bucketList[i];

            if (list == null) {
                i--;
                continue;
            }

            list.sort(Collections.reverseOrder());

            for (int n : list) {
                if (topX.size() < x) {
                    topX.add(n);
                } else {
                    break;
                }
            }

            i--;
        }

        int sum = 0;
        for (int n : topX) {
            sum += n * frequencyMap.get(n);
        }

        return sum;
    }
}

package leetcode.solution.LC3321;

import java.util.*;

public class FindXSumOfAllKLongSubarraysII {
    public long[] findXSum(int[] nums, int k, int x) {
        Map<Integer, Integer> frequencies = new HashMap<>();
        TreeMap<Integer, TreeSet<Integer>> buckets = new TreeMap<>();
        long[] sumArray = new long[nums.length - k + 1];

        for (int i = 0; i < k; i++) {
            add(frequencies, buckets, nums[i]);
        }

        sumArray[0] = computeTopXSum(buckets, x);

        for (int i = k; i < nums.length; i++) {
            remove(frequencies, buckets, nums[i - k]);
            add(frequencies, buckets, nums[i]);
            sumArray[i - k + 1] = computeTopXSum(buckets, x);
        }

        return sumArray;
    }

    public void add(Map<Integer, Integer> frequencies, TreeMap<Integer, TreeSet<Integer>> buckets, int num) {
        int currFreq = frequencies.getOrDefault(num, 0);
        int newFreq = currFreq + 1;

        frequencies.put(num, newFreq);

        if (currFreq > 0) {
            TreeSet<Integer> bucket = buckets.get(currFreq);
            bucket.remove(num);

            if (bucket.isEmpty()) {
                buckets.remove(currFreq);
            }
        }

        buckets.computeIfAbsent(newFreq, k -> new TreeSet<>()).add(num);
    }

    public void remove(Map<Integer, Integer> frequencies, TreeMap<Integer, TreeSet<Integer>> buckets, int num) {
        int currFreq = frequencies.getOrDefault(num, 0);
        if (currFreq == 0) {
            return;
        }

        TreeSet<Integer> bucket = buckets.get(currFreq);
        bucket.remove(num);

        if (bucket.isEmpty()) {
            buckets.remove(currFreq);
        }

        if (currFreq == 1) {
            frequencies.remove(num);
        } else {
            int newFreq = currFreq - 1;
            frequencies.put(num, newFreq);
            buckets.computeIfAbsent(newFreq, k -> new TreeSet<>()).add(num);
        }
    }

    public long computeTopXSum(TreeMap<Integer, TreeSet<Integer>> buckets, int x) {
        long sum = 0;
        int count = 0;

        for (int freq : buckets.descendingKeySet()) {
            TreeSet<Integer> bucket = buckets.get(freq);

            for (int n : bucket.descendingSet()) {
                sum += (long) n * freq;

                if (++count == x) {
                    return sum;
                }
            }
        }

        return sum;
    }
}

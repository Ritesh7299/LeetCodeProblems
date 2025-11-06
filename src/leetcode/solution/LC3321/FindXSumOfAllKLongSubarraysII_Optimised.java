package leetcode.solution.LC3321;

import java.util.*;
import java.util.function.Consumer;

public class FindXSumOfAllKLongSubarraysII_Optimised {
    static class Pair {
        int num, freq;
        Pair(int n, int f) { num = n; freq = f; }

        // TreeSet uses comparator for ordering, but remove() may rely on equals/hashCode
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair)) return false;
            Pair p = (Pair) o;
            return num == p.num && freq == p.freq;
        }

        @Override
        public int hashCode() {
            return Objects.hash(num, freq);
        }
    }

    public long[] findXSum(int[] nums, int k, int x) {
        int n = nums.length;
        long[] ans = new long[n - k + 1];

        Map<Integer, Integer> freq = new HashMap<>();

        // Two TreeSets (simulate multisets) sorted by (freq desc, num desc)
        TreeSet<Pair> top = new TreeSet<>((a, b) -> {
            if (a.freq != b.freq) return b.freq - a.freq;
            return b.num - a.num;
        });
        TreeSet<Pair> rest = new TreeSet<>((a, b) -> {
            if (a.freq != b.freq) return b.freq - a.freq;
            return b.num - a.num;
        });

        final long[] topSum = new long[]{0L}; // mutable holder usable inside lambdas

        // Rebalance routine: ensure top has up to x best elements
        Runnable rebalance = () -> {
            // If top has more than x, move smallest in top to rest
            while (top.size() > x) {
                Pair last = top.last();  // smallest in top (because top is sorted desc)
                top.remove(last);
                rest.add(last);
                topSum[0] -= (long) last.num * last.freq;
            }
            // If top has less than x, promote largest from rest
            while (top.size() < x && !rest.isEmpty()) {
                Pair first = rest.first(); // largest in rest
                rest.remove(first);
                top.add(first);
                topSum[0] += (long) first.num * first.freq;
            }
            // If boundary elements are out of order (smallest in top < largest in rest), swap
            while (!top.isEmpty() && !rest.isEmpty()) {
                Pair t = top.last(), r = rest.first();
                // t is "worse" than r (i.e., lower freq or same freq but lower num) -> swap
                if (t.freq > r.freq || (t.freq == r.freq && t.num > r.num)) {
                    break; // top elements are all >= rest elements, OK
                }
                top.remove(t);
                rest.remove(r);
                top.add(r);
                rest.add(t);
                topSum[0] += (long) r.num * r.freq - (long) t.num * t.freq;
            }
        };

        // Function to add a number into data structures
        Consumer<Integer> add = num -> {
            int f = freq.getOrDefault(num, 0);
            if (f > 0) {
                Pair old = new Pair(num, f);
                // remove old pair from whichever set it is in
                if (!top.remove(old)) rest.remove(old);
                else topSum[0] -= (long) num * f;
            }
            freq.put(num, f + 1);
            Pair nw = new Pair(num, f + 1);
            // initially insert into rest; rebalance will place it correctly
            rest.add(nw);
            rebalance.run();
        };

        // Function to remove a number from data structures (sliding window)
        Consumer<Integer> remove = num -> {
            int f = freq.getOrDefault(num, 0);
            if (f == 0) return;
            Pair old = new Pair(num, f);
            // remove old pair from whichever set it is in
            if (!top.remove(old)) rest.remove(old);
            else topSum[0] -= (long) num * f;
            if (f == 1) {
                freq.remove(num);
            } else {
                freq.put(num, f - 1);
                rest.add(new Pair(num, f - 1));
            }
            rebalance.run();
        };

        // initialize first window
        for (int i = 0; i < k; i++) add.accept(nums[i]);
        ans[0] = topSum[0];

        // slide window
        for (int i = k; i < n; i++) {
            remove.accept(nums[i - k]);
            add.accept(nums[i]);
            ans[i - k + 1] = topSum[0];
        }

        return ans;
    }
}

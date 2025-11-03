package leetcode.solution.LC3217;

import common.utils.ListNode;
import java.util.HashSet;
import java.util.Set;

public class DeleteNodesFromLinkedListPresentInArray {
    public ListNode modifiedList(int[] nums, ListNode head) {
        ListNode tHead = new ListNode(0);
        Set<Integer> numsSet = new HashSet<>();

        tHead.next = head;

        for (int num : nums) {
            numsSet.add(num);
        }

        ListNode prev = tHead;

        while (head != null) {
            if (numsSet.contains(head.val)) {
                prev.next = head.next;
            } else {
                prev = head;
            }

            head = head.next;
        }

        return tHead.next;
    }
}

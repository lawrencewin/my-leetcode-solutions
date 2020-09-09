import java.util.*;

// Problem here: https://leetcode.com/problems/merge-k-sorted-lists/
class Solution {

// Definition for singly-linked list.
    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
 
    
    // O(k)
    private boolean containsNonNull(ListNode[] lists) {
        for (ListNode n : lists) {
            if (n != null) return true;
        }
        return false;
    }
    
    private ListNode mergeTwo (ListNode n1, ListNode n2) {
        ListNode head = new ListNode(0);
        ListNode curr = head;
        while (n1 != null && n2 != null) {
            int nextVal = 0;
            if (n1.val < n2.val) {
                nextVal = n1.val;
                n1 = n1.next;
            } else {
                nextVal = n2.val;
                n2 = n2.next;
            }
            curr.next = new ListNode(nextVal);
            curr = curr.next;
        }
        ListNode leftover = n1 == null ? n2 : n1;
        while (leftover != null) {
            curr.next = new ListNode(leftover.val);
            curr = curr.next;
            leftover = leftover.next;
        }
        return head.next;
    }
    
    // O(Nlogk) for k lists and N nodes
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        Deque<ListNode> nodeCon = new LinkedList<>();
        for (ListNode n : lists) 
            nodeCon.addLast(n);
        while (nodeCon.size() > 1) {
            for (int i = 0; i < nodeCon.size() / 2; i += 2) {
                ListNode n1 = nodeCon.removeFirst();
                ListNode n2 = nodeCon.removeFirst();
                nodeCon.addLast(mergeTwo(n1, n2));
            }
        }
        return nodeCon.removeFirst();
        
    }

    // O(Nk) for k lists and N nodes
    public ListNode mergeKListsSlow(ListNode[] lists) {
        ListNode head = new ListNode(0);
        ListNode curr = head;
        while (containsNonNull(lists)) {
            // Find first non null list
            int index = 0;
            while (lists[index] == null) 
                index++;
            // Find smallest list now
            int smallestIdx = index;
            for (int i = index + 1; i < lists.length; i++) {
                if (lists[i] != null && lists[i].val < lists[smallestIdx].val) {
                    smallestIdx = i;
                }
            }
            curr.next = new ListNode(lists[smallestIdx].val);
            curr = curr.next;
            lists[smallestIdx] = lists[smallestIdx].next;
        }
        return head.next;
    }
}
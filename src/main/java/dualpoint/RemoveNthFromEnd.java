package dualpoint;

public class RemoveNthFromEnd {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fast = head;
        ListNode slow = head;
        ListNode prev = null;

        while (n > 0) {
            fast = fast.next;
            n--;
        }

        if (fast == null) {
            return head.next;
        }


        while (fast != null) {
            prev = slow;
            fast = fast.next;
            slow = slow.next;
        }

        prev.next = slow.next;
        return head;
    }
}

package dualpoint;

public class RotateRight {
    public ListNode rotateRight(ListNode head, int k) {
        ListNode prev = null;
        ListNode prepare = null;
        ListNode p = head;
        int len = 0;
        while (p != null) {
            prev = p;
            p = p.next;
            len ++;
        }
        k %= len;
        k = len - k;
        p = head;
        while (k > 0) {
            prepare = p;
            p = p.next;
            k--;
        }
        if (prepare!= null){
            prev.next = head;
        }
        if (p != null) {
            prepare.next = null;
        }
        return p;
    }
}

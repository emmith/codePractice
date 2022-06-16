package bytedance.linkedlist;

import DataStructure.ListNode;

public class ReverseKGroup {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode fakeHead = new ListNode();
        fakeHead.next = head;
        ListNode prev = fakeHead;
        ListNode cur = head;
        ListNode index = head;
        int len = 0;
        while (index != null) {
            len++;
            index = index.next;
        }
        index = head;

        for (int i = 0; i < len / k;i++) {
            for (int j = 0;j < k - 1;j++) {
                index = cur.next;
                cur.next = index.next;
                index.next = prev.next;
                prev.next = index;
            }
            prev = cur;
            cur = index;
        }
        return fakeHead.next;
    }
}

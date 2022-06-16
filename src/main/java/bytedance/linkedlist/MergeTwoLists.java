package bytedance.linkedlist;


import DataStructure.ListNode;

public class MergeTwoLists {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode head = new ListNode();
        ListNode res = head;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                head.next = list1;
                head = head.next;
                list1 = list1.next;
            } else {
                head.next = list2;
                head = head.next;
                list2 = list2.next;
            }
        }
        if (list1 != null) {
            head.next = list1;
        } else {
            head.next = list2;
        }
        return res.next;
    }


    public ListNode mergeTwoLists2(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return null;
        }
        if (list2 == null) {
            return null;
        }
        ListNode res = list1.val < list2.val ? list1 : list2;
        res.next = mergeTwoLists2(res.next, list1.val >= list2.val ? list1 : list2);
        return res;
    }
}


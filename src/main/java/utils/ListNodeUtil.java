package utils;


import DataStructure.ListNode;

public class ListNodeUtil {
    public static ListNode createList(int []data) {
        int num = data.length;
        ListNode head = new ListNode(-1);
        ListNode p = head;
        for(int i = 0;i < data.length;i++) {
            ListNode node = new ListNode(data[i]);
            p.next = node;
            p = p.next;
        }
        return head.next;
    }

    public static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }
}

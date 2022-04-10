package bytedance.linkedlist;

import DataStructure.ListNode;
import utils.ListNodeUtil;

import java.util.List;

public class ReverseList {
    public static ListNode reverseList(ListNode head) {
        ListNode fakeHead = new ListNode();
        fakeHead.next = null;
        ListNode next = null;

        while (head != null) {
            next = head.next;
            head.next = fakeHead.next;
            fakeHead.next = head;
            head = next;
        }
        return fakeHead.next;
    }

    //反转链表的left 到 right
    public static ListNode reverseList(ListNode head, int left, int right) {
        ListNode fakeHead = new ListNode(-1);
        fakeHead.next = head;
        ListNode p = fakeHead;

        //找到p的前置节点
        for (int i = 0; i < left;i++) {
            p = p.next;
        }

        //反转中间部分
        ListNode m = p.next;
        ListNode n = m.next;
        for (int i = 0; i < right - left;i++) {
            ListNode temp = n.next;
            n.next = m;
            m = n;
            n = temp;
        }
        //此时中间部分已经反转，还需要把反转后的部分放进去
        //p.next 为反转前的第一个元素 现在将其连接后半部分
        p.next.next = n;
        //连接前半部分连接
        p.next = m;

        return fakeHead.next;
    }

    //K个反转链表，后面不足k的也会反转，采用三指针原地反转法
    public static ListNode reverseKthList(ListNode head, int k) {
        ListNode fakeHead = new ListNode(-1);
        fakeHead.next = head;
        ListNode p = fakeHead;

        while(p.next != null) {
            //反转中间部分
            ListNode m = p.next;
            ListNode n = m.next;
            for (int i = 0; i < k - 1 && n != null;i++) {
                ListNode temp = n.next;
                n.next = m;
                m = n;
                n = temp;
            }
            ListNode pre = p.next;
            //此时中间部分已经反转，还需要把反转后的部分放进去
            //p.next 为反转前的第一个元素 现在将其连接后半部分
            p.next.next = n;
            //连接前半部分连接
            p.next = m;
            p = pre;
        }

        return fakeHead.next;
    }

    //K个反转链表，后面不足k的也会反转，头插法
    public static ListNode reverseKthList2(ListNode head, int k) {
        ListNode fakeHead = new ListNode(-1);
        fakeHead.next = head;
        ListNode pre = fakeHead;

        while(pre.next != null) {
            //反转中间部分
            ListNode cur = pre.next;
            ListNode curNext = cur.next;
            for (int i = 0; i < k - 1 && curNext != null;i++) {
                cur.next = curNext.next;
                curNext.next = pre.next;
                pre.next = curNext;
                curNext = cur.next;
            }
            pre = cur;
        }

        return fakeHead.next;
    }

    public static void main(String[] args) {
        ListNode linkList = ListNodeUtil.createList(new int[] {1,2,3,4,5,6,7,8});
        ListNode p = linkList;

        ListNodeUtil.printList(reverseKthList2(p, 5));
    }
}

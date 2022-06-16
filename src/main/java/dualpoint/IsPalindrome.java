package dualpoint;

public class IsPalindrome {
    public boolean isPalindrome(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        ListNode prev = null;

        //用快慢指针找到中点
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        while (slow != null) {
            ListNode temp = slow.next;
            slow.next = prev;
            prev = slow;
            slow = temp;
        }

        while (head != null && prev != null) {
            if (prev.val != head.val) {
                return false;
            }
            prev = prev.next;
            head = head.next;
        }
        return true;
    }

    //优化
    public boolean isPalindrome2(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        ListNode prev = null;

        //用快慢指针找到中点
        while (fast != null && fast.next != null) {
            ListNode temp = slow.next;
            slow.next = prev;
            prev = slow;
            slow = temp;
            fast = fast.next.next;
        }

        //处理奇数长度
        if (fast != null) {
            slow = slow.next;
        }

        while (head != null && prev != null) {
            if (prev.val != head.val) {
                return false;
            }
            prev = prev.next;
            head = head.next;
        }
        return true;
    }

    public boolean isPalindrome(String s) {
        int len = s.length();
        int left = 0, right = len - 1;
        while (left < right) {
            while (left < right && !isLetter(s.charAt(left))) {
                left++;
            }
            while (left < right && !isLetter(s.charAt(right))) {
                right--;
            }
            if (toLower(s.charAt(left)) != toLower(s.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public boolean isLetter(char ch) {
        if (ch >= '0' && ch <= '9' || ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z'){
            return true;
        }
        return false;
    }

    public char toLower(char ch) {
        if (ch >= 'A' && ch <= 'Z' ) {
            return (char) (ch + 32);
        }
        return ch;
    }
}

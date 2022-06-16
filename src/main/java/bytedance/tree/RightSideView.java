package bytedance.tree;

import bytedance.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RightSideView {
    public List<Integer> rightSideView(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(root);
        }

        List<Integer> list = new ArrayList<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            TreeNode node = queue.peek();
            list.add(node.val);

            while (size > 0) {
                node = queue.poll();
                if (node.right != null) {
                    queue.offer(node.right);
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                size--;
            }
        }
        return list;
    }
}

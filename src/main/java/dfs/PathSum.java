package dfs;

import bytedance.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class PathSum {
    List<List<Integer>> lists = new ArrayList<>();
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        dfs(root, targetSum, 0, new ArrayList<>());
        return lists;
    }

    public void dfs(TreeNode root, int target, int current, List<Integer> list) {
        if (root == null ) {
            return;
        }
        list.add(root.val);
        if (root.left == null && root.right == null) {
            if (current == target) {
                lists.add(new ArrayList<>(list));
            }
        }
        dfs(root.left, target, current + root.val, list);
        dfs(root.right, target, current + root.val, list);
        list.remove(list.size() - 1);
    }
}

package dfs;


import bytedance.TreeNode;

public class MaxPathSum {
    int res = 0;
    public int maxPathSum(TreeNode root) {
        getMax(root);
        return res;
    }

    public int getMax(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = Math.max(0, getMax(root.left));
        int right = Math.max(0, getMax(root.right));

        res = Math.max(left + right + root.val, res);
        return Math.max(left, right) + root.val;
    }

    public int getMaxLen(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = getMaxLen(root.left);
        int right = getMaxLen(root.right);

        res = Math.max(left + right + 2, res);
        return Math.max(left, right) + 1;
    }
}

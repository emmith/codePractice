package dfs;

import bytedance.TreeNode;

public class BuildTree {
    //先序遍历的第一个点为根节点
    //找到其在中序遍历中的位置，其左边为左子树，右边为右子树
    //递归即可
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return dfs(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    public TreeNode dfs(int[] preorder, int l1, int r1, int[] inorder, int l2, int r2) {
        //建立根节点
        TreeNode root = new TreeNode(preorder[l1]);
        //没有左右子树，直接返回
        if (l1 == r1) {
            return root;
        }
        //找到根节点在中序遍历中的位置
        int index = 0;
        for (int i = l2; i <= r2;i++) {
            if (inorder[i] == root.val) {
                index = i;
            }
        }
        //左子树长度
        int left_len = index - l2;
        //右子树长度
        int right_len = r2 - index;

        if (left_len > 0) {
            root.left = dfs(preorder,l1+1, l1+left_len, inorder, l2,  index - 1);
        }
        if (right_len > 0) {
            root.right = dfs(preorder,r1 - right_len + 1, r1, inorder, index+1, r2);
        }
        return root;
    }
}

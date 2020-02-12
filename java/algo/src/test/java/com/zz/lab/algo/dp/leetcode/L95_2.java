package com.zz.lab.algo.dp.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


//https://leetcode-cn.com/problems/target-sum/submissions/

public class L95_2 {
    @Test
    public void test() {
        List<TreeNode> results = generateTrees(10);
        String a = "sdf";
        System.out.println(a);
        if (a.charAt(1) == 'a') {

        }

    }


    public List<TreeNode> generateTrees(int n) {
        List<TreeNode> results = new ArrayList<>();
        if (n == 1) {
            results.add(new TreeNode(n));
        }else {
            List<TreeNode> subTrees = generateTrees(n-1);
            for (TreeNode oneTree: subTrees) {
                List<TreeNode> added = addNode(oneTree, n);
                results.addAll(added);
            }
        }

        return results;

    }

    public List<TreeNode> addNode(TreeNode tree, int n) {
        List<TreeNode> results = new ArrayList<>();
        //case 1: root node
        TreeNode root = new TreeNode(n);
        root.left = tree;
        results.add(root);

        //case 2: put into right node
        if (tree != null) {
            List<TreeNode> subTrees = addNode(tree.right, n);
            for (TreeNode subTree: subTrees) {
                TreeNode oneNewTree = new TreeNode(tree.val);
                oneNewTree.right = subTree;
                results.add(oneNewTree);
            }

        }

        return results;

    }

}

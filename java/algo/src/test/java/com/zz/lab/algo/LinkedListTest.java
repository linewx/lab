package com.zz.lab.algo;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LinkedListTest {


    @Test
    public void testReverseList() {
        ListNode root = new ListNode(3);
        root.next = new ListNode(5);
        ListNode node = reverseBetween(root, 1, 2);
        System.out.println(node.val);
    }

    public ListNode reverseBetween(ListNode head, int m, int n) {
        int index = 1;
        ListNode root = new ListNode(0);
        ListNode start = new ListNode(0);
        start.next = head;
        root.next = head;
        //reverse list
        ListNode currentTail = head;
        if (head == null) {
            return head;
        }
        ListNode currentNode = head.next;

        while (currentNode != null) {

            //find the start point, iter start point util
            if (index < m) {
                start = start.next;
                index++;
                continue;
            } else if (index == m) {
                //init status
                currentTail = start.next;
                currentNode = currentTail.next;
                index++;
                continue;
            } else if (index > n) {
                //exceed the limit
                break;
            }


            // get the start point, start reversing
            currentTail.next = currentNode.next;


            //move current node to head
            currentNode.next = start.next;
            start.next = currentNode;

            //reset currentNode
            currentNode = currentTail.next;
            index++;

        }
        return root.next;

    }
}




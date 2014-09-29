// you can also use imports, for example:
// import java.util.*;

// you can use System.out.println for debugging purposes, e.g.
// System.out.println("this is a debug message");

import java.util.ArrayList;

class Solution {
    public int solution(int[] A, int[] B, int[] C) {
        // write your code in Java SE 8
        int N = A.length;
        if (N == 0)
            return 0;
        Node[] nodes = new Node[N];
        Node root = new Node(-1,0,0);
        // build the tree
        for (int i = 0; i < N; i++) {
            Node node = new Node(i, B[i], A[i]);
            if (C[i] == -1)
                node.parent = root;
            else
                node.parent = nodes[C[i]];
            node.parent.children.add(i);
            nodes[i] = node;
        }
        int[] childrenWeight = new int[N];
        int min = 0;
        int max = N - 1;
        int result = 0;
        // binary search
        while (min <= max) {
            int mid = (min + max) / 2;
            // count the weights of the children
            for (int i = 0; i < N; i++)
                childrenWeight[i] = -1;
            // use a stack to do a depth first search
            Stack stack = new Stack(N);
            if (check(root, nodes, stack, childrenWeight, mid)) {
                result = mid + 1;
                min = mid + 1;
            } else
                max = mid - 1;
        }
        return result;
    }

    public static boolean check(Node root, Node[] nodeArray, Stack stack, int[] childrenWeight, int number) {
        for (int child : root.children)
            if (child <= number)
                stack.push(nodeArray[child]);
        while (!stack.isEmpty()) {
            Node node = stack.getTopValue();
            int nodeId = node.nodeId;
            if (!node.children.isEmpty() && childrenWeight[nodeId] == -1) {
                for (int child : node.children) {
                    if (child <= number)
                        stack.push(nodeArray[child]);
                    childrenWeight[nodeId] = 0;
                }
            } else if (node.children.isEmpty()) {
                stack.pop();
                if (nodeArray[nodeId].weight > nodeArray[nodeId].durability)
                    return false;
                int parentId = nodeArray[nodeId].parent.nodeId;
                if (parentId != -1)
                    childrenWeight[parentId] += nodeArray[nodeId].weight;
            } else if (childrenWeight[nodeId] != -1) {
                stack.pop();
                if (nodeArray[nodeId].weight + childrenWeight[nodeId] > nodeArray[nodeId].durability)
                    return false;
                int parentId = nodeArray[nodeId].parent.nodeId;
                if (parentId != -1)
                    childrenWeight[parentId] += nodeArray[nodeId].weight + childrenWeight[nodeId];
            }
        }
        return true;
    }
}

class Stack {
    Node[] array;
    int top;
    int length;

    public Stack(int length) {
        array = new Node[length];
        this.length = length;
        top = 0;
    }

    public Node pop() {
        if (top != 0)
            return array[--top];
        else
            return null;
    }

    public boolean push(Node node) {
        if (top != length) {
            array[top++] = node;
            return true;
        } else
            return false;
    }

    public Node getTopValue() {
        if (top != 0)
            return array[top - 1];
        else
            return null;
    }

    public boolean isEmpty() {
        return top == 0 ? true : false;
    }
}

class Node {
    int nodeId;
    int weight;
    int durability;
    Node parent;
    ArrayList<Integer> children = new ArrayList<Integer>();

    public Node(int id, int w, int d) {
        nodeId = id;
        weight = w;
        durability = d;
    }
}

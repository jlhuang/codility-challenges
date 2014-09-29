// you can also use imports, for example:
// import java.util.*;

// you can use System.out.println for debugging purposes, e.g.
// System.out.println("this is a debug message");

import java.util.ArrayList;

class Solution {
    public int solution(int[] A, int[] B, int[] C) {
        // write your code in Java SE 8
        // use binary search to find the maximum number of nodes.
        // first build a tree and count the weights of every node recursively.
        // the time complexity is O(N*log(N)) but the stack overflow will happen
        // at the worst case, so it gets a runtime error.
        int N = A.length;
        if (N == 0)
            return 0;
        Node[] nodes = new Node[N];
        Node root = new Node(-1,0,0);
        for (int i = 0; i < N; i++) {
            Node node = new Node(i, B[i], A[i]);
            if (C[i] == -1)
                node.parent = root;
            else
                node.parent = nodes[C[i]];
            node.parent.children.add(i);
            nodes[i] = node;
        }
        int min = 0;
        int max = N - 1;
        int result = 0;
        // binary search
        while (min <= max) {
            int mid = (min + max) / 2;
            for (int i = 0; i< N; i++)
                nodes[i].weight = B[i];
            for (int child : root.children)
                countWeight(nodes[child], nodes, mid);
            boolean pass = true;
            for (int i = 0; i <= mid; i++) {
                if (nodes[i].weight > nodes[i].durability)
                    pass = false;
            }
            if (pass) {
                result = mid + 1;
                min = mid + 1;
            } else
                max = mid - 1;
        }
        return result;
    }
    // count the weight of every node recursively
    public int countWeight(Node root, Node[] nodeArray, int number) {
        if (!root.children.isEmpty()) {
            for (int i : root.children) {
                if (i <= number) {
                    nodeArray[root.nodeId].weight += countWeight(nodeArray[i], nodeArray, number);
                }
            }
            return nodeArray[root.nodeId].weight;
        } else if (root.nodeId <= number)
            return nodeArray[root.nodeId].weight;
        return 0;
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

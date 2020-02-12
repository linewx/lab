package com.zz.lab.algo.graph;

import java.util.*;

public class MyGraph {
    private List<Integer>[] nodes;

    public MyGraph(int v) {
        //initial graph
        nodes = new LinkedList[v];
        for (int index = 0; index < nodes.length; index++) {
            nodes[index] = new LinkedList<>();
        }
    }

    public void addEdge(int a, int b) {
        nodes[a].add(b);
        nodes[b].add(a);
    }

    //广度优先遍历
    public List<Integer> bfs() {
        //init
        List<Integer> results = new ArrayList<>();
        Queue<Integer> queue = new ArrayDeque<>();
        boolean []visited = new boolean[nodes.length];
        //start from node  0
        queue.add(0);
        visited[0] = true;

        while(!queue.isEmpty()) {
            //poll
            Integer elem = queue.poll();
            //mark as visited

            results.add(elem);
            //get all linked node
            List<Integer> linkedNode = nodes[elem];
            for (Integer oneNode : linkedNode) {
                //is visited or not
                //if not put back into queue
                if (!visited[oneNode]) {
                    queue.add(oneNode);
                    visited[oneNode] = true;
                }
            }
        }

        return results;
    }

    public List<Integer> dfs() {
        boolean []visited = new boolean[nodes.length];
        List<Integer> path = new ArrayList<>();
        dfs(visited, 0, path);
        return path;
    }

    public void dfs(boolean []visited, int start, List<Integer> path) {
        visited[start] = true;
        path.add(start);

        for (Integer linkNode : nodes[start]) {
            if (!visited[linkNode]) {
                dfs(visited, linkNode, path);;
            }
        }
    }
}

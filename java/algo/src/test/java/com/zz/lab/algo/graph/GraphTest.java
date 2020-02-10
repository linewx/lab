package com.zz.lab.algo.graph;

import org.junit.Test;

import java.util.List;

public class GraphTest {
    @Test
    public void testGraph() {
        MyGraph myGraph = new MyGraph(8);
        myGraph.addEdge(0,1);
        myGraph.addEdge(0,3);
        myGraph.addEdge(1,2);
        myGraph.addEdge(1,4);
        myGraph.addEdge(2,5);
        myGraph.addEdge(3,4);
        myGraph.addEdge(4,5);
        myGraph.addEdge(4,6);
        myGraph.addEdge(5,7);
        myGraph.addEdge(6,7);

        List<Integer> results = myGraph.bfs();
        System.out.println(results);

        List<Integer> results2 = myGraph.dfs();
        System.out.println(results2);
    }
}

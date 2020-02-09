package com.zz.lab.algo;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class QueenTest {
    @Test
    public void queenTest() {
        solveNQueens(8);

    }

    private List<List<String>> result = new ArrayList<>();
    private int count = 0;

    public List<List<String>> solveNQueens(int n) {
        int []chesses = new int[n];
        dfs(0, chesses, n);
        System.out.println(count);
        return result;
    }

    public List<String> adapterResuslt(int []chesses, int n) {
        List<String> oneSolution = new ArrayList<>();
        for (int i=0; i<n; i++) {
            String oneLine = "";
            for (int j=0; j<n; j++) {
                if (j == chesses[i]) {
                    oneLine = oneLine + "Q";
                }else {
                    oneLine = oneLine + ".";
                }
            }
            oneSolution.add(oneLine);

        }

        return oneSolution;
    }

    public void dfs(int level, int[]chesses, int n) {
        if (level == n) {
            count ++;
            result.add(adapterResuslt(chesses, n));
            return;
        }
        for (int i=0; i<n; i++) {
            chesses[level] = i;
            if (!isInAttack(chesses, level)) {
                dfs(level + 1, chesses, n);
            }
        }
    }

    public boolean isInAttack(int[]chesses,int level) {
        int position = chesses[level];
        for (int index = 0; index<level; index++) {
            if (position == chesses[index]) {
                return true;
            }else if(Math.abs(position - chesses[index]) == (level - index)) {
                return true;
            }
        }
        return false;
    }
}




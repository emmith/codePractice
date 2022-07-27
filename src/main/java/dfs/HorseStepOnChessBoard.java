package dfs;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 马踏棋盘 贪心策略DFS 落子优先选择可走方向少的棋子
 * 例如如果当前棋子有 6个方向可以走 这六个方向对应的位置下一步又分别有 (2,3,4,5,6,5)种方案
 * 我们优先选择 只有下一步只有2种方案的棋子下 每一次都选择小的 能最快找到可行方案
 * 棋盘大小为 X x Y
 */
public class HorseStepOnChessBoard {
    int[][] visit = new int[9][9];
    int X = 8;
    int Y = 8;
    int[] step1 = {1, 1, -1, -1, 2, 2, -2, -2};
    int[] step2 = {2, -2, 2, -2, 1, -1, 1, -1};


    public boolean dfs(int x, int y, int count) {
        visit[x][y] = count;
        if (count == X * Y) {
            return true;
        }
        PriorityQueue<P> minHeap = new PriorityQueue<>((o1, o2) -> {
            return getNext(o1.x, o1.y).size() - getNext(o2.x, o2.y).size();
        });
        List<P> list = getNext(x, y);
        for (P p : list) {
            minHeap.add(p);
        }

        while (!minHeap.isEmpty()) {
            P p = minHeap.poll();
            boolean res = dfs(p.x, p.y, count + 1);
            if (res) {
                return true;
            }
        }

        visit[x][y] = 0;
        return false;
    }

    //获取对应位置，下一步能走位置的下标
    private List<P> getNext(int x, int y) {
        List<P> list = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            int xx = x + step1[i];
            int yy = y + step2[i];
            if (check(xx, yy)) {
                list.add(new P(xx, yy));
            }
        }

        return list;
    }

    //检测当前位置是否可走
    private boolean check(int x, int y) {
        if (x >= 0 && x < X && y >= 0 && y < Y && visit[x][y] == 0) {
            return true;
        }
        return false;
    }


    public static void main(String[] args) {

        HorseStepOnChessBoard horseStepOnChessBoard = new HorseStepOnChessBoard();
        long a = System.currentTimeMillis();
        horseStepOnChessBoard.dfs(6, 5, 1);
        System.out.println(System.currentTimeMillis() - a + "ms");
        for (int[] num : horseStepOnChessBoard.visit) {
            System.out.println(Arrays.toString(num));
        }
    }
}

class P {
    public int x;
    public int y;

    public P(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

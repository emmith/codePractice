package dfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * 你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
 * <p>
 * 在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，其中 prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则 必须 先学习课程  bi 。
 * <p>
 * 例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
 * 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：numCourses = 2, prerequisites = [[1,0]]
 * 输出：true
 * 解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。
 * 示例 2：
 * <p>
 * 输入：numCourses = 2, prerequisites = [[1,0],[0,1]]
 * 输出：false
 * 解释：总共有 2 门课程。学习课程 1 之前，你需要先完成​课程 0 ；并且学习课程 0 之前，你还应先完成课程 1 。这是不可能的。
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= numCourses <= 105
 * 0 <= prerequisites.length <= 5000
 * prerequisites[i].length == 2
 * 0 <= ai, bi < numCourses
 * prerequisites[i] 中的所有课程对 互不相同
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/course-schedule
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class CanFinish {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        //定义邻接表
        List<List<Integer>> adjacencyList = new ArrayList<>();
        //行的下标i表示课程的序号，每一行对应的一维list为修完i课程后可以修的其他课程，其总数为i的出度
        for (int i = 0; i < numCourses; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        //定义入度数组
        int[] inDegree = new int[numCourses];
        //填充邻接表和入度数组
        for (int[] ele : prerequisites) {
            inDegree[ele[1]]++;
            adjacencyList.get(ele[0]).add(ele[1]);
        }
        //定义队列，准备广度优先遍历
        Queue<Integer> queue = new LinkedList<>();
        //将入度为零的课程入队列
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }
        //拓扑排序
        while (!queue.isEmpty()) {
            //从队列中取出入度为零的课程
            int cur = queue.poll();
            numCourses--;
            //将此门课程指向的其他课程入度减一
            for (int i : adjacencyList.get(cur)) {
                inDegree[i]--;
                //入度为零则添加到队列中
                if (inDegree[i] == 0) {
                    queue.add(inDegree[i]);
                }
            }
        }
        //如果拓扑排序能将所有课程遍历完，则无环，否则有环
        return numCourses == 0;
    }

    public boolean canFinish2(int numCourses, int[][] prerequisites) {
        //定义邻接表
        List<List<Integer>> adjacencyList = new ArrayList<>();
        //行的下标i表示课程的序号，每一行对应的一维list为修完i课程后可以修的其他课程，其总数为i的出度
        for (int i = 0; i < numCourses; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        //定义flag数组，记录节点的遍历状态
        int[] flag = new int[numCourses];
        //填充邻接表和入度数组
        for (int[] ele : prerequisites) {
            adjacencyList.get(ele[0]).add(ele[1]);
        }
        for (int i = 0;i < numCourses;i++) {
            if (dfs(i, prerequisites, flag, adjacencyList) == false) {
                return false;
            }
        }
        return true;
    }

    public boolean dfs(int i, int[][] prerequisites, int[] flag, List<List<Integer>> adjacencyList) {
        if (flag[i] == 1) {
            return false;
        }
        if (flag[i] == 2) {
            return true;
        }
        flag[i] = 1;
        //递归遍历与i相关的节点
        for (Integer j: adjacencyList.get(i)) {
            if (dfs(j, prerequisites, flag, adjacencyList) == false) {
                return false;
            }
        }
        //遍历完i相关的节点，没有返回false，表面i不存在环，将标志位设置为2
        flag[i] = 2;
        return true;
    }
}

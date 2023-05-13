package tools;

import java.util.Stack;

public class BreadthFirstPaths {
    private final boolean[] ifMarked; // Whether the path to the vertex is known
    private final int[] edgeTo; // The last point on the path to the target point
    private final int s; // starting point

    /**
     * This class is the implementation of breadth first search method. It finds the shortest path between them by giving the position of the starting point and the ending point. This allows ghosts to chase players, and can also create arbitrary player coordinates to achieve pseudo random movement of ghosts.
     * @param G Graph with all points and paths between them
     * @param s Starting point
     */
    public BreadthFirstPaths(Graph G, int s) {
        ifMarked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }

    /**
     * Get the shortest path from the starting point to all other points
     */
    private void bfs(Graph G, int s) {
        Queue<Integer> queue = new Queue<>();
        ifMarked[s] = true; // Mark start point
        queue.enqueue(s);
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : G.adj(v)) {
                if (!ifMarked[w]) {
                    edgeTo[w] = v;
                    ifMarked[w] = true;
                    queue.enqueue(w);
                }
            }
        }
    }

    private boolean hasPathTo(int v) {
        return ifMarked[v];
    }

    /**
     * Returns a path from the starting point to the target point. The path is stored in the form of a stack, where the top element is the next position the starting point should go to
     * @param v finishing point
     * @return A path in the form of stack
     */
    private Stack<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }

    /**
     *This is a method that will eventually be used directly by the game. It returns to the next point the ghost is going to. This avoids the possibility of ghosts crossing the wall (because the wall cannot be in the path).
     * @param v finishing point
     * @return Coordinates of the next point
     */
    public int getNextPoint(int v) {
        Stack<Integer> stack = pathTo(v);
        assert stack != null;
        if (stack.size() != 1) { // When the target point is the starting point, return to the starting point itself
            stack.pop();
        }
        return stack.pop(); // Otherwise, return to the point closest to the starting point on the path
    }
}


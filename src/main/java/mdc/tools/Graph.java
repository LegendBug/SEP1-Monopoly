package mdc.tools;

import java.util.List;

/**
 * This class contains all accessible points and paths between points of ghosts in this game, which are prepared for their pathfinding
 */
public class Graph {
    private final int V; // Number of points
    private int E; // Number of paths between points
    private final Bag<Integer>[] adj; // Contains information about all points and the adjacent points of each point

    public Graph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    public Graph(List<int[]> arr) {
        this(arr.size());
        for (int[] pos : arr) {
            int v = arr.indexOf(pos);
            for (int[] next_pos : arr) {
                // Detect the upper adjacent point
                if (next_pos[0] == pos[0] && next_pos[1] == pos[1] - 1) {
                    int w = arr.indexOf(next_pos);
                    addEdge(v, w);
                    break;
                }
            }
            for (int[] next_pos : arr) {
                // Detect the next adjacent point
                if (next_pos[0] == pos[0] && next_pos[1] == pos[1] + 1) {
                    int w = arr.indexOf(next_pos);
                    addEdge(v, w);
                }
            }
            for (int[] next_pos : arr) {
                // Detect the right adjacent point
                if (next_pos[0] == pos[0] + 1 && next_pos[1] == pos[1]) {
                    int w = arr.indexOf(next_pos);
                    addEdge(v, w);
                }
            }
            for (int[] next_pos : arr) {
                // Detect the left adjacent point
                if (next_pos[0] == pos[0] - 1 && next_pos[1] == pos[1]) {
                    int w = arr.indexOf(next_pos);
                    addEdge(v, w);
                }
            }
        }
    }

    public int V() {
        return V;
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }
}

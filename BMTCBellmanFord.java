import java.util.*;

class Edge {
    int source, destination, weight;

    Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
}

public class BMTCBellmanFord {

    static final int INF = Integer.MAX_VALUE;

    public static void bellmanFord(List<Edge> edges, int V, int source, String[] hubs) {

        int[] distance = new int[V];

        Arrays.fill(distance, INF);
        distance[source] = 0;

        // Relax all edges V-1 times
        for (int i = 1; i < V; i++) {
            for (Edge edge : edges) {

                if (distance[edge.source] != INF &&
                    distance[edge.source] + edge.weight < distance[edge.destination]) {

                    distance[edge.destination] =
                            distance[edge.source] + edge.weight;
                }
            }
        }

        // Check for Negative Weight Cycle
        for (Edge edge : edges) {

            if (distance[edge.source] != INF &&
                distance[edge.source] + edge.weight < distance[edge.destination]) {

                System.out.println("Negative Weight Cycle Detected!");
                return;
            }
        }

        System.out.println("Shortest Travel Time from MJC:\n");

        for (int i = 0; i < V; i++) {
            System.out.println(hubs[i] + " : " + distance[i] + " minutes");
        }
    }

    public static void main(String[] args) {

        String[] hubs = {
                "MJC",
                "KEM",
                "JAY",
                "KOR",
                "WHF",
                "HBR",
                "MRT"
        };

        int V = hubs.length;

        List<Edge> edges = new ArrayList<>();

        // Graph Edges

        edges.add(new Edge(0,1,8));   // MJC -> KEM
        edges.add(new Edge(0,2,5));   // MJC -> JAY
        edges.add(new Edge(0,3,12));  // MJC -> KOR

        edges.add(new Edge(1,5,7));   // KEM -> HBR
        edges.add(new Edge(1,4,10));  // KEM -> WHF

        edges.add(new Edge(3,4,6));   // KOR -> WHF
        edges.add(new Edge(3,6,9));   // KOR -> MRT

        edges.add(new Edge(4,6,-3));  // WHF -> MRT (Negative Edge)

        edges.add(new Edge(5,4,2));   // HBR -> WHF
        edges.add(new Edge(5,6,11));  // HBR -> MRT

        bellmanFord(edges, V, 0, hubs);
    }
}
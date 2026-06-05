import java.util.*;

public class RetweetReachPrediction {

    static class Pair {
        String node;
        int depth;

        Pair(String node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }

    public static void main(String[] args) {

        Map<String, List<String>> graph = new HashMap<>();

        graph.put("A", Arrays.asList("B", "C"));
        graph.put("B", Arrays.asList("D", "E"));
        graph.put("C", Arrays.asList("E", "F"));
        graph.put("D", Arrays.asList("G"));
        graph.put("E", Arrays.asList("G", "H"));
        graph.put("F", Arrays.asList("H", "I"));
        graph.put("G", new ArrayList<>());
        graph.put("H", new ArrayList<>());
        graph.put("I", new ArrayList<>());

        Queue<Pair> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.offer(new Pair("A", 0));
        visited.add("A");

        int maxDepth = 3;

        System.out.println("=== X (TWITTER) RETWEET REACH PREDICTION ===\n");

        while (!queue.isEmpty()) {

            Pair current = queue.poll();

            System.out.println(
                    "Visited User: " + current.node +
                    " | Depth: " + current.depth);

            if (current.depth == maxDepth)
                continue;

            for (String neighbor : graph.get(current.node)) {

                if (!visited.contains(neighbor)) {

                    visited.add(neighbor);

                    queue.offer(
                            new Pair(
                                    neighbor,
                                    current.depth + 1
                            )
                    );

                    System.out.println(
                            "   Reach -> " + neighbor +
                            " (Depth " + (current.depth + 1) + ")"
                    );
                }
                else {

                    System.out.println(
                            "   Skipped Duplicate -> " +
                            neighbor
                    );
                }
            }
        }

        System.out.println("\n--------------------------------");

        System.out.println("Unique Users Reached:");

        for (String user : visited)
            System.out.print(user + " ");

        System.out.println("\n\nTotal Reach = "
                + visited.size());

        System.out.println("\nDepth Limit = 3");

        System.out.println("\nTime Complexity: O(V + E)");

        System.out.println(
                "\nVisited Set prevents duplicate counting of users."
        );
    }
}

import java.util.*;

public class CO3_CaseStudyTwitterReachBFS {

    static Set<String> bfsBounded(
            Map<String, List<String>> adj,
            String source,
            int maxDepth) {

        Set<String> visited = new HashSet<>();
        visited.add(source);

        Queue<Object[]> queue = new LinkedList<>();
        queue.offer(new Object[]{source, 0});

        Set<String> reached = new LinkedHashSet<>();

        System.out.println("\nBFS Queue Evolution");
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-10s %-8s %-20s %-25s%n",
                "Node",
                "Depth",
                "Discovered",
                "Visited Set");
        System.out.println("---------------------------------------------------------------");

        while (!queue.isEmpty()) {

            Object[] cur = queue.poll();

            String u = (String) cur[0];
            int depth = (int) cur[1];

            List<String> discovered = new ArrayList<>();

            if (depth < maxDepth) {

                List<String> neighbors =
                        adj.getOrDefault(u, new ArrayList<>());

                Collections.sort(neighbors);

                for (String v : neighbors) {

                    if (!visited.contains(v)) {

                        visited.add(v);
                        reached.add(v);

                        queue.offer(
                                new Object[]{v, depth + 1});

                        discovered.add(v);
                    }
                }
            }

            System.out.printf(
                    "%-10s %-8d %-20s %-25s%n",
                    u,
                    depth,
                    discovered,
                    visited
            );
        }

        return reached;
    }

    public static void main(String[] args) {

        Map<String, List<String>> graph =
                new HashMap<>();

        graph.put("A",
                Arrays.asList("B", "C"));

        graph.put("B",
                Arrays.asList("D", "E"));

        graph.put("C",
                Arrays.asList("E", "F"));

        graph.put("D",
                Arrays.asList("G"));

        graph.put("E",
                Arrays.asList("G", "H"));

        graph.put("F",
                Arrays.asList("H", "I"));

        graph.put("G",
                new ArrayList<>());

        graph.put("H",
                new ArrayList<>());

        graph.put("I",
                new ArrayList<>());

        System.out.println("====================================");
        System.out.println("X (Twitter) Reach Prediction using BFS");
        System.out.println("====================================");

        Set<String> reached =
                bfsBounded(graph, "A", 3);

        System.out.println("\n====================================");
        System.out.println("FINAL REACHED USERS");
        System.out.println("====================================");

        System.out.println(reached);

        System.out.println("\nMultiple In-Edge Nodes:");
        System.out.println("E reached first through B");
        System.out.println("G reached first through D");
        System.out.println("H reached first through E");

        System.out.println("\nVisited set prevents:");
        System.out.println("E from being counted twice");
        System.out.println("G from being counted twice");
        System.out.println("H from being counted twice");

        System.out.println("\n====================================");
        System.out.println("CAPACITY ANALYSIS");
        System.out.println("====================================");

        long depth1 = 50000;

        long depth2Candidates =
                depth1 * 500;

        long depth2Distinct =
                (long)(depth2Candidates * 0.7);

        long depth3Candidates =
                depth2Distinct * 500;

        long depth3Distinct =
                (long)(depth3Candidates * 0.7);

        long totalDistinct =
                1 +
                depth1 +
                depth2Distinct +
                depth3Distinct;

        System.out.println(
                "Estimated Distinct Reach ≤ Depth 3 = "
                        + totalDistinct);

        double workMs =
                (totalDistinct * 2.0) / 1000.0;

        System.out.println(
                "Estimated Work = "
                        + workMs
                        + " ms");

        if (workMs < 500)
            System.out.println(
                    "Within 500 ms Budget");
        else
            System.out.println(
                    "Exceeds 500 ms Budget");

        System.out.println(
                "\nProgram Executed Successfully");
    }
}
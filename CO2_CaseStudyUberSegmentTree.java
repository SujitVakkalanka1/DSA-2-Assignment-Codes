public class CO2_CaseStudyUberSegmentTree {

    static class SegTreeLazy {

        double[] tree;
        double[] lazy;
        int n;

        SegTreeLazy(int n) {
            this.n = n;
            tree = new double[4 * n];
            lazy = new double[4 * n];
        }

        void build(int node, int start, int end, double[] arr) {

            if (start == end) {
                tree[node] = arr[start];
                return;
            }

            int mid = (start + end) / 2;

            build(node * 2, start, mid, arr);
            build(node * 2 + 1, mid + 1, end, arr);

            tree[node] = Math.max(tree[node * 2],
                                  tree[node * 2 + 1]);
        }

        void pushDown(int node) {

            if (lazy[node] != 0) {

                tree[node * 2] += lazy[node];
                lazy[node * 2] += lazy[node];

                tree[node * 2 + 1] += lazy[node];
                lazy[node * 2 + 1] += lazy[node];

                lazy[node] = 0;
            }
        }

        void updateRange(
                int node,
                int lo,
                int hi,
                int l,
                int r,
                double delta) {

            if (r < lo || hi < l)
                return;

            if (l <= lo && hi <= r) {
                tree[node] += delta;
                lazy[node] += delta;
                return;
            }

            pushDown(node);

            int mid = (lo + hi) / 2;

            updateRange(node * 2,
                    lo,
                    mid,
                    l,
                    r,
                    delta);

            updateRange(node * 2 + 1,
                    mid + 1,
                    hi,
                    l,
                    r,
                    delta);

            tree[node] = Math.max(
                    tree[node * 2],
                    tree[node * 2 + 1]);
        }

        double queryMax(
                int node,
                int lo,
                int hi,
                int l,
                int r) {

            if (r < lo || hi < l)
                return Double.NEGATIVE_INFINITY;

            if (l <= lo && hi <= r)
                return tree[node];

            pushDown(node);

            int mid = (lo + hi) / 2;

            return Math.max(
                    queryMax(node * 2,
                            lo,
                            mid,
                            l,
                            r),
                    queryMax(node * 2 + 1,
                            mid + 1,
                            hi,
                            l,
                            r));
        }
    }

    static void printZones(double[] zones) {

        for (int i = 0; i < zones.length; i++) {

            System.out.printf(
                    "z%-2d = %.1f%n",
                    i,
                    zones[i]);
        }
    }

    public static void main(String[] args) {

        int n = 16;

        double[] zones = new double[n];

        for (int i = 0; i < n; i++)
            zones[i] = 1.0;

        SegTreeLazy st = new SegTreeLazy(n);

        st.build(1, 0, n - 1, zones);

        System.out.println("=================================");
        System.out.println("UBER BENGALURU SURGE SYSTEM");
        System.out.println("=================================");

        System.out.println("\nInitial Zone Multipliers");
        printZones(zones);

        // UPDATE 1
        System.out.println("\n=================================");
        System.out.println("UPDATE 1");
        System.out.println("update [3,9] += 0.5");
        System.out.println("=================================");

        st.updateRange(1, 0, 15, 3, 9, 0.5);

        for (int i = 3; i <= 9; i++)
            zones[i] += 0.5;

        printZones(zones);

        // UPDATE 2
        System.out.println("\n=================================");
        System.out.println("UPDATE 2");
        System.out.println("update [7,14] += 0.3");
        System.out.println("=================================");

        st.updateRange(1, 0, 15, 7, 14, 0.3);

        for (int i = 7; i <= 14; i++)
            zones[i] += 0.3;

        printZones(zones);

        // QUERY 1
        System.out.println("\n=================================");
        System.out.println("QUERY 1");
        System.out.println("max [0,15]");
        System.out.println("=================================");

        System.out.println(
                "Answer = " +
                st.queryMax(1, 0, 15, 0, 15));

        // UPDATE 3
        System.out.println("\n=================================");
        System.out.println("UPDATE 3");
        System.out.println("update [2,6] += 0.7");
        System.out.println("=================================");

        st.updateRange(1, 0, 15, 2, 6, 0.7);

        for (int i = 2; i <= 6; i++)
            zones[i] += 0.7;

        printZones(zones);

        // QUERY 2
        System.out.println("\n=================================");
        System.out.println("QUERY 2");
        System.out.println("max [4,10]");
        System.out.println("=================================");

        System.out.println(
                "Answer = " +
                st.queryMax(1, 0, 15, 4, 10));

        System.out.println("\n=================================");
        System.out.println("FINAL ZONE VALUES");
        System.out.println("=================================");

        printZones(zones);

        System.out.println(
                "\nProgram Executed Successfully");
    }
}
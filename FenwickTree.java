class FenwickTree {

    int[] bit;
    int n;

    FenwickTree(int n) {
        this.n = n;
        bit = new int[n + 1];
    }

    void update(int index, int value) {
        while (index <= n) {
            bit[index] += value;
            index += index & (-index);
        }
    }

    int prefixSum(int index) {
        int sum = 0;

        while (index > 0) {
            sum += bit[index];
            index -= index & (-index);
        }

        return sum;
    }

    int rangeSum(int left, int right) {
        return prefixSum(right) - prefixSum(left - 1);
    }

    void printBIT() {
        for (int i = 1; i <= n; i++) {
            System.out.print(bit[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {

        int[] spend = {
                0,
                1200, 800, 0, 2400, 1500,
                600, 0, 0, 3500, 0,
                1100, 950, 700, 0
        };

        int n = 14;

        FenwickTree ft = new FenwickTree(n);

        System.out.println("=== HDFC NETBANKING DAILY SPEND ANALYSIS ===");

        System.out.println("\nInput Spend Array:");

        for (int i = 1; i <= n; i++) {
            System.out.print(spend[i] + " ");
            ft.update(i, spend[i]);
        }

        System.out.println("\n\nBIT Array:");

        ft.printBIT();

        int startDay = 5;
        int endDay = 12;

        int prefix12 = ft.prefixSum(12);
        int prefix4 = ft.prefixSum(4);

        int answer = ft.rangeSum(startDay, endDay);

        System.out.println("\nRange Query:");
        System.out.println("Spend from Day "
                + startDay + " to Day "
                + endDay);

        System.out.println("\nPrefix(12) = "
                + prefix12);

        System.out.println("Prefix(4) = "
                + prefix4);

        System.out.println("\nTotal Spend = "
                + prefix12 + " - "
                + prefix4);

        System.out.println("Answer = ₹"
                + answer);

        System.out.println("\nVerification:");

        int manual =
                1500 + 600 + 0 + 0 +
                3500 + 0 + 1100 +
                950;

        System.out.println("Manual Sum = ₹"
                + manual);

        System.out.println("\nTime Complexity:");
        System.out.println("Point Update : O(log n)");
        System.out.println("Range Query  : O(log n)");
    }
}

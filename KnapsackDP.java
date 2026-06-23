public class KnapsackDP {

    public static void main(String[] args) {

        int[] weight = {5, 8, 3, 10, 4, 6, 7, 2};
        int[] value =  {40,50,20,70,30,35,45,15};

        String[] items = {"A","B","C","D","E","F","G","H"};

        int n = weight.length;
        int capacity = 24;

        int[][] dp = new int[n + 1][capacity + 1];

        // Build DP Table
        for (int i = 1; i <= n; i++) {

            for (int w = 0; w <= capacity; w++) {

                if (weight[i - 1] <= w) {

                    dp[i][w] = Math.max(
                            dp[i - 1][w],
                            value[i - 1] + dp[i - 1][w - weight[i - 1]]
                    );

                } else {

                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        System.out.println("Maximum Payment Value = ₹" + dp[n][capacity] + " thousand");

        System.out.println("\nSelected Consignments:");

        int w = capacity;

        for (int i = n; i > 0; i--) {

            if (dp[i][w] != dp[i - 1][w]) {

                System.out.println(
                        items[i - 1] +
                        " (Weight = " + weight[i - 1] +
                        ", Value = ₹" + value[i - 1] + "k)"
                );

                w -= weight[i - 1];
            }
        }
    }
}
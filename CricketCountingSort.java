
class Delivery {
    int over;
    int ball;

    Delivery(int over, int ball) {
        this.over = over;
        this.ball = ball;
    }

    @Override
    public String toString() {
        return "(" + over + "," + ball + ")";
    }
}

public class CricketCountingSort {

    // Stable Counting Sort by Ball Number
    static void sortByBall(Delivery[] deliveries) {

        int maxBall = 12;

        int[] count = new int[maxBall + 1];
        Delivery[] output = new Delivery[deliveries.length];

        for (Delivery d : deliveries)
            count[d.ball]++;

        for (int i = 1; i <= maxBall; i++)
            count[i] += count[i - 1];

        for (int i = deliveries.length - 1; i >= 0; i--) {
            output[count[deliveries[i].ball] - 1] = deliveries[i];
            count[deliveries[i].ball]--;
        }

        System.arraycopy(output, 0, deliveries, 0, deliveries.length);
    }

    // Stable Counting Sort by Over Number
    static void sortByOver(Delivery[] deliveries) {

        int maxOver = 49;

        int[] count = new int[maxOver + 1];
        Delivery[] output = new Delivery[deliveries.length];

        for (Delivery d : deliveries)
            count[d.over]++;

        for (int i = 1; i <= maxOver; i++)
            count[i] += count[i - 1];

        for (int i = deliveries.length - 1; i >= 0; i--) {
            output[count[deliveries[i].over] - 1] = deliveries[i];
            count[deliveries[i].over]--;
        }

        System.arraycopy(output, 0, deliveries, 0, deliveries.length);
    }

    public static void main(String[] args) {

        Delivery[] deliveries = {
            new Delivery(2,4),
            new Delivery(1,1),
            new Delivery(3,6),
            new Delivery(1,5),
            new Delivery(2,2),
            new Delivery(3,1),
            new Delivery(1,3),
            new Delivery(2,6),
            new Delivery(3,4),
            new Delivery(1,2)
        };

        // Sort by secondary key first
        sortByBall(deliveries);

        // Then sort by primary key
        sortByOver(deliveries);

        System.out.println("Sorted Deliveries:");

        for (Delivery d : deliveries)
            System.out.print(d + " ");
    }
}
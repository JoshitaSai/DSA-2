import java.util.HashMap;

class Order {
    int orderId;
    int price;

    Order(int orderId, int price) {
        this.orderId = orderId;
        this.price = price;
    }
}

class BSTNode {
    Order order;
    BSTNode left, right;

    BSTNode(Order order) {
        this.order = order;
    }
}

public class NSEOrderBook {

    BSTNode root;

    // Key fix 1: orderId -> price map, used for O(1) lookup during cancel
    HashMap<Integer, Integer> orderMap = new HashMap<>();

    // Key fix 2: BST is now keyed on PRICE (correct for order book).
    // Duplicate prices are handled by a secondary sort on orderId.
    BSTNode insert(BSTNode node, Order order) {
        if (node == null)
            return new BSTNode(order);

        if (order.price < node.order.price)
            node.left = insert(node.left, order);
        else if (order.price > node.order.price)
            node.right = insert(node.right, order);
        else {
            // Key fix 3: equal price -> use orderId as tiebreaker
            // so no order is silently lost
            if (order.orderId < node.order.orderId)
                node.left = insert(node.left, order);
            else
                node.right = insert(node.right, order);
        }

        return node;
    }

    BSTNode findMax(BSTNode node) {
        while (node.right != null)
            node = node.right;
        return node;
    }

    // Key fix 4: delete now takes BOTH price and orderId to uniquely
    // identify the node when duplicate prices exist
    BSTNode delete(BSTNode node, int price, int orderId) {
        if (node == null)
            return null;

        if (price < node.order.price)
            node.left = delete(node.left, price, orderId);
        else if (price > node.order.price)
            node.right = delete(node.right, price, orderId);
        else {
            // Same price — use orderId to find the exact node
            if (orderId < node.order.orderId)
                node.left = delete(node.left, price, orderId);
            else if (orderId > node.order.orderId)
                node.right = delete(node.right, price, orderId);
            else {
                // Exact match found — standard BST deletion
                if (node.left == null)
                    return node.right;
                if (node.right == null)
                    return node.left;

                BSTNode successor = findMax(node.left);
                node.order = successor.order;
                node.left = delete(node.left,
                        successor.order.price,
                        successor.order.orderId);
            }
        }

        return node;
    }

    void insertOrder(int orderId, int price) {
        Order order = new Order(orderId, price);
        root = insert(root, order);
        orderMap.put(orderId, price);
        System.out.println("Inserted Order #" + orderId + " @ ₹" + price);
    }

    void deleteByOrderId(int orderId) {
        if (!orderMap.containsKey(orderId)) {
            System.out.println("Order #" + orderId + " not found");
            return;
        }

        int price = orderMap.get(orderId);
        // Key fix 5: pass both price AND orderId to delete the right node
        root = delete(root, price, orderId);
        orderMap.remove(orderId);

        System.out.println("Cancelled Order #" + orderId + " @ ₹" + price);
    }

    // Key fix 6: bestBid always computed fresh — no stale pointer bugs
    int peekBestBid() {
        if (root == null)
            return -1;
        return findMax(root).order.price;
    }

    void descending(BSTNode node) {
        if (node != null) {
            descending(node.right);
            System.out.println(
                    "Order #" + node.order.orderId +
                            " -> ₹" + node.order.price);
            descending(node.left);
        }
    }

    public static void main(String[] args) {

        NSEOrderBook book = new NSEOrderBook();

        int bestAsk = 2988;

        int[][] orders = {
                {101, 2980},
                {102, 2965},
                {103, 2992},
                {104, 2985},
                {105, 2970},
                {106, 2998},
                {107, 2978},
                {108, 2988},
                {109, 2982},
                {110, 2995},
                {111, 2972},
                {112, 2990},
                {113, 2986},
                {114, 2975}
        };

        System.out.println("=== NSE CASH MARKET ORDER BOOK ===");
        System.out.println("Best Ask = ₹" + bestAsk);
        System.out.println("\nProcessing Orders:");

        for (int[] o : orders) {
            int orderId = o[0];
            int price = o[1];

            if (price >= bestAsk) {
                System.out.println(
                        "Order #" + orderId +
                                " @ ₹" + price +
                                " MATCHED immediately and removed");
            } else {
                book.insertOrder(orderId, price);
            }
        }

        System.out.println("\nCurrent Buy Book (Highest Bid First)");
        book.descending(book.root);

        System.out.println("\nBest Bid = ₹" + book.peekBestBid());

        System.out.println("\nCancelling Order #107");
        book.deleteByOrderId(107);

        System.out.println("\nBuy Book After Cancellation");
        book.descending(book.root);

        System.out.println("\nBest Bid = ₹" + book.peekBestBid());

        System.out.println("\nComplexities");
        System.out.println("Insert              : O(log n)");
        System.out.println("peek_best_bid()     : O(log n)");
        System.out.println("delete_by_order_id(): O(log n)");
    }
}

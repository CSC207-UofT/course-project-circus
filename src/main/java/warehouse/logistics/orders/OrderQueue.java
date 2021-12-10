package warehouse.logistics.orders;

import java.util.*;

/**
 * A queue of Orders.
 */
public class OrderQueue {
    private final Queue<Order> orderQueue;

    /**
     * Construct an OrderQueue.
     * @param orderComparator The comparator to use when comparing Orders.
     */
    public OrderQueue(Comparator<Order> orderComparator) {
        // Create a new comparator that encodes the "readiness" of an order.
        // This way, the priority queue will give the oldest READY Order in the queue, or a non-ready order,
        // in which case we don't have to do anything.
        orderQueue = new PriorityQueue<>((o1, o2) -> {
            // We want to make sure that all ready orders are BEFORE unready orders.
            boolean ready1 = o1.isReady();
            boolean ready2 = o2.isReady();
            if ((ready1 && ready2) || (!ready1 && !ready2)) {
                return orderComparator.compare(o1, o2);
            } else if (ready1) {
                // o1 is ready and o2 is unready, so o1 < o2
                return -1;
            } else {
                // o1 is not ready and o2 is ready, so o1 > o2
                return 1;
            }
        });
    }

    /**
     * Construct an OrderQueue with a OrderCreatedAtComparator.
     */
    public OrderQueue() {
        this(new OrderCreatedAtComparator());
    }

    /**
     * An Order to this OrderQueue
     * @param order the Order to add.
     */
    public void add(Order order) {
        orderQueue.add(order);
    }

    /**
     * Remove and return the next Order in this OrderQueue that can be processed.
     * @remark Since the ready status of an Order might have changed since we last updated the priority queue,
     * this operation requires rebuilding the priority queue used to store orders, which is an O(nlog(n)) operation.
     * @return the next processable Order in this queue, or null if the queue is empty or there are no such orders.
     */
    public Order getNextOrder() {
        return getNextOrder(true);
    }

    /**
     * Remove and return the next Order in this OrderQueue that can be processed.
     * @param rebuild Whether to rebuild the priority queue. Note that if an order ready status changed and the priority
     *                queue isn't rebuilt, then the return value of this method may not be accurate.
     * @return the next processable Order in this queue, or null if the queue is empty or there are no such orders.
     */
    public Order getNextOrder(boolean rebuild) {
        if (rebuild) {
            rebuild();
        }

        Order order = orderQueue.peek();
        if (order == null || !order.isReady()) {
            // There are no ready orders yet, so return null.
            return null;
        } else {
            orderQueue.poll();
            return order;
        }
    }

    /**
     * Rebuild the priority queue representing the orders.
     */
    public void rebuild() {
        List<Order> orders = new ArrayList<>();
        while (!orderQueue.isEmpty()) {
            orders.add(orderQueue.remove());
        }
        orderQueue.addAll(orders);
    }

    /**
     * CLear this OrderQueue.
     */
    public void clear() {
        orderQueue.clear();
    }

    /**
     * Returns the number of orders in this OrderQueue.
     */
    public int size() {
        return orderQueue.size();
    }

    /**
     * Returns whether this OrderQueue is empty.
     */
    public boolean isEmpty() {
        return orderQueue.isEmpty();
    }

    public List<Order> peekOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        for (Object obj : orderQueue.toArray()) {
            orders.add((Order) obj);
        }
        return orders;
    }
}

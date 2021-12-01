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
     * Get the next Order in this OrderQueue that can be processed.
     * @return the next processable Order in this queue, or null if the queue is empty or there are no such orders.
     */
    public Order getNextOrder() {
        Order order = orderQueue.poll();
        if (order == null || !order.isReady()) {
            // There are no ready orders yet, so return null.
            return null;
        } else {
            return order;
        }
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
}

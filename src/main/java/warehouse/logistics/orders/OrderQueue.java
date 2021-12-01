package warehouse.logistics.orders;

import warehouse.Warehouse;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * A queue of Orders.
 */
public class OrderQueue {
    private final Warehouse warehouse;
    private final Queue<Order> orderQueue;

    /**
     * Construct an OrderQueue given a Warehouse.
     * @param warehouse The warehouse to execute Orders in.
     * @param orderComparator The comparator to use when comparing Orders.
     */
    public OrderQueue(Warehouse warehouse, Comparator<Order> orderComparator) {
        this.warehouse = warehouse;
        orderQueue = new PriorityQueue<>(orderComparator);
    }

    /**
     * Construct an OrderQueue given a Warehouse with a OrderCreatedAtComparator.
     * @param warehouse The warehouse to execute Orders in.
     */
    public OrderQueue(Warehouse warehouse) {
        this(warehouse, new OrderCreatedAtComparator());
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
     * @return the next processable Order in this queue.
     */
//    public Order getNextOrder() {
//        while (!orderQueue.isEmpty()) {
//            Order order = orderQueue.remove();
//            return order;
//        }
//        return null;
//    }
}

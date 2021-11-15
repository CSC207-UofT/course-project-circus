package orders;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Current Warehouse order queue.
 */
public class OrderQueue {
    private Queue<Order> orderQueue;

    /**
     * Construct an empty Order Queue.
     */
    public OrderQueue() {
        this.orderQueue = new LinkedList<>();
    }

    /**
     * Remove Order from OrderQueue once it is completed and
     * update the order's status.
     */
    public void completeOrder() {
        Order completedOrder = orderQueue.poll();
        assert completedOrder != null;
        completedOrder.updateStatus();
    }

    /**
     * Add specified Order to the OrderQueue.
     * @param order New Order to be added.
     */
    public void addOrder(Order order) {
        orderQueue.add(order);
    }
}

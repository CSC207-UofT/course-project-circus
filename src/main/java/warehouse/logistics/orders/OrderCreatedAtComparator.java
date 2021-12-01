package warehouse.logistics.orders;

import java.util.Comparator;

/**
 * A comparator that compares Orders by the time they were created at.
 */
public class OrderCreatedAtComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return o1.getCreatedAt().compareTo(o2.getCreatedAt());
    }
}

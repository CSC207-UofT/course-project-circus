package warehouse.logistics.orders;

import warehouse.robots.Robot;

import java.util.List;

public class WrappedOrderHandlerSelectionPolicy<T extends Order> {
    private final Class<T> clazz;
    private final OrderHandlerSelectionPolicy<T> selectionPolicy;

    public WrappedOrderHandlerSelectionPolicy(Class<T> clazz, OrderHandlerSelectionPolicy<T> selectionPolicy) {
        this.clazz = clazz;
        this.selectionPolicy = selectionPolicy;
    }

    /**
     * Select a Robot for the given Order.
     * @param robots The Robots to select from.
     * @param order The Order to find a handler for.
     * @return the Robot that will handle the given Order, or null if no Robot could be selected.
     */
    public Robot select(List<Robot> robots, Order order) {
        if (clazz.isAssignableFrom(order.getClass())) {
            return selectionPolicy.select(robots, clazz.cast(order));
        } else {
            throw new IllegalArgumentException(String.format("Invalid Order type! Expected %s but got %s",
                    clazz.getSimpleName(), order.getClass().getSimpleName()));
        }
    }
}

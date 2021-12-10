package warehouse.logistics.orders;

import warehouse.robots.Robot;

import java.util.List;

/**
 * A policy for selecting order handles.
 */
public interface OrderHandlerSelectionPolicy<T extends Order> {
    /**
     * Select a Robot for the given Order.
     * @param robots The Robots to select from.
     * @param order The Order to find a handler for.
     * @return the Robot that will handle the given Order, or null if no Robot could be selected.
     */
    Robot select(List<Robot> robots, T order);
}

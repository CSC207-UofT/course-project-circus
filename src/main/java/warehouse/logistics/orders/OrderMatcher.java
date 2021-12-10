package warehouse.logistics.orders;

import warehouse.robots.Robot;
import warehouse.robots.RobotMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Matches Orders to available Robots.
 */
public class OrderMatcher {
    private final OrderQueue orderQueue;
    private final RobotMapper<?> robotMapper;
    private final HashMap<Class<?>, WrappedOrderHandlerSelectionPolicy<?>> selectionPolicies;

    /**
     * Construct an OrderMatcher.
     * @param orderQueue The OrderQueue to read from.
     * @param robotMapper The robot mapper.
     */
    public OrderMatcher(OrderQueue orderQueue, RobotMapper<?> robotMapper) {
        this.orderQueue = orderQueue;
        this.robotMapper = robotMapper;
        this.selectionPolicies = new HashMap<>();
    }

    /**
     * Perform (synchronous) order-robot matching for this time step.
     */
    public void match() {
        List<Robot> availableRobots = getAvailableRobots();
        while (!orderQueue.isEmpty() && availableRobots.size() > 0) {
            Order order = orderQueue.getNextOrder();
            if (order == null) {
                // A null next order means that there are no more ready orders, so we are done.
                break;
            }
            Class<? extends Order> clazz = order.getClass();
            Robot selectedRobot = availableRobots.get(0);
            if (selectionPolicies.containsKey(clazz)) {
                selectedRobot = selectionPolicies.get(clazz).select(availableRobots, order);
            }
            if (selectedRobot == null) {
                // We could not select a Robot!
                orderQueue.add(order);
            } else {
                order.assign(selectedRobot);
                availableRobots.remove(selectedRobot);
            }
        }
    }

    /**
     * Return the available robots.
     */
    private List<Robot> getAvailableRobots() {
        List<Robot> robots = new ArrayList<>();
        for (Robot robot : robotMapper.getRobots()) {
            if (!robot.getIsBusy()) {
                robots.add(robot);
            }
        }
        return robots;
    }

    /**
     * Add a selection policy for an order type.
     */
    public <T extends Order> void addSelectionPolicy(Class<T> clazz, OrderHandlerSelectionPolicy<T> selectionPolicy) {
        selectionPolicies.put(clazz, new WrappedOrderHandlerSelectionPolicy<>(clazz, selectionPolicy));
    }
}

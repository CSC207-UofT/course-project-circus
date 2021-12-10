package warehouse.logistics.orders;

/**
 * The status of an Order.
 */
public enum OrderStatus {
    /**
     * The Order is waiting to be assigned.
     */
    PENDING,
    /**
     * The Order has been assigned to a Robot, but hasn't been started yet.
     */
    ASSIGNED,
    /**
     * The Order is in progress.
     */
    IN_PROGRESS,
    /**
     * The Order is complete.
     */
    COMPLETE
}

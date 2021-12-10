package warehouse.logistics.orders;

/**
 * A custom order used for testing.
 */
public class CustomOrder extends Order {
    private boolean isReady = false;

    /**
     * Sets the "readiness" of this Order. This is used in the getNextOrder test to simulate the readiness
     * of an Order changing dynamically.
     */
    public void setReady(boolean isReady) {
        this.isReady = isReady;
    }

    @Override
    public boolean isReady() {
        return isReady;
    }
}

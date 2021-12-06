package warehouse;

import utils.RandomUtils;

/**
 * An agent in the warehouse.
 */
public class Robot {
    private String id;
    private int x;
    private int y;

    /**
     * Construct a Robot with the given id at the given coordinates.
     * @param id The id of the Robot.
     * @param x The horizontal coordinate of the Robot in the warehouse.
     * @param y The vertical coordinate of the Robot in the warehouse.
     */
    public Robot(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    /**
     * Construct a Robot with a random id located at the given coordinates.
     * @param x The horizontal coordinate of the Robot in the warehouse.
     * @param y The vertical coordinate of the Robot in the warehouse.
     */
    public Robot (int x, int y) {
        this(RandomUtils.randomId(), x, y);
    }

    /**
     * Get this Robot's id.
     */
    public String getId() {
        return id;
    }

    /**
     * Set this Robot's id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the horizontal coordinate of this Robot in the warehouse.
     */
    public int getX() {
        return x;
    }

    /**
     * Get the vertical coordinate of this Robot in the warehouse.
     */
    public int getY() {
        return y;
    }
}

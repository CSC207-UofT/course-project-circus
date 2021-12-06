package warehouse.robots;

import utils.RandomUtils;

import java.util.Objects;

/**
 * An agent in the warehouse.
 */
public class Robot {
    private String id;

    /**
     * Construct a Robot with the given id at the given coordinates.
     * @param id The id of the Robot.
     */
    public Robot(String id) {
        this.id = id;
    }

    /**
     * Construct a Robot with a random id located at the given coordinates.
     */
    public Robot () {
        this(RandomUtils.randomId());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Robot robot = (Robot) o;
        return Objects.equals(id, robot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
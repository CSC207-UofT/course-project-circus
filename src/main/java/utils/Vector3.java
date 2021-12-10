package utils;

/**
 * A vector in 3-dimensional space.
 */
public class Vector3 {
    private float x;
    private float y;
    private float z;

    /**
     * Construct a Vector3 with the given components.
     */
    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}

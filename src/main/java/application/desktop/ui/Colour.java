package application.desktop.ui;

import imgui.ImGui;
import imgui.ImVec4;

/**
 * An RGB colour with optional transparency.
 */
public class Colour {
    public static final Colour WHITE = new Colour(1.0f, 1.0f, 1.0f);
    public static final Colour BLACK = new Colour(0, 0, 0);

    private float r;
    private float g;
    private float b;
    private float a;

    /**
     * Construct a Colour given fractional RGBA values (0 to 1).
     * @param r The red component of the colour.
     * @param g The green component of the colour.
     * @param b The blue component of the colour.
     * @param a The alpha component of the colour (0 means transparent, and 1 means opaque).
     */
    public Colour(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    /**
     * Construct a Colour given fractional RGB values (0 to 1).
     * @param r The red component of the colour.
     * @param g The green component of the colour.
     * @param b The blue component of the colour.
     */
    public Colour(float r, float g, float b) {
        this(r, g, b, 1);
    }

    /**
     * Construct a Colour given integer RGBA values (0 to 255).
     * @param r The red component of the colour.
     * @param g The green component of the colour.
     * @param b The blue component of the colour.
     * @param a The alpha component of the colour (0 means transparent, and 255 means opaque).
     */
    public Colour(int r, int g, int b, int a) {
        this(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
    }

    /**
     * Construct a Colour given integer RGB values (0 to 255) and a fractional alpha.
     * @param r The red component of the colour.
     * @param g The green component of the colour.
     * @param b The blue component of the colour.
     * @param a The alpha component of the colour (0 means transparent, and 1 means opaque).
     */
    public Colour(int r, int g, int b, float a) {
        this(r / 255.0f, g / 255.0f, b / 255.0f, a);
    }

    /**
     * Construct a Colour given integer RGB values (0 to 255).
     * @param r The red component of the colour.
     * @param g The green component of the colour.
     * @param b The blue component of the colour.
     */
    public Colour(int r, int g, int b) {
        this(r, g, b, 255);
    }

    /**
     * Copy constructor for the Colour class.
     * @param colour The colour to copy.
     */
    public Colour(Colour colour) {
        this(colour.getR(), colour.getG(), colour.getB(), colour.getA());
    }

    /**
     * Copy constructor for the Colour class with alpha overwrite.
     * @param colour The colour to copy.
     * @param a The alpha value of the copied colour.
     */
    public Colour(Colour colour, float a) {
        this(colour);
        this.a = a;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    /**
     * Convert this Colour to its unsigned 32-bit integer (U32) representation.
     * @remark This is used by ImGui during rendering.
     */
    public int toU32Colour() {

        return ImGui.getColorU32(r, g, b, a);
    }
}

package application.desktop.ui.components;

import imgui.ImGui;
import imgui.ImVec4;

/**
 * Colour scheme of the WarehouseCanvas.
 */
public class WarehouseCanvasColourScheme {
    /**
     * Default border colour of the canvas.
     */
    private static final ImVec4 DEFAULT_BORDER_COLOUR = new ImVec4(1.0f,
            1.0f,
            1.0f,
            1.0f);

    /**
     * Default background colour of the canvas.
     */
    private static final ImVec4 DEFAULT_BACKGROUND_COLOUR = new ImVec4(0.19607843f,
            0.19607843f,
            0.19607843f,
            1.0f);

    /**
     * Default colour of the grid lines.
     */
    private static final ImVec4 DEFAULT_GRID_LINE_COLOUR = new ImVec4(0.78431374f,
            0.78431374f,
            0.78431374f,
            0.15686275f);

    /**
     * Default colour scheme for the WarehouseCanvas.
     */
    public static final WarehouseCanvasColourScheme DEFAULT = new WarehouseCanvasColourScheme(
            DEFAULT_BORDER_COLOUR, DEFAULT_BACKGROUND_COLOUR, DEFAULT_GRID_LINE_COLOUR);

    private ImVec4 borderColour;
    private ImVec4 backgroundColour;
    private ImVec4 gridLineColour;

    /**
     * Construct a new WarehouseCanvas with a custom colour scheme.
     * @param borderColour The colour of the canvas border, given as a float RGBA vector.
     * @param backgroundColour The colour of the canvas background, given as a float RGBA vector.
     * @param gridLineColour The colour of the canvas grid lines, given as a float RGBA vector.
     */
    public WarehouseCanvasColourScheme(ImVec4 borderColour, ImVec4 backgroundColour, ImVec4 gridLineColour) {
        this.borderColour = borderColour;
        this.backgroundColour = backgroundColour;
        this.gridLineColour = gridLineColour;
    }

    public ImVec4 getBorderColour() {
        return borderColour;
    }

    public void setBorderColour(ImVec4 borderColour) {
        this.borderColour = borderColour;
    }

    public ImVec4 getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(ImVec4 backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    public ImVec4 getGridLineColour() {
        return gridLineColour;
    }

    public void setGridLineColour(ImVec4 gridLineColour) {
        this.gridLineColour = gridLineColour;
    }

    /**
     * Convert an RGBA float vector to its unsigned 32-bit integer (U32) representation.
     * This is used by ImGui during rendering.
     * @param colour The colour to convert.
     */
    public static int toU32Colour(ImVec4 colour) {
        return ImGui.getColorU32(colour.x, colour.y, colour.z, colour.w);
    }
}

package application.desktop.ui.components.editor;

import application.desktop.ui.Colour;

/**
 * Colour scheme of the WarehouseCanvas.
 */
public class WarehouseCanvasColourScheme {
    /**
     * Default border colour of the canvas.
     */
    private static final Colour DEFAULT_BORDER_COLOUR = Colour.WHITE;

    /**
     * Default background colour of the canvas.
     */
    private static final Colour DEFAULT_BACKGROUND_COLOUR = new Colour(50, 50, 50);

    /**
     * Default colour of the grid lines.
     */
    private static final Colour DEFAULT_GRID_LINE_COLOUR = new Colour(200,  200, 200, 40);

    /**
     * Default colour scheme for the WarehouseCanvas.
     */
    public static final WarehouseCanvasColourScheme DEFAULT = new WarehouseCanvasColourScheme(
            DEFAULT_BORDER_COLOUR, DEFAULT_BACKGROUND_COLOUR, DEFAULT_GRID_LINE_COLOUR);

    private Colour borderColour;
    private Colour backgroundColour;
    private Colour gridLineColour;

    /**
     * Construct a new WarehouseCanvas with a custom colour scheme.
     * @param borderColour The colour of the canvas border.
     * @param backgroundColour The colour of the canvas background.
     * @param gridLineColour The colour of the canvas grid lines.
     */
    public WarehouseCanvasColourScheme(Colour borderColour, Colour backgroundColour, Colour gridLineColour) {
        this.borderColour = borderColour;
        this.backgroundColour = backgroundColour;
        this.gridLineColour = gridLineColour;
    }

    public Colour getBorderColour() {
        return borderColour;
    }

    public void setBorderColour(Colour borderColour) {
        this.borderColour = borderColour;
    }

    public Colour getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(Colour backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    public Colour getGridLineColour() {
        return gridLineColour;
    }

    public void setGridLineColour(Colour gridLineColour) {
        this.gridLineColour = gridLineColour;
    }

}

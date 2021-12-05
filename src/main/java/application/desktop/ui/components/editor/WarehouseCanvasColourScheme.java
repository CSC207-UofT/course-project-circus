package application.desktop.ui.components.editor;

import application.desktop.ui.Colour;

/**
 * Colour scheme of the WarehouseCanvas.
 */
public class WarehouseCanvasColourScheme {
    /**
     * Default border colour of the canvas.
     */
    private static final Colour DEFAULT_BORDER_COLOUR = new Colour(99, 99, 99);

    /**
     * Default background colour of the canvas.
     */
    private static final Colour DEFAULT_BACKGROUND_COLOUR = new Colour(50, 50, 50);

    /**
     * Default colour of the grid lines.
     */
    private static final Colour DEFAULT_GRID_LINE_COLOUR = new Colour(200,  200, 200, 40);

    /**
     * Default selection outline colour.
     */
    private static final Colour DEFAULT_SELECTION_OUTLINE_COLOUR = new Colour(177, 255, 101);
    /**
     * Default selection outline colour.
     */
    private static final Colour DEFAULT_MOVE_OUTLINE_COLOUR = new Colour(255, 88, 255);


    /**
     * Default colour scheme for the WarehouseCanvas.
     */
    public static final WarehouseCanvasColourScheme DEFAULT = new WarehouseCanvasColourScheme(
            DEFAULT_BORDER_COLOUR, DEFAULT_BACKGROUND_COLOUR, DEFAULT_GRID_LINE_COLOUR,
            DEFAULT_SELECTION_OUTLINE_COLOUR, DEFAULT_MOVE_OUTLINE_COLOUR);

    private Colour borderColour;
    private Colour backgroundColour;
    private Colour gridLineColour;
    private Colour selectionOutlineColour;
    private Colour moveOutlineColour;

    /**
     * Construct a new WarehouseCanvas with a custom colour scheme.
     * @param borderColour The colour of the canvas border.
     * @param backgroundColour The colour of the canvas background.
     * @param gridLineColour The colour of the canvas grid lines.
     */
    public WarehouseCanvasColourScheme(Colour borderColour, Colour backgroundColour,
                                       Colour gridLineColour,
                                       Colour selectionOutlineColour, Colour moveOutlineColour) {
        this.borderColour = borderColour;
        this.backgroundColour = backgroundColour;
        this.gridLineColour = gridLineColour;
        this.selectionOutlineColour = selectionOutlineColour;
        this.moveOutlineColour = moveOutlineColour;
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

    public Colour getSelectionOutlineColour() {
        return selectionOutlineColour;
    }

    public void setSelectionOutlineColour(Colour selectionOutlineColour) {
        this.selectionOutlineColour = selectionOutlineColour;
    }

    public Colour getMoveOutlineColour() {
        return moveOutlineColour;
    }

    public void setMoveOutlineColour(Colour moveOutlineColour) {
        this.moveOutlineColour = moveOutlineColour;
    }
}

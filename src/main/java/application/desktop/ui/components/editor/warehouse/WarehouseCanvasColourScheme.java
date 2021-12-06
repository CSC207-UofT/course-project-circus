package application.desktop.ui.components.editor.warehouse;

import application.desktop.ui.Colour;
import application.desktop.ui.FontAwesomeIcon;
import warehouse.tiles.Rack;
import warehouse.tiles.ReceiveDepot;
import warehouse.tiles.ShipDepot;
import warehouse.tiles.Tile;

import java.util.HashMap;
import java.util.Map;

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
     * Default warehouse border colour.
     */
    private static final Colour WAREHOUSE_BORDER_COLOUR = new Colour(113, 129, 109, 1.0f);
    /**
     * Default warehouse background colour.
     */
    private static final Colour WAREHOUSE_BACKGROUND_COLOUR = new Colour(101, 101, 101, 0.5f);
    /**
     * Default warehouse tile colours
     */
    private static final Map<Class<? extends Tile>, Colour> DEFAULT_WAREHOUSE_TILE_COLOURS = new HashMap<>();
    /**
     * Default warehouse tile icons
     */
    private static final Map<Class<? extends Tile>, FontAwesomeIcon> DEFAULT_WAREHOUSE_TILE_ICONS = new HashMap<>();
    /**
     * Default border thickness of warehouse tiles.
     */
    private static final float DEFAULT_WAREHOUSE_TILE_BORDER_THICKNESS = 2;
    /**
     * Default border radius of warehouse tiles.
     */
    private static final float DEFAULT_WAREHOUSE_TILE_BORDER_RADIUS = 5;

    static {
        DEFAULT_WAREHOUSE_TILE_COLOURS.put(Rack.class, new Colour(68, 118, 160, 0.2f));
        DEFAULT_WAREHOUSE_TILE_COLOURS.put(ReceiveDepot.class, new Colour(207, 192, 121, 0.2f));
        DEFAULT_WAREHOUSE_TILE_COLOURS.put(ShipDepot.class, new Colour(166, 109, 113, 0.2f));

        DEFAULT_WAREHOUSE_TILE_ICONS.put(ReceiveDepot.class, FontAwesomeIcon.SignInAlt);
        DEFAULT_WAREHOUSE_TILE_ICONS.put(ShipDepot.class, FontAwesomeIcon.SignOutAlt);
    }

    /**
     * The default colour of the warehouse tile icons.
     */
    private static final Colour DEFAULT_WAREHOUSE_TILE_ICON_COLOUR = new Colour(223, 224, 223, 0.6f);
    /**
     * The default item/package icon.
     */
    private static final FontAwesomeIcon DEFAULT_ITEM_ICON = FontAwesomeIcon.Box;

    /**
     * Default colour scheme for the WarehouseCanvas.
     */
    public static final WarehouseCanvasColourScheme DEFAULT = new WarehouseCanvasColourScheme(
            DEFAULT_BORDER_COLOUR, DEFAULT_BACKGROUND_COLOUR, DEFAULT_GRID_LINE_COLOUR,
            DEFAULT_SELECTION_OUTLINE_COLOUR, DEFAULT_MOVE_OUTLINE_COLOUR,
            WAREHOUSE_BORDER_COLOUR, WAREHOUSE_BACKGROUND_COLOUR,
            DEFAULT_WAREHOUSE_TILE_COLOURS, DEFAULT_WAREHOUSE_TILE_ICONS,
            DEFAULT_WAREHOUSE_TILE_BORDER_THICKNESS, DEFAULT_WAREHOUSE_TILE_BORDER_RADIUS,
            DEFAULT_WAREHOUSE_TILE_ICON_COLOUR, DEFAULT_ITEM_ICON);

    private Colour borderColour;
    private Colour backgroundColour;
    private Colour gridLineColour;
    private Colour selectionOutlineColour;
    private Colour moveOutlineColour;

    private Colour warehouseBorderColour;
    private Colour warehouseBackgroundColour;
    private Map<Class<? extends Tile>, Colour> warehouseTileColours;
    private Map<Class<? extends Tile>, FontAwesomeIcon> warehouseTileIcons;
    private float warehouseTileBorderThickness;
    private float warehouseTileBorderRadius;
    private Colour warehouseTileIconColour;
    private FontAwesomeIcon itemIcon;

    /**
     * Construct a new WarehouseCanvas with a custom colour scheme.
     * @param borderColour The colour of the canvas border.
     * @param backgroundColour The colour of the canvas background.
     * @param gridLineColour The colour of the canvas grid lines.
     * @param selectionOutlineColour The colour of the selection outline.
     * @param moveOutlineColour The colour of the move outline.
     * @param warehouseBorderColour The warehouse border colour.
     * @param warehouseBackgroundColour The warehouse background colour.
     * @param warehouseTileColours The colours of the tiles in the warehouse.
     * @param warehouseTileIcons The icons of the tiles in the warehouse.
     * @param warehouseTileBorderThickness The border thickness of warehouse tiles.
     * @param warehouseTileBorderRadius The border radius of warehouse tiles.
     * @param warehouseTileIconColour The colour of the warehouse tile icons.
     * @param itemIcon The item/package icon.
     */
    public WarehouseCanvasColourScheme(Colour borderColour, Colour backgroundColour,
                                       Colour gridLineColour,
                                       Colour selectionOutlineColour, Colour moveOutlineColour,
                                       Colour warehouseBorderColour, Colour warehouseBackgroundColour,
                                       Map<Class<? extends Tile>, Colour> warehouseTileColours,
                                       Map<Class<? extends Tile>, FontAwesomeIcon> warehouseTileIcons,
                                       float warehouseTileBorderThickness, float warehouseTileBorderRadius,
                                       Colour warehouseTileIconColour, FontAwesomeIcon itemIcon) {
        this.borderColour = borderColour;
        this.backgroundColour = backgroundColour;
        this.gridLineColour = gridLineColour;
        this.selectionOutlineColour = selectionOutlineColour;
        this.moveOutlineColour = moveOutlineColour;
        this.warehouseBorderColour = warehouseBorderColour;
        this.warehouseBackgroundColour = warehouseBackgroundColour;
        this.warehouseTileColours = warehouseTileColours;
        this.warehouseTileIcons = warehouseTileIcons;
        this.warehouseTileBorderThickness = warehouseTileBorderThickness;
        this.warehouseTileBorderRadius = warehouseTileBorderRadius;
        this.warehouseTileIconColour = warehouseTileIconColour;
        this.itemIcon = itemIcon;
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

    public Colour getWarehouseBorderColour() {
        return warehouseBorderColour;
    }

    public void setWarehouseBorderColour(Colour warehouseBorderColour) {
        this.warehouseBorderColour = warehouseBorderColour;
    }

    public Colour getWarehouseBackgroundColour() {
        return warehouseBackgroundColour;
    }

    public void setWarehouseBackgroundColour(Colour warehouseBackgroundColour) {
        this.warehouseBackgroundColour = warehouseBackgroundColour;
    }

    public Map<Class<? extends Tile>, Colour> getWarehouseTileColours() {
        return warehouseTileColours;
    }

    public void setWarehouseTileColours(Map<Class<? extends Tile>, Colour> warehouseTileColours) {
        this.warehouseTileColours = warehouseTileColours;
    }

    public Map<Class<? extends Tile>, FontAwesomeIcon> getWarehouseTileIcons() {
        return warehouseTileIcons;
    }

    public void setWarehouseTileIcons(Map<Class<? extends Tile>, FontAwesomeIcon> warehouseTileIcons) {
        this.warehouseTileIcons = warehouseTileIcons;
    }

    public float getWarehouseTileBorderThickness() {
        return warehouseTileBorderThickness;
    }

    public void setWarehouseTileBorderThickness(float warehouseTileBorderThickness) {
        this.warehouseTileBorderThickness = warehouseTileBorderThickness;
    }

    public float getWarehouseTileBorderRadius() {
        return warehouseTileBorderRadius;
    }

    public void setWarehouseTileBorderRadius(float warehouseTileBorderRadius) {
        this.warehouseTileBorderRadius = warehouseTileBorderRadius;
    }

    public Colour getWarehouseTileIconColour() {
        return warehouseTileIconColour;
    }

    public void setWarehouseTileIconColour(Colour warehouseTileIconColour) {
        this.warehouseTileIconColour = warehouseTileIconColour;
    }

    public FontAwesomeIcon getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(FontAwesomeIcon itemIcon) {
        this.itemIcon = itemIcon;
    }
}

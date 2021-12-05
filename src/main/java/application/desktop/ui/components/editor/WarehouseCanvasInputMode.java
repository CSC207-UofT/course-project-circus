package application.desktop.ui.components.editor;

/**
 * Input mode of the WarehouseCanvas.
 */
public enum WarehouseCanvasInputMode {
    /**
     * No input.
     */
    NONE,
    /**
     * Input empty tiles, e.g. clicking on a non-empty tile erases it.
     */
    PLACE_EMPTY,
    /**
     * Input a rack.
     */
    PLACE_RACK,
    /**
     * Input a receive depot.
     */
    PLACE_RECEIVE_DEPOT,
    /**
     * Input a ship depot
     */
    PLACE_SHIP_DEPOT,
    /**
     * Move a tile
     */
    MOVE_TILE,
    /**
     * Select a tile
     */
    SELECT_TILE
}

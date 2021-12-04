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
    EMPTY,
    /**
     * Input a rack.
     */
    RACK,
    /**
     * Input a receive depot.
     */
    RECEIVE_DEPOT,
    /**
     * Input a ship depot
     */
    SHIP_DEPOT
}

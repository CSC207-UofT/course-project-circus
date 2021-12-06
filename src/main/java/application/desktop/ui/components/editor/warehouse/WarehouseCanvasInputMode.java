package application.desktop.ui.components.editor.warehouse;

/**
 * Input mode of the WarehouseCanvas.
 */
public enum WarehouseCanvasInputMode {
    /**
     * No input.
     */
    SELECT_TILE,
    /**
     * Move a tile
     */
    MOVE_TILE,
    /**
     * Insert a tile.
     */
    INSERT_TILE,
    /**
     * Erase a tile.
     */
    ERASE_TILE
}

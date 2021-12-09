package application.desktop.ui.components.editor.warehouse;

/**
 * Input mode of the WarehouseCanvas.
 */
public enum WarehouseCanvasInputMode {
    /**
     * Select a tile at the mouse position
     */
    SELECT_TILE,
    /**
     * Move a tile at the mouse position
     */
    MOVE_TILE,
    /**
     * Insert a tile at the mouse position
     */
    INSERT_TILE,
    /**
     * Erase the object at the mouse position
     */
    ERASE_OBJECT,
    /**
     * Places a robot at the mouse position
     */
    PLACE_ROBOT
}

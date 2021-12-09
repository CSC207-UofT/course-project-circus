package application.desktop.ui.components.editor.warehouse.renderers;

import application.desktop.ui.Colour;
import application.desktop.ui.components.editor.warehouse.WarehouseCanvasColourScheme;
import application.desktop.ui.components.editor.warehouse.WarehouseCanvasTransform;
import imgui.ImDrawList;
import imgui.ImVec2;
import warehouse.WarehouseState;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;
import warehouse.tiles.Tile;

/**
 * A Warehouse renderer.
 */
public interface WarehouseCanvasRenderer<T extends WarehouseCoordinateSystem<U>,
        U extends WarehouseCoordinate> {
    /**
     * Handle user interaction.
     */
    void handleInteraction();

    /**
     * Draws the given Warehouse to the given ImDrawList instance.
     * @param drawList The context to draw to.
     * @param state The warehouse state.
     * @param transform The canvas transform
     * @param colourScheme The colour scheme.
     */
    void drawWarehouse(ImDrawList drawList, WarehouseState<T, U> state,
                       WarehouseCanvasTransform transform, WarehouseCanvasColourScheme colourScheme);

    /**
     * Draw a tile gizmo (debug overlay for that tile).
     * @param drawList The draw list to draw on.
     * @param state The warehouse state.
     * @param transform The canvas transform.
     * @param tile The tile to draw.
     * @param positionOffset An offset on the top left position of the Tile in screen space.
     * @param drawHandles Draw handles on the tile.
     * @param colour The colour of the gizmo.
     */
    void drawTileGizmo(ImDrawList drawList, WarehouseState<T, U> state,
                       WarehouseCanvasTransform transform,
                       Tile tile, ImVec2 positionOffset, boolean drawHandles, Colour colour);
    /**
     * Pan the renderer so that the contents are centered to the given WarehouseCanvasTransform.
     */
    void panToCentre(WarehouseState<T, U> state, WarehouseCanvasTransform transform);

    /**
     * Get the Tile under the mouse.
     * @param state The warehouse state.
     * @param transform The canvas transform.
     * @return the Tile under the mouse, or null if the mouse is not under a tile.
     */
    Tile getTileAtMousePosition(WarehouseState<T, U> state, WarehouseCanvasTransform transform);
}

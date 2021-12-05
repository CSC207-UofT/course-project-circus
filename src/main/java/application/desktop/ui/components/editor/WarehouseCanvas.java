package application.desktop.ui.components.editor;

import application.desktop.DesktopApplication;
import application.desktop.ui.Colour;
import application.desktop.ui.FontAwesomeIcon;
import application.desktop.ui.components.common.Component;
import imgui.*;
import imgui.flag.ImGuiButtonFlags;
import imgui.flag.ImGuiMouseButton;
import utils.Pair;
import warehouse.*;
import warehouse.tiles.*;

/**
 * A canvas that visualizes the Warehouse.
 */
public class WarehouseCanvas extends Component {
    private Warehouse warehouse;
    private WarehouseCanvasColourScheme colourScheme;

    private float cellSize;
    private float minSizeX;
    private float minSizeY;

    private float minZoom;
    private float maxZoom;
    private float zoomStep;
    private boolean showGrid;

    private int frameCounter;

    private ImVec2 size;
    private ImVec2 topLeft;

    private TileType tileTypeToInsert;
    private TileFactory tileFactory;

    /**
     * Offset applied to canvas elements to enable scrolling.
     */
    private final ImVec2 panOffset;
    /**
     * Scale multiplier for zooming.
     */
    private float zoom;
    /**
     * Current input mode of this WarehouseCanvas.
     */
    private WarehouseCanvasInputMode inputMode;
    /**
     * The currently selected tile, or null if no tile is selected.
     */
    private Tile selectedTile;

    /**
     * Construct a new WarehouseCanvas with a default colour scheme.
     *
     * @param warehouse The Warehouse to visualise.
     */
    public WarehouseCanvas(Warehouse warehouse) {
        this(warehouse,
                WarehouseCanvasColourScheme.DEFAULT,
                64.0f,
                100.0f,
                100.0f, 0.05f, 3.0f, 0.1f,
                true);
    }

    /**
     * Construct a new WarehouseCanvas with a custom colour scheme.
     *
     * @param warehouse    The Warehouse to visualise.
     * @param colourScheme The colour scheme of this WarehouseCanvas.
     * @param cellSize     The size of a grid cell in screen coordinates.
     * @param minSizeX     The minimum horizontal size of the canvas, in pixels.
     * @param minSizeY     The minimum vertical size of the canvas, in pixels.
     * @param minZoom      The minimum scale allowed zooming out.
     * @param maxZoom      The maximum scale allowed zooming in.
     * @param zoomStep     The amount to step when zooming.
     * @param showGrid     Whether to show the grid.
     */
    public WarehouseCanvas(Warehouse warehouse,
                           WarehouseCanvasColourScheme colourScheme,
                           float cellSize,
                           float minSizeX, float minSizeY,
                           float minZoom, float maxZoom, float zoomStep,
                           boolean showGrid) {
        this.warehouse = warehouse;
        this.colourScheme = colourScheme;
        this.cellSize = cellSize;
        this.minSizeX = minSizeX;
        this.minSizeY = minSizeY;
        this.minZoom = minZoom;
        this.maxZoom = maxZoom;
        this.zoomStep = zoomStep;
        this.showGrid = showGrid;

        tileTypeToInsert = TileType.RACK;
        tileFactory = new TileFactory();

        size = new ImVec2(minSizeX, minSizeY);
        topLeft = new ImVec2(0, 0);
        panOffset = new ImVec2(0, 0);
        zoom = 1.0f;
        inputMode = WarehouseCanvasInputMode.NONE;

        frameCounter = 0;
    }

    @Override
    public void drawContent(DesktopApplication application) {
        updateTransform();
        handleInteraction();

        // NOTE: The frame counter is a hacky way of centering the contents on load. It works by waiting for the second
        // frame, so that all the contents have been rendered and updated at least once.
        if (frameCounter <= 1) {
            zoomToFit();
            panToCentre();
            frameCounter += 1;
        }

        ImDrawList drawList = ImGui.getWindowDrawList();
        drawBackground(drawList);
        drawCanvas(drawList);
    }


    /**
     * Return the origin of the canvas in screen space.
     */
    private ImVec2 getOrigin() {
        return new ImVec2(topLeft.x + panOffset.x, topLeft.y + panOffset.y);
    }

    /**
     * Return the position of the mouse relative to the canvas origin.
     */
    private ImVec2 getRelativeMousePosition() {
        ImGuiIO io = ImGui.getIO();
        ImVec2 origin = getOrigin();
        return new ImVec2(io.getMousePos().x - origin.x, io.getMousePos().y - origin.y);
    }

    /**
     * Return the size of the Warehouse in pixel space.
     */
    private ImVec2 getWarehouseSizeInPixels() {
        float gridStep = getGridStep();
        return new ImVec2(gridStep * warehouse.getWidth(), gridStep * warehouse.getHeight());
    }

    /**
     * Return the coordinates of the tile at the given point in warehouse space.
     * @remark Note that it is NOT guaranteed that the warehouse space coordinates correspond to an actual tile
     * in the warehouse, e.g. the returned coordinates may be out of bounds!
     * @param p The point in screen space to convert to warehouse space.
     */
    private Pair<Integer, Integer> screenToWarehousePoint(ImVec2 p) {
        ImVec2 pixelWarehouseSize = getWarehouseSizeInPixels();
        float gridStep = getGridStep();
        int tileX = (int) ((p.x + pixelWarehouseSize.x / 2) / gridStep);
        int tileY = (int) ((p.y  + pixelWarehouseSize.y / 2) / gridStep);
        return new Pair<>(tileX, tileY);
    }

    /**
     * Return the Tile at the given screen point, or null if the point (in warehouse space) is out of bounds.
     * @param p The position of the tile in screen space.
     */
    private Tile getTileFromScreenPoint(ImVec2 p) {
        Pair<Integer, Integer> tileCoords = screenToWarehousePoint(p);
        return warehouse.getTileAt(tileCoords.getFirst(), tileCoords.getSecond());
    }

    /**
     * Update the panOffset so that the contents are centered in the canvas. Mutates panOffset.
     */
    private void panToCentre() {
        float gridStep = getGridStep();
        panOffset.x = gridStep * (float) Math.floor(warehouse.getWidth() / 2.0f)
                + (size.x - gridStep * warehouse.getWidth()) / 2.0f;
        panOffset.y = gridStep * (float) Math.floor(warehouse.getHeight() / 2.0f)
                + (size.y - gridStep * warehouse.getHeight()) / 2.0f;
    }

    /**
     * Update the zoom scale so that the contents fit on the screen. Mutates the zoom.
     */
    private void zoomToFit() {
        ImVec2 pixelWarehouseSize = getWarehouseSizeInPixels();
        zoom = Math.min(pixelWarehouseSize.x / size.x, pixelWarehouseSize.y / size.y);
    }

    /**
     * Update transform information such as size and position.
     */
    private void updateTransform() {
        ImVec2 contentAvailable = ImGui.getContentRegionAvail();
        size = new ImVec2(Math.max(contentAvailable.x, minSizeX),
                Math.max(contentAvailable.y, minSizeY));
        topLeft = ImGui.getCursorScreenPos();
    }

    /**
     * Handle user interaction.
     */
    private void handleInteraction() {
        ImGuiIO io = ImGui.getIO();
        ImGui.invisibleButton("canvas", size.x, size.y, ImGuiButtonFlags.MouseButtonLeft |
                ImGuiButtonFlags.MouseButtonRight);
        boolean isHovered = ImGui.isItemHovered();
        if (isHovered && ImGui.isMouseClicked(ImGuiMouseButton.Left)) {
            ImGui.setWindowFocus();
        }

//        // Add first and second point
//        if (isHovered && !adding_line && ImGui.isMouseClicked(ImGuiMouseButton.Left))
//        {
//            points.add(mousePosition);
//            points.add(mousePosition);
//            adding_line = true;
//        }
//        if (adding_line)
//        {
//            points.set(points.size() - 1, mousePosition);
////            points.back() = mouse_pos_in_canvas;
//            if (!ImGui.isMouseDown(ImGuiMouseButton.Left))
//                adding_line = false;
//        }

        // Pan (we use a zero mouse threshold when there's no context menu)
        // You may decide to make that threshold dynamic based on whether the mouse is hovering something etc.
        if (isHovered && ImGui.isMouseDragging(ImGuiMouseButton.Right, -1)) {
            panOffset.x += io.getMouseDelta().x;
            panOffset.y += io.getMouseDelta().y;
        }

        // Zoom
        if (isHovered) {
            zoom += Math.signum(io.getMouseWheel()) * zoomStep;
            zoom = Math.max(Math.min(zoom, maxZoom), minZoom);
        }

        // Context menu (under default mouse threshold)
        ImVec2 dragDelta = ImGui.getMouseDragDelta(ImGuiMouseButton.Right);
        if (ImGui.isMouseReleased(ImGuiMouseButton.Right) && dragDelta.x == 0.0f && dragDelta.y == 0.0f) {
            ImGui.openPopupOnItemClick("context");
        }

        if (ImGui.beginPopup("context")) {
            ImGui.text("context menu");
            ImGui.endPopup();
        }

        if (isHovered && ImGui.isMouseClicked(ImGuiMouseButton.Left)) {
            handleLeftClick();
        }
    }

    /**
     * Handle left click interaction.
     */
    private void handleLeftClick() {
        selectedTile = getTileFromScreenPoint(getRelativeMousePosition());
        if (inputMode == WarehouseCanvasInputMode.INSERT_TILE) {
            Pair<Integer, Integer> tileCoords = screenToWarehousePoint(getRelativeMousePosition());
            Tile tileToInsert = tileFactory.createTile(tileTypeToInsert, tileCoords.getFirst(), tileCoords.getSecond());
            if (tileToInsert != null) {
                warehouse.setTile(tileToInsert);
            }
        }
    }

    /**
     * Draw the canvas background. Mutates the given ImDrawList.
     */
    private void drawBackground(ImDrawList drawList) {
        ImVec2 bottomRight = getBottomRightCoordinate();
        final int borderColour = colourScheme.getBorderColour().toU32Colour();
        drawList.addRect(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y, borderColour);
        final int backgroundColour = colourScheme.getBackgroundColour().toU32Colour();
        drawList.addRectFilled(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y, backgroundColour);
    }

    /**
     * Draw the canvas grid. Mutates the given ImDrawList.
     */
    private void drawCanvas(ImDrawList drawList) {
        if (!showGrid) {
            return;
        }

        ImVec2 bottomRight = getBottomRightCoordinate();
        drawList.pushClipRect(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y, true);
        final int gridLineColour = colourScheme.getGridLineColour().toU32Colour();
        float gridStep = getGridStep();

        // Draw horizontal lines
        for (float x = panOffset.x % gridStep; x < size.x; x += gridStep) {
            float x1 = topLeft.x + x;
            float x2 = topLeft.x + x;
            drawList.addLine(x1, topLeft.y, x2, bottomRight.y, gridLineColour);
        }
        // Draw vertical lines
        for (float y = panOffset.y % gridStep; y < size.y; y += gridStep) {
            float y1 = topLeft.y + y;
            float y2 = topLeft.y + y;
            drawList.addLine(topLeft.x, y1, bottomRight.x, y2, gridLineColour);
        }

        drawWarehouse(drawList);
        drawList.popClipRect();
    }

    /**
     * Draw the warehouse centered at the origin. Mutates the given ImDrawList.
     */
    private void drawWarehouse(ImDrawList drawList) {
        ImVec2 origin = getOrigin();
        float gridStep = getGridStep();

        // Colours
        int WORLD_BORDER_COLOUR = ImGui.getColorU32(113 / 255f, 129 / 255.0f, 109 / 255.0f, 1.0f);
        int FLOOR_TILE_COLOUR = ImGui.getColorU32(101 / 255.0f, 101 / 255.0f, 101 / 255.0f, 0.5f);

        float centreOffsetX = -(float) Math.floor(warehouse.getWidth() / 2.0f);
        float centreOffsetY = -(float) Math.floor(warehouse.getHeight() / 2.0f);

        // Draw border
        float borderThickness = 4.0f * zoom;
        drawList.addRect(origin.x + gridStep * centreOffsetX - borderThickness * 0.5f,
                origin.y + gridStep * centreOffsetY - borderThickness * 0.5f,
                origin.x + gridStep * (warehouse.getWidth() + centreOffsetX) + borderThickness * 0.5f,
                origin.y + gridStep * (warehouse.getHeight() + centreOffsetY) + borderThickness * 0.5f,
                WORLD_BORDER_COLOUR, 10.0f, 0, borderThickness);

        // Draw tiles
        for (int y = 0; y < warehouse.getHeight(); y++) {
            for (int x = 0; x < warehouse.getWidth(); x++) {
                float x1 = origin.x + gridStep * (x + centreOffsetX);
                float y1 = origin.y + gridStep * (y + centreOffsetY);
                float x2 = x1 + gridStep;
                float y2 = y1 + gridStep;
                drawList.addRectFilled(x1, y1, x2, y2, FLOOR_TILE_COLOUR);

                Tile tile = warehouse.getTileAt(x, y);
                if (tile instanceof Rack) {
                    drawRack(drawList, x1, y1, x2, y2);
                } else if (tile instanceof ReceiveDepot) {
                    drawReceiveDepot(drawList, x1, y1, x2, y2);
                } else if (tile instanceof ShipDepot) {
                    drawShipDepot(drawList, x1, y1, x2, y2);
                }
            }
        }

        // Draw selected tile outline
        if (selectedTile != null) {
            drawList.addRect(x1, y1, x2, y2,
                    colourScheme.getSelectionOutlineColour().toU32Colour(),
                    5.0f, 0, 1.5f);
        }
    }

    private void drawRack(ImDrawList drawList, float x1, float y1, float x2, float y2) {
        float thickness = 4.0f * zoom;
        Colour RACK_BACKGROUND_COLOUR = new Colour(68, 118, 160, 0.2f);
        Colour RACK_BORDER_COLOUR = new Colour(RACK_BACKGROUND_COLOUR, 1.0f);

        // Draw rack
        drawList.addRectFilled(x1 + thickness * 0.5f, y1 + thickness * 0.5f,
                x2 - thickness * 0.5f, y2 - thickness * 0.5f,
                RACK_BACKGROUND_COLOUR.toU32Colour(), 5.0f, 0);

        drawList.addRect(x1 + thickness * 0.5f, y1 + thickness * 0.5f,
                x2 - thickness * 0.5f, y2 - thickness * 0.5f,
                RACK_BORDER_COLOUR.toU32Colour(),
                5.0f, 0, thickness);
    }

    private void drawReceiveDepot(ImDrawList drawList, float x1, float y1, float x2, float y2) {
        float thickness = 4.0f * zoom;
        Colour BACKGROUND_COLOUR = new Colour(207, 192, 121, 0.2f);
        Colour BORDER_COLOUR = new Colour(BACKGROUND_COLOUR, 1.0f);
        Colour ICON_COLOUR = new Colour(223, 224, 223, 0.6f);

        // Draw receive depot
        drawList.addRectFilled(x1 + thickness * 0.5f, y1 + thickness * 0.5f,
                x2 - thickness * 0.5f, y2 - thickness * 0.5f,
                BACKGROUND_COLOUR.toU32Colour(), 5.0f, 0);

        drawList.addRect(x1 + thickness * 0.5f, y1 + thickness * 0.5f,
                x2 - thickness * 0.5f, y2 - thickness * 0.5f,
                BORDER_COLOUR.toU32Colour(),
                5.0f, 0, thickness);

        float iconSize = 35 * zoom;
        drawList.addText(ImGui.getFont(), iconSize, (x1 + x2 - iconSize) / 2, (y1 + y2 - iconSize) / 2,
                ICON_COLOUR.toU32Colour(), FontAwesomeIcon.SignInAlt.getIconCode());
    }

    private void drawShipDepot(ImDrawList drawList, float x1, float y1, float x2, float y2) {
        float thickness = 4.0f * zoom;
        Colour BACKGROUND_COLOUR = new Colour(166, 109, 113, 0.2f);
        Colour BORDER_COLOUR = new Colour(BACKGROUND_COLOUR, 1.0f);
        Colour ICON_COLOUR = new Colour(223, 224, 223, 0.6f);

        // Draw receive depot
        drawList.addRectFilled(x1 + thickness * 0.5f, y1 + thickness * 0.5f,
                x2 - thickness * 0.5f, y2 - thickness * 0.5f,
                BACKGROUND_COLOUR.toU32Colour(), 5.0f, 0);

        drawList.addRect(x1 + thickness * 0.5f, y1 + thickness * 0.5f,
                x2 - thickness * 0.5f, y2 - thickness * 0.5f,
                BORDER_COLOUR.toU32Colour(),
                5.0f, 0, thickness);

        float iconSize = 35 * zoom;
        ImFont font = ImGui.getFont();
        drawList.addText(font, iconSize, (x1 + x2 - iconSize) / 2 + 3, (y1 + y2 - iconSize) / 2,
                ICON_COLOUR.toU32Colour(), FontAwesomeIcon.SignOutAlt.getIconCode());
    }

    /**
     * Return whether the given tile is selected.
     * @param x The horizontal coordinate of the tile (in warehouse space).
     * @param y The vertical coordinate of the tile (in warehouse space).
     */
    private boolean isTileSelected(int x, int y) {
        if (selectedTile == null) {
            return false;
        } else {
            return selectedTile.getX() == x && selectedTile.getY() == y;
        }
    }

    /**
     * Get the bottom right coordinate of the canvas.
     *
     * @return An ImVec2 object representing the bottom right coordinate.
     */
    private ImVec2 getBottomRightCoordinate() {
        return new ImVec2(topLeft.x + size.x, topLeft.y + size.y);
    }

    /**
     * Get the actual size of a single grid cell, in pixels. This accounts for zooming.
     */
    private float getGridStep() {
        return cellSize * zoom;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public WarehouseCanvasColourScheme getColourScheme() {
        return colourScheme;
    }

    public void setColourScheme(WarehouseCanvasColourScheme colourScheme) {
        this.colourScheme = colourScheme;
    }

    public float getCellSize() {
        return cellSize;
    }

    public void setCellSize(float cellSize) {
        this.cellSize = cellSize;
    }

    public float getMinSizeX() {
        return minSizeX;
    }

    public void setMinSizeX(float minSizeX) {
        this.minSizeX = minSizeX;
    }

    public float getMinSizeY() {
        return minSizeY;
    }

    public void setMinSizeY(float minSizeY) {
        this.minSizeY = minSizeY;
    }

    public float getMinZoom() {
        return minZoom;
    }

    public void setMinZoom(float minZoom) {
        this.minZoom = minZoom;
    }

    public float getMaxZoom() {
        return maxZoom;
    }

    public void setMaxZoom(float maxZoom) {
        this.maxZoom = maxZoom;
    }

    public float getZoomStep() {
        return zoomStep;
    }

    public void setZoomStep(float zoomStep) {
        this.zoomStep = zoomStep;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public WarehouseCanvasInputMode getInputMode() {
        return inputMode;
    }

    public void setInputMode(WarehouseCanvasInputMode inputMode) {
        this.inputMode = inputMode;
    }

    public TileType getTileTypeToInsert() {
        return tileTypeToInsert;
    }

    public void setTileTypeToInsert(TileType tileTypeToInsert) {
        this.tileTypeToInsert = tileTypeToInsert;
    }

    public Tile getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(Tile selectedTile) {
        this.selectedTile = selectedTile;
    }

    public boolean isShowGrid() {
        return showGrid;
    }

    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
    }
}

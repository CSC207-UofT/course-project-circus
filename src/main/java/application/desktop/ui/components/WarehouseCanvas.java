package application.desktop.ui.components;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.Component;
import imgui.*;
import imgui.flag.ImGuiButtonFlags;
import imgui.flag.ImGuiMouseButton;
import warehouse.*;
import warehouse.storage.StorageUnit;
import warehouse.tiles.Rack;
import warehouse.tiles.StorageTile;
import warehouse.tiles.Tile;

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

    /**
     * Offset applied to canvas elements to enable scrolling.
     */
    private final ImVec2 panOffset;
    /**
     * Scale multiplier for zooming.
     */
    private float zoom;

    /**
     * Construct a new WarehouseCanvas with a default colour scheme.
     * @param warehouse The Warehouse to visualise.
     */
    public WarehouseCanvas(Warehouse warehouse) {
        this(warehouse,
                WarehouseCanvasColourScheme.DEFAULT,
                64.0f,
                100.0f,
                100.0f, 0.05f,3.0f, 0.1f,
                true);
    }

    /**
     * Construct a new WarehouseCanvas with a custom colour scheme.
     * @param warehouse The Warehouse to visualise.
     * @param colourScheme The colour scheme of this WarehouseCanvas.
     * @param cellSize The size of a grid cell in screen coordinates.
     * @param minSizeX The minimum horizontal size of the canvas, in pixels.
     * @param minSizeY The minimum vertical size of the canvas, in pixels.
     * @param minZoom The minimum scale allowed zooming out.
     * @param maxZoom The maximum scale allowed zooming in.
     * @param zoomStep The amount to step when zooming.
     * @param showGrid Whether to show the grid.
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

        size = new ImVec2(minSizeX, minSizeY);
        topLeft = new ImVec2(0, 0);
        panOffset = new ImVec2(0, 0);
        zoom = 1.0f;

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
        drawGrid(drawList);
    }

    /**
     * Update the panOffset so that the contents are centered in the canvas. Mutates panOffset.
     */
    private void panToCentre() {
        float gridStep = getGridStep();
        panOffset.x = gridStep * (float)Math.floor(warehouse.getWidth() / 2.0f)
                + (size.x - gridStep * warehouse.getWidth()) / 2.0f;
        panOffset.y = gridStep * (float)Math.floor(warehouse.getHeight() / 2.0f)
                + (size.y - gridStep * warehouse.getHeight()) / 2.0f;
    }

    /**
     * Update the zoom scale so that the contents fit on the screen. Mutates the zoom.
     */
    private void zoomToFit() {
        float gridStep = getGridStep();
        float actualWarehouseWidth = gridStep * warehouse.getWidth();
        float actualWarehouseHeight = gridStep * warehouse.getHeight();
        zoom = Math.min(actualWarehouseWidth / size.x, actualWarehouseHeight / size.y);
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
        boolean isActive = ImGui.isItemActive();
        if(isHovered && ImGui.isMouseClicked(ImGuiMouseButton.Left)) {
            ImGui.setWindowFocus();
        }

        ImVec2 origin = new ImVec2(topLeft.x + panOffset.x, topLeft.y + panOffset.y);
        ImVec2 mousePosition = new ImVec2(io.getMousePos().x - origin.x, io.getMousePos().y - origin.y);

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
        if (isHovered && ImGui.isMouseDragging(ImGuiMouseButton.Right, -1))
        {
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

        if (ImGui.beginPopup("context"))
        {
            ImGui.text("context menu");
            ImGui.endPopup();
        }
    }

    /**
     * Draw the canvas background. Mutates the given ImDrawList.
     */
    private void drawBackground(ImDrawList drawList) {
        ImVec2 bottomRight = getBottomRightCoordinate();
        final int borderColour = WarehouseCanvasColourScheme.toU32Colour(colourScheme.getBorderColour());
        drawList.addRect(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y, borderColour);
        final int backgroundColour = WarehouseCanvasColourScheme.toU32Colour(colourScheme.getBackgroundColour());
        drawList.addRectFilled(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y, backgroundColour);
    }

    /**
     * Draw the canvas grid. Mutates the given ImDrawList.
     */
    private void drawGrid(ImDrawList drawList) {
        if (!showGrid) {
            return;
        }

        ImVec2 bottomRight = getBottomRightCoordinate();
        drawList.pushClipRect(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y, true);
        final int gridLineColour = WarehouseCanvasColourScheme.toU32Colour(colourScheme.getGridLineColour());
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
        ImVec2 origin = new ImVec2(topLeft.x + panOffset.x, topLeft.y + panOffset.y);
        float gridStep = getGridStep();

        // Colours
        int WORLD_BORDER_COLOUR = ImGui.getColorU32(113 / 255f, 129 / 255.0f, 109 / 255.0f, 1.0f);
        int FLOOR_TILE_COLOUR = ImGui.getColorU32(101 / 255.0f, 101 / 255.0f, 101 / 255.0f, 0.5f);

        float centreOffsetX = -(float)Math.floor(warehouse.getWidth() / 2.0f);
        float centreOffsetY = -(float)Math.floor(warehouse.getHeight() / 2.0f);

        float thickness = 4.0f * zoom;
        ImVec4 RACK_BACKGROUND_COLOUR = new ImVec4(68 / 255.0f, 118 / 255.0f, 160 / 255.0f, 0.2f);
        ImVec4 RACK_BORDER_COLOUR = new ImVec4(RACK_BACKGROUND_COLOUR.x, RACK_BACKGROUND_COLOUR.y, RACK_BACKGROUND_COLOUR.z, 1.0f);


        // Draw tiles
        for (int y = 0; y < warehouse.getHeight(); y++) {
            for (int x = 0; x < warehouse.getWidth(); x++) {
                float x1 = origin.x + gridStep * (x + centreOffsetX);
                float y1 = origin.y + gridStep * (y + centreOffsetY);
                float x2 = x1 + gridStep;
                float y2 = y1 + gridStep;
                drawList.addRectFilled(x1, y1, x2, y2, FLOOR_TILE_COLOUR);

                try {
                    Tile tile = warehouse.getTileAt(x, y);
                    if (tile instanceof Rack) {
                        // Draw rack
                        drawList.addRectFilled(x1 + thickness * 0.5f, y1 + thickness * 0.5f,
                                x2 - thickness * 0.5f, y2 - thickness * 0.5f,
                                WarehouseCanvasColourScheme.toU32Colour(RACK_BACKGROUND_COLOUR), 5.0f, 0);

                        drawList.addRect(x1 + thickness * 0.5f, y1 + thickness * 0.5f,
                                x2 - thickness * 0.5f, y2 - thickness * 0.5f, WarehouseCanvasColourScheme.toU32Colour(RACK_BORDER_COLOUR),
                                5.0f, 0, thickness);
                    }
                } catch (TileOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }

        // Draw border
        float borderThickness = 4.0f * zoom;
        drawList.addRect(origin.x + gridStep * centreOffsetX - borderThickness * 0.5f,
                origin.y + gridStep * centreOffsetY - borderThickness * 0.5f,
                origin.x + gridStep * (warehouse.getWidth() + centreOffsetX) + borderThickness * 0.5f,
                origin.y + gridStep * (warehouse.getHeight() + centreOffsetY) + borderThickness * 0.5f,
                WORLD_BORDER_COLOUR, 10.0f, 0, borderThickness);
    }

    /**
     * Get the bottom right coordinate of the canvas.
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

    public boolean isShowGrid() {
        return showGrid;
    }

    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
    }
}

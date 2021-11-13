package application.desktop.ui.components;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.Component;
import imgui.*;
import imgui.flag.ImGuiButtonFlags;
import imgui.flag.ImGuiMouseButton;

public class WarehouseLayoutCanvas extends Component {
    private WarehouseLayoutCanvasColourScheme colourScheme;
    private float cellSize;
    private float minSizeX;
    private float minSizeY;

    private float minZoom;
    private float maxZoom;
    private float zoomStep;
    private boolean showGrid;

    private ImVec2 size;
    private ImVec2 topLeft;

    /**
     * Offset applied to canvas elements to enable scrolling.
     */
    private final ImVec2 scrollOffset;
    /**
     * Scale multiplier for zooming.
     */
    private float zoom;

    /**
     * Construct a new WarehouseLayoutCanvas with a default colour scheme.
     */
    public WarehouseLayoutCanvas() {
        this(WarehouseLayoutCanvasColourScheme.DEFAULT,
                64.0f,
                100.0f,
                100.0f, 0.05f,3.0f, 0.1f,
                true);

        getOnStartMouseHoverEvent().addListener((source, application) -> {
            System.out.println("On start mouse hover");
        });
    }

    /**
     * Construct a new WarehouseLayoutCanvas with a custom colour scheme.
     * @param colourScheme The colour scheme of this WarehouseLayoutCanvas.
     * @param cellSize The size of a grid cell in screen coordinates.
     * @param minSizeX The minimum horizontal size of the canvas, in pixels.
     * @param minSizeY The minimum vertical size of the canvas, in pixels.
     * @param minZoom The minimum scale allowed zooming out.
     * @param maxZoom The maximum scale allowed zooming in.
     * @param zoomStep The amount to step when zooming.
     * @param showGrid Whether to show the grid.
     */
    public WarehouseLayoutCanvas(WarehouseLayoutCanvasColourScheme colourScheme,
                                 float cellSize,
                                 float minSizeX, float minSizeY,
                                 float minZoom, float maxZoom, float zoomStep,
                                 boolean showGrid) {
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

        scrollOffset = new ImVec2(0, 0);
        zoom = 1.0f;
    }

    @Override
    public void onDraw(DesktopApplication application) {
        ImVec2 contentAvailable = ImGui.getContentRegionAvail();
        size = new ImVec2(Math.max(contentAvailable.x, minSizeX),
                Math.max(contentAvailable.y, minSizeY));
        topLeft = ImGui.getCursorScreenPos();

        handleInteraction();
        ImDrawList drawList = ImGui.getWindowDrawList();
        drawBackground(drawList);
        drawGrid(drawList);

//        for (int n = 0; n < points.size(); n += 2) {
//            draw_list.addLine(origin.x + points.get(n).x, origin.y + points.get(n).y,
//                    origin.x + points.get(n + 1).x, origin.y + points.get(n + 1).y,
//                    ImGui.getColorU32(255, 255, 0, 255), 2.0f);
//        }
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

        ImVec2 origin = new ImVec2(topLeft.x + scrollOffset.x, topLeft.y + scrollOffset.y);
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
            scrollOffset.x += io.getMouseDelta().x;
            scrollOffset.y += io.getMouseDelta().y;
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
        final int borderColour = WarehouseLayoutCanvasColourScheme.toU32Colour(colourScheme.getBorderColour());
        drawList.addRect(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y, borderColour);
        final int backgroundColour = WarehouseLayoutCanvasColourScheme.toU32Colour(colourScheme.getBackgroundColour());
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
        final int gridLineColour = WarehouseLayoutCanvasColourScheme.toU32Colour(colourScheme.getGridLineColour());
        float gridStep = cellSize * zoom;

        // Draw horizontal lines
        for (float x = scrollOffset.x % gridStep; x < size.x; x += gridStep) {
            float x1 = topLeft.x + x;
            float x2 = topLeft.x + x;
            drawList.addLine(x1, topLeft.y, x2, bottomRight.y, gridLineColour);
        }
        // Draw vertical lines
        for (float y = scrollOffset.y % gridStep; y < size.y; y += gridStep) {
            float y1 = topLeft.y + y;
            float y2 = topLeft.y + y;
            drawList.addLine(topLeft.x, y1, bottomRight.x, y2, gridLineColour);
        }

        drawList.popClipRect();
    }

    /**
     * Get the bottom right coordinate of the canvas.
     * @return An ImVec2 object representing the bottom right coordinate.
     */
    private ImVec2 getBottomRightCoordinate() {
        return new ImVec2(topLeft.x + size.x, topLeft.y + size.y);
    }

    public WarehouseLayoutCanvasColourScheme getColourScheme() {
        return colourScheme;
    }

    public void setColourScheme(WarehouseLayoutCanvasColourScheme colourScheme) {
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

package application.desktop.ui.components;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.UIComponent;
import imgui.*;
import imgui.flag.ImGuiButtonFlags;
import imgui.flag.ImGuiMouseButton;

public class WarehouseLayoutCanvas extends UIComponent {
    private WarehouseLayoutCanvasColourScheme colourScheme;
    private float cellSize;

    private float minZoom;
    private float maxZoom;
    private float zoomStep;
    private boolean showGrid;

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
        this(WarehouseLayoutCanvasColourScheme.DEFAULT, 64.0f,
                0.05f,3.0f, 0.1f,
                true);
    }

    /**
     * Construct a new WarehouseLayoutCanvas with a custom colour scheme.
     * @param colourScheme The colour scheme of this WarehouseLayoutCanvas.
     * @param cellSize The size of a grid cell in screen coordinates.
     * @param minZoom The minimum scale allowed zooming out.
     * @param maxZoom The maximum scale allowed zooming in.
     * @param zoomStep The amount to step when zooming.
     * @param showGrid Whether to show the grid.
     */
    public WarehouseLayoutCanvas(WarehouseLayoutCanvasColourScheme colourScheme, float cellSize,
                                 float minZoom, float maxZoom, float zoomStep, boolean showGrid) {
        this.colourScheme = colourScheme;
        this.cellSize = cellSize;
        this.minZoom = minZoom;
        this.maxZoom = maxZoom;
        this.zoomStep = zoomStep;
        this.showGrid = showGrid;

        scrollOffset = new ImVec2(0, 0);
        zoom = 1.0f;
    }

    @Override
    public void render(DesktopApplication application) {
        ImVec2 canvasTopLeft = ImGui.getCursorScreenPos();
        ImVec2 canvasSize = ImGui.getContentRegionAvail();
        canvasSize.x = Math.max(canvasSize.x, 50);
        canvasSize.y = Math.max(canvasSize.y, 50);
        ImVec2 canvasBottomRight = new ImVec2(canvasTopLeft.x + canvasSize.x, canvasTopLeft.y + canvasSize.y);

        handleInteraction();

        ImDrawList drawList = ImGui.getWindowDrawList();
        // Draw background and border
        final int borderColour = WarehouseLayoutCanvasColourScheme.toU32Colour(colourScheme.getBorderColour());
        drawList.addRect(canvasTopLeft.x, canvasTopLeft.y, canvasBottomRight.x, canvasBottomRight.y, borderColour);
        final int backgroundColour = WarehouseLayoutCanvasColourScheme.toU32Colour(colourScheme.getBackgroundColour());
        drawList.addRectFilled(canvasTopLeft.x, canvasTopLeft.y, canvasBottomRight.x, canvasBottomRight.y, backgroundColour);
        // Draw grid + all lines in the canvas
        if (showGrid) {
            drawList.pushClipRect(canvasTopLeft.x, canvasTopLeft.y, canvasBottomRight.x, canvasBottomRight.y, true);
            final int gridLineColour = WarehouseLayoutCanvasColourScheme.toU32Colour(colourScheme.getGridLineColour());
            float gridStep = cellSize * zoom;
            for (float x = scrollOffset.x % gridStep; x < canvasSize.x; x += gridStep) {
                float x1 = canvasTopLeft.x + x;
                float x2 = canvasTopLeft.x + x;
                drawList.addLine(x1, canvasTopLeft.y, x2, canvasBottomRight.y, gridLineColour);
            }
            for (float y = scrollOffset.y % gridStep; y < canvasSize.y; y += gridStep) {
                float y1 = canvasTopLeft.y + y;
                float y2 = canvasTopLeft.y + y;
                drawList.addLine(canvasTopLeft.x, y1, canvasBottomRight.x, y2, gridLineColour);
            }
            drawList.popClipRect();
        }

//        for (int n = 0; n < points.size(); n += 2) {
//            draw_list.addLine(origin.x + points.get(n).x, origin.y + points.get(n).y,
//                    origin.x + points.get(n + 1).x, origin.y + points.get(n + 1).y,
//                    ImGui.getColorU32(255, 255, 0, 255), 2.0f);
//        }
    }

    private void handleInteraction() {
        ImGuiIO io = ImGui.getIO();
        ImVec2 canvasTopLeft = ImGui.getCursorScreenPos();
        ImVec2 canvasSize = ImGui.getContentRegionAvail();

        ImGui.invisibleButton("canvas", canvasSize.x, canvasSize.y,
                ImGuiButtonFlags.MouseButtonLeft | ImGuiButtonFlags.MouseButtonRight);
        boolean isHovered = ImGui.isItemHovered();
        boolean isActive = ImGui.isItemActive();

        ImVec2 origin = new ImVec2(canvasTopLeft.x + scrollOffset.x, canvasTopLeft.y + scrollOffset.y);
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
        if (isActive && ImGui.isMouseDragging(ImGuiMouseButton.Right, -1))
        {
            scrollOffset.x += io.getMouseDelta().x;
            scrollOffset.y += io.getMouseDelta().y;
        }

        // Zoom
        if (ImGui.isWindowFocused()) {
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

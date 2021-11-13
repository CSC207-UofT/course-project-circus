package application.desktop.ui.components;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.UIComponent;
import imgui.*;
import imgui.flag.ImGuiButtonFlags;
import imgui.flag.ImGuiMouseButton;

public class WarehouseLayoutCanvas extends UIComponent {
    /**
     * Offset applied to canvas elements to enable scrolling.
     */
    private final ImVec2 scrollOffset;
    private WarehouseLayoutCanvasColourScheme colourScheme;

    /**
     * Construct a new WarehouseLayoutCanvas with a default colour scheme.
     */
    public WarehouseLayoutCanvas() {
        this(WarehouseLayoutCanvasColourScheme.DEFAULT);
    }

    /**
     * Construct a new WarehouseLayoutCanvas with a custom colour scheme.
     * @param colourScheme The colour scheme of this WarehouseLayoutCanvas.
     */
    public WarehouseLayoutCanvas(WarehouseLayoutCanvasColourScheme colourScheme) {
        this.colourScheme = colourScheme;
        scrollOffset = new ImVec2(0, 0);
    }

    @Override
    public void render(DesktopApplication application) {
        ImGui.begin("Warehouse Layout");
        ImGui.text("Mouse Left: drag to add lines,\nMouse Right: drag to scroll, click for context menu.");

        renderCanvas();
        ImGui.end();
    }

    private void renderCanvas() {
        ImVec2 canvasTopLeft = ImGui.getCursorScreenPos();
        ImVec2 canvasSize = ImGui.getContentRegionAvail();
        ImVec2 canvasBottomRight = new ImVec2(canvasTopLeft.x + canvasSize.x, canvasTopLeft.y + canvasSize.y);

        // Draw border and background color
        ImGuiIO io = ImGui.getIO();
        ImDrawList drawList = ImGui.getWindowDrawList();

        final int borderColour = WarehouseLayoutCanvasColourScheme.toU32Colour(colourScheme.getBorderColour());
        drawList.addRect(canvasTopLeft.x, canvasTopLeft.y, canvasBottomRight.x, canvasBottomRight.y, borderColour);
        final int backgroundColour = WarehouseLayoutCanvasColourScheme.toU32Colour(colourScheme.getBackgroundColour());
        drawList.addRectFilled(canvasTopLeft.x, canvasTopLeft.y, canvasBottomRight.x, canvasBottomRight.y, backgroundColour);

        // This will catch our interactions
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

        // Draw grid + all lines in the canvas
        drawList.pushClipRect(canvasTopLeft.x, canvasTopLeft.y, canvasBottomRight.x, canvasBottomRight.y, true);
        float GRID_STEP = 64.0f;
        final int gridLineColour = WarehouseLayoutCanvasColourScheme.toU32Colour(colourScheme.getGridLineColour());
        for (float x = scrollOffset.x % GRID_STEP; x < canvasSize.x; x += GRID_STEP) {
            float x1 = canvasTopLeft.x + x;
            float x2 = canvasTopLeft.x + x;
            drawList.addLine(x1, canvasTopLeft.y, x2, canvasBottomRight.y, gridLineColour);
        }
        for (float y = scrollOffset.y % GRID_STEP; y < canvasSize.y; y += GRID_STEP) {
            float y1 = canvasTopLeft.y + y;
            float y2 = canvasTopLeft.y + y;
            drawList.addLine(canvasTopLeft.x, y1, canvasBottomRight.x, y2, gridLineColour);
        }

//        for (int n = 0; n < points.size(); n += 2) {
//            draw_list.addLine(origin.x + points.get(n).x, origin.y + points.get(n).y,
//                    origin.x + points.get(n + 1).x, origin.y + points.get(n + 1).y,
//                    ImGui.getColorU32(255, 255, 0, 255), 2.0f);
//        }
        drawList.popClipRect();
    }

    public WarehouseLayoutCanvasColourScheme getColourScheme() {
        return colourScheme;
    }

    public void setColourScheme(WarehouseLayoutCanvasColourScheme colourScheme) {
        this.colourScheme = colourScheme;
    }
}

package application.desktop.ui.utils;

import application.desktop.ui.Colour;
import imgui.*;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;

/**
 * Drawing utility functions.
 */
public class DrawingUtils {
    /**
     * Draw a rectangle from the given top-left coordinate to the given bottom-right coordinate.
     * @param drawList The draw list to add the rectangle to.
     * @param topLeft The top-left coordinate of the rectangle.
     * @param bottomRight The bottom-right coordinate of the rectangle.
     * @param fill The fill colour of the rectangle. If null, then no fill is drawn.
     * @param border The border colour of the rectangle. If null, the no border is drawn.
     * @param borderThickness The thickness of the border.
     * @param borderRadius The radius of the border.
     * @param borderType The type of border to draw.
     */
    public static void drawRect(ImDrawList drawList, ImVec2 topLeft, ImVec2 bottomRight, Colour fill,
                          Colour border, float borderThickness, float borderRadius, RectBorderType borderType) {
        // Fill coordinates
        float x1 = topLeft.x;
        float y1 = topLeft.y;
        float x2 = bottomRight.x;
        float y2 = bottomRight.y;
        // Border coordinates
        float bx1 = x1;
        float by1 = y1;
        float bx2 = x2;
        float by2 = y2;

        if (border != null) {
            borderThickness = Math.max(0, borderThickness);
            borderRadius = Math.max(0, borderRadius);
            // Transform coordinates based on border type
            float halfBorderThickness = borderThickness * 0.5f;
            if (borderType == RectBorderType.Outer) {
                bx1 -= halfBorderThickness;
                by1 -= halfBorderThickness;
                bx2 += halfBorderThickness;
                by2 += halfBorderThickness;
                x1 -= halfBorderThickness;
                y1 -= halfBorderThickness;
                x2 += halfBorderThickness;
                y2 += halfBorderThickness;
            } else if (borderType == RectBorderType.Inner) {
                bx1 += halfBorderThickness;
                by1 += halfBorderThickness;
                bx2 -= halfBorderThickness;
                by2 -= halfBorderThickness;
                x1 += halfBorderThickness;
                y1 += halfBorderThickness;
                x2 -= halfBorderThickness;
                y2 -= halfBorderThickness;
            }
            // NOTE: Do nothing if borderType == RectBorderType.Middle since the ImDrawList
            // draws a middle border by default.
        }

        if (fill != null) {
            drawList.addRectFilled(x1, y1, x2, y2,
                    fill.toU32Colour(), borderRadius);
        }

        if (border != null) {
            // Only draw the border if there is a border to draw!
            drawList.addRect((float)Math.floor(bx1), (float)Math.floor(by1),
                    (float)Math.ceil(bx2), (float)Math.ceil(by2),
                    border.toU32Colour(), borderRadius, 0, borderThickness);
        }
    }

    /**
     * Draw a rectangle from the given top-left coordinate to the given bottom-right coordinate with an inner border.
     * @param drawList The draw list to add the rectangle to.
     * @param topLeft The top-left coordinate of the rectangle.
     * @param bottomRight The bottom-right coordinate of the rectangle.
     * @param fill The fill colour of the rectangle. If null, then no fill is drawn.
     * @param border The border colour of the rectangle. If null, the no border is drawn.
     * @param borderThickness The thickness of the border.
     * @param borderRadius The radius of the border.
     */
    public static void drawRect(ImDrawList drawList, ImVec2 topLeft, ImVec2 bottomRight, Colour fill,
                          Colour border, float borderThickness, float borderRadius) {
        drawRect(drawList, topLeft, bottomRight, fill, border, borderThickness, borderRadius, RectBorderType.Inner);
    }

    /**
     * Draw a rectangle from the given top-left coordinate to the given bottom-right coordinate with no border.
     * @param drawList The draw list to add the rectangle to.
     * @param topLeft The top-left coordinate of the rectangle.
     * @param bottomRight The bottom-right coordinate of the rectangle.
     * @param fill The fill colour of the rectangle. If null, then no fill is drawn.
     */
    public static void drawRect(ImDrawList drawList, ImVec2 topLeft, ImVec2 bottomRight, Colour fill) {
        drawRect(drawList, topLeft, bottomRight, fill, null, 0, 0);
    }

    /**
     * Draw a rectangle of the given size and whose centre is the given point.
     * @param drawList The draw list to add the rectangle to.
     * @param centre The centre of the rectangle.
     * @param size The size of the rectangle.
     * @param fill The fill colour of the rectangle. If null, then no fill is drawn.
     * @param border The border colour of the rectangle. If null, the no border is drawn.
     * @param borderThickness The thickness of the border. If non-positive, then no border is drawn.
     * @param borderRadius The radius of the border. If non-positive, then no border radius is used.
     * @param borderType The type of border to draw.
     */
    public static void drawRectFromCentre(ImDrawList drawList, ImVec2 centre, ImVec2 size, Colour fill,
                                    Colour border, float borderThickness, float borderRadius, RectBorderType borderType) {
        ImVec2 topLeft = new ImVec2(centre.x - size.x * 0.5f, centre.y - size.y * 0.5f);
        ImVec2 bottomRight = new ImVec2(centre.x + size.x * 0.5f, centre.y + size.y * 0.5f);
        drawRect(drawList, topLeft, bottomRight, fill, border, borderThickness, borderRadius, borderType);
    }

    /**
     * Draw a rectangle of the given size and whose centre is the given point with no border.
     * @param drawList The draw list to add the rectangle to.
     * @param centre The centre of the rectangle.
     * @param size The size of the rectangle.
     * @param fill The fill colour of the rectangle. If null, then no fill is drawn.
     */
    public static void drawRectFromCentre(ImDrawList drawList, ImVec2 centre, ImVec2 size, Colour fill) {
        drawRectFromCentre(drawList, centre, size, fill, null, 0, 0, RectBorderType.Inner);
    }

    /**
     * Draws a hyperlink-styled label.
     * @param label The label of the hyperlink.
     * @param tooltip The tooltip to show when the label is hovered over.
     * @param normalColour The normal colour of the label. If null, then the default normal colour from the current style is used.
     * @param activeColour The active colour of the label, e.g. when it is clicked. If null, then the default active colour
     *                     from the current style is used.
     * @return True if the label was clicked, and False otherwise.
     */
    public static boolean hyperlinkLabel(String label, String tooltip, Colour normalColour, Colour activeColour)
    {
        ImGuiStyle style = ImGui.getStyle();
        if (activeColour == null) {
            activeColour = new Colour(style.getColor(ImGuiCol.ButtonHovered));
        }

        if (normalColour == null) {
            normalColour = new Colour(style.getColor(ImGuiCol.Text));
        }

        ImVec2 cursorScreenPos = ImGui.getCursorScreenPos();
        // Draw invisible text for interaction
        ImGui.pushStyleVar(ImGuiStyleVar.Alpha, 0);
        ImGui.text(label);
        ImGui.popStyleVar();

        boolean hovered = ImGui.isItemHovered();
        boolean clicked = false;
        Colour colour = normalColour;

        if (hovered)
        {
            if( ImGui.isMouseClicked(0) )
            {
                clicked = true;
            }

            colour = activeColour;
        }

        ImGui.setCursorScreenPos(cursorScreenPos.x, cursorScreenPos.y);
        ImGui.pushStyleColor(ImGuiCol.Text, colour.toU32Colour());
        ImGui.text(label);
        ImGui.popStyleColor();

        if (hovered) {
            addUnderline(colour);
            ImGui.setTooltip(tooltip);
        }

        return clicked;
    }

    public static void addUnderline(Colour colour) {
        ImVec2 min = ImGui.getItemRectMin();
        ImVec2 max = ImGui.getItemRectMax();
        min.y = max.y;
        ImGui.getWindowDrawList().addLine(min.x, min.y, max.x, max.y, colour.toU32Colour());
    }
}

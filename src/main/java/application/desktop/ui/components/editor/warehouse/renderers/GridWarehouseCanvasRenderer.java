package application.desktop.ui.components.editor.warehouse.renderers;

import application.desktop.adapters.PhysicalGridRobot;
import application.desktop.ui.Colour;
import application.desktop.ui.FontAwesomeIcon;
import application.desktop.ui.components.editor.warehouse.WarehouseCanvasColourScheme;
import application.desktop.ui.components.editor.warehouse.WarehouseCanvasTransform;
import application.desktop.ui.utils.DrawingUtils;
import application.desktop.ui.utils.RectBorderType;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImVec2;
import imgui.flag.ImGuiMouseButton;
import warehouse.WarehouseLayout;
import warehouse.WarehouseState;
import warehouse.geometry.grid.GridWarehouseCoordinateSystem;
import warehouse.geometry.grid.Point;
import warehouse.robots.Robot;
import warehouse.robots.RobotAdapter;
import warehouse.robots.RobotAdapterUpdater;
import warehouse.robots.RobotMapper;
import warehouse.tiles.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Renders a Warehouse with a GridWarehouseCoordinateSystem.
 */
public class GridWarehouseCanvasRenderer implements WarehouseCanvasRenderer<GridWarehouseCoordinateSystem, Point> {
    private final float gridStep;
    private final boolean showGrid;
    private final ImVec2 panOffset;

    /**
     * Construct a new WarehouseCanvas with default options.
     */
    public GridWarehouseCanvasRenderer() {
        this(32.0f, true);
    }

    public GridWarehouseCanvasRenderer(float gridStep, boolean showGrid) {
        this.gridStep = gridStep;
        this.showGrid = showGrid;
        this.panOffset = new ImVec2();
    }

    /**
     * Handle user interaction.
     */
    @Override
    public void handleInteraction() {
        boolean isHovered = ImGui.isItemHovered();
        // Mouse panning
        if (isHovered && ImGui.isMouseDragging(ImGuiMouseButton.Right, -1)) {
            ImGuiIO io = ImGui.getIO();
            panOffset.x += io.getMouseDelta().x;
            panOffset.y += io.getMouseDelta().y;
        }
    }

    /**
     * Draws the given Warehouse to the given ImDrawList.
     * @param drawList The context to draw to.
     */
    @Override
    public void drawWarehouse(ImDrawList drawList, WarehouseState<GridWarehouseCoordinateSystem, Point> state,
                              WarehouseCanvasTransform transform,
                              WarehouseCanvasColourScheme colourScheme) {
        if (showGrid) {
            drawGrid(drawList, transform, colourScheme);
        }

        drawWarehouseTiles(drawList, state, transform, colourScheme);
        drawRobots(drawList, state, transform);
        drawList.popClipRect();
    }

    private void drawGrid(ImDrawList drawList, WarehouseCanvasTransform transform, WarehouseCanvasColourScheme colourScheme) {
        ImVec2 contentSize = transform.getContentSize();
        ImVec2 contentTopLeft = transform.getContentTopLeft();
        ImVec2 contentBottomRight = transform.getContentBottomRight();

        drawList.pushClipRect(contentTopLeft.x, contentTopLeft.y, contentBottomRight.x, contentBottomRight.y, true);
        final int gridLineColour = colourScheme.getGridLineColour().toU32Colour();

        // Draw horizontal lines
        for (float x = panOffset.x % gridStep; x < contentSize.x; x += gridStep) {
            float x1 = contentTopLeft.x + x;
            float x2 = contentTopLeft.x + x;
            drawList.addLine(x1, contentTopLeft.y, x2, contentBottomRight.y, gridLineColour);
        }
        // Draw vertical lines
        for (float y = panOffset.y % gridStep; y < contentSize.y; y += gridStep) {
            float y1 = contentTopLeft.y + y;
            float y2 = contentTopLeft.y + y;
            drawList.addLine(contentTopLeft.x, y1, contentBottomRight.x, y2, gridLineColour);
        }
    }

    /**
     * Draw the robots in the warehouse.
     */
    private void drawRobots(ImDrawList drawList, WarehouseState<GridWarehouseCoordinateSystem, Point> state,
                            WarehouseCanvasTransform transform) {
        RobotMapper<Point> robotMapper = state.getRobotMapper();
        List<Robot> robots = robotMapper.getRobots();

        for (Robot robot : robots) {
            RobotAdapterUpdater<GridWarehouseCoordinateSystem, Point> robotAdapterUpdater = state.getRobotAdapterUpdater();
            RobotAdapter<GridWarehouseCoordinateSystem, Point> robotAdapter = robotAdapterUpdater.getRobotAdapter(robot);
            if (robotAdapter == null) continue;
            drawRobot(drawList, state, transform, robotAdapter);
        }
    }

    /**
     * Draw a single Robot.
     */
    private void drawRobot(ImDrawList drawList, WarehouseState<GridWarehouseCoordinateSystem, Point> state,
                           WarehouseCanvasTransform transform, RobotAdapter<GridWarehouseCoordinateSystem, Point> robotAdapter) {
        // Draw robot path
        PhysicalGridRobot physicalGridRobot = (PhysicalGridRobot) robotAdapter;
        if (physicalGridRobot.getCurrentRouteNodes() != null) {
            List<Tile> route = new ArrayList<>(physicalGridRobot.getCurrentRouteNodes());
            route.add(0, physicalGridRobot.getCurrentSource());
            for (int i = 1; i < route.size() ; i++) {
                Tile previous = route.get(i - 1);
                Tile current = route.get(i);

                Point currentPosition = state.getCoordinateSystem().projectIndexToCoordinate(current.getIndex());
                Point previousPosition = state.getCoordinateSystem().projectIndexToCoordinate(previous.getIndex());
                ImVec2 p1 = getTileTopLeft(getOrigin(transform), previousPosition.getX(), previousPosition.getY());
                p1.x += 0.5f * gridStep;
                p1.y += 0.5f * gridStep;
                ImVec2 p2 = getTileTopLeft(getOrigin(transform), currentPosition.getX(), currentPosition.getY());
                p2.x += 0.5f * gridStep;
                p2.y += 0.5f * gridStep;
                // Draw line between p1 and p2
                drawList.addLine(p1.x, p1.y, p2.x, p2.y, new Colour(0, 255, 0).toU32Colour());
            }
        }

        ImVec2 topLeft = getTileTopLeft(getOrigin(transform),
                physicalGridRobot.getX(), physicalGridRobot.getY());
        ImVec2 bottomRight = new ImVec2(topLeft.x + gridStep, topLeft.y + gridStep);
        // TODO: Move styles to colour scheme
        DrawingUtils.drawRect(drawList, topLeft, bottomRight,
                new Colour(64, 64, 64, 0.2f), new Colour(255, 162, 80, 1.0f),
                3.0f, 10.0f, RectBorderType.Inner);

        FontAwesomeIcon icon = FontAwesomeIcon.Robot;
        int iconColour = new Colour(223, 224, 223).toU32Colour();

        ImVec2 textSize = new ImVec2();
        ImGui.calcTextSize(textSize, icon.getIconCode());
        drawList.addText(ImGui.getFont(), 14,
                (topLeft.x + bottomRight.x - textSize.x + 3) / 2,
                (topLeft.y + bottomRight.y - textSize.y) / 2,
                iconColour, icon.getIconCode());
    }

    /**
     * Draw the warehouse centered at the origin. Mutates the given ImDrawList.
     */
    private void drawWarehouseTiles(ImDrawList drawList, WarehouseState<GridWarehouseCoordinateSystem, Point> state,
                                    WarehouseCanvasTransform transform, WarehouseCanvasColourScheme colourScheme) {
        GridWarehouseCoordinateSystem coordinateSystem = state.getCoordinateSystem();
        WarehouseLayout<Point> layout = state.getLayout();

        // Draw border
        float worldBorderThickness = 2.0f;
        float worldBorderRadius = 10.0f;
        ImVec2 origin = getOrigin(transform);
        ImVec2 borderBottomRight = new ImVec2(origin.x + gridStep * coordinateSystem.getWidth(),
                origin.y + gridStep * coordinateSystem.getHeight());
        DrawingUtils.drawRect(drawList, origin, borderBottomRight, null,
                colourScheme.getWarehouseBorderColour(),
                worldBorderThickness, worldBorderRadius, RectBorderType.Outer);

        // Draw floors - we need to draw this BEFORE the tiles due to layering
        for (int y = 0; y < coordinateSystem.getHeight(); y++) {
            for (int x = 0; x < coordinateSystem.getWidth(); x++) {
                ImVec2 topLeft = getTileTopLeft(origin, x, y);
                drawWarehouseFloor(drawList, topLeft, colourScheme);
            }
        }

        // Draw tiles
        for (int y = 0; y < coordinateSystem.getHeight(); y++) {
            for (int x = 0; x < coordinateSystem.getWidth(); x++) {
                Tile tile = layout.getTileAt(new Point(x, y));
                drawTile(drawList, tile, getTileTopLeft(origin, x, y), colourScheme);
            }
        }
    }

    /**
     * Draw the given tile.
     * @param drawList The draw list to draw to.
     * @param tile The tile to draw.
     * @param topLeft The top left point to draw the tile at.
     */
    private void drawTile(ImDrawList drawList, Tile tile, ImVec2 topLeft, WarehouseCanvasColourScheme colourScheme) {
        ImVec2 bottomRight = new ImVec2(topLeft.x + gridStep, topLeft.y + gridStep);
        Class<? extends Tile> clazz = tile.getClass();
        Map<Class<? extends Tile>, Colour> tileColours = colourScheme.getWarehouseTileColours();
        if (tileColours.containsKey(clazz)) {
            Colour backgroundColour = tileColours.get(clazz);
            Colour borderColour = new Colour(backgroundColour, 1);
            DrawingUtils.drawRect(drawList, topLeft, bottomRight,
                    backgroundColour, borderColour,
                    colourScheme.getWarehouseTileBorderThickness(), colourScheme.getWarehouseTileBorderRadius(),
                    RectBorderType.Inner);
        }

        Map<Class<? extends Tile>, FontAwesomeIcon> tileIcons = colourScheme.getWarehouseTileIcons();
        if (tileIcons.containsKey(clazz)) {
            FontAwesomeIcon icon = tileIcons.get(clazz);
            //float iconSize = 17.5f;
            int iconColour = colourScheme.getWarehouseTileIconColour().toU32Colour();

            ImVec2 textSize = new ImVec2();
            ImGui.calcTextSize(textSize, icon.getIconCode());
            drawList.addText(ImGui.getFont(), 14,
                    (topLeft.x + bottomRight.x - textSize.x + 2) / 2,
                    (topLeft.y + bottomRight.y - textSize.y) / 2,
                    iconColour, icon.getIconCode());
        }
    }

    /**
     * Draw the warehouse floor.
     * @param drawList The draw list to draw to.
     * @param topLeft The top left point to draw the tile at.
     */
    private void drawWarehouseFloor(ImDrawList drawList, ImVec2 topLeft, WarehouseCanvasColourScheme colourScheme) {
        ImVec2 bottomRight = new ImVec2(topLeft.x + gridStep, topLeft.y + gridStep);
        DrawingUtils.drawRect(drawList, topLeft, bottomRight,
                colourScheme.getWarehouseBackgroundColour());
    }

    /**
     * Draws the selected tile.
     * @param drawList The draw list to draw to.
     */
    public void drawTileGizmo(ImDrawList drawList, WarehouseState<GridWarehouseCoordinateSystem, Point> state,
                              WarehouseCanvasTransform transform,
                              Tile tile, ImVec2 positionOffset, boolean drawHandles, Colour colour) {
        if (tile == null) return;
        Point tilePosition = state.getCoordinateSystem().projectIndexToCoordinate(tile.getIndex());
        ImVec2 topLeft = getTileTopLeft(getOrigin(transform), tilePosition.getX(), tilePosition.getY());
        if (positionOffset != null) {
            topLeft.x += positionOffset.x;
            topLeft.y += positionOffset.y;
        }

        ImVec2 bottomRight = new ImVec2(topLeft.x + gridStep, topLeft.y + gridStep);
        DrawingUtils.drawRect(drawList, topLeft, bottomRight, null, colour,
                1, 0, RectBorderType.Outer);

        // Draw handles
        if (drawHandles) {
            ImVec2 handleSize = new ImVec2(6, 6);
            float handleBorderThickness = 1.5f;
            // Top left
            DrawingUtils.drawRectFromCentre(drawList, topLeft, handleSize,
                    Colour.WHITE, colour,
                    handleBorderThickness, 0, RectBorderType.Outer);
            // Top right
            DrawingUtils.drawRectFromCentre(drawList, new ImVec2(bottomRight.x, topLeft.y), handleSize,
                    Colour.WHITE, colour,
                    handleBorderThickness, 0, RectBorderType.Outer);

            // Bottom left
            DrawingUtils.drawRectFromCentre(drawList, new ImVec2(topLeft.x, bottomRight.y), handleSize,
                    Colour.WHITE, colour,
                    handleBorderThickness, 0, RectBorderType.Outer);
            // Bottom right
            DrawingUtils.drawRectFromCentre(drawList, bottomRight, handleSize,
                    Colour.WHITE, colour,
                    handleBorderThickness, 0, RectBorderType.Outer);
        }
    }

    /**
     * Get the top-left coordinate of the tile at the given warehouse space coordinates.
     * @param tileX The horizontal coordinate of the tile, in warehouse space.
     * @param tileY The vertical coordinate of the tile, in warehouse space.
     * @return An ImVec2 representing the top-left coordinate of the tile in screen space.
     */
    private ImVec2 getTileTopLeft(ImVec2 origin, float tileX, float tileY) {
        return new ImVec2(origin.x + gridStep * tileX, origin.y + gridStep * tileY);
    }

    /**
     * Return the coordinates of the tile at the given point in warehouse space.
     * @remark Note that it is NOT guaranteed that the warehouse space coordinates correspond to an actual tile
     * in the warehouse, e.g. the returned coordinates may be out of bounds!
     * @param p The point in screen space to convert to warehouse space.
     */
    private Point screenToWarehousePoint(ImVec2 p) {
        int tileX = (int) Math.floor(p.x / gridStep);
        int tileY = (int) Math.floor(p.y / gridStep);
        return new Point(tileX, tileY);
    }

    @Override
    public Tile getTileAtMousePosition(WarehouseState<GridWarehouseCoordinateSystem, Point> state,
                                       WarehouseCanvasTransform transform) {
        Point point = screenToWarehousePoint(getRelativeMousePosition(transform));
        return state.getLayout().getTileAt(point);
    }

    /**
     * Update the panOffset so that the contents are centered in the canvas. Mutates panOffset.
     */
    @Override
    public void panToCentre(WarehouseState<GridWarehouseCoordinateSystem, Point> state,
                            WarehouseCanvasTransform transform) {
        GridWarehouseCoordinateSystem coordinateSystem = state.getCoordinateSystem();
        ImVec2 contentSize = transform.getContentSize();
        panOffset.x = gridStep * (float) Math.floor(coordinateSystem.getWidth() / 2.0f)
                + contentSize.x / 2.0f - gridStep * coordinateSystem.getWidth();
        panOffset.y = gridStep * (float) Math.floor(coordinateSystem.getHeight() / 2.0f)
                + contentSize.y / 2.0f - gridStep * coordinateSystem.getHeight();
    }

    private ImVec2 getOrigin(WarehouseCanvasTransform transform) {
        ImVec2 contentTopLeft = transform.getContentTopLeft();
        return new ImVec2(contentTopLeft.x + panOffset.x, contentTopLeft.y + panOffset.y);
    }

    /**
     * Return the position of the mouse relative to the canvas origin.
     */
    private ImVec2 getRelativeMousePosition(WarehouseCanvasTransform transform) {
        ImGuiIO io = ImGui.getIO();
        ImVec2 origin = getOrigin(transform);
        return new ImVec2(io.getMousePos().x - origin.x, io.getMousePos().y - origin.y);
    }
}

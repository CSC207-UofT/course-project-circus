package application.desktop.ui.components.editor.warehouse;

import application.desktop.DesktopApplication;
import application.desktop.ui.Colour;
import application.desktop.ui.FontAwesomeIcon;
import application.desktop.ui.utils.DrawingUtils;
import application.desktop.ui.utils.RectBorderType;
import application.desktop.ui.components.common.Component;
import imgui.*;
import imgui.flag.ImGuiButtonFlags;
import imgui.flag.ImGuiMouseButton;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import utils.Pair;
import warehouse.*;
import warehouse.robots.Robot;
import warehouse.robots.RobotMapper;
import warehouse.tiles.*;

import java.util.List;
import java.util.Map;

/**
 * A canvas that visualizes the Warehouse.
 */
public class WarehouseCanvas extends Component {
    /**
     * The name of the "erase tile popup dialog."
     */
    private static final String ERASE_TILE_POPUP_DIALOG_NAME = "Delete?##erase_tile_dialog_popup";

    private WarehouseState warehouseState;
    private WarehouseCanvasColourScheme colourScheme;

    private final float gridStep;
    private final float minSizeX;
    private final float minSizeY;
    private final boolean showGrid;

    private int frameCounter;

    private ImVec2 size;
    private ImVec2 contentTopLeft;

    private TileType tileTypeToInsert;
    private final TileFactory tileFactory;

    /**
     * Offset applied to canvas elements to enable scrolling.
     */
    private final ImVec2 panOffset;
    /**
     * Current input mode of this WarehouseCanvas.
     */
    private WarehouseCanvasInputMode inputMode;
    /**
     * The currently selected tile, or null if no tile is selected.
     */
    private Tile selectedTile;

    private final ImBoolean rememberEraseTilePopupChoice;

    /**
     * Construct a new WarehouseCanvas with a default colour scheme.
     *
     * @param warehouseState The Warehouse to visualise.
     */
    public WarehouseCanvas(WarehouseState warehouseState) {
        this(warehouseState, WarehouseCanvasColourScheme.DEFAULT,
                32.0f,
                100.0f, 100.0f,
                true);
    }

    /**
     * Construct a new WarehouseCanvas with a custom colour scheme.
     *
     * @param warehouseState    The WarehouseState to visualise.
     * @param colourScheme The colour scheme of this WarehouseCanvas.
     * @param gridStep     The size of a grid cell in screen coordinates.
     * @param minSizeX     The minimum horizontal size of the canvas, in pixels.
     * @param minSizeY     The minimum vertical size of the canvas, in pixels.
     * @param showGrid     Whether to show the grid.
     */
    public WarehouseCanvas(WarehouseState warehouseState,
                           WarehouseCanvasColourScheme colourScheme,
                           float gridStep,
                           float minSizeX, float minSizeY,
                           boolean showGrid) {
        this.warehouseState = warehouseState;
        this.colourScheme = colourScheme;
        this.gridStep = gridStep;
        this.minSizeX = minSizeX;
        this.minSizeY = minSizeY;
        this.showGrid = showGrid;

        tileTypeToInsert = TileType.RACK;
        tileFactory = new TileFactory();

        size = new ImVec2(minSizeX, minSizeY);
        contentTopLeft = new ImVec2(0, 0);
        panOffset = new ImVec2(0, 0);
        inputMode = WarehouseCanvasInputMode.SELECT_TILE;

        frameCounter = 0;
        rememberEraseTilePopupChoice = new ImBoolean(false);
    }

    @Override
    public void drawContent(DesktopApplication application) {
        updateTransform();
        handleInteraction();

        // NOTE: The frame counter is a hacky way of centering the contents on load. It works by waiting for the second
        // frame, so that all the contents have been rendered and updated at least once.
        if (frameCounter <= 1) {
            panToCentre();
            frameCounter += 1;
        }

        ImDrawList drawList = ImGui.getWindowDrawList();
        drawBackground(drawList);
        drawCanvas(drawList);

        drawEraseTilePopupDialog();
    }

    /**
     * Draw the "erase tile popup dialog." Confirms if the user wants to delete the tile.
     */
    private void drawEraseTilePopupDialog() {
        if (selectedTile == null || !ImGui.beginPopup(ERASE_TILE_POPUP_DIALOG_NAME,
                ImGuiWindowFlags.AlwaysAutoResize)) {
            return;
        }

        ImGui.text("All those beautiful tiles will be deleted.\nThis operation cannot be undone!\n\n");
        ImGui.separator();

        ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 0, 0);
        ImGui.checkbox("Don't ask me next time", rememberEraseTilePopupChoice);
        ImGui.popStyleVar();

        if (ImGui.button("OK", 120 ,0)) {
            warehouseState.getWarehouse().setTile(new EmptyTile(selectedTile.getX(), selectedTile.getY()));
            selectedTile = null;
            ImGui.closeCurrentPopup();
        }

        ImGui.setItemDefaultFocus();
        ImGui.sameLine();

        if (ImGui.button("Cancel", 120, 0)) {
            ImGui.closeCurrentPopup();
        }
        ImGui.endPopup();
    }


    /**
     * Return the origin of the canvas in screen space.
     */
    private ImVec2 getOrigin() {
        return new ImVec2(contentTopLeft.x + panOffset.x, contentTopLeft.y + panOffset.y);
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
     * Return the coordinates of the tile at the given point in warehouse space.
     * @remark Note that it is NOT guaranteed that the warehouse space coordinates correspond to an actual tile
     * in the warehouse, e.g. the returned coordinates may be out of bounds!
     * @param p The point in screen space to convert to warehouse space.
     */
    private Pair<Integer, Integer> screenToWarehousePoint(ImVec2 p) {
        int tileX = (int) Math.floor(p.x / gridStep);
        int tileY = (int) Math.floor(p.y / gridStep);
        return new Pair<>(tileX, tileY);
    }

    /**
     * Return the Tile at the given screen point, or null if the point (in warehouse space) is out of bounds.
     * @param p The position of the tile in screen space.
     */
    private Tile getTileFromScreenPoint(ImVec2 p) {
        Pair<Integer, Integer> tileCoords = screenToWarehousePoint(p);
        return warehouseState.getWarehouse().getTileAt(tileCoords.getFirst(), tileCoords.getSecond());
    }

    /**
     * Update the panOffset so that the contents are centered in the canvas. Mutates panOffset.
     */
    private void panToCentre() {
        Warehouse warehouse = warehouseState.getWarehouse();
        panOffset.x = gridStep * (float) Math.floor(warehouse.getWidth() / 2.0f)
                + size.x / 2.0f - gridStep * warehouse.getWidth();
        panOffset.y = gridStep * (float) Math.floor(warehouse.getHeight() / 2.0f)
                + size.y / 2.0f - gridStep * warehouse.getHeight();
    }

    /**
     * Update transform information such as size and position.
     */
    private void updateTransform() {
        ImVec2 contentAvailable = ImGui.getContentRegionAvail();
        size = new ImVec2(Math.max(contentAvailable.x, minSizeX),
                Math.max(contentAvailable.y, minSizeY));
        contentTopLeft = ImGui.getCursorScreenPos();
    }

    /**
     * Handle user interaction.
     */
    private void handleInteraction() {
        ImGui.invisibleButton("canvas", size.x, size.y, ImGuiButtonFlags.MouseButtonLeft |
                ImGuiButtonFlags.MouseButtonRight);

        handleLeftClick();
        handleDragging();
    }

    private boolean isMovingTile;
    private ImVec2 moveTileDragDelta;

    private void handleDragging() {
        ImGuiIO io = ImGui.getIO();
        boolean isHovered = ImGui.isItemHovered();

        // Move tool
        if (isHovered && ImGui.isMouseDragging(ImGuiMouseButton.Left, -1) &&
                inputMode == WarehouseCanvasInputMode.MOVE_TILE && selectedTile != null) {
            isMovingTile = true;
            if (moveTileDragDelta == null) {
                moveTileDragDelta = new ImVec2();
            }

            moveTileDragDelta.x += io.getMouseDeltaX();
            moveTileDragDelta.y += io.getMouseDeltaY();
        }

        if (isMovingTile && ImGui.isMouseReleased(ImGuiMouseButton.Left)) {
            isMovingTile = false;
            moveTileDragDelta = null;

            Pair<Integer, Integer> newTileCoords = screenToWarehousePoint(getRelativeMousePosition());
            Warehouse warehouse = warehouseState.getWarehouse();
            if (warehouse.isTileCoordinateInRange(newTileCoords.getFirst(), newTileCoords.getSecond())) {
                int oldX = selectedTile.getX();
                int oldY = selectedTile.getY();
            }
        }

        // Mouse panning
        if (isHovered && ImGui.isMouseDragging(ImGuiMouseButton.Right, -1)) {
            panOffset.x += io.getMouseDelta().x;
            panOffset.y += io.getMouseDelta().y;
        }
    }

    /**
     * Handle left click interaction.
     */
    private void handleLeftClick() {
        boolean isHovered = ImGui.isItemHovered();
        if (!isHovered || !ImGui.isMouseClicked(ImGuiMouseButton.Left)) {
            return;
        }

        ImGui.setWindowFocus();
        if (inputMode == WarehouseCanvasInputMode.INSERT_TILE) {
            insertTileAtMousePosition();
        } else if (inputMode == WarehouseCanvasInputMode.ERASE_OBJECT) {
            Tile tile = getTileFromScreenPoint(getRelativeMousePosition());
            if (tile != null) {
                Warehouse warehouse = warehouseState.getWarehouse();
                RobotMapper robotMapper = warehouseState.getRobotMapper();

                if (!warehouse.isEmpty(tile)) {
                    if (rememberEraseTilePopupChoice.get()) {
                        warehouse.setTile(new EmptyTile(tile.getX(), tile.getY()));
                    } else {
                        ImGui.openPopup(ERASE_TILE_POPUP_DIALOG_NAME);
                    }
                } else if (robotMapper.isRobotAt(tile)) {
                    robotMapper.removeRobotsAt(tile);
                }

            }
        } else if (inputMode == WarehouseCanvasInputMode.PLACE_ROBOT) {
            placeRobotAtMousePosition();
        }
        // Update selected tile
        selectedTile = getTileFromScreenPoint(getRelativeMousePosition());
    }

    /**
     * Inserts a tile at the current mouse position.
     */
    private void insertTileAtMousePosition() {
        Pair<Integer, Integer> tileCoords = screenToWarehousePoint(getRelativeMousePosition());
        Tile tileToInsert = tileFactory.createTile(tileTypeToInsert, tileCoords.getFirst(), tileCoords.getSecond());
        if (tileToInsert != null) {
            warehouseState.getWarehouse().setTile(tileToInsert);
        }
    }

    /**
     * Places a Robot at the current mouse position.
     * @remark This will only place a robot if and only the tile under the mouse is empty!
     */
    private void placeRobotAtMousePosition() {
        Tile tile = getTileFromScreenPoint(getRelativeMousePosition());
        if (tile != null && warehouseState.getWarehouse().isEmpty(tile)) {
            warehouseState.getRobotMapper().addRobotAt(new Robot(), tile);
        }
    }

    /**
     * Draw the canvas background. Mutates the given ImDrawList.
     */
    private void drawBackground(ImDrawList drawList) {
        ImVec2 contentBottomRight = getContentBottomRight();
        DrawingUtils.drawRect(drawList, contentTopLeft, contentBottomRight, colourScheme.getBackgroundColour(),
                colourScheme.getBorderColour(), 1, 0, RectBorderType.Middle);
    }

    /**
     * Draw the canvas grid. Mutates the given ImDrawList.
     */
    private void drawCanvas(ImDrawList drawList) {
        if (!showGrid) {
            return;
        }

        ImVec2 bottomRight = getContentBottomRight();
        drawList.pushClipRect(contentTopLeft.x, contentTopLeft.y, bottomRight.x, bottomRight.y, true);
        final int gridLineColour = colourScheme.getGridLineColour().toU32Colour();

        // Draw horizontal lines
        for (float x = panOffset.x % gridStep; x < size.x; x += gridStep) {
            float x1 = contentTopLeft.x + x;
            float x2 = contentTopLeft.x + x;
            drawList.addLine(x1, contentTopLeft.y, x2, bottomRight.y, gridLineColour);
        }
        // Draw vertical lines
        for (float y = panOffset.y % gridStep; y < size.y; y += gridStep) {
            float y1 = contentTopLeft.y + y;
            float y2 = contentTopLeft.y + y;
            drawList.addLine(contentTopLeft.x, y1, bottomRight.x, y2, gridLineColour);
        }

        drawWarehouse(drawList);
        drawRobots(drawList);
        drawList.popClipRect();
    }

    /**
     * Draw the robots in the warehouse.
     */
    private void drawRobots(ImDrawList drawList) {
        RobotMapper robotMapper = warehouseState.getRobotMapper();
        List<Robot> robots = robotMapper.getRobots();
        for (Robot robot : robots) {
            Tile robotTile = robotMapper.getRobotPosition(robot);
            ImVec2 topLeft = getTileTopLeft(robotTile.getX(), robotTile.getY());
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
    }

    /**
     * Draw the warehouse centered at the origin. Mutates the given ImDrawList.
     */
    private void drawWarehouse(ImDrawList drawList) {
        ImVec2 origin = getOrigin();
        Warehouse warehouse = warehouseState.getWarehouse();

        // Draw border
        float worldBorderThickness = 2.0f;
        float worldBorderRadius = 10.0f;
        ImVec2 borderBottomRight = new ImVec2(origin.x + gridStep * warehouse.getWidth(),
                origin.y + gridStep * warehouse.getHeight());
        DrawingUtils.drawRect(drawList, origin, borderBottomRight, null,
                colourScheme.getWarehouseBorderColour(),
                worldBorderThickness, worldBorderRadius, RectBorderType.Outer);

        // Draw floors - we need to draw this BEFORE the tiles due to layering
        for (int y = 0; y < warehouse.getHeight(); y++) {
            for (int x = 0; x < warehouse.getWidth(); x++) {
                ImVec2 topLeft = getTileTopLeft(x, y);
                drawWarehouseFloor(drawList, topLeft);
            }
        }

        // Draw tiles
        for (int y = 0; y < warehouse.getHeight(); y++) {
            for (int x = 0; x < warehouse.getWidth(); x++) {
                Tile tile = warehouse.getTileAt(x, y);
                drawTile(drawList, tile, getTileTopLeft(x, y));
            }
        }

        drawSelectedTile(drawList);
    }

    /**
     * Draw the given tile.
     * @param drawList The draw list to draw to.
     * @param tile The tile to draw.
     * @param topLeft The top left point to draw the tile at.
     */
    private void drawTile(ImDrawList drawList, Tile tile, ImVec2 topLeft) {
        if (tile == selectedTile && isMovingTile) {
            topLeft.x += moveTileDragDelta.x;
            topLeft.y += moveTileDragDelta.y;
        }

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
    private void drawWarehouseFloor(ImDrawList drawList, ImVec2 topLeft) {
        ImVec2 bottomRight = new ImVec2(topLeft.x + gridStep, topLeft.y + gridStep);
        DrawingUtils.drawRect(drawList, topLeft, bottomRight,
                colourScheme.getWarehouseBackgroundColour());
    }

    /**
     * Draws the select tile.
     * @param drawList The draw list to draw to.
     */
    private void drawSelectedTile(ImDrawList drawList) {
        if (selectedTile == null) return;

        ImVec2 topLeft = getTileTopLeft(selectedTile.getX(), selectedTile.getY());
        if (moveTileDragDelta != null) {
            topLeft.x += moveTileDragDelta.x;
            topLeft.y += moveTileDragDelta.y;
        }

        ImVec2 bottomRight = new ImVec2(topLeft.x + gridStep, topLeft.y + gridStep);

        Colour outlineColour = getCurrentTileHandleColour();
        DrawingUtils.drawRect(drawList, topLeft, bottomRight, null, outlineColour,
                1, 0, RectBorderType.Outer);

        // Draw handles
        if (inputMode.equals(WarehouseCanvasInputMode.MOVE_TILE)) {
            ImVec2 handleSize = new ImVec2(6, 6);
            float handleBorderThickness = 1.5f;
            // Top left
            DrawingUtils.drawRectFromCentre(drawList, topLeft, handleSize,
                    Colour.WHITE, outlineColour,
                    handleBorderThickness, 0, RectBorderType.Outer);
            // Top right
            DrawingUtils.drawRectFromCentre(drawList, new ImVec2(bottomRight.x, topLeft.y), handleSize,
                    Colour.WHITE, outlineColour,
                    handleBorderThickness, 0, RectBorderType.Outer);

            // Bottom left
            DrawingUtils.drawRectFromCentre(drawList, new ImVec2(topLeft.x, bottomRight.y), handleSize,
                    Colour.WHITE, outlineColour,
                    handleBorderThickness, 0, RectBorderType.Outer);
            // Bottom right
            DrawingUtils.drawRectFromCentre(drawList, bottomRight, handleSize,
                    Colour.WHITE, outlineColour,
                    handleBorderThickness, 0, RectBorderType.Outer);
        }
    }

    /**
     * Get the colour of the tile handle outline.
     */
    private Colour getCurrentTileHandleColour() {
        if (inputMode == WarehouseCanvasInputMode.MOVE_TILE) {
            return colourScheme.getMoveOutlineColour();
        } else {
            return colourScheme.getSelectionOutlineColour();
        }
    }

    /**
     * Get the top-left coordinate of the tile at the given warehouse space coordinates.
     * @param tileX The horizontal coordinate of the tile, in warehouse space.
     * @param tileY The vertical coordinate of the tile, in warehouse space.
     * @return An ImVec2 representing the top-left coordinate of the tile in screen space.
     */
    private ImVec2 getTileTopLeft(int tileX, int tileY) {
        ImVec2 origin = getOrigin();
        return new ImVec2(origin.x + gridStep * tileX, origin.y + gridStep * tileY);
    }

    /**
     * Get the bottom right coordinate of the canvas.
     */
    private ImVec2 getContentBottomRight() {
        return new ImVec2(contentTopLeft.x + size.x, contentTopLeft.y + size.y);
    }

    /**
     * Get the WarehouseState.
     */
    public WarehouseState getWarehouseState() {
        return warehouseState;
    }

    /**
     * Set the WarehouseState to a new value.
     */
    public void setWarehouseState(WarehouseState warehouseState) {
        this.warehouseState = warehouseState;
    }

    /**
     * Gets the current colour scheme of the canvas.
     */
    public WarehouseCanvasColourScheme getColourScheme() {
        return colourScheme;
    }

    /**
     * Sets the colour scheme of the canvas. This configures all styles.
     */
    public void setColourScheme(WarehouseCanvasColourScheme colourScheme) {
        this.colourScheme = colourScheme;
    }

    /**
     * Gets the current input mode.
     */
    public WarehouseCanvasInputMode getInputMode() {
        return inputMode;
    }

    /**
     * Sets the input mode of the canvas, e.g. which tool to use.
     */
    public void setInputMode(WarehouseCanvasInputMode inputMode) {
        this.inputMode = inputMode;
    }

    /**
     * Gets the tile type currently being inserted.
     */
    public TileType getTileTypeToInsert() {
        return tileTypeToInsert;
    }

    /**
     * Sets the type of Tile to insert when the user uses the "insert tile" tool.
     */
    public void setTileTypeToInsert(TileType tileTypeToInsert) {
        this.tileTypeToInsert = tileTypeToInsert;
    }

    /**
     * Get the currently selected Tile.
     * @return The currently selected Tile, or null if no Tile is selected.
     */
    public Tile getSelectedTile() {
        return selectedTile;
    }
}
